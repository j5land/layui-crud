package com.j5land.layuicrud.mapper;

import com.j5land.layuicrud.model.SystemTables;
import com.j5land.layuicrud.model.auto.BeanColumn;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 数据库Schema相关接口
 *
 */
@Mapper
public interface GeneratorMapper {
	/**
	 * 查询当前所有表
	 * @param tableName 条件搜索
	 * @return
	 */
	 List<SystemTables> queryList(String tableName);
	 
	 /**
	  * 查询具体某表信息
	  * @param tableName
	  * @return
	  */
	 SystemTables queryTable(String tableName);
	 
	 /**
	  * 查询表详情
	  * @param tableName
	  * @return
	  */
	 List<Map<String, String>> queryColumns(String tableName);

	 /**
	  * 查询表详情
	  * @param tableName
	  * @return
	  */
	 List<BeanColumn> queryColumns2(String tableName);


	 List<Map<String, String>> queryColumns3(String tableName);
}
