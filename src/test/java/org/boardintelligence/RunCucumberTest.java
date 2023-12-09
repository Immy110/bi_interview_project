package org.boardintelligence;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

import static io.cucumber.junit.CucumberOptions.SnippetType.CAMELCASE;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty", "summary", "io.qameta.allure.cucumber6jvm.AllureCucumber6Jvm",
        "rerun:target/failed_scenarios.txt"}, snippets = CAMELCASE)
public class RunCucumberTest {
}
