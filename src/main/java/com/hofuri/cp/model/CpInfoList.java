package com.hofuri.cp.model;

import com.hofuri.cp.task.entity.CpWebDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Builder(access = AccessLevel.PRIVATE)
@Getter
public class CpInfoList {

  private final List<CpInfo> cpInfos;

  /**
   * リストドメインファクトリ
   *
   * @param cpWebDtos webから取得したCP情報のリスト
   * @return CP情報のリストドメイン
   */
  public static CpInfoList from(List<CpWebDto> cpWebDtos) {
    List<CpInfo> cpInfos = cpWebDtos.stream()
        .map(CpInfo::from)
        .collect(Collectors.toUnmodifiableList());

    return CpInfoList.builder()
        .cpInfos(cpInfos)
        .build();
  }
}
