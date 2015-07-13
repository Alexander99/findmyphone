//Lab4
public class Title extends DBKey {
	//String to denote title of book
	String title;
	//constructor, sets up a title
	public Title(String setTitle) {
		title = setTitle;
	}
	//override isEqualTo here, use title.equals(toTitle)
	boolean isEqualTo(Title toTitle) {
		 //compare the titles of title and toTitle
		 return title.equals(toTitle); 
	 }
}
