package com.hofuri.cp.model.statistic.daily;

import com.hofuri.cp.repository.database.entity.CpDailyAmountDto;
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

  private final String issureName;
  // 該当の日付における各発行体の日次残高を格納するリスト
  private final List<Integer> amounts;

  /**
   * CP残高統計情報のドメイン
   *
   * @param issureName 集計対象の発行体名
   * @param dtos       DBから取得した各発行体の日時残高DTOのリスト
   * @return CP残高統計情報のドメイン
   */
  public static CpDailyAmount of(String issureName,
      List<CpDailyAmountDto> dtos) {

    return CpDailyAmount.builder()
        .issureName(issureName)
        .amounts(
            dtos.stream()
                .map(CpDailyAmountDto::getAmount)
                .collect(Collectors.toUnmodifiableList())
        )
        .build();
  }

}
