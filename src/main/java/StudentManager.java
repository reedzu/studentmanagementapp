import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import io.github.cdimascio.dotenv.Dotenv;

public class StudentManager {
    // This is our direct pipeline to Supabase
    private Connection connection;

    // Constructor: Connects to the database the second the app starts
    public StudentManager() {
        // 1. Grab the keys from the .env vault
        Dotenv dotenv = Dotenv.load();
        String dbUrl = dotenv.get("DB_URL");
        String dbUser = dotenv.get("DB_USER");
        String dbPassword = dotenv.get("DB_PASSWORD");

        // 2. Open the connection
        try {
            connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            System.out.println("System: Securely connected to Supabase Database!\n");
        } catch (SQLException e) {
            System.out.println("System: Database connection failed. Check your .env file and internet connection.");
            e.printStackTrace();
        }
    }

    // Method 1: Send a new student to the cloud using SQL INSERT
    public void addStudent(Student student) {
        String sql = "INSERT INTO students (student_id, name, program, year_level) VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            // Fill in the blanks in our SQL command
            pstmt.setString(1, student.getStudentId());
            pstmt.setString(2, student.getName());
            pstmt.setString(3, student.getProgram());
            pstmt.setInt(4, student.getYearLevel());
            
            // Execute the save command
            pstmt.executeUpdate();
            System.out.println("System: Successfully saved " + student.getName() + " to the cloud database.");
        } catch (SQLException e) {
            System.out.println("System: Failed to save student.");
            e.printStackTrace();
        }
    }

    // Method 2: Pull everyone from the cloud using SQL SELECT
    public void displayAllStudents() {
        String sql = "SELECT * FROM students";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            System.out.println("\n--- Current Enrolled Students ---");
            boolean hasStudents = false;
            
            // Loop through every row Supabase sends back
            while (rs.next()) {
                hasStudents = true;
                String id = rs.getString("student_id");
                String name = rs.getString("name");
                String program = rs.getString("program");
                int year = rs.getInt("year_level");
                
                System.out.println("[" + id + "] " + name + " | " + program + " - Year " + year);
            }
            
            if (!hasStudents) {
                System.out.println("System: No students enrolled yet.");
            }
            System.out.println("---------------------------------\n");
            
        } catch (SQLException e) {
            System.out.println("System: Failed to retrieve students.");
            e.printStackTrace();
        }
    }
}