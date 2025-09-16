
public record Person(String name, int age) {}

void main() {
    var jack = new Person("Jack", 12);
    var jill = new Person("Jill", 34);

    List<Person> persons = List.of(jack, jill);

    IO.println(persons);
    IO.println(persons.size());
    IO.println(persons.get(0));

    for (int i = 0; i < persons.size(); ++i) {
        IO.println(persons.get(i));
    }
}
