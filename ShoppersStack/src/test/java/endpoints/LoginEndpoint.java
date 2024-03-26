package endpoints;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class LoginEndpoint {

	//given & when
	public static Response Login(payload.Login payload) {
		Response resp = RestAssured.given().contentType(ContentType.JSON)
				.body(payload)
				.when().post(Routes.loginUrl);
		return resp;
	}
}
