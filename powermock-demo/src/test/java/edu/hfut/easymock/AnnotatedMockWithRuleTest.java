package edu.hfut.easymock;

import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Rule;
import org.junit.Test;

/**
 * Example of how to use {@code @Mock} and {@code @TestSubject} annotations with Junit Rule.
 *
 * @author Alistair Todd
 */
public class AnnotatedMockWithRuleTest extends EasyMockSupport {

	@Rule
	public EasyMockRule mocks = new EasyMockRule(this);

	@Mock
	private Collaborator collaborator;

	@TestSubject
	private final ClassTested classUnderTest = new ClassTested();

	@Test
	public void addDocument() {
		collaborator.documentAdded("New Document");
		replayAll();
		classUnderTest.addDocument("New Document", "content");
		verifyAll();
	}
}