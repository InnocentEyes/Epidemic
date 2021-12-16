package com.qzlnode.epidemic.miniprogram.dao.mysql;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author qzlzzz
 */
@Mapper
public interface MainDao {

    /**
     *
     * @return epidemicData
     */
    List<String> findAll();

    /**
     *
     * @return {@code false} or {@code true}
     */
    boolean changeAll(List<String> epidemicData);
}
