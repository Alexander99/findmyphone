//Lab4
public class Person extends DBEntry {
	//global string name
	String name;
	public Person(String rename,long setSsn){
		//Set the name of the person to whatever rename is
		name = rename;
		//Set the ssn up for the person
		//set SocialSecurityNumber
		KeyBlade = new SSN(setSsn);
	}
}
