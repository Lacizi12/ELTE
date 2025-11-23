public class Point {
    // 1. ADATTAGOK
    double x;
    double y;

    // 2. KONSTRUKTOROK
    
    // Hagyományos konstruktor (számokkal)
    public Point(double initX, double initY) {
        this.x = initX;
        this.y = initY;
    }

    // --- EZ HIÁNYZOTT NÁLAD: ---
    // "Okos" konstruktor (tömbbel)
    public Point(String[] args, int idx) {
        this.x = Double.parseDouble(args[idx]);
        this.y = Double.parseDouble(args[idx+1]);
    }
    // ----------------------------

    // 3. METÓDUSOK

    // Eltolás
    public void shift(double dx, double dy) {
        this.x = this.x + dx;
        this.y = this.y + dy;
    }

    // Tükrözés
    public void mirror(double cx, double cy) {
        this.x = 2 * cx - this.x;
        this.y = 2 * cy - this.y;
    }

    // Távolságmérés
    public double distance(Point p) {
        double dx = this.x - p.x;
        double dy = this.y - p.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    // Kiírás (sima)
    public void print() {
        System.out.printf("At (%2.1f, %2.1f)%n", this.x, this.y);
    }

    // Kiírás (bővített)
    public void print(String suffix, Point other) {
        System.out.printf("At (%2.1f, %2.1f) %s (%2.1f, %2.1f)%n", 
                          this.x, this.y, suffix, other.x, other.y);
    }
}