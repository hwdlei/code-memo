package edu.hfut.easymock;

import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Example of how to use {@code @Mock} and {@code @TestSubject} annotations with JUnit Runner.
 *
 * @author Henri Tremblay
 */
@RunWith(EasyMockRunner.class)
public class AnnotatedMockWithRunnerTest extends EasyMockSupport {

	@TestSubject
	private final ClassTested classUnderTest = new ClassTested();

	@Mock
	private Collaborator collaborator;

	@Test
	public void addDocument() {
		collaborator.documentAdded("New Document");
		replayAll();
		classUnderTest.addDocument("New Document", "content");
		verifyAll();
	}
}