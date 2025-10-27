/**
 * 従業員テーブルの描画・検索・API連携を行うフロントエンドスクリプト
 * @module employees
 */
import { API_BASE_URL } from './constants.js';
/**
 * 従業員データの配列をHTMLテーブルに描画する
 * @param {Array<Object>} employees - 従業員データ配列
 */
function renderEmployeeTable(employees) {
    const $status = $('#status');
    const $table = $('#employeeTable');
    const $body = $('#tableBody');
    $body.empty();

    // データが空の場合の分岐
    if (!employees || employees.length === 0) {
        $status.text('No data found.').show();
        $table.hide();
    } else {
        // データがある場合は1行ずつテーブルに追加
        for (const emp of employees) {
            const $row = $('<tr></tr>');
            $row.append($('<td></td>').text(emp.employeeId ?? ''));
            $row.append($('<td></td>').text(emp.firstName ?? ''));
            $row.append($('<td></td>').text(emp.lastName ?? ''));
            $row.append($('<td></td>').text(emp.email ?? ''));
            $row.append($('<td></td>').text(emp.phoneNumber ?? ''));
            $row.append($('<td></td>').text(emp.salary ?? ''));
            $row.append($('<td></td>').text(emp.departmentId ?? ''));
            $body.append($row);
        }
        $status.hide();
        $table.show();
    }
}

/**
 * 全従業員データをAPIから取得しテーブル表示する
 */
function loadEmployees() {
    const $status = $('#status');
    $status.text('Loading data...').show();
    // APIから従業員データを取得
    fetch(`${API_BASE_URL}/api/employees`)
        .then((response) => {
            // レスポンスが正常でなければ例外を投げる
            if (!response.ok) throw response;
            // JSONデータとしてパース
            return response.json();
        })
        .then((data) => {
            // 取得したデータをテーブルに描画
            renderEmployeeTable(data);
        })
        .catch(() => {
            // エラー時の表示
            $status.text('Error loading employees. Check console.').show();
        });
}

$(function () {
    loadEmployees();
    /**
     * 検索バーの入力値で従業員データをAPI検索しテーブル表示する
     * @function
     */
    const search = function () {
        const query = $('#searchInput').val().trim();
        const $status = $('#status');
        $status.text('Loading data...').show();

        let url;
        // 検索バーに入力があればID/名前検索、なければ全件取得API
        if (query) {
            url = `${API_BASE_URL}/api/employees/` + encodeURIComponent(query);
        } else {
            url = `${API_BASE_URL}/api/employees`;
        }

        // APIリクエスト実行
        fetch(url)
            .then((response) => {
                // レスポンスが正常でなければ例外を投げる
                if (!response.ok) throw response;
                // JSONデータとしてパース
                return response.json();
            })
            .then((data) => {
                let normalized = [];
                // 返却データが配列か単一オブジェクトかで正規化
                if (Array.isArray(data)) {
                    normalized = data;
                } else if (data) {
                    normalized = [data];
                }
                // テーブル描画
                renderEmployeeTable(normalized);
            })
            .catch(async (err) => {
                let status = err.status || 0;
                // ステータスごとにエラーダイアログ表示
                if (status === 404) {
                    $('<div>該当する従業員が見つかりませんでした。</div>').dialog({
                        title: 'エラー',
                        modal: true,
                        buttons: {
                            OK: function () {
                                $(this).dialog('close');
                            },
                        },
                    });
                } else if (status === 500) {
                    $('<div>サーバーエラーが発生しました。管理者に連絡してください。</div>').dialog(
                        {
                            title: 'エラー',
                            modal: true,
                            buttons: {
                                OK: function () {
                                    $(this).dialog('close');
                                },
                            },
                        }
                    );
                } else {
                    $('<div>エラーが発生しました。ステータス: ' + status + '</div>').dialog({
                        title: 'エラー',
                        modal: true,
                        buttons: {
                            OK: function () {
                                $(this).dialog('close');
                            },
                        },
                    });
                }
                // ステータスバーにエラー表示
                $status.text('Error loading employees. Check console.').show();
            });
    };
    $('#searchButton').on('click', search);
    $('#searchInput').on('keydown', function (e) {
        if (e.key === 'Enter') {
            search();
        }
    });
});
