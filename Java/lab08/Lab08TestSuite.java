import org.junit.platform.suite.api.*;
import org.junit.jupiter.api.*;

@SelectClasses({
    Lab08TestSuite.StructuralTests.class,
    Lab08TestSuite.FunctionalTests.class,
})
@Suite(failIfNoTests=false) public class Lab08TestSuite {
    @SelectClasses({
        WorkerScheduleStructureTest.class,
        ListUtilStructureTest.class,
        CharacterStatisticsStructureTest.class,
    })
    @Suite(failIfNoTests=false) @Tag("structural") public static class StructuralTests {}

    @SelectClasses({
        worker.schedule.WorkerScheduleTest.class,
        data.structure.ListUtilTest.class,
        text.util.CharacterStatisticsTest.class,
    })
    @Suite(failIfNoTests=false) @Tag("functional") public static class FunctionalTests {}
}

