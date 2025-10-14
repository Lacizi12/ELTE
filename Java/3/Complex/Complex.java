public class Complex {
        // adattagok
    public double re; // valós rész
    public double im; // képzetes rész

    // konstruktor
    public Complex(double re, double im) {
        this.re = re;
        this.im = im;
    }

    // abszolút érték
    public double abs() {
        return Math.sqrt(re * re + im * im);
    }

    // összeadás
    public void add(Complex c) {
        this.re += c.re;
        this.im += c.im;
    }

    // kivonás
    public void sub(Complex c) {
        this.re -= c.re;
        this.im -= c.im;
    }

    // szorzás
    public void mul(Complex c) {
        double newRe = this.re * c.re - this.im * c.im;
        double newIm = this.re * c.im + this.im * c.re;
        this.re = newRe;
        this.im = newIm;
    }

    // szép kiíráshoz
    @Override
    public String toString() {
        return re + " + " + im + "i";
    }
}
