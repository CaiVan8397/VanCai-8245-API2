package test;

import method.CreateIssue;
import method.Delete;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.Constants;
import utilities.Log;

public class Transition {
    private static final method.Transition transition = new method.Transition();
    private static final method.CreateIssue CREATE_ISSUE = new CreateIssue();
    private static final method.Delete delete = new Delete();

    @Test(description = "User send request to update transition for issue")
    public void sendAPostRequestUpdateTransitions() {
        Log.info("Pre step: Send POST request to create issue");
        String responseIssue = CREATE_ISSUE.createIssue(201);
        JSONObject jsonObject = new JSONObject(responseIssue);
        Log.info("Response: \n" + jsonObject);
        String issueId = (String) jsonObject.get("key");
        Log.info("IssueId just created is: " + issueId);

        Log.info("Step 1. Send request to update comment to To Do");
        transition.updateTransition(Constants.TODO, issueId,204);

        Log.info("Step 2. Get information of issue");
        String response = CREATE_ISSUE.getIssue(issueId,200);
        JSONObject jsonObjectCreate = new JSONObject(response);
        Log.info("Response: \n"+response);

        Log.info("Step 3. Verify issue transition successfully");
        String actualValue = (String)((JSONObject) ((JSONObject) jsonObjectCreate.get("fields")).get("status")).get("name");
        Assert.assertEquals(actualValue, "To Do", "The status should be to do");

        Log.info("Step 4. Send request to update comment to In Progress");
        transition.updateTransition(Constants.IN_PROGRESS,issueId,204);

        Log.info("Step 5. Get information of issue");
        response = CREATE_ISSUE.getIssue(issueId,200);
        jsonObjectCreate = new JSONObject(response);
        Log.info("Response: \n"+response);

        Log.info("Step 6. Verify issue transition successfully");
        actualValue = (String)((JSONObject) ((JSONObject) jsonObjectCreate.get("fields")).get("status")).get("name");
        Assert.assertEquals(actualValue, "In Progress", "The status should be In Progress");

        Log.info("Step 7. Send request to update comment to Done");
        transition.updateTransition(Constants.DONE,issueId,204);

        Log.info("Step 8. Get information of issue");
        response = CREATE_ISSUE.getIssue(Constants.BASE_URL + issueId,200);
        jsonObjectCreate = new JSONObject(response);
        Log.info("Response: \n"+response);

        Log.info("Step 7. Verify issue transition successfully");
        actualValue = (String)((JSONObject) ((JSONObject) jsonObjectCreate.get("fields")).get("status")).get("name");
        Assert.assertEquals(actualValue, "Done", "The status should be Done");

        Log.info("After test. Delete issue");
        String responseDelete = delete.deleteIssue(issueId, 204);
        JSONObject jsonObjectDelete = new JSONObject(responseDelete);
        Assert.assertEquals(jsonObjectDelete, "", "Response should be empty");
    }
}
