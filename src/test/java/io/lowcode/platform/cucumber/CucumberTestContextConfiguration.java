package io.lowcode.platform.cucumber;

import io.cucumber.spring.CucumberContextConfiguration;
import io.lowcode.platform.IntegrationTest;
import org.springframework.test.context.web.WebAppConfiguration;

@CucumberContextConfiguration
@IntegrationTest
@WebAppConfiguration
public class CucumberTestContextConfiguration {}
