package com.hofuri.cp.model;

import com.hofuri.cp.task.entity.CpWebDto;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Builder(access = AccessLevel.PRIVATE)
@Getter
public class CpInfo {

  private final String name;
  private final String issuerCode;
  private final String isinCode;
  private final int bondUnit;
  private final int amount;
  private final LocalDate date;

  public static CpInfo from(CpWebDto dto) {
    return CpInfo.builder()
        .name(dto.getName())
        .issuerCode(dto.getIssuerCode())
        .isinCode(dto.getIsinCode())
        .bondUnit(dto.getBondUnit())
        .amount(dto.getAmount())
        .date(dto.getDate())
        .build();
  }
}
