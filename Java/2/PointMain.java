import java.util.Scanner; // Kell az interaktív beolvasáshoz (g feladat)

public class PointMain {
    public static void main(String[] args) {
        
        // --- F FELADAT: Alapértelmezett adatok ---
        // Ha nem adtunk meg semmit, "hazudjuk" azt, hogy megadtuk ezeket a számokat.
        // A .split(" ") a szóközöknél feldarabolja a szöveget egy tömbbé.
        if (args.length == 0) {
            System.out.println("Nincs paraméter -> Demo mód indul!");
            args = "10.0 20.0 1.2 -3.4 10.0 10.0".split(" ");
        }

        // Ha még a demo adatokkal sincs meg a minimum 2 szám, akkor feladjuk
        if (args.length < 2) {
            System.out.println("Hiba: Legalább 2 szám kell a kezdéshez!");
            return;
        }

        // 1. KEZDŐPONT LÉTREHOZÁSA (Közös minden esetben)
        Point p = new Point(args, 0);
        System.out.print("Start: ");
        p.print();

        // --- G FELADAT: Interaktív mód ---
        // Ha pontosan 2 adatunk van (csak a kezdőpont), akkor billentyűzetről olvasunk tovább
        if (args.length == 2) {
            System.out.println("Interaktív mód! Formátum: X Y. Kilépés: -123456");
            Scanner scanner = new Scanner(System.in);
            
            int lepes = 1;
            boolean eltolasJon = true; // Ezzel váltogatjuk: Eltolás -> Tükrözés -> Eltolás...

            while (true) {
                // Bekérjük az adatokat
                System.out.print("Adj meg egy " + (eltolasJon ? "eltolást" : "tükrözést") + " (X Y): ");
                
                if (!scanner.hasNextDouble()) break; // Ha nem számot írnak, kilépünk
                double adatX = scanner.nextDouble();
                
                // Kilépési feltétel (Magic Number)
                if (adatX == -123456) {
                    System.out.println("Kilépés...");
                    break;
                }
                
                double adatY = scanner.nextDouble(); // A második szám
                
                Point adatok = new Point(adatX, adatY);

                if (eltolasJon) {
                    // Eltolás végrehajtása
                    p.shift(adatX, adatY);
                    String uzenet = "step #" + lepes + " after being shifted by";
                    p.print(uzenet, adatok);
                } else {
                    // Tükrözés végrehajtása
                    p.mirror(adatX, adatY);
                    String uzenet = "step #" + lepes + " after being mirrored on";
                    p.print(uzenet, adatok);
                    lepes++; // Csak a tükrözés után növeljük a lépésszámot (a feladat logikája szerint)
                }

                // Váltunk: ha most eltolás volt, legközelebb tükrözés jön
                eltolasJon = !eltolasJon; 
            }
        
        } else {
            // --- RÉGI MÓDSZER: Ha sok paraméter van, dolgozzuk fel őket sorban ---
            // (Ez fut le a demo adatokkal is, mert ott 6 db adat van)
            
            for (int i = 2; i < args.length; i = i + 2) {
                Point eltolas = new Point(args, i);
                p.shift(eltolas.x, eltolas.y);
                
                String uzenet = "step #" + ((i - 2) / 2 + 1) + " after being shifted by";
                p.print(uzenet, eltolas);
            }
        }
    }
}