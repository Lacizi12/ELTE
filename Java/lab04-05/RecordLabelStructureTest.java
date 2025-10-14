import check.*;
import static check.Use.*;

import module org.junit.jupiter;

@BeforeAll
public static void init() {
    Use.theClass("music.recording.RecordLabel")
       .that(hasUsualModifiers());
}

@Test
public void fieldName() {
    it.hasField("name: String")
      .thatIs(INSTANCE_LEVEL, FULLY_IMPLEMENTED, NOT_MODIFIABLE, VISIBLE_TO_NONE)
      .thatHas(GETTER)
      .thatHasNo(SETTER);
}

@Test
public void fieldCash() {
    it.hasField("cash: int")
      .that(hasUsualModifiers())
      .thatHas(GETTER)
      .thatHasNo(SETTER);
}

@Test
public void constructor() {
    it.hasConstructor(withParams("name: String", "netWorth: int"))
      .that(hasUsualModifiers());
}

@Test
public void methodGotIncome() {
    it.hasMethod("gotIncome", withParams("sum: int"))
      .that(hasUsualModifiers())
      .thatReturnsNothing();
}

void main() {}


