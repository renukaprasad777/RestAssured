package tests;

import org.testng.Reporter;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.*;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import endpoints.AddressEndpoints;
import endpoints.CartEndpoints;
import endpoints.LoginEndpoint;
import endpoints.OrderEndpoints;
import endpoints.ReviewEndpoints;
import endpoints.ViewProductsEndpoints;
import io.restassured.response.Response;
import payload.AddReview;
import payload.Address;
import payload.Login;
import payload.Order;

public class E2e {
	int shopperId;
	String token;
	int productId;
	int itemId;
	int orderId;
	int addressId;
	int	reviewId;


	@Test(priority = 1)
	public void login() {
		Login log=new Login();
		log.setEmail("api.rp21@gmail.com");
		log.setPassword("Reset@123");
		log.setRole("SHOPPER");

		Response resp=LoginEndpoint.Login(log);
		Reporter.log("\n logged in");
		shopperId=resp.jsonPath().get("data.userId");
		token=resp.jsonPath().get("data.jwtToken");
		resp.then().assertThat().statusCode(200);
		System.out.println(shopperId);
		System.out.println(token);
	}

	@Test(priority = 2)
	public void viewProducts() {
		Response resp=ViewProductsEndpoints.viewProducts();
		productId=resp.jsonPath().get("data[0].productId");
		resp.then().assertThat().statusCode(200);

		System.out.println(productId);
	}

	@Test(priority = 3)
	public void addToCart() {
		payload.addToCart add=new payload.addToCart();
		add.setProductId(productId);
		add.setQuantity(1);

		Response resp=CartEndpoints.addToCart(add, token, shopperId);
		itemId=resp.jsonPath().get("data.itemId");
		System.out.println(itemId);
		resp.then().assertThat().statusCode(201);
	}

	@Test(priority = 4)
	public void updateCart() {
		payload.updateCart up=new payload.updateCart();
		up.setProductId(productId);
		up.setQuantity(5);

		Response resp=CartEndpoints.updateCart(up, token, shopperId,itemId);
		resp.then().assertThat().statusCode(200);
	}	

	@Test(priority = 5)
	public void getCart() {

		Response resp=CartEndpoints.getCart(token, shopperId, itemId);
		resp.then().assertThat().statusCode(200);
	}

	@Test(priority = 6)
	public void addAddress() {
		Address add=new Address();
		add.setAddressId(0);
		add.setBuildingInfo("4th floor");
		add.setCity("Bangalore");
		add.setCountry("India");
		add.setLandmark("Near Puma showroom");
		add.setName("ABC mansion");
		add.setPhone("9263792172");
		add.setPincode("560010");
		add.setState("Karnataka");
		add.setStreetInfo("16th main road");
		add.setType("Office");

		Response resp=AddressEndpoints.addAddress(add, shopperId,token);
		addressId=resp.jsonPath().get("data.addressId");
		System.out.println(addressId);
		resp.then().assertThat().statusCode(201);

	}

	@Test(priority = 7)
	public void PlaceOrder() {
		Address add=new Address();
		add.setAddressId(addressId);

		Order ord=new Order();
		ord.setAddress(add);
		ord.setPaymentMode("COD");

		Response resp=OrderEndpoints.PlaceOrder(ord, shopperId, token);
		resp.then().assertThat().statusCode(201);
		orderId=resp.jsonPath().get("data.orderId");
		System.out.println(orderId);

	}

	@Test(priority = 8)
	public void updateOrder() {

		Response resp=OrderEndpoints.updateOrder(shopperId, token,orderId);
		resp.then().assertThat().statusCode(200)
		.body("data.orderStatus", equalTo("DELIVERED"));
	}

	@Test(priority = 9)
	public void addReview() {
		AddReview add=new AddReview();
		add.setHeading("Amazing product");
		add.setDescription("Wonderful");
		add.setRating(3);
		add.setShopperId(shopperId);
		add.setShopperName("RP");

		Response resp=	ReviewEndpoints.addReview(add, token,productId);
		resp.then().assertThat().statusCode(200);
		reviewId=resp.jsonPath().get("data.reviewId");
		System.out.println(reviewId);


	}

	@Test(priority = 10)
	public void updateReview() {
		AddReview upd=new AddReview();
		upd.setHeading("worst product");
		upd.setDescription("dabba product");
		upd.setRating(1);
		upd.setShopperId(shopperId);
		upd.setShopperName("RP");

		Response resp=	ReviewEndpoints.updateReview(upd, token,reviewId,productId);
		resp.then().assertThat().statusCode(200);

	}

	@Test(priority = 11)
	public void deleteReview() {

		Response resp=	ReviewEndpoints.deleteReview(token,reviewId,productId);
		resp.then().assertThat().statusCode(200);

	}
}