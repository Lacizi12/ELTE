import org.junit.platform.suite.api.*;
import org.junit.jupiter.api.*;

@SelectClasses({
    Lab07TestSuite.StructuralTests.class,
    Lab07TestSuite.FunctionalTests.class,
})
@Suite(failIfNoTests=false) public class Lab07TestSuite {
    @SelectClasses({
        DataProcessorStructureTest.class,
        SingleLineFileStructureTest.class,
        MultiLineFileStructureTest.class,
        SumCheckerStructureTest.class,
        StatisticsStructureTest.class,
        FileContentStructureTest.class,
    })
    @Suite(failIfNoTests=false) @Tag("structural") public static class StructuralTests {}

    @SelectClasses({
        text.to.numbers.DataProcessorTest.class,
        text.to.numbers.SingleLineFileTest.class,
        text.to.numbers.MultiLineFileTest.class,
        file.line.SumCheckerTest.class,
        textfile.StatisticsTest.class,
        textfile.lookup.FileContentTest.class,
    })
    @Suite(failIfNoTests=false) @Tag("functional") public static class FunctionalTests {}
}

