import check.*;
import static check.Use.*;

import module org.junit.jupiter;

@BeforeAll
public static void init() {
    Use.theClass("printed.material.specific.EBook", withParent("printed.material.Book"))
       .that(hasUsualModifiers());
}

@Test
public void fieldPdfSize() {
    it.hasField("pdfSize: int")
      .that(hasUsualModifiers())
      .thatHasNo(GETTER, SETTER);
}

@Test
public void fieldIsUnitPrefix() {
    it.hasField("isUnitPrefix: boolean")
      .that(hasUsualModifiers())
      .thatHasNo(GETTER, SETTER);
}

@Test
public void fieldUnit() {
    it.hasField("unit: String")
      .that(hasUsualModifiers())
      .thatHasNo(GETTER, SETTER);
}

@Test
public void constructor() {
    it.hasConstructor(withParams("book: printed.material.Book", "pdfSize: int", "isUnitPrefix: boolean", "unit: String"))
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
      .thatReturns("EBook");
}

void main() {}


