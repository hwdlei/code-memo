package zx.soft.utils.hbase;

import java.util.Arrays;

import org.apache.hadoop.hbase.util.Bytes;

import com.google.common.base.Objects;

public class AbstractModel {
	private String rowKey;
	private String family;
	private String qualifier;
	private long timestamp;
	private String value;

	@Override
	public String toString() {
		return Objects.toStringHelper(AbstractModel.class).add("rowKey", rowKey).add("family", family)
				.add("qualifier", qualifier).add("value", value).toString();
	}

	public String getRowKey() {
		return rowKey;
	}

	public void setRowKey(String rowKey) {
		this.rowKey = rowKey;
	}

	public String getQualifier() {
		return qualifier;
	}

	public void setQualifier(String qualifier) {
		this.qualifier = qualifier;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getFamily() {
		return family;
	}

	public void setFamily(String family) {
		this.family = family;
	}

	public static void main(String[] args) {
		long m = 123L;
		System.out.println(Arrays.toString(Bytes.toBytes(m)));
		System.out.println(Arrays.toString(Bytes.toBytes(-m)));
	}

}
