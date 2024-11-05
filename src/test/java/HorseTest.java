
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Лошадиный тест")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HorseTest {
    private Horse horse;
    private Exception exception;


    @Test
    @Order(1)
    @DisplayName("Тест: при передаче в конструктор 1 параметром null, будет выброшено IllegalArgumentException")
    void shouldThrowIllegalArgumentExceptionIfConstructorGetNullFirstArgument() {
        exception = assertThrows(IllegalArgumentException.class, () -> new Horse(null, 55, 55));
    }

    @Test
    @Order(2)
    @DisplayName("Тест: при передаче в конструктор 1 параметром null, выброшенное исключение будет содержать сообщение \"Name cannot be null\"")
    void shouldShowRightMessageWhenThrowIllegalArgumentExceptionNullFirstArgument() {
        assertEquals("Name cannot be null.", exception.getMessage());
    }

    @ParameterizedTest
    @Order(3)
    @DisplayName("Тест: при передаче в конструктор 1 параметром пустой строки/строки содержащей только пробельные символы => выброшено IllegalArgumentException")
    @ValueSource(strings = {"", "\s\s\s\s\s", "\t\t\t\t\t", "\n\n\n\n\n", "\r\r\r\r\r", "\f\f\f\f\f"})
    void shouldThrowIllegalArgumentExceptionIfConstructorGetEmptyFirstArgument(String argument) {
        exception = assertThrows(IllegalArgumentException.class, () -> new Horse(argument, 55, 55));
    }

    @Test
    @Order(4)
    @DisplayName("Тест: при передаче в конструктор 1 параметром пустой строки/строки содержащей только пробельные символы => выброшенное исключение будет содержать сообщение \"Name cannot be blank.\"")
    void shouldShowRightMessageWhenThrowIllegalArgumentExceptionEmptyFirstArgument() {
        assertEquals("Name cannot be blank.", exception.getMessage());
    }

    @Test
    @Order(5)
    @DisplayName("Тест: при передаче в конструктор 2 параметром отрицательного числа, будет выброшено IllegalArgumentException")
    void shouldThrowIllegalArgumentExceptionWhenGetNegativeSecondArgument() {
        exception = assertThrows(IllegalArgumentException.class, () -> new Horse("Августин", -1, 55));
    }

    @Test
    @Order(6)
    @DisplayName("Тест: при передаче в конструктор вторым параметром отрицательного числа, выброшенное исключение будет содержать сообщение \"Speed cannot be negative.\"")
    void shouldShowRightMessageWhenThrowIllegalArgumentExceptionWhenGetNegativeSecondArgument() {
        assertEquals("Speed cannot be negative.", exception.getMessage());
    }

    @Test
    @Order(7)
    @DisplayName("Тест: при передаче в конструктор третьим параметром отрицательного числа, будет выброшено IllegalArgumentException")
    void shouldThrowIllegalArgumentExceptionWhenGetNegativeThirdArgument() {
        exception = assertThrows(IllegalArgumentException.class, () -> new Horse("Августин", 55, -1));
    }

    @Test
    @Order(8)
    @DisplayName("Тест: при передаче в конструктор третьим параметром отрицательного числа, выброшенное исключение будет содержать сообщение \"Distance cannot be negative.\"")
    void shouldShowRightMessageWhenThrowIllegalArgumentExceptionWhenGetNegativeThirdArgument() {
        assertEquals("Distance cannot be negative.", exception.getMessage());
    }

    @Test
    @Order(9)
    @DisplayName("Тест: метод getName() возвращает строку, которая была передана первым параметром в конструктор")
    void shouldReturnNameFromConstructor() {
        String name = "Hero";
        horse = new Horse(name, 77, 77);
        assertEquals(name, horse.getName());
    }

    @Test
    @Order(10)
    @DisplayName("Тест: метод getSpeed() возвращает число, которое было передано вторым параметром в конструктор")
    void shouldReturnSpeedFromConstructor() {
        int speed = 77;
        horse = new Horse("Honey", speed, 33);
        assertEquals(speed, horse.getSpeed());
    }

    @Test
    @Order(11)
    @DisplayName("Тест: метод getDistance() возвращает число, которое было передано третьим параметром в конструктор;")
    void shouldReturnDistanceFromConstructor() {
        int distance = 77;
        horse = new Horse("Honey", 33, distance);
        assertEquals(distance, horse.getDistance());
    }

    @Test
    @Order(12)
    @DisplayName("Тест: метод getDistance() возвращает ноль, если объект был создан с помощью конструктора с двумя параметрами")
    void shouldReturnZeroFromConstructor() {
        horse = new Horse("Pony", 22);
        assertEquals(0, horse.getDistance());
    }

    @ParameterizedTest
    @Order(13)
    @DisplayName("Тест: метод move() вызывает внутри метод getRandomDouble с параметрами 0.2 и 0.9.")
    @CsvSource("Movie, 33, 100")
    void moveShouldRightCalculateAndCallGetRandomDoubleWithRightArguments(String name, double speed, double distance) {
        horse = new Horse(name, speed, distance);
        try (MockedStatic<Horse> mockHorse = mockStatic(Horse.class)) {
            horse.move();
            mockHorse.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @ParameterizedTest
    @Order(14)
    @DisplayName("Тест: метод move() присваивает дистанции значение высчитанное по формуле: distance + speed * getRandomDouble(0.2, 0.9)")
    @CsvSource("Dummy, 33, 100, 0.3, 0.2, 0.9")
    void moveShouldCountDistanceWithRightFormula(String name, double speed, double distance, double random, double first, double second) {
        horse = new Horse(name, speed, distance);
        try (MockedStatic<Horse> mockHorse = mockStatic(Horse.class)) {
            double expectedDistance = horse.getDistance() + speed * random;
            mockHorse.when(() -> Horse.getRandomDouble(first, second)).thenReturn(random);
            horse.move();
            assertEquals(expectedDistance, horse.getDistance());
        }
    }
}