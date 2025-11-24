import check.*;
import static check.Use.*;

import module org.junit.jupiter;

@BeforeAll
public static void init() {
    Use.theClass("famous.sequence.Fibonacci")
       .that(hasUsualModifiers());
}

@Test
public void constructor() {
    it.hasConstructor(withArgsLikeAllFields())
      .that(hasUsualModifiers());
}

@Test
public void methodFib() {
    it.hasMethod("fib", withParams("n: int"))
      .thatIs(USABLE_WITHOUT_INSTANCE, FULLY_IMPLEMENTED, MODIFIABLE, VISIBLE_TO_ALL)
      .thatReturns("int")
      .info("""
          Az `n`edik Fibonacci-számot adja ki.
          A megvalósítás a "szokásos", a metódus rekurzívan hívja önmagát.
          Feltételezzük, hogy csak kis, nemnegatív számokkal hívják.
          Lásd https://hu.wikipedia.org/wiki/Fibonacci-számok
      """)
      .info("A tesztelő kód ugyanebbe a csomagba, a `FibonacciTest` osztályba kerüljön.");
}

void main() {}


