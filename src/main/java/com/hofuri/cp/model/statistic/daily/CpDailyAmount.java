package com.hofuri.cp.model.statistic.daily;

import com.hofuri.cp.repository.database.entity.CpDailyAmountDto;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

/**
 * 特定日付の発行体ごとの発行残高を集計する統計用ドメイン
 */
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class CpDailyAmount {

  private final LocalDate date;

  // 該当の日付における各発行体の日次残高を格納するリスト
  private final List<AmountInfo> amountInfos;

  /**
   * CP残高統計情報のドメイン
   *
   * @param targetDate 集計対象の日付
   * @param dtos       DBから取得した特定日付の各発行体の残高DTOのリスト
   * @return CP残高統計情報のドメイン
   */
  public static CpDailyAmount of(LocalDate targetDate,
      List<CpDailyAmountDto> dtos) {
    List<AmountInfo> amountInfos = dtos.stream()
        .map(CpDailyAmount.AmountInfo::from)
        .collect(Collectors.toUnmodifiableList());

    return CpDailyAmount.builder()
        .date(targetDate)
        .amountInfos(amountInfos)
        .build();
  }

  @Builder(access = AccessLevel.PRIVATE)
  @Getter
  public static class AmountInfo {

    private final String name;
    private final int amount;

    /**
     * 各発行体の日次残高を格納するドメインの生成ファクトリ
     *
     * @param dto DBから取得した各発行体の日付ごとの残高DTO
     * @return 各発行体の日次残高を格納するドメイン
     */
    public static AmountInfo from(CpDailyAmountDto dto) {
      return CpDailyAmount.AmountInfo.builder()
          .name(dto.getName())
          .amount(dto.getAmount())
          .build();
    }
  }

}
