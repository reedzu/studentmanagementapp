import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // 1. Setup the tools
        Scanner scanner = new Scanner(System.in);
        StudentManager manager = new StudentManager();
        boolean isRunning = true;

        System.out.println("System starting up...\n");

        // 2. The Main Application Loop
        while (isRunning) {
            System.out.println("=== STUDENT MANAGEMENT SYSTEM ===");
            System.out.println("1. Add a New Student");
            System.out.println("2. View All Students");
            System.out.println("3. Exit");
            System.out.print("Choose an option (1-3): ");

            String choice = scanner.nextLine();

            // 3. Handle the user's choice
            switch (choice) {
                case "1":
                    System.out.print("Enter Student ID: ");
                    String id = scanner.nextLine();
                    
                    System.out.print("Enter Full Name: ");
                    String name = scanner.nextLine();
                    
                    System.out.print("Enter Program (e.g., BSIT): ");
                    String program = scanner.nextLine();
                    
                    System.out.print("Enter Year Level (Number only): ");
                    int year = Integer.parseInt(scanner.nextLine());

                    // Build the blueprint and give it to the manager
                    Student newStudent = new Student(id, name, program, year);
                    manager.addStudent(newStudent);
                    break;

                case "2":
                    manager.displayAllStudents();
                    break;

                case "3":
                    isRunning = false;
                    System.out.println("Shutting down system. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice. Please type 1, 2, or 3.");
            }
            System.out.println(); // Just adds a blank line to keep the terminal clean
        }
        
        scanner.close(); // Clean up after ourselves
    }
}