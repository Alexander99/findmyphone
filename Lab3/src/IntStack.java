//Lab3

import java.util.LinkedList;

public class IntStack {
  private LinkedList ll;
  public IntStack() { ll = new LinkedList(); }
  public void push(int x) { ll.addFirst(new Integer(x)); }
  public int pop() { return ((Integer)ll.removeFirst()).intValue(); }
  public int top() { return ((Integer)ll.get(0)).intValue(); }
  public int lookup(int no) {
	  //carrier variable to determine if the number we are looking for is on the stack
	  int stackElem = 1;
	  //while the stack is not empty, search for the value of no
	  while(stackElem != 0){
		//set stackElem = next item in stack
		stackElem = pop();
		//if the value is found, return 1 (TRUE)
		if(stackElem == no) {
			return 1;
		}
	  }
	  //else, return 0 (FALSE)
	  return 0;
  }
} 
