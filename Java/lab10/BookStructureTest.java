import check.*;
import static check.Use.*;

import module org.junit.jupiter;

@BeforeAll
public static void init() {
    Use.theClass("printed.material.Book")
       .that(hasUsualModifiers());
}

@Test
public void fieldDEFAULT_AUTHOR() {
    it.hasField("DEFAULT_AUTHOR: String")
      .thatIs(USABLE_WITHOUT_INSTANCE, FULLY_IMPLEMENTED, NOT_MODIFIABLE, VISIBLE_TO_ALL)
      .thatHasNo(GETTER, SETTER);
}

@Test
public void fieldDEFAULT_TITLE() {
    it.hasField("DEFAULT_TITLE: String")
      .thatIs(USABLE_WITHOUT_INSTANCE, FULLY_IMPLEMENTED, NOT_MODIFIABLE, VISIBLE_TO_ALL)
      .thatHasNo(GETTER, SETTER);
}

@Test
public void fieldDEFAULT_PAGE_COUNT() {
    it.hasField("DEFAULT_PAGE_COUNT: int")
      .thatIs(USABLE_WITHOUT_INSTANCE, FULLY_IMPLEMENTED, NOT_MODIFIABLE, VISIBLE_TO_ALL)
      .thatHasNo(GETTER, SETTER);
}

@Test
public void fieldAuthor() {
    it.hasField("author: String")
      .that(hasUsualModifiers())
      .thatHas(GETTER)
      .thatHasNo(SETTER);
}

@Test
public void fieldTitle() {
    it.hasField("title: String")
      .that(hasUsualModifiers())
      .thatHas(GETTER)
      .thatHasNo(SETTER);
}

@Test
public void fieldPageCount() {
    it.hasField("pageCount: int")
      .thatIs(INSTANCE_LEVEL, FULLY_IMPLEMENTED, MODIFIABLE, VISIBLE_TO_SUBCLASSES)
      .thatHas(GETTER)
      .thatHasNo(SETTER);
}

@Test
public void constructor01() {
    it.hasConstructor(withArgsLikeFields())
      .that(hasUsualModifiers());
}

@Test
public void constructor02() {
    it.hasConstructor(withArgsLikeAllFields())
      .that(hasUsualModifiers());
}

@Test
public void methodCheckInitData() {
    it.hasMethod("checkInitData", withArgsLikeAllFields())
      .thatIs(INSTANCE_LEVEL, FULLY_IMPLEMENTED, MODIFIABLE, VISIBLE_TO_NONE)
      .thatReturnsNothing();
}

@Test
public void methodInitBook() {
    it.hasMethod("initBook", withArgsLikeAllFields())
      .thatIs(INSTANCE_LEVEL, FULLY_IMPLEMENTED, MODIFIABLE, VISIBLE_TO_SUBCLASSES)
      .thatReturnsNothing();
}

@Test
public void methodGetAuthorWithInitials() {
    it.hasMethod("getAuthorWithInitials", withArgsLikeFields())
      .thatIs(INSTANCE_LEVEL, FULLY_IMPLEMENTED, MODIFIABLE, VISIBLE_TO_SUBCLASSES)
      .thatReturns("String");
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
public void methodGetShortInfo() {
    it.hasMethod("getShortInfo", withArgsLikeFields())
      .that(hasUsualModifiers())
      .thatReturns("String");
}

@Test
public void methodCreateReference() {
    it.hasMethod("createReference", withParams("articleTitle: String", "from: int", "to: int"))
      .that(hasUsualModifiers())
      .thatReturns("String");
}

@Test
public void methodDecode() {
    it.hasMethod("decode", withParams("input: String"))
      .thatIs(USABLE_WITHOUT_INSTANCE, FULLY_IMPLEMENTED, MODIFIABLE, VISIBLE_TO_ALL)
      .thatReturns("Book");
}

void main() {}


