import check.*;
import static check.Use.*;

import module org.junit.jupiter;

@BeforeAll
public static void init() {
    // usedLang = Lang.EN; // uncomment to enforce the message language
    Use.theClass("race.car.SectorTimer")
       .that(hasUsualModifiers());
}

@Test
public void fieldSectorTimes() {
    it.hasField("sectorTimes: array of int")
      .that(hasUsualModifiers())
      .thatHas(GETTER)
      .thatHasNo(SETTER);
}

@Test
public void constructor01() {
    it.hasConstructor(withArgsLikeAllFields())
      .that(hasUsualModifiers());
}

@Test
public void constructor02() {
    it.hasConstructor(withParams("fake: boolean", "sectorTimes: array of int"))
      .that(hasUsualModifiers());
}

@Test
public void methodSetLapTimes() {
    it.hasMethod("setLapTimes", withArgsLikeAllFields())
      .that(hasUsualModifiers())
      .thatReturnsNothing();
}

@Test
public void methodInitSectorTimes() {
    it.hasMethod("initSectorTimes", withArgsLikeAllFields())
      .thatIs(INSTANCE_LEVEL, FULLY_IMPLEMENTED, MODIFIABLE, VISIBLE_TO_NONE)
      .thatReturnsNothing();
}

@Test
public void methodGetSectorTimesV2() {
    it.hasMethod("getSectorTimesV2", withNoParams())
      .that(hasUsualModifiers())
      .thatReturns("array of int");
}

@Test
public void methodSetSectorTimesV2() {
    it.hasMethod("setSectorTimesV2", withParams("lapTimes: array of int"))
      .that(hasUsualModifiers())
      .thatReturnsNothing();
}

@Test
public void methodGetSectorTime() {
    it.hasMethod("getSectorTime", withParams("idx: int"))
      .that(hasUsualModifiers())
      .thatReturns("int");
}

void main() {}


