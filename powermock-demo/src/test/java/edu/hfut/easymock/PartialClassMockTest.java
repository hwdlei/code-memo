package edu.hfut.easymock;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;

import org.easymock.EasyMockSupport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Example of how to perform partial mocking
 *
 * @author Henri Tremblay
 */
public class PartialClassMockTest extends EasyMockSupport {

	public static class Rect {

		private int x;

		private int y;

		public int getX() {
			return x;
		}

		public void setX(int x) {
			this.x = x;
		}

		public int getY() {
			return y;
		}

		public void setY(int y) {
			this.y = y;
		}

		public int getArea() {
			return getX() * getY();
		}
	}

	private Rect rect;

	@Before
	public void setUp() throws Exception {
		rect = partialMockBuilder(Rect.class).addMockedMethods("getX", "getY").createMock();
	}

	@After
	public void tearDown() {
		rect = null;
	}

	@Test
	public void testGetArea() {
		expect(rect.getX()).andReturn(4);
		expect(rect.getY()).andReturn(5);
		replayAll();
		assertEquals(20, rect.getArea());
		verifyAll();
	}
}