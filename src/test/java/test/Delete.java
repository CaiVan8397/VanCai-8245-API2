package test;

import method.CreateIssue;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.Log;

public class Delete {
    private static final method.CreateIssue CREATE_ISSUE = new CreateIssue();
    private static final method.Delete delete = new method.Delete();

    @Test(description = "User delete a issue")
    public void sendAPostRequestCreateIssue() {
        Log.info("1. Send POST request to create issue");
        String responseIssue = CREATE_ISSUE.createIssue(201);
        JSONObject jsonObject = new JSONObject(responseIssue);
        Log.info("Response: \n" + jsonObject);
        String issueId = (String) jsonObject.get("key");
        Log.info("IssueId just created is: " + issueId);

        Log.info("Step 2. Get information of issue");
        String response = CREATE_ISSUE.getIssue(issueId,200);
        JSONObject jsonObjectCreate = new JSONObject(response);
        Log.info("Response: \n"+response);
        String actualValue = (String)((JSONObject) ((JSONObject) jsonObjectCreate.get("fields")).get("status")).get("name");

        Log.info("Step 3. Verify status in response with status send request");
        Assert.assertEquals(actualValue, "To Do", "Issue create not successfully");

        Log.info("Step 4. Delete issue");
        String responseDelete = delete.deleteIssue(issueId, 204);
        JSONObject jsonObjectDelete = new JSONObject(responseDelete);
        Assert.assertEquals(jsonObjectDelete, "", "Response should be empty");

        Log.info("Step 5. Verify issue delete successfully");
        String res = CREATE_ISSUE.getIssue(issueId,404);
        JSONObject jsonObjectRes = new JSONObject(res);
        Log.info("Response: \n"+res);
//        String actualVal = (String)((JSONObject) ((JSONObject) jsonObjectCreate.get("errorMessages"));
//        Assert.assertEquals(actualVal, "Issue does not exist or you do not have permission to see it.", "Issue delete not successfully");
    }
}
