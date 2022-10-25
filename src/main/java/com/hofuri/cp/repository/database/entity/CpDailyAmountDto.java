package com.hofuri.cp.repository.database.entity;

import java.time.LocalDate;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
/**
 * DBのCP残高統計情報を受け取りオブジェクトにマッピングする際のDTOオブジェクト
 */
public class CpDailyAmountDto {

  private String name;
  private int amount;
  private LocalDate date;

}
