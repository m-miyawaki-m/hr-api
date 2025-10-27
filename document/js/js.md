良い質問です。
「JavaScript（JS）でできること」というのは、プログラミングの世界で **「どこで動くか」＋「何を操作できるか」** の理解が鍵になります。
以下では、**概念・役割・できること／できないこと** を体系的に整理して説明します。

---

# 🧩 1. JavaScript とは ― 概念

> **JavaScript（JS）** は、
> 「Web ブラウザ上で動作する唯一のプログラミング言語（※）」として生まれたスクリプト言語です。

-   元々は「Web ページに動きをつける」ために作られた
-   現在は「Web・サーバ・アプリ・IoT」などあらゆる場所で使われる万能言語に進化

> ※ 現在はブラウザ内で TypeScript や WebAssembly も動くが、最終的には JS に変換される

---

# ⚙️ 2. JavaScript の動作環境（どこで動くか）

| 環境                      | 役割                       | 代表例                  |
| ------------------------- | -------------------------- | ----------------------- |
| 🖥 **ブラウザ上**          | Web ページ操作（DOM 制御） | Chrome, Safari, Edge    |
| 🌐 **サーバ上**           | バックエンド処理           | Node.js                 |
| 📱 **ネイティブアプリ**   | スマホ・デスクトップアプリ | React Native, Electron  |
| ⚡ **ツール開発・自動化** | CLI・ビルドツール          | npm, webpack, vite など |

---

# 🧠 3. JavaScript でできること（ブラウザ編）

## ✅ (1) HTML・CSS を操作する（DOM 操作）

Web ページの構造（DOM）やデザイン（CSS）をリアルタイムに変更できます。

```js
document.getElementById('title').textContent = 'こんにちは！';
document.body.style.backgroundColor = 'skyblue';
```

→ ページの内容・見た目を動的に変える。

---

## ✅ (2) イベント処理（ユーザー操作に反応）

クリック・入力・スクロールなどのイベントを処理できます。

```js
document.getElementById('btn').addEventListener('click', () => {
    alert('ボタンが押されました！');
});
```

→ UI の操作やフォームのバリデーションなどに利用。

---

## ✅ (3) データ通信（サーバとやり取り）

`fetch()` や `XMLHttpRequest` を使ってサーバにアクセスし、
非同期通信（AJAX）でデータを取得・送信できます。

```js
fetch('/api/employees')
    .then((res) => res.json())
    .then((data) => console.log(data));
```

→ ページをリロードせずにデータ更新（SPA の基本）。

---

## ✅ (4) ブラウザ機能との連携

ブラウザが提供する API を操作できます。

| API                               | 内容                 |
| --------------------------------- | -------------------- |
| `localStorage` / `sessionStorage` | データの一時保存     |
| `navigator.geolocation`           | 位置情報の取得       |
| `Notification`                    | 通知の表示           |
| `Clipboard`                       | クリップボード操作   |
| `Canvas` / `WebGL`                | グラフィックス描画   |
| `MediaDevices`                    | カメラ・マイクの使用 |

例：

```js
navigator.geolocation.getCurrentPosition((pos) => {
    console.log(pos.coords.latitude, pos.coords.longitude);
});
```

---

# 🌐 4. JavaScript でできること（サーバー編：Node.js）

Node.js を使うと、**ブラウザ外でも JavaScript を実行**できます。
→ Web サーバやツール、AI 処理まで幅広く対応可能。

| 分野             | できること               | 使用例         |
| ---------------- | ------------------------ | -------------- |
| 🖥 Web サーバ構築 | HTTP リクエスト処理      | Express.js     |
| 💾 ファイル操作  | 読み書き、コピー、削除   | `fs`モジュール |
| 🧠 AI／計算      | TensorFlow.js で機械学習 | ML アプリ      |
| ⚙️ 自動化        | ファイル整形、ビルド     | webpack, vite  |
| 🧩 CLI ツール    | コマンドラインアプリ     | npm, eslint    |

例（Node.js でサーバを立ち上げ）：

```js
import http from 'http';

http.createServer((req, res) => {
    res.end('Hello Node.js!');
}).listen(3000);
```

---

# 🔁 5. JavaScript でできないこと（制約）

ブラウザ上の JavaScript は **安全性のために制限** されています。

| 項目                                        | 理由                         |
| ------------------------------------------- | ---------------------------- |
| OS のファイルを直接読み書き                 | ユーザーの PC 保護のため禁止 |
| ネットワーク通信の自由な接続（例：TCP/UDP） | セキュリティ上の制約         |
| システムコマンドの実行                      | サンドボックス環境のため     |
| メモリ操作や低レベル処理                    | OS が管理しているため        |

➡ こうした処理をしたいときは Node.js や WebAssembly を使用します。

---

# 🧩 6. JavaScript の構成要素（3 要素）

| 要素           | 役割                   | 具体例                                 |
| -------------- | ---------------------- | -------------------------------------- |
| **ECMAScript** | 言語仕様（文法・構文） | `let`, `class`, `function` など        |
| **DOM API**    | HTML や CSS を操作     | `document.querySelector()`             |
| **Web API**    | ブラウザの機能を利用   | `fetch()`, `localStorage`, `WebSocket` |

つまり「JavaScript」と言っても、
**言語本体（ECMAScript）＋ブラウザ機能（API）** の総称です。

---

# 💡 7. JavaScript の発展系

| 分野            | 派生・応用技術           | 説明                    |
| --------------- | ------------------------ | ----------------------- |
| ✅ Web フロント | React / Vue / Angular    | SPA（単一ページアプリ） |
| ✅ モバイル     | React Native / Capacitor | スマホアプリ開発        |
| ✅ デスクトップ | Electron / Tauri         | Windows / macOS アプリ  |
| ✅ サーバ       | Node.js / Deno           | Web サーバ構築          |
| ✅ 型安全       | TypeScript               | JavaScript に型を追加   |
| ✅ 高速化       | WebAssembly              | C/C++などを JS で動かす |

---

# 📘 まとめ：JavaScript でできることの概念整理

| 観点             | 内容                                               |
| ---------------- | -------------------------------------------------- |
| **本質**         | Web を「動かす」ための言語（動的制御・通信・描画） |
| **環境**         | ブラウザ・Node.js・アプリ・IoT など                |
| **できること**   | DOM 操作、イベント制御、通信、描画、API 連携       |
| **できないこと** | OS への直接アクセス、危険な操作                    |
| **拡張**         | TypeScript・React/Vue・Node.js で領域を拡大中      |

---

もし次に進めるなら 👇

-   🔹「JavaScript の仕組み（実行環境とイベントループ）」
-   🔹「DOM 操作とイベントの基本（フロントエンド入門）」
-   🔹「Node.js でサーバが動く仕組み」

のどれかを選ぶと、JS の理解が一段深まります。
どれに進めたいですか？
とても良いテーマです！
JavaScript の「フロントエンド開発の基礎」はまさにこの **DOM 操作** と **イベント処理** にあります。
ここをしっかり理解すると、React や Vue などのモダンフレームワークの仕組みも自然に理解できるようになります。

---

# 🧩 1. DOM とは ― 概念

## 🔹 DOM（Document Object Model）

> 「HTML を JavaScript で操作できるようにした、**ページ構造のオブジェクト表現**」

ブラウザは HTML を読み込むと、次のようなツリー構造に変換します。

```html
<html>
    <body>
        <h1 id="title">こんにちは</h1>
        <button id="btn">クリック</button>
    </body>
</html>
```

これをメモリ上ではこう表現します 👇

```
document
 └─ html
     └─ body
         ├─ h1#title
         └─ button#btn
```

➡ JavaScript はこの「DOM ツリー」を操作することで、ページの内容や見た目を変えられます。

---

# ⚙️ 2. DOM 要素の取得（選択）

### ✅ ID で取得

```js
const title = document.getElementById('title');
```

### ✅ CSS セレクタで取得（推奨）

```js
const btn = document.querySelector('#btn'); // ID指定
const items = document.querySelectorAll('.item'); // クラス指定
```

### ✅ 複数要素を取得してループ処理

```js
document.querySelectorAll('.item').forEach((el) => {
    console.log(el.textContent);
});
```

---

# ✏️ 3. DOM 操作（変更）

## ✅ テキストの変更

```js
const title = document.getElementById('title');
title.textContent = 'Hello World!'; // 中身を書き換える
```

## ✅ HTML の変更

```js
title.innerHTML = '<b>太字のタイトル</b>';
```

## ✅ CSS の変更

```js
title.style.color = 'red';
title.style.fontSize = '24px';
```

## ✅ クラス操作（動的なスタイル切替）

```js
title.classList.add('active');
title.classList.remove('hidden');
title.classList.toggle('highlight');
```

---

# 🧱 4. DOM 要素の追加・削除

## ✅ 新しい要素を作って追加

```js
const newItem = document.createElement('li');
newItem.textContent = '新しい項目';
document.querySelector('ul').appendChild(newItem);
```

## ✅ 要素の削除

```js
const btn = document.querySelector('#btn');
btn.remove();
```

---

# 🎮 5. イベント処理 ― 「ユーザー操作に反応する」

JavaScript の本領発揮はここです。
クリック、入力、スクロール、マウス操作などに応じて動作を定義できます。

---

## ✅ 基本構文

```js
element.addEventListener('イベント名', 関数);
```

### 例：ボタンクリックでメッセージを表示

```js
document.querySelector('#btn').addEventListener('click', () => {
    alert('ボタンが押されました！');
});
```

---

## ✅ よく使うイベント一覧

| 分類       | イベント                                     | 内容                     |
| ---------- | -------------------------------------------- | ------------------------ |
| マウス     | `click`, `mouseover`, `mouseout`, `dblclick` | クリックやホバーなど     |
| キーボード | `keydown`, `keyup`, `keypress`               | キー入力時               |
| フォーム   | `input`, `change`, `submit`                  | 入力フォーム関連         |
| ウィンドウ | `load`, `resize`, `scroll`                   | ページロード・サイズ変更 |
| タッチ     | `touchstart`, `touchend`                     | スマホ操作               |

---

## ✅ 入力イベント例

```js
document.querySelector('#name').addEventListener('input', (e) => {
    console.log(e.target.value);
});
```

→ ユーザーが文字を入力するたびに呼ばれる。

---

## ✅ フォーム送信イベント例

```js
document.querySelector('form').addEventListener('submit', (e) => {
    e.preventDefault(); // ← ページ遷移を止める
    alert('送信完了！');
});
```

---

# ⚡ 6. イベントオブジェクト（`event`）

イベントが発生した時に、詳細情報を含む `event` オブジェクトが渡されます。

```js
document.querySelector('#btn').addEventListener('click', (event) => {
    console.log(event.type); // click
    console.log(event.target); // クリックされた要素
});
```

---

# 🔁 7. イベントのバブリングとデリゲーション

## ✅ バブリング（イベントの伝播）

子要素で発生したイベントが親要素に伝わる仕組みです。

```html
<div id="parent">
    <button id="child">クリック</button>
</div>
```

```js
document.getElementById('parent').addEventListener('click', () => {
    console.log('親がクリックされた！');
});
```

ボタンを押すと「親がクリックされた！」も表示される。
➡ これを活用して「動的に追加された要素のクリック」も拾える。

---

## ✅ イベントデリゲーション（委譲）

動的な要素を扱うときに便利。

```js
document.querySelector('ul').addEventListener('click', (e) => {
    if (e.target.tagName === 'LI') {
        console.log(e.target.textContent + ' がクリックされました');
    }
});
```

➡ `<li>` が後から追加されてもイベントが動く。

---

# 🧠 8. 実際の流れ（DOM 操作 × イベント）

例：クリックで背景色を切り替える

```html
<h1 id="title">クリックで背景変更</h1>
<button id="btn">切り替え</button>
```

```js
const btn = document.getElementById('btn');
btn.addEventListener('click', () => {
    document.body.classList.toggle('dark-mode');
});
```

CSS：

```css
.dark-mode {
    background-color: #333;
    color: white;
}
```

---

# 💡 9. モダン JS での DOM 操作の進化

| 手法                      | 説明                                  |
| ------------------------- | ------------------------------------- |
| **Vanilla JS**            | 素の JavaScript（今回の内容）         |
| **jQuery**                | DOM 操作を簡略化（2000 年代主流）     |
| **React / Vue / Angular** | 仮想 DOM による再描画制御（現代主流） |

→ React や Vue は、この **DOM 操作＋イベント処理の抽象化レイヤー** として誕生しています。
つまり、今学んだ内容が「フロントの基礎中の基礎」です。

---

# 📘 まとめ：DOM 操作とイベントの基本

| 観点                   | 内容                                                               |
| ---------------------- | ------------------------------------------------------------------ |
| **DOM**                | ブラウザ上の HTML 構造を表すオブジェクトツリー                     |
| **DOM 操作**           | HTML や CSS を動的に書き換える（`textContent`, `classList`, etc.） |
| **イベント処理**       | ユーザー操作（click, input, submit）に反応して処理を実行           |
| **モダン開発との関係** | React/Vue などの仮想 DOM 技術の基礎概念                            |

---

もし次にステップアップするなら 👇

-   🔹「ブラウザのイベントループと非同期処理（setTimeout / Promise / async）」
-   🔹「React/Vue における DOM 管理の違い（仮想 DOM の仕組み）」

どちらを掘り下げたいですか？
とても良い流れです。
JavaScript の「非同期処理」と「イベントループ」を理解すると、
**「なぜ JS が 1 スレッドでも並行的に動くのか」** が腑に落ちます。
これは初心者〜上級者まで一度はつまずく重要テーマです。

---

# 🧩 1. JavaScript の実行モデル

## 🔹 シングルスレッド

> JavaScript は **1 本のスレッド（＝同時に 1 つの処理しか動かせない）** 言語です。

```text
✅ 同時に2つの命令は実行できない
❌ しかし「同時に進んでいるように見せる」ことはできる
```

この「同時に見せる」仕組みを実現しているのが――
👉 **イベントループ（Event Loop）** です。

---

# ⚙️ 2. 同期処理と非同期処理の違い

| 種類           | 内容                           | 例                               |
| -------------- | ------------------------------ | -------------------------------- |
| **同期処理**   | 上から順に 1 つずつ実行        | 通常の関数呼び出し               |
| **非同期処理** | 結果を待たずに次の処理を進める | `setTimeout`, `fetch`, `Promise` |

### 例

```js
console.log('A');
setTimeout(() => console.log('B'), 1000);
console.log('C');
```

出力結果：

```
A
C
B
```

➡ `setTimeout` の処理は **後回し（非同期）** にされる。
ここで登場するのが **イベントループの仕組み**。

---

# 🧠 3. イベントループ（Event Loop）の全体像

ブラウザや Node.js の中では、次のような構造で動いています 👇

```
┌──────────────────────────────┐
│ JavaScript Engine (例: V8)    │
│   ├─ Call Stack（実行中の関数）│
│   └─ Heap（メモリ領域）        │
└──────────────────────────────┘
        ↓
┌──────────────────────────────┐
│ Web APIs（ブラウザ機能）       │
│   ├─ setTimeout, fetch, DOM   │
│   └─ イベント登録など         │
└──────────────────────────────┘
        ↓
┌──────────────────────────────┐
│ Task Queue（コールバック待機列）│
└──────────────────────────────┘
        ↓
🌀 Event Loop が常に監視して、
Call Stack が空になったら Task Queue の処理を実行！
```

---

# 🔁 4. 処理の流れ（例：setTimeout）

```js
console.log('1');
setTimeout(() => console.log('2'), 0);
console.log('3');
```

処理の流れ：

| 手順 | 内容                                            | 結果         |
| ---- | ----------------------------------------------- | ------------ |
| ①    | "1" を出力                                      | 1            |
| ②    | `setTimeout()` をブラウザに渡す（Web API へ）   | タイマー開始 |
| ③    | "3" を出力                                      | 3            |
| ④    | イベントループがスタックが空になるのを待つ      |              |
| ⑤    | タイマー終了後、コールバックを **キューに追加** |              |
| ⑥    | Call Stack が空になるとコールバック実行         | 2            |

出力：

```
1
3
2
```

➡ `setTimeout(fn, 0)` でも「後で実行」される。
これが「イベントループによる非同期実行」。

---

# 🕓 5. 非同期の実装手段：3 段階で進化してきた

| 段階 | 手法                 | 問題点／特徴                               |
| ---- | -------------------- | ------------------------------------------ |
| ①    | **コールバック関数** | ネストが深くなりやすい（コールバック地獄） |
| ②    | **Promise**          | 非同期をチェーンで書ける                   |
| ③    | **async / await**    | 同期的に見える非同期処理（最新形）         |

---

## ① コールバック関数

```js
setTimeout(() => {
    console.log('処理1');
    setTimeout(() => {
        console.log('処理2');
    }, 1000);
}, 1000);
```

→ ネストが深くなり、可読性が下がる（Callback Hell）

---

## ② Promise（約束）

Promise は「未来の結果を表すオブジェクト」です。

```js
const promise = new Promise((resolve, reject) => {
    setTimeout(() => resolve('成功！'), 1000);
});

promise.then((result) => console.log(result));
```

出力：

```
成功！
```

### チェーン可能

```js
fetch('/api/data')
    .then((res) => res.json())
    .then((data) => console.log(data))
    .catch((err) => console.error(err));
```

---

## ③ async / await（Promise の糖衣構文）

```js
async function loadData() {
    try {
        const res = await fetch('/api/data');
        const data = await res.json();
        console.log(data);
    } catch (err) {
        console.error(err);
    }
}
```

➡ 非同期なのに「同期的に読める」書き方。

---

# 🔍 6. イベントループと Promise の関係（Microtask Queue）

実は `setTimeout` と `Promise` には **優先度の違い** があります。

| 種類                             | 実行キュー                   | タイミング                 |
| -------------------------------- | ---------------------------- | -------------------------- |
| `setTimeout`, `setInterval`      | **Task Queue（Macro Task）** | 次のループサイクルで実行   |
| `Promise.then`, `queueMicrotask` | **Microtask Queue**          | 現在のタスク終了直後に実行 |

例：

```js
console.log('A');
setTimeout(() => console.log('B'));
Promise.resolve().then(() => console.log('C'));
console.log('D');
```

出力順：

```
A
D
C
B
```

👉 Promise（Microtask）が先に処理される。

---

# 🧠 7. 非同期の動きを理解するメンタルモデル

```
1️⃣ 同期処理：すぐ実行（Call Stack）
2️⃣ 非同期API：Web APIに登録（タイマーやfetch）
3️⃣ 終了後 → Task Queueへ
4️⃣ Event Loop が Call Stack が空になるのを監視
5️⃣ 空いたら Queue から1つ取り出して実行
```

→ これを繰り返すことで「同時に進んでいるように見える」。

---

# ⚡ 8. 非同期処理の実用例

## ✅ API 通信（fetch）

```js
async function getUsers() {
    const res = await fetch('https://jsonplaceholder.typicode.com/users');
    const data = await res.json();
    console.log(data);
}
```

## ✅ アニメーション制御

```js
setInterval(() => {
    console.log('アニメフレーム更新');
}, 1000 / 60); // 60fps
```

## ✅ ボタンを一定時間後に有効化

```js
btn.disabled = true;
setTimeout(() => (btn.disabled = false), 3000);
```

---

# 📘 9. まとめ

| 観点                      | 内容                                            |
| ------------------------- | ----------------------------------------------- |
| **JavaScript の実行方式** | シングルスレッドでイベントループにより並行処理  |
| **同期／非同期の違い**    | 結果を待つか／待たずに次へ進むか                |
| **イベントループの役割**  | キューに溜まったタスクを順次実行                |
| **主要非同期 API**        | `setTimeout`, `fetch`, `Promise`, `async/await` |
| **優先度**                | Promise（Microtask） > setTimeout（Macro Task） |
| **現代の主流**            | `async / await` によるシンプルな非同期制御      |

---

もし次に進めるなら：

-   🔹 **「Promise の仕組み（内部実装と状態遷移）」**
-   🔹 **「非同期処理でよくあるバグとデバッグ方法（await 忘れ・同期待ち）」**
    どちらを深掘りしてみますか？
    .素晴らしい流れです 👏
    ここまで来ると、JavaScript の「非同期の心臓部」にあたる **Promise** の本質に踏み込む段階です。
    「なぜ `.then()` や `await` が動くのか」「Promise 内部で何が起きているのか」
    を理解すると、JS の非同期を“自在に操る”ことができます。

---

# 🧩 1. Promise とは ― 概念

> **Promise（プロミス）** は、「非同期処理の最終結果」を表すオブジェクト。
> まだ終わっていない処理の「約束（Promise）」を表現する仕組みです。

```js
const promise = new Promise((resolve, reject) => {
    // 非同期処理
    setTimeout(() => resolve('成功！'), 1000);
});

promise.then((result) => console.log(result));
```

出力：

```
成功！
```

---

# ⚙️ 2. Promise の 3 つの状態（state）

Promise は常に以下のどれかの状態を持ちます：

| 状態        | 意味                              | 遷移先                                     |
| ----------- | --------------------------------- | ------------------------------------------ |
| `pending`   | 実行中（結果待ち）                | ✅ `fulfilled` または ❌ `rejected` へ移行 |
| `fulfilled` | 成功（resolve が呼ばれた）        | 結果を `.then()` に渡す                    |
| `rejected`  | 失敗（reject が呼ばれた or 例外） | エラーを `.catch()` に渡す                 |

---

## ✅ 状態遷移のイメージ図

```
     ┌──────────────┐
     │   pending    │
     └──────┬───────┘
            │
   resolve()│        reject()
            │
     ┌──────▼──────┐
     │  fulfilled  │─────▶ then()
     └─────────────┘
     ┌──────▼──────┐
     │   rejected  │────▶ catch()
     └─────────────┘
```

---

# 🧠 3. Promise の基本構文

```js
const promise = new Promise((resolve, reject) => {
    // 非同期処理
    const success = true;
    if (success) {
        resolve('OK'); // 成功
    } else {
        reject('NG'); // 失敗
    }
});
```

受け取り側：

```js
promise
    .then((result) => console.log('成功:', result))
    .catch((error) => console.error('失敗:', error))
    .finally(() => console.log('完了'));
```

---

# 🧱 4. Promise チェーンの仕組み

Promise の `.then()` は **新しい Promise を返す** のがポイントです。

```js
fetch('data.json')
    .then((res) => res.json())
    .then((data) => console.log(data))
    .catch((err) => console.error(err));
```

これにより「処理の順番」を保証できる。

---

## チェーンのイメージ

```js
Promise.resolve(1)
    .then((x) => x + 1) // → 2
    .then((x) => x * 2) // → 4
    .then(console.log); // 4
```

`.then()` は「値を返すたびに新しい Promise を作って次に渡す」。
→ これにより **非同期でも直列処理** が可能になります。

---

# 🔁 5. Promise の状態変化を自作して理解する

内部動作を理解するには、簡易実装を模倣してみるのが一番です 👇

```js
function myPromise(executor) {
    let onResolve, onReject;
    let state = 'pending';
    let value;

    const resolve = (val) => {
        if (state !== 'pending') return;
        state = 'fulfilled';
        value = val;
        if (onResolve) onResolve(value);
    };

    const reject = (err) => {
        if (state !== 'pending') return;
        state = 'rejected';
        value = err;
        if (onReject) onReject(value);
    };

    executor(resolve, reject);

    return {
        then: (callback) => {
            onResolve = callback;
            if (state === 'fulfilled') onResolve(value);
            return this;
        },
        catch: (callback) => {
            onReject = callback;
            if (state === 'rejected') onReject(value);
            return this;
        },
    };
}
```

使ってみる：

```js
myPromise((resolve, reject) => {
    setTimeout(() => resolve('成功！'), 1000);
}).then(console.log);
```

出力：

```
成功！
```

➡ 内部で「resolve」「reject」を管理して状態遷移しているのが分かります。

---

# 🧩 6. Promise の内部実行タイミング（Microtask）

`resolve()` や `.then()` の実行はすぐではなく、**「マイクロタスクキュー」**に入ります。

```js
console.log('A');
Promise.resolve().then(() => console.log('B'));
console.log('C');
```

出力順：

```
A
C
B
```

➡ `.then()` のコールバックは、**現在の処理が終わった後**（イベントループ 1 周後）に実行される。
（`setTimeout(..., 0)` よりも早い）

---

# 🧩 7. Promise.all / Promise.race などのユーティリティ

| メソッド                       | 動作                                              | 例                     |
| ------------------------------ | ------------------------------------------------- | ---------------------- |
| `Promise.all([p1, p2, ...])`   | 全て成功したら成功。1 つでも失敗したら reject     | 並列でデータ取得       |
| `Promise.allSettled([p1, p2])` | 全件完了後、成功/失敗に関わらず結果を返す         | 状態を全部確認したい時 |
| `Promise.race([p1, p2])`       | 最初に完了した Promise の結果を採用               | タイムアウト処理など   |
| `Promise.any([p1, p2])`        | 最初に成功した Promise を返す（全失敗ならエラー） | 複数リトライ時など     |

例：

```js
const p1 = fetch('/user');
const p2 = fetch('/profile');

Promise.all([p1, p2])
    .then(([res1, res2]) => console.log('両方成功'))
    .catch((err) => console.error('どちらか失敗'));
```

---

# ⚡ 8. async/await は Promise の「糖衣構文」

`async` 関数は自動的に Promise を返します。

```js
async function greet() {
    return 'こんにちは';
}

greet().then(console.log);
```

出力：

```
こんにちは
```

`await` は「Promise の結果を待つ」構文です。

```js
async function loadData() {
    const data = await fetch('/api/data').then((res) => res.json());
    console.log(data);
}
```

➡ 裏側では、
`await` → Promise が`fulfilled`になるまで一時停止（Event Loop へ制御を返す）
→ 再開時に値を受け取る、という動きをしています。

---

# 🧠 9. Promise の内部メンタルモデルまとめ

```
new Promise() → pending
       ↓ resolve()
fulfilled → then() のコールバックをMicrotask Queueへ登録
       ↓ reject()
rejected → catch() のコールバックをMicrotask Queueへ登録
       ↓
finally() → 常に最後に呼ばれる
```

---

# 📘 10. まとめ

| 観点               | 内容                                                            |
| ------------------ | --------------------------------------------------------------- |
| **Promise とは**   | 非同期処理の結果（成功・失敗）を 1 つのオブジェクトで表す仕組み |
| **3 つの状態**     | pending → fulfilled / rejected                                  |
| **resolve/reject** | 状態遷移を起こす関数                                            |
| **then/catch**     | 結果を受け取るメソッド（チェーン可）                            |
| **内部処理**       | Microtask Queue でスケジュール実行                              |
| **派生構文**       | async / await（Promise を直感的に書ける構文）                   |
| **複数制御**       | Promise.all / race / any / allSettled                           |

---

もしさらに深掘りしたいなら、次の 2 つの方向があります 👇

-   🔹 **「Promise.all と async/await の組み合わせ（並列 vs 直列処理の最適化）」**
-   🔹 **「Promise の内部実装を再現する（完全版 Polyfill 解説）」**

どちらを次に掘り下げてみたいですか？
