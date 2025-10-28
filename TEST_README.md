# JUnit テストガイド

このプロジェクトには、アプリケーションの各レイヤーに対応する包括的な JUnit テストコードが用意されています。

## テスト構成

### 1. コントローラーテスト (`EmployeeControllerTest`)

-   **場所**: `src/test/java/com/example/hrapp/controller/EmployeeControllerTest.java`
-   **目的**: REST API エンドポイントのテスト
-   **特徴**:
    -   `@WebMvcTest`を使用して Web レイヤーのみをテスト
    -   MockMvc を使った HTTP リクエスト/レスポンスのテスト
    -   サービス層をモック化してコントローラーのロジックのみを検証

**テストケース**:

-   全従業員取得 API（正常系・空のリスト）
-   ID 指定従業員取得 API（正常系・該当なし）
-   名前検索 API（正常系・該当なし・複数件ヒット）

### 2. サービステスト (`EmployeeServiceTest`)

-   **場所**: `src/test/java/com/example/hrapp/service/EmployeeServiceTest.java`
-   **目的**: ビジネスロジックのテスト
-   **特徴**:
    -   `@ExtendWith(MockitoExtension.class)`を使用
    -   Mapper をモック化してサービス層のロジックのみを検証
    -   AssertJ を使った豊富なアサーション

**テストケース**:

-   全従業員取得（正常系・空のリスト）
-   ID 指定従業員取得（正常系・該当なし）
-   名前検索（単一ヒット・複数ヒット・該当なし・空文字列・部分一致）

### 3. モデルテスト (`EmployeeTest`)

-   **場所**: `src/test/java/com/example/hrapp/model/EmployeeTest.java`
-   **目的**: エンティティクラスの基本動作のテスト
-   **特徴**:
    -   Lombok で生成される getter/setter の動作確認
    -   equals/hashCode/toString の動作確認
    -   null 値の適切な処理の確認

**テストケース**:

-   各プロパティの設定・取得
-   全プロパティの一括設定
-   null 値の設定
-   equals/hashCode の動作
-   toString の動作

### 4. 統合テスト (`HrApiApplicationIntegrationTest`)

-   **場所**: `src/test/java/com/example/hrapp/HrApiApplicationIntegrationTest.java`
-   **目的**: アプリケーション全体の統合テスト
-   **特徴**:
    -   `@SpringBootTest`を使用して Spring Context の起動を確認
    -   テスト用プロファイル（`application-test.yml`）を使用

## テスト実行方法

### 全テスト実行

```bash
gradle test
```

### 特定のテストクラス実行

```bash
# コントローラーテストのみ
gradle test --tests "com.example.hrapp.controller.EmployeeControllerTest"

# サービステストのみ
gradle test --tests "com.example.hrapp.service.EmployeeServiceTest"

# モデルテストのみ
gradle test --tests "com.example.hrapp.model.EmployeeTest"

# 統合テストのみ
gradle test --tests "com.example.hrapp.HrApiApplicationIntegrationTest"
```

### 特定のテストメソッド実行

```bash
gradle test --tests "com.example.hrapp.controller.EmployeeControllerTest.testGetAll_Success"
```

## テスト設定

### テスト用設定ファイル

-   `src/test/resources/application-test.yml`: テスト環境用設定
-   `src/test/resources/schema.sql`: テスト用テーブル定義
-   `src/test/resources/data.sql`: テスト用初期データ

### 使用技術・ライブラリ

-   **JUnit 5**: テストフレームワーク
-   **Mockito**: モック作成・管理
-   **AssertJ**: 豊富なアサーション機能
-   **Spring Boot Test**: Spring Boot アプリケーションテスト支援
-   **H2 Database**: テスト用インメモリデータベース

## テストレポート確認

テスト実行後、以下の場所でレポートを確認できます：

```
build/reports/tests/test/index.html
```

## 追加のテストケース作成

新しい機能を追加した際は、対応するテストケースも追加してください：

1. **コントローラー**: 新しいエンドポイントの正常系・異常系テスト
2. **サービス**: 新しいビジネスロジックのテスト
3. **モデル**: 新しいエンティティのプロパティテスト
4. **統合テスト**: 新機能を含むエンドツーエンドテスト

## ベストプラクティス

1. **AAA パターン**を使用：Arrange（準備）→ Act（実行）→ Assert（検証）
2. **意味のあるテストメソッド名**を使用
3. **@DisplayName**で日本語での説明を追加
4. **適切なモック化**でテストを独立させる
5. **エッジケース**も含めて網羅的にテスト
6. **テストデータ**は各テストで独立して用意する
