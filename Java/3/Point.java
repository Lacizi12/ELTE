public class Point {
    public double x, y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void move(double x, double y) {
        this.x += x;
        this.y += y;
    }

    public void mirror(double cx, double cy){
        x = 2 * cx - x;
        y = 2 * cy - y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}