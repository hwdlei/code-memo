package edu.hfut.easymock;


import java.util.HashMap;
import java.util.Map;

/**
 * @author OFFIS, Tammo Freese
 */
public class ClassTested {

	private Collaborator listener;

	private final Map<String, String> documents = new HashMap<String, String>();

	public void setListener(Collaborator listener) {
		this.listener = listener;
	}

	public void addDocument(String title, String content) {
		boolean documentChange = documents.containsKey(title);
		documents.put(title, content);
		if (documentChange) {
			notifyListenersDocumentChanged(title);
		} else {
			notifyListenersDocumentAdded(title);
		}
	}

	public boolean removeDocument(String title) {
		if (!documents.containsKey(title)) {
			return true;
		}

		if (!listenersAllowRemoval(title)) {
			return false;
		}

		documents.remove(title);
		notifyListenersDocumentRemoved(title);

		return true;
	}

	public boolean removeDocuments(String... titles) {
		if (!listenersAllowRemovals(titles)) {
			return false;
		}

		for (String title : titles) {
			documents.remove(title);
			notifyListenersDocumentRemoved(title);
		}
		return true;
	}

	private void notifyListenersDocumentAdded(String title) {
		listener.documentAdded(title);
	}

	private void notifyListenersDocumentChanged(String title) {
		listener.documentChanged(title);
	}

	private void notifyListenersDocumentRemoved(String title) {
		listener.documentRemoved(title);
	}

	private boolean listenersAllowRemoval(String title) {
		return listener.voteForRemoval(title) > 0;
	}

	private boolean listenersAllowRemovals(String... titles) {
		return listener.voteForRemovals(titles) > 0;
	}

}
