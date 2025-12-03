import check.*;
import static check.Use.*;

import module org.junit.jupiter;

@BeforeAll
public static void init() {
    Use.theClass("cafe.Bartender")
       .that(hasUsualModifiers());
}

@Test
public void fieldLegalAge() {
    it.hasField("legalAge: int")
      .that(hasUsualModifiers())
      .thatHasNo(GETTER, SETTER);
}

@Test
public void constructor() {
    it.hasConstructor(withArgsLikeAllFields())
      .that(hasUsualModifiers());
}

@Test
public void methodOrder() {
    it.hasMethod("order", withParams("guest: Guest"))
      .that(hasUsualModifiers())
      .thatReturns("boolean");
}

void main() {}


