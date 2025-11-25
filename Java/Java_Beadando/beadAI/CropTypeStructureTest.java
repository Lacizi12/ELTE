import check.*;
import static check.Use.*;

import module org.junit.jupiter;

@Test
public void elements() {
    Use.theEnum("farm.crop.CropType")
       .ofEnumElements("LETTUCE", "CORN", "STRAWBERRY")
       .that(hasUsualModifiers())
       .info("Az alábbi értékeke előre megadottak.")
       .info("Az elemek `growthRate` értékei sorban: 3, 5, 6.")
       .info("Az elemek `possibleMaturity` értékei sorban: 30. 75, 60.");
}

void main() {}


