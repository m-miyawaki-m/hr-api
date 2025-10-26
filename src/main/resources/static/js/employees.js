import { API_BASE_URL } from './constants.js';
// jQuery版：表作成
function renderEmployeeTable(employees) {
    const $status = $('#status');
    const $table = $('#employeeTable');
    const $body = $('#tableBody');
    $body.empty();

    if (!employees || employees.length === 0) {
        $status.text('No data found.').show();
        $table.hide();
    } else {
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

// jQuery版：APIリクエストと初期表示
function loadEmployees() {
    const $status = $('#status');
    $status.text('Loading data...').show();
    fetch(`${API_BASE_URL}/api/employees`)
        .then((response) => {
            if (!response.ok) throw response;
            return response.json();
        })
        .then((data) => {
            renderEmployeeTable(data);
        })
        .catch(() => {
            $status.text('Error loading employees. Check console.').show();
        });
}

$(function () {
    loadEmployees();
    const search = function () {
        const query = $('#searchInput').val().trim();
        const $status = $('#status');
        $status.text('Loading data...').show();

        let url;
        if (query) {
            url = `${API_BASE_URL}/api/employees/` + encodeURIComponent(query);
        } else {
            url = `${API_BASE_URL}/api/employees`;
        }

        fetch(url)
            .then((response) => {
                if (!response.ok) throw response;
                return response.json();
            })
            .then((data) => {
                let normalized = [];
                if (Array.isArray(data)) {
                    normalized = data;
                } else if (data) {
                    normalized = [data];
                }
                renderEmployeeTable(normalized);
            })
            .catch(async (err) => {
                let status = err.status || 0;
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
