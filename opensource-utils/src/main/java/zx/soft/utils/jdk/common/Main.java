package zx.soft.utils.jdk.common;

public class Main {

	public static class Node {

		public final String name;
		private Node next;

		public Node(String name) {
			this.name = name;
		}

		public void setNext(Node next) {
			this.next = next;
		}

		public Node getNext() {
			return this.next;
		}

		@Override
		public String toString() {
			return this.name;
		}
	}

	public static Node revertList(Node node) {
		if (node == null) {
			return null;
		}
		Node result = node;
		Node lastNode = null;
		Node oldNode = null;
		int i = 1;
		while (node != null) {
			if (i % 2 != 0) {
				oldNode = lastNode;
				lastNode = node;
				node = node.getNext();
			} else {
				if (oldNode == null) {
					lastNode.setNext(node.getNext());
					node.setNext(lastNode);
					result = node;
				} else {
					oldNode.setNext(node);
					lastNode.setNext(node.getNext());
					node.setNext(lastNode);
				}
				oldNode = node;
				node = lastNode.getNext();
			}
			i++;
		}
		return result;
	}

	public static void main(String[] args) {

		Node a = new Node("a");
		Node b = new Node("b");
		a.setNext(b);
		Node c = new Node("c");
		b.setNext(c);
		Node d = new Node("d");
		c.setNext(d);
		Node e = new Node("e");
		d.setNext(e);

		Node result = revertList(a);

		while (result != null) {
			System.out.print(result.name + "->");
			result = result.getNext();
		}

	}

}
