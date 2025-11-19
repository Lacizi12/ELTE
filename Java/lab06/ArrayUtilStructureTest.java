import check.*;
import static check.Use.*;

import module org.junit.jupiter;

@BeforeAll
public static void init() {
    // usedLang = Lang.EN; // uncomment to enforce the message language
    Use.theClass("array.util.ArrayUtil")
       .that(hasUsualModifiers());
}

@Test
public void constructor() {
    it.hasConstructor(withNoParams())
      .that(hasUsualModifiers());
}

@Test
public void methodMax() {
    it.hasMethod("max", withParams("array: array of int"))
      .thatIs(USABLE_WITHOUT_INSTANCE, FULLY_IMPLEMENTED, MODIFIABLE, VISIBLE_TO_ALL)
      .thatReturns("int", "üres tömb maximuma legyen 0")
      .info("a tömb elemeit indexelve járjuk be a tömböt egyszerű maximumkiválasztással")
      .info("a max.kiv. átmeneti maximuma az `Integer.MIN_VALUE` értékről induljon")
      .info("if segítségével vizsgáld mindig, hogy az aktuális elem az átmeneti maximumánál nagyobb-e")
      .testWith(testCase("maxLength0"), "üres tömb maximuma", "mind a négy metódust próbáljuk ki")
      .testWith(testCase("maxLength1"), "egyelemű tömböt tesztel", "0, 1, nagy értékek, negatív értékek")
      .testWith(testCase("maxLength2"), """
          kételemű tömböt tesztel: egy min és egy max értéket használ;
          a négy metódust próbáljuk ki úgy, hogy az értékek min, max sorrendben szerepelnek a tömbben, és úgy is, hogy max, min sorrendben
      """);
}

@Test
public void methodMax2() {
    it.hasMethod("max2", withParams("array: array of int"))
      .thatIs(USABLE_WITHOUT_INSTANCE, FULLY_IMPLEMENTED, MODIFIABLE, VISIBLE_TO_ALL)
      .thatReturns("int", "mint `max`")
      .info("mint `max`, de a csere a ?: operátorral történjen")
      .testWith(testCase("maxLength{0,1,2}"), "ugyanazok a tesztek, mint `max`-nál");
}

@Test
public void methodMax3() {
    it.hasMethod("max3", withParams("array: array of int"))
      .thatIs(USABLE_WITHOUT_INSTANCE, FULLY_IMPLEMENTED, MODIFIABLE, VISIBLE_TO_ALL)
      .thatReturns("int", "még mindig ugyanaz, mint `max`")
      .info("mint `max2`, de a csere a Math.max() hívással történjen")
      .testWith(testCase("maxLength{0,1,2}"), "még mindig ugyanazok...");
}

@Test
public void methodMax4() {
    it.hasMethod("max4", withParams("array: array of int"))
      .thatIs(USABLE_WITHOUT_INSTANCE, FULLY_IMPLEMENTED, MODIFIABLE, VISIBLE_TO_ALL)
      .thatReturns("int", "még mindig...")
      .info("mint `max3`, de a ciklus bejáró legyen, ne indexelő")
      .testWith(testCase("maxLength{0,1,2}"), "és még mindig ugyanazok...");
}

@Test
public void methodMinMax() {
    it.hasMethod("minMax", withParams("array: array of int"))
      .thatIs(USABLE_WITHOUT_INSTANCE, FULLY_IMPLEMENTED, MODIFIABLE, VISIBLE_TO_ALL)
      .thatReturns("array of int", "üres tömb esetén egy [0, 0] tömböt adjunk vissza")
      .info("a `minMax()` metódus a tömb minimumát és maximumát egy kételemű tömbben adja ki")
      .info("a megvalósítás a maximum/minimum meghatározására a fenti lehetőségek közül bármelyiket használhatja")
      .info("tipp: szükség lesz az `Integer.MAX_VALUE` értékre")
      .testWith(testCase("maxLength{0,1,2}"), "hasonlóak a tesztek, mint `max`-nál");
}

void main() {}


