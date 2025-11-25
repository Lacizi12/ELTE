package famous.sequence;

// Importok: Kell a sima teszthez és a paraméterezetthez is
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class FibonacciTest {

    // 1. A HAGYOMÁNYOS TESZT (Amit az előbb írtunk)
    @Test
    void testFib() {
        assertEquals(0, Fibonacci.fib(1)); // 1. elem = 0
        assertEquals(1, Fibonacci.fib(2)); // 2. elem = 1
        assertEquals(1, Fibonacci.fib(3));
        assertEquals(2, Fibonacci.fib(4));
        assertEquals(3, Fibonacci.fib(5));
        assertEquals(5, Fibonacci.fib(6));
    }

    // 2. A PARAMÉTEREZETT TESZT (A képről)
    // Ez ugyanazt csinálja, csak táblázatos formában
    @ParameterizedTest
    @CsvSource({
        "1, 0",  // input: 1, elvárt: 0
        "2, 1",  // input: 2, elvárt: 1
        "6, 5"   // input: 6, elvárt: 5
    })
    void testFibParams(int n, int expected) {
        assertEquals(expected, Fibonacci.fib(n));
    }
}