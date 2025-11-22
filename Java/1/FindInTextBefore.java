public class FindInTextBefore {

    public static void main(String[] args) {
        // Teszt adatok
        // Indexek: 0123456789...
        String szoveg = "alma korte barack alma szilva";
        
        System.out.println("A szöveg: " + szoveg);
        
        // Kipróbáljuk a keresést
        // A feladat: vegyük ki a 18. és 22. karakter közötti részt ("alma" a végéről)
        // És keressük meg, hol van ez legelőször.
        int eredmeny = findFirstIdx(szoveg, 18, 22);
        
        System.out.println("Az első előfordulás helye: " + eredmeny);

        // Egy "szellős" szöveg
        szoveg = "a l m a   k o r t e";
        
        // A feladat: Vágjuk ki a 2. és 5. karakter közötti részt ("l m")
        // és keressük meg a helyét úgy, mintha nem lennének szóközök.
        // Eredménynek 1-et várunk (mert az 'alma' szóban az 'lm' az 1. indexen kezdődik: a[l]ma)
        int hely = findFirstIdxIgnoreSpaces(szoveg, 2, 5);
        
        System.out.println("A találat helye (szóközök nélkül): " + hely);
    }

    // A FELADAT MEGVALÓSÍTÁSA
    public static int findFirstIdx(String text, int start, int end) {
        // 1. LÉPÉS: Vágjuk ki a keresendő darabot a megadott indexek alapján
        // Emlékszel még a substringre? (start, end)
        String keresettSzoveg = text.substring(start, end);
        
        System.out.println("Ezt keressük: " + keresettSzoveg);

        // 2. LÉPÉS: Keressük meg az ELSŐ előfordulását
        // A .indexOf() visszaadja az első találat indexét (vagy -1-et, ha nincs benne)
        int elsoHely = text.indexOf(keresettSzoveg);
        
        return elsoHely;
    }

    // 2. FELADAT / 2. PONT: Szóközök figyelmen kívül hagyása
    public static int findFirstIdxIgnoreSpaces(String text, int start, int end) {
        // 1. Először kivágjuk azt a darabot, amit keresni akarunk (pl. " l m ")
        String keresettDarab = text.substring(start, end);
        
        // 2. "Takarítás": Kitöröljük a szóközöket a teljes szövegből
        // Példa: "a l m a" -> "alma"
        String tisztaSzoveg = text.replace(" ", "");
        tisztaSzoveg = tisztaSzoveg.toLowerCase();
        
        // 3. Kitöröljük a szóközöket abból is, amit keresünk
        // Példa: " l m " -> "lm"
        String tisztaKeresett = keresettDarab.replace(" ", "");
        
        System.out.println("Tiszta szöveg: '" + tisztaSzoveg + "'");
        System.out.println("Ebben keressük ezt: '" + tisztaKeresett + "'");

        // 4. Most végezzük el a keresést a tiszta verziókon
        return tisztaSzoveg.indexOf(tisztaKeresett);
    }
}