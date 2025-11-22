public class PointMain {
    public static void main(String[] args) {
        // 1. Létrehozunk egy pontot (10, 20)
        Point p = new Point(10.0, 20.0);
        
        System.out.print("Kezdetben: ");
        p.print(); // Az alap print hívása

        // 2. Eltolás adatai
        double dx = 1.2;
        double dy = -3.4;
        
        // 3. Utasítjuk a pontot, hogy mozduljon el
        p.shift(dx, dy);

        // 4. Az eredmény kiírása a "bőbeszédű" módszerrel
        // Ehhez készítünk egy ideiglenes pontot, ami csak az eltolás mértékét tárolja
        // (hogy át tudjuk adni a printnek, ami Point-ot vár)
        Point eltolasAdatok = new Point(dx, dy);
        
        // Itt hívjuk meg a túlterhelt print-et
        // p: a már eltolt pont (11.2, 16.6)
        // "after...": a suffix
        // eltolasAdatok: az other pont (1.2, -3.4)
        p.print("after being shifted by", eltolasAdatok);
    }
}