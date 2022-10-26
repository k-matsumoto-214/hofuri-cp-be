package com.hofuri.cp.presentation.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import java.time.LocalDate;
import java.util.Objects;
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
    if (Objects.isNull(from) || Objects.isNull(to)) {
      return false;
    }
    return !to.isBefore(from);
  }

}
