//Lab2
public class MyTruck extends MyAutomobile{
    String cargobay;
    String cargobaycontents;
	 //constructor for MyTruck, initialize it with values determined by user
	 public MyTruck(int setWheels, String setMotortype, String setLicenseplate){
		 //Wheels = 4 
		 Wheels = setWheels;
		 //0 < Motortype <= 400 miles
		 Motortype = setMotortype;
         // cargo bay initially empty
		 cargobay = "Empty";
		 //Any 8 A-Z,a-z,0-9 combo
		 Licenseplate = setLicenseplate;
	 }
	 //Function to load the cargo bay with some content
	 public void load(String setcargobaycontents){
		 cargobay = "Full";
		 System.out.print("cargo bay is ");
		 System.out.println(cargobay);
		 cargobaycontents = setcargobaycontents;
		 System.out.print("Contains ");
		 System.out.println(cargobaycontents);
	 }
	 //Function to unload the cargo bay
	 public void unload(){
		 cargobay = "Empty";
		 System.out.print("cargo bay is ");
		 System.out.println(cargobay);
		 cargobaycontents = "";
		 System.out.println("Cargo now contains nothing");
	 }
	 //Function drive to overwrite old drive
	 public void drive(){
		 System.out.print("License Plate is ");
		 System.out.println(Licenseplate);
		 if(cargobaycontents != null){
		 System.out.print("cargo bay contents are ");
		 System.out.println(cargobaycontents);
		 }
		 else {
			 System.out.println("cargo bay contains nothing now");
		 }
	 }
	 //main function
	 public static void main(String[] args){
		 MyTruck Truck1 = new MyTruck(4,"G6001","H4SM87YT");
		 MyTruck Truck2 = new MyTruck(4,"G6002","H4SM87YU");
		 Truck1.drive();
		 Truck1.load("Apples");
		 Truck2.drive();
		 Truck2.load("Oranges");
		 Truck1.unload();
		 Truck2.unload();
	 }

}
