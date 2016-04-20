package com.adactin.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class BookingConfirmPage
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
	@FindBy(how = How.ID, using = "departure_date")
	private WebElement bcDepartureDate;

	// Total Rooms locator
	@FindBy(how = How.ID, using = "total_room")
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

	public BookingConfirmPage(WebDriver driver)
	{
		this.driver = driver;
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
