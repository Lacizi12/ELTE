// Nincs "package" sor! Ez a "névtelen" (default) csomag.

import point2d.Point; // Mivel a Point most már public, látnunk kell

public class PointMainInDefaultPackage {
    public static void main(String[] args) {
        Point p = new Point(10, 20);
        System.out.println("Sikerült a névtelen csomagból is!");
        p.print();
    }
}