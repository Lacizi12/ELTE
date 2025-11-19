import check.*;
import static check.Use.*;

import module org.junit.jupiter;

@BeforeAll
public static void init() {
    // usedLang = Lang.EN; // uncomment to enforce the message language
    Use.theClass("text.util.CharacterStatistics")
       .that(hasUsualModifiers());
}

@Test
public void fieldCharToCount() {
    it.hasField("charToCount: HashMap of Character to Integer")
      .that(hasUsualModifiers())
      .thatHasNo(GETTER, SETTER);
}

@Test
public void constructor() {
    it.hasConstructor(withParams("text: String"))
      .that(hasUsualModifiers());
}

@Test
public void methodGetCount() {
    it.hasMethod("getCount", withParams("c: char"))
      .that(hasUsualModifiers())
      .thatReturns("int");
}

@Test
public void text() {
    it.has(TEXTUAL_REPRESENTATION);
}

void main() {}


