/**
 * Abstract class to modelize generic person
 */
public abstract class Person {
    // Attributes
    /**
     * person name
     */
    private String name;
    //Age

    public Person() {
        this.name = "";
    }

    // Constructor
    public Person(String name) {
        this.name = name;
    }
    // Getter
    public String getName() {
        return name;
    }

}

