package ${package}.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.cox.amp.commons.logging.LoggerUtil;

import ${package}.util.${app-name}Util;
import ${package}.dao.${app-name}Dao;
import ${package}.service.${app-name}Service;
import ${package}.model.Names;

#set ($appname = $app-name.toLowerCase())

@CacheConfig(cacheNames = "${app-name}Cache")
@Service("${app-name}ServiceImpl")
public class ${app-name}ServiceImpl implements ${app-name}Service{
	
	private static final Logger logger = LoggerFactory.getLogger(${app-name}ServiceImpl.class);
	
	@Autowired
	${app-name}Dao ${appname}Dao;
	
	public List<Names> getNames(){
		LoggerUtil.logMessage(logger, "Getting all the names from Database.");
		List<Names> namesList = ${appname}Dao.findAll();
		LoggerUtil.logMessage(logger, "Returning response with "+namesList.size()+" results.");
		return namesList;
	}
	
	public void addName(Names names) {
		LoggerUtil.logMessage(logger, "Adding new names to database - "+names.getName());
		${appname}Dao.save(names);
	} 
	
	@Cacheable(key="#id")
	public Names getNameById(int id) {
		LoggerUtil.logMessage(logger, "Looking for name with ID - "+id);
		return ${appname}Dao.findOne(id);
	}
	
	@CacheEvict(key = "#id")
	public void deleteName(int id) {
		LoggerUtil.logMessage(logger, "Deleting name with ID - "+id);
		${appname}Dao.delete(id);
	}
	
	@CacheEvict(allEntries=true)
	public void clearAllCache() {
		LoggerUtil.logMessage(logger, "Clearing cache.");
	}

	@CacheEvict(key = "#id")
	public void clearCache(Integer id) {
		LoggerUtil.logMessage(logger, "Removing id = "+id+" from cache.");
	}
	
}