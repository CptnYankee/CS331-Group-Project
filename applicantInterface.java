import java.io.*;
import java.util.Scanner;

@SuppressWarnings("unused")
public class applicantInterface {				
	@SuppressWarnings({ "resource", "unused" })
	public static void main(String[] args) {
		
		int prefRoomMateBOOL;
		int prefRoomMateSID;
		boolean isMarried = false;
		System.out.println("\tWelcome to the Housing Application");
		System.out.println("\nDo you have a preferred roommate?\n\t[1] Yes\n\t[2] No\n");
		Scanner input1 = new Scanner(System.in);
		prefRoomMateBOOL = input1.nextInt();
		// if there is a preferred RoomMate
		//		ask&flag if it is their spouse
		//		see if prefRoomMate is a resident (They have a room# in their Person tuple)
		//				if they are, and if they prefer the user and they have space available
		//					add them to the room
		//				if they aren't,
		//					and if they are their prefRoomMate is their spouse
		//						display availability of rooms eligible for married couples
		//						add them to the room they choose or if none are available print sorry message
		//					or if its not their spouse
		//						display availability of rooms with more than two beds
		//						add them to the room they choose or if none are available print sorry message
		if (prefRoomMateBOOL == 1) {
			boolean prefRoomMateisRes;
			int isPrefRoomMateSpouse;
			System.out.print("\nPlease enter their SID:\t\t");
			Scanner input2 = new Scanner(System.in);
			prefRoomMateSID = input2.nextInt();
			// ALTER Person --> assign prefRoomMateSID
			System.out.println("Is this person your spouse? \n\t[1] Yes\n\t[2] No\n");
			Scanner input3 = new Scanner(System.in);
			isPrefRoomMateSpouse = input3.nextInt();
			if (isPrefRoomMateSpouse == 1) { isMarried = true; }						// flag
			else if(isPrefRoomMateSpouse == 2) { isMarried = false; }					// flag
			String prefRoomMateRoom = null; /* NULL when not testing */
			boolean roomMatePrefMatches = false; /* FALSE when not testing */
			boolean spaceAvailable = false; /* FALSE when not testing */
			// prefRoomMateRoom = (QUERY) Check if prefRoomMate is a resident (do they have a room# != null) (select room#)?
			if (prefRoomMateRoom != null) {		
				prefRoomMateisRes = true;
				System.out.println("\t\t\t\t\tYour preferred roommate has a room"); // for testing
			 /* PseudoCode:
			 * 	(QUERY) Check if this.StudentID == <Roommate's>.prefRoomMate.StudentID
			 * 			if yes: 	roomMatePrefMatches = true
			 * 	(QUERY) Check if prefRoomMate.Room.bedsAvailable >= 1
			 * 			if yes:		spaceAvailable = true 
			 */
			 	if (spaceAvailable == roomMatePrefMatches == true) {
			 		System.out.println("\t\t\t\t\tYou have been added to their room"); // for testing
			 		// PseudoCode:
			  		// (ALTER RESIDENT) add this to resident with room# = prefRoomMateRoom
			  		// (ALTER APPLICANT) remove this from applicant
			 	}	 	
			} else if (prefRoomMateRoom == null) { 
				prefRoomMateisRes = false;
				System.out.println("\t\t\t\t\tYour preferred roommate does not have a room."); // for testing
				if (isMarried == true) { 
					System.out.println("\n\t\t\t\t\tYou are married to your preferred roommate");
					System.out.println("\t\t\t\t\t*Lists availability of rooms eligble for married couples"); // for testing
					// PseudoCode: 
					// (QUERY) display availability of "One bedroom, two persons" rooms
					// (QUERY) display availability of "Two bedroom, two persons" rooms
					// if none are returned, print an error message (no rooms to accommodate for married couples are available)
					// else add person to that room
				}
				else if (isMarried == false) {
					System.out.println("\n\t\t\t\t\tYou are not married to your preferred roommate");
					System.out.println("\t\t\t\t\t*Lists availibility of rooms that can accommodate roommates"); // for testing
					// PseudoCode:
					// (QUERY) display availability of * rooms having bedsAvailable >=2
					// if none are returned, print an error message (no rooms to accommodate for a preferred roommate are available) 
					// else add person to that room
				}
			} 	
		}
		if (prefRoomMateBOOL == 2) {
			System.out.println("\t\t\t\t\tYou don't have a preferred roommate");
			System.out.println("\t\t\t\t\t*Lists availability of all rooms");
			// PseudoCode:
			// (QUERY) display availability of * rooms having besAvailable >= 1
			// if none are returned, print an error message (no rooms available)
			// else add person to that room
		}
		else if (prefRoomMateBOOL != 1 && prefRoomMateBOOL != 2){ System.out.println("\t\t\t\tError!\n\t\t\tInvalid option."); }
	}
}