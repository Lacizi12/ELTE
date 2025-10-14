import check.*;
import static check.Use.*;

import module org.junit.jupiter;

@BeforeAll
public static void init() {
    Use.theClass("music.fan.Fan")
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
public void fieldFavourite() {
    it.hasField("favourite: music.recording.Artist")
      .thatIs(INSTANCE_LEVEL, FULLY_IMPLEMENTED, NOT_MODIFIABLE, VISIBLE_TO_NONE)
      .thatHas(GETTER)
      .thatHasNo(SETTER);
}

@Test
public void fieldMoneySpent() {
    it.hasField("moneySpent: int")
      .that(hasUsualModifiers())
      .thatHas(GETTER)
      .thatHasNo(SETTER);
}

@Test
public void constructor() {
    it.hasConstructor(withArgsLikeFields("name", "favourite"))
      .that(hasUsualModifiers());
}

@Test
public void methodBuyMerchandise() {
    it.hasMethod("buyMerchandise", withParams("cost: int", "friends: array of Fan"))
      .that(hasUsualModifiers())
      .thatReturns("int");
}

@Test
public void methodFavesAtSameLabel() {
    it.hasMethod("favesAtSameLabel", withParams("other: Fan"))
      .that(hasUsualModifiers())
      .thatReturns("boolean");
}

@Test
public void methodToString1() {
    it.hasMethod("toString1", withArgsLikeFields())
      .that(hasUsualModifiers())
      .thatReturns("String");
}

@Test
public void methodToString2() {
    it.hasMethod("toString2", withArgsLikeFields())
      .that(hasUsualModifiers())
      .thatReturns("String");
}

@Test
public void methodToString3() {
    it.hasMethod("toString3", withArgsLikeFields())
      .that(hasUsualModifiers())
      .thatReturns("String");
}

@Test
public void methodToString4() {
    it.hasMethod("toString4", withArgsLikeFields())
      .that(hasUsualModifiers())
      .thatReturns("String");
}

void main() {}


