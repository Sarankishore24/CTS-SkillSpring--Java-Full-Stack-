package MVCPatternExample;

public class Main {
    public static void main(String[] args) {
      
        Student model = new Student("Alice Smith", "S101", "A");

      
        StudentView view = new StudentView();

      
        StudentController controller = new StudentController(model, view);

      
        System.out.println("Initial View:");
        controller.updateView();

        controller.setStudentGrade("A+");
        controller.setStudentName("Alice J. Smith");

        System.out.println("\nUpdated View:");
        controller.updateView();
    }
}