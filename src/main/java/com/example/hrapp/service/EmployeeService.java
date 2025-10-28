package com.example.hrapp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.hrapp.mapper.EmployeeMapper;
import com.example.hrapp.model.Employee;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 従業員情報の取得・検索サービス。
 *
 * <p>
 * Mapperを利用してDBアクセスを行います。
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeMapper mapper;

    /**
     * 全従業員データを取得します。
     *
     * @return 従業員リスト
     */
    public List<Employee> getAll() {
        log.info("getAll() called");
        List<Employee> employees = mapper.findAll();
        log.info("getAll() result: {}件", employees.size());
        return employees;
    }

    /**
     * 指定したIDの従業員データを取得します。
     *
     * @param id 従業員ID
     * @return 従業員データ
     */
    public Employee getById(int id) {
        log.info("getById({}) called", id);
        Employee employee = mapper.findById(id);
        log.info("getById({}) result: {}", id, employee);
        return employee;
    }

    /**
     * 名前（部分一致）で従業員データを検索します。
     *
     * @param name 検索する名前
     * @return 該当する従業員リスト
     */
    public List<Employee> getByName(String name) {
        log.info("getByName('{}') called", name);
        List<Employee> employees = mapper.selectByName(name);
        log.info("getByName('{}') result: {}件", name, employees.size());
        return employees;
    }
}
