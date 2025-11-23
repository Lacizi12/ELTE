public class PointPath {
    public static void main(String[] args) {
        // Minimum 2 pont kell (ami 4 koordináta/szám)
        if (args.length < 4) {
            System.out.println("Adj meg legalább 2 pontot (4 számot)!");
            // Demo adatok, ha lusta vagy írni: (10,0) -> (20,0) -> (20,10)
            args = "10 0 20 0 20 10".split(" ");
            System.out.println("Demo mód: 10 0 20 0 20 10");
        }

        // 1. Az első pont (Kezdőpont)
        Point current = new Point(args, 0);
        
        System.out.print("Start: ");
        current.print();
        
        double totalDistance = 0.0;

        // 2. Végigmegyünk a többi ponton
        // i = 2-től indulunk (a második pont X koordinátája), és 2-esével lépkedünk
        for (int i = 2; i < args.length; i = i + 2) {
            System.out.println("DEBUG: Jelenleg itt állok: " + current.x + ", " + current.y);
            // Létrehozzuk a KÖVETKEZŐ pontot
            Point next = new Point(args, i);
            
            // Kiszámoljuk a távolságot a JELENLEGI és a KÖVETKEZŐ között
            double dist = current.distance(next);
            
            // Hozzáadjuk az össztávhoz
            totalDistance = totalDistance + dist;
            
            // Lépünk: A "következő" lesz mostantól a "jelenlegi"
            current = next;
            
            // Kiírás (formázottan)
            System.out.printf("Arrived at (%2.1f, %2.1f). Step: %2.1f, Total: %2.1f%n", 
                              current.x, current.y, dist, totalDistance);
        }
    }
}