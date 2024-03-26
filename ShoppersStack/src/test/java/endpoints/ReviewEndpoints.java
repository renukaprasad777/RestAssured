package endpoints;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import payload.AddReview;

public class ReviewEndpoints {

	public static Response addReview(AddReview payload, String token,int productId) {
		Response resp=RestAssured.given().body(payload)
				.contentType(ContentType.JSON)
				.header("Authorization","Bearer "+token)
				.queryParam("productId", productId)
				.when().post(Routes.addReviewUrl);
		return resp;
	}

	public static Response updateReview(AddReview payload, String token,int reviewId, int productId) {
		Response resp=RestAssured.given().body(payload)
				.contentType(ContentType.JSON)
				.header("Authorization","Bearer "+token)
				.pathParam("reviewId", reviewId)
				.queryParam("productId", productId)
				.when().put(Routes.updateReviewUrl);
		return resp;
	}

	public static Response deleteReview(String token,int reviewId,int productId) {
		Response resp=RestAssured.given()
				.header("Authorization","Bearer "+token)
				.pathParam("reviewId", reviewId)
				.queryParam("productId", productId)
				.when().delete(Routes.deleteReviewUrl);
		return resp;
	}
}
