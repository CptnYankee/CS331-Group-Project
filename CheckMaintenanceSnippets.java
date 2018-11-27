//this snippet assumes a connection conn already exists.

statment = conn.createStatement();
//For resident
//This gets us all maintenance requests from this resident.
String sql = "SELECT RequestID, SubmitterID, RoomNum, FloorNum, BuildingID, SubmissionDate, ClearDate, Description, Employee" +
"FROM MaintenanceRequest"+
"WHERE SubmitterID =" + userID;
ResultSet userRequests = statement.executeQuery(sql);
while(userRequests.next()){
	//RequestID
	System.out.print(userRequests.getInt(1) + " ");
	//SubmitterID
	System.out.print(userRequests.getInt(2) + " ");
	//RoomNum
	System.out.print(userRequests.getInt(3) + " ");
	//FloorNum
	System.out.print(userRequests.getInt(4) + " ");
	//BuildingID
	System.out.print(userRequests.getInt(4) + " ");
	//SubmissionDate
	System.out.print(userRequests.getDate(5) + " ");
	//ClearDate
	System.out.print(userRequests.getDate(6) + " ");
	//Description
	System.out.print(userRequests.getString(7) + " ");
	//Employee
	System.out.print(userRequests.getString(8) + "\n");
	}

statment = conn.createStatement();
//For resident
//This gets us all the open maintenance requests for this resident
String sql = "SELECT RequestID, SubmitterID, RoomNum, FloorNum, BuildingID, SubmissionDate, Description" +
"FROM MaintenanceRequest"+
"WHERE SubmitterID =" + userID +
"AND ClearDate = NULL";
ResultSet userOpenRequests = statement.executeQuery(sql);
while(userOpenRequests.next()){
	//RequestID
	System.out.print(userOpenRequests.getInt(1) + " ");
	//SubmitterID
	System.out.print(userOpenRequests.getInt(2) + " ");
	//RoomNum
	System.out.print(userOpenRequests.getInt(3) + " ");
	//FloorNum
	System.out.print(userOpenRequests.getInt(4) + " ");
	//BuildingID
	System.out.print(userOpenRequests.getInt(4) + " ");
	//SubmissionDate
	System.out.print(userOpenRequests.getDate(5) + " ");
	//Description
	System.out.print(userOpenRequests.getString(6) + "\n");
	}

statment = conn.createStatement();
//For admin
//This gets us all the open maintenance requests for the date specified YYYY-MM-DD
String sql = "SELECT RequestID, SubmitterID, RoomNum, FloorNum, BuildingID, SubmissionDate, Description" +
"FROM MaintenanceRequest"+
"WHERE SubmissionDate =" + date +
"OR ClearDate =" + date +
"ORDERED BY SubmissionDate";
ResultSet manageByDate = statement.executeQuery(sql);
while(manageByDate.next()){
	//RequestID
	System.out.print(manageByDate.getInt(1) + " ");
	//SubmitterID
	System.out.print(manageByDate.getInt(2) + " ");
	//RoomNum
	System.out.print(manageByDate.getInt(3) + " ");
	//FloorNum
	System.out.print(manageByDate.getInt(4) + " ");
	//BuildingID
	System.out.print(manageByDate.getInt(4) + " ");
	//SubmissionDate
	System.out.print(manageByDate.getDate(5) + " ");
	//Description
	System.out.print(manageByDate.getString(6) + "\n");
	}
