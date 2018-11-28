import java.io.*;
import java.util.Scanner;

public class applicantInterface {				
	private static int mySID;
	private static int prefRoomMateBOOL;
	private static int prefRoomMateSID;
	private static boolean isMarried;
	private static String prefRoomStyle;

	public static void main(String[] args) {
		//int prefRoomMateBOOL;
		//int prefRoomMateSID;
		//boolean isMarried = false;
		header();
		boolean grantAccess = authenticateApplicant();
		if (grantAccess == false) {
			System.out.println("\n\t\tAccess to the Housing Application has been denied.");
		}
		if (grantAccess == true) {
			System.out.println("\n\t\tAccess granted.");
			boolean prefersRoommate = promptRoommatePreference();
			if (prefersRoommate == false) { displayRoomsNoPref(); }
			if (prefersRoommate == true) {
				prefRoomMateSID = promptRoommatePrefSID();
				isMarried = promptMarriedToRoommatePref();
				boolean prefIsResident = checkResidency(prefRoomMateSID);
				if (prefIsResident) {
					boolean available = checkEligibility(prefRoomMateSID);
					if (available) { 
						// Search query, pull prefRoomMate's room#
						// Alter statement, give this person that same room#
						// 
					}
				} else {
					if (isMarried == true) { displayRoomsMarried(); }
					if (isMarried == false) { displayRoomsWithPref(); }
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
	public static void displayRoomsNoPref() {
		System.out.println("\nPlease choose from an available option:\n");
		// QueryA for following
		System.out.println("[1] Two Bedroom Apartment, Four Persons\t\tA");
		// QueryB for following
		System.out.println("[2] Four Bedroom Apartment, Four Persons\tB");
		// QueryC for following
		System.out.println("[3] One Bedroom Suite, One Persons\t\tC");
		// QueryD for following
		System.out.println("[4] One Bedroom Suite, Two Persons\t\tD");
		// QueryE for following
		System.out.println("[5] Two Bedroom Suite, Two Persons\t\tE");
		// QueryF for following
		System.out.println("[6] Two Bedroom Suite, Three Persons\tF");				
		int selection;
		Scanner scIn = new Scanner(System.in);
		selection = scIn.nextInt();
		if (selection == 1) { assignRoomStyle("Two Bedroom, Four Persons", 1); }
		if (selection == 2) { assignRoomStyle("Four Bedroom, Four Persons", 1); }
		if (selection == 3) { assignRoomStyle("One Bedroom, One Persons", 1); }
		if (selection == 4) { assignRoomStyle("One Bedroom, Two Persons", 1); }
		if (selection == 5) { assignRoomStyle("Two Bedroom, Two Persons", 1); }
		if (selection == 6) { assignRoomStyle("Two Bedroom, Three Persons", 1); }
		else if (selection < 1 || 6 < selection) { 
			System.out.println("\n\t\t\tInvalid option. Try again.");
			Scanner scIn2 = new Scanner(System.in);
			selection = scIn2.nextInt();
		}
	}
	public static void displayRoomsWithPref() {
		// *NOTE* --> These queries check for bedsAvailable >= 2
		// For testing:
		System.out.println("\nPlease choose from an available option:");
		// QueryA for following
		System.out.println("[1] Two Bedroom Apartment, Four Persons\t\tA");
		// QueryB for following
		System.out.println("[2] Four Bedroom Apartment, Four Persons\tB");
		// QueryC for following
		System.out.println("[3] One Bedroom Suite, One Persons\t\tC");
		// QueryD for following
		System.out.println("[4] One Bedroom Suite, Two Persons\t\tD");
		// QueryE for following
		System.out.println("[5] Two Bedroom Suite, Two Persons\t\tE");
		// QueryF for following
		System.out.println("[6] Two Bedroom Suite, Three Persons\tF");
		int selection;
		Scanner scIn = new Scanner(System.in);
		selection = scIn.nextInt();
		if (selection == 1) { assignRoomStyle("Two Bedroom, Four Persons", 2); }
		if (selection == 2) { assignRoomStyle("Four Bedroom, Four Persons", 2); }
		if (selection == 3) { assignRoomStyle("One Bedroom, One Persons", 2); }
		if (selection == 4) { assignRoomStyle("One Bedroom, Two Persons", 2); }
		if (selection == 5) { assignRoomStyle("Two Bedroom, Two Persons", 2); }
		if (selection == 6) { assignRoomStyle("Two Bedroom, Three Persons", 2); }
		else if (selection < 1 || 6 < selection) { 
			System.out.println("\n\t\t\tInvalid option. Try again.");
			Scanner scIn2 = new Scanner(System.in);
			selection = scIn2.nextInt();
		}
	}
	public static void displayRoomsMarried() {
		// For testing:
		System.out.println("\nPlease choose from an available option:");
		// QueryA for following
		System.out.println("[1] One Bedroom Suite, Two Persons\t\tA");
		// QueryB for following
		System.out.println("[2] Two Bedroom Suite, Two Persons\t\tB");
		int selection;
		Scanner scIn = new Scanner(System.in);
		selection = scIn.nextInt();
		if (selection == 1) { assignRoomStyle("One Bedroom, Two Persons", 1); }
		if (selection == 2) { assignRoomStyle("Two Bedroom, Two Persons", 2); }
		else if (selection < 1 || 2 < selection) { 
			System.out.println("\n\t\t\tInvalid option. Try again.");
			Scanner scIn2 = new Scanner(System.in);
			selection = scIn2.nextInt();
		}
	}
	// Authentication
	public static boolean authenticateApplicant() {
		int StudentID;
		String Password;
		System.out.print("Enter StudentID:\t\t");
		Scanner in = new Scanner(System.in);
		StudentID = in.nextInt();
		mySID = StudentID;
		System.out.print("Enter Password:\t\t\t");
		Scanner in2 = new Scanner(System.in);
		Password = in2.nextLine();
		boolean isApplicant = false;
		int room = getRoom(StudentID);
		if (room == 0) { isApplicant = true; } // room == 0 to allow access during testing, otherwise room != 0
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
	// Checks
	public static boolean checkResidency(int sid) {
		int room = getRoom(sid);
		if (room != 0) { return false; }
		else { return false; } // true. false for testing
		
	}
	public static boolean checkEligibility(int sid) {
		int room = getRoom(sid);
		int bedsAvailable = 1; // For testing. For actual code: = (Query) SELECT bedsAvailable FROM Room WHERE Room# = room;
		int otherSID = 1; // For testing. For actual code: = (Query) SELECT RoommatePref FROM Person WHERE sid = Person.sid;
		if (bedsAvailable >= 1 && mySID == otherSID) { return true; }
		else {return true;} // false. true for testing
	}
	// Accessor
	public static int getRoom(int SID) {
		int room = 0; 
		// QUERY to retrieve room#'s using SID
		return room;
	}
	public static void assignRoomStyle(String style, int minBeds) {
		// search query, pull room# of a 1st room with matching description (style) and minimum number of beds (minBeds) available
	}
	
}