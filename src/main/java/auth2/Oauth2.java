package auth2;

import org.json.JSONObject;
import utilities.Constants;
import utilities.Log;

import static io.restassured.RestAssured.given;

public class Oauth2 {
    public String getAccessToken() {
        String response= given()
                .accept("application/json")
                .contentType("application/json")
                .body("{\n" +
                        "    \"grant_type\": \"client_credentials\",\n" +
                        "    \"client_id\": \""+Constants.CLIENT_ID+"\",\n" +
                        "    \"client_secret\": \""+Constants.CLIENT_SECRET+"\",\n" +
                        "    \"scope\": \"read:write\",\n" +
                        "    \"redirect_uri\": \""+Constants.API_URL+"\"\n" +
                        "}")
                .post(Constants.TOKEN_URI).then()
                .statusCode(200).extract().asString();
        JSONObject jsonObject = new JSONObject(response);
        String accessToken = (String) jsonObject.get("access_token");
        Log.info("Access token is: "+ accessToken);
        return accessToken;
    }

    public String getCloudId(String accessToken) {
        String response= given()
                .header("Authorization", "Bearer "+accessToken)
                .accept("application/json")
                .contentType("application/json")
                .get(Constants.CLOUD_ID_URI).then()
                .statusCode(200).extract().asString();
        response = response.substring(1,response.length()-1);
        JSONObject jsonObject = new JSONObject(response);
        System.out.println("Response: \n"+jsonObject);
        String cloudId = (String) jsonObject.get("id");
        Log.info("Cloud id is: "+ cloudId);
        return cloudId;
    }
}