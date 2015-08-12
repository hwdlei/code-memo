package zx.soft.utils.hbase;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.Append;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.HConnectionManager;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Increment;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HBaseUtils {
	private static Logger logger = LoggerFactory.getLogger(HBaseUtils.class);

	private static Configuration conf = null;
	private static HConnection conn = null;

	static {
		conf = HBaseConfiguration.create();
		conf.set("hbase.zookeeper.quorum", "localhost");
		conf.setInt("hbase.zookeeper.property.clientPort", 2181);
		try {
			conn = HConnectionManager.createConnection(conf);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	public static void createTable(String tableName, String[] families) throws MasterNotRunningException,
			ZooKeeperConnectionException, IOException {
		HBaseAdmin admin = new HBaseAdmin(conf);
		if (admin.tableExists(tableName)) {
			System.out.println("table" + tableName + "already exists!");
		} else {
			HTableDescriptor descriptor = new HTableDescriptor(TableName.valueOf(tableName));
			for (String family : families) {
				descriptor.addFamily(new HColumnDescriptor(family));
			}
			admin.createTable(descriptor);
			System.out.println("create table " + tableName + " success!");
		}
		admin.close();

	}

	public static boolean addData(String tableName, String rowKey, String family, String qualifier, String value) {
		try {
			HTableInterface table = null;
			try {
				table = conn.getTable(tableName);
				Put put = new Put(Bytes.toBytes(rowKey));
				put.add(Bytes.toBytes(family), Bytes.toBytes(qualifier), Bytes.toBytes(value));
				table.put(put);
				return true;
			} finally {
				table.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean appendData(String tableName, String rowKey, String family, String qualifier, String value) {
		try {
			HTableInterface table = null;
			try {
				table = conn.getTable(tableName);
				Append append = new Append(Bytes.toBytes(rowKey));
				append.add(Bytes.toBytes(family), Bytes.toBytes(qualifier), Bytes.toBytes(value));
				table.append(append);
				return true;
			} finally {
				table.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean incrementData(String tableName, String rowKey, String family, String qualifier, long value) {
		try {
			HTableInterface table = null;
			try {
				table = conn.getTable(tableName);
				Increment incre = new Increment(Bytes.toBytes(rowKey));
				incre.addColumn(Bytes.toBytes(family), Bytes.toBytes(qualifier), value);
				table.increment(incre);
				return true;
			} finally {
				table.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static List<AbstractModel> getOneRow(String tableName, String rowKey, String family) {
		List<AbstractModel> lists = new LinkedList<AbstractModel>();
		try {
			HTableInterface table = null;
			try {
				table = conn.getTable(tableName);
				Get get = new Get(Bytes.toBytes(rowKey));
				get.addFamily(Bytes.toBytes(family));
				Result result = table.get(get);
				for (Cell cell : result.listCells()) {
					AbstractModel instance = new AbstractModel();
					instance.setRowKey(new String(CellUtil.cloneRow(cell)));
					instance.setFamily(new String(CellUtil.cloneFamily(cell)));
					instance.setQualifier(new String(CellUtil.cloneQualifier(cell)));
					instance.setValue(new String(CellUtil.cloneValue(cell)));
					lists.add(instance);
				}
				return lists;
			} finally {
				table.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lists;
	}

	public static List<AbstractModel> getAllVersion(String tableName, String rowKey, String family, String qualifier) {
		List<AbstractModel> lists = new LinkedList<AbstractModel>();
		try {
			HTableInterface table = null;
			try {
				table = conn.getTable(tableName);
				Get get = new Get(Bytes.toBytes(rowKey));
				get.addColumn(Bytes.toBytes(family), Bytes.toBytes(qualifier));
				Result result = table.get(get);

				for (Cell cell : result.getColumnCells(Bytes.toBytes(family), Bytes.toBytes(qualifier))) {
					AbstractModel instance = new AbstractModel();
					instance.setRowKey(new String(CellUtil.cloneRow(cell)));
					instance.setFamily(new String(CellUtil.cloneFamily(cell)));
					instance.setQualifier(new String(CellUtil.cloneQualifier(cell)));
					instance.setValue(new String(CellUtil.cloneValue(cell)));
					lists.add(instance);
				}
				return lists;
			} finally {
				table.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lists;
	}

	public static boolean scanRows(String tableName, String startRow, String stopRow) {
		try {
			HTableInterface table = null;
			try {
				table = conn.getTable(tableName);
				Scan scan = new Scan(Bytes.toBytes(startRow), Bytes.toBytes(stopRow));
				ResultScanner scanner = table.getScanner(scan);
				for (Result result : scanner) {
					for (Cell cell : result.listCells()) {
						System.out.println(new String(CellUtil.cloneRow(cell)));
					}

				}
				return true;
			} finally {
				table.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean scanByFilter(String tableName, String rowKey, String family, String qualifier, String value) {
		try {
			HTableInterface table = null;
			try {
				table = conn.getTable(tableName);
				Scan scan = new Scan();
				scan.addColumn(family.getBytes(), qualifier.getBytes());
				Filter filter = new SingleColumnValueFilter(Bytes.toBytes(family), Bytes.toBytes(qualifier),
						CompareOp.EQUAL, Bytes.toBytes(value));
				scan.setFilter(filter);
				return true;
			} finally {
				table.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean deleteRow(String tableName, String rowKey) {
		try {
			HTableInterface table = null;
			try {
				table = conn.getTable(tableName);
				Delete delete = new Delete(Bytes.toBytes(rowKey));
				table.delete(delete);
				return true;
			} finally {
				table.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean deleteColumn(String tableName, String rowKey, String family, String qualifier) {
		try {
			HTableInterface table = null;
			try {
				table = conn.getTable(tableName);
				Delete delete = new Delete(Bytes.toBytes(rowKey));
				delete.deleteColumns(Bytes.toBytes(family), Bytes.toBytes(qualifier));
				table.delete(delete);
				return true;
			} finally {
				table.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}


	public static void main(String[] args) {
		String tableName = "twits";
		String[] families = { "twits" };
		try {
			HBaseUtils.createTable(tableName, families);
			//			System.out.println(HBaseUtils.addData("users", "TheRealMT", "info", "name", "Mark Twain2"));
			//			System.out.println(HBaseUtils.appendData("users", "TheRealMT", "info", "name", "append"));
			//			System.out.println(HBaseUtils.addData("users", "TheRealMT", "info", "password", "example"));
			//			System.out.println(HBaseUtils.getOneRow("users", "TheRealMT", "info"));
			//			System.out.println(HBaseUtils.getAllVersion("users", "TheRealMT", "info", "name"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
