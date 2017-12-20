package ${package}.controller;

import io.swagger.annotations.Api;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cox.amp.commons.logging.LoggerUtil;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ${package}.model.Names;
import ${package}.service.${app-name}Service;
#set ($appname = $app-name.toLowerCase())

@RequestMapping("/v${app-version}")
@RestController
@Api(value="namestore", description="Operations pertaining to list of names stored in database.")
public class ${app-name}Controller{
	
	private static final Logger logger = LoggerFactory.getLogger(${app-name}Controller.class);
	
	@Autowired
	${app-name}Service ${appname}Service;
	
	@RequestMapping(value = "/names", method = RequestMethod.GET, produces = "application/json")
	public List<Names> getNames(){
		LoggerUtil.logMessage(logger, "Starting getNames service.");
		return ${appname}Service.getNames();
	}
	
	@PostMapping(value = "/names", consumes = "application/json")
	public void setName(@RequestBody Names names){
		LoggerUtil.logMessage(logger, "Starting add names service.");
		${appname}Service.addName(names);
	}
	
	@GetMapping("/names/{id}")
	public Names getNameById(@PathVariable Integer id){
		LoggerUtil.logMessage(logger, "Starting get name by ID service.");
		return ${appname}Service.getNameById(id);
	}
	
	@DeleteMapping("/names/{id}")
	public void deleteNameById(@PathVariable Integer id){
		LoggerUtil.logMessage(logger, "Starting delete name by ID service.");
		${appname}Service.deleteName(id);
	}
	
	@DeleteMapping("/cache/remove/all")
	public void clearAllCache(){
		${appname}Service.clearAllCache();
	}
	
	@DeleteMapping("/cache/remove/{id}")
	public void clearCache(@PathVariable Integer id){
		${appname}Service.clearCache(id);
	}
	
}