package com.hofuri.cp.presentation.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hofuri.cp.model.statistic.daily.CpDailyAmount;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class CpDailyAmountResponse {

  @JsonFormat(pattern = "yyyy-MM-dd")
  private final List<LocalDate> dates;
  private final List<CpDailyAmountData> cpDailyAmountDatas;

  /**
   * レスポンスの生成メソッド
   *
   * @param cpDailyAmounts 各日付ごとの各発行体の発行残高のリストをもつドメインのリスト
   * @return CP残高統計情報レスポンス
   */
  public static CpDailyAmountResponse from(List<LocalDate> dates,
      List<CpDailyAmount> cpDailyAmounts) {

    List<CpDailyAmountData> cpDailyAmountDatas = cpDailyAmounts.stream()
        .map(CpDailyAmountData::from)
        .collect(Collectors.toUnmodifiableList());

    return CpDailyAmountResponse.builder()
        .dates(dates)
        .cpDailyAmountDatas(cpDailyAmountDatas)
        .build();
  }

  @Getter
  @Builder(access = AccessLevel.PRIVATE)
  public static class CpDailyAmountData {

    private final String issureName;
    private final List<Integer> amounts;

    /**
     * レスポンスの生成メソッド
     *
     * @param cpDailyAmount cpAmountドメイン
     * @return CP残高統計情報レスポンスの各データ
     */
    public static CpDailyAmountData from(CpDailyAmount cpDailyAmount) {

      return CpDailyAmountData.builder()
          .issureName(cpDailyAmount.getIssureName())
          .amounts(cpDailyAmount.getAmounts())
          .build();
    }
  }
}
