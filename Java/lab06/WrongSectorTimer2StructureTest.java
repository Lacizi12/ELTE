import check.*;
import static check.Use.*;

import module org.junit.jupiter;

@BeforeAll
public static void init() {
    // usedLang = Lang.EN; // uncomment to enforce the message language
    Use.theClass("race.car.WrongSectorTimer2")
       .that(hasUsualModifiers());
}

@Test
public void fieldSectorTimes() {
    it.hasField("sectorTimes: array of int")
      .that(hasUsualModifiers())
      .thatHas(GETTER, SETTER);
}

@Test
public void constructor() {
    it.hasConstructor(withArgsLikeAllFields())
      .that(hasUsualModifiers());
}

@Test
public void methodGetLapTime() {
    it.hasMethod("getLapTime", withParams("idx: int"))
      .that(hasUsualModifiers())
      .thatReturns("int");
}

@Test
public void methodGetSectorTime() {
    it.hasMethod("getSectorTime", withParams("idx: int"))
      .that(hasUsualModifiers())
      .thatReturns("int");
}

void main() {}


