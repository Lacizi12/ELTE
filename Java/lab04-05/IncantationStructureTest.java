import check.*;
import static check.Use.*;

import module org.junit.jupiter;

@BeforeAll
public static void init() {
    // usedLang = Lang.EN; // uncomment to enforce the message language
    Use.theClass("magic.library.Incantation")
       .that(hasUsualModifiers());
}

@Test
public void fieldText() {
    it.hasField("text: String")
      .that(hasUsualModifiers())
      .thatHas(GETTER)
      .thatHasNo(SETTER);
}

@Test
public void fieldIndex() {
    it.hasField("index: int")
      .that(hasUsualModifiers())
      .thatHas(GETTER, SETTER);
}

@Test
public void constructor01() {
    it.hasConstructor(withArgsLikeAllFields())
      .that(hasUsualModifiers())
      .info("""
          Létrehoz egy varázslatos ráolvasást, melynek szövegét eltárolja az index változóban tárolt egész számmal együtt.
          @throws IllegalArgumentException
      """);
}

@Test
public void constructor02() {
    it.hasConstructor(withParams("other: Incantation"))
      .that(hasUsualModifiers())
      .info("Létrehoz egy varázslatos ráolvasást az argumentumban lévő ráolvasásból másolva.");
}

@Test
public void methodEnchant() {
    it.hasMethod("enchant", withParams("other: Incantation", "isPrepend: boolean"))
      .that(hasUsualModifiers())
      .thatReturns("boolean")
      .info("A paraméterben átadott ráolvasás szövegét próbálja megváltoztatni.");
}

void main() {}


