import check.*;
import static check.Use.*;

import module org.junit.jupiter;

@BeforeAll
public static void init() {
    Use.theClass("cafe.Minor", withParent("Guest"))
       .that(hasUsualModifiers());
}

@Test
public void constructor() {
    it.hasConstructor(withParams("name: String", "age: int"))
      .that(hasUsualModifiers());
}

void main() {}


