package com.example.hrapp.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.hrapp.model.Employee;
import com.example.hrapp.service.EmployeeService;

/** EmployeeControllerのテストクラス。 Spring Boot Testを使用してRESTエンドポイントをテストします。 */
@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EmployeeService employeeService;

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
    @DisplayName("全従業員取得API - 正常系")
    void testGetAll_Success() throws Exception {
        // Mock設定
        when(employeeService.getAll()).thenReturn(employeeList);

        // APIテスト実行
        mockMvc
                .perform(get("/employees").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].employeeId").value(1))
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[0].lastName").value("Doe"))
                .andExpect(jsonPath("$[1].employeeId").value(2))
                .andExpect(jsonPath("$[1].firstName").value("Jane"))
                .andExpect(jsonPath("$[1].lastName").value("Smith"));
    }

    @Test
    @DisplayName("全従業員取得API - 空のリストの場合")
    void testGetAll_EmptyList() throws Exception {
        // Mock設定
        when(employeeService.getAll()).thenReturn(Collections.emptyList());

        // APIテスト実行
        mockMvc
                .perform(get("/employees").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    @DisplayName("ID指定従業員取得API - 正常系")
    void testGetById_Success() throws Exception {
        // Mock設定
        when(employeeService.getById(1)).thenReturn(employee1);

        // APIテスト実行
        mockMvc
                .perform(get("/employees/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.employeeId").value(1))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"))
                .andExpect(jsonPath("$.phoneNumber").value("123-456-7890"))
                .andExpect(jsonPath("$.jobId").value("IT_PROG"))
                .andExpect(jsonPath("$.salary").value(50000))
                .andExpect(jsonPath("$.departmentId").value(10));
    }

    @Test
    @DisplayName("ID指定従業員取得API - 存在しないIDの場合")
    void testGetById_NotFound() throws Exception {
        // Mock設定
        when(employeeService.getById(999)).thenReturn(null);

        // APIテスト実行
        mockMvc
                .perform(get("/employees/999").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("名前検索API - 正常系")
    void testGetByName_Success() throws Exception {
        // Mock設定
        when(employeeService.getByName("John")).thenReturn(Arrays.asList(employee1));

        // APIテスト実行
        mockMvc
                .perform(
                        get("/employees/search").param("name", "John").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].employeeId").value(1))
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[0].lastName").value("Doe"));
    }

    @Test
    @DisplayName("名前検索API - 該当なしの場合")
    void testGetByName_NoMatches() throws Exception {
        // Mock設定
        when(employeeService.getByName("NotExist")).thenReturn(Collections.emptyList());

        // APIテスト実行
        mockMvc
                .perform(
                        get("/employees/search")
                                .param("name", "NotExist")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    @DisplayName("名前検索API - 複数件ヒットの場合")
    void testGetByName_MultipleMatches() throws Exception {
        // Mock設定
        when(employeeService.getByName("a")).thenReturn(employeeList);

        // APIテスト実行
        mockMvc
                .perform(
                        get("/employees/search").param("name", "a").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2));
    }
}
