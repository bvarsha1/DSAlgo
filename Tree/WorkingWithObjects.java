import java.util.Objects;

class Person {
    private String name;
    private int age;
    private String email; // Assume we want to check equality based on name and email only

    public Person(String name, int age, String email) {
        this.name = name;
        this.age = age;
        this.email = email;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Person person = (Person) obj;
        return Objects.equals(name, person.name) &&
               Objects.equals(email, person.email); // Comparing only name and email
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email); // HashCode must be consistent with equals()
    }

    public static void main(String[] args) {
        Person p1 = new Person("Alice", 25, "alice@example.com");
        Person p2 = new Person("Alice", 30, "alice@example.com");
        Person p3 = new Person("Bob", 25, "bob@example.com");

        System.out.println(p1.equals(p2)); // true (same name and email)
        System.out.println(p1.equals(p3)); // false (different name and email)
    }
}
