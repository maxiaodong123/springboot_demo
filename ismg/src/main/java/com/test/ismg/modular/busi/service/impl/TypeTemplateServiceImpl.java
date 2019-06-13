package com.test.ismg.modular.busi.service.impl;

import java.util.Map;
import java.util.List;

import com.test.ismg.modular.busi.entity.TypeTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.test.ismg.modular.busi.dao.TypeTemplateDao;
import com.test.ismg.modular.busi.service.TypeTemplateService;

@Service
public class TypeTemplateServiceImpl implements TypeTemplateService{

	@Autowired
	TypeTemplateDao mapper;
	
	@Transactional(rollbackFor=Throwable.class)
	public void saveOrUpdate(TypeTemplate entity) {
		if(entity.getId()!=null && this.selectById(entity.getId())!=null) {
			this.update(entity);
		}else {
			this.save(entity);
		}
	}
	
	@Transactional(rollbackFor=Throwable.class)
	public void save(TypeTemplate entity) {
		mapper.save(entity);
	}
	
	@Transactional(rollbackFor=Throwable.class)
	public void delete(Long id) {
		mapper.delete(id);
	}
	
	@Transactional(rollbackFor=Throwable.class)
	public void deleteLogic(Long id){
		mapper.deleteLogic(id);
	}
	
	@Transactional(rollbackFor=Throwable.class)
	public void update(TypeTemplate entity) {
		mapper.update(entity);
	}
	
	@Transactional(rollbackFor=Throwable.class)
	public void updateWithOutNull(TypeTemplate entity) {
		mapper.updateWithOutNull(entity);
	}
	
	public TypeTemplate selectById(Long id) {
		return mapper.selectById(id);
	}
	
	public List<TypeTemplate> listAll() {
		return mapper.listAll();
	}
	
	public List<TypeTemplate> list(Map<String,Object> param){
		return mapper.list(param);
	}
	
	public TypeTemplate singleQuery(Map<String,Object> param) throws Exception{
		List<TypeTemplate> results = mapper.list(param);
		if(results.size()>1){
			throw new Exception("not a single result!");
		}else if(results.size()==0){
			return null;
		}else {
			return results.get(0);
		}
	}
	
	public PageInfo<TypeTemplate> selectPage(int pageSize,int pageNum, Map<String,Object> param) {
		PageHelper.startPage(pageNum, pageSize);
		List<TypeTemplate> pageList = mapper.selectPage(param);
		PageInfo<TypeTemplate> pageInfo = new PageInfo<TypeTemplate>(pageList);
		return pageInfo;
	}

}

