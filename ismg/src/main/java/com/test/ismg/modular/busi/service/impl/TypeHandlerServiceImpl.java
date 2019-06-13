package com.test.ismg.modular.busi.service.impl;

import java.util.List;
import java.util.Map;

import com.test.ismg.modular.busi.entity.TypeHandler;
import com.test.ismg.modular.busi.service.TypeHandlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.test.ismg.modular.busi.dao.TypeHandlerDao;

@Service
public class TypeHandlerServiceImpl implements TypeHandlerService {

	@Autowired
	TypeHandlerDao mapper;
	
	@Transactional(rollbackFor=Throwable.class)
	public void saveOrUpdate(TypeHandler entity) {
		if(entity.getId()!=null && this.selectById(entity.getId())!=null) {
			this.update(entity);
		}else {
			this.save(entity);
		}
	}
	
	@Transactional(rollbackFor=Throwable.class)
	public void save(TypeHandler entity) {
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
	public void update(TypeHandler entity) {
		mapper.update(entity);
	}
	
	@Transactional(rollbackFor=Throwable.class)
	public void updateWithOutNull(TypeHandler entity) {
		mapper.updateWithOutNull(entity);
	}
	
	public TypeHandler selectById(Long id) {
		return mapper.selectById(id);
	}
	
	public List<TypeHandler> listAll() {
		return mapper.listAll();
	}
	
	public List<TypeHandler> list(Map<String,Object> param){
		return mapper.list(param);
	}
	
	public TypeHandler singleQuery(Map<String,Object> param) throws Exception{
		List<TypeHandler> results = mapper.list(param);
		if(results.size()>1){
			throw new Exception("not a single result!");
		}else if(results.size()==0){
			return null;
		}else {
			return results.get(0);
		}
	}
	
	public PageInfo<TypeHandler> selectPage(int pageSize,int pageNum, Map<String,Object> param) {
		PageHelper.startPage(pageNum, pageSize);
		List<TypeHandler> pageList = mapper.selectPage(param);
		PageInfo<TypeHandler> pageInfo = new PageInfo<TypeHandler>(pageList);
		return pageInfo;
	}

}

