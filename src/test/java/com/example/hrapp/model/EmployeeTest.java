package com.example.hrapp.model;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** Employeeモデルのテストクラス。 エンティティの基本的な動作をテストします。 */
class EmployeeTest {

    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = new Employee();
    }

    @Test
    @DisplayName("Employee初期化テスト")
    void testEmployeeCreation() {
        // 検証 - インスタンスが正常に作成されること
        assertThat(employee).isNotNull();
    }

    @Test
    @DisplayName("employeeIdの設定・取得テスト")
    void testEmployeeId() {
        // 実行
        employee.setEmployeeId(100);

        // 検証
        assertThat(employee.getEmployeeId()).isEqualTo(100);
    }

    @Test
    @DisplayName("firstNameの設定・取得テスト")
    void testFirstName() {
        // 実行
        employee.setFirstName("John");

        // 検証
        assertThat(employee.getFirstName()).isEqualTo("John");
    }

    @Test
    @DisplayName("lastNameの設定・取得テスト")
    void testLastName() {
        // 実行
        employee.setLastName("Doe");

        // 検証
        assertThat(employee.getLastName()).isEqualTo("Doe");
    }

    @Test
    @DisplayName("emailの設定・取得テスト")
    void testEmail() {
        // 実行
        employee.setEmail("john.doe@example.com");

        // 検証
        assertThat(employee.getEmail()).isEqualTo("john.doe@example.com");
    }

    @Test
    @DisplayName("phoneNumberの設定・取得テスト")
    void testPhoneNumber() {
        // 実行
        employee.setPhoneNumber("123-456-7890");

        // 検証
        assertThat(employee.getPhoneNumber()).isEqualTo("123-456-7890");
    }

    @Test
    @DisplayName("jobIdの設定・取得テスト")
    void testJobId() {
        // 実行
        employee.setJobId("IT_PROG");

        // 検証
        assertThat(employee.getJobId()).isEqualTo("IT_PROG");
    }

    @Test
    @DisplayName("salaryの設定・取得テスト")
    void testSalary() {
        // 実行
        BigDecimal salary = new BigDecimal("50000.00");
        employee.setSalary(salary);

        // 検証
        assertThat(employee.getSalary()).isEqualTo(salary);
    }

    @Test
    @DisplayName("departmentIdの設定・取得テスト")
    void testDepartmentId() {
        // 実行
        employee.setDepartmentId(10);

        // 検証
        assertThat(employee.getDepartmentId()).isEqualTo(10);
    }

    @Test
    @DisplayName("すべてのプロパティ設定テスト")
    void testAllProperties() {
        // 実行 - すべてのプロパティを設定
        employee.setEmployeeId(1);
        employee.setFirstName("Jane");
        employee.setLastName("Smith");
        employee.setEmail("jane.smith@example.com");
        employee.setPhoneNumber("098-765-4321");
        employee.setJobId("SA_REP");
        employee.setSalary(new BigDecimal("45000.00"));
        employee.setDepartmentId(20);

        // 検証 - すべてのプロパティが正しく設定されていること
        assertThat(employee.getEmployeeId()).isEqualTo(1);
        assertThat(employee.getFirstName()).isEqualTo("Jane");
        assertThat(employee.getLastName()).isEqualTo("Smith");
        assertThat(employee.getEmail()).isEqualTo("jane.smith@example.com");
        assertThat(employee.getPhoneNumber()).isEqualTo("098-765-4321");
        assertThat(employee.getJobId()).isEqualTo("SA_REP");
        assertThat(employee.getSalary()).isEqualTo(new BigDecimal("45000.00"));
        assertThat(employee.getDepartmentId()).isEqualTo(20);
    }

    @Test
    @DisplayName("nullプロパティ設定テスト")
    void testNullProperties() {
        // 実行 - nullを設定
        employee.setEmployeeId(null);
        employee.setFirstName(null);
        employee.setLastName(null);
        employee.setEmail(null);
        employee.setPhoneNumber(null);
        employee.setJobId(null);
        employee.setSalary(null);
        employee.setDepartmentId(null);

        // 検証 - nullが正しく設定されていること
        assertThat(employee.getEmployeeId()).isNull();
        assertThat(employee.getFirstName()).isNull();
        assertThat(employee.getLastName()).isNull();
        assertThat(employee.getEmail()).isNull();
        assertThat(employee.getPhoneNumber()).isNull();
        assertThat(employee.getJobId()).isNull();
        assertThat(employee.getSalary()).isNull();
        assertThat(employee.getDepartmentId()).isNull();
    }

    @Test
    @DisplayName("equals/hashCodeテスト（同じデータ）")
    void testEqualsAndHashCode_SameData() {
        // 準備 - 同じデータを持つ2つのEmployeeオブジェクト
        Employee employee1 = new Employee();
        employee1.setEmployeeId(1);
        employee1.setFirstName("John");
        employee1.setLastName("Doe");

        Employee employee2 = new Employee();
        employee2.setEmployeeId(1);
        employee2.setFirstName("John");
        employee2.setLastName("Doe");

        // 検証
        assertThat(employee1).isEqualTo(employee2).hasSameHashCodeAs(employee2);
    }

    @Test
    @DisplayName("equals/hashCodeテスト（異なるデータ）")
    void testEqualsAndHashCode_DifferentData() {
        // 準備 - 異なるデータを持つ2つのEmployeeオブジェクト
        Employee employee1 = new Employee();
        employee1.setEmployeeId(1);
        employee1.setFirstName("John");

        Employee employee2 = new Employee();
        employee2.setEmployeeId(2);
        employee2.setFirstName("Jane");

        // 検証
        assertThat(employee1).isNotEqualTo(employee2);
        assertThat(employee1.hashCode()).isNotEqualTo(employee2.hashCode());
    }

    @Test
    @DisplayName("toStringテスト")
    void testToString() {
        // 実行
        employee.setEmployeeId(1);
        employee.setFirstName("John");
        employee.setLastName("Doe");

        String result = employee.toString();

        // 検証 - toStringの結果にプロパティが含まれていること
        assertThat(result)
                .contains("Employee")
                .contains("employeeId=1")
                .contains("firstName=John")
                .contains("lastName=Doe");
    }
}
