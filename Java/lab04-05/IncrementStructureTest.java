import check.*;
import static check.Use.*;

import module org.junit.jupiter;

@BeforeAll
public static void init() {
    Use.theClass("math.operation.safe.Increment")
       .that(hasUsualModifiers())
       .info("Biztonságos növelő művelet egész számokra, amely megakadályozza a túlcsordulást.")
       .info("Ha a bemenet Integer.MAX_VALUE, akkor az Integer.MAX_VALUE értéket adja vissza (túlcsordulás nélkül).")
       .info("Hasznos olyan esetekben, amikor el kell kerülni az egész számok túlcsordulását.");
}

@Test
public void constructor() {
    it.hasConstructor(withArgsLikeAllFields())
      .that(hasUsualModifiers());
}

@Test
public void methodIncrement() {
    it.hasMethod("increment", withParams("num: int"))
      .thatIs(USABLE_WITHOUT_INSTANCE, FULLY_IMPLEMENTED, MODIFIABLE, VISIBLE_TO_ALL)
      .thatReturns("int", "int")
      .info("Növeli a megadott egész számot eggyel, kivéve, ha az már Integer.MAX_VALUE.")
      .info("@param num: int")
      .info("@param num Az egész szám, amelyet növelni kell.")
      .info("@link https://docs.oracle.com/javase/8/docs/api/java/lang/Integer.html#MAX_VALUE")
      .testWith(testCase("incrementNormal"), "normál egész szám növelése (pl. 5 → 6)")
      .testWith(testCase("incrementMaxValue"), "bemenet Integer.MAX_VALUE", "az eredmény is Integer.MAX_VALUE");
}

void main() {}


