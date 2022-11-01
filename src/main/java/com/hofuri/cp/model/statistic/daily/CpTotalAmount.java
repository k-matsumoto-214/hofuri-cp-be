package com.hofuri.cp.model.statistic.daily;

import com.hofuri.cp.repository.database.entity.CpTotalAmountDto;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

/**
 * 特定日付の総発行残高を集計する統計用ドメイン
 */
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class CpTotalAmount {

  private final LocalDate date;
  private final int amount;

  /**
   * 日時の総発行残高を保持するドメイン
   *
   * @param dto DBから取得した日時の総発行残高データ
   * @return 日時の総発行残高を保持するドメイン
   */
  public static CpTotalAmount from(CpTotalAmountDto dto) {
    return CpTotalAmount.builder()
        .date(dto.getDate())
        .amount(dto.getAmount())
        .build();
  }
}
