import check.*;
import static check.Use.*;

import module org.junit.jupiter;

@BeforeAll
public static void init() {
    // usedLang = Lang.EN; // uncomment to enforce the message language
    Use.theClass("worker.schedule.WorkerSchedule")
       .that(hasUsualModifiers());
}

@Test
public void fieldWeekToWorkers() {
    it.hasField("weekToWorkers: HashMap of Integer to HashSet of String")
      .that(hasUsualModifiers())
      .thatHasNo(GETTER, SETTER);
}

@Test
public void constructor() {
    it.hasConstructor(withNoParams())
      .that(hasUsualModifiers());
}

@Test
public void methodAdd01() {
    it.hasMethod("add", withParams("week: int", "workers: HashSet of String"))
      .that(hasUsualModifiers())
      .thatReturnsNothing();
}

@Test
public void methodAdd02() {
    it.hasMethod("add", withParams("weeks: HashSet of Integer", "workers: ArrayList of String"))
      .that(hasUsualModifiers())
      .thatReturnsNothing();
}

@Test
public void methodIsWorkingOnWeek() {
    it.hasMethod("isWorkingOnWeek", withParams("worker: String", "week: int"))
      .that(hasUsualModifiers())
      .thatReturns("boolean");
}

@Test
public void methodGetWorkWeeks() {
    it.hasMethod("getWorkWeeks", withParams("worker: String"))
      .that(hasUsualModifiers())
      .thatReturns("HashSet of Integer");
}

void main() {}


