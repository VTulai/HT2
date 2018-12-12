import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TestJ {

    String base_url = "http://localhost:8080/";
    WebDriver driver = null;
    PageObjectSample pos;

    @BeforeClass
    public void beforeClass() throws Exception {
        System.setProperty("webdriver.chrome.driver", "D:/Studies/БГУ/Java/GoogleDriver/chromedriver.exe");
        driver = new ChromeDriver();
        driver.get(base_url);
        pos = PageFactory.initElements(driver, PageObjectSample.class);
    }

    @AfterClass
    public void afterClass()
    {
        driver.quit();
    }

    @Test(dataProvider = "adminSignInData", dataProviderClass = UserInfoDataProvider.class)
    public void testSignIn(String username, String password) {
        pos.signIn(username, password);
        Assert.assertEquals(driver.getTitle(), "Dashboard [Jenkins]");
    }

    @Test(dependsOnMethods = {"testSignIn"})
    public void testManage(){
        pos.click_manage_jenkins_link();
        Assert.assertEquals(driver.getTitle(), "Manage Jenkins [Jenkins]");
        Assert.assertEquals(pos.getManageUsersDtText(), "Manage Users");
        Assert.assertEquals(pos.getManageUsersDdText(), "Create/delete/modify users that can log in to this Jenkins");
        pos.click_manage_users_link();
        Assert.assertTrue(pos.isAccessibleCreateUser(), "Create user link is not accessible");
    }

    @Test(dataProvider = "createUserData", dataProviderClass = UserInfoDataProvider.class, dependsOnMethods = {"testSignIn", "testManage"})
    public void testCreateUser(String username, String password, String confirmPassword, String fullName, String email){
        pos.click_create_user_link();
        Assert.assertTrue(pos.isCreateUserTableValid());
        pos.fillCreateUserFields(username, password, confirmPassword, fullName, email);
        pos.click_create_user_button();
        Assert.assertEquals(pos.getUserIDText(username), username);
    }

    @Test(dataProvider = "deleteUserData", dataProviderClass = UserInfoDataProvider.class, dependsOnMethods = {"testSignIn", "testManage"})
    public void testDeleteUser(String username){
        pos.click_delete_user_button(username);
        Assert.assertTrue(pos.getDeleteUserText("Are you sure about deleting the user from Jenkins?"),
                "No such text after deleting user");
        pos.click_delete_user_confirm_button();
        Assert.assertFalse(pos.isUserInTable(username), "User hasn't been deleted");
        Assert.assertFalse(pos.isAdminDeleteButtonExists());
    }

    @Test(dependsOnMethods = {"testSignIn"})
    public void testEnableDisableAutoRefresh(){
        if(pos.isEnableAutoRefreshLinkExists()) {
            pos.click_enable_auto_refresh_link();
            Assert.assertTrue(pos.isDisableAutoRefreshLinkExists(), "Can't find disable_auto_refresh_link");
            pos.click_disable_auto_refresh_link();
            Assert.assertTrue(pos.isEnableAutoRefreshLinkExists(), "Can't find enable_auto_refresh_link");
        } else {
            pos.click_disable_auto_refresh_link();
            Assert.assertTrue(pos.isEnableAutoRefreshLinkExists(), "Can't find enable_auto_refresh_link");
            pos.click_enable_auto_refresh_link();
            Assert.assertTrue(pos.isDisableAutoRefreshLinkExists(), "Can't find disable_auto_refresh_link");
        }

    }

}