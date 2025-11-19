import check.*;
import static check.Use.*;

import module org.junit.jupiter;

@BeforeAll
public static void init() {
    // usedLang = Lang.EN; // uncomment to enforce the message language
    Use.theClass("file.line.SumChecker")
       .that(hasUsualModifiers());
}

@Test
public void constructor() {
    it.hasConstructor(withNoParams())
      .that(hasUsualModifiers());
}

@Test
public void methodMain() {
    it.hasMethod("main", withParams("args: array of String"))
      .thatIs(USABLE_WITHOUT_INSTANCE, FULLY_IMPLEMENTED, MODIFIABLE, VISIBLE_TO_ALL)
      .thatReturnsNothing();
}

@Test
public void methodGetNumbers() {
    it.hasMethod("getNumbers", withParams("numsTxt: String"))
      .thatIs(USABLE_WITHOUT_INSTANCE, FULLY_IMPLEMENTED, MODIFIABLE, VISIBLE_TO_NONE)
      .thatReturns("array of int");
}

void main() {}


