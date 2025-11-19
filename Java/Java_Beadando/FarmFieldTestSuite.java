import org.junit.platform.suite.api.*;
import org.junit.jupiter.api.*;

@SelectClasses({
    FarmFieldTestSuite.StructuralTests.class,
    FarmFieldTestSuite.FunctionalTests.class,
})
@Suite(failIfNoTests=false) public class FarmFieldTestSuite {
    @SelectClasses({
        CropStructureTest.class,
        CropTypeStructureTest.class,
        FarmerStructureTest.class,
        FarmStructureTest.class,
        PlotStructureTest.class,
    })
    @Suite(failIfNoTests=false) @Tag("structural") public static class StructuralTests {}

    @SelectClasses({
        farm.crop.CropTest.class,
        farm.person.FarmerTest.class,
        farm.field.FarmTest.class,
        farm.field.PlotTest.class,
    })
    @Suite(failIfNoTests=false) @Tag("functional") public static class FunctionalTests {}
}

