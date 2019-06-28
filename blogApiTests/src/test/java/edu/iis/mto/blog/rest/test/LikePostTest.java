package edu.iis.mto.blog.rest.test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.junit.Test;

public class LikePostTest extends FunctionalTests {
    private static final String ADD_LIKE_TO_POST_API = "/blog/user/{userId}/like/{postId}";

    @Test
    public void postShouldReturnStatusConflict_IfUserHasStatusRemoved() {
        RestAssured.given()
                   .accept(ContentType.JSON)
                   .header("Content-Type", "application/json;charset=UTF-8")
                   .expect()
                   .log()
                   .all()
                   .statusCode(HttpStatus.SC_FORBIDDEN)
                   .when()
                   .post(ADD_LIKE_TO_POST_API, 2, 1);
    }
}
