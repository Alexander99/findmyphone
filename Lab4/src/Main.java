//Lab4
public class Main {
	public static void main(String[] args) {
		//create a new PeopleBase
		OODB PeopleBase = new OODB(2);
		//Create two people to be stored into the Peoplebase
		Person Andrew = new Person("Andrew",4443213);
		Person Michael = new Person("Michael",3334561);
		//store these people into OODB Peoplebase, EntriesList[]
		PeopleBase.addToDB(Andrew);
		PeopleBase.addToDB(Michael);
		//look for those people in the Peoplebase
		//go into the KeyBlade, find the Person
	    Person newTempPerson1 = (Person) PeopleBase.lookupInDB(PeopleBase.EntryList[0].KeyBlade);
	    Person newTempPerson2 = (Person) PeopleBase.lookupInDB(PeopleBase.EntryList[1].KeyBlade);
		//display the name of this individual
		System.out.println(newTempPerson1.name);
		System.out.println(newTempPerson2.name);
		
		//Section for books and authors; create a BookBase
		OODB BookBase = new OODB(2);
		//Create two books to be stored into the BookBase
		Book Select1 = new Book("Fyodor Dostoevsky","Crime and Punishment");
		Book Select2 = new Book("Bram Stroker","Dracula");
		//Add selections to the BookBase
		BookBase.addToDB(Select1);
		BookBase.addToDB(Select2);
		//find those selections now
		Book tempSelect1 = (Book) BookBase.lookupInDB(BookBase.EntryList[0].KeyBlade);
		Book tempSelect2 = (Book) BookBase.lookupInDB(BookBase.EntryList[1].KeyBlade);
		//Display the names of the books authors!
		System.out.println(tempSelect1.author);
		System.out.println(tempSelect2.author);
	}
}
