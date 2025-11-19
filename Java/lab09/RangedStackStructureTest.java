import check.*;
import static check.Use.*;

import module org.junit.jupiter;

@BeforeAll
public static void init() {
    Use.withTypeArg("E")
       .theClass("data.structure.RangedStack")
       .that(hasUsualModifiers());
}

@Test
public void fieldElems() {
    it.hasField("elems: LinkedList of E")
      .that(hasUsualModifiers())
      .thatHasNo(GETTER, SETTER);
}

@Test
public void constructor01() {
    it.hasConstructor(withArgsLikeFields())
      .that(hasUsualModifiers());
}

@Test
public void constructor02() {
    it.hasConstructor(withParams("other: RangedStack of E"))
      .that(hasUsualModifiers());
}

@Test
public void methodPush() {
    it.hasMethod("push", withParams("elems: array of E"))
      .that(hasUsualModifiers())
      .thatReturnsNothing();
}

@Test
public void methodPop() {
    it.hasMethod("pop", withParams("count: int"))
      .that(hasUsualModifiers())
      .thatReturns("ArrayList of E");
}

void main() {}


