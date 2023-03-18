import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import java.time.Duration;
import static com.codeborne.selenide.Selenide.*;


class AuthTest {
    @BeforeEach
    public void setUp() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
    }

    @Test
    void activeUser() {
        AuthDto user = DataGenerator.user("active");
        $("[data-test-id='login'] input").val(user.getLogin());
        $("[data-test-id='password'] input").val(user.getPassword());
        $("[data-test-id='action-login']").click();
        $("div h2").shouldHave(Condition.text("Личный кабинет"));
    }

    @Test
    void blockedUser() {
        AuthDto user = DataGenerator.user("blocked");
        $("[data-test-id='login'] input").val(user.getLogin());
        $("[data-test-id='password'] input").val(user.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification']").shouldHave(Condition.text("Пользователь заблокирован"), Duration.ofSeconds(15));
    }

    @Test
    void incorrectLogin() {
        AuthDto user = DataGenerator.incorrectLoginUser();
        $("[data-test-id='login'] input").val(user.getLogin());
        $("[data-test-id='password'] input").val(user.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification']").shouldHave(Condition.text("Неверно указан логин или пароль"), Duration.ofSeconds(15));
    }

    @Test
    void incorrectPassword() {
        AuthDto user = DataGenerator.incorrectPasswordUser();
        $("[data-test-id='login'] input").val(user.getLogin());
        $("[data-test-id='password'] input").val(user.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification']").shouldHave(Condition.text("Неверно указан логин или пароль"), Duration.ofSeconds(15));
    }
}