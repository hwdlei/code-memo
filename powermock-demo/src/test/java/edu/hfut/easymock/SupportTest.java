package edu.hfut.easymock;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertTrue;

import org.easymock.EasyMockSupport;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Henri Tremblay
 */
public class SupportTest extends EasyMockSupport {

	private Collaborator collaborator;

	private ClassTested classUnderTest;

	@Before
	public void setup() {
		classUnderTest = new ClassTested();
	}

	@Test
	public void addDocument() {
		collaborator = mock(Collaborator.class);
		classUnderTest.setListener(collaborator);
		collaborator.documentAdded("New Document");
		replayAll();
		classUnderTest.addDocument("New Document", "content");
		verifyAll();
	}

	@Test
	public void voteForRemovals() {

		IMocksControl ctrl = createControl();
		collaborator = ctrl.createMock(Collaborator.class);
		classUnderTest.setListener(collaborator);

		collaborator.documentAdded("Document 1");

		expect(collaborator.voteForRemovals("Document 1")).andReturn((byte) 20);

		collaborator.documentRemoved("Document 1");

		replayAll();

		classUnderTest.addDocument("Document 1", "content");
		assertTrue(classUnderTest.removeDocuments("Document 1"));

		verifyAll();
	}
}