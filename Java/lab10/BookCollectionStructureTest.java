import check.*;
import static check.Use.*;

import module org.junit.jupiter;

@BeforeAll
public static void init() {
    Use.theClass("printed.material.BookCollection")
       .that(hasUsualModifiers()); // publickent class BookCollection

}

@Test
public void fieldBooks() {
    it.hasField("books: ArrayList of Book")
      .that(hasUsualModifiers())
      .thatHas(GETTER)
      .thatHasNo(SETTER);
}

@Test
public void constructor() {
    it.hasConstructor(withArgsLikeFields()) //  Akarmennyi adattag olyan  tipusu, minden adattagra parameter
      .that(hasUsualModifiers());
}

@Test
public void methodAdd() {
    it.hasMethod("add", withParams("books: array of Book"))
      .that(hasUsualModifiers())
      .thatReturnsNothing(); // void 
}

@Test
public void methodSave() {
    it.hasMethod("save", withParams("filename: String"))
      .that(hasUsualModifiers())
      .thatReturns("boolean");
}

@Test
public void methodLoad() {
    it.hasMethod("load", withParams("filename: String"))
      .thatIs(USABLE_WITHOUT_INSTANCE, FULLY_IMPLEMENTED, MODIFIABLE, VISIBLE_TO_ALL)
      .thatReturns("BookCollection");
}

void main() {}


