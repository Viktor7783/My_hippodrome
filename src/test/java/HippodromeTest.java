import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Тест Ипподрома")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HippodromeTest {
    private Exception exception;
    Hippodrome hippodrome;
    List<Horse> horses;

    @Test
    @Order(1)
    @DisplayName("Тест: при передаче в конструктор null, будет выброшено IllegalArgumentException")
    void shouldThrowIllegalArgumentExceptionIfConstructorGetNullArgument() {
        exception = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
    }

    @Test
    @Order(2)
    @DisplayName("Тест: при передаче в конструктор null, будет выброшено IllegalArgumentException")
    void shouldShowRightMessageWhenThrowIllegalArgumentExceptionNullArgument() {
        assertEquals("Horses cannot be null.", exception.getMessage());
    }

    @Test
    @Order(3)
    @DisplayName("Тест: при передаче в конструктор пустого списка, будет выброшено IllegalArgumentException")
    void shouldThrowIllegalArgumentExceptionIfConstructorGetEmptyArgument() {
        exception = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(new ArrayList<>()));
    }

    @Test
    @Order(4)
    @DisplayName("Тест: при передаче в конструктор null, будет выброшено IllegalArgumentException")
    void shouldShowRightMessageWhenThrowIllegalArgumentExceptionEmptyArgument() {
        assertEquals("Horses cannot be empty.", exception.getMessage());
    }

    @Test
    @Order(5)
    @DisplayName("Тест: метод getHorses() возвращает список, который содержит те же объекты и в той же последовательности, что и список который был передан в конструктор")
    void shouldReturnListContainsTheSameObjectsAsListWasPassedInConstructor() {
        horses = IntStream.range(1, 31).mapToObj(number -> new Horse(Horse.class.getSimpleName() + " " + number, number + 20, number + 40)).toList();
        hippodrome = new Hippodrome(horses);
        assertArrayEquals(horses.toArray(new Horse[0]), hippodrome.getHorses().toArray(new Horse[0]));
    }

    @Test
    @Order(6)
    @ExtendWith(MockitoExtension.class)
    @DisplayName("Тест: метод move() вызывает метод move() у всех лошадей")
    void moveMethodShouldCallsMoveMethodOnAllHorses() {
        hippodrome = new Hippodrome(IntStream.range(0, 50).mapToObj((n) -> mock(Horse.class)).toList());
        hippodrome.move();
        hippodrome.getHorses().forEach((h) -> verify(h).move());
    }

    @Test
    @Order(7)
    @DisplayName("Тест: метод getWinner() возвращает лошадь с самым большим значением distance")
    void getWinnerMethodReturnHorseWithMaxDistance() {
        Horse maxHorse = horses.get(horses.size() - 1);
        hippodrome = new Hippodrome(horses);
        assertEquals(maxHorse, hippodrome.getWinner());
    }
}