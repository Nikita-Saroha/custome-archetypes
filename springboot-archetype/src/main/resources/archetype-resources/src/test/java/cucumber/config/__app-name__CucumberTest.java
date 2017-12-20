package ${package}.cucumber.config;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources" , glue="${package}.cucumber.steps")
public class ${app-name}CucumberTest {

}
