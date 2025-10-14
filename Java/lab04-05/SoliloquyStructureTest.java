import check.*;
import static check.Use.*;

import module org.junit.jupiter;

@BeforeAll
public static void init() {
    Use.theClass("magic.Soliloquy")
       .that(hasUsualModifiers());
}

@Test
public void constructor() {
    it.hasConstructor(withArgsLikeAllFields())
      .that(hasUsualModifiers());
}

@Test
public void methodMain() {
    it.hasMethod("main", withParams("args: array of String"))
      .thatIs(USABLE_WITHOUT_INSTANCE, FULLY_IMPLEMENTED, MODIFIABLE, VISIBLE_TO_ALL)
      .thatReturnsNothing();
}

@Test
public void methodReciteIncantations() {
    it.hasMethod("reciteIncantations", withParams("inc1: magic.library.Incantation", "inc2: magic.library.Incantation", "idx: int", "startWithAppend: boolean"))
      .thatIs(USABLE_WITHOUT_INSTANCE, FULLY_IMPLEMENTED, MODIFIABLE, VISIBLE_TO_ALL)
      .thatReturnsNothing();
}

@Test
public void methodPrintStatus() {
    it.hasMethod("printStatus", withParams("result: boolean", "inc1: magic.library.Incantation", "inc2: magic.library.Incantation"))
      .thatIs(USABLE_WITHOUT_INSTANCE, FULLY_IMPLEMENTED, MODIFIABLE, VISIBLE_TO_NONE)
      .thatReturnsNothing();
}

void main() {}


