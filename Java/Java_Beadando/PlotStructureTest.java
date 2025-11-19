import check.*;
import static check.Use.*;

import module org.junit.jupiter;

@BeforeAll
public static void init() {
    // usedLang = Lang.EN; // uncomment to enforce the message language
    Use.theClass("farm.field.Plot")
       .that(hasUsualModifiers())
       .info("Egyetlen parcellányi földet reprezentál.");
}

@Test
public void fieldCrop() {
    it.hasField("crop: farm.crop.Crop")
      .that(hasUsualModifiers())
      .thatHas(GETTER)
      .thatHasNo(SETTER)
      .info("Ha `null`, akkor nincs növény a parcellán.");
}

@Test
public void constructor() {
    it.hasConstructor(withNoParams())
      .that(hasUsualModifiers());
}

@Test
public void methodIsEmpty() {
    it.hasMethod("isEmpty", withNoParams())
      .that(hasUsualModifiers())
      .thatReturns("boolean", "pontosan akkor, ha nincs növény a parcellán.");
}

@Test
public void methodPlant() {
    it.hasMethod("plant", withArgsLikeAllFields())
      .that(hasUsualModifiers())
      .thatReturnsNothing()
      .info("Elültet egy növényt.")
      .info("throws `IllegalArgumentException` ha a paraméter `null`.")
      .info("throws `IllegalStateException` ha a parcellán nincs növény.");
}

@Test
public void methodRemoveCrop() {
    it.hasMethod("removeCrop", withNoParams())
      .that(hasUsualModifiers())
      .thatReturnsNothing()
      .info("Eltávolítja a növényt a parcelláról: `null`-ra állítja.")
      .info("throws `IllegalStateException` ha a parcellán nincs növény.");
}

@Test
public void methodHasDeadCrop() {
    it.hasMethod("hasDeadCrop", withNoParams())
      .that(hasUsualModifiers())
      .thatReturns("boolean", "pontosan akkor igaz, ha a parcella halott növényt tartalmaz.");
}

@Test
public void text() {
    it.has(TEXTUAL_REPRESENTATION)
      .info("Egy `E` betű, ha üres, különben a növény szövege.");
}

void main() {}


