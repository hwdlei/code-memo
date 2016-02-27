package edu.hfut.easymock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.getCurrentArguments;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.IAnswer;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author OFFIS, Tammo Freese, Henri Tremblay
 */
public class ExampleTest extends EasyMockSupport {

	@Rule
	public EasyMockRule rule = new EasyMockRule(this);

	@TestSubject
	private ClassTested classUnderTest = new ClassTested();

	@Mock
	private Collaborator mock;

	@Test
	public void removeNonExistingDocument() {
		replayAll();
		classUnderTest.removeDocument("Does not exist");
	}

	@Test
	public void addDocument() {
		mock.documentAdded("New Document");
		replayAll();
		classUnderTest.addDocument("New Document", "content");
		verifyAll();
	}

	@Test
	public void addAndChangeDocument() {
		mock.documentAdded("Document");
		mock.documentChanged("Document");
		expectLastCall().times(3);
		replayAll();
		classUnderTest.addDocument("Document", "content");
		classUnderTest.addDocument("Document", "content");
		classUnderTest.addDocument("Document", "content");
		classUnderTest.addDocument("Document", "content");
		verifyAll();
	}

	@Test
	public void voteForRemoval() {
		// expect document addition
		mock.documentAdded("Document");
		// expect to be asked to vote, and vote for it
		expect(mock.voteForRemoval("Document")).andReturn((byte) 42);
		// expect document removal
		mock.documentRemoved("Document");

		replayAll();
		classUnderTest.addDocument("Document", "content");
		assertTrue(classUnderTest.removeDocument("Document"));
		verifyAll();
	}

	@Test
	public void voteAgainstRemoval() {
		// expect document addition
		mock.documentAdded("Document");
		// expect to be asked to vote, and vote against it
		expect(mock.voteForRemoval("Document")).andReturn((byte) -42); //
		// document removal is *not* expected

		replayAll();
		classUnderTest.addDocument("Document", "content");
		assertFalse(classUnderTest.removeDocument("Document"));
		verifyAll();
	}

	@Test
	public void voteForRemovals() {
		mock.documentAdded("Document 1");
		mock.documentAdded("Document 2");
		expect(mock.voteForRemovals("Document 1", "Document 2")).andReturn((byte) 42);
		mock.documentRemoved("Document 1");
		mock.documentRemoved("Document 2");
		replayAll();
		classUnderTest.addDocument("Document 1", "content 1");
		classUnderTest.addDocument("Document 2", "content 2");
		assertTrue(classUnderTest.removeDocuments("Document 1", "Document 2"));
		verifyAll();
	}

	@Test
	public void voteAgainstRemovals() {
		mock.documentAdded("Document 1");
		mock.documentAdded("Document 2");
		expect(mock.voteForRemovals("Document 1", "Document 2")).andReturn((byte) -42);
		replayAll();
		classUnderTest.addDocument("Document 1", "content 1");
		classUnderTest.addDocument("Document 2", "content 2");
		assertFalse(classUnderTest.removeDocuments("Document 1", "Document 2"));
		verifyAll();
	}

	@SuppressWarnings("unchecked")
	@Test
	public void answerVsDelegate() {
		List<String> l = createMock(List.class);

		// andAnswer style
		expect(l.remove(10)).andAnswer(new IAnswer<String>() {
			@Override
			public String answer() throws Throwable {
				return getCurrentArguments()[0].toString();
			}
		});

		// andDelegateTo style
		expect(l.remove(10)).andDelegateTo(new ArrayList<String>() {
			private static final long serialVersionUID = 1L;

			@Override
			public String remove(int index) {
				return Integer.toString(index);
			}
		});

		replayAll();

		assertEquals("10", l.remove(10));
		assertEquals("10", l.remove(10));

		verifyAll();
	}
}