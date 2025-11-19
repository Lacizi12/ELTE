import check.*;
import static check.Use.*;

import module org.junit.jupiter;

@BeforeAll
public static void init() {
    // usedLang = Lang.EN; // uncomment to enforce the message language
    Use.withTypeArg("E")
       .theClass("data.structure.MultiSet")
       .that(hasUsualModifiers());
}

@Test
public void fieldElemToCount() {
    it.hasField("elemToCount: HashMap of E to Integer")
      .that(hasUsualModifiers())
      .thatHasNo(GETTER, SETTER);
}

@Test
public void constructor() {
    it.hasConstructor(withParams("elems: array of E"))
      .that(hasUsualModifiers());
}

@Test
public void methodSize() {
    it.hasMethod("size", withNoParams())
      .that(hasUsualModifiers())
      .thatReturns("int");
}

@Test
public void methodGetCount() {
    it.hasMethod("getCount", withParams("elem: E"))
      .that(hasUsualModifiers())
      .thatReturns("int");
}

@Test
public void methodAdd() {
    it.hasMethod("add", withParams("elem: E"))
      .that(hasUsualModifiers())
      .thatReturns("int");
}

@Test
public void methodIntersect() {
    it.hasMethod("intersect", withParams("otherMultiSet: MultiSet of E"))
      .that(hasUsualModifiers())
      .thatReturns("MultiSet of E");
}

@Test
public void methodCountExcept() {
    it.hasMethod("countExcept", withParams("notCounted: Set of E"))
      .that(hasUsualModifiers())
      .thatReturns("int");
}

void main() {}


