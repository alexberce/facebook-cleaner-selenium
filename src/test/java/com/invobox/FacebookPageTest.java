package com.invobox;


import org.openqa.selenium.By;

import com.invobox.core.TestBase;
import com.invobox.core.WebElement;
import com.invobox.pages.FacebookPage;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

public class FacebookPageTest extends TestBase {
    private static final String USERNAME = "XXX";
    private static final String PASSWORD = "XXX";

    private static final String PAGE_URL = "https://www.facebook.com/";
    private static final List USERS_TO_SKIP = Arrays.asList("XXX","YYY");

    @BeforeMethod()
    public void setUp() {
        try {
            rootInit();
            getDriver().get(FacebookPageTest.PAGE_URL);
            findElement(FacebookPage.Login.EMAIL_INPUT).sendKeys(USERNAME);
            findElement(FacebookPage.Login.PASSWORD_INPUT).sendKeys(PASSWORD);
            findElement(FacebookPage.Login.SUBMIT_BTN).click();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterMethod()
    public void tearDown() {
        rootTearDown();
    }

    @Test
    public void testUnlike() {
        goToActivityLog(FacebookPage.ActivityLog.LIKES_AND_REACTIONS_TAB_BTN);
        getLongWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(FacebookPage.ActivityLog.USER_PROFILE_LINK)));
        WebElement currentLikePost;
        while ((currentLikePost = getNextPostedElement()) != null) {
            findElement(String.format(FacebookPage.ActivityLog.EDIT_POST_BTN, currentLikePost.getAttribute("data-hovercard").split("id=")[1])).click();
            findElement(FacebookPage.ActivityLog.UNLIKE_POST_BTN).click();
            getDriver().navigate().refresh();
        }
    }

    @Test
    public void testUnCommnent() {
        goToActivityLog(FacebookPage.ActivityLog.COMMENTS_TAB_BTN);
        getLongWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(FacebookPage.ActivityLog.USER_PROFILE_LINK)));
        WebElement currentCommentPost;
        while ((currentCommentPost = getNextPostedElement()) != null) {
            findElement(String.format(FacebookPage.ActivityLog.EDIT_POST_BTN, currentCommentPost.getAttribute("data-hovercard").split("id=")[1])).click();
            findElement(FacebookPage.ActivityLog.DELETE_POST_BTN).click();
            getDriver().navigate().refresh();
        }
    }

    private WebElement getNextPostedElement() {
        List<WebElement> userProfileElements = findElements(FacebookPage.ActivityLog.USER_PROFILE_LINK);
        for (WebElement liker : userProfileElements) {
            String id = liker.getAttribute("data-hovercard").split("id=")[1];
            if (id != null && !FacebookPageTest.USERS_TO_SKIP.contains(id))
                return liker;
        }
        return null;
    }

    private void goToActivityLog(String xpath) {
        findElement(FacebookPage.ACCOUNT_SETTINGS_BTN).click();
        findElement(FacebookPage.ActivityLog.ACTIVITY_LOG_BTN).click();
        findElement(xpath).click();
    }


}
