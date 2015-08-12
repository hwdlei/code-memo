package zx.soft.utils.guava.files;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.lang.System.err;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.common.io.LineProcessor;

public class FilesDemo {
	public static void main(String[] args) throws IOException {
		demoFileWrite("a.txt", "hello world");
	}

	public static void demoFileWrite(final String fileName, final String contents) throws IOException {
		checkNotNull(fileName, "Provided file name for writing must NOT be null.");
		checkNotNull(contents, "Unable to write null contents.");
		final File newFile = new File(fileName);
		try {
			Files.append(contents, newFile, Charsets.UTF_8);
		} catch (IOException fileIoEx) {
			err.println("ERROR trying to write to file '" + fileName + "' - " + fileIoEx.toString());
		}
		List<String> lines = null;
		try {
			lines = Files.readLines(newFile, Charsets.UTF_8);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (String line : lines) {
			System.out.println(line);
		}
		CounterLine counter = new CounterLine();
		Files.readLines(newFile, Charsets.UTF_16, counter);
		System.out.println(counter.getResult());
	}

	static class CounterLine implements LineProcessor<Integer> {
		private int rowNum = 0;

		public boolean processLine(String line) throws IOException {
			rowNum++;
			return true;
		}

		public Integer getResult() {
			return rowNum;
		}
	}

}
