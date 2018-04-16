import com.github.javafaker.Faker;
import com.google.gson.JsonObject;
import io.restassured.RestAssured;
import org.junit.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;


public class Customers {

    @Test
    public void get_all_customers() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 3000;
        RestAssured.basePath = "/customers";

        given().
                get(baseURI).
                then().
                assertThat().
                statusCode(200);
    }

    @Test
    public void get_specific_customer() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 3000;
        RestAssured.basePath = "/customers/1";

        given().
                get().
                then().
                assertThat().
                statusCode(200).
                and().
                body("first_name", equalTo("John")).
                body("last_name", equalTo("Smith")).
                body("phone", equalTo("219-839-2819"));
    }

    @Test
    public void create_specific_customer() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 3000;
        RestAssured.basePath = "/customers";

        Faker faker = new Faker();
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String phone = faker.phoneNumber().cellPhone();

        JsonObject customerJSON = new JsonObject();
        customerJSON.addProperty("first_name", firstName);
        customerJSON.addProperty("last_name", lastName);
        customerJSON.addProperty("phone", phone);

        given().
                contentType("application/json").
                body(customerJSON).
                and().
                expect().
                statusCode(201).
                when().
                post();
    }
}
