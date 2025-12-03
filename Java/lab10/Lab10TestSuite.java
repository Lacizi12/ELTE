import org.junit.platform.suite.api.*;
import org.junit.jupiter.api.*;

@SelectClasses({
    Lab10TestSuite.StructuralTests.class,
    Lab10TestSuite.FunctionalTests.class,
})
@Suite(failIfNoTests=false) public class Lab10TestSuite {
    @SelectClasses({
        BookStructureTest.class,
        CoverTypeStructureTest.class,
        InvalidBookExceptionStructureTest.class,
        PrintedBookStructureTest.class,
        EBookStructureTest.class,
        DamagedBookStructureTest.class,
        BookCollectionStructureTest.class,
        BartenderStructureTest.class,
        GuestStructureTest.class,
        AdultStructureTest.class,
        MinorStructureTest.class,
        StuffyListStructureTest.class,
    })
    @Suite(failIfNoTests=false) @Tag("structural") public static class StructuralTests {}

    @SelectClasses({
        printed.material.BookTest.class,
        printed.material.InvalidBookExceptionTest.class,
        printed.material.specific.PrintedBookTest.class,
        printed.material.specific.EBookTest.class,
        printed.material.specific.DamagedBookTest.class,
        printed.material.BookCollectionTest.class,
        cafe.BartenderTest.class,
        cafe.AdultTest.class,
        cafe.MinorTest.class,
        data.structure.peculiar.StuffyListTest.class,
    })
    @Suite(failIfNoTests=false) @Tag("functional") public static class FunctionalTests {}
}

