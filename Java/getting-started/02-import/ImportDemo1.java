
// given no package name, it uses the "default package"
// given no class name, it takes the filename
// ha nincsen csomag  megadva, a "névtelen csomagba" kerül
// ha nincsen osztály megadva, a fájlnév szolgáltatja


// importing one specific class
// egy specifikus osztály importálása
import utils.printing.Class1;

void main() {
    String doubledString = Class1.doubleString("hello");
    IO.println(doubledString);
}
