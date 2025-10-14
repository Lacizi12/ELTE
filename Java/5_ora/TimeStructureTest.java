import check.*;
import static check.Use.*;

import module org.junit.jupiter;

@BeforeAll
public static void init() {
    Use.theClass("time.Time")
       .that(hasUsualModifiers())
       .info("Shows the time of day reasonably, e.g. 12:34.");
}

@Test
public void fieldHour() {
    it.hasField("hour: int")
      .that(hasUsualModifiers())
      .thatHas(GETTER, SETTER);
}

@Test
public void fieldMin() {
    it.hasField("min: int")
      .that(hasUsualModifiers())
      .thatHas(GETTER, SETTER);
}

@Test
public void constructor() {
    it.hasConstructor(withArgsLikeAllFields())
      .that(hasUsualModifiers())
      .info("For the sake of simplicity, we assume that we get a valid time.");
}

@Test
public void methodGetEarlier() {
    it.hasMethod("getEarlier", withParams("that: Time"))
      .that(hasUsualModifiers())
      .thatReturns("Time", "Our time or `that`, whichever is earlier in the day.")
      .testWith(testCase("testEarlier"), "01:02 vs 12:34", "01:59 vs 12:34", "23:59 vs 12:34");
}

void main() {}


