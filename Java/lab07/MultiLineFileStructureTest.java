import check.*;
import static check.Use.*;

import module org.junit.jupiter;

@BeforeAll
public static void init() {
    // usedLang = Lang.EN; // uncomment to enforce the message language
    Use.theClass("text.to.numbers.MultiLineFile")
       .that(hasUsualModifiers());
}

@Test
public void constructor() {
    it.hasConstructor(withNoParams())
      .that(hasUsualModifiers());
}

@Test
public void methodAddNumbers() {
    it.hasMethod("addNumbers", withParams("filename: String", "separator: char"))
      .thatIs(USABLE_WITHOUT_INSTANCE, FULLY_IMPLEMENTED, MODIFIABLE, VISIBLE_TO_ALL)
      .thatReturns("int")
      .thatThrows("IOException");
}

void main() {}


