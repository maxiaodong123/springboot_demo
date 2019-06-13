package com.test.ismg.modular.busi.dao;

import java.util.Map;
import java.util.List;

import com.test.ismg.modular.busi.entity.TypeTemplate;

public interface TypeTemplateDao {

	/**
	 * 保存
	 * @param entity
	 */
	public void save(TypeTemplate entity);

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
	public void update(TypeTemplate entity);
	
	/**
	 * 更新非空字段
	 * @param entity
	 */
	public void updateWithOutNull(TypeTemplate entity);

	/**
	 * 按id查询
	 * @param id
	 * @return
	 */
	public TypeTemplate selectById(Long id);
	
	/**
	 * 查询所有
	 * @return
	 */
	public List<TypeTemplate> listAll();
	
	/**
	 * 根据条件查询
	 * @return
	 */
	public List<TypeTemplate> list(Map<String,Object> param);
	
	/**
	 * 分页查询 
	 * @param param	查询参数
	 * @return
	 */
	public List<TypeTemplate> selectPage(Map<String,Object> param);

}

