package model.patterns;

import junit.framework.TestCase;

public class IndexListTests extends TestCase {

	public void testToString() throws Exception {
		IndexList list = new IndexList();
		list.add(45);
		assertEquals("45 ", list.toString());
	}
}
