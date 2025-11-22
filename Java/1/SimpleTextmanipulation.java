public class SimpleTextManipulation {

    public static void main(String[] args) {
        // Ez a teszt szöveg, ezzel dolgozunk
        String txt = "Szeretek röplabdázni és programozni"; 

        System.out.println("Az eredeti szöveg: " + txt);

        main01(txt);
        main02(txt, 3);
        String furcsaSzoveg = main03("Alma");
        System.out.println("A prefixek összerakva: " + furcsaSzoveg);
    }

    public static void main01(String text) {
        // 1
        int hossz = text.length();
        // 2
        System.out.println("A szöveg hossza: " + hossz);
        
        char elso = text.charAt(0);
        System.out.println("Az első karakter: " + elso);
        
        char utolso = text.charAt(hossz-1);
        System.out.println("Az utolsó karakter: " + utolso);
        
        // 3
        // Ez a "rossz" (ASCII matek) kiírás:
        System.out.println(elso + utolso); 
        // Ez a jó kiírás:
        System.out.println("Az eredmeny: " + elso + utolso);
    }

    // 4
    public static void main02(String text, int n) {
        String eleje = text.substring(0, n);
        
        System.out.printf("%s a szoveg eleje%n", eleje);

        int kezdoindex = text.length() - n;
        String vege = text.substring(kezdoindex);
        
        System.out.printf("%s a szoveg vege%n", vege);
    }
    // Figyeld meg: void helyett String van, mert szöveggel tér vissza!
    public static String main03(String text) {
    String eredmeny = ""; // Egy üres doboz, amibe gyűjtjük a betűket
    
    // CIKLUS: i indul 1-től, és elmegy a szöveg hosszáig
    // i++ jelentése: minden körben növeld eggyel az i-t
    for (int i = 1; i <= text.length(); i++) {
        
        // Levágjuk az első 'i' darab karaktert
        String darab = text.substring(0, i);
        
        // Hozzáragasztjuk a gyűjtődobozunkhoz
        eredmeny = eredmeny + darab;
    }
    
    // Itt nem iratunk ki semmit! Visszadobjuk a labdát a main-nek.
    return eredmeny;
    }
}