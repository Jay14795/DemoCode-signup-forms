package signup;

import factories.UserFactory;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.AccountSuccessPage;
import pages.RegistrationPage;

import java.util.HashMap;
import java.util.Map;

public class SignupServerSideValidationsTests {
    private WebDriver driver;
    private RegistrationPage registrationPage;
    private AccountSuccessPage accountSuccessPage;

    @BeforeAll
    public static void setUpClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setUp() {
        var options = new ChromeOptions();
        Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("profile.managed_default_content_settings.javascript", 2);
        options.setExperimentalOption("prefs", prefs);

        driver = new ChromeDriver(options);
        registrationPage = new RegistrationPage(driver);
        accountSuccessPage = new AccountSuccessPage(driver);
    }

    // validations
    @Test
    public void privacyPolicyNotCheckedValidationDisplayed_when_notAgree() {
        var user = UserFactory.createDefault();
        user.setAgreePrivacyPolicy(false);

        registrationPage.open();
        registrationPage.register(user, false);

        registrationPage.assertPrivacyPolicyAgreementValidation();
    }

    @Test
    public void firstNameValidationDisplayed_when_emptyFirstName() {
        var user = UserFactory.createDefault();
        user.setFirstName("");

        registrationPage.open();
        registrationPage.register(user, false);

        registrationPage.assertFirstNameValidation();
    }

    @Test
    public void firstNameValidationDisplayed_when_firstName33Characters() {
        var user = UserFactory.createDefault();
        user.setFirstName(StringUtils.repeat("AAA", 33));

        registrationPage.open();
        registrationPage.register(user, false);

        registrationPage.assertFirstNameValidation();
    }

    @Test
    public void lastNameValidationDisplayed_when_emptyLastName() {
        var user = UserFactory.createDefault();
        user.setLastName("");

        registrationPage.open();
        registrationPage.register(user, false);

        registrationPage.assertLastNameValidation();
    }

    @Test
    public void lastNameValidationDisplayed_when_lastName33Characters() {
        var user = UserFactory.createDefault();
        user.setLastName(StringUtils.repeat("AAA", 33));

        registrationPage.open();
        registrationPage.register(user, false);

        registrationPage.assertLastNameValidation();
    }

    @Test
    public void emailValidationDisplayed_when_emptyEmail() {
        var user = UserFactory.createDefault();
        user.setEmail("");

        registrationPage.open();
        registrationPage.register(user, false);

        registrationPage.assertEmailValidation();
    }

    @Test
    public void emailValidationDisplayed_when_email33Characters() {
        var user = UserFactory.createDefault();
        user.setEmail("aa@" + StringUtils.repeat("A", 26) + ".com");

        registrationPage.open();
        registrationPage.register(user, false);

        registrationPage.assertEmailValidation();
    }

    @Test
    public void emailValidationDisplayed_when_incorrectEmailSet() {
        var user = UserFactory.createDefault();
        user.setEmail("aaaa");

        registrationPage.open();
        registrationPage.register(user, false);

        registrationPage.assertEmailValidation();
    }

    @Test
    public void telephoneValidationDisplayed_when_emptyTelephone() {
        var user = UserFactory.createDefault();
        user.setTelephone("");

        registrationPage.open();
        registrationPage.register(user, false);

        registrationPage.assertLastNameValidation();
    }

    @Test
    public void telephoneValidationDisplayed_when_telephone33Characters() {
        var user = UserFactory.createDefault();
        user.setTelephone(StringUtils.repeat("3", 33));

        registrationPage.open();
        registrationPage.register(user, false);

        registrationPage.assertLastNameValidation();
    }

    @Test
    public void passwordDisplayedEncrypted_when_typePassword() {
        var user = UserFactory.createDefault();

        registrationPage.open();

        Assertions.assertEquals("password", registrationPage.passwordInput().getAttribute("type"));
    }

    @Test
    public void passwordConfirmDisplayedEncrypted_when_typePassword() {
        var user = UserFactory.createDefault();

        registrationPage.open();

        Assertions.assertEquals("password", registrationPage.passwordConfirmInput().getAttribute("type"));
    }

    @AfterEach
    public void tearDown() {

        if (driver != null) {
            driver.quit();
        }
    }
}
