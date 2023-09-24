package com.hofuri.cp.task.helper;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WebDriverHelper {

  @Value("${selenium.host}")
  private String seleniumHost;

  @Value("${selenium.options}")
  private List<String> options;

  private static final Duration DURATION_TIMEOUT_SECONDS = Duration.ofSeconds(30);

  /**
   * chromeを操作するWebDriverを取得する
   *
   * @return WebDriverインスタンス
   */
  public RemoteWebDriver getWebDriver() {
    try {
      // seleniumに渡すオプションを定義
      ChromeOptions chromeOptions = new ChromeOptions().addArguments(options);
      RemoteWebDriver remoteWebDriver = new RemoteWebDriver(new URL(seleniumHost), chromeOptions);
      remoteWebDriver.manage().timeouts().implicitlyWait(DURATION_TIMEOUT_SECONDS);
      return remoteWebDriver;
    } catch (MalformedURLException e) {
      throw new RuntimeException(e);
    }
  }
}
