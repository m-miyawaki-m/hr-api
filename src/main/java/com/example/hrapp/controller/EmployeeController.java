package com.example.hrapp.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.hrapp.model.Employee;
import com.example.hrapp.service.EmployeeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
@CrossOrigin
/**
 * 従業員情報の取得・検索APIコントローラー。
 *
 * <p>
 * 全件取得、ID検索、名前検索のエンドポイントを提供します。
 */
public class EmployeeController {

    private final EmployeeService service;

    /**
     * 全従業員データを取得します。
     *
     * @return 従業員リスト
     */
    @GetMapping
    public List<Employee> getAll() {
        return service.getAll();
    }

    /**
     * 指定したIDの従業員データを取得します。
     *
     * @param id 従業員ID
     * @return 従業員データ（存在しない場合は404）
     */
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getById(@PathVariable("id") int id) {
        Employee emp = service.getById(id);
        if (emp == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(emp);
    }

    /**
     * 名前（部分一致）で従業員データを検索します。
     *
     * @param name 検索する名前
     * @return 該当する従業員リスト
     */
    @GetMapping("/search")
    public List<Employee> getByName(@RequestParam String name) {
        return service.getByName(name);
    }
}
