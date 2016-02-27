package edu.hfut.easymock;

public class ClassUnderTest {

	private Collaborator listener;

	// ...
	public void setListener(Collaborator listener) {
		this.listener = listener;
	}

	public void addDocument(String title, String document) {
		this.listener.documentAdded(title);
	}
}