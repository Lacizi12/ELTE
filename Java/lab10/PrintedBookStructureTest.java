import check.*;
import static check.Use.*;

import module org.junit.jupiter;

@BeforeAll
public static void init() {
    Use.theClass("printed.material.specific.PrintedBook", withParent("printed.material.Book"))
       .that(hasUsualModifiers());
}

@Test
public void fieldCoverType() {
    it.hasField("coverType: printed.material.CoverType")
      .thatIs(INSTANCE_LEVEL, FULLY_IMPLEMENTED, MODIFIABLE, VISIBLE_TO_SUBCLASSES)
      .thatHasNo(GETTER, SETTER);
}

@Test
public void constructor01() {
    it.hasConstructor(withParams("book: printed.material.Book"))
      .that(hasUsualModifiers());
}

@Test
public void constructor02() {
    it.hasConstructor(withParams("author: String", "title: String", "pageCount: int", "coverType: printed.material.CoverType"))
      .that(hasUsualModifiers());
}

@Test
public void methodGetPrice() {
    it.hasMethod("getPrice", withArgsLikeFields())
      .that(hasUsualModifiers())
      .thatReturns("int");
}

@Test
public void text() {
    it.has(TEXTUAL_REPRESENTATION);
}

@Test
public void methodDecode() {
    it.hasMethod("decode", withParams("text: String"))
      .thatIs(USABLE_WITHOUT_INSTANCE, FULLY_IMPLEMENTED, MODIFIABLE, VISIBLE_TO_ALL)
      .thatReturns("PrintedBook");
}

void main() {}


