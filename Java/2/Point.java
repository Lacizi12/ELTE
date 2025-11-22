public class Point {
    // 1. ADATTAGOK (Mezők) - A pont "memóriája"
    double x;
    double y;

    // 2. KONSTRUKTOR - Ez építi fel a pontot
    public Point(double initX, double initY) {
        this.x = initX;
        this.y = initY;
    }

    // 3. METÓDUSOK (Képességek)

    // ELTOLÁS (shift): Megváltoztatja a saját koordinátáit
    public void shift(double dx, double dy) {
        this.x = this.x + dx;
        this.y = this.y + dy;
    }

    // KIÍRÁS 1. (Alap): Csak kiírja magát
    public void print() {
        System.out.printf("At (%2.1f, %2.1f)%n", this.x, this.y);
    }

    // KIÍRÁS 2. (Túlterhelt / Overloaded): Kiírja magát + szöveget + egy másik pontot
    public void print(String suffix, Point other) {
        System.out.printf("At (%2.1f, %2.1f) %s (%2.1f, %2.1f)%n", 
                          this.x, this.y, suffix, other.x, other.y);
    }
}