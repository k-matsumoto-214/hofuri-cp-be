package com.hofuri.cp.repository.database.mapper;

import com.hofuri.cp.model.CpInfo;
import com.hofuri.cp.repository.database.entity.CpDailyAmountDto;
import java.time.LocalDate;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CpMapper {

  void upsert(CpInfo cpInfo);

  List<CpDailyAmountDto> findCpAmountBetweenDate(LocalDate from, LocalDate to);
}
