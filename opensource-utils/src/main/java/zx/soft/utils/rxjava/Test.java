package zx.soft.utils.rxjava;

import rx.Observable;
import rx.functions.Action1;

public class Test {
	public static void main(String[] args) {
		hello("Ben", "George");
		size("Ben", "George");
	}

	public static void hello(String... names) {
		Observable.from(names).subscribe(new Action1<String>() {

			@Override
			public void call(String s) {
				System.out.println("Hello " + s + "!");
			}

		});
	}

	public static void size(String... name) {
		Observable.from(name).subscribe(new Action1<String>() {

			@Override
			public void call(String arg0) {
				System.out.println(arg0.length());


			}
		});
	}


}
