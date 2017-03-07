package zx.soft.utils.jdk.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Kruskal {

	/**
	 * 输入顶点的个数：
	 * 6
	 * 请在每一行输入一个点到其他点的距离（TAB键间隔）
	 * 0	6	1	5	1000	1000
	 * 6	0	5	1000	3	1000
	 * 1	5	0	5	6	4
	 * 5	1000	5	0	1000	2
	 * 1000	3	6	1000	0	6
	 * 1000	3	6	1000	0	6
	 * @param args
	 */

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		System.out.println("本程序使用临接矩阵存储图，示例输入如下：");
		System.out.println("请在每一行输入一个点到其他点的距离（TAB键间隔），如：");
		System.out.println("0	6	1");
		System.out.println("6	0	5");
		System.out.println("1	5	0");
		System.out.println("输出：[1->3:1, 2->3:5]");
		System.out.println("－－－－－－－－－－－－－－－－－－－－－－");
		System.out.println("输入顶点的个数：");
		int num = Integer.parseInt(scan.nextLine());
		System.out.println("请在每一行输入一个点到其他点的距离（TAB键间隔）");
		for (int i = 0; i < num; i++) {
			System.out.print("\t" + (i + 1));
		}
		System.out.println("\n");
		int[][] nextArray = new int[num][num];
		for (int i = 0; i < num; i++) {
			System.out.print((i + 1) + "\t");
			for (int j = 0; j < num; j++) {
				nextArray[i][j] = scan.nextInt();
			}
		}
		List<Edge> edges = new ArrayList<Edge>();
		for (int i = 0; i < num; i++) {
			for (int j = i + 1; j < num; j++) {
				edges.add(new Edge(i + 1, j + 1, nextArray[i][j]));
			}
		}
		quickSort(edges, 0, edges.size() - 1);
		int[] vertex = new int[num + 1];
		List<Edge> results = new ArrayList<Kruskal.Edge>();
		int index = 0;
		int count = 0;
		while (index < edges.size()) {
			int aValue = vertex[edges.get(index).a];
			int bValue = vertex[edges.get(index).b];
			// 不是子集且无交集，入选；
			if (aValue == 0 && bValue == 0) {
				count += 1;
				results.add(edges.get(index));
				vertex[edges.get(index).a] = count;
				vertex[edges.get(index).b] = count;
			} else if (aValue != bValue) {
				count += 1;
				results.add(edges.get(index));
				if (aValue != 0) {
					for (int n = 0; n < vertex.length; n++) {
						if (vertex[n] == aValue) {
							vertex[n] = count;
						}
					}
				}
				if (bValue != 0) {
					for (int n = 0; n < vertex.length; n++) {
						if (vertex[n] == bValue) {
							vertex[n] = count;
						}
					}
				}
				vertex[edges.get(index).a] = count;
				vertex[edges.get(index).b] = count;
			}
			if (count >= num - 1) {
				break;
			}
			index++;
		}
		System.out.println(results);

	}

	public static void quickSort(List<Edge> data, int start, int end) {
		Edge key = data.get(start);
		int i = start;
		int j = end;
		while (i < j) {
			while (data.get(j).compareTo(key) > 0 && j > i) {
				j--;
			}
			data.set(i, data.get(j));
			while (data.get(i).compareTo(key) <= 0 && i < j) {
				i++;
			}
			data.set(j, data.get(i));
		}
		// 此时 i==j
		data.set(i, key);

		// 递归调用
		if (i - 1 > start) {
			// 递归调用，把key前面的完成排序
			quickSort(data, start, i - 1);
		}
		if (i + 1 < end) {
			// 递归调用，把key后面的完成排序
			quickSort(data, i + 1, end);
		}
	}

	public static class Edge implements Comparable<Edge> {

		public final int a;
		public final int b;
		public final int value;

		public Edge(int a, int b, int value) {
			this.a = a;
			this.b = b;
			this.value = value;
		}

		public int compareTo(Edge o) {
			if (value > o.value) {
				return 1;
			} else if (value < o.value) {
				return -1;
			}
			return 0;
		}

		@Override
		public String toString() {
			return a + "->" + b + ":" + value;
		}

	}

}
