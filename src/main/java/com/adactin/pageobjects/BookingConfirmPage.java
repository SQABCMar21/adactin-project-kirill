package com.adactin.pageobjects;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class BookingConfirmPage extends BookHotelPage
{
	private WebDriver driver;

	// Hotel location locator
	@FindBy(how = How.ID, using = "location")
	private WebElement bcLocation;

	// Hotel name locator
	@FindBy(how = How.ID, using = "hotel_name")
	private WebElement bcHotel;

	// Hotel room type locator
	@FindBy(how = How.ID, using = "room_type")
	private WebElement bcRoomType;

	// Arrival Date locator
	@FindBy(how = How.ID, using = "arrival_date")
	private WebElement bcArrivalDate;

	// Departure Date locator
	@FindBy(how = How.ID, using = "departure_text")
	private WebElement bcDepartureDate;

	// Total Rooms locator
	@FindBy(how = How.ID, using = "total_rooms")
	private WebElement bcTotalRooms;

	// Adults per Room locator
	@FindBy(how = How.ID, using = "adults_room")
	private WebElement bcAdultsRoom;

	// Children per Room locator
	@FindBy(how = How.ID, using = "childen_room")
	private WebElement bcChildrenRoom;

	// Price per night locator
	@FindBy(how = How.ID, using = "price_night")
	private WebElement bcPriceNight;

	// Total Price locator
	@FindBy(how = How.ID, using = "total_price")
	private WebElement bcTotalPrice;

	// Guest(GST) price locator
	@FindBy(how = How.ID, using = "gst")
	private WebElement bcGuestPrice;

	// Final Billed Price locator
	@FindBy(how = How.ID, using = "final_price")
	private WebElement bcBilledPrice;

	// First Name field locator
	@FindBy(how = How.ID, using = "first_name")
	private WebElement bcFirstName;

	// Last Name field locator
	@FindBy(how = How.ID, using = "last_name")
	private WebElement bcLastName;

	// Billing Address field locator
	@FindBy(how = How.ID, using = "address")
	private WebElement bcBillingAddress;

	// Order no. locator
	@FindBy(how = How.ID, using = "order_no")
	private WebElement bcOrderNo;

	// "Search Hotel" button locator
	@FindBy(how = How.ID, using = "search_hotel")
	private WebElement bcSearchHotel;

	// "My Itinerary" button locator
	@FindBy(how = How.ID, using = "my_itinerary")
	private WebElement bcMyItinerary;

	// "Logout" button locator
	@FindBy(how = How.ID, using = "logout")
	private WebElement bcLogout;

	public BookingConfirmPage(WebDriver driver) throws Exception
	{
		super(driver);
		this.driver = driver;
	}

	@Override
	public List<String> bookHotelVals(String... vals) throws ParseException
	{
		List<String> searchedVals = new ArrayList<String>();
		for (int i = 0; i < vals.length; i++)
		{
			if (vals[i].equalsIgnoreCase("order id"))
			{
				vals[i] = this.bcOrderNo.getAttribute("value").toString();
				searchedVals.add(i, vals[i]);
			}
			else if (vals[i].equalsIgnoreCase("hotel name"))
			{
				vals[i] = this.bcHotel.getAttribute("value").toString();
				searchedVals.add(i, vals[i]);
			}
			else if (vals[i].equalsIgnoreCase("location"))
			{
				vals[i] = this.bcLocation.getAttribute("value").toString();
				searchedVals.add(i, vals[i]);
			}
			else if (vals[i].equalsIgnoreCase("rooms"))
			{
				vals[i] = this.bcTotalRooms.getAttribute("value").toString();
				vals[i] = vals[i].replace("(s)", "s");
				searchedVals.add(i, vals[i]);
			}
			else if (vals[i].equalsIgnoreCase("first name"))
			{
				vals[i] = this.bcFirstName.getAttribute("value").toString();
				searchedVals.add(i, vals[i]);
			}
			else if (vals[i].equalsIgnoreCase("last name"))
			{
				vals[i] = this.bcLastName.getAttribute("value").toString();
				searchedVals.add(i, vals[i]);
			}
			else if (vals[i].equalsIgnoreCase("arrival date"))
			{
				vals[i] = this.bcArrivalDate.getAttribute("value").toString();
				searchedVals.add(i, vals[i]);
			}
			else if (vals[i].equalsIgnoreCase("departure date"))
			{
				vals[i] = this.bcDepartureDate.getAttribute("value").toString();
				searchedVals.add(i, vals[i]);
			}
			else if (vals[i].equalsIgnoreCase("number of days"))
			{
				vals[i] = calcDaysStay();
				searchedVals.add(i, vals[i]);
			}
			else if (vals[i].equalsIgnoreCase("rooms type"))
			{
				vals[i] = this.bcRoomType.getAttribute("value").toString();
				searchedVals.add(i, vals[i]);
			}
			else if (vals[i].equalsIgnoreCase("price per night"))
			{
				vals[i] = this.bcPriceNight.getAttribute("value").toString();
				searchedVals.add(i, vals[i]);
			}
			else if (vals[i].equalsIgnoreCase("total price"))
			{
				vals[i] = this.bcBilledPrice.getAttribute("value").toString();
				searchedVals.add(i, vals[i]);
			}
		}
		return searchedVals;
	}

	public String calcDaysStay() throws ParseException
	{
		// Assign date format matching with job search format
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

		// check if the date values (ie. day, month and year) are within
		// a valid range
		df.setLenient(false);

		// get check in date as a Date object
		Date inDate = df.parse(this.bcArrivalDate.getAttribute("value").toString());
		// get check out date as a Date object
		Date outDate = df.parse(this.bcDepartureDate.getAttribute("value").toString());

		// get the difference between two dates in milliseconds
		long diff = outDate.getTime() - inDate.getTime();
		// convert it to days
		int diffDays = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

		String val = String.valueOf(diffDays) + " Days";

		return val;
	}

	public BookedItineraryPage myItinerary()
	{
		this.bcMyItinerary.click();
		return PageFactory.initElements(this.driver, BookedItineraryPage.class);
	}

	public boolean orderNumPresent()
	{
		if (this.bcOrderNo.isDisplayed())
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}
