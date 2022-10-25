package com.hofuri.cp.task.model;

import lombok.AccessLevel;
import lombok.Builder;

/**
 * ほふりCP残高ページをページングするドメイン
 */
@Builder(access = AccessLevel.PRIVATE)
public class Paging {

  int totalNumber;
  int numberInPage;

  /**
   * ページングドメイン生成
   *
   * @param totalNumber  ページング対象の要素数（全件数）
   * @param numberInPage 1ページに表示する件数
   * @return ページング用ドメイン
   */
  public static Paging of(int totalNumber, int numberInPage) {
    return Paging.builder()
        .totalNumber(totalNumber)
        .numberInPage(numberInPage)
        .build();
  }

  /**
   * ページ数を取得する
   *
   * @return 最大ページ数
   */
  public int maxPage() {
    return (totalNumber + numberInPage - 1) / numberInPage;
  }

  /**
   * 指定したページに存在する要素数を返します
   *
   * @param pageNumber 要素数を取得したいページ番号 >= 1
   * @return 特定ページに存在する要素数
   */
  public int numberInSpecificPage(int pageNumber) {
    if (pageNumber < this.maxPage()) { // 最終ページ以外は1ページあたりの表示件数をそのまま返す
      return numberInPage;
    } else {
      return totalNumber % numberInPage; // 最終ページの時、総件数を1ページあたりの件数でわった余りを返す
    }
  }
}
