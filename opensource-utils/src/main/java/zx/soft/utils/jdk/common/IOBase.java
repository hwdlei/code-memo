package zx.soft.utils.jdk.common;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;

public class IOBase {

	private DataInputStream is;

	public IOBase(DataInputStream data) {
		this.is = data;
	}

	protected boolean readBytes(byte[] buf) {
		try {
			this.is.readFully(buf);
			return true;
		} catch (EOFException e) {
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	protected int readBytes2(byte[] buf) throws IOException {
		return this.is.read(buf);
	}

	public void close() {
		try {
			this.is.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
