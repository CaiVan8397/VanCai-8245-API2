package method;

import auth2.Oauth2;
import io.restassured.http.ContentType;
import utilities.Constants;

import static io.restassured.RestAssured.given;

public class Delete {
    public Oauth2 oauth2 = new Oauth2();
    public String accessToken = oauth2.getAccessToken();

    public String deleteIssue(String issueId, int statusCode) {
        String response = given().auth().oauth2(accessToken)
                .contentType(ContentType.JSON)
                .when()
                .delete(Constants.BASE_URL + "/rest/api/3/issue/" + issueId).then()
                .statusCode(statusCode).extract().asString();
        return response;
    }
}
