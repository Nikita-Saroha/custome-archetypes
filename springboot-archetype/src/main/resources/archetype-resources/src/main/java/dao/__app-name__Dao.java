package ${package}.dao;

import java.util.List;

import ${package}.model.Names;

public interface ${app-name}Dao {
	
	public List<Names> findAll();
	
	public Names save(Names name);	
	
	public Names findOne(Integer id);
	
	public void delete(Integer id);
}