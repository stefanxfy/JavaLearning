package annotationLearning;

public class User {
    @LengthCheck()
    private String name = "xfystefan";

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
