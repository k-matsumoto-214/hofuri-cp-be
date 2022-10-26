package com.hofuri.cp.repository.database;

import com.hofuri.cp.model.CpInfo;
import com.hofuri.cp.model.statistic.daily.CpDailyAmountList;
import java.time.LocalDate;

public interface CpRepository {

  /**
   * 指定した日付間に含まれるCP情報を取得します
   *
   * @param from 開始日
   * @param to   終了日
   * @return CP残高の統計ドメイン
   */
  CpDailyAmountList fetchDailyCpAmountBetweenDate(LocalDate from, LocalDate to);

  /**
   * CP情報をDBに登録します
   *
   * @param cpInfo CPドメイン
   */
  void upsert(CpInfo cpInfo);

}
