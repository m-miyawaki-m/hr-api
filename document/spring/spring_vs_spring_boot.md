すばらしい質問です。
「Spring」と「Spring Boot」の違いを明確に理解すると、なぜ近年ほとんどのプロジェクトが “Spring Boot 前提” で構築されるのかが腑に落ちます。
以下で、**概念 → Spring の仕組み → Boot の登場背景 → 違いの整理** の順に体系的に解説します。

---

# 🧩 1. Spring とは（Spring Framework）

## ■ 概念

> **Spring Framework** は、Java でエンタープライズアプリケーション（業務システムなど）を構築するための**包括的なフレームワーク**です。

その中心思想は：

> 「アプリケーションの業務ロジックと、インフラ的な処理（DB 接続・DI・トランザクションなど）を分離して、疎結合にする」

---

## ■ Spring の主な役割（コア機能）

| 機能                                   | 内容                                               | 関連キーワード                   |
| -------------------------------------- | -------------------------------------------------- | -------------------------------- |
| **DI（Dependency Injection）**         | 依存関係を外部から注入し、クラス間の結合度を下げる | `@Autowired`, `@Component`       |
| **IoC コンテナ**                       | Bean（インスタンス）を生成・管理するコンテナ       | `ApplicationContext`             |
| **AOP（Aspect Oriented Programming）** | トランザクションやログなど共通処理を横断的に適用   | `@Transactional`                 |
| **トランザクション管理**               | DB 処理の一貫性を確保                              | `PlatformTransactionManager`     |
| **Spring MVC**                         | Web アプリ構築（Controller, View, Model）          | `@Controller`, `@RequestMapping` |
| **Spring JDBC / ORM 統合**             | MyBatis や Hibernate などとの統合                  | `JdbcTemplate`, `MyBatis`        |

---

## 🧱 Spring の基本構成イメージ

```
Spring Framework
 ├─ Core（DI / IoC）
 ├─ AOP
 ├─ Data Access（JDBC, ORM）
 ├─ Web（Spring MVC）
 ├─ Test
 └─ Security, Batch, Cloud, etc.
```

---

# ⚙️ 2. Spring の使い方（従来）

従来の Spring では、設定が XML ベースでした。
たとえば、`applicationContext.xml` に Bean 定義を書く必要がありました。

```xml
<beans>
  <bean id="employeeService" class="com.example.EmployeeService"/>
  <bean id="employeeRepository" class="com.example.EmployeeRepository"/>
</beans>
```

これを `ApplicationContext` が読み込んで、依存関係を注入します。

```java
ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
EmployeeService service = ctx.getBean(EmployeeService.class);
```

➡ 設定ファイルが増え、初期構築が煩雑になりがちでした。

---

# 🚀 3. Spring Boot とは

> **Spring Boot** は、Spring Framework をベースに、
> **「設定なしですぐ動く Spring アプリ」を作るための拡張フレームワーク** です。

つまり：

> 「Spring を簡単に使えるようにした便利セット」

---

## 🧠 Spring Boot の目的

| 問題点（Spring 時代）                | Boot の解決                      |
| ------------------------------------ | -------------------------------- |
| 設定 XML が多い                      | 自動設定（Auto Configuration）   |
| Tomcat などを手動で構築              | 組み込み Tomcat を自動起動       |
| 依存ライブラリのバージョン管理が煩雑 | `spring-boot-starter` が一括管理 |
| 起動・デプロイが面倒                 | `java -jar` で即実行可能         |
| 設定ファイルの複雑さ                 | `application.yml` に統一         |

---

## ✅ 代表的な特徴

| 機能                   | 内容                                                                         |
| ---------------------- | ---------------------------------------------------------------------------- |
| **Auto Configuration** | 必要な Bean を自動生成（DB 接続、MVC 設定など）                              |
| **Starter 依存関係**   | `spring-boot-starter-web`, `spring-boot-starter-data-jpa` などを指定するだけ |
| **組み込みサーバ**     | Tomcat, Jetty を内蔵しており、WAR 不要                                       |
| **Actuator**           | アプリの状態確認・監視用のエンドポイント提供                                 |
| **application.yml**    | 設定を統一的に管理（YAML or properties）                                     |

---

# 🧩 4. Spring Boot アプリの典型構成

```
src/
 ├─ main/java/com/example/hrapp
 │    ├─ HrAppApplication.java  ← 起動クラス
 │    ├─ controller/
 │    ├─ service/
 │    ├─ mapper/
 │    └─ model/
 └─ main/resources
      ├─ application.yml
      └─ mapper/*.xml
```

### 起動クラス例

```java
@SpringBootApplication
public class HrAppApplication {
    public static void main(String[] args) {
        SpringApplication.run(HrAppApplication.class, args);
    }
}
```

これで組み込み Tomcat が起動し、REST API を即利用可能です。

---

# ⚖️ 5. Spring と Spring Boot の違いまとめ

| 比較項目 | Spring Framework                       | Spring Boot                                  |
| -------- | -------------------------------------- | -------------------------------------------- |
| 定義方法 | XML / Java Config                      | 完全 Java ベース（`@SpringBootApplication`） |
| 起動方法 | 外部 Tomcat にデプロイ（WAR）          | `java -jar` で直接実行                       |
| 設定     | 手動設定（Bean 定義、DataSource など） | 自動設定（Auto Configuration）               |
| 依存関係 | 個別指定                               | Starter で一括指定                           |
| デプロイ | WAR ファイル                           | JAR ファイル                                 |
| 構築速度 | 遅い（手動設定多い）                   | 速い（最小構成で即動く）                     |
| 主な用途 | 大規模・既存システム統合               | 新規・マイクロサービス・REST API             |

---

# 💡 6. 実際のイメージ

### ■ Spring（従来）

> 「全てを自分で構築・設定する必要があるフレームワーク」

-   XML で Bean 定義
-   Tomcat 手動構築
-   JDBC 設定手動記述
-   バージョン競合が起きやすい

### ■ Spring Boot（現在主流）

> 「Spring の便利パッケージ化。設定を省略し、即実行できるテンプレート」

-   `@SpringBootApplication` 一発で起動
-   組み込みサーバ
-   設定最小限で DB・MVC 連携 OK
-   `application.yml` 一元管理

---

# 🧠 7. Spring Boot の位置づけ（比喩）

| イメージ        | 例え                                                   |
| --------------- | ------------------------------------------------------ |
| **Spring**      | フレームとパーツを自分で組み立てる「組み立て式マシン」 |
| **Spring Boot** | あらかじめ組み上げられた「完成済みテンプレート」       |

👉 Boot は“Spring のエンジンを内蔵した、スタートアップキット” です。

---

# 📘 まとめ

| 観点       | Spring Framework                    | Spring Boot                               |
| ---------- | ----------------------------------- | ----------------------------------------- |
| コンセプト | DI / AOP による疎結合フレームワーク | Spring を即使えるようにする拡張           |
| 設定       | XML 中心（今は Java Config）        | 自動設定（Convention over Configuration） |
| 起動       | 外部サーバ依存                      | 組み込みサーバ                            |
| 学習対象   | 仕組み・構成理解が必須              | 開発スピード優先                          |
| 推奨用途   | 既存大規模システム                  | 新規開発・マイクロサービス                |

---

もし次に進めるなら、
🔹「Spring Boot の自動設定（AutoConfiguration）の仕組み」
🔹「Starter の仕組みと内部で何が動いているか」
のどちらかを解説できます。
どちらを深掘りしますか？

とても良い選択です！
Spring Boot の「魔法のように動く仕組み」の正体は、まさにこの **AutoConfiguration（自動設定）機構** にあります。
ここを理解すると、Spring Boot の「便利さの本質」が見えてきます。

---

# 🧩 1. AutoConfiguration とは何か

## ■ 概念

> **AutoConfiguration（自動設定）** とは、
> Spring Boot がアプリ起動時に「必要な設定（Bean 登録）」を自動的に行う仕組みのことです。

Spring Boot は、

> 「あなたが何をしたいのか（Web? DB?）」をクラスパスから推測して、
> 必要な設定を自動的に適用します。

つまり、
**開発者が 1 行も設定を書かなくても動く理由がこれ。**

---

# 🧠 2. 背景：Spring 時代との比較

| 項目                  | 従来の Spring                                 | Spring Boot                          |
| --------------------- | --------------------------------------------- | ------------------------------------ |
| Web アプリ起動        | `web.xml` や `DispatcherServlet` を自分で設定 | 自動設定で Servlet 登録済み          |
| DataSource 設定       | XML に手動で書く                              | `spring.datasource.*` を設定するだけ |
| ViewResolver 設定     | XML に `InternalResourceViewResolver` を定義  | Boot が自動登録                      |
| Controller マッピング | XML or JavaConfig                             | `@RestController`で自動スキャン      |

---

# ⚙️ 3. 自動設定の実現メカニズム

## 起点：`@SpringBootApplication`

```java
@SpringBootApplication
public class HrAppApplication {
    public static void main(String[] args) {
        SpringApplication.run(HrAppApplication.class, args);
    }
}
```

このアノテーションは内部的に 3 つを含んでいます：

```java
@SpringBootConfiguration
@EnableAutoConfiguration   // ← ここがAutoConfigurationの核
@ComponentScan
```

---

# 🧱 4. `@EnableAutoConfiguration` の仕組み

`@EnableAutoConfiguration` は、

> **クラスパス上の依存関係を調べて、自動で設定クラスを読み込む** 仕組みです。

---

## 📦 自動設定の登録先

Spring Boot の jar ファイルの中には次のファイルがあります：

```
META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports
```

このファイルに、

> 「どの自動設定クラスをロードするか」
> が全てリストされています。

例（抜粋）：

```
org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration
org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration
org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration
...
```

➡ Boot はここを読み取り、クラスパス状況を見て **必要な AutoConfiguration だけを有効化** します。

---

# 🔍 5. 自動設定クラスの中身

例：`DataSourceAutoConfiguration`

```java
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(DataSource.class)
@EnableConfigurationProperties(DataSourceProperties.class)
public class DataSourceAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public DataSource dataSource(DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().build();
    }
}
```

ここでのポイントは 2 つ：

| アノテーション              | 役割                                         |
| --------------------------- | -------------------------------------------- |
| `@ConditionalOnClass`       | クラスパスに `DataSource` が存在すれば有効化 |
| `@ConditionalOnMissingBean` | 既に自前定義がある場合はスキップ             |

つまり、

> 「必要なら設定を適用するが、手動設定があるなら尊重する」
> という **賢い挙動** になっています。

---

# 🧠 6. Conditional 系アノテーションの種類

| アノテーション                    | 意味                                     |
| --------------------------------- | ---------------------------------------- |
| `@ConditionalOnClass`             | クラスがクラスパス上にある場合           |
| `@ConditionalOnMissingClass`      | クラスが存在しない場合                   |
| `@ConditionalOnBean`              | Bean が定義済みの場合                    |
| `@ConditionalOnMissingBean`       | Bean が未定義の場合                      |
| `@ConditionalOnProperty`          | application.yml の設定値が特定条件のとき |
| `@ConditionalOnWebApplication`    | Web アプリケーションの場合               |
| `@ConditionalOnNotWebApplication` | Web でない場合                           |
| `@ConditionalOnResource`          | 特定リソースファイルが存在する場合       |

Boot はこれらの条件を組み合わせて、
**「自動設定を適用するか否か」** を判定しています。

---

# 🧩 7. 自動設定の優先順位

Spring Boot は設定の優先順位を明確に定義しています：

| 優先度 | 設定ソース                       | 説明                          |
| ------ | -------------------------------- | ----------------------------- |
| 🥇 1   | **コード内の Bean 定義**         | 開発者が明示的に定義した Bean |
| 🥈 2   | **application.yml / properties** | 設定ファイルによる上書き      |
| 🥉 3   | **AutoConfiguration**            | Boot のデフォルト設定         |

つまり、

> 開発者が書いた設定が常に優先される。

→ Boot は「自動設定を強制しないフレームワーク」です。

---

# ⚡ 8. 自動設定の有効／無効の確認

## ✅ 有効な設定を確認する

```bash
$ mvn spring-boot:run --debug
```

起動ログに：

```
=========================
AUTO-CONFIGURATION REPORT
=========================

Positive matches:
-----------------
   DataSourceAutoConfiguration matched
   WebMvcAutoConfiguration matched
   JacksonAutoConfiguration matched

Negative matches:
-----------------
   MongoAutoConfiguration (not found)
   JpaAutoConfiguration (not found)
```

➡ Boot がどの設定を有効にしたか一覧で確認できます。

---

# 🧩 9. 自動設定の上書き・無効化

## ✅ 一部の自動設定を無効化したい場合

```java
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class HrAppApplication {
    ...
}
```

または、複数無効化：

```java
@SpringBootApplication(exclude = {
    DataSourceAutoConfiguration.class,
    SecurityAutoConfiguration.class
})
```

---

# 💡 10. application.yml との連携

たとえば、DataSource の自動設定は次の設定と連携します：

```yaml
spring:
    datasource:
        url: jdbc:oracle:thin:@localhost:1521/XEPDB1
        username: hr
        password: hr
```

これを見て、Boot が自動的に `DataSource` Bean を生成します。
つまり、

> yml に値を書くだけで、裏で `DataSourceAutoConfiguration` が動く。

---

# 📘 まとめ

| 観点       | 内容                                                                        |
| ---------- | --------------------------------------------------------------------------- |
| 定義       | Spring Boot がアプリ起動時に必要な Bean を自動登録する仕組み                |
| 実装       | `@EnableAutoConfiguration` + `META-INF/spring/...AutoConfiguration.imports` |
| 条件分岐   | `@ConditionalOn...` 系アノテーションで判定                                  |
| 優先順位   | 手動定義 > yml 設定 > 自動設定                                              |
| 利点       | XML 不要・初期設定最小化・必要な機能のみ自動適用                            |
| 無効化方法 | `@SpringBootApplication(exclude = {...})`                                   |

---

もし次のステップとして理解を深めるなら：

-   🔹 **「Spring Boot Starter の仕組み（依存と自動設定の連動）」**
-   🔹 **「AutoConfiguration を自作する方法（独自 Starter 作成）」**

どちらかを選ぶと、「フレームワークを使う側 → 作る側」に一歩進めます。
どちらを深掘りしてみますか？
