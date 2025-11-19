import org.junit.platform.suite.api.*;
import org.junit.jupiter.api.*;

@SelectClasses({
    Lab09TestSuite.StructuralTests.class,
    Lab09TestSuite.FunctionalTests.class,
})
@Suite(failIfNoTests=false) public class Lab09TestSuite {
    @SelectClasses({
        MultiSetStructureTest.class,
        OrganiserStructureTest.class,
        RangedStackStructureTest.class,
    })
    @Suite(failIfNoTests=false) @Tag("structural") public static class StructuralTests {}

    @SelectClasses({
        data.structure.MultiSetTest.class,
        data.organiser.OrganiserTest.class,
        data.structure.RangedStackTest.class,
    })
    @Suite(failIfNoTests=false) @Tag("functional") public static class FunctionalTests {}
}

