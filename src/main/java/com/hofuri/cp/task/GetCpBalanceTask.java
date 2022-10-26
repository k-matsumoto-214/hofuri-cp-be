package com.hofuri.cp.task;

import com.hofuri.cp.model.CpInfoList;
import com.hofuri.cp.service.CpRegisterService;
import com.hofuri.cp.task.component.CpGetter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class GetCpBalanceTask {

  private final CpGetter cpGetter;
  private final CpRegisterService cpRegisterService;

  /**
   * ほふりから日時のCP発行残高を取得してDBに登録する
   */
  @Scheduled(cron = "${cron.get-cp-balance}")
  public void getCpBalance() {
    try {
      log.info("CP残高情報取得開始");

      CpInfoList cpInfoList = cpGetter.getCpInfoList();
      cpInfoList.getCpInfos().forEach(cpRegisterService::registerCp);

      log.info("CP残高情報取得完了");

    } catch (Exception e) {
      log.error("CP残高情報取得失敗: {}", e.toString());
    }
  }

}
