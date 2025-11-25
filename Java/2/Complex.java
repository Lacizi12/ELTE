public class Complex {
    double re;
    double im;


    public Complex(double re, double im) {
        this.re = re;
        this.im = im;
    }

    public double abs (Complex num) {
        return Math.sqrt(re*re + im*im);
    }

    public void add(Complex c){
        this.re += c.re;
        this.im += c.im;
    }

    public void sub(Complex c){
        this.re -= c.re;
        this.im -= c.im;
    }
// Szorzás: (a+bi)*(c+di) = (ac-bd) + (ad+bc)i
    public void mul(Complex c) {
        double newRe = (this.re * c.re) - (this.im * c.im);
        double newIm = (this.re * c.im) + (this.im * c.re);
        
        // Frissítjük a saját adatainkat
        this.re = newRe;
        this.im = newIm;
    }

    // Formázott szöveg visszaadása (getText)
    // A feladat kéri: ha a képzetes rész 0, ne írjuk ki az "i"-t.
    public String getText() {
        if (this.im == 0) {
            return String.format("%2.1f", this.re);
        }
        // Figyelj: itt + vagy - előjel is kellhet középre
        if (this.im < 0) {
            return String.format("%2.1f%2.1fi", this.re, this.im);
        } else {
            return String.format("%2.1f+%2.1fi", this.re, this.im);
        }
    }

    // Kiírás (két változatban, mint a Point-nál)
    public void print() {
        System.out.println("Complex number: " + getText());
    }

    public void print(String suffix, Complex other) {
        System.out.println("The number is " + getText() + " " + suffix + " " + other.getText());
    }
}