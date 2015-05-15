//Lab1:
//Here is a new change! This should trigger the pull and diff
//My first complex java class
public class MyClass {
 // Variable construct used to count the number
 // of instances of This class are made
 public static int construct;
 //String line used for name
 public String name; 
 //Constructor: Not a void, only sets up the class
 //and counts constructor up by one
 //Should also take a string to remember and keep
 public MyClass(String nameArray){
  name = nameArray;
  construct++;
 }
 //Returns how many classes of type MyClass there are
 public static void HowMany(int classes){
	 System.out.print("There are ");
	 System.out.print(classes);
	 System.out.print(" constructs\n");
 }
 //Returns the name of a class
 public static void WhatIsYourName(MyClass classtest){
	 System.out.print("MY NAME IS ");
	 System.out.println(classtest.name);	 
 }
 //main function
 public static void main(String[] args){
 //Specialized function:
	 //create 3 classes myClass#
	 MyClass myClass1 = new MyClass("myClass1");
	 MyClass myClass2 = new MyClass("myClass2");
	 MyClass myClass3 = new MyClass("myClass3");
	 //call HowMany and WhatIsYourName for each class
	 HowMany(construct);
	 WhatIsYourName(myClass1);
	 WhatIsYourName(myClass2);
	 WhatIsYourName(myClass3);
	 //Cause myClass3 = myClass1
	 myClass3 = myClass1;
	 //call HowMany and WhatIs your Name for each class again
	 HowMany(construct);
	 WhatIsYourName(myClass1);
	 WhatIsYourName(myClass2);
	 WhatIsYourName(myClass3);
 }
}
