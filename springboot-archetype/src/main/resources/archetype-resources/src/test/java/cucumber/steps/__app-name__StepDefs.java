package ${package}.cucumber.steps;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;
import java.util.UUID;

import org.apache.log4j.MDC;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.cox.amp.commons.util.CommonConstants.MDC_Constants;

import ${package}.controller.${app-name}Controller;
import ${package}.cucumber.config.${app-name}SpringIntegrationTest;
import ${package}.model.Names;
import ${package}.service.${app-name}Service;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class ${app-name}StepDefs extends ${app-name}SpringIntegrationTest {
	
	@Mock
	${app-name}Service service;
	
	@InjectMocks
	${app-name}Controller controller;
	
	@Before
	public void setup(){
		MDC.put(MDC_Constants.CHECK_POINT, String.valueOf(new Date().getTime()));
		MDC.put(MDC_Constants.START_TIME, String.valueOf(new Date().getTime()));
		MDC.put(MDC_Constants.TRANSACTION_ID,UUID.randomUUID().toString());
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}
	
	@When("the client calls /names$")
	public void getNamesWhenTest(){
		getAllNames();
		when(service.getNames()).thenReturn(mockNameList);
	}
	
	@Then("the client receives status code of (\\d+)$")
	public void getNamesThenTest(int statusCode) throws Exception{
		mockMvc.perform(get("/v${app-version}/names"))
			.andExpect(status().is(statusCode));
	}
	
	@And("the client receives list of names with (\\d+) elements$")
	public void getNamesAndTest(int noOfElements) throws Exception{
		mockMvc.perform(get("/v${app-version}/names"))
		.andExpect(jsonPath("$",hasSize(noOfElements)))
		.andExpect(jsonPath("$[0].id",is(1)))
		.andExpect(jsonPath("$[0].name",is("Cognizant")));
	}
	
	@When("the client calls /names with id (\\d+) and name (.+)$")
	public void setNamesWhenTest(int id, String name){
		setNames(id, name);
		Mockito.doNothing().when(service).addName(new Names());
	}
	
	@Then("the client receives status code of (\\d+) on POST$")
	public void setNamesThenTest(int statusCode) throws Exception{
		mockMvc.perform(post("/v${app-version}/names")
						.content("{\"id\":2,\"name\":\"Dummy\"}")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(statusCode));
	}
	
	@And("GET call returns (\\d+) elements with 3rd id (\\d+) and name (.+)$")
	public void setNamesAndTest(int noOfElements, int id, String name) throws Exception{
		when(service.getNames()).thenReturn(mockNameList);
		mockMvc.perform(get("/v${app-version}/names"))
		.andExpect(jsonPath("$",hasSize(noOfElements)))
		.andExpect(jsonPath("$[2].id",is(id)))
		.andExpect(jsonPath("$[2].name",is(name)));
	}
}
