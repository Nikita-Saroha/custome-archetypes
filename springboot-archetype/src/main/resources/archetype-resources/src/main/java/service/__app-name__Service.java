package ${package}.service;

import java.util.List;

import ${package}.model.Names;

public interface ${app-name}Service{
	
	List<Names> getNames();
	
	void addName(Names names);
	
	Names getNameById(int id);
	
	void deleteName(int id);
	
	void clearAllCache();

	void clearCache(Integer id);
	
}