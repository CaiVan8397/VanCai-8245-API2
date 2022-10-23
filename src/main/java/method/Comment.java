package method;

import auth2.Oauth2;
import io.restassured.http.ContentType;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import utilities.Constants;
import utilities.Log;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import static io.restassured.RestAssured.given;

public class Comment {
    JSONParser parser = new JSONParser();
    public Oauth2 oauth2 = new Oauth2();
    public String accessToken = oauth2.getAccessToken();

    public String addComment(String comment, String issueId, int statusCode){
        try {
            Object obj = parser.parse(new FileReader("src/main/api2/payload/Comment.json"));
            JSONObject jsonObject =  (JSONObject) obj;
            String body = String.format(jsonObject.toString(),comment);
            Log.info(body);
            String response= given().auth().oauth2(accessToken)
                    .contentType(ContentType.JSON)
                    .body(body)
                    .when()
                    .post(Constants.BASE_URL + "/rest/api/3/issue/"+issueId+"/comment").then()
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

    public String updateComment(String comment,String pathURL,int statusCode){
        try {
            //path file name
            Object obj = parser.parse(new FileReader("src/main/api2/payload/Comment.json"));
            JSONObject jsonObject =  (JSONObject) obj;
            String body = String.format(jsonObject.toString(),comment);
            System.out.println(body);
            String response= given().auth().oauth2(accessToken)
                    .contentType(ContentType.JSON)
                    .body(body)
                    .when()
                    .put(pathURL).then()
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
}
