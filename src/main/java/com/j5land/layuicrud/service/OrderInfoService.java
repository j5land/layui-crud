package com.j5land.layuicrud.service;

import java.util.List;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;
import com.j5land.layuicrud.utils.ConvertUtil;
import com.j5land.layuicrud.mapper.OrderInfoMapper;
import com.j5land.layuicrud.model.auto.OrderInfo;
import com.j5land.layuicrud.model.auto.OrderInfoCriteria;
import com.j5land.layuicrud.model.request.TableParams;
import com.j5land.layuicrud.utils.SnowflakeIdWorker;
import com.j5land.layuicrud.utils.StringUtils;

/**
 * 评鉴订单信息 OrderInfoService
 * @Title: OrderInfoService.java 
 * @Package com.j5land.layuicrud.service 
 * @author Herry Jiang
 * @date 2021-07-25 23:53:00  
 **/
@Service
public class OrderInfoService implements BaseService<OrderInfo, OrderInfoCriteria>{
	@Autowired
	private OrderInfoMapper orderInfoMapper;
      	   	      	      	      	      	      	      	      	      	      	      	      	      	      	      	      	      	      	      	      	      	      	
	/**
	 * 分页查询
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	 public PageInfo<OrderInfo> list(TableParams tablepar,OrderInfo orderInfo){
		OrderInfoCriteria testCriteria=new OrderInfoCriteria();
		//搜索
		if(StrUtil.isNotEmpty(tablepar.getSearchText())) {//小窗体
			testCriteria.createCriteria().andLikeQuery2(tablepar.getSearchText());
		}else {//大搜索
			testCriteria.createCriteria().andLikeQuery(orderInfo);
		}
		PageHelper.startPage(tablepar.getPage(), tablepar.getLimit());
		List<OrderInfo> list= orderInfoMapper.selectByCriteria(testCriteria);
		PageInfo<OrderInfo> pageInfo = new PageInfo<OrderInfo>(list);
		return  pageInfo;
	 }

	@Override
	public int deleteByPrimaryKey(String ids) {
				Long[] integers = ConvertUtil.toLongArray(",", ids);
		List<Long> stringB = Arrays.asList(integers);
		OrderInfoCriteria example=new OrderInfoCriteria();
		example.createCriteria().andIdIn(stringB);
		return orderInfoMapper.deleteByCriteria(example);
			}
	
	
	@Override
	public OrderInfo selectByPrimaryKey(String id) {
				Long id1 = Long.valueOf(id);
		return orderInfoMapper.selectByPrimaryKey(id1);
			}

	@Override
	public int updateByPrimaryKeySelective(OrderInfo record) {
		return orderInfoMapper.updateByPrimaryKeySelective(record);
	}
	
	/**
	 * 添加
	 */
	@Override
	public int insertSelective(OrderInfo record) {
				record.setId(null);
				return orderInfoMapper.insertSelective(record);
	}

	@Override
	public int updateByCriteriaSelective(OrderInfo record, OrderInfoCriteria example) {
		return orderInfoMapper.updateByCriteriaSelective(record, example);
	}

	@Override
	public int updateByCriteria(OrderInfo record, OrderInfoCriteria example) {
		return orderInfoMapper.updateByCriteria(record, example);
	}

	@Override
	public List<OrderInfo> selectByCriteria(OrderInfoCriteria example) {
		return orderInfoMapper.selectByCriteria(example);
	}

	@Override
	public long countByCriteria(OrderInfoCriteria example) {
		return orderInfoMapper.countByCriteria(example);
	}

	@Override
	public int deleteByCriteria(OrderInfoCriteria example) {
		return orderInfoMapper.deleteByCriteria(example);
	}

}
