import check.*;
import static check.Use.*;

import module org.junit.jupiter;

@BeforeAll
public static void init() {
    Use.theClass("cafe.Guest")
       .thatIs(INSTANCE_LEVEL, NOT_IMPLEMENTED, MODIFIABLE, VISIBLE_TO_ALL);
}

@Test
public void fieldName() {
    it.hasField("name: String")
      .thatIs(INSTANCE_LEVEL, FULLY_IMPLEMENTED, MODIFIABLE, VISIBLE_TO_SUBCLASSES)
      .thatHas(GETTER)
      .thatHasNo(SETTER);
}

@Test
public void fieldAge() {
    it.hasField("age: int")
      .thatIs(INSTANCE_LEVEL, FULLY_IMPLEMENTED, MODIFIABLE, VISIBLE_TO_SUBCLASSES)
      .thatHas(GETTER)
      .thatHasNo(SETTER);
}

@Test
public void constructor() {
    it.hasConstructor(withArgsLikeAllFields())
      .that(hasUsualModifiers());
}

void main() {}


