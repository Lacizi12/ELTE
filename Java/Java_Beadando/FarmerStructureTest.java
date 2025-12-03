import check.*;
import static check.Use.*;

import module org.junit.jupiter;

@BeforeAll
public static void init() {
    // usedLang = Lang.EN; // uncomment to enforce the message language
    Use.theClass("farm.person.Farmer")
       .that(hasUsualModifiers())
       .info("""
           Egy gazdát reprezentál, aki a farm parcelláin tevékenykedik.
           Ültethet, öntözhet és betakaríthat a farmon, valamint nyilván is tartja a begyűjtött növényeket.
       """);
}

@Test
public void fieldName() {
    it.hasField("name: String")
      .thatIs(INSTANCE_LEVEL, FULLY_IMPLEMENTED, NOT_MODIFIABLE, VISIBLE_TO_NONE)
      .thatHas(GETTER)
      .thatHasNo(SETTER);
}

@Test
public void fieldFarm() {
    it.hasField("farm: farm.field.Farm")
      .thatIs(INSTANCE_LEVEL, FULLY_IMPLEMENTED, NOT_MODIFIABLE, VISIBLE_TO_NONE)
      .thatHasNo(GETTER, SETTER)
      .info("A farmer farmja.");
}

@Test
public void fieldHarvestedCrops() {
    it.hasField("harvestedCrops: ArrayList of farm.crop.Crop")
      .thatIs(INSTANCE_LEVEL, FULLY_IMPLEMENTED, NOT_MODIFIABLE, VISIBLE_TO_NONE)
      .thatHas(GETTER)
      .thatHasNo(SETTER)
      .info("A gazda által begyűjtött növények.");
}

@Test
public void constructor() {
    it.hasConstructor(withArgsLikeFields("name", "farm"))
      .that(hasUsualModifiers())
      .thatThrows("IllegalArgumentException", "Ha a név üres vagy ha a név/farm `null`.")
      .info("Üressé teszi a `harvestedCrops` mezőt, a többit a paraméterekből tölti fel.");
}

@Test
public void methodPlantCrop() {
    it.hasMethod("plantCrop", withParams("row: int", "col: int", "type: farm.crop.CropType"))
      .that(hasUsualModifiers())
      .thatReturnsNothing()
      .thatThrows("IllegalStateException", "Ha a parcella már foglalt.")
      .info("Elülteti a megadott fajta növényt a megadott sorba/oszlopba.");
}

@Test
public void methodWaterCrop() {
    it.hasMethod("waterCrop", withParams("row: int", "col: int"))
      .that(hasUsualModifiers())
      .thatReturnsNothing()
      .info("Megöntözi a megadott helyen található növényt, ha az létezik és él.")
      .testWith(testCase("testPlantCropAndWater"), """
          Elültet egy növényt. Ellenőrzi, hogy tényleg ott van.
          Aztán megöntözi. Ellenőrzi, hogy több vize van.
      """);
}

@Test
public void methodHarvestCrop() {
    it.hasMethod("harvestCrop", withParams("row: int", "col: int"))
      .that(hasUsualModifiers())
      .thatReturnsNothing()
      .info("Begyűjti a megadott helyen található növényt, ha az megérett, és hozzáadja a `harvestedCrops` listához.")
      .testWith(testCase("testHarvestRemovesMatureCrop"), "Begyűjtés után a parcella kiürül.");
}

@Test
public void methodWaterCrops() {
    it.hasMethod("waterCrops", withParams("type: farm.crop.CropType"))
      .that(hasUsualModifiers())
      .thatReturnsNothing()
      .info("Megöntözi a `type` jellegű, élő növényeket az egész farmon.");
}

@Test
public void methodHarvestCrops() {
    it.hasMethod("harvestCrops", withParams("type: farm.crop.CropType"))
      .that(hasUsualModifiers())
      .thatReturnsNothing()
      .info("Begyűjti a `type` jellegű, érett növényeket az egész farmon, és hozzáadja őket a `harvestedCrops` listához.");
}

@Test
public void methodCleanPlot() {
    it.hasMethod("cleanPlot", withParams("row: int", "col: int"))
      .that(hasUsualModifiers())
      .thatReturnsNothing()
      .info("Ha elpusztult növény van a megadott helyen, eltávolítja.")
      .testWith(testCase("testCleanDeadCrop"), "Elpusztult növény eltávolítása rendben megy.");
}

@Test
public void methodSimulateDay() {
    it.hasMethod("simulateDay", withNoParams())
      .that(hasUsualModifiers())
      .thatReturnsNothing()
      .info("A farm egy napját szimulálja.")
      .testWith(testCase("testStory"), """
          Két növényt ültet. `interval` naponként, összesen `days` napon át. Ezután begyűjti őket: `expected` darab adódik.
          Kipróbálandó a következő tesztesetekkel. (A megjegyzések csak az értelmezésben segítenek.)
          LETTUCE,       CORN, 2, 14, 1 // Mindkettő életben marad, csak egy érik meg
          LETTUCE,       CORN, 2, 15, 2 // Mindkettő életben marad, mindkettő megérik
          STRAWBERRY,    CORN, 4, 27, 0 // STRAWBERRY elpusztul, CORN megmarad, de nem érik meg
          STRAWBERRY,    CORN, 4, 28, 1 // STRAWBERRY elpusztul, CORN megmarad és megérik
          STRAWBERRY, LETTUCE, 5,  8, 0 // Mindkettő elpusztul
      """);
}

@Test
public void methodWaterMostThirstyCrop() {
    it.hasMethod("waterMostThirstyCrop", withNoParams())
      .that(hasUsualModifiers())
      .thatReturnsNothing()
      .info("Megöntözi a legkevesebb vízzel rendelkező növényt, ha ilyen létezik.")
      .testWith(testCase("testWaterMostThirstyCrop"), "Ellenőrzi, hogy a legalacsonyabb vizű növény kap vizet.");
}

void main() {}


