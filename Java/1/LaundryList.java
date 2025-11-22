public class LaundryList {

    public static void main(String[] args) {
        // Mivel most nem parancssorból futtatunk paraméterekkel,
        // létrehozunk egy "kamu" bemenetet a teszteléshez.
        // Szerkezet: [DARAB, NÉV, DARAB, NÉV, ...]
        String[] ruhak = {"3", "trousers", "6", "pants", "100", "neckties"};
        
        System.out.println("--- 1. Feladat: Nevek ellenőrzése ---");
        
        // Meghívjuk az ellenőrző metódust
        boolean mindenJo = hasAllGoodNames(ruhak);
        
        if (mindenJo) {
            System.out.println("Minden név rendben van!");
        } else {
            System.out.println("Hiba: Van üres név a listában!");
        }
        System.out.println("--- 2. Feladat: Darabszám ---");
        int osszesRuha = getItemCount(ruhak);
        System.out.println("Összesen " + osszesRuha + " db ruha van.");

        System.out.println("--- 3. Feladat: Fajták száma ---");
        int fajtak = getKindCount(ruhak);
        System.out.println("Különböző fajták száma: " + fajtak);
    }

    // 1. FELADAT: Ellenőrizzük, hogy a nevek nem üresek-e
    public static boolean hasAllGoodNames(String[] args) {
        // Egy for ciklussal végigszaladunk a tömbön.
        // TRÜKK: Nem 0-tól indulunk, hanem 1-től (mert ott van az első név)!
        // És kettesével lépkedünk (i = i + 2), hogy mindig csak a nevekre ugorjunk.
        for (int i = 1; i < args.length; i = i + 2) {
            
            String aktualisNev = args[i];
            
            // A feladat azt kéri, ellenőrizzük, üres-e.
            // A String osztálynak van erre egy "specializált" metódusa: .isEmpty()
            if (aktualisNev.isEmpty()) {
                // Ha találtunk egy rosszat, azonnal visszatérünk Hamissal
                return false;
            }
        }
        
        // Ha a ciklus végigment, és nem talált hibát, akkor minden oké
        return true;
    }
    // 2. FELADAT: Összes ruha darabszámának összeadása
    public static int getItemCount(String[] args) {
        int osszes = 0; // Ebbe gyűjtjük az összeget
        
        // A 0. indexen van az első szám, utána a 2., 4., stb.
        for (int i = 0; i < args.length; i = i + 2) {
            
            String szamSzovegkent = args[i];
            
            // A VARÁZSLAT: Átalakítjuk a szöveget számmá
            int darab = Integer.parseInt(szamSzovegkent);
            
            // Hozzáadjuk a gyűjtőhöz
            osszes = osszes + darab;
        }
        
        return osszes;
    }
    // 3. FELADAT: Hányfajta ruha van? (Egyediek száma)
    public static int getKindCount(String[] args) {
        int fajtakSzama = 0;
        
        // KÜLSŐ CIKLUS: Végigmegyünk a neveken (1, 3, 5...)
        for (int i = 1; i < args.length; i = i + 2) {
            
            String aktualisNev = args[i];
            boolean vanMegIlyenKesobb = false;
            
            // BELSŐ CIKLUS: Előrenézünk a maradékban
            // A j-t onnan indítjuk, ahol tartunk + 2 (következő név)
            for (int j = i + 2; j < args.length; j = j + 2) {
                if (aktualisNev.equals(args[j])) {
                    vanMegIlyenKesobb = true;
                    break; // Megtaláltuk, felesleges tovább keresni
                }
            }
            
            // DÖNTÉS: Csak akkor növeljük a számlálót, ha NEM találtunk többet
            // (A ! jel a tagadás: "ha NEM igaz, hogy van még")
            if (!vanMegIlyenKesobb) {
                fajtakSzama++;
            }
        }
        
        return fajtakSzama;
    }
}