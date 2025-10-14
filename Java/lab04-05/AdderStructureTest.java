import check.*;
import static check.Use.*;

import module org.junit.jupiter;

@BeforeAll
public static void init() {
    Use.theClass("math.operation.textual.Adder")
       .that(hasUsualModifiers());
}

@Test
public void constructor() {
    it.hasConstructor(withArgsLikeAllFields())
      .that(hasUsualModifiers());
}

@Test
public void methodAddAsText() {
    it.hasMethod("addAsText", withParams("opTxt1: String", "opTxt2: String"))
      .that(hasUsualModifiers())
      .thatReturns("String", "Az összeg szövegként, vagy 'Operands are not numbers', ha bármelyik bemenet érvénytelen.")
      .info("Két szövegesen megadott számot ad össze; érvényes bemenet esetén az összeg szövegét adja vissza.")
      .info("Nem számszerű bemenet esetén a visszatérési érték: 'Operands are not numbers'.")
      .info("@link https://download.java.net/java/early_access/jdk25/docs/api/java.base/java/lang/NumberFormatException.html")
      .testWith(testCase("wrongInput"), "nem számszerű szövegek", "egyik szám + másik nem szám")
      .testWith(testCase("addZero"), "nullát adjunk a számhoz (balról és jobbról)", "0.0 hozzáadása egy egészhez")
      .testWith(testCase("add"), "két szám összeadása elvárt eredménnyel", "próbáljuk: int+int, double+double, int+double", "felcserélt sorrendben is")
      .testWith(testCase("addCommutative"), "azonos bemenetek mindkét sorrendben", "az eredmények egyezzenek");
}

void main() {}


