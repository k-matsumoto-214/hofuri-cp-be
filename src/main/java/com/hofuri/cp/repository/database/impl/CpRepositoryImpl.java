package com.hofuri.cp.repository.database.impl;

import com.hofuri.cp.model.CpInfo;
import com.hofuri.cp.model.statistic.daily.CpDailyAmountList;
import com.hofuri.cp.model.statistic.daily.CpTotalAmountList;
import com.hofuri.cp.repository.database.CpRepository;
import com.hofuri.cp.repository.database.entity.CpDailyAmountDto;
import com.hofuri.cp.repository.database.entity.CpTotalAmountDto;
import com.hofuri.cp.repository.database.mapper.CpMapper;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CpRepositoryImpl implements CpRepository {

  private final CpMapper cpMapper;

  @Override
  public CpDailyAmountList fetchDailyCpAmountBetweenDate(LocalDate from, LocalDate to) {
    List<CpDailyAmountDto> cpDailyAmountDtos = cpMapper.fetchDailyCpAmountBetweenDate(from, to);
    return CpDailyAmountList.from(cpDailyAmountDtos);
  }

  @Override
  public CpTotalAmountList fetchTotalCpAmountBetweenDate(LocalDate from, LocalDate to) {
    List<CpTotalAmountDto> cpTotalAmountDtos = cpMapper.fetchTotalCpAmountBetweenDate(from, to);
    return CpTotalAmountList.from(cpTotalAmountDtos);
  }

  @Override
  public void upsert(CpInfo cpInfo) {
    cpMapper.upsert(cpInfo);
  }
}
