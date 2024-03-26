package endpoints;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import payload.Address;

public class AddressEndpoints {

	public static Response addAddress(Address payload,int shopperId, String token) {
		Response resp=RestAssured.given().body(payload)
				.contentType(ContentType.JSON)
				.header("Authorization","Bearer "+token)
				.pathParam("shopperId", shopperId)
				.when().post(Routes.addAddressUrl);
		
		return resp;
				
				
	}
}
