package com.hofuri.cp.task.entity;

import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

/**
 * ほふりのサイトからクロールしてきたCP情報をそのままバインドするDTOクラス
 */
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class CpWebDto {

  private final String name;
  private final String issuerCode;
  private final String isinCode;
  private final int bondUnit;
  private final int amount;
  private final LocalDate date;


  /**
   * ほふりWEBサイトをクロールして取得したCP情報のDTO
   *
   * @param name       発行体名
   * @param issuerCode 発行体コード
   * @param isinCode   ISINCode
   * @param bondUnit   各社債単位
   * @param amount     発行金額
   * @param date       更新日時
   * @return WEBから取得したCP情報のDTO
   */
  public static CpWebDto of(String name, String issuerCode, String isinCode, int bondUnit,
      int amount, LocalDate date) {

    return CpWebDto.builder()
        .name(name)
        .issuerCode(issuerCode)
        .isinCode(isinCode)
        .bondUnit(bondUnit)
        .amount(amount)
        .date(date)
        .build();
  }


}
