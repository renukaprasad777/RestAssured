package endpoints;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class ViewProductsEndpoints {

	public static Response viewProducts() {
		Response	resp=	RestAssured.given()
				.when().get(Routes.viewProductsUrl);

		return resp;
	}
}
