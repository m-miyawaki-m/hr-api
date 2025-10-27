とても良い質問です。
Spring や ORM（JPA など）と並べて理解すると、**MyBatis は「SQL 主導型の O/R マッピングフレームワーク」** であることがよく見えてきます。
ここでは、概念 → 特徴 → 基本構造 → 主な文法 の順で体系的に整理します。

---

# 🧩 MyBatis とは ― 概念と位置づけ

## ■ 1. 概念

> **MyBatis（マイバティス）** は、Java オブジェクトとデータベースの間を橋渡しする **O/R マッピング（Object-Relational Mapping）フレームワーク** です。
> ただし、**自動生成ではなく、SQL を自分で書く** ことを前提にしています。

つまり：

-   **JPA**：SQL をフレームワークが自動生成（宣言的・抽象的）
-   **MyBatis**：SQL を開発者が定義（明示的・制御的）

👉 **SQL を細かく制御したい人向けの ORM**

---

## ■ 2. 特徴まとめ

| 特徴              | 説明                                             |
| ----------------- | ------------------------------------------------ |
| SQL 主導          | SQL を自分で書く（チューニング・可視化しやすい） |
| 軽量              | XML or アノテーションで簡単に設定できる          |
| 柔軟なマッピング  | SQL 結果を任意の DTO や Map に変換可能           |
| Spring 連携が容易 | `mybatis-spring-boot-starter` で自動設定可       |
| JPA より低レイヤ  | トランザクションやクエリ制御を細かく指定可       |

---

# 🧱 MyBatis の基本構造

```text
プロジェクト構成
├─ model/Employee.java        ← データ転送オブジェクト（DTO）
├─ mapper/EmployeeMapper.java ← Mapperインターフェース
└─ resources/mapper/
       └─ EmployeeMapper.xml  ← SQL定義ファイル
```

MyBatis はこの 3 つを関連づけて動きます。

1. **Mapper インターフェース**：メソッド定義（呼び出し口）
2. **Mapper XML**：SQL 文を定義
3. **Model クラス（DTO）**：結果を格納する入れ物

---

# ⚙️ MyBatis の基本的な使い方

## 1️⃣ SQL マッピングの仕組み

### Java 側（インターフェース）

```java
@Mapper
public interface EmployeeMapper {
    Employee findById(int id);
}
```

### XML 側

```xml
<select id="findById" resultType="Employee" parameterType="int">
    SELECT * FROM EMPLOYEES WHERE EMPLOYEE_ID = #{id}
</select>
```

MyBatis はこの `id="findById"` とメソッド名を対応させ、
実行時に SQL を呼び出します。

---

# ✍️ 主な文法まとめ（XML）

## ■ 1. 基本文法

| 要素        | 用途              | 例                               |
| ----------- | ----------------- | -------------------------------- |
| `<select>`  | データ取得        | `SELECT * FROM ...`              |
| `<insert>`  | データ追加        | `INSERT INTO ...`                |
| `<update>`  | データ更新        | `UPDATE ...`                     |
| `<delete>`  | データ削除        | `DELETE FROM ...`                |
| `<sql>`     | 共通 SQL 定義     | 再利用可能な SQL 断片を定義      |
| `<include>` | `<sql>`を呼び出す | `<include refid="baseColumns"/>` |

---

## ■ 2. プレースホルダ

| 記法       | 用途         | 説明                                                           |
| ---------- | ------------ | -------------------------------------------------------------- |
| `#{param}` | バインド変数 | PreparedStatement に安全に渡す（SQL インジェクション対策済み） |
| `${param}` | 文字列展開   | SQL の一部として埋め込む（危険）                               |

例：

```xml
WHERE EMPLOYEE_ID = #{id}    ← 安全（推奨）
ORDER BY ${columnName}       ← SQL構文上必要な時のみ使用
```

---

## ■ 3. 条件分岐（if, choose）

```xml
<select id="findDynamic" resultType="Employee">
  SELECT * FROM EMPLOYEES
  <where>
    <if test="firstName != null">
      AND FIRST_NAME = #{firstName}
    </if>
    <if test="departmentId != null">
      AND DEPARTMENT_ID = #{departmentId}
    </if>
  </where>
</select>
```

👉 Java の `if` と同じ感覚で条件を制御可能。

---

## ■ 4. 繰り返し（foreach）

```xml
<select id="findByIds" resultType="Employee">
  SELECT * FROM EMPLOYEES WHERE EMPLOYEE_ID IN
  <foreach item="id" collection="ids" open="(" separator="," close=")">
    #{id}
  </foreach>
</select>
```

呼び出し側：

```java
List<Employee> list = mapper.findByIds(List.of(100, 101, 102));
```

---

## ■ 5. 結果マッピング（resultMap）

列名とプロパティ名が一致しない場合：

```xml
<resultMap id="EmployeeMap" type="Employee">
    <id property="employeeId" column="EMPLOYEE_ID" />
    <result property="firstName" column="FIRST_NAME" />
    <result property="lastName" column="LAST_NAME" />
</resultMap>

<select id="findAll" resultMap="EmployeeMap">
  SELECT * FROM EMPLOYEES
</select>
```

---

## ■ 6. SQL 再利用（include / sql）

```xml
<sql id="baseColumns">
  EMPLOYEE_ID, FIRST_NAME, LAST_NAME, EMAIL
</sql>

<select id="findAll" resultType="Employee">
  SELECT <include refid="baseColumns" /> FROM EMPLOYEES
</select>
```

---

## ■ 7. 自動採番取得（insert + selectKey）

```xml
<insert id="insertEmployee" parameterType="Employee" useGeneratedKeys="true" keyProperty="employeeId">
  INSERT INTO EMPLOYEES (FIRST_NAME, LAST_NAME, EMAIL)
  VALUES (#{firstName}, #{lastName}, #{email})
</insert>
```

または Oracle の場合：

```xml
<insert id="insertEmployee" parameterType="Employee">
  <selectKey keyProperty="employeeId" order="BEFORE" resultType="int">
    SELECT EMPLOYEES_SEQ.NEXTVAL FROM DUAL
  </selectKey>
  INSERT INTO EMPLOYEES (EMPLOYEE_ID, FIRST_NAME, LAST_NAME)
  VALUES (#{employeeId}, #{firstName}, #{lastName})
</insert>
```

---

# 🔧 MyBatis の思想

> “SQL はアプリケーションの一部である”
> ― MyBatis 開発者の理念

JPA のように SQL を隠すのではなく、
**SQL を「見える化」して管理・最適化する** という思想に基づいています。

-   SQL チューニングが可能
-   複雑な JOIN やサブクエリも柔軟に扱える
-   Mapper XML を一種の「SQL 辞書」として管理できる

---

# 📘 まとめ

| 項目               | MyBatis の位置づけ                                                                 |
| ------------------ | ---------------------------------------------------------------------------------- |
| フレームワーク種別 | SQL マッピングフレームワーク                                                       |
| 開発スタイル       | SQL 明示記述型                                                                     |
| メリット           | 柔軟・軽量・制御性が高い                                                           |
| デメリット         | SQL を自分で管理するコスト                                                         |
| Spring 連携        | `mybatis-spring-boot-starter` で容易                                               |
| 主なタグ           | `<select>`, `<insert>`, `<update>`, `<delete>`, `<if>`, `<foreach>`, `<resultMap>` |

---

必要であれば次の段階として：

-   ✅ `MyBatis Generator` による自動コード生成の仕組み
-   ✅ Spring + MyBatis のトランザクション制御
-   ✅ JPA（Hibernate）との比較表

を体系的にまとめることもできます。
どれを次に深掘りしたいですか？

とても良いテーマです。
Spring と MyBatis を組み合わせる場合、**トランザクション（Transaction）制御** は非常に重要な設計要素です。
ここでは、

> 🔹「概念」 → 🔹「Spring での制御仕組み」 → 🔹「MyBatis との連携」 → 🔹「実装例」
> の順に、体系的に解説します。

---

# 🧩 1. トランザクションとは

**トランザクション（Transaction）** とは、
「一連の DB 処理をひとまとめにして、すべて成功 or すべて失敗にする」仕組みです。

| 処理         | 意味                 |
| ------------ | -------------------- |
| **BEGIN**    | トランザクション開始 |
| **COMMIT**   | すべて成功（確定）   |
| **ROLLBACK** | 途中で失敗（全取消） |

### 例

```text
① 残高引き落とし
② 振込先への入金
```

→ ① で成功、② で失敗したら、① も取り消す（ROLLBACK）。

---

# ⚙️ 2. Spring によるトランザクション制御の仕組み

Spring は「宣言的トランザクション管理」をサポートしています。
つまり、**@Transactional** アノテーションを付けるだけで、
内部的に `DataSourceTransactionManager` がトランザクションを制御します。

---

## 🔸 宣言的トランザクションの流れ

```text
Controller
   ↓
Service（@Transactional）
   ↓
Mapper（MyBatis）
   ↓
Database
```

Service 層のメソッドに `@Transactional` を付けると、

-   メソッド開始時に `BEGIN`
-   成功時に `COMMIT`
-   例外発生時に `ROLLBACK`
    が自動で行われます。

---

# 🧱 3. Spring + MyBatis の構成

## （1）依存関係（pom.xml）

```xml
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>3.0.3</version>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-jdbc</artifactId>
</dependency>

<dependency>
    <groupId>com.oracle.database.jdbc</groupId>
    <artifactId>ojdbc11</artifactId>
    <version>23.3.0.23.09</version>
</dependency>
```

---

## （2）設定ファイル（application.yml）

```yaml
spring:
    datasource:
        driver-class-name: oracle.jdbc.OracleDriver
        url: jdbc:oracle:thin:@localhost:1521/XEPDB1
        username: hr
        password: hr
    mybatis:
        mapper-locations: classpath:mapper/*.xml
        type-aliases-package: com.example.hrapp.model
```

Spring Boot は自動的に `DataSourceTransactionManager` を作成します。

---

# 🧩 4. トランザクション制御の書き方

## ✅ 基本形

```java
@Service
public class EmployeeService {

    private final EmployeeMapper employeeMapper;
    private final DepartmentMapper departmentMapper;

    public EmployeeService(EmployeeMapper employeeMapper, DepartmentMapper departmentMapper) {
        this.employeeMapper = employeeMapper;
        this.departmentMapper = departmentMapper;
    }

    @Transactional  // ← これだけでOK
    public void transferEmployee(int employeeId, int newDeptId) {
        // 1. 部署更新
        employeeMapper.updateDepartment(employeeId, newDeptId);

        // 2. ログ登録（例外が出たらrollbackされる）
        departmentMapper.insertHistory(employeeId, newDeptId);

        // 3. 例外テスト
        if (newDeptId == 0) {
            throw new RuntimeException("Invalid department");
        }
    }
}
```

結果：

-   `newDeptId == 0` の場合 → ①② ともにロールバックされる
-   正常時 → 両方コミットされる

---

# 🔍 5. `@Transactional` の詳細属性

| 属性名        | 内容                             | 例                              |
| ------------- | -------------------------------- | ------------------------------- |
| `propagation` | 伝播（既存 Tx がある場合の動作） | `REQUIRED`（デフォルト）        |
| `isolation`   | 分離レベル                       | `Isolation.READ_COMMITTED`      |
| `timeout`     | タイムアウト秒                   | `timeout = 10`                  |
| `readOnly`    | 読み取り専用                     | `readOnly = true`               |
| `rollbackFor` | ロールバック対象例外             | `rollbackFor = Exception.class` |

### 例

```java
@Transactional(
    propagation = Propagation.REQUIRED,
    isolation = Isolation.READ_COMMITTED,
    rollbackFor = Exception.class
)
```

---

# 🔁 6. 伝播属性（Propagation）の理解

| 値              | 意味                                             |
| --------------- | ------------------------------------------------ |
| `REQUIRED`      | 既存 Tx があれば参加、なければ新規（デフォルト） |
| `REQUIRES_NEW`  | 常に新しい Tx を開始（親 Tx とは独立）           |
| `SUPPORTS`      | Tx があれば参加、なくても実行                    |
| `MANDATORY`     | Tx 必須（なければ例外）                          |
| `NOT_SUPPORTED` | Tx を中断して非トランザクションで実行            |
| `NEVER`         | Tx 中なら例外発生                                |
| `NESTED`        | ネストトランザクション（セーブポイント使用）     |

---

# 🧠 7. 注意点（よくある落とし穴）

| ケース                             | 原因                                                   | 対策                                           |
| ---------------------------------- | ------------------------------------------------------ | ---------------------------------------------- |
| `@Transactional` が効かない        | 同クラス内で自己呼び出し                               | → 別クラスから呼び出す or AOP proxy 経由にする |
| チェック例外でロールバックされない | `@Transactional` は `RuntimeException` でのみ rollback | → `rollbackFor = Exception.class` を指定       |
| `readOnly=true` なのに更新が通る   | JDBC ドライバ依存                                      | → 書き込み禁止目的ならアプリ設計で制御         |

---

# 🔧 8. XML ベースでの設定（旧式 Spring の場合）

Spring Boot を使わない場合は、`applicationContext.xml` に以下のように設定します。

```xml
<bean id="txManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
  <property name="dataSource" ref="dataSource"/>
</bean>

<tx:annotation-driven transaction-manager="txManager"/>
```

→ これにより `@Transactional` が有効化されます。

---

# 📘 まとめ

| 項目             | 内容                                                |
| ---------------- | --------------------------------------------------- |
| 管理方法         | Spring の AOP による「宣言的トランザクション」      |
| 実装箇所         | 通常は Service 層に `@Transactional` を付与         |
| ロールバック条件 | `RuntimeException` または `rollbackFor` 指定時      |
| MyBatis 連携     | 自動的に同一 `SqlSession` が使用され、Tx 共有される |
| 推奨構成         | Controller → Service（@Transactional） → Mapper     |

---

もし希望があれば次に、
🧩 **「複数データソースを扱う場合のトランザクション制御（XA／分散 Tx 対応）」**
も解説できます。続けますか？
