package com.hofuri.cp.presentation.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import java.time.LocalDate;
import javax.validation.constraints.AssertTrue;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CpDailyAmountRequest {

  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @NotNull()
  private LocalDate from;

  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @NotNull
  private LocalDate to;

  @AssertTrue(message = "開始日付は終了日付より前の値を入力してください")
  public boolean isValidDate() {
    return !to.isBefore(from);
  }

}
