import check.*;
import static check.Use.*;

import module org.junit.jupiter;

@BeforeAll
public static void init() {
    // usedLang = Lang.EN; // uncomment to enforce the message language
    Use.theClass("text.to.numbers.DataProcessor")
       .that(hasUsualModifiers());
}

@Test
public void constructor() {
    it.hasConstructor(withNoParams())
      .that(hasUsualModifiers());
}

@Test
public void methodMain() {
    it.hasMethod("main", withParams("args: array of String"))
      .thatIs(USABLE_WITHOUT_INSTANCE, FULLY_IMPLEMENTED, MODIFIABLE, VISIBLE_TO_ALL)
      .thatReturnsNothing()
      .thatThrows("FileNotFoundException")
      .thatThrows("IOException");
}

@Test
public void methodConvert() {
    it.hasMethod("convert", withParams("nums: array of String"))
      .thatIs(USABLE_WITHOUT_INSTANCE, FULLY_IMPLEMENTED, MODIFIABLE, VISIBLE_TO_ALL)
      .thatReturns("array of int");
}

@Test
public void methodConvertWithDefault() {
    it.hasMethod("convertWithDefault", withParams("defaultValue: int", "nums: array of String"))
      .thatIs(USABLE_WITHOUT_INSTANCE, FULLY_IMPLEMENTED, MODIFIABLE, VISIBLE_TO_ALL)
      .thatReturns("array of int");
}

@Test
public void methodCountFileLines() {
    it.hasMethod("countFileLines", withParams("filename: String"))
      .thatIs(USABLE_WITHOUT_INSTANCE, FULLY_IMPLEMENTED, MODIFIABLE, VISIBLE_TO_ALL)
      .thatReturns("int");
}

@Test
public void methodSumFile() {
    it.hasMethod("sumFile", withParams("inFilename: String", "outFilename: String"))
      .thatIs(USABLE_WITHOUT_INSTANCE, FULLY_IMPLEMENTED, MODIFIABLE, VISIBLE_TO_ALL)
      .thatReturnsNothing()
      .thatThrows("FileNotFoundException")
      .thatThrows("IOException");
}

void main() {}


