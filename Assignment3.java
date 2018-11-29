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
            String url = "jdbc:mysql://localhost:3306/Housing?serverTimezone=UTC&useSSL=TRUE";
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
        String query = "Select name from Admin where userid=? and password=?";
        ///authentication here for admin
        boolean condition = execute(query, conn,userid,password);
        if (condition) {
            display();
            adminOptions();
            System.out.println("Type in your option: ");
            int choice = scan.nextInt();
            operations(choice,conn);
        } else {
            System.out.println("Action not allowed");
        }
    }

    private static boolean execute(String query, Connection conn ,String userid,String password) {
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1,userid);
            pst.setString(2,password);
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
        System.out.println("2. Demographic Studies");
        System.out.println("3. Manage Maintenance orders");
        System.out.println("4. Administrative Reports");
        System.out.println("5. Quit");
    }

    private static void operations(int choice,Connection conn) {
        if (choice == 1) manageRes(conn);
        else if (choice == 2) demStudies();
        else if (choice == 3) manageMaintenanceOrder(conn);
        else if (choice == 4) adminReports();
    }

    private static void demStudies(Connection conn) {
    System.out.println("Results of First study");
    String[] first=query1("1");
    execution(first,conn);
    System.out.println("Results of Second study");
    String[] second=query1("1");
    execution(second,conn);
    System.out.println("Results of Third study");
    String[] third=query1("0");
    execution(third,conn);
    System.out.println("Results of Fourth Study");
    String[] four=query2();
    execution(four,conn);
    System.out.println("Results of Fifth Study");
    fifthQuery(conn);
    System.out.print("Enter the day you wish to check its Maintenance Request(MM/DD/YYYY ");
    String day=scan.nextLine();
    String query="Select Description,Status from MaintenanceRequest where SubmissionDate=?";
    sixthQuery(query,day,conn);
    }
    public static void execution(String[] first,Connection conn) {
        for (int i = 0; i < first.length; i++) {
            try {
                PreparedStatement pst = conn.prepareStatement(first[i]);
                ResultSet rs = pst.executeQuery();
                System.out.println(rs.getInt(1));
            } catch (SQLException e) {
            }
        }
        System.out.println();
    }
    private static void fifthQuery(Connection conn){
        String query="Select Description from MaintenanceRequest where status<>Fixed";
        try
         {
            PreparedStatement pst = conn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while(rs.next()) {
                System.out.println(rs.getString(1));
            }
            System.out.println();
        } catch (SQLException e) {
        }
    }
    private static void sixthQuery(String query,String day,Connection conn){
        try
        {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1,day)
            ResultSet rs = pst.executeQuery();
            System.out.println(rs.getInt(1));
            System.out.println();
        } catch (SQLException e) {
        }
    }
    private static String[] query2(){
        String[] first=new String[5];
        first[0]="SELECT sum(MaritalStatus) FROM Person,Resident WHERE Person.StudentID=Resident.StudentID AND Gender='F' and Graduation_Year >=2013";
        first[1]="SELECT sum(MaritalStatus) FROM Person,Resident WHERE Person.StudentID=Resident.StudentID and Gender='F' and Graduation_Year >=2014";
        first[2]="SELECT sum(MaritalStatus) FROM Person,Resident WHERE Person.StudentID=Resident.StudentID and Gender='F' and Graduation_Year >=2015 OR GradeLevel = 'Senior'";
        first[3]="SELECT sum(MaritalStatus) FROM Person,Resident WHERE Person.StudentID=Resident.StudentID AND Gender='F'and Graduation_Year >=2016 OR GradeLevel = 'Senior' OR GradeLevel = 'Junior'";
        first[4]="SELECT sum(MaritalStatus) FROM Person,Resident WHERE Person.StudentID=Resident.StudentID and Gender='F'and Graduation_Year >=2017 OR GradeLevel = 'Senior' OR GradeLevel = 'Junior' OR GradeLevel = 'Sophmore'";
        return first;
    }

    private static String[] query1(String one){
        String[] first=new String[5];
        first[0]="SELECT sum(MaritalStatus) FROM Person,Resident WHERE Person.StudentID=Resident.StudentID and MaritalStatus = "+one+" AND Graduation_Year >=2013";
        first[1]="SELECT sum(MaritalStatus) FROM Person,Resident WHERE Person.StudentID=Resident.StudentID and MaritalStatus = "+one+" AND Graduation_Year >=2014";
        first[2]="SELECT sum(MaritalStatus) FROM Person,Resident WHERE Person.StudentID=Resident.StudentID and MaritalStatus = "+one+" AND Graduation_Year >=2015 OR GradeLevel = 'Senior'";
        first[3]="SELECT sum(MaritalStatus) FROM Person,Resident WHERE Person.StudentID=Resident.StudentID and MaritalStatus = "+one+" AND Graduation_Year >=2016 OR GradeLevel = 'Senior' OR GradeLevel = 'Junior'";
        first[4]="SELECT sum(MaritalStatus) FROM Person,Resident WHERE Person.StudentID=Resident.StudentID and MaritalStatus = "+one+" AND Graduation_Year >=2017 OR GradeLevel = 'Senior' OR GradeLevel = 'Junior' OR GradeLevel = 'Sophmore'";
        return first;
    }
    private static void manageRes(Connection conn) {
        System.out.print("what is the ID of the Resident you want to update? ");
        int ID = scan.nextInt();
        System.out.print("what new room do you want to assign him to? ");
        int room = scan.nextInt();
        String query = "Update Resident set RoomNum=? where StudentID=?";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, room);
            pst.setString(2, ID);
            pst.executeQuery();
        } catch (SQLException e) {
        }
    }
    private static void manageMaintenanceOrder(Connection conn) {
        System.out.print("what is the ID of the Resident who submitted the request? ");
        int ID=scan.nextInt();
        System.out.print("what is the ID of the employee who fixed it? ");
        int ID2=scan.nextInt();
        String query="Update MaintenanceRequest set employee=? and status=Fixed where SubmitterID=? ";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1,ID2);
            pst.setString(2,ID);
            pst.executeQuery();
        } catch (SQLException e) {
        }
    }
    private static void adminReports() {

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
        String query = "Select ResName from Resident where StudentID=? and Password=?";
        //authentication here for resident
        boolean condition = execute(query, conn,id,pass);
        if (condition) {
            residentOptions();
            System.out.print("Enter an option ");
            int i = scan.nextInt();
            //perform operation
            resOperations(i, scan,conn);
        } else {
            System.out.println("Operation not allowed");
        }
    }

    private static void resOperations(int i, Scanner scan,Connection conn) {
        if (i == 1) submitMaintenance(scan,conn);
        else if (i == 2) checkStatus(scan,conn);
        else if (i == 3) maintenanceHistory(scan,conn);
    }

    public static void residentOptions() {
        System.out.println("1. Submit Maintenance Request");
        System.out.println("2. Check Status");
        System.out.println("3. Maintenance History");
    }

    public static void submitMaintenance(Scanner scan,Connection conn) {
//description of the maintenance request
        System.out.print("What is your ID? ");
        Int ID = scan.nextInt();
        System.out.print("What is your Building ID? ");
        int num = scan.nextInt();
        System.out.print("What is your Room Number? ");
        int roomnum = scan.nextInt();
        System.out.print("What is your floor number? ");
        int floornum = scan.nextInt();
        System.out.print("What is the date of today?(MM/DD/YYYY)");
        String date = scan.nextLine();
        System.out.print("Please Describe your request ");
        String description = scan.nextLine();
        System.out.print("What is your name? ");
        String resName = scan.nextLine();
        ////to insert into the Maintenance Table here
        String query="Insert Into MaintenanceRequest Values(0,?,?,?,?,'?','?','?','?',' ',' ')";
            try {
                PreparedStatement pst = conn.prepareStatement(query);
                pst.setInt(1,ID);
                pst.setInt(2,num);
                pst.setInt(3,roomnum);
                pst.setInt(4,floornum);
                pst.setString(5,date);
                pst.setString(6,description);
                pst.setString(7,resName);
                pst.executeQuery();
            } catch (SQLException e) {

            }
    }
    private static void checkStatus(Scanner scan,Connection conn) {
        System.out.print("What is your ID? ");
        int id = scan.nextInt();
        String query="Select status from maintenance where SubmitterID=?";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1,id);
            ResultSet r = pst.executeQuery();
            System.out.println(r.next());
        } catch (SQLException e) {

        }
    }
    private static void maintenanceHistory(Scanner scan,Connection conn) {
        System.out.print("What is your ID? ");
        Int id = scan.nextInt();
        String query="Select Description,SubmissionDate,Status from MaintenanceRequest where SubmitterID=?";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1,id);
            ResultSet r = pst.executeQuery();
            while(r.next()){
            system.out.println(r.getString(1)+" "+r.getString(2)+" "+r.getString(3))
            }
        } catch (SQLException e) {

        }
    }
}