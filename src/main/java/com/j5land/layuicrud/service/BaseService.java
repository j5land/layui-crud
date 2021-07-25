package com.j5land.layuicrud.service;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 抽象类BaseService
* @ClassName: BaseService
* @Description: Service实现这个
*
 */
public interface BaseService<T,T2> {
	
    int deleteByPrimaryKey(String id);

    int insertSelective(T record);

    T selectByPrimaryKey(String id);
   
    int updateByPrimaryKeySelective(T record);
    
    int updateByCriteriaSelective(@Param("record") T record, @Param("example") T2 example);

    int updateByCriteria(@Param("record") T record, @Param("example") T2 example);
    
    List<T> selectByCriteria(T2 example);

    long countByCriteria(T2 example);

    int deleteByCriteria(T2 example);

}
