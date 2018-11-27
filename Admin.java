
    public static void admin(Scanner scan){
        System.out.print("Enter UserId: ");
        String userid=scan.nextLine();
        System.out.print("Enter Password: ");
        String password=scan.nextLine();
        //////to authenticate from Database here here/////
        /////required to be userid=admin and password=admin/////
        display();
        adminOptions();
        System.out.println("Type in your option: ");
        int choice=scan.nextInt();
        operations(choice);
    }
         private static void display(){
             System.out.println("**************************************************************************************");
             System.out.println("                    Welcome to Bellevue College Housing System                        ");
             System.out.println("                                  Administrators Staff                                ");
             System.out.println();
             System.out.println("**************************************************************************************");
         }
         private static void adminOptions(){
          System.out.println("1. Manage Residents");
          System.out.println("2. Manage Applicants");
          System.out.println("3. Demographic Studies");
          System.out.println("4. Manage Maintenance orders");
          System.out.println("5. Administrative Reports");
          System.out.println("6. Quit");
         }
         private static void operations(int choice){
         if(choice==1) manageRes();
         else if(choice==2) manageApp();
         else if(choice==3) demStudies();
         else if(choice==4) manageMaintenanceOrder();
         else if(choice==5) adminReports();
         }

         private static void manageRes(){
            ///Queries
         }
         private static void demStudies(){
            ///Queries
         }
         private static void manageApp(){
            ///Queries
         }
         private static void manageMaintenanceOrder(){
            ///Queries
         }
         private static void adminReports(){
            ///Queries
         }
