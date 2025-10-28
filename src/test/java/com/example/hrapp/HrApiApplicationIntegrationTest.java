package com.example.hrapp;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.example.hrapp.mapper.EmployeeMapper;

/** HR-APIアプリケーション統合テストクラス。 アプリケーション全体の統合テストを実施します。 */
@SpringBootTest
@ActiveProfiles("test")
class HrApiApplicationIntegrationTest {

  @MockitoBean private EmployeeMapper employeeMapper;

  @Test
  @DisplayName("Spring Contextロードテスト")
  void contextLoads() {
    // コンテキストが正常にロードされることを確認
    // このテストメソッドが実行されることで、Spring Bootアプリケーションが正常に起動することを確認
    assertThat(employeeMapper).isNotNull();
  }
}
