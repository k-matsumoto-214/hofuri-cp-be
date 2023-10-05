# CP(短期社債)残高情報を閲覧できるWEBアプリ（https://hofuri.keismats.com/)

### 目的
- [ほふり（証券保管振替機構）](https://www.jasdec.com/description/cp/cpmei.html?hakkoushaMei=&isinCode=&boshuuKubun=&hoshouKubun=&s=true)に掲載されているCP発行残高情報を収集する
  - 前職の金融機関で毎日パワークエリを使って取得するのが手間だった
  - 取得した後社内で利用するためにエクセル上で加工が必要であり手間だった
  - 上記のファイルサイズが膨らみ非常に重かった
  
### 使用技術  
- API・バッチ
  - Java
  - SpringBoot
  - Selenium
  - [Groovy/Spock(自動テスト)](https://github.com/k-matsumoto-214/hofuricp-be/tree/main/src/test/groovy/com/hofuri/cp)・・・カバレッジ90%以上(コンフィグクラス含まない)
  - [GitHubActions(自動テスト・デプロイ)](https://github.com/k-matsumoto-214/hofuricp-be/actions)
- [フロント](https://github.com/k-matsumoto-214/hofuricp-fe)
  - TypeScript
  - React.js
  - Next.js
  - [GitHubActions(自動デプロイ)](https://github.com/k-matsumoto-214/hofuricp-fe/actions)
- DB
  - MySQL

### できること
- CP日次残高の取得
  - 毎日1回、ほふりをクロールして更新されたCP残高をISINコード別に記録する
- CP残高の閲覧
  - 日次の総CP残高（全発行体の残高合計）をグラフで表示
  - 指定した期間における各発行体別の日次の発行残高を表で表示
  - 指定した期間における各発行体別の日次の発行残高表をExcelファイル形式でダウンロード

### 動作イメージ
![cp-all](https://github.com/k-matsumoto-214/hofuricp-fe/assets/91876695/1a4f8b0e-066b-4de9-9c8f-c6e3ce3ddb64)
![cp-list](https://github.com/k-matsumoto-214/hofuricp-fe/assets/91876695/15794428-97bd-4b88-a8fb-14575b84e737)



