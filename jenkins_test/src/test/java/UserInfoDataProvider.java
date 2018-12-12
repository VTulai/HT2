import org.testng.annotations.DataProvider;

public class UserInfoDataProvider {
    @DataProvider(name = "adminSignInData")
    public static Object[][] adminSignInData() {
        return new Object[][] {
                {"Admin", "admin"}
        };
    }

    @DataProvider(name = "createUserInfo")
    public static Object[][] createUserData() {
        return new Object[][] {
                {"someuser", "somepassword", "somepassword", "Some Full Name", "some@addr.dom"}
        };
    }

    @DataProvider(name = "deleteUserInfo")
    public static Object[][] deleteUserData() {
        return new Object[][] {
                {"someuser"}
        };
    }
}
