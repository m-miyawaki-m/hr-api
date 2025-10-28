package com.example.hrapp.model;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.Data;

/**
 * 従業員情報のエンティティクラス。
 *
 * <p>DBのemployeesテーブルに対応します。
 */
@Data
public class Employee {
  /** 従業員ID */
  private Integer employeeId;

  /** 名 */
  private String firstName;

  /** 姓 */
  private String lastName;

  /** メールアドレス */
  private String email;

  /** 電話番号 */
  private String phoneNumber;

  /** 職種ID */
  private String jobId;

  /** 給与 */
  private BigDecimal salary;

  /** 部門ID */
  private Integer departmentId;

  private static final Logger log = LoggerFactory.getLogger(Employee.class);

  /** Employeeインスタンス生成時のログ出力 */
  public Employee() {
    log.info("Employeeインスタンス生成: {}", this);
  }
}
