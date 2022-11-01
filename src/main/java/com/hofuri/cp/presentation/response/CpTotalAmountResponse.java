package com.hofuri.cp.presentation.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hofuri.cp.model.statistic.daily.CpTotalAmount;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)

/**
 * レスポンスの生成メソッド
 *
 * @param cpTotalAmounts cpAmountドメインのリスト
 * @return CP総発行残高統計情報レスポンスデータのレスポンス
 */
public class CpTotalAmountResponse {

  private final List<CpTotalAmountData> cpTotalAmountDatas;

  public static CpTotalAmountResponse from(List<CpTotalAmount> cpTotalAmounts) {
    List<CpTotalAmountData> cpTotalAmountDatas = cpTotalAmounts.stream()
        .map(CpTotalAmountData::from)
        .collect(Collectors.toUnmodifiableList());

    return CpTotalAmountResponse.builder()
        .cpTotalAmountDatas(cpTotalAmountDatas)
        .build();
  }

  @Getter
  @Builder(access = AccessLevel.PRIVATE)
  public static class CpTotalAmountData {

    @JsonFormat(pattern = "yyyy-MM-dd")
    private final LocalDate date;

    private final int amount;

    /**
     * レスポンスの生成メソッド
     *
     * @param cpTotalAmount cpAmountドメイン
     * @return CP残高統計情報レスポンスの各データ
     */
    public static CpTotalAmountData from(CpTotalAmount cpTotalAmount) {
      return CpTotalAmountData.builder()
          .date(cpTotalAmount.getDate())
          .amount(cpTotalAmount.getAmount())
          .build();
    }
  }
}
