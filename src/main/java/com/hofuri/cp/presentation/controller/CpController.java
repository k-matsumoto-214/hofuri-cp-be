package com.hofuri.cp.presentation.controller;

import com.hofuri.cp.model.statistic.daily.CpDailyAmountList;
import com.hofuri.cp.model.statistic.daily.CpTotalAmountList;
import com.hofuri.cp.presentation.request.CpDailyAmountRequest;
import com.hofuri.cp.presentation.request.CpTotalAmountRequest;
import com.hofuri.cp.presentation.response.CpDailyAmountResponse;
import com.hofuri.cp.presentation.response.CpTotalAmountResponse;
import com.hofuri.cp.service.CpFindService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CpController {

  private final CpFindService cpFindService;

  /**
   * 指定した期間の各発行体の日次残高を取得する
   * <br>
   * (フロントでグラフ表示・エクセル作成を前提に値を返却する)
   *
   * @param request 日次残高取得リクエスト
   * @return 指定した期間の各発行体の日次残高レスポンス
   */
  @GetMapping("/cp/amount/daily")
  public CpDailyAmountResponse fetchDailyCpAmountBetweenDate(@Valid CpDailyAmountRequest request) {

    CpDailyAmountList cpDailyAmountList =
        cpFindService.fetchDailyCpAmountBetweenDate(request.getFrom(), request.getTo());

    return CpDailyAmountResponse.from(cpDailyAmountList.getDates(),
        cpDailyAmountList.getCpDailyAmounts());
  }


  /**
   * 指定した期間の日次総発行残高を取得する
   *
   * @param request 日次総発行残高取得リクエスト
   * @return 指定した期間の総発行残高の日次残高レスポンス
   */
  @GetMapping("/cp/amount/total")
  public CpTotalAmountResponse fetchTotalCpAmountBetweenDate(@Valid CpTotalAmountRequest request) {

    CpTotalAmountList cpTotalAmountList =
        cpFindService.fetchTotalCpAmountBetweenDate(request.getFrom(), request.getTo());

    return CpTotalAmountResponse.from(cpTotalAmountList.getCpTotalAmounts());
  }
}
