import check.*;
import static check.Use.*;

import module org.junit.jupiter;

@BeforeAll
public static void init() {
    // usedLang = Lang.EN; // uncomment to enforce the message language
    Use.withTypeArg("T")
       .theClass("data.organiser.Organiser")
       .that(hasUsualModifiers());
}

@Test
public void fieldElems() {
    it.hasField("elems: ArrayList of T")
      .that(hasUsualModifiers())
      .thatHasNo(GETTER, SETTER);
}

@Test
public void fieldSwaps() {
    it.hasField("swaps: ArrayList of Map.Entry of Integer to Integer")
      .that(hasUsualModifiers())
      .thatHasNo(GETTER, SETTER);
}

@Test
public void constructor() {
    it.hasConstructor(withParams("elems: array of T"))
      .that(hasUsualModifiers());
}

@Test
public void methodGet01() {
    it.hasMethod("get", withParams("idx: int"))
      .that(hasUsualModifiers())
      .thatReturns("T");
}

@Test
public void methodGet02() {
    it.hasMethod("get", withNoParams())
      .that(hasUsualModifiers())
      .thatReturns("ArrayList of T");
}

@Test
public void methodAddSwap() {
    it.hasMethod("addSwap", withParams("from: int", "to: int"))
      .that(hasUsualModifiers())
      .thatReturnsNothing();
}

@Test
public void methodSwap() {
    it.hasMethod("swap", withParams("from: int", "to: int"))
      .thatIs(INSTANCE_LEVEL, FULLY_IMPLEMENTED, MODIFIABLE, VISIBLE_TO_NONE)
      .thatReturnsNothing();
}

@Test
public void methodRunSwaps() {
    it.hasMethod("runSwaps", withNoParams())
      .that(hasUsualModifiers())
      .thatReturnsNothing();
}

@Test
public void text() {
    it.has(TEXTUAL_REPRESENTATION);
}

void main() {}


