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

public class Transition {
    public void updateTransition(String status, String issueId, int statusCode){

        JSONParser parser = new JSONParser();
        Oauth2 oauth2 = new Oauth2();
        String accessToken = oauth2.getAccessToken();

        try {
            Object obj = parser.parse(new FileReader("src/main/api2/payload/Update_Transition.json"));
            JSONObject jsonObject =  (JSONObject) obj;
            String body = String.format(jsonObject.toString(),status);
            System.out.println(body);
            given().auth().oauth2(accessToken)
                    .contentType(ContentType.JSON)
                    .body(body)
                    .when()
                    .post(Constants.BASE_URL+"/rest/api/3/issue/"+issueId+"/transitions").then()
                    .statusCode(statusCode).extract().asString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
