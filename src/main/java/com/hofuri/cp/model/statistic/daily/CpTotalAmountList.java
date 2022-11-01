package com.hofuri.cp.model.statistic.daily;

import com.hofuri.cp.repository.database.entity.CpTotalAmountDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;


@Builder(access = AccessLevel.PRIVATE)
@Getter
public class CpTotalAmountList {

  private final List<CpTotalAmount> cpTotalAmounts;

  /**
   * CP総発行残高統計情報のリストドメイン
   *
   * @param cpTotalAmountDtos DBから取得したCP総残高統計情報のDTO
   * @return CP総発行残高統計情報のリストドメイン
   */
  public static CpTotalAmountList from(List<CpTotalAmountDto> cpTotalAmountDtos) {
    List<CpTotalAmount> cpTotalAmounts = cpTotalAmountDtos.stream()
        .map(CpTotalAmount::from)
        .collect(Collectors.toUnmodifiableList());

    return CpTotalAmountList.builder()
        .cpTotalAmounts(cpTotalAmounts)
        .build();
  }

}
