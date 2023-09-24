package com.hofuri.cp.model.statistic.daily;

import com.hofuri.cp.repository.database.entity.CpDailyAmountDto;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Builder(access = AccessLevel.PRIVATE)
@Getter
public class CpDailyAmountList {

  private final List<LocalDate> dates;
  private final List<CpDailyAmount> cpDailyAmounts;

  /**
   * CP残高統計情報のリストドメイン
   *
   * @param cpDailyAmountDtos DBから取得したCP残高統計情報のDTO
   * @return CP残高統計情報のリストドメイン
   */
  public static CpDailyAmountList from(List<CpDailyAmountDto> cpDailyAmountDtos) {

    // 日付で各日付ごとの各発行体の残高をグルーピング
    Map<LocalDate, List<CpDailyAmountDto>> cpDailyAmountDtosGroupingByDate =
        cpDailyAmountDtos.stream()
            .collect(Collectors.groupingBy(CpDailyAmountDto::getDate));

    // 各発行体ごとに発行残高をグルーピング（発行残高がない日時は0が入っている）
    Map<String, List<CpDailyAmountDto>> cpDailyAmountDtosGroupingByName =
        cpDailyAmountDtos.stream()
            .collect(Collectors.groupingBy(CpDailyAmountDto::getName));

    // 1発行体でも残高のある日付リストを作成する
    List<LocalDate> dates = cpDailyAmountDtosGroupingByDate.entrySet()
        .stream()
        .sorted(Entry.comparingByKey())
        .map(Entry::getKey)
        .collect(Collectors.toUnmodifiableList());

    // 発行体ごとの発行残高リストを作成する
    List<CpDailyAmount> cpDailyAmounts = cpDailyAmountDtosGroupingByName.entrySet()
        .stream()
        .sorted(Entry.comparingByKey())
        .map(entry -> CpDailyAmount.of(entry.getKey(), entry.getValue()))
        .collect(Collectors.toUnmodifiableList());

    return CpDailyAmountList.builder()
        .dates(dates)
        .cpDailyAmounts(cpDailyAmounts)
        .build();
  }
}
