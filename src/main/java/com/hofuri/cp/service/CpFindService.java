package com.hofuri.cp.service;

import com.hofuri.cp.model.statistic.daily.CpDailyAmountList;
import com.hofuri.cp.repository.database.CpRepository;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CpFindService {

  private final CpRepository cpRepository;

  @Retryable
  public CpDailyAmountList fetchDailyCpAmountBetweenDate(LocalDate from, LocalDate to) {
    return cpRepository.fetchDailyCpAmountBetweenDate(from, to);
  }

}
