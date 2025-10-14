import check.*;
import static check.Use.*;

import module org.junit.jupiter;

@BeforeAll
public static void init() {
    Use.theClass("cipher.CaesarCipher")
       .that(hasUsualModifiers());
}

@Test
public void fieldShift() {
    it.hasField("shift: int")
      .that(hasUsualModifiers())
      .thatHasNo(GETTER, SETTER);
}

@Test
public void constructor() {
    it.hasConstructor(withArgsLikeAllFields())
      .that(hasUsualModifiers())
      .info("""
          Létrehoz egy Caesar-kódolót és eltárolja a megadott eltolási távolságot.
          Ez az érték adja meg, hogy hány pozícióval tolódnak el a betűk az ábécében.
          Pozitív érték előre, negatív érték hátrafelé tol.
      """)
      .info("@link https://en.wikipedia.org/wiki/Caesar_cipher");
}

@Test
public void methodEncrypt() {
    it.hasMethod("encrypt", withParams("text: String"))
      .that(hasUsualModifiers())
      .thatReturns("String", "A titkosított szöveg.")
      .info("""
          A megadott szöveget titkosítja Caesar-kódolás segítségével a beállított eltolással.
          Csak a kisbetűk módosulnak, minden más karakter változatlan marad.
      """)
      .info("@param text A titkosítandó szöveg.")
      .testWith(testCase("noShift"), "Az eltolás=0 esetben a szöveg maradjon változatlan.")
      .testWith(testCase("encryptBy"), "Példák eltolás=1 vagy eltolás=2 esetén, csak a kisbetűk módosuljanak.");
}

@Test
public void methodDecrypt() {
    it.hasMethod("decrypt", withParams("text: String"))
      .that(hasUsualModifiers())
      .thatReturns("String", "A visszafejtett szöveg.")
      .info("Visszafejti a megadott szöveget, az `encrypt` ellentéte.")
      .info("@param text A visszafejtendő szöveg.")
      .testWith(testCase("noShift"), "Ismét, eltolás=0 esetben a szöveg maradjon változatlan.")
      .testWith(testCase("inverseOperation"), "Két, egymással ellentétes hatású művelet egymás után meghívása, az eredmény az eredeti szöveg legyen.");
}

void main() {}


