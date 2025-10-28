package com.example.hrapp.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.hrapp.mapper.EmployeeMapper;
import com.example.hrapp.model.Employee;

/** EmployeeServiceのテストクラス。 Mockitoを使用してMapperの依存関係をモックします。 */
@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

  @Mock private EmployeeMapper employeeMapper;

  @InjectMocks private EmployeeService employeeService;

  private Employee employee1;
  private Employee employee2;
  private List<Employee> employeeList;

  @BeforeEach
  void setUp() {
    // テストデータの準備
    employee1 = new Employee();
    employee1.setEmployeeId(1);
    employee1.setFirstName("John");
    employee1.setLastName("Doe");
    employee1.setEmail("john.doe@example.com");
    employee1.setPhoneNumber("123-456-7890");
    employee1.setJobId("IT_PROG");
    employee1.setSalary(new BigDecimal("50000"));
    employee1.setDepartmentId(10);

    employee2 = new Employee();
    employee2.setEmployeeId(2);
    employee2.setFirstName("Jane");
    employee2.setLastName("Smith");
    employee2.setEmail("jane.smith@example.com");
    employee2.setPhoneNumber("098-765-4321");
    employee2.setJobId("SA_REP");
    employee2.setSalary(new BigDecimal("45000"));
    employee2.setDepartmentId(20);

    employeeList = Arrays.asList(employee1, employee2);
  }

  @Test
  @DisplayName("全従業員取得 - 正常系")
  void testGetAll_Success() {
    // Mock設定
    when(employeeMapper.findAll()).thenReturn(employeeList);

    // メソッド実行
    List<Employee> result = employeeService.getAll();

    // 検証
    assertThat(result).isNotNull().hasSize(2);
    assertThat(result.get(0).getEmployeeId()).isEqualTo(1);
    assertThat(result.get(0).getFirstName()).isEqualTo("John");
    assertThat(result.get(1).getEmployeeId()).isEqualTo(2);
    assertThat(result.get(1).getFirstName()).isEqualTo("Jane");
  }

  @Test
  @DisplayName("全従業員取得 - 空のリストの場合")
  void testGetAll_EmptyList() {
    // Mock設定
    when(employeeMapper.findAll()).thenReturn(Collections.emptyList());

    // メソッド実行
    List<Employee> result = employeeService.getAll();

    // 検証
    assertThat(result).isNotNull().isEmpty();
  }

  @Test
  @DisplayName("ID指定従業員取得 - 正常系")
  void testGetById_Success() {
    // Mock設定
    when(employeeMapper.findById(1)).thenReturn(employee1);

    // メソッド実行
    Employee result = employeeService.getById(1);

    // 検証
    assertThat(result).isNotNull();
    assertThat(result.getEmployeeId()).isEqualTo(1);
    assertThat(result.getFirstName()).isEqualTo("John");
    assertThat(result.getLastName()).isEqualTo("Doe");
    assertThat(result.getEmail()).isEqualTo("john.doe@example.com");
    assertThat(result.getPhoneNumber()).isEqualTo("123-456-7890");
    assertThat(result.getJobId()).isEqualTo("IT_PROG");
    assertThat(result.getSalary()).isEqualTo(new BigDecimal("50000"));
    assertThat(result.getDepartmentId()).isEqualTo(10);
  }

  @Test
  @DisplayName("ID指定従業員取得 - 存在しないIDの場合")
  void testGetById_NotFound() {
    // Mock設定
    when(employeeMapper.findById(999)).thenReturn(null);

    // メソッド実行
    Employee result = employeeService.getById(999);

    // 検証
    assertThat(result).isNull();
  }

  @Test
  @DisplayName("名前検索 - 正常系（1件ヒット）")
  void testGetByName_SingleMatch() {
    // Mock設定
    when(employeeMapper.selectByName("John")).thenReturn(Arrays.asList(employee1));

    // メソッド実行
    List<Employee> result = employeeService.getByName("John");

    // 検証
    assertThat(result).isNotNull().hasSize(1);
    assertThat(result.get(0).getEmployeeId()).isEqualTo(1);
    assertThat(result.get(0).getFirstName()).isEqualTo("John");
  }

  @Test
  @DisplayName("名前検索 - 正常系（複数件ヒット）")
  void testGetByName_MultipleMatches() {
    // Mock設定
    when(employeeMapper.selectByName("a")).thenReturn(employeeList);

    // メソッド実行
    List<Employee> result = employeeService.getByName("a");

    // 検証
    assertThat(result).isNotNull().hasSize(2);
    assertThat(result.get(0).getFirstName()).isEqualTo("John");
    assertThat(result.get(1).getFirstName()).isEqualTo("Jane");
  }

  @Test
  @DisplayName("名前検索 - 該当なしの場合")
  void testGetByName_NoMatches() {
    // Mock設定
    when(employeeMapper.selectByName("NotExist")).thenReturn(Collections.emptyList());

    // メソッド実行
    List<Employee> result = employeeService.getByName("NotExist");

    // 検証
    assertThat(result).isNotNull().isEmpty();
  }

  @Test
  @DisplayName("名前検索 - 空文字列の場合")
  void testGetByName_EmptyString() {
    // Mock設定
    when(employeeMapper.selectByName("")).thenReturn(Collections.emptyList());

    // メソッド実行
    List<Employee> result = employeeService.getByName("");

    // 検証
    assertThat(result).isNotNull().isEmpty();
  }

  @Test
  @DisplayName("名前検索 - 部分一致の場合")
  void testGetByName_PartialMatch() {
    // Mock設定
    when(employeeMapper.selectByName("Joh")).thenReturn(Arrays.asList(employee1));

    // メソッド実行
    List<Employee> result = employeeService.getByName("Joh");

    // 検証
    assertThat(result).isNotNull().hasSize(1);
    assertThat(result.get(0).getFirstName()).isEqualTo("John");
  }
}
