import check.*;
import static check.Use.*;

import module org.junit.jupiter;

@Test
public void elements() {
    Use.theEnum("election.candidate.Candidate")
       .ofEnumElements("JACK", "JILL", "SAM", "MAX")
       .that(hasUsualModifiers());
}

void main() {}


