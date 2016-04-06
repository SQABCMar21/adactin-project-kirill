package com.adactin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.adactin.pageobjects.LoginPage;
import com.adactin.pageobjects.SearchHotelPage;

public class AdactinTests
{
	private static final String BASE_URL = "http://adactin.com/HotelApp/index.php";
	private WebDriver driver;
	private WebDriverWait wait;
	private Properties props;
	private File file;
	private FirefoxProfile profile;
	private LoginPage login;
	private SearchHotelPage searchHotel;
	private String username;
	private String password;

	@AfterClass
	public void afterClass()
	{
		this.driver.quit();
	}

	@Test
	public void checkOutDateInThePast() throws ParseException
	{
		this.searchHotel = PageFactory.initElements(this.driver, SearchHotelPage.class);
		this.searchHotel.hotelLocation("Sydney").rooms("1 - One").checkInAndOutDates("06/12/2015", "05/12/2015");
		this.searchHotel.submit();
		Assert.assertEquals(this.searchHotel.validError("checkOutError"),
				"Check-Out Date shall be after than Check-In Date");
	}

	@Test
	public void compareCheckInCheckOutDates() throws ParseException
	{
		this.searchHotel = PageFactory.initElements(this.driver, SearchHotelPage.class);
		this.searchHotel.hotelLocation("Sydney").rooms("1 - One").checkInAndOutDates("04/12/2015", "03/06/2015");
		this.searchHotel.submit();
		Assert.assertEquals(this.searchHotel.validError("checkInError"),
				"Check-In Date shall be before than Check-Out Date");
	}

	@BeforeMethod
	public void loginFirst() throws Exception
	{
		this.profile = new FirefoxProfile();
		// add firebug extension to firefox test browser
		this.profile.addExtension(new File(
				"/Users/kvoitau/Dropbox/MyProjects/adactin-website/Firefox_profile/firebug@software.joehewitt.com.xpi"));
		this.driver = new FirefoxDriver();
		this.wait = new WebDriverWait(this.driver, 15);

		this.driver.get(BASE_URL);
		this.wait.until(ExpectedConditions.titleIs("AdactIn.com - Hotel Reservation System"));

		this.username = this.props.getProperty("username");
		this.password = this.props.getProperty("password");

		this.login = new LoginPage(this.driver);
		this.login.with(this.username, this.password);
	}

	@Test
	public void LoginToWebsite() throws Exception
	{
		this.searchHotel = PageFactory.initElements(this.driver, SearchHotelPage.class);
		Assert.assertEquals(this.searchHotel.helloUser(), "Hello " + this.username + "!");
	}

	@BeforeClass
	public void setUp() throws IOException, FileNotFoundException
	{
		this.file = new File("src/test/resources/adactin.properties");
		FileInputStream in = new FileInputStream(this.file);
		this.props = new Properties();
		this.props.load(in);
	}

	@AfterMethod
	public void takeScreenShotOnFailure(ITestResult testResult) throws IOException
	{
		if (testResult.getStatus() == ITestResult.FAILURE)
		{
			System.out.println(testResult.getStatus());
			File scrFile = ((TakesScreenshot) this.driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile,
					new File("FailedTestsScreenshots/" + testResult.getMethod().getMethodName() + ".png"));
		}
		this.driver.quit();
	}
}
