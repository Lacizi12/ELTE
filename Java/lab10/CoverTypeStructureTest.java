import check.*;
import static check.Use.*;

import module org.junit.jupiter;

@Test
public void elements() {
    Use.theEnum("printed.material.CoverType")
       .ofEnumElements("SOFTCOVER", "HARDCOVER")
       .that(hasUsualModifiers());
}

void main() {}


