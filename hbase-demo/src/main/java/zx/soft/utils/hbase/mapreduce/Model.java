package zx.soft.utils.hbase.mapreduce;

import org.apache.hadoop.hbase.util.Bytes;

public class Model {

	public static final byte[] T = Bytes.toBytes("t");
	public static final byte[] S = Bytes.toBytes("s");

	public static final byte[] TS = Bytes.toBytes("ts");
	public static final byte[] SRCIP = Bytes.toBytes("sip");
	public static final byte[] DESIP = Bytes.toBytes("dip");
	public static final byte[] SRCPORT = Bytes.toBytes("spt");
	public static final byte[] DESPORT = Bytes.toBytes("dpt");
	public static final byte[] PROTOCOL = Bytes.toBytes("ptl");
	public static final byte[] BRIEF = Bytes.toBytes("bf");
	public static final byte[] DETAIL = Bytes.toBytes("dtl");

	private String timestamp;
	private String srcIp;
	private String desIp;
	private String srcPort;
	private String desPort;
	private String protocol;
	private String brief;
	private String detail;

	@Override
	public String toString() {
		if (timestamp == null || srcIp == null || desIp == null || protocol == null) {
			return "";
		} else {
			return timestamp + "\t" + srcIp + "\t" + desIp + "\t" + srcPort + "\t" + desPort + "\t" + protocol;
		}
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getSrcIp() {
		return srcIp;
	}

	public void setSrcIp(String srcIp) {
		this.srcIp = srcIp;
	}

	public String getDesIp() {
		return desIp;
	}

	public void setDesIp(String desIp) {
		this.desIp = desIp;
	}

	public String getSrcPort() {
		return srcPort;
	}

	public void setSrcPort(String srcPort) {
		this.srcPort = srcPort;
	}

	public String getDesPort() {
		return desPort;
	}

	public void setDesPort(String desPort) {
		this.desPort = desPort;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getBrief() {
		return brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

}
