package runnerClass;

import org.junit.runner.RunWith;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
		// format={"pretty"},
		features = "src/test/resources/features", glue = { "ui.security.authentication" }, plugin = {
				"html: target/cucmber-html-report", "json:target/cucumber.json", "pretty:target/cucmber-pretty.txt",
				"usage:target/cucumber-usage.json", "junit:target/cucmber-result.xml" }
// ,dryRun = true
)

public class testRunner {

}