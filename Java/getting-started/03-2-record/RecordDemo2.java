
public record Person(String name, int age) {}
public record Duo(Person p1, Person p2, int score) {}

void main() {
    // constructing a record
    // rekord megkonstruálása
    var jack = new Person("Jack", 12);
    var jill = new Person("Jill", 34);
    var duo  = new Duo(jack, jill, 20);

    // deconstructing a record
    // rekord dekonstruálása
    var result =
        switch (duo) {
        case Duo(Person(var name1, var age1), var p2, var _)    when age1   > 66 -> 1;
        case Duo(Person p1, Person p2, int _)                   when p1.age > 66 -> 2;
        case Duo(Person(String name, int age), Person _, int _) when age    > 66 -> 3;
        case Duo(Person p1, Person p2, int score)                                -> 4;
        default                                                                  -> 5;
    };

    IO.print(result);
}
