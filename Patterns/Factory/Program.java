import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DeveloperFactory developerFactory = createDeveloperBySpecialty(scanner.nextLine());
        Developer developer = developerFactory.createDeveloper();
        developer.writeCode();
    }

    static DeveloperFactory createDeveloperBySpecialty(String specialty) {
        if (specialty.equalsIgnoreCase("java")) {
            return new JavaDeveloperFactory();
        } else if (specialty.equalsIgnoreCase("cpp")) {
            return new CppDeveloperFactory();
        } else if (specialty.equalsIgnoreCase("python")) {
            return new PythonDeveloperFabric();
        } else {
            throw new RuntimeException(specialty + " is unknown specialty");
        }
    }
}
