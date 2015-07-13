//Lab3
import junit.framework.TestCase;


public class UnitTest extends TestCase {
	IntStack s = new IntStack();
	protected static void setUpBeforeClass() throws Exception {
	}

	protected static void tearDownAfterClass() throws Exception {
	}

	protected void setUp() throws Exception {
		s.push(0);
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	//testPopAfterPush first pushes something onto the stack, then pops it
	public void testPopAfterPush() {
		  s.push(10);
		  assertTrue(s.pop() == 10);
		}
	//testPopOnly creates a new stack and pops it, no pushes
	public void testPopOnly() {
		assertTrue(s.pop() == 0);
	}
	//testPopAfterPushes pushes more than one thing onto the stack, then pops last thing pushed
	public void testPopAfterPushes() {
		for(int i = 1; i < 10; i++) {
			s.push(i);
		}
		assertTrue(s.pop() == 9);
	}
	//testPopsAfterPushes pushes more than one thing onto the stack, then pops all of it
	public void testPopsAfterPushes() {
		for(int i = 1; i < 10; i++) {
			s.push(i);
		}
		for(int j = 9; j >= 0; j--) {
			assertTrue(s.pop() == j);
		}
	}
	//lookup looks for a variable x and returns true if found
	public void testLookup() {
		IntStack s = new IntStack();
		for(int i = 0; i < 10; i++) {
			s.push(i);
		}
		int lookupTest = s.lookup(4);
		assertTrue(lookupTest == 1);
	}
}
