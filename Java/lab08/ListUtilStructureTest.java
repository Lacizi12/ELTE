import check.*;
import static check.Use.*;

import module org.junit.jupiter;

@BeforeAll
public static void init() {
    Use.theClass("data.structure.ListUtil")
       .that(hasUsualModifiers());
}

@Test
public void constructor() {
    it.hasConstructor(withArgsLikeAllFields())
      .that(hasUsualModifiers());
}

@Test
public void methodDivisors() {
    it.hasMethod("divisors", withParams("number: int"))
      .thatIs(USABLE_WITHOUT_INSTANCE, FULLY_IMPLEMENTED, MODIFIABLE, VISIBLE_TO_ALL)
      .thatReturns("ArrayList of Integer");
}

@Test
public void methodWithSameStartEnd() {
    it.hasMethod("withSameStartEnd", withParams("txts: ArrayList of String"))
      .thatIs(USABLE_WITHOUT_INSTANCE, FULLY_IMPLEMENTED, MODIFIABLE, VISIBLE_TO_ALL)
      .thatReturns("ArrayList of String");
}

@Test
public void methodMaxToFront() {
    it.hasMethod("maxToFront", withParams("txts: ArrayList of String"))
      .thatIs(USABLE_WITHOUT_INSTANCE, FULLY_IMPLEMENTED, MODIFIABLE, VISIBLE_TO_ALL)
      .thatReturnsNothing();
}

void main() {}


