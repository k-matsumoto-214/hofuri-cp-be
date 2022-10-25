package com.hofuri.cp.service;

import com.hofuri.cp.model.CpInfo;
import com.hofuri.cp.repository.database.CpRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CpRegisterService {

  private final CpRepository cpRepository;

  /**
   * DBにCP情報を登録する
   *
   * @param cpInfo CPドメイン
   */
  public void registerCp(CpInfo cpInfo) {
    cpRepository.upsert(cpInfo);
  }
}
