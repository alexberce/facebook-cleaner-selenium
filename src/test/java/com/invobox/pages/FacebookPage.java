package com.invobox.pages;

public class FacebookPage {
    public static final class Login {
        public static final String EMAIL_INPUT = "//input[@id='email']";
        public static final String PASSWORD_INPUT = "//input[@id='pass']";
        public static final String SUBMIT_BTN = "//label[@id='loginbutton']";
    }

    public static final String ACCOUNT_SETTINGS_BTN = "//div[@id='userNavigationLabel']";

    public static final class ActivityLog {
        public static final String ACTIVITY_LOG_BTN = "//div[text()='Activity Log']";
        public static final String LIKES_AND_REACTIONS_TAB_BTN = "//div[contains(text(), 'Likes and Reactions')]";
        public static final String COMMENTS_TAB_BTN = "//span[@class='imgWrap']/following-sibling::div[contains(text(), 'Comments')]";
        public static final String USER_PROFILE_LINK = "//a[@class='profileLink' and contains(@data-hovercard, 'id')]";
        public static final String BOX_OF_ELEMENTS_TPL = "//div[contains(@class, 'uiBoxWhite bottomborder') and contains(.//following-sibling::a/@data-hovercard, '%s')]";
        public static final String EDIT_POST_BTN = BOX_OF_ELEMENTS_TPL + "//a[@data-tooltip-content='Edit']";
        public static final String UNLIKE_POST_BTN = "//span[contains(text(), 'Unlike')]";
        public static final String DELETE_POST_BTN = "//span[contains(text(), 'Delete')]";
    }
}
