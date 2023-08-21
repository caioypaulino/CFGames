package com.project.cfgames;// Generated by Selenium IDE



import cucumber.api.java.After;
import cucumber.deps.com.thoughtworks.xstream.converters.basic.DateConverter;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class SaveNewClientTest {
  private WebDriver driver;
  private Map<String, Object> vars;
  JavascriptExecutor js;
  @Before
  public void setUp() {
    driver = new ChromeDriver();
    js = (JavascriptExecutor) driver;
    vars = new HashMap<String, Object>();
  }
  @After
  public void tearDown() {
    driver.quit();
  }
  @Test
  public void saveNewCliente() {
    System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
    driver = new ChromeDriver();
    driver.get("http://localhost:8080/admin/painel");
    driver.manage().window().setSize(new Dimension(1053, 807));
    driver.findElement(By.cssSelector("a:nth-child(3) > .btn")).click();
    driver.findElement(By.id("nome")).click();
    driver.findElement(By.id("nome")).sendKeys("Caio Paulino");
    driver.findElement(By.id("cpf")).click();
    driver.findElement(By.id("cpf")).sendKeys("50324765878");
    driver.findElement(By.id("dataNascimento")).click();
    WebElement dateBox = driver.findElement(By.id("dataNascimento"));
    dateBox.sendKeys(Keys.TAB);
    dateBox.sendKeys(Keys.TAB);
    dateBox.sendKeys("09");
    dateBox.sendKeys(Keys.TAB);
    dateBox.sendKeys("09");
    dateBox.sendKeys(Keys.TAB);
    dateBox.sendKeys("2000");
    driver.findElement(By.id("telefone")).click();
    driver.findElement(By.id("telefone")).sendKeys("11111111111");
    driver.findElement(By.id("email")).click();
    driver.findElement(By.id("email")).sendKeys("caioPaulindo@gmail.com");
    driver.findElement(By.cssSelector("body")).click();
    driver.findElement(By.id("senha")).click();
    driver.findElement(By.id("email")).click();
    driver.findElement(By.id("senha")).click();
    driver.findElement(By.id("senha")).sendKeys("SenhaTest@123");
    driver.findElement(By.cssSelector("button")).click();
  }
}