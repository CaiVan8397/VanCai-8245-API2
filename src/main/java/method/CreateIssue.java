package method;

import auth2.Oauth2;
import io.restassured.http.ContentType;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import utilities.Constants;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import static io.restassured.RestAssured.given;

public class CreateIssue {
    JSONParser parser = new JSONParser();
    public Oauth2 oauth2 = new Oauth2();
    public String accessToken = oauth2.getAccessToken();

    public String createIssue(int statusCode){
        String accessToken = oauth2.getAccessToken();
        try {
            Object obj = parser.parse(new FileReader("src/main/java/payload/Issue_Payload.json"));
            JSONObject jsonObject =  (JSONObject) obj;
            String body = jsonObject.toString();
            System.out.println(body);
            String response = given().auth().oauth2(accessToken)
                    .contentType(ContentType.JSON)
                    .body(body)
                    .when()
                    .post(Constants.BASE_URL + "/rest/api/3/issue/").then()
                    .statusCode(statusCode).extract().asString();
            return response;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getIssue(String issueId,int statusCode){
        String response= given().auth().oauth2(accessToken)
                .contentType(ContentType.JSON)
                .when()
                .get(Constants.BASE_URL + "/rest/api/3/issue/" + issueId).then()
                .statusCode(statusCode).extract().asString();
        return response;
    }
}
