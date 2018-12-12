import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PageObjectSample {

    private String base_url;
    private WebDriverWait wait;
    private final WebDriver driver;
    @FindBy(xpath = "//body")
    private WebElement body;
    @FindBy(xpath = "//form[@action='/securityRealm/addUser']")
    private WebElement form_create_user;
    @FindBy(name = "j_username")
    private WebElement username;
    @FindBy(name = "j_password")
    private WebElement password;
    @FindBy(name = "Submit")
    private WebElement submit_button;
    @FindBy(xpath = "//a[@href='/manage'][@class='task-link']")
    private WebElement manage_jenkins_link;
    @FindBy(xpath = "//a[@href='securityRealm/'][@title='Manage Users']")
    private WebElement manage_users_link;
    @FindBy(xpath = "//a[@href='securityRealm/'][@title='Manage Users']/dl/dt")
    private WebElement manage_users_dt;
    @FindBy(xpath = "//a[@href='securityRealm/'][@title='Manage Users']/dl/dd[1]")
    private WebElement manage_users_dd;
    @FindBy(xpath = "//a[@href='addUser'][@class='task-link']")
    private WebElement create_user_link;
    @FindBy(xpath = "//button[@id='yui-gen2-button']")
    private WebElement create_user_button;
    @FindBy(xpath = "//input[@name='username']")
    private WebElement create_user_username_field;
    @FindBy(xpath = "//input[@name='password1']")
    private WebElement create_user_password_field;
    @FindBy(xpath = "//input[@name='password2']")
    private WebElement create_user_confirm_password_field;
    @FindBy(xpath = "//input[@name='fullname']")
    private WebElement create_user_full_name_field;
    @FindBy(xpath = "//input[@name='email']")
    private WebElement create_user_email_field;
    @FindBy(xpath = "//table[@id='people']/descendant::a[text()='someuser']")
    private WebElement userID_new_username_field;
    @FindBy(xpath = "//form[@name='delete']")
    private WebElement delete_user_text;
    @FindBy(xpath = "//button[@id='yui-gen2-button'][text()='Yes']")
    private WebElement delete_user_confirm_button;
    @FindBy(xpath = "//a[@href='?auto_refresh=true']")
    private WebElement enable_auto_refresh_link;
    @FindBy(xpath = "//a[@href='?auto_refresh=false']")
    private WebElement disable_auto_refresh_link;

    public PageObjectSample(WebDriver driver) {
        base_url = "http://localhost:8080";
        this.driver = driver;
        this.wait = new WebDriverWait(this.driver, 100);
        //Провекрка того факта, что мы на верной странице.
        if ((!driver.getTitle().equals("Sign in [Jenkins]")) || (!driver.getCurrentUrl().equals(base_url + "/login?from=%2F"))) {
            throw new IllegalStateException("Wrong site page!");
        }
    }

    //Вход в систему
    public void signIn(String username, String password) {
        setUsername(username);
        setPassword(password);
        submit_button.click();
    }

    //Заполнение полей для создания нового пользователя
    public void fillCreateUserFields(String username, String password, String confPassword, String fullName, String email){
        setCreateUserUsername(username);
        setCreateUserPassword(password);
        setCreateUserConfirmPassword(confPassword);
        setCreateUserFullName(fullName);
        setCreateUserEmail(email);
    }

    //setters
    public PageObjectSample setUsername(String name) {
        username.clear();
        username.sendKeys(name);
        return this;
    }

    public PageObjectSample setPassword(String password) {
        this.password.clear();
        this.password.sendKeys(password);
        return this;
    }

    public PageObjectSample setCreateUserUsername(String password) {
        create_user_username_field.clear();
        create_user_username_field.sendKeys(password);
        return this;
    }

    public PageObjectSample setCreateUserPassword(String password) {
        create_user_password_field.clear();
        create_user_password_field.sendKeys(password);
        return this;
    }

    public PageObjectSample setCreateUserConfirmPassword(String password) {
        create_user_confirm_password_field.clear();
        create_user_confirm_password_field.sendKeys(password);
        return this;
    }

    public PageObjectSample setCreateUserFullName(String password) {
        create_user_full_name_field.clear();
        create_user_full_name_field.sendKeys(password);
        return this;
    }

    public PageObjectSample setCreateUserEmail(String password) {
        create_user_email_field.clear();
        create_user_email_field.sendKeys(password);
        return this;
    }

    //getters
    public String getManageUsersDtText() {
        return manage_users_dt.getText();
    }

    public String getManageUsersDdText() {
        return manage_users_dd.getText();
    }

    public boolean getDeleteUserText(String text) {
        return delete_user_text.getText().contains(text);
    }

    public String getUserIDText(String username){
        if (isUserInTable(username)){
            return driver.findElement(By.xpath("//table[@id='people']/descendant::a[text()='" + username + "']")).getText();
        } else{
            throw new NoSuchElementException("There is no such user");
        }
    }

    //clickers
    public void click_manage_jenkins_link() {
        manage_jenkins_link.click();
    }

    public void click_manage_users_link() {
        manage_users_link.click();
    }

    public void click_create_user_link(){
        create_user_link.click();
    }

    public void click_create_user_button(){
        create_user_button.click();
    }

    public void click_delete_user_button(String username){
        if (isUserInTable(username)){
            driver.findElement(By.xpath("//a[@href='user/" + username + "/delete']")).click();
        } else {
            throw new NoSuchElementException("There is no such user");
        }
    }

    public void click_delete_user_confirm_button(){
        delete_user_confirm_button.click();
    }

    public void click_enable_auto_refresh_link(){
        enable_auto_refresh_link.click();
    }

    public void click_disable_auto_refresh_link(){
        disable_auto_refresh_link.click();
    }

    //checkers
    public boolean isAdminDeleteButtonExists() {
        try{
            driver.findElement(By.xpath("//a[@href='user/admin/delete']"));
        } catch (NoSuchElementException a) {
            return false;
        }
        return true;
    }

    public boolean isUserInTable(String username) {
        try{
            driver.findElement(By.xpath("//table[@id='people']/descendant::a[text()='" + username + "']"));
        } catch (NoSuchElementException a) {
            return false;
        }
        return true;
    }

    public boolean isCreateUserTableValid(){
        return (create_user_username_field.getText().isEmpty()) &&
                (create_user_password_field.getText().isEmpty()) &&
                (create_user_confirm_password_field.getText().isEmpty()) &&
                (create_user_full_name_field.getText().isEmpty()) &&
                (create_user_email_field.getText().isEmpty()) &&
                (driver.findElements(By.xpath("//input[@type='text']")).size()==3) &&
                (driver.findElements(By.xpath("//input[@type='password']")).size()==2);
    }

    public boolean isAccessibleCreateUser() {
        return create_user_link.isEnabled();
    }

    public boolean isEnableAutoRefreshLinkExists() {
        return (enable_auto_refresh_link.isEnabled()) && (enable_auto_refresh_link.getText().contains("ENABLE AUTO REFRESH"));
    }

    public boolean isDisableAutoRefreshLinkExists() {
        return (disable_auto_refresh_link.isEnabled()) && (disable_auto_refresh_link.getText().contains("DISABLE AUTO REFRESH"));
    }
}

