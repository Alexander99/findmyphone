//Lab4
public class Book extends DBEntry {
	//String denotes an author of a particular title
	String author;
	//Constructor creates key from title and contains Author data
	public Book(String reAuthor,String setTitle){
		//Set the name of the Author to whatever reAuthor is
		author = reAuthor;
		//Set the title up for the book
		KeyBlade = new Title(setTitle);
	}
}
