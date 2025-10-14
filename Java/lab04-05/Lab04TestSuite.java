import org.junit.platform.suite.api.*;
import org.junit.jupiter.api.*;

@SelectClasses({
    Lab04TestSuite.StructuralTests.class,
    Lab04TestSuite.FunctionalTests.class,
})
@Suite(failIfNoTests=false) public class Lab04TestSuite {
    @SelectClasses({
        RecordLabelStructureTest.class,
        ArtistStructureTest.class,
        FanStructureTest.class,
        TriangularNumbersStructureTest.class,
        IncrementStructureTest.class,
        AdderStructureTest.class,
        CaesarCipherStructureTest.class,
        SoliloquyStructureTest.class,
        IncantationStructureTest.class,
        FibonacciStructureTest.class,
    })
    @Suite(failIfNoTests=false) @Tag("structural") public static class StructuralTests {}

    @SelectClasses({
        music.recording.RecordLabelTest.class,
        music.recording.ArtistTest.class,
        music.fan.FanTest.class,
        famous.sequence.TriangularNumbersTest.class,
        math.operation.safe.IncrementTest.class,
        math.operation.textual.AdderTest.class,
        cipher.CaesarCipherTest.class,
        magic.SoliloquyTest.class,
        magic.library.IncantationTest.class,
        famous.sequence.FibonacciTest.class,
    })
    @Suite(failIfNoTests=false) @Tag("functional") public static class FunctionalTests {}
}

