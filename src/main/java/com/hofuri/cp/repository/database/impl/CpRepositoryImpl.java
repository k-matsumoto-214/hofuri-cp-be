package com.hofuri.cp.repository.database.impl;

import com.hofuri.cp.model.CpInfo;
import com.hofuri.cp.model.statistic.daily.CpDailyAmountList;
import com.hofuri.cp.repository.database.CpRepository;
import com.hofuri.cp.repository.database.entity.CpDailyAmountDto;
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
  public CpDailyAmountList findCpAmountBetweenDate(LocalDate from, LocalDate to) {
    List<CpDailyAmountDto> cpDailyAmountDtos = cpMapper.findCpAmountBetweenDate(from, to);
    return CpDailyAmountList.from(cpDailyAmountDtos);
  }

  @Override
  public void upsert(CpInfo cpInfo) {
    cpMapper.upsert(cpInfo);
  }
}
