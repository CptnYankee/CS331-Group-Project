import java.sql.*;
import java.io.*;
import java.util.*;
import com.mysql.jdbc.Driver;

public class Assignment3 {
    public static void main(String[] args) {
        Connection conn = null;
        Scanner scan = new Scanner(System.in);
        try {
            //DriverManager.registerDriver(new Driver());
            String url = "jdbc:mysql://localhost:3306/FoodMarket?serverTimezone=UTC&useSSL=TRUE";
            String user, pass;
            user = readEntry("UserID: ");
            pass = readEntry("Password: ");
            conn = DriverManager.getConnection(url, user, pass);
            intro();
            options();
            System.out.print("Type in your option: ");
            int i = scan.nextInt();
            while (i != 4 && i > 0 && i < 5) {
                result(i, scan, conn);
                System.out.println();
                options();
                System.out.print("Type in your option: ");
                i = scan.nextInt();
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    System.out.println();
                }
            }
        }
    }

    private static void intro() {
        System.out.println("**********************************************************************************");
        System.out.println("                               ******************                                 ");
        System.out.println("                          Welcome to the Housing System                           ");
        System.out.println("                               ******************                                 ");
        System.out.println("**********************************************************************************");
    }

    private static void result(int i, Scanner scan, Connection conn) {
        if (i == 1) {
            resident(scan, conn);
        } else if (i == 2) {
            applicant();
        } else if (i == 3) {
            admin(scan, conn);
        }
    }

    public static void admin(Scanner scan, Connection conn) {
        System.out.print("Enter UserId: ");
        String userid = scan.nextLine();
        System.out.print("Enter Password: ");
        String password = scan.nextLine();
        //////to authenticate from Database here here/////
        /////required to be userid=admin and password=admin/////
        String query = "Select name from Admin where userid=" + userid + "and password=" + password;
        ///authentication here for admin
        boolean condition = execute(query, conn);
        if (condition) {
            display();
            adminOptions();
            System.out.println("Type in your option: ");
            int choice = scan.nextInt();
            operations(choice);
        } else {
            System.out.println("Action not allowed");
        }
    }

    private static boolean execute(String query, Connection conn) {
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            ResultSet r = pst.executeQuery();
            return r.next();
        } catch (SQLException e) {
            return false;
        }
    }

    private static void display() {
        System.out.println("**************************************************************************************");
        System.out.println("                    Welcome to Bellevue College Housing System                        ");
        System.out.println("                                  Administrators Staff                                ");
        System.out.println();
        System.out.println("**************************************************************************************");
    }

    private static void adminOptions() {
        System.out.println("1. Manage Residents");
        System.out.println("2. Manage Applicants");
        System.out.println("3. Demographic Studies");
        System.out.println("4. Manage Maintenance orders");
        System.out.println("5. Administrative Reports");
        System.out.println("6. Quit");
    }

    private static void operations(int choice) {
        if (choice == 1) manageRes();
        else if (choice == 2) manageApp();
        else if (choice == 3) demStudies();
        else if (choice == 4) manageMaintenanceOrder();
        else if (choice == 5) adminReports();
    }

    private static void manageRes() {
        ///Queries
    }

    private static void demStudies() {
        ///Queries
    }

    private static void manageApp() {
        ///Queries
    }

    private static void manageMaintenanceOrder() {
        ///Queries
    }

    private static void adminReports() {
        ///Queries
    }

    public static void applicant() {

    }

    private static void options() {
        System.out.println("1. Resident");
        System.out.println("2. Applicant Registration/Apply");
        System.out.println("3. Admin");
        System.out.println("4. Quit");
    }

    private static void resident(Scanner scan, Connection conn) {
        System.out.print("Enter UserId: ");
        String id = scan.nextLine();
        System.out.print("Enter Password: ");
        String pass = scan.nextLine();
        //////to authenticate from Database here here/////
        String query = "Select name from Resident where userid=" + id + " and password=" + pass;
        //authentication here for resident
        boolean condition = execute(query, conn);
        if (condition) {
            residentOptions();
            System.out.print("Enter an option ");
            int i = scan.nextInt();
            //perform operation
            resOperations(i, scan);
        } else {
            System.out.println("Operation not allowed");
        }
    }

    private static void resOperations(int i, Scanner scan) {
        if (i == 1) submitMaintenance(scan);
        else if (i == 2) checkStatus(scan);
        else if (i == 3) maintenanceHistory(scan);
    }

    public static void residentOptions() {
        System.out.println("1. Submit Maintenance Request");
        System.out.println("2. Check Status");
        System.out.println("3. Maintenance History");
    }

    public static void submitMaintenance(Scanner scan) {
//description of the maintenance request
        System.out.print("What is your Building Number? ");
        int num = scan.nextInt();
        System.out.print("What is your Room Number? ");
        int roomnum = scan.nextInt();
        System.out.print("What is your floor number? ");
        int floornum = scan.nextInt();
        System.out.print("What is the date of today?(YYYY-MM-DD)");
        String date = scan.nextLine();
        System.out.print("Please Describe your request ");
        String description = scan.nextLine();
        System.out.print("What is your name? ");
        String resName = scan.nextLine();
        ////to insert into the Maintenance Table here
        // String query="Insert Into Mantenance Values()";
    }

    private static void checkStatus(Scanner scan) {
        System.out.print("What is your ID? ");
        String id = scan.nextLine();
        System.out.print("What day did you submit your request? ");
        String date = scan.nextLine();
        ///String query="Select from maintenance
        ///where ID=id and submission=date
    }

    private static void maintenanceHistory(Scanner scan) {
        System.out.print("What is your name? ");
        String resName = scan.nextLine();
        ///String query="Select Description,submission,status from maintenance where ResidentName=resName"
    }
}