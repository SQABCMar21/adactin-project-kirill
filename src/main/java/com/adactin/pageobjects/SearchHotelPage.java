package com.adactin.pageobjects;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class SearchHotelPage
{
	private WebDriver driver;
	private Select selector;
	private boolean isError;
	private String error;

	// "Hello <username>!" greeting text locator
	@FindBy(how = How.ID, using = "username_show")
	private WebElement greetUser;

	// Hotel location locator
	@FindBy(how = How.ID, using = "location")
	private WebElement location;

	// Hotel name locator
	@FindBy(how = How.ID, using = "hotels")
	private WebElement hotels;

	// Hotel room type locator
	@FindBy(how = How.ID, using = "room_type")
	private WebElement roomType;

	// Number of rooms in Hotel locator
	@FindBy(how = How.ID, using = "room_nos")
	private WebElement numberOfRooms;

	// Hotel check in date locator
	@FindBy(how = How.ID, using = "datepick_in")
	private WebElement checkInDate;

	// Hotel check out date locator
	@FindBy(how = How.ID, using = "datepick_out")
	private WebElement checkOutDate;

	// Number of adults per hotel room locator
	@FindBy(how = How.ID, using = "adult_room")
	private WebElement adultsPerRoom;

	// Number of children per hotel room locator
	@FindBy(how = How.ID, using = "child_room")
	private WebElement children_per_room;

	// "Submit" hotel search button locator
	@FindBy(how = How.ID, using = "Submit")
	private WebElement submitButton;

	// "Reset" hotel fields button locator
	@FindBy(how = How.ID, using = "Reset")
	private WebElement resetButton;

	// Number of hotel rooms error locator
	@FindBy(how = How.ID, using = "num_room_span")
	private WebElement numRoomsError;

	// Hotel location error locator
	@FindBy(how = How.ID, using = "location_span")
	private WebElement locationError;

	// Hotel check in error locator
	@FindBy(how = How.ID, using = "checkin_span")
	private WebElement checkInError;

	// Hotel check out error locator
	@FindBy(how = How.ID, using = "checkout_span")
	private WebElement checkOutError;

	// adults per room error locator
	@FindBy(how = How.ID, using = "adults_room_span")
	private WebElement adultsError;

	// logout link locator
	@FindBy(how = How.LINK_TEXT, using = "Logout")
	private WebElement logoutLink;

	public SearchHotelPage(WebDriver driver)
	{
		this.driver = driver;
		driver.get("http://adactin.com/HotelApp/SearchHotel.php");
	}

	public SearchHotelPage adults(String num)
	{
		this.selector = new Select(this.adultsPerRoom);
		this.selector.selectByVisibleText(num);
		return this;
	}

	// method to verify the entered check in and check out dates
	public boolean checkInAndOutDates(String checkIn, String checkOut) throws ParseException
	{
		this.checkInDate.clear();
		this.checkInDate.sendKeys(checkIn);
		this.checkOutDate.clear();
		this.checkOutDate.sendKeys(checkOut);

		// Assign date format matching with job search format
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

		// check if the date values (ie. day, month and year) are within
		// a valid range
		df.setLenient(false);

		// get check in date as a Date object
		Date inDate = df.parse(this.checkInDate.getAttribute("value").toString());
		// get check out date as a Date object
		Date outDate = df.parse(this.checkOutDate.getAttribute("value").toString());

		// get the difference between two dates in milliseconds
		long diff = outDate.getTime() - inDate.getTime();
		// convert it to days
		int diffDays = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

		// verify if the check out date entered at least the same or bigger
		// than check in date
		if (diffDays < 0)
		{
			this.isError = true;
		}
		else
		{
			this.isError = false;
		}
		return this.isError;
	}

	public SearchHotelPage children(String num)
	{
		this.selector = new Select(this.children_per_room);
		this.selector.selectByVisibleText(num);
		return this;
	}

	public String helloUser()
	{
		String myGreeting = this.greetUser.getAttribute("value").toString();
		return myGreeting;
	}

	public SearchHotelPage hotelLocation(String loc)
	{
		this.selector = new Select(this.location);
		this.selector.selectByVisibleText(loc);
		return this;
	}

	public SearchHotelPage hotelName(String name)
	{
		this.selector = new Select(this.hotels);
		this.selector.selectByVisibleText(name);
		return this;
	}

	public LogoutPage logout()
	{
		this.logoutLink.click();
		return PageFactory.initElements(this.driver, LogoutPage.class);
	}

	public SearchHotelPage reset()
	{
		this.resetButton.click();
		return this;
	}

	public SearchHotelPage rooms(String num)
	{
		this.selector = new Select(this.numberOfRooms);
		this.selector.selectByVisibleText(num);
		return this;
	}

	public SearchHotelPage roomType(String type)
	{
		this.selector = new Select(this.roomType);
		this.selector.selectByVisibleText(type);
		return this;
	}

	public SelectHotelPage submit()
	{
		this.submitButton.click();
		if (this.isError)
		{
			return null;
		}
		else
		{
			return PageFactory.initElements(this.driver, SelectHotelPage.class);
		}
	}

	public String validError(String error)
	{
		if (this.locationError.isDisplayed() && error.equalsIgnoreCase("locationError"))
		{
			error = this.locationError.getText();
		}
		else if (this.numRoomsError.isDisplayed() && error.equalsIgnoreCase("numRoomsError"))
		{
			error = this.numRoomsError.getText();
		}
		else if (this.checkInError.isDisplayed() && error.equalsIgnoreCase("checkInError"))
		{
			error = this.checkInError.getText();
		}
		else if (this.checkOutError.isDisplayed() && error.equalsIgnoreCase("checkOutError"))
		{
			error = this.checkOutError.getText();
		}
		else if (this.adultsError.isDisplayed() && error.equalsIgnoreCase("adultsError"))
		{
			error = this.adultsError.getText();
		}
		return error;
	}
}
