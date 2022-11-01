package com.hofuri.cp.repository.database.entity;

import java.time.LocalDate;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
/**
 * DBのCP残高統計情報を受け取りオブジェクトにマッピングする際のDTOオブジェクト
 */
public class CpTotalAmountDto {

  private LocalDate date;
  private int amount;

}
