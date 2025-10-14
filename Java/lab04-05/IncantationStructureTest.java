import check.*;
import static check.Use.*;

import module org.junit.jupiter;

@BeforeAll
public static void init() {
    Use.theClass("magic.library.Incantation")
       .that(hasUsualModifiers());
}

@Test
public void fieldText() {
    it.hasField("text: String")
      .that(hasUsualModifiers())
      .thatHas(GETTER)
      .thatHasNo(SETTER);
}

@Test
public void fieldIndex() {
    it.hasField("index: int")
      .that(hasUsualModifiers())
      .thatHas(GETTER, SETTER);
}

@Test
public void constructor01() {
    it.hasConstructor(withArgsLikeAllFields())
      .that(hasUsualModifiers());
}

@Test
public void constructor02() {
    it.hasConstructor(withParams("other: Incantation"))
      .that(hasUsualModifiers());
}

@Test
public void methodEnchant() {
    it.hasMethod("enchant", withParams("other: Incantation", "isPrepend: boolean"))
      .that(hasUsualModifiers())
      .thatReturns("boolean");
}

void main() {}


