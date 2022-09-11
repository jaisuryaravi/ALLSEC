package tests;

import java.time.Duration;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class T01 {
	WebDriver driver;
	boolean displayed;
	String countrycodeiso = "USA4";
	String countryname = "United States of America 4";
	String currencycode = "USD";

	@Test(priority = 1)
	public void launchBrowser() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
	}

	// @Test(dependsOnMethods = "launchBrowser")
	@Test(priority = 2)
	public void loginToBuzzily() {
		// TODO Auto-generated method stub
		driver.get("https://app.buzzily.com/run/aspx/signin.aspx");
		driver.findElement(By.id("axUserName")).sendKeys("swethav");
		driver.findElement(By.id("axPassword")).sendKeys("agile");
		driver.findElement(By.id("btnSubmit")).click();

	}

	// @Test(dependsOnMethods = "loginToBuzzily")
	@Test(priority = 3)
	public void loadCountryMaster() throws InterruptedException {
		driver.findElement(By.cssSelector("[title='Setup']")).click();
		Thread.sleep(2000);
		driver.findElement(By.cssSelector("[title='Common Masters']")).click();
		driver.findElement(By.cssSelector("[title='Geography']")).click();
		driver.findElement(By.cssSelector("[title='Country']")).click();
	}

	// @Test(dependsOnMethods = "loadCountryMaster")
	@Test(priority = 4)
	public void refreshReport() {
		driver.switchTo().frame(0);
		driver.findElement(By.id("dvRefreshParamIcon")).click();
		driver.switchTo().defaultContent();
	}

	// @Test(dependsOnMethods = "refreshReport")
	@Test(priority = 5)
	public void searchARecord() {
		driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@id='middle1']")));
		WebElement searchicon = driver.findElement(By.xpath("//a[@id='iconsNewSearchIcon']"));
		click(driver, searchicon, 20);
		WebElement searchinput = driver.findElement(By.xpath("//input[@id='ivInSearchInput']"));
		sendkeys(driver, searchinput, 20, "INDIA23");
		WebElement verify = driver.findElement(By.xpath("(//td[@data-field-name='country_name'])[1]"));
		String expectedresult = verify.getText();
		System.out.println(expectedresult);
		Assert.assertEquals("INDIA23", expectedresult);

	}

	/*
	 * @Test(dependsOnMethods = "searchARecord") public void addARecord() {
	 * driver.switchTo().frame(0);
	 * driver.findElement(By.id("iconsNewNewIcon")).click();
	 * driver.findElement(By.id("country_code000F1")).sendKeys("IND5");
	 * driver.findElement(By.name("country_name000F1")).sendKeys("INDIA5");
	 * WebElement dd = driver.findElement(By.className("select2")); Select dropdown
	 * = new Select(dd);
	 * System.out.println(dropdown.getFirstSelectedOption().getText());
	 * dropdown.selectByValue("IND");
	 * System.out.println(dropdown.getFirstSelectedOption().getText());
	 * driver.findElement(By.id("ftbtn_iSave")).click(); }
	 */

	// @Test(dependsOnMethods = "refreshReport")
	@Test(priority = 6)
	public void addARecord() throws InterruptedException {
		driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@id='middle1']")));
		WebElement addbtn = driver.findElement(By.xpath("//a[@id='iconsNewNewIcon']"));
		click(driver, addbtn, 20);

		try {
			WebElement result = driver
					.findElement(By.xpath("//body/div[4]/div[2]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]"));
			displayed = result.isDisplayed();
			System.out.println(displayed);
			WebElement no = driver.findElement(By.xpath("//button[contains(text(),'No')]"));
			click(driver, no, 20);
			Thread.sleep(3000);
		} catch (NoSuchElementException e) {
			System.out.println("catched");
		}

		WebElement countrycodeiso = driver.findElement(By.xpath("//input[@id='country_code000F1']"));
		sendkeys(driver, countrycodeiso, 20, this.countrycodeiso);
		WebElement countryname1 = driver.findElement(By.xpath("//input[@id='country_name000F1']"));
		sendkeys(driver, countryname1, 20, this.countryname);
		WebElement currencycode = driver.findElement(By.xpath(
				"//body/div[@id='dvlayout']/div[@id='pagebdy']/div[@id='heightframe']/div[@id='wBdr']/div[@id='wbdrHtml']/div[@id='DivFrame1']/div[1]/div[1]/div[1]/div[4]/div[2]/div[2]/span[1]/span[1]/span[1]"));
		click(driver, currencycode, 20);
		WebElement input = driver.findElement(By.xpath("//body/span[1]/span[1]/span[1]/input[1]"));
		sendkeys(driver, input, 20, this.currencycode);
		Thread.sleep(4000);
		input.sendKeys(Keys.ENTER);
		WebElement effectiveform = driver.findElement(By.xpath("//span[@id='basic-addon2']"));
		click(driver, effectiveform, 20);
		Thread.sleep(1000);
		WebElement selectdate = driver.findElement(By.xpath("//body/div[3]/div[2]/div[1]/div[2]/div[1]/span[12]"));
		click(driver, selectdate, 20);

		WebElement submit = driver.findElement(By.xpath("//a[@id='ftbtn_iSave']"));
		click(driver, submit, 20);
		Thread.sleep(4000);

		WebElement listview = driver.findElement(By.xpath("//a[@id='btn13']"));
		click(driver, listview, 20);
		WebElement checkbox = driver.findElement(By.xpath("//tbody/tr[1]/td[1]/div[1]/input[1]"));
		click(driver, checkbox, 20);
		WebElement verify = driver.findElement(By.xpath("(//td[@data-field-name='country_name'])[1]"));
		String expectedresult = verify.getText();
		System.out.println(expectedresult);
		Assert.assertEquals(countryname, expectedresult);
		Thread.sleep(4000);
	}

	@AfterTest()
	public void closeBrowser() {
		driver.close();
	}

	public static void sendkeys(WebDriver driver, WebElement element, int timeout, String value) {
		new WebDriverWait(driver, Duration.ofSeconds(timeout)).until(ExpectedConditions.visibilityOf(element));
		element.sendKeys(value);
	}

	public static void click(WebDriver driver, WebElement element, int timeout) {
		new WebDriverWait(driver, Duration.ofSeconds(timeout)).until(ExpectedConditions.elementToBeClickable(element));
		element.click();
	}

}
