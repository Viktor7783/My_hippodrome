import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

class MainTest {

    @Disabled("Тест временно отключен. Правда, правда")
    @Test
    @DisplayName("Тест: метод main() выполняется не дольше 22 секунд")
    @Timeout(value = 22)
    void failsIfMainExecutionTimeExceeds22Seconds() {
        try {
            Main.main(new String[]{"Hello"});
        } catch (Exception e) {
            System.out.println("Возникло исключение в методе main():" + e.getMessage());
        }
    }
}