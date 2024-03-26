package endpoints;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class CartEndpoints {

	public static Response addToCart(payload.addToCart payload,String token, int shopperId) {
		Response resp=RestAssured.given().body(payload)
				.contentType(ContentType.JSON)
				.header("Authorization","Bearer "+token)
				.pathParam("shopperId", shopperId)
				.when().post(Routes.addToCartUrl);
		return resp;
	}
	
	public static Response updateCart(payload.updateCart payload,String token, int shopperId,int itemId) {
		Response resp=RestAssured.given().body(payload)
				.contentType(ContentType.JSON)
				.header("Authorization","Bearer "+token)
				.pathParam("shopperId", shopperId)
				.pathParam("itemId", itemId)
				.when().put(Routes.updateCartUrl);
		return resp;
	}
	
	public static Response getCart(String token, int shopperId,int itemId) {
		Response resp=RestAssured.given()
				.header("Authorization","Bearer "+token)
				.pathParam("shopperId", shopperId)
				.when().get(Routes.getCartUrl);
		return resp;
	}
}
