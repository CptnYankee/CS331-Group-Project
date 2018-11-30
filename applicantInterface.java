import java.io.*;
import java.sql.*;
import java.util.*;

public class applicantInterface {				
	private static String mySID = "1538913561";
	private static String prefRoomMateSID;
	private static boolean isMarried;
	// rename main() to applicant(conn) when implementing into final code
	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		// Establish connection. Can be deleted when implemented into final code 
		/*
		Class.forName("com.mysql.cj.jdbc.Driver");
		String url = "jdbc:mysql://localhost:3306/HousingProject_CS331?serverTimezone=UTC&useSSL=TRUE";
		String user = "student";
		String password = "password";
		Connection conn = DriverManager.getConnection(url, user, password);
		// End of establishing connection statements, beginning of applicant(conn)
		*/
		Connection conn = null;
		try {
			// Establish connection. Can be deleted when implemented into final code
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/HousingProject_CS331?serverTimezone=UTC&useSSL=TRUE";
			String user = "student";
			String password = "password";
			conn = DriverManager.getConnection(url, user, password);
			// End of establishing connection statements, beginning of applicant(conn)
			boolean done = false;
			// Interface Recursion
			do {
				header();
/*				String name;
				String query = "Select Name FROM Person WHERE StudentID = ? ";
				PreparedStatement ps = null;
				try {
					ps = conn.prepareStatement(query);
					ps.setString(1, mySID);
					ResultSet rs;
					rs = ps.executeQuery();
					rs.next();
					name = rs.getString(1);
					System.out.println(name);
				} catch (SQLException ex) {
					System.out.println(ex.getMessage() + ex.getSQLState()
														+ ex.getErrorCode());
				}
				done = true;
/*/
				boolean grantAccess = authenticateApplicant(conn);
				if (grantAccess == false) { System.out.println("\n\t\tAccess to the Housing Application has been denied."); }
				if (grantAccess == true) {
					System.out.println("\n\t\tAccess granted.");
					boolean prefersRoommate = promptRoommatePreference();
					if (prefersRoommate == false) { displayRoomsNoPref(conn); done = true; }
					if (prefersRoommate == true) {
						prefRoomMateSID = promptRoommatePrefSID(conn);
						isMarried = promptMarriedToRoommatePref();
						boolean prefIsResident = checkResidency(prefRoomMateSID, conn);
						if (prefIsResident) {
							System.out.println("Your preferred roommate is already a resident.");
							boolean available = checkEligibility(prefRoomMateSID, conn);
							if (available) { assignToRoommateRoom(prefRoomMateSID, conn); }
							 done = true;
						} 
						else {
							if (isMarried == true) { displayRoomsMarried(conn); }
							if (isMarried == false) { displayRoomsWithPref(conn); }
							 done = true;
						}
					}
				}
			} while (!done);
		} catch (ClassNotFoundException e) {
			System.out.println("Could not load the Driver.");
		} catch (SQLException ex) {
			System.out.println(ex);
		} finally {
			if(conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {/*ignored*/}
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
		// Interface: display available options
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
		// Console input
		int selection;
		Scanner scIn = new Scanner(System.in);
		selection = scIn.nextInt();
		// Assign to room based on input
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
		// Interface: display available options
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
		// Interface: display available options
		System.out.println("\nPlease choose from an available option.\n");
		System.out.println("\tRoom Style:\t\t\tRooms available:");
		int a = roomAvailability("One Bedroom, Two Persons", 1, conn);
		int b = roomAvailability("Two Bedroom, Two Persons", 1, conn);
		System.out.println("[1] One Bedroom Suite, Two Persons\t\t\t" + a);
		System.out.println("[2] Two Bedroom Suite, Two Persons\t\t\t" + b);
		// Console input
		int selection;
		Scanner scIn = new Scanner(System.in);
		selection = scIn.nextInt();
		// Assign to room based on input
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
		boolean isApplicant = true;
		String StudentID;
		String Password;
		System.out.print("Enter StudentID:\t\t");
		Scanner in = new Scanner(System.in);
		StudentID = in.nextLine();
		mySID = StudentID;
		System.out.print("Enter Password:\t\t\t");
		Scanner in2 = new Scanner(System.in);
		Password = in2.nextLine();
        String query="SELECT Name FROM Resident WHERE StudentID=?";
        PreparedStatement pst = null;
		try {
            pst = conn.prepareStatement(query);
            pst.setString(1,StudentID);
            ResultSet r = pst.executeQuery();
            isApplicant = false; 
        } catch (SQLException e) {
        	System.out.println("catch");
        }
		return isApplicant;
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
	public static String promptRoommatePrefSID(Connection conn) {
		String sID;
		System.out.print("\nPlease enter their SID:\t\t");
		Scanner in = new Scanner(System.in);
		sID = in.nextLine();
		prefRoomMateSID = sID;
		updatePrefRoomate(prefRoomMateSID, conn);
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
	public static boolean checkResidency(String prefSID, Connection conn) {
		int room = getRoom(prefSID, conn);
		if (room != 0) { System.out.println("Your pref roommate is not a resident."); return false; } // == 0 for testing, != 0 when not testing.
		else { System.out.println("Your pref roommate is a resident (default response until data is input)"); return true; } // true. false for testing
		
	}
	public static boolean checkEligibility(String prefSID, Connection conn) {
		int room = getRoom(prefSID, conn);
		int building = getBuilding(prefSID, conn);
		int bedsAvailable = 0; 
		String otherSID = ""; 
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
			ps.setInt(2, building);
			ResultSet rs;
			rs = ps.executeQuery();
			rs.next();
			bedsAvailable = rs.getInt(1);
			otherSID = rs.getString(2);
		} catch (SQLException ex) {
			System.out.println(ex.getMessage() + ex.getSQLState()
								+ ex.getErrorCode());
		}
		if (bedsAvailable >= 1 && mySID == otherSID) { System.out.println("Applying to their room is available");return true; }
		else {System.out.println("Applying to their room is unavailable");return false;} // false. true for testing
	}
	public static int getRoom(String sid, Connection conn) {
		int room = 0;
		String query = "SELECT RoomNum FROM Resident WHERE StudentID = ? ";
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, sid);
			ResultSet rs;
			rs = ps.executeQuery();
			if(rs.next()) { room = rs.getInt(1); }
		} catch (SQLException ex) {
			System.out.println(ex.getMessage() + ex.getSQLState()
			+ ex.getErrorCode());
		}
		return room;
	}
	public static int getBuilding(String SID, Connection conn) {
		int building = 0;
		String query = "SELECT BuildingID FROM Resident WHERE StudentID = ?";
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, SID);
			ResultSet rs;
			rs = ps.executeQuery();
			rs.next();
			building = rs.getInt(1);
		} catch (SQLException ex) {
			System.out.println(ex.getMessage() + ex.getSQLState()
			+ ex.getErrorCode());
		}
		return building;
	}
	private static void updatePrefRoomate(String pref, Connection conn) {
		String update = "UPDATE Applicant SET PrefRoomate = ? WHERE StudentID = ?";
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(update);
			ps.setString(1, pref);
			ps.setString(2, mySID);
			ps.executeUpdate();
			System.out.println(pref + " has been set as your preferred roommate.");
		} catch(SQLException ex) {/*ignore*/}
	}	
	public static int roomAvailability(String style, int minBeds, Connection conn) throws SQLException {
		int roomAvail = 0;
		String query = "SELECT COUNT(RoomNum) " +
						"FROM Room " +
						"WHERE Style = ? AND " +
							"SpaceAvailable >= ?";
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(query);
			stmt.setString(1,style);
			stmt.setInt(2, minBeds);
			ResultSet rs;
			rs = stmt.executeQuery();
			rs.next();
			roomAvail = rs.getInt(1);
		} catch (SQLException ex) { 
			System.out.println(ex.getMessage() + ex.getSQLState()
			+ ex.getErrorCode());
		}
		return roomAvail;
	}
	public static void assignToRoommateRoom(String prefSID, Connection conn) {
		// fields to fill in and transfer from Applicant to Resident
		int roomNum = 0;
		int buildingID = 0;
		// Pull roomNum and buildingID
		String query = "SELECT RoomNum, BuildingID " +
						"FROM Resident " +
						"WHERE StudentID = ?";
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(query);
			stmt.setString(1, prefSID);
			ResultSet rs;
			rs = stmt.executeQuery();
			rs.next();
			roomNum = rs.getInt(1);
			buildingID = rs.getInt(2);
		} catch (SQLException ex) {
			System.out.println(ex.getMessage() + ex.getSQLState()
			+ ex.getErrorCode());
		}
		// Transfer over personal information from Applicant to Resident
		// Create a Resident tuple with Applicant info and AssignedRoom#
		String insert = "INSERT INTO Resident VALUES(?,?,?,?)";			
		PreparedStatement stmt3 = null;
		try {
			stmt3 = conn.prepareStatement(insert);
			stmt3.setString(1, mySID);
			stmt3.setInt(2, buildingID);
			stmt3.setInt(3, roomNum);
			stmt3.setString(4, prefRoomMateSID);
			stmt3.executeUpdate();
			System.out.println("You have been added to their room.");
		} catch (SQLException ex) {
			System.out.println(ex.getMessage() + ex.getSQLState()
			+ ex.getErrorCode());
		}
		// Remove Applicant from relation
		String dlt = "DELETE FROM TABLE Applicant WHERE StudentID = ?";
		PreparedStatement stmt4 = null;
		try {
			stmt4 = conn.prepareStatement(dlt);
			stmt4.setString(1, mySID);
			stmt4.executeUpdate();
			System.out.println("You have been removed from Applicant table");
		} catch (SQLException ex) {
			System.out.println(ex.getMessage() + ex.getSQLState()
			+ ex.getErrorCode());
		}
		// Decrement "SpaceAvailable" attribute for the Assigned Room
		String update = "UPDATE Room SET SpaceAvailable = (SpaceAvailable - 1) " +
						"WHERE RoomNum = ? AND BuildingID = ?";
		PreparedStatement stmt5 = null;
		try {
			stmt5 = conn.prepareStatement(update);
			stmt5.setInt(1, roomNum);
			stmt5.setInt(2, buildingID);
			stmt5.executeUpdate();
			System.out.println("Room "+buildingID+"-"+roomNum+" has 1 less bed available");
		} catch (SQLException ex) {
			System.out.println(ex.getMessage() + ex.getSQLState()
			+ ex.getErrorCode());
		}
	}	
	public static void assignRoomStyle(String style, int minBeds, Connection conn) {
		// pull room# & BuilidingID of 1st room with matching description (style) and minimum number of beds (minBeds) available
		int roomNum = 0;
		int buildingID = 0;
		String query = "SELECT RoomNum, BuildingID " +
						"FROM Room " +
						"WHERE Style = ? AND " +
							"SpaceAvailable >= ? " +
						"LIMIT 1";
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(query);
			stmt.setString(1,style);
			stmt.setInt(2, minBeds);
			ResultSet rs;
			rs = stmt.executeQuery();
			rs.next();
			roomNum = rs.getInt(1);
			buildingID = rs.getInt(2);
			System.out.println("We have chosen a room for you.");
		} catch (SQLException ex) {
			System.out.println(ex.getMessage() + ex.getSQLState()
			+ ex.getErrorCode());
		}
		// Transfer over personal information from Applicant to Resident
		// Create a Resident tuple with Applicant info and AssignedRoom#
		String insert = "INSERT INTO Resident VALUES(?,?,?,?)";			
		PreparedStatement stmt3 = null;
		try {
			stmt3 = conn.prepareStatement(insert);
			stmt3.setString(1, mySID);
			stmt3.setInt(2, buildingID);
			stmt3.setInt(3, roomNum);
			stmt3.setString(4, prefRoomMateSID);
			stmt3.executeUpdate();
			System.out.println("You are now a resident");
			System.out.println("You have been assigned to\troom: "+roomNum+"\n\t\t\t\tbuilding: "+buildingID);
		} catch (SQLException ex) {
			System.out.println(ex.getMessage() + ex.getSQLState()
			+ ex.getErrorCode());
		}
		// Remove Applicant from relation
		String dlt = "DELETE FROM Applicant WHERE StudentID = ?";
		PreparedStatement stmt4 = null;
		try {
			stmt4 = conn.prepareStatement(dlt);
			stmt4.setString(1, mySID);
			stmt4.executeUpdate();
			System.out.println("You have been removed from the Applicant's table");
		} catch (SQLException ex) {
			System.out.println(ex.getMessage() + ex.getSQLState()
			+ ex.getErrorCode());
		}
		// Decrement "SpaceAvailable" attribute for the Assigned Room
		String update = "UPDATE Room SET SpaceAvailable = (SpaceAvailable - 1) " +
						"WHERE RoomNum = ? AND BuildingID = ?";
		PreparedStatement stmt5 = null;
		try {
			stmt5 = conn.prepareStatement(update);
			stmt5.setInt(1, roomNum);
			stmt5.setInt(2, buildingID);
			stmt5.executeUpdate();
			System.out.println("There is now 1 less space open in room "+buildingID+"-"+roomNum);
		} catch (SQLException ex) {
			System.out.println(ex.getMessage() + ex.getSQLState()
			+ ex.getErrorCode());
		}
	}

}