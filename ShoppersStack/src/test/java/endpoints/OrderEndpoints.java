package endpoints;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import payload.Order;

public class OrderEndpoints {

	public static Response PlaceOrder(Order payload,int shopperId, String token) {
		Response resp=RestAssured.given().body(payload)
				.header("Authorization","Bearer "+token)
				.contentType(ContentType.JSON)
				.pathParam("shopperId", shopperId)
				.when().post(Routes.placeOrderUrl);
		return resp;
				
				
	}
	public static Response updateOrder(int shopperId, String token,int orderId) {
		Response resp=RestAssured.given()
				.header("Authorization","Bearer "+token)
				.pathParam("shopperId", shopperId)
				.pathParam("orderId", orderId)
				.queryParam("status", "DELIVERED")
				.when().patch(Routes.updateOrderStatusUrl);
		return resp;
				
				
	}
}
