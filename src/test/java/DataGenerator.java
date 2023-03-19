import com.github.javafaker.*;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import java.util.Locale;

import static io.restassured.RestAssured.given;

public class DataGenerator {
    private DataGenerator() {
    }

    private static Faker faker = new Faker(new Locale("en"));
    private static RequestSpecification requestSpec = new RequestSpecBuilder()
        .setBaseUri("http://localhost")
        .setPort(9999)
        .setAccept(ContentType.JSON)
        .setContentType(ContentType.JSON)
        .log(LogDetail.ALL)
        .build();

    private static void makeRegistration(AuthDto authDto) {
        given()
            .spec(requestSpec)
            .body(authDto)
            .when()
            .post("/api/system/users")
            .then()
            .statusCode(200);
    }

    public static AuthDto user(String status) {
        String login = faker.name().firstName().toLowerCase();
        String password = faker.internet().password();
        makeRegistration(new AuthDto(login, password, status));
        return new AuthDto(login, password, status);
    }


    public static AuthDto incorrectLoginUser() {
        String password = faker.internet().password();
        makeRegistration(new AuthDto("novalidlogin", password, "active"));
        return new AuthDto("login", password, "active");
    }

    public static AuthDto incorrectPasswordUser() {
        String login = faker.name().firstName().toLowerCase();
        makeRegistration(new AuthDto(login, "novalidpass", "active"));
        return new AuthDto(login, "novalid", "active");
    }
}