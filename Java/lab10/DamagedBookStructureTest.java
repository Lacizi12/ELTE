import check.*;
import static check.Use.*;

import module org.junit.jupiter;

@BeforeAll
public static void init() {
    Use.theClass("printed.material.specific.DamagedBook", withParent("printed.material.Book"))
       .that(hasUsualModifiers());
}

@Test
public void constructor() {
    it.hasConstructor(withArgsLikeAllFields())
      .that(hasUsualModifiers());
}

void main() {}


