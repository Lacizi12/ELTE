
public record Person(String name, int age) {}

void main() {
    var persons = List.of(
        new Person("Jack", 12),
        new Person("Jill", 34)
    );

    IO.println(persons);
    IO.println(persons.size());
    IO.println(persons.get(0));

    // bejáró for ciklus
    for (Person person : persons) {
        IO.println(person);
    }
}
