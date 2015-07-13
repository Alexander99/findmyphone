//Lab2
//Create Class car
public class MyCar extends MyAutomobile {
 int noPassengerseats;
 //constructor for MyCar, initialize it with values determined by user
 public MyCar(int setWheels, String setMotortype, int setnoPassengerseats, String setLicenseplate){
	 //Wheels = 4 
	 Wheels = setWheels;
	 //0 < Motortype <= 400 miles
	 Motortype = setMotortype;
	 //0 < noPassengerseats <= 6
	 noPassengerseats = setnoPassengerseats;
	 //Any 8 A-Z,a-z,0-9 combo
	 Licenseplate = setLicenseplate;
 }
 //main function
 public static void main(String[] args){
	 MyCar Audi = new MyCar(4,"I8400",4,"15GCE15P");
	 Audi.drive();
 }
}
