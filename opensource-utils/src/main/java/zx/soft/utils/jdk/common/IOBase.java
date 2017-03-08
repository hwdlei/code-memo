package zx.soft.utils.jdk.commom;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;

public class IOBase {

    private BufferedInputStream is;

    private boolean             eof;

    public IOBase(DataInputStream data) {
        this.is = new BufferedInputStream(data);
        this.eof = false;
    }

    protected boolean readFully(byte[] buf) {
        try {
            int i = 0;
            while (!this.eof && i != buf.length) {
                int num = this.is.read(buf, i, buf.length - i);
                if (num == -1) {
                    this.eof = true;
                    System.out.println("Has reached the eof. has read " + i);
                    return false;
                }
                i += num;
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    protected int readBytes(byte[] buf) throws IOException {
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
