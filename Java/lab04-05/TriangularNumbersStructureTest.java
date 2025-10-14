import check.*;
import static check.Use.*;

import module org.junit.jupiter;

@BeforeAll
public static void init() {
    Use.theClass("famous.sequence.TriangularNumbers")
       .that(hasUsualModifiers());
}

@Test
public void constructor() {
    it.hasConstructor(withArgsLikeAllFields())
      .that(hasUsualModifiers());
}

@Test
public void methodGetTriangularNumber() {
    it.hasMethod("getTriangularNumber", withParams("n: int"))
      .thatIs(USABLE_WITHOUT_INSTANCE, FULLY_IMPLEMENTED, MODIFIABLE, VISIBLE_TO_ALL)
      .thatReturns("int", "Az `n`edik háromszögszám: 1+2+...+n.")
      .info("@link https://en.wikipedia.org/wiki/Triangular_number")
      .testWith(testCase("singleValues"), "0", "1", "-1", "-100")
      .testWith(testCase("Gauss"), """
          A szám, ami a fiatal Gauss anekdotájában szerepel.
          @see https://hu.wikipedia.org/wiki/Carl_Friedrich_Gauss#Fiatalkora
      """);
}

@Test
public void methodGetTriangularNumberAlternative() {
    it.hasMethod("getTriangularNumberAlternative", withParams("n: int"))
      .thatIs(USABLE_WITHOUT_INSTANCE, FULLY_IMPLEMENTED, MODIFIABLE, VISIBLE_TO_ALL)
      .thatReturns("int")
      .info("Ebben a megvalósításban a zárt képletet kell használni.")
      .info("Negatív bemenetre adjon nulla eredményt a metódus.")
      .testWith(testCase("singleValuesAlt"), "bemenet mint korábban", "Gauss híres számára is");
}

void main() {}


