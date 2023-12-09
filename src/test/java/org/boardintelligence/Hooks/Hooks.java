package org.boardintelligence.Hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;
import org.boardintelligence.TestContext;
import org.boardintelligence.utils.Constants;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogType;
import roman.Reporting;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.concurrent.Semaphore;



public class Hooks {
    private static final int MAX_CONCURRENT_WEB_DRIVERS = 2;
    private static final Semaphore semaphore = new Semaphore(MAX_CONCURRENT_WEB_DRIVERS, true);

    private final TestContext testContext;

    FirefoxDriver firefoxDriver;
    ChromeDriver chromeDriver;
    EdgeDriver edgeDriver;

    public Hooks(TestContext testContext) {
        this.testContext = testContext;
    }

    @Before
    public void setup(Scenario scenario) {
        boolean headless = Boolean.parseBoolean(System.getProperty("headless", "false"));

        try {
            semaphore.acquire();
            System.err.println("SEMAPHORE ACQUIRED " + LocalDateTime.now());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        try {
            if (scenario.getSourceTagNames().contains("@FF")) {
                FirefoxOptions options = new FirefoxOptions();
                options.setHeadless(headless);
                testContext.getRoman().setDriver(testContext.getRoman().selenium.startFireFoxDriver(options));
                firefoxDriver = (FirefoxDriver) testContext.getRoman().getDriver();
            } else if (scenario.getSourceTagNames().contains("@Edge")) {
                EdgeOptions options = new EdgeOptions();
                options.setHeadless(headless);
                testContext.getRoman().setDriver(testContext.getRoman().selenium.startEdgeDriver(options));
                edgeDriver = (EdgeDriver) testContext.getRoman().getDriver();
            } else {
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments(Constants.CHROME_ARG_TO_ALLOW_ALL_CONNECTIONS);
                testContext.getRoman().setDriver(testContext.getRoman().selenium.startChromeDriver(headless, chromeOptions));
                chromeDriver = (ChromeDriver) testContext.getRoman().getDriver();
            }
            testContext.getRoman().getDriver().manage().window().setSize(new Dimension(1440, 900));
        } catch (
                Throwable t) {
            semaphore.release();
        }
    }

    @After
    public void after(Scenario scenario) {
        String msg = (scenario.isFailed()) ? "Error screenshot" : "Test Complete";
        Allure.addAttachment(msg, Reporting.takeScreenshot(testContext.getRoman().getDriver()));
        LogEntries logEntries = testContext.getRoman().getDriver().manage().logs().get(LogType.BROWSER);
        StringBuilder logs = new StringBuilder();
        for (org.openqa.selenium.logging.LogEntry entry : logEntries) {
            logs.append(new Date(entry.getTimestamp()))
                    .append(System.lineSeparator())
                    .append(" Level: ").append(entry.getLevel())
                    .append(System.lineSeparator())
                    .append(" Message: ").append(entry.getMessage())
                    .append(System.lineSeparator());
            logs.append(System.lineSeparator());
        }
        Allure.addAttachment("Console log: ", String.valueOf(logs));
        testContext.getRoman().dispose();
        semaphore.release();
    }
}
