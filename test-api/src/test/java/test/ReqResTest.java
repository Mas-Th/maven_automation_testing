package test;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class ReqResTest {

    @Test
    void testReqResUserById2() {
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api/user";
        RestAssured.useRelaxedHTTPSValidation();

        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

        SoftAssertions softly = new SoftAssertions();

        Response response = given()
                .header("x-api-key", "reqres-free-v1")
                .when()
                .get("/2");

        softly.assertThat(response.statusCode())
                .as("status code should be 200")
                .isEqualTo(200);

        softly.assertThat(response.jsonPath().getInt("data.id"))
                .as("data.id should be 2")
                .isEqualTo(2);

        softly.assertThat(response.jsonPath().getInt("data.year"))
                .as("data.year should be 2001")
                .isEqualTo(2001);

        softly.assertThat(response.jsonPath().getString("data.name"))
                .as("data.name should be 'fuchsia rose'")
                .isEqualTo("fuchsia rose");

        softly.assertThat(response.jsonPath().getString("support.url"))
                .as("support.url should match")
                .isEqualTo("https://contentcaddy.io?utm_source=reqres&utm_medium=json&utm_campaign=referral");

        softly.assertAll();
    }

    @Test
    void testReqResUsers() {
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api/users";
        RestAssured.useRelaxedHTTPSValidation();

        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

        SoftAssertions softly = new SoftAssertions();

        Response response = given()
                .header("x-api-key", "reqres-free-v1")
                .when()
                .get();

        softly.assertThat(response.statusCode())
                .as("status code should be 200")
                .isEqualTo(200);

        softly.assertThat(response.jsonPath().getInt("page"))
                .as("page should be 1")
                .isEqualTo(1);

        softly.assertThat(response.jsonPath().getInt("per_page"))
                .as("per_page should be 6")
                .isEqualTo(6);

        softly.assertThat(response.jsonPath().getInt("total"))
                .as("total should be 12")
                .isEqualTo(12);

        softly.assertThat(response.jsonPath().getString("data[1].email"))
                .as("data[2].email 'janet.weaver@reqres.in'")
                .isEqualTo("janet.weaver@reqres.in");

        softly.assertAll();
    }

    @Test
    void testReqResUsersQuery() throws InterruptedException {
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api/users";
        RestAssured.useRelaxedHTTPSValidation();

        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

        SoftAssertions softly = new SoftAssertions();

        long timeTotal = 0;
        long[] resTimes = new long[10];

        for(int i = 0; i < 10; i++) {
            Response response = given()
                    .header("x-api-key", "reqres-free-v1")
                    .queryParam("page", 2)
                    .when()
                    .get();

            resTimes[i] = response.time();
            timeTotal += resTimes[i];  // cộng dồn timeTotal ngay tại đây

            // Thực hiện assert ở lần cuối cùng
            if(i == 9){
                softly.assertThat(response.statusCode())
                        .as("status code should be 200")
                        .isEqualTo(200);

                softly.assertThat(response.jsonPath().getInt("page"))
                        .as("page should be 2")
                        .isEqualTo(2);

                softly.assertThat(response.jsonPath().getInt("per_page"))
                        .as("per_page should be 6")
                        .isEqualTo(6);

                softly.assertThat(response.jsonPath().getInt("total"))
                        .as("total should be 12")
                        .isEqualTo(12);

                softly.assertThat(response.jsonPath().getList("data").size())
                        .as("data[] should be 6")
                        .isEqualTo(6);

                softly.assertThat(response.jsonPath().getString("data[1].email"))
                        .as("data[1].email 'lindsay.ferguson@reqres.in'")
                        .isEqualTo("lindsay.ferguson@reqres.in");

                softly.assertAll();
            }

            Thread.sleep(1000);
        }

        // In ra mảng time
        System.out.print("Times: ");
        for(int i = 0; i < resTimes.length; i++) {
            System.out.print((i == 0 ? "" : ", ") + resTimes[i]);
        }

        System.out.println("\nTotal times count: " + resTimes.length);
        System.out.println("Time total: " + timeTotal  + "   Average Response Time: " + (timeTotal / resTimes.length) + " ms");
    }

}
