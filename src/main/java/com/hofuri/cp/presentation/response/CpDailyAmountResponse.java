package com.hofuri.cp.presentation.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hofuri.cp.model.statistic.daily.CpDailyAmount;
import com.hofuri.cp.model.statistic.daily.CpDailyAmount.AmountInfo;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class CpDailyAmountResponse {

  private List<CpDailyAmountData> cpDailyAmountDatas;

  /**
   * レスポンスの生成メソッド
   *
   * @param cpDailyAmounts 各日付ごとの各発行体の発行残高のリストをもつドメインのリスト
   * @return CP残高統計情報レスポンス
   */
  public static CpDailyAmountResponse from(List<CpDailyAmount> cpDailyAmounts) {

    List<CpDailyAmountData> cpDailyAmountDatas = cpDailyAmounts.stream()
        .map(CpDailyAmountData::from)
        .collect(Collectors.toUnmodifiableList());

    return CpDailyAmountResponse.builder()
        .cpDailyAmountDatas(cpDailyAmountDatas)
        .build();
  }

  @Getter
  @Builder(access = AccessLevel.PRIVATE)
  public static class CpDailyAmountData {

    @JsonFormat(pattern = "yyyy-MM-dd")
    private final LocalDate date;

    private final List<CpAmountData> cpAmountData;

    /**
     * レスポンスの生成メソッド
     *
     * @param cpDailyAmount cpAmountドメイン
     * @return CP残高統計情報レスポンスの各データ
     */
    public static CpDailyAmountData from(CpDailyAmount cpDailyAmount) {

      List<CpAmountData> cpAmountData = cpDailyAmount.getAmountInfos()
          .stream()
          .map(CpAmountData::from)
          .collect(Collectors.toUnmodifiableList());

      return CpDailyAmountData.builder()
          .date(cpDailyAmount.getDate())
          .cpAmountData(cpAmountData)
          .build();
    }

    @Getter
    @Builder(access = AccessLevel.PRIVATE)
    public static class CpAmountData {

      private final String name;
      private final int amount;

      /**
       * レスポンス用の各発行体の特定日付の発行残高を表すオブジェクト
       *
       * @param amountInfo 各発行体の特定日付の発行残高を表すドメイン
       * @return レスポンス用の各発行体の特定日付の発行残高を表すオブジェクト
       */
      public static CpAmountData from(AmountInfo amountInfo) {
        return CpAmountData.builder()
            .name(amountInfo.getName())
            .amount(amountInfo.getAmount())
            .build();
      }
    }
  }
}
