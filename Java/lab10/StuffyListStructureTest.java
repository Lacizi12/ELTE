import check.*;
import static check.Use.*;

import module org.junit.jupiter;

@BeforeAll
public static void init() {
    Use.withTypeArg("E")
       .theClass("data.structure.peculiar.StuffyList", withParent("ArrayList of HashSet of E"))
       .that(hasUsualModifiers());
}

@Test
public void fieldNeededElement() {
    it.hasField("neededElement: E")
      .that(hasUsualModifiers())
      .thatHasNo(GETTER, SETTER);
}

@Test
public void constructor() {
    it.hasConstructor(withArgsLikeAllFields())
      .that(hasUsualModifiers());
}

@Test
public void methodAdd() {
    it.hasMethod("add", withParams("set: HashSet of E"))
      .that(hasUsualModifiers())
      .thatReturns("boolean");
}

void main() {}


