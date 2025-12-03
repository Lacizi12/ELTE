import check.*;
import static check.Use.*;

import module org.junit.jupiter;

@BeforeAll
public static void init() {
    Use.theCheckedException("printed.material.InvalidBookException")
       .that(hasUsualModifiers());
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
public void constructor() {
    it.hasConstructor(withArgsLikeAllFields())
      .that(hasUsualModifiers());
}

void main() {}


