package com.hofuri.cp.task.component;

import com.hofuri.cp.config.HofuriWebConfig;
import com.hofuri.cp.config.WebDriverConfig;
import com.hofuri.cp.model.CpInfoList;
import com.hofuri.cp.model.Paging;
import com.hofuri.cp.model.dto.CpWebDto;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CpGetter {

  private final WebDriverConfig webDriverConfig;
  private final HofuriWebConfig hofuriWebConfig;


  /**
   * CP残高の情報をほふりサイトより取得する
   *
   * @return CP情報のリストドメイン
   */
  @Retryable
  public CpInfoList getCpInfoList() {
    // 結果返却用のリストを定義
    List<CpWebDto> cpWebDtos = new ArrayList<>();

    // seleniumドライバ取得
    WebDriver webDriver = webDriverConfig.getWebDriver();

    try {
      //保振のページを開く
      webDriver.get(hofuriWebConfig.firstCpPageUrl());

      // 残高更新日を取得
      final By dateSelector = hofuriWebConfig.dateSelector();
      final String dateString = webDriver.findElement(dateSelector).getText();
      final LocalDate date = hofuriWebConfig.parseDate(dateString);

      // CP件数を取得
      final By cpNumberSelector = hofuriWebConfig.cpNumberSelector();
      final String cpNumberString = webDriver.findElement(cpNumberSelector).getText();
      final int cpNumber = hofuriWebConfig.parseCpNumber(cpNumberString);

      log.info("取得対象CP件数: {} 件", cpNumber);

      Paging paging = Paging.of(cpNumber, hofuriWebConfig.numberInPage());

      log.info("取得ページ数: {}, 最終ページ要素数: {}",
          paging.maxPage(),
          paging.numberInSpecificPage(paging.maxPage()));

      // 最大ページ数までCP情報を取得し続ける(ページ数カウントは1から)
      for (int pageNumber = 1; pageNumber <= paging.maxPage(); pageNumber++) {
        try {
          // 対象ページへ飛ぶ
          webDriver.get(hofuriWebConfig.cpPageUrl(pageNumber));

          // 一秒停止
          Thread.sleep(1000);

          // 表示されている要素を取得する
          cpWebDtos.addAll(
              this.getCpInfoInPage(webDriver, paging.numberInSpecificPage(pageNumber), date));

        } catch (InterruptedException e) {
          log.info("割り込み例外が発生 {}", e.toString());
          Thread.currentThread().interrupt();
          throw new RuntimeException(e);
        }
      }

      return CpInfoList.from(cpWebDtos);

    } catch (Exception e) {
      log.error("CP残高取得中に予期せぬ例外が発生しました。 {}", e.toString());
      throw new RuntimeException(e);

    } finally {
      webDriver.quit();
    }
  }

  /**
   * 入力されたWebDriverで表示しているページのCP情報を全権取得する
   *
   * @param webDriver    CP情報を表示しているWebDriver
   * @param numberInPage 取得対象のページに存在する要素数
   * @param date         ページから取得した残高更新日
   * @return ページから取得したCP情報のDTOリスト
   */
  private List<CpWebDto> getCpInfoInPage(WebDriver webDriver, int numberInPage, LocalDate date) {

    List<CpWebDto> results = new ArrayList<>();

    // 1ページに含まれる要素数分ループ（要素取得の際のカウントは0から）
    for (int repeatNumber = 0; repeatNumber < numberInPage; repeatNumber++) {

      // 発行体名の取得
      String name = webDriver
          .findElement(hofuriWebConfig.nameSelector(repeatNumber))
          .getText();

      // ISINCodeの取得
      String isinCode = webDriver
          .findElement(hofuriWebConfig.isinCodeSelector(repeatNumber))
          .getText();

      //発行体コードの取得
      String issuerCode = hofuriWebConfig.parseIssuerCode(isinCode);

      // 各社債の金額取得
      String bondUnitString = webDriver
          .findElement(hofuriWebConfig.bondUnitSelector(repeatNumber))
          .getText();
      int bondUnit = hofuriWebConfig.parseBondUnit(bondUnitString);

      // 発行総額の取得
      String amountString = webDriver
          .findElement(hofuriWebConfig.amountSelector(repeatNumber))
          .getText();
      int amount = hofuriWebConfig.parseCpAmount(amountString);

      // CP情報のDTOを生成し結果配列に格納
      CpWebDto dto = CpWebDto.of(name, issuerCode, isinCode, bondUnit, amount, date);
      results.add(dto);
    }

    return results;
  }

}
