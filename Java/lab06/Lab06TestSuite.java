import org.junit.platform.suite.api.*;
import org.junit.jupiter.api.*;

@SelectClasses({
    Lab06TestSuite.StructuralTests.class,
    Lab06TestSuite.FunctionalTests.class,
})
@Suite(failIfNoTests=false) public class Lab06TestSuite {
    @SelectClasses({
        WrongSectorTimer1StructureTest.class,
        WrongSectorTimer2StructureTest.class,
        SectorTimerStructureTest.class,
        ArrayUtilStructureTest.class,
        CandidateStructureTest.class,
        ElectionStructureTest.class,
        ElectionTestStructureTest.class,
    })
    @Suite(failIfNoTests=false) @Tag("structural") public static class StructuralTests {}

    @SelectClasses({
        race.car.WrongSectorTimer1Test.class,
        race.car.WrongSectorTimer2Test.class,
        race.car.SectorTimerTest.class,
        array.util.ArrayUtilTest.class,
        election.ElectionTest.class,
        election.ElectionTestTest.class,
    })
    @Suite(failIfNoTests=false) @Tag("functional") public static class FunctionalTests {}
}

