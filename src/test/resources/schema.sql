-- テスト用employeesテーブル作成
CREATE TABLE IF NOT EXISTS employees (
    employee_id INTEGER PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    phone_number VARCHAR(50),
    job_id VARCHAR(10) NOT NULL,
    salary DECIMAL(8,2),
    department_id INTEGER
);