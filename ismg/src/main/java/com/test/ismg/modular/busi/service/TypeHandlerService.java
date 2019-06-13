package com.test.ismg.modular.busi.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.test.ismg.modular.busi.entity.TypeHandler;

public interface TypeHandlerService {

	/**
	 * 保存或更新
	 * @param entity
	 */
	public void saveOrUpdate(TypeHandler entity);
	
	/**
	 * 保存
	 * @param entity
	 */
	public void save(TypeHandler entity);

	/**
	 * 删除
	 * @param id
	 */
	public void delete(Long id);
	
	/**
	 * 逻辑删除
	 * @param id
	 */
	public void deleteLogic(Long id);
	
	/**
	 * 更新
	 * @param entity
	 */
	public void update(TypeHandler entity);
	
	/**
	 * 更新非空字段
	 * @param entity
	 */
	public void updateWithOutNull(TypeHandler entity);
	
	/**
	 * 按id查询
	 * @param id
	 * @return
	 */
	public TypeHandler selectById(Long id);
	
	/**
	 * 查询所有
	 * @return
	 */
	public List<TypeHandler> listAll();
	
	/**
	 * 根据条件查询
	 * @return
	 */
	public List<TypeHandler> list(Map<String,Object> param);
	
	/**
	 * 根据条件查询单条数据
	 * @return
	 */	
	public TypeHandler singleQuery(Map<String,Object> param) throws Exception;
	
	/**
	 * 分页查询 
	 * @param pageSize	页面大小
	 * @param pageNum	第几页
	 * @param orderCondition		排序条件
	 * @return
	 */
	public PageInfo<TypeHandler> selectPage(int pageSize,int pageNum, Map<String,Object> param);

}

