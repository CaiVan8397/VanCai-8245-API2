package test;

import method.CreateIssue;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.Constants;
import utilities.Log;

public class Comment {
    private static final method.CreateIssue CREATE_ISSUE = new CreateIssue();
    private static final method.Comment comment = new method.Comment();
    private static final method.Delete delete = new method.Delete();

    @Test(description = "User can add a comment with valid ISSUE_ID")
    public void sendAPostRequestAddComment() {
        Log.info("1. Send POST request to create issue");
        String responseIssue = CREATE_ISSUE.createIssue(201);
        JSONObject jsonObject = new JSONObject(responseIssue);
        Log.info("Response: \n" + jsonObject);
        String issueId = (String) jsonObject.get("key");
        Log.info("IssueId just created is: " + issueId);

        Log.info("Step 2. Add comment with valid ISSUE_ID ");
        String responseCreate= comment.addComment(Constants.COMMENT, issueId,201);
        JSONObject jsonObjectCreate = new JSONObject(responseCreate);
        Log.info("Response: \n"+jsonObjectCreate);

        Log.info("Step3. Verify comment add successfully");
        String actualValue = (String) ((JSONObject) ((JSONArray) ((JSONObject) ((JSONArray) ((JSONObject) jsonObjectCreate.get("body"))
                .get("content")).get(0)).get("content")).get(0)).get("text");
        Assert.assertEquals(actualValue, Constants.COMMENT,"Comment and not successfully");

        Log.info("Step 4. Delete issue");
        String responseDelete = delete.deleteIssue(issueId, 204);
        JSONObject jsonObjectDelete = new JSONObject(responseDelete);
        Assert.assertEquals(jsonObjectDelete, "", "Response should be empty");

    }
}
