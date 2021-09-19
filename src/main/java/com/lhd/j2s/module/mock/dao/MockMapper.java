package com.lhd.j2s.module.mock.dao;

/**
 * @author lhd
 */
public interface MockMapper {

    /**
     * 清空用户
     */
    void clearUser();

    /**
     * 清空产品
     */
    void clearProduct();

    /**
     * 清空订单
     */
    void clearOrder();

    /**
     * 新增用户
     * @param path
     */
    void insertUser(String path);

    /**
     * 新增产品
     * @param path
     */
    void insertProduct(String path);

    /**
     * 新增订单
     * @param path
     */
    void insertOrder(String path);
}
