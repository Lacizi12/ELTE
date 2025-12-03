import check.*;
import static check.Use.*;

import module org.junit.jupiter;

@BeforeAll
public static void init() {
    // usedLang = Lang.EN; // uncomment to enforce the message language
    Use.theClass("farm.crop.Crop")
       .that(hasUsualModifiers());
}

@Test
public void fieldType() {
    it.hasField("type: CropType")
      .thatIs(INSTANCE_LEVEL, FULLY_IMPLEMENTED, NOT_MODIFIABLE, VISIBLE_TO_NONE)
      .thatHas(GETTER)
      .thatHasNo(SETTER);
}

@Test
public void fieldGrowthLevel() {
    it.hasField("growthLevel: int")
      .that(hasUsualModifiers())
      .thatHas(GETTER)
      .thatHasNo(SETTER)
      .info("Kezdetben 0, mert el kell telnie időnek ahhoz, hogy nőhessen.");
}

@Test
public void fieldWaterLevel() {
    it.hasField("waterLevel: int")
      .that(hasUsualModifiers())
      .thatHas(GETTER)
      .thatHasNo(SETTER)
      .info("Kezdetben 2.");
}

@Test
public void fieldIsCropDead() {
    it.hasField("isCropDead: boolean")
      .that(hasUsualModifiers())
      .thatHas(GETTER)
      .thatHasNo(SETTER);
}

@Test
public void fieldDryDays() {
    it.hasField("dryDays: int")
      .that(hasUsualModifiers())
      .thatHasNo(GETTER, SETTER)
      .info("Kezdetben 0.");
}

@Test
public void constructor() {
    it.hasConstructor(withArgsLikeFields("type"))
      .that(hasUsualModifiers())
      .thatThrows("IllegalArgumentException", "`type` nem lehet `null`.");
}

@Test
public void methodWater() {
    it.hasMethod("water", withNoParams())
      .that(hasUsualModifiers())
      .thatReturnsNothing()
      .info("A víz mennyisége legfeljebb 10 lehet.")
      .info("Megnöveli a víz mennyiségét kettővel és kinullázza `dryDays` értékét.");
}

@Test
public void methodSimulateDay() {
    it.hasMethod("simulateDay", withNoParams())
      .that(hasUsualModifiers())
      .thatReturnsNothing()
      .info("Eltelik egy nap. Ha nem volt víz, eggyel nő a száraz napok száma, különben a víz csökken eggyel, és a növények az ütemük szerint nőnek, legfeljebb 100-ig.")
      .info("STRAWBERRY, LETTUCE és CORN sorra 1, 2 és 3 napot bírnak víz nélkül, különben elpusztulnak.")
      .testWith(testCase("testWaterDecayAfterSimulateDay"), "`simulateDay` csökkenti eggyel a víz mennyiségét.")
      .testWith(testCase("testGrowthOccursWhenWatered"), "Nem-száraz napon a CropType szerinti mértékben nőnek a növények, amikor eltelik egy nap.")
      .testWith(testCase("testDifferentDeathThresholds"), "A tűréskorlátjukon túli szárazság megöli a növényeket.");
}

@Test
public void methodIsMature() {
    it.hasMethod("isMature", withNoParams())
      .that(hasUsualModifiers())
      .thatReturns("boolean", "A növény akkor érett, ha a növekedési szintje elérte vagy meghaladta az érettségi küszöböt.")
      .testWith(testCase("testIsMature"), "A növények meg tudnak érni.");
}

@Test
public void methodHarvest() {
    it.hasMethod("harvest", withNoParams())
      .that(hasUsualModifiers())
      .thatReturns("boolean")
      .info("`growthLevel`, `waterLevel`, `dryDays` visszaáll a kezdeti értékeire.")
      .testWith(testCase("testHarvestSuccess"), "Megérett növények rendben betakaríthatók.")
      .testWith(testCase("testHarvestFailsWhenNotMatureOrDead"), "Éretlen vagy elpusztult növényeket nem lehet betakarítani.");
}

@Test
public void text() {
    it.has(TEXTUAL_REPRESENTATION)
      .info("LETTUCE, CORN és STRAWBERRY szövege 'L', 'C', 'S'.")
      .info("Elpusztult növény szövege 'D'.");
}

void main() {}


