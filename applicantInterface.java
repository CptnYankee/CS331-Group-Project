import java.io.*;
import java.sql.*;
import java.util.*;

public class applicantInterface {				
	private static int mySID;
	private static String Password;
	private static int prefRoomMateBOOL;
	private static int prefRoomMateSID;
	private static boolean isMarried;
	private static String prefRoomStyle;

	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		String url = "jdbc:mysql://localhost:3306/HousingProject_CS331?serverTimezone=UTC&useSSL=TRUE";
		String user = "student";
		String password = "password";
		Connection conn = DriverManager.getConnection(url, user, password);
		header();
		boolean grantAccess = authenticateApplicant(conn);
		if (grantAccess == false) { System.out.println("\n\t\tAccess to the Housing Application has been denied."); }
		if (grantAccess == true) {
			System.out.println("\n\t\tAccess granted.");
			boolean prefersRoommate = promptRoommatePreference();
			if (prefersRoommate == false) { displayRoomsNoPref(conn); }
			if (prefersRoommate == true) {
				prefRoomMateSID = promptRoommatePrefSID();
				isMarried = promptMarriedToRoommatePref();
				boolean prefIsResident = checkResidency(prefRoomMateSID, conn);
				if (prefIsResident) {
					System.out.println("Your preferred roommate is already a resident.");
					boolean available = checkEligibility(prefRoomMateSID, conn);
					if (available) { assignToRoommateRoom(prefRoomMateSID, conn); }
				} 
				else {
					if (isMarried == true) { displayRoomsMarried(conn); }
					if (isMarried == false) { displayRoomsWithPref(conn); }
				}
			}
		}
	}
	// Displays
	public static void header() {
		System.out.println("**************************************************************************************");
		System.out.println("|\t\t\t\t\t\t\t\t\t\t     |");
		System.out.println("|\t\t    Bellevue College Student Housing Application\t\t     |");
		System.out.println("|\t\t\t\t\t\t\t\t\t\t     |");
		System.out.println("**************************************************************************************");
	}
	public static void displayRoomsNoPref(Connection conn) throws SQLException {
		System.out.println("\nPlease choose from an available option.\n");
		System.out.println("\tRoom Style:\t\t\tRooms available:");
		int a = roomAvailability("Two Bedroom, Four Persons", 1, conn);
		int b = roomAvailability("Four Bedroom, Four Persons", 1, conn);
		int c = roomAvailability("One Bedroom, One Person", 1, conn);
		int d = roomAvailability("One Bedroom, Two Persons", 1, conn);
		int e = roomAvailability("Two Bedroom, Two Persons", 1, conn);
		int f = roomAvailability("Two Bedroom, Three Persons", 1, conn);
		System.out.println("[1] Two Bedroom Apartment, Four Persons\t\t\t" + a);
		System.out.println("[2] Four Bedroom Apartment, Four Persons\t\t" + b);
		System.out.println("[3] One Bedroom Suite, One Persons\t\t\t" + c);
		System.out.println("[4] One Bedroom Suite, Two Persons\t\t\t" + d);
		System.out.println("[5] Two Bedroom Suite, Two Persons\t\t\t" + e);
		System.out.println("[6] Two Bedroom Suite, Three Persons\t\t\t" + f);
		int selection;
		Scanner scIn = new Scanner(System.in);
		selection = scIn.nextInt();
		if (selection == 1) { assignRoomStyle("Two Bedroom, Four Persons", 1, conn); }
		if (selection == 2) { assignRoomStyle("Four Bedroom, Four Persons", 1, conn); }
		if (selection == 3) { assignRoomStyle("One Bedroom, One Person", 1, conn); }
		if (selection == 4) { assignRoomStyle("One Bedroom, Two Persons", 1, conn); }
		if (selection == 5) { assignRoomStyle("Two Bedroom, Two Persons", 1, conn); }
		if (selection == 6) { assignRoomStyle("Two Bedroom, Three Persons", 1, conn); }
		else if (selection < 1 || 6 < selection) { 
			System.out.println("\n\t\t\tInvalid option. Try again.");
			Scanner scIn2 = new Scanner(System.in);
			selection = scIn2.nextInt();
		}
	}
	public static void displayRoomsWithPref(Connection conn) throws SQLException {
		// *NOTE* --> These queries check for bedsAvailable >= 2
		System.out.println("\nPlease choose from an available option.\n");
		System.out.println("\tRoom Style:\t\t\tRooms available:");
		int a = roomAvailability("Two Bedroom, Four Persons", 2, conn);
		int b = roomAvailability("Four Bedroom, Four Persons", 2, conn);
		int c = roomAvailability("One Bedroom, One Person", 2, conn);
		int d = roomAvailability("One Bedroom, Two Persons", 2, conn);
		int e = roomAvailability("Two Bedroom, Two Persons", 2, conn);
		int f = roomAvailability("Two Bedroom, Three Persons", 2, conn);
		System.out.println("[1] Two Bedroom Apartment, Four Persons\t\t\t" + a);
		System.out.println("[2] Four Bedroom Apartment, Four Persons\t\t" + b);
		System.out.println("[3] One Bedroom Suite, One Persons\t\t\t" + c);
		System.out.println("[4] One Bedroom Suite, Two Persons\t\t\t" + d);
		System.out.println("[5] Two Bedroom Suite, Two Persons\t\t\t" + e);
		System.out.println("[6] Two Bedroom Suite, Three Persons\t\t\t" + f);
		int selection;
		Scanner scIn = new Scanner(System.in);
		selection = scIn.nextInt();
		if (selection == 1) { assignRoomStyle("Two Bedroom, Four Persons", 2, conn); }
		if (selection == 2) { assignRoomStyle("Four Bedroom, Four Persons", 2, conn); }
		if (selection == 3) { assignRoomStyle("One Bedroom, One Person", 2, conn); }
		if (selection == 4) { assignRoomStyle("One Bedroom, Two Persons", 2, conn); }
		if (selection == 5) { assignRoomStyle("Two Bedroom, Two Persons", 2, conn); }
		if (selection == 6) { assignRoomStyle("Two Bedroom, Three Persons", 2, conn); }
		else if (selection < 1 || 6 < selection) { 
			System.out.println("\n\t\t\tInvalid option. Try again.");
			Scanner scIn2 = new Scanner(System.in);
			selection = scIn2.nextInt();
		}
	}
	public static void displayRoomsMarried(Connection conn) throws SQLException {
		System.out.println("\nPlease choose from an available option.\n");
		System.out.println("\tRoom Style:\t\t\tRooms available:");
		int a = roomAvailability("One Bedroom, Two Persons", 1, conn);
		int b = roomAvailability("Two Bedroom, Two Persons", 1, conn);
		System.out.println("[1] One Bedroom Suite, Two Persons\t\t\t" + a);
		System.out.println("[2] Two Bedroom Suite, Two Persons\t\t\t" + b);
		int selection;
		Scanner scIn = new Scanner(System.in);
		selection = scIn.nextInt();
		if (selection == 1) { assignRoomStyle("One Bedroom, Two Persons", 1, conn); }
		if (selection == 2) { assignRoomStyle("Two Bedroom, Two Persons", 2, conn); }
		else if (selection < 1 || 2 < selection) { 
			System.out.println("\n\t\t\tInvalid option. Try again.");
			Scanner scIn2 = new Scanner(System.in);
			selection = scIn2.nextInt();
		}
	}
	// Authentication
	public static boolean authenticateApplicant(Connection conn) {
		int StudentID;
		String Password;
		System.out.print("Enter StudentID:\t\t");
		Scanner in = new Scanner(System.in);
		StudentID = in.nextInt();
		System.out.print("Enter Password:\t\t\t");
		Scanner in2 = new Scanner(System.in);
		Password = in2.nextLine();
		setUSER(StudentID, Password);
		boolean isApplicant = false;
		int room = getRoom(StudentID, conn);
		if (room == 0) { isApplicant = true; } // room == 0 to allow access during testing, otherwise room != 0
		return isApplicant;
	}
	public static void setUSER(int sid, String pw) {
		mySID = sid;
		Password = pw;
	}
	// Prompts User
	public static boolean promptRoommatePreference() {
		boolean yn = false;
		int input;
		System.out.println("\nDo you have a preferred roommate?\n\t\t\t[1] Yes\n\t\t\t[2] No\n");
		Scanner scInput = new Scanner(System.in);
		input = scInput.nextInt();
		if (input == 1) { yn = true; }
		if (input == 2) { yn = false; }
		else if (input != 1 && input != 2) {
			System.out.println("\n\t\t\tInvalid option");
			Scanner scInputRecursion = new Scanner(System.in);
			input = scInputRecursion.nextInt();
		}
		return yn;
	}
	public static int promptRoommatePrefSID() {
		int sID;
		System.out.print("\nPlease enter their SID:\t\t");
		Scanner in = new Scanner(System.in);
		sID = in.nextInt();
		return sID;
	}
	public static boolean promptMarriedToRoommatePref() {
		boolean yn = false;
		int in;
		System.out.println("Is this person your spouse? \n\t\t\t[1] Yes\n\t\t\t[2] No\n");
		Scanner scIn = new Scanner(System.in);
		in = scIn.nextInt();
		if (in == 1) { yn = true; }						
		else if(in == 2) { yn = false; }
		return yn;
	}
	// Queries
	public static boolean checkResidency(int sid, Connection conn) {
		int room = getRoom(sid, conn);
		if (room != 0) { System.out.println("Your pref roommate is not a resident."); return false; }
		else { System.out.println("Your pref roommate is a resident (testing)"); return true; } // true. false for testing
		
	}
	public static boolean checkEligibility(int sid, Connection conn) {
		int room = getRoom(sid, conn);
		int building = getBuilding(sid, conn);
		int bedsAvailable = 0; 
		int otherSID = 0; 
		String query = "SELECT SpaceAvailable, otherSID " +
						"FROM Room, Resident " +
						"WHERE Resident.StudentID = sid AND " +
								"Room.RoomNum = Resident.RoomNum AND " +
								"Room.RoomNum = ? AND " +
								"Room.BuildingID = ?";
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, room);
			ps.setInt(2,  building);
			ResultSet rs;
			rs = ps.executeQuery(query);
			bedsAvailable = rs.getInt(1);
			otherSID = rs.getInt(2);
		} catch (SQLException ex) {/* ignore */}
		if (bedsAvailable >= 1 && mySID == otherSID) { System.out.println("Applying to their room is available");return true; }
		else {System.out.println("Applying to their room is unavailable");return false;} // false. true for testing
	}
	public static int getRoom(int SID, Connection conn) {
		int room = 0; 
		String query = "SELECT RoomNum FROM Resident WHERE StudentID = ?";
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, SID);
			ResultSet rs;
			rs = ps.executeQuery(query);
			room = rs.getInt(1);
		} catch (SQLException ex) {/* ignore */}
		return room;
	}
	public static int getBuilding(int SID, Connection conn) {
		int building = 0;
		String query = "SELECT BuildingID FROM Resident WHERE StudentID = ?";
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, SID);
			ResultSet rs;
			rs = ps.executeQuery(query);
			building = rs.getInt(1);
		} catch (SQLException ex) {/* ignore */}
		return building;
	}
	public static int roomAvailability(String style, int minBeds, Connection conn) throws SQLException {
		int roomAvail = 0;
		String query = "SELECT COUNT(RoomNum) " +
						"FROM Room " +
						"WHERE Style = ? AND " +
							"SpaceAvailable >= minBeds";
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(query);
			stmt.setString(1,style);
			ResultSet rs;
			rs = stmt.executeQuery(query);
			roomAvail = rs.getInt(1);
		} catch (SQLException ex) { /*ignore*/ }
		return roomAvail;
	}
	public static void assignToRoommateRoom(int sid, Connection conn) {
		// fields to fill in and transfer from Applicant to Resident
		int roomNum = 0;
		int buildingID = 0;
		String pw = "";
		int ssn = 0;
		String AppName = "";
		String gender = "";
		boolean marStat = false;
		String address = "";
		int phone = 0;
		String major = "";
		String dept = "";
		int hSSN = 0;
		// Pull roomNum and buildingID
		String query = "SELECT RoomNum, BuildingID " +
						"FROM Resident " +
						"WHERE StudentID = ?";
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, sid);
			ResultSet rs;
			rs = stmt.executeQuery(query);
			roomNum = rs.getInt(1);
			buildingID = rs.getInt(2);
		} catch (SQLException ex) {/* ignore */}
		// Transfer over personal information from Applicant to Resident
		String query2 = "SELECT * " +
						"FROM Applicant " +
						"WHERE StudentID = ? ";
		PreparedStatement stmt2 = null;
		try {
			stmt2 = conn.prepareStatement(query2);
			stmt.setInt(1, mySID);
			ResultSet rs2;
			rs2 = stmt2.executeQuery(query2);
			pw = rs2.getString(2);
			ssn = rs2.getInt(3);
			AppName = rs2.getString(4);
			gender = rs2.getString(5);
			marStat = rs2.getBoolean(6);
			address = rs2.getString(7);
			phone = rs2.getInt(8);
			major = rs2.getString(9);
			dept = rs2.getString(10);
			hSSN = rs2.getInt(11);
		} catch (SQLException ex) {/* ignore */}
		// Create a Resident tuple with Applicant info and AssignedRoom#
		String insert = "INSERT INTO Resident VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";			
		PreparedStatement stmt3 = null;
		try {
			stmt3 = conn.prepareStatement(insert);
			stmt3.setInt(1, mySID);
			stmt3.setInt(2, roomNum);
			stmt3.setInt(3, buildingID);
			stmt3.setString(4, pw);
			stmt3.setInt(5, ssn);
			stmt3.setString(6, AppName);
			stmt3.setString(7, gender);
			stmt3.setBoolean(8, marStat);
			stmt3.setString(9, address);
			stmt3.setInt(10, phone);
			stmt3.setString(11, major);
			stmt3.setString(12, dept);
			stmt3.setInt(13, hSSN);
			ResultSet rs3;
			rs3 = stmt3.executeQuery(insert);
		} catch (SQLException ex) {/* ignore */}
		// Remove Applicant from relation
		String dlt = "DELETE FROM TABLE Applicant WHERE StudentID = ?";
		PreparedStatement stmt4 = null;
		try {
			stmt4 = conn.prepareStatement(dlt);
			stmt4.setInt(1, mySID);
			ResultSet rs4;
			rs4 = stmt4.executeQuery(dlt);
		} catch (SQLException ex) {/* ignore */}
		// Decrement "SpaceAvailable" attribute for the Assigned Room
		String update = "UPDATE Room SET SpaceAvailable = (SpaceAvailable - 1) " +
						"WHERE RoomNum = ? AND BuildingID = ?";
		PreparedStatement stmt5 = null;
		try {
			stmt5 = conn.prepareStatement(update);
			stmt5.setInt(1, roomNum);
			stmt5.setInt(2, buildingID);
			ResultSet rs5;
			rs5 = stmt5.executeQuery(update);
		} catch (SQLException ex) {/* ignore */}
		System.out.println("You have been added to their room.");
	}	
	public static void assignRoomStyle(String style, int minBeds, Connection conn) {
		// pull room# & BuilidingID of 1st room with matching description (style) and minimum number of beds (minBeds) available
		int roomNum = 0;
		int buildingID = 0;
		int sid = mySID;
		String pw = "";
		int ssn = 0;
		String AppName = "";
		String gender = "";
		boolean marStat = false;
		String address = "";
		int phone = 0;
		String major = "";
		String dept = "";
		int hSSN = 0;
		String query = "SELECT RoomNum, BuildingID " +
						"FROM Room " +
						"WHERE Style = ? AND " +
							"SpaceAvailable >= minBeds " +
						"LIMIT 1";
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(query);
			stmt.setString(1,style);
			ResultSet rs;
			rs = stmt.executeQuery(query);
			roomNum = rs.getInt(1);
			buildingID = rs.getInt(2);
			System.out.println("We have chosen a room for you.");
		} catch (SQLException ex) {/* ignore */}
		// Transfer over personal information from Applicant to Resident
		String query2 = "SELECT * " +
						"FROM Applicant " +
						"WHERE StudentID = ? ";
		PreparedStatement stmt2 = null;
		try {
			stmt2 = conn.prepareStatement(query2);
			stmt.setInt(1, mySID);
			ResultSet rs2;
			rs2 = stmt2.executeQuery(query2);
			pw = rs2.getString(2);
			ssn = rs2.getInt(3);
			AppName = rs2.getString(4);
			gender = rs2.getString(5);
			marStat = rs2.getBoolean(6);
			address = rs2.getString(7);
			phone = rs2.getInt(8);
			major = rs2.getString(9);
			dept = rs2.getString(10);
			hSSN = rs2.getInt(11);
			System.out.println("Your application file has been pulled");
		} catch (SQLException ex) {/* ignore */}
		// Create a Resident tuple with Applicant info and AssignedRoom#
		String insert = "INSERT INTO Resident VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";			
		PreparedStatement stmt3 = null;
		try {
			stmt3 = conn.prepareStatement(insert);
			stmt3.setInt(1, mySID);
			stmt3.setInt(2, roomNum);
			stmt3.setInt(3, buildingID);
			stmt3.setString(4, pw);
			stmt3.setInt(5, ssn);
			stmt3.setString(6, AppName);
			stmt3.setString(7, gender);
			stmt3.setBoolean(8, marStat);
			stmt3.setString(9, address);
			stmt3.setInt(10, phone);
			stmt3.setString(11, major);
			stmt3.setString(12, dept);
			stmt3.setInt(13, hSSN);
			ResultSet rs3;
			rs3 = stmt3.executeQuery(insert);
			System.out.println("You are now a resident");
			System.out.println("You have been assigned to\troom: "+roomNum+"\n\t\t\t\tbuilding: "+buildingID);
		} catch (SQLException ex) {/* ignore */}
		// Remove Applicant from relation
		String dlt = "DELETE FROM TABLE Applicant WHERE StudentID = ?";
		PreparedStatement stmt4 = null;
		try {
			stmt4 = conn.prepareStatement(dlt);
			stmt4.setInt(1, mySID);
			ResultSet rs4;
			rs4 = stmt4.executeQuery(dlt);
			System.out.println("You have been removed from the Applicant's table");
		} catch (SQLException ex) {/* ignore */}
		// Decrement "SpaceAvailable" attribute for the Assigned Room
		String update = "UPDATE Room SET SpaceAvailable = (SpaceAvailable - 1) " +
						"WHERE RoomNum = ? AND BuildingID = ?";
		PreparedStatement stmt5 = null;
		try {
			stmt5 = conn.prepareStatement(update);
			stmt5.setInt(1, roomNum);
			stmt5.setInt(2, buildingID);
			ResultSet rs5;
			rs5 = stmt5.executeQuery(update);
			System.out.println("There is now 1 less space open in room "+buildingID+"-"+roomNum);
		} catch (SQLException ex) {/* ignore */}
	}

}