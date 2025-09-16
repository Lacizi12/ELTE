
void main() {
    var p1 = new Person("Jack", 12);
    var p2 = new Person("Jill", 34);
    var p3 = new Person("Jack", 12);
    var p4 = p1;

    IO.println(p1 == p1);       // true : ugyanaz az objektum
    IO.println(p1 == p3);       // false: különböző objektumok
    IO.println(p1 == p4);       // true : ugyanaz az objektum, álnévvel
    IO.println(p1.equals(p1));  // true : rövidzárral
    IO.println(p1.equals(p2));  // false: tartalmilag különböznek
    IO.println(p1.equals(p3));  // true : tartalmilag egyeznek
}
