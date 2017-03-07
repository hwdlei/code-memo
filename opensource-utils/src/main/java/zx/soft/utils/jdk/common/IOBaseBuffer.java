package zx.soft.utils.jdk.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class IOBaseBuffer {

	private FileChannel is;

	private ByteBuffer buffer;

	private static final int BUFFER_SIZE = 1024;

	private boolean eof;

	public IOBaseBuffer(FileInputStream fis) throws FileNotFoundException {
		this.is = fis.getChannel();
		this.buffer = ByteBuffer.allocate(BUFFER_SIZE);
		this.buffer.limit(0);
		this.eof = false;
	}

	protected boolean readBytes(byte[] buf) {
		try {
			if (buf.length > BUFFER_SIZE) {
				return false;
			}
			if (!this.eof && this.buffer.remaining() < buf.length) {
				this.buffer.compact();
				while (!this.eof && this.buffer.hasRemaining()) {
					int num = this.is.read(this.buffer);
					if (num == -1) {
						this.eof = true;
					}
				}
				this.buffer.flip();
			}
			this.buffer.get(buf);
			return true;
		} catch (BufferUnderflowException e) {
			return false;
		} catch (IOException e) {
			return false;
		}
	}

	protected int readBytes2(byte[] buf) {
		try {
			if (buf.length > BUFFER_SIZE) {
				return 0;
			}
			if (!this.eof && this.buffer.remaining() < buf.length) {
				this.buffer.compact();
				while (!this.eof && this.buffer.hasRemaining()) {
					int num = this.is.read(this.buffer);
					if (num == -1) {
						this.eof = true;
					}
				}
				this.buffer.flip();
			}
			int remaining = this.buffer.remaining();
			if (this.buffer.remaining() < buf.length) {
				this.buffer.get(buf, 0, this.buffer.remaining());
				return remaining;
			} else {
				this.buffer.get(buf);
				return buf.length;
			}
		} catch (IOException e) {
			return 0;
		}
	}

	public void close() throws IOException {
		this.is.close();
	}

}
