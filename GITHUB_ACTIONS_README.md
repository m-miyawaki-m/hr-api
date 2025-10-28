# GitHub Actions ワークフロー

このプロジェクトでは、GitHub Actions を使用して CI/CD パイプラインを自動化しています。

## 🔄 ワークフロー一覧

### 1. CI - Test and Build (`ci.yml`)

**実行タイミング:**

-   `main`または`develop`ブランチへの push 時
-   `main`または`develop`ブランチへのプルリクエスト作成時
-   手動実行（workflow_dispatch）

**実行内容:**

#### test ジョブ:

-   ✅ Java 17 のセットアップ
-   ✅ Gradle キャッシュの有効化
-   ✅ JUnit テストの実行
-   ✅ テストレポートのアップロード
-   ✅ JaCoCo テストカバレッジレポートの生成
-   ✅ テスト結果の PR コメント投稿
-   ✅ Gradle ビルドの実行
-   ✅ ビルド成果物のアップロード

#### code-quality ジョブ:

-   ✅ コードフォーマットチェック（Spotless）
-   ✅ 静的解析レポートのアップロード

**成果物:**

-   `test-results`: テスト結果（30 日保持）
-   `coverage-reports`: カバレッジレポート（30 日保持）
-   `build-artifacts`: ビルド成果物（7 日保持）
-   `code-quality-reports`: 静的解析レポート（30 日保持）

### 2. Security Scan (`security.yml`)

**実行タイミング:**

-   毎週月曜日の午前 9 時（JST）
-   `main`ブランチへの push 時
-   手動実行（workflow_dispatch）

**実行内容:**

-   🔒 OWASP Dependency Check による依存関係脆弱性スキャン
-   🔒 GitHub Security Advisories との照合
-   🔒 セキュリティレポートのアップロード

**成果物:**

-   `dependency-check-reports`: 脆弱性スキャンレポート（30 日保持）

## 📊 レポートの確認方法

### テストレポート

1. GitHub Actions のワークフロータブで CI 実行結果を確認
2. `test-results`成果物をダウンロードして`index.html`を開く
3. プルリクエストのコメントでテスト結果概要を確認

### テストカバレッジレポート

1. `coverage-reports`成果物をダウンロード
2. `html/index.html`を開いてカバレッジ詳細を確認

### セキュリティレポート

1. `dependency-check-reports`成果物をダウンロード
2. `dependency-check-report.html`を開いて脆弱性詳細を確認
3. GitHub Security Advisories タブで脆弱性アラートを確認

## ⚙️ 設定のカスタマイズ

### テストカバレッジの基準変更

`build.gradle`の以下の部分で最小カバレッジを変更できます：

```gradle
jacocoTestCoverageVerification {
    violationRules {
        rule {
            limit {
                minimum = 0.70 // ← この値を変更
            }
        }
    }
}
```

### セキュリティスキャンの基準変更

`build.gradle`の以下の部分で CVSS 基準を変更できます：

```gradle
dependencyCheck {
    failBuildOnCVSS = 7.0 // ← この値を変更（0.0-10.0）
}
```

### 脆弱性の抑制

誤検知される脆弱性は`dependency-check-suppressions.xml`で抑制できます：

```xml
<suppress>
    <notes><![CDATA[抑制理由をここに記載]]></notes>
    <packageUrl regex="true">^pkg:maven/group/artifact@.*$</packageUrl>
    <cve>CVE-YYYY-NNNNN</cve>
</suppress>
```

## 🚀 手動実行方法

1. GitHub リポジトリの Actions タブを開く
2. 実行したいワークフローを選択
3. `Run workflow`ボタンをクリック
4. 必要に応じてパラメータを入力して実行

## 🔧 トラブルシューティング

### よくある問題と対処法

#### テストが失敗する場合

1. ローカルで`./gradlew test`を実行して再現確認
2. テストレポートで失敗原因を確認
3. 依存関係やテストデータの問題を確認

#### ビルドが失敗する場合

1. ローカルで`./gradlew build`を実行して再現確認
2. Java バージョンの互換性を確認
3. 依存関係の競合を確認

#### セキュリティスキャンでエラーが出る場合

1. 脆弱性が実際に影響するかを確認
2. 必要に応じて依存関係を更新
3. 誤検知の場合は抑制ファイルに追加

## 📝 プルリクエストテンプレート

プルリクエスト作成時は、`.github/pull_request_template.md`のチェックリストを確認してください：

-   [ ] 新しい機能のテストケースを追加
-   [ ] 既存のテストが全て通ることを確認
-   [ ] コードフォーマット（Spotless）をチェック
-   [ ] 適切な JavaDoc コメントを追加
-   [ ] セキュリティ上の問題がないことを確認

## 🎯 ベストプラクティス

1. **コミット前の確認**

    ```bash
    ./gradlew test spotlessCheck
    ```

2. **プッシュ前の確認**

    ```bash
    ./gradlew build jacocoTestReport
    ```

3. **セキュリティチェック**

    ```bash
    ./gradlew dependencyCheckAnalyze
    ```

4. **定期的なメンテナンス**
    - 依存関係の更新
    - セキュリティアラートの確認
    - テストカバレッジの改善
