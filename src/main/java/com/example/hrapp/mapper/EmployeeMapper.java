package com.example.hrapp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.hrapp.model.Employee;

/**
 * 従業員情報のDBアクセス用Mapperインターフェース。
 *
 * <p>
 * 全件取得、ID検索、名前検索のSQLを提供します。
 */
@Mapper
public interface EmployeeMapper {
    /**
     * 全従業員データを取得します。
     *
     * @return 従業員リスト
     */
    List<Employee> findAll();

    /**
     * 指定したIDの従業員データを取得します。
     *
     * @param id
     *            従業員ID
     * @return 従業員データ
     */
    Employee findById(int id);

    /**
     * 名前（部分一致）で従業員データを検索します。
     *
     * @param name
     *            検索する名前
     * @return 該当する従業員リスト
     */
    List<Employee> selectByName(String name);
}
