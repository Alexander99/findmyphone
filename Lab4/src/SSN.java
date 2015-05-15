//Lab4
public class SSN extends DBKey{
	//global variable ssn
	public long ssn;
	public SSN(long ssnInit) {
		//initialize ssn
		ssn = ssnInit;
	}
	boolean isEqualTo(SSN to) {
		 //compare the ssns of this and to
		 return this.ssn == to.ssn; 
	 }

}
