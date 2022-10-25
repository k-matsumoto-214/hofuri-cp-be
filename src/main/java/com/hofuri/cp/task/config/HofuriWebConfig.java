package com.hofuri.cp.task.config;

import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.openqa.selenium.By;
import org.springframework.stereotype.Component;

/**
 * ほふりのCP残高をクロールする際の各種情報を扱うクラス
 */
@Component
public class HofuriWebConfig {

  private static final String CP_PAGE_URL =
      "https://www.jasdec.com/reading/cpmei.php?todb=ok&nexts=0&offset=%s";

  private static final String SELECTOR_DATE =
      "#MAIN > div:nth-child(3) > table > tbody > tr > td > font > table:nth-child(5) > tbody > tr > td";

  private static final String SELECTOR_CP_NUMBER =
      "#MAIN > div:nth-child(3) > table > tbody > tr > td > font > table:nth-child(7) > tbody > tr:nth-child(1) > td > b";

  private static final String SELECTOR_NAME =
      "#MAIN > div:nth-child(3) > table > tbody > tr > td > font > table:nth-child(%d) > tbody > tr > td > table > tbody > tr:nth-child(1) > td > span";

  private static final String SELECTOR_ISIN_CODE =
      "#MAIN > div:nth-child(3) > table > tbody > tr > td > font > table:nth-child(%d) > tbody > tr > td > table > tbody > tr:nth-child(7) > td:nth-child(2) > span";

  private static final String SELECTOR_BOND_UNIT =
      "#MAIN > div:nth-child(3) > table > tbody > tr > td > font > table:nth-child(%d) > tbody > tr > td > table > tbody > tr:nth-child(7) > td:nth-child(4) > span";

  private static final String SELECTOR_AMOUNT =
      "#MAIN > div:nth-child(3) > table > tbody > tr > td > font > table:nth-child(%d) > tbody > tr > td > table > tbody > tr:nth-child(8) > td:nth-child(2) > span";

  private static final int ADD_NUMBER_DATA = 8; // ほふりからCP情報を取得する際、各要素の格納場所を指すために各CP情報の行番号に足す定数


  private static final NumberFormat NUMBER_FORMATTER = NumberFormat.getInstance(); // ほふりの数値変換用のフォーマッター

  private static final DateTimeFormatter DATE_FORMATTER =
      DateTimeFormatter.ofPattern("yyyy/MM/dd"); // ほふりの残高更新日時の変換用フォーマッター

  private static final int SUBSTRING_DATE_FROM = 1; // ほふりの残高更新日時のトリミング開始位置

  private static final int SUBSTRING_DATE_TO = 11; // ほふりの残高更新日時のトリミング終了位置

  private static final int SUBSTRING_CP_NUMBER_FROM = 6; // ほふりのCP件数のトリミング開始位置

  private static final int SUBSTRING_CP_NUMBER_TO = 10; // ほふりのCP件数のトリミング終了位置

  private static final int SUBSTRING_ISSUER_CODE_FROM = 5; // ほふりのISINCodeから発行体コードを取得するトリミング終了位置

  private static final int SUBSTRING_ISSUER_CODE_TO = 8; // ほふりのISINCodeから発行体コードを取得するトリミング終了位置

  private static final int NUMBER_IN_PAGE = 50; // ほふりの１ページあたりのCP件数

  /**
   * 取得したいページ番号からURLを生成して返す
   *
   * @param pageNumber 取得したいCP情報のページ番号 >= 1
   * @return 取得したいページのURL
   */
  public String cpPageUrl(int pageNumber) {
    int offset = (pageNumber - 1) * 50;

    return String.format(CP_PAGE_URL, offset);
  }

  /**
   * ほふりのCP残高ページの1ページ目のURLを返します
   *
   * @return CP残高ページの1ページ目のURL
   */
  public String firstCpPageUrl() {
    return String.format(CP_PAGE_URL, 0);
  }

  public By dateSelector() {
    return By.cssSelector(SELECTOR_DATE);
  }

  public By cpNumberSelector() {
    return By.cssSelector(SELECTOR_CP_NUMBER);
  }

  /**
   * 発行体名の取得セレクタ
   *
   * @param number 取得したいCPテーブルの行番号 0 <= number <= 49
   * @return 発行体名の取得セレクタ
   */
  public By nameSelector(int number) {
    return By.cssSelector(String.format(SELECTOR_NAME, number + ADD_NUMBER_DATA));
  }

  /**
   * ISINCodeの取得セレクタ
   *
   * @param number 取得したいCPテーブルの行番号 0 <= number <= 49
   * @return ISINCodeの取得セレクタ
   */
  public By isinCodeSelector(int number) {
    return By.cssSelector(String.format(SELECTOR_ISIN_CODE, number + ADD_NUMBER_DATA));
  }

  /**
   * 各社債金額の取得セレクタ
   *
   * @param number 取得したいCPテーブルの行番号 0 <= number <= 49
   * @return 各社債金額の取得セレクタ
   */
  public By bondUnitSelector(int number) {
    return By.cssSelector(String.format(SELECTOR_BOND_UNIT, number + ADD_NUMBER_DATA));
  }

  /**
   * 発行金額の取得セレクタ
   *
   * @param number 取得したいCPテーブルの行番号 0 <= number <= 49
   * @return 発行金額の取得セレクタ
   */
  public By amountSelector(int number) {
    return By.cssSelector(String.format(SELECTOR_AMOUNT, number + ADD_NUMBER_DATA));
  }

  /**
   * ほふりから取得した更新日文字列からLocalDateの更新日を返す
   *
   * @param dateString 更新日時（[2022/10/21 19:00:00]更新のような文字列）
   * @return 更新日
   */
  public LocalDate parseDate(String dateString) {
    return LocalDate.parse(
        dateString.substring(SUBSTRING_DATE_FROM, SUBSTRING_DATE_TO),
        DATE_FORMATTER);
  }

  /**
   * ほふりページから取得したCP件数文字列をint型に変換する
   *
   * @param cpNumberString ほふりから取得したCP件数を含む文字列 ex. "検索の結果（3299件中1～ 50 件を表示）"
   * @return cp件数
   */
  public int parseCpNumber(String cpNumberString) {
    return Integer.parseInt(
        cpNumberString.substring(SUBSTRING_CP_NUMBER_FROM, SUBSTRING_CP_NUMBER_TO));
  }

  /**
   * ほふりCP残高ページに1ページに表示されている件数を返す
   *
   * @return 1ページあたりの件数
   */
  public int numberInPage() {
    return NUMBER_IN_PAGE;
  }

  /**
   * ほふりから取得した発行金額文字列から数値の発行金額を返す
   *
   * @param cpAmountString 発行金額（5,000のようにコンマで区切られている数値の文字列）
   * @return 発行金額
   */
  public int parseCpAmount(String cpAmountString) {
    try {
      return NUMBER_FORMATTER.parse(cpAmountString).intValue();
    } catch (ParseException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * ISINCodeから発行体コードを抜き出して返却する
   *
   * @return 発行体コード
   */
  public String parseIssuerCode(String isinCode) {
    return isinCode.substring(SUBSTRING_ISSUER_CODE_FROM, SUBSTRING_ISSUER_CODE_TO);
  }

  /**
   * ほふりから取得した社債単位文字列から数値の社債単位を返す
   *
   * @param bondUnitString 各社債単位（5,000のようにコンマで区切られている数値の文字列）
   * @return 社債単位
   */
  public int parseBondUnit(String bondUnitString) {
    try {
      return NUMBER_FORMATTER.parse(bondUnitString).intValue();
    } catch (ParseException e) {
      throw new RuntimeException(e);
    }
  }
}
