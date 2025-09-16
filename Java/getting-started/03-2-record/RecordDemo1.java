
public record Person(String name, int age) {}

void main() {
    Person jack = new Person("Jack", 12);
    var    jill = new Person("Jill", 34);

    IO.println(jack.name());
    IO.println(jill.age());

    IO.println(jack); // Person[name=Jack, age=12]
    IO.println(jill); // Person[name=Jill, age=34]
}
