import check.*;
import static check.Use.*;

import module org.junit.jupiter;

@BeforeAll
public static void init() {
    // usedLang = Lang.EN; // uncomment to enforce the message language
    Use.theClass("farm.field.Farm")
       .that(hasUsualModifiers())
       .info("Egy `Plot`-okból álló 2D-s tömböt kezel. A parcella vagy üres, vagy egy `Crop` terem rajta.");
}

@Test
public void fieldField() {
    it.hasField("field: array of array of Plot")
      .thatIs(INSTANCE_LEVEL, FULLY_IMPLEMENTED, NOT_MODIFIABLE, VISIBLE_TO_NONE)
      .thatHas(GETTER)
      .thatHasNo(SETTER);
}

@Test
public void constructor() {
    it.hasConstructor(withParams("rows: int", "cols: int"))
      .that(hasUsualModifiers())
      .thatThrows("IllegalArgumentException", "Ha `rows` vagy `columns` értéke 1 alatti.")
      .info("Feltölti a `field` tömböt új parcellákal.");
}

@Test
public void methodSimulateDay() {
    it.hasMethod("simulateDay", withNoParams())
      .that(hasUsualModifiers())
      .thatReturnsNothing()
      .info("Szimulálja egy nap elteltét az összes parcella számára.");
}

@Test
public void methodFindMostThirstyCrop() {
    it.hasMethod("findMostThirstyCrop", withNoParams())
      .that(hasUsualModifiers())
      .thatReturns("farm.crop.Crop", "`null`, ha nincsen egyetlen életben maradt növény sem. Különben a legalacsonyabb vízszintű növény.");
}

@Test
public void text() {
    it.has(TEXTUAL_REPRESENTATION)
      .info("""
          A növények nevei sorokban és oszlopokban. Példák:
          E E E          S D E
          E E E   vagy   L E C   .....
          E E E          C S L
      """);
}

void main() {}


