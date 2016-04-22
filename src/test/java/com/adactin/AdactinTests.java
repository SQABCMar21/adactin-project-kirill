package com.adactin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
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

import com.adactin.pageobjects.BookHotelPage;
import com.adactin.pageobjects.BookedItineraryPage;
import com.adactin.pageobjects.BookingConfirmPage;
import com.adactin.pageobjects.LoginPage;
import com.adactin.pageobjects.LogoutPage;
import com.adactin.pageobjects.SearchHotelPage;
import com.adactin.pageobjects.SelectHotelPage;

public class AdactinTests
{
	private static final String BASE_URL = "http://adactin.com/HotelApp/index.php";
	private WebDriver driver;
	private WebDriverWait wait;
	private Properties props;
	private File file;
	private FirefoxProfile profile;
	private LoginPage login;
	private LogoutPage logout;
	private SearchHotelPage searchHotel;
	private SelectHotelPage selectHotel;
	private BookHotelPage bookHotel;
	private BookingConfirmPage bcHotel;
	private BookedItineraryPage biHotel;
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

	@Test
	public void compareSearchAndSelectHotelCheckInAndOutDates() throws ParseException
	{
		this.searchHotel = PageFactory.initElements(this.driver, SearchHotelPage.class);
		this.searchHotel.hotelLocation("Sydney").roomType("Standard").rooms("1 - One").adults("1 - One")
				.children("1 - One").checkInAndOutDates("01/12/2015", "15/10/2016");
		this.selectHotel = this.searchHotel.submit();
		Assert.assertEquals(this.selectHotel.hotelInfo("arrival date", "01/12/2015"), "01/12/2015");
		Assert.assertEquals(this.selectHotel.hotelInfo("departure date", "15/10/2016"), "15/10/2016");
	}

	@Test
	public void compareSearchAndSelectHotelLocation() throws ParseException
	{
		this.searchHotel = PageFactory.initElements(this.driver, SearchHotelPage.class);
		this.searchHotel.hotelLocation("Sydney").roomType("Standard").rooms("1 - One").adults("1 - One")
				.children("1 - One").checkInAndOutDates("04/09/2015", "30/09/2015");
		this.selectHotel = this.searchHotel.submit();
		Assert.assertEquals(this.selectHotel.hotelInfo("location", "Sydney"), "Sydney");
	}

	@Test
	public void compareSearchAndSelectHotelNumberOfRooms() throws ParseException
	{
		this.searchHotel = PageFactory.initElements(this.driver, SearchHotelPage.class);
		this.searchHotel.hotelLocation("Sydney").roomType("Standard").rooms("1 - One").adults("1 - One")
				.children("1 - One").checkInAndOutDates("01/12/2015", "15/10/2016");
		this.selectHotel = this.searchHotel.submit();
		Assert.assertEquals(this.selectHotel.hotelInfo("rooms", "1 Rooms"), "1 Rooms");
	}

	@Test
	public void compareSearchAndSelectHotelRoomType() throws ParseException
	{
		this.searchHotel = PageFactory.initElements(this.driver, SearchHotelPage.class);
		this.searchHotel.hotelLocation("Sydney").hotelName("Hotel Creek").roomType("Deluxe").rooms("1 - One")
				.adults("1 - One").children("1 - One").checkInAndOutDates("10/04/2016", "11/04/2016");
		this.selectHotel = this.searchHotel.submit();
		Assert.assertEquals(this.selectHotel.hotelInfo("rooms type", "Deluxe"), "Deluxe");
	}

	@Test
	public void compareSelectAndBookHotelVals() throws ParseException
	{
		this.searchHotel = PageFactory.initElements(this.driver, SearchHotelPage.class);
		this.searchHotel.hotelLocation("Sydney").hotelName("Hotel Creek").roomType("Standard").rooms("2 - Two")
				.adults("1 - One").checkInAndOutDates("12/04/2016", "13/04/2016");
		this.selectHotel = this.searchHotel.submit();
		this.selectHotel.selectHotel();
		List<String> sh = this.selectHotel.selectedHotelVals("hotel name", "location", "number of days",
				"price per night");
		this.bookHotel = this.selectHotel.next();
		Assert.assertEquals(this.bookHotel.bookHotelVals("hotel name", "location", "total days", "price per night"),
				sh);
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

	@BeforeMethod
	public void userlogin() throws Exception
	{
		this.profile = new FirefoxProfile();
		// add firebug extension to firefox test browser
		this.profile.addExtension(new File(
				"/Users/kvoitau/Dropbox/MyProjects/adactin-website/Firefox_profile/firebug@software.joehewitt.com.xpi"));
		this.driver = new FirefoxDriver(this.profile);
		this.wait = new WebDriverWait(this.driver, 15);
		this.driver.get(BASE_URL);
		this.wait.until(ExpectedConditions.titleIs("AdactIn.com - Hotel Reservation System"));

		this.username = this.props.getProperty("username");
		this.password = this.props.getProperty("password");

		this.login = PageFactory.initElements(this.driver, LoginPage.class);
		this.login.with(this.username, this.password);
	}

	@Test
	public void verifyBookedItineraryValsNotEditable() throws ParseException
	{
		this.searchHotel = PageFactory.initElements(this.driver, SearchHotelPage.class);
		this.searchHotel.hotelLocation("Sydney").hotelName("Hotel Creek").roomType("Standard").rooms("2 - Two")
				.adults("1 - One").checkInAndOutDates("19/04/2016", "20/04/2016");
		this.selectHotel = this.searchHotel.submit();
		this.selectHotel.selectHotel();
		this.bookHotel = this.selectHotel.next();
		this.bookHotel.firstName("John").lastName("Tester").billingAddress("San Francisco, CA")
				.ccNumber("1234567890123456").ccType("VISA").ccExpMonth("June").ccExpYear("2018").cvvNum("123")
				.bookNow();
		this.bcHotel = PageFactory.initElements(this.driver, BookingConfirmPage.class);
		this.biHotel = this.bcHotel.myItinerary();
		Assert.assertEquals(this.biHotel.changeCellVals(), true);
	}

	@Test
	public void verifyBookedItineraryValsSameAsBookConfirm() throws ParseException
	{
		this.searchHotel = PageFactory.initElements(this.driver, SearchHotelPage.class);
		this.searchHotel.hotelLocation("Sydney").hotelName("Hotel Creek").roomType("Standard").rooms("2 - Two")
				.adults("1 - One").checkInAndOutDates("19/04/2016", "20/04/2016");
		this.selectHotel = this.searchHotel.submit();
		this.selectHotel.selectHotel();
		this.bookHotel = this.selectHotel.next();
		this.bookHotel.firstName("John").lastName("Tester").billingAddress("San Francisco, CA")
				.ccNumber("1234567890123456").ccType("VISA").ccExpMonth("June").ccExpYear("2018").cvvNum("123");
		this.bcHotel = this.bookHotel.bookNow();
		List<String> bc = this.bcHotel.bookHotelVals("hotel name", "location", "rooms", "first name", "last name",
				"arrival date", "departure date", "number of days", "rooms type", "price per night", "total price");
		this.biHotel = this.bcHotel.myItinerary();
		Assert.assertEquals(this.biHotel.compareBookingWithItineraryVals(bc, 3), true);
	}

	@Test
	public void verifyCorrectPageTitlesDisplayed() throws ParseException
	{
		this.searchHotel = PageFactory.initElements(this.driver, SearchHotelPage.class);
		this.searchHotel.clickLink("Search Hotel");
		Assert.assertEquals(this.searchHotel.correctTitle("AdactIn.com - Search Hotel"), true);
		this.searchHotel.clickLink("Booked Itinerary");
		Assert.assertEquals(this.searchHotel.correctTitle("AdactIn.com - Booked Itinerary"), true);
	}

	@Test
	public void verifyFinalBilledPrice() throws ParseException
	{
		this.searchHotel = PageFactory.initElements(this.driver, SearchHotelPage.class);
		this.searchHotel.hotelLocation("Sydney").roomType("Standard").rooms("2 - Two").adults("1 - One")
				.checkInAndOutDates("12/04/2016", "13/04/2016");
		this.selectHotel = this.searchHotel.submit();
		this.bookHotel = this.selectHotel.selectHotel().next();

		Assert.assertEquals(this.bookHotel.foundBilledPrice(), this.bookHotel.calcTotalBilledPrice(), 0);
	}

	@Test
	public void verifyHotelStayTotalPrice() throws ParseException
	{
		this.searchHotel = PageFactory.initElements(this.driver, SearchHotelPage.class);
		this.searchHotel.hotelLocation("Sydney").hotelName("Hotel Creek").roomType("Standard").rooms("2 - Two")
				.adults("1 - One").checkInAndOutDates("10/04/2016", "11/04/2016");
		this.selectHotel = this.searchHotel.submit();
		Assert.assertEquals(Arrays.toString(this.selectHotel.foundTotalPrice()),
				Arrays.toString(this.selectHotel.calcStayPrice()));
	}

	@Test
	public void verifyOrderCancellationSuccess() throws ParseException
	{
		this.searchHotel = PageFactory.initElements(this.driver, SearchHotelPage.class);
		this.searchHotel.hotelLocation("Sydney").hotelName("Hotel Creek").roomType("Standard").rooms("2 - Two")
				.adults("1 - One").checkInAndOutDates("19/04/2016", "20/04/2016");
		this.selectHotel = this.searchHotel.submit();
		this.selectHotel.selectHotel();
		this.bookHotel = this.selectHotel.next();
		this.bookHotel.firstName("John").lastName("Tester").billingAddress("San Francisco, CA")
				.ccNumber("1234567890123456").ccType("VISA").ccExpMonth("June").ccExpYear("2018").cvvNum("123");
		this.bcHotel = this.bookHotel.bookNow();
		this.biHotel = this.bcHotel.myItinerary();
		this.biHotel = PageFactory.initElements(this.driver, BookedItineraryPage.class);
		Assert.assertEquals(this.biHotel.orderCancel(), true);
	}

	@Test
	public void verifyOrderNumGenBookingConfirm() throws ParseException
	{
		this.searchHotel = PageFactory.initElements(this.driver, SearchHotelPage.class);
		this.searchHotel.hotelLocation("Sydney").hotelName("Hotel Creek").roomType("Standard").rooms("2 - Two")
				.adults("1 - One").checkInAndOutDates("19/04/2016", "20/04/2016");
		this.selectHotel = this.searchHotel.submit();
		this.selectHotel.selectHotel();
		this.bookHotel = this.selectHotel.next();
		this.bookHotel.firstName("John").lastName("Tester").billingAddress("San Francisco, CA")
				.ccNumber("1234567890123456").ccType("VISA").ccExpMonth("June").ccExpYear("2018").cvvNum("123");
		this.bcHotel = this.bookHotel.bookNow();
		Assert.assertEquals(this.bcHotel.orderNumPresent(), true);
	}

	@Test
	public void verifySearchedIdDisplaysValidRecodVals() throws ParseException
	{
		this.searchHotel = PageFactory.initElements(this.driver, SearchHotelPage.class);
		this.searchHotel.hotelLocation("Sydney").hotelName("Hotel Creek").roomType("Standard").rooms("2 - Two")
				.adults("1 - One").checkInAndOutDates("19/04/2016", "20/04/2016");
		this.selectHotel = this.searchHotel.submit();
		this.selectHotel.selectHotel();
		this.bookHotel = this.selectHotel.next();
		this.bookHotel.firstName("John").lastName("Tester").billingAddress("San Francisco, CA")
				.ccNumber("1234567890123456").ccType("VISA").ccExpMonth("June").ccExpYear("2018").cvvNum("123");
		this.bcHotel = this.bookHotel.bookNow();
		this.biHotel = this.bcHotel.myItinerary();
		this.biHotel = PageFactory.initElements(this.driver, BookedItineraryPage.class);
		List<String> biId = this.biHotel.searchedIdVals();
		Assert.assertEquals(this.biHotel.compareBookingWithItineraryVals(biId, 0), true);
	}

	@Test
	public void verifyTotalBookPrice() throws ParseException
	{
		this.searchHotel = PageFactory.initElements(this.driver, SearchHotelPage.class);
		this.searchHotel.hotelLocation("Sydney").hotelName("Hotel Creek").roomType("Standard").rooms("2 - Two")
				.adults("1 - One").checkInAndOutDates("15/04/2016", "16/04/2016");
		this.selectHotel = this.searchHotel.submit();
		this.bookHotel = this.selectHotel.selectHotel().next();

		Assert.assertEquals(this.bookHotel.foundBilledPrice(), this.bookHotel.calcTotalBilledPrice(), 0);
	}

	@Test
	public void verifyUserLogout() throws Exception
	{
		this.searchHotel = PageFactory.initElements(this.driver, SearchHotelPage.class);
		this.logout = this.searchHotel.logout();
		Assert.assertEquals(this.logout.logoutSuccess(), "You have successfully logged out. Click here to login again");
	}
}