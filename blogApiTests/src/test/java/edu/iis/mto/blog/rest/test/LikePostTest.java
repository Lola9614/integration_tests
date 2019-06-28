package edu.iis.mto.blog.rest.test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.Test;

public class LikePostTest extends FunctionalTests {
    private static final String ADD_LIKE_TO_POST_API = "/blog/user/{userId}/like/{postId}";

    @Test
    public void postShouldReturnStatusFORBBIDEN_IfUserHasStatusRemoved() {
        RestAssured.given()
                   .accept(ContentType.JSON)
                   .header("Content-Type", "application/json;charset=UTF-8")
                   .expect()
                   .log()
                   .all()
                   .statusCode(HttpStatus.SC_FORBIDDEN)
                   .when()
                   .post(ADD_LIKE_TO_POST_API, 3, 1);
    }


    @Test
    public void postShouldReturnStatusFORBIDDEN_IfUserHasStatusNew() {
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



    @Test
    public void postShouldReturnStatusOK_IfUserHasStatusConfirmed() {
        RestAssured.given()
                   .accept(ContentType.JSON)
                   .header("Content-Type", "application/json;charset=UTF-8")
                   .expect()
                   .log()
                   .all()
                   .statusCode(HttpStatus.SC_FORBIDDEN)
                   .when()
                   .post(ADD_LIKE_TO_POST_API, 1, 1);
    }
}
