//Lab4
public class OODB {
	//entry pointer
	int entryNo = 0;
	int entryNum = 0;
	//DBEntry init
	static DBEntry EntryList[];
	//constructor for entries list
	public OODB(int sizeOfEntriesList){
		//create an array of entries
		EntryList = new DBEntry[sizeOfEntriesList];
		//set entryNo equal to sizeOfEntriesList+1 so when we add we add to the top
		entryNo = sizeOfEntriesList-1;
	}
	//add to the database
	void addToDB(DBEntry toAdd){
		//add to the specific entry detailed by entryNo
		if(entryNum <= entryNo){
		EntryList[entryNum] = toAdd;
		entryNum++;
		}
		else{
			System.out.println("ERR: Cannot add more to database");
		}
	}
	//Look for specified entry
	DBEntry lookupInDB(DBKey k){
		//look through entries for correct key and return the specific entry
		for(int look = entryNo; look >= 0; look--){
			if(k.isEqualTo(EntryList[look].KeyBlade)){
				return EntryList[look];
			}
		}
		//if the correct entry is not found, return a null entry
		return null;
	}
	
}
