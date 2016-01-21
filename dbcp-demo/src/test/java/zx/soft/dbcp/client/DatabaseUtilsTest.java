package zx.soft.dbcp.client;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;


public class DatabaseUtilsTest {

	@Test
	public void testExecQuery() {
		List<Demo> demos = DatabaseUtils.execQuery("SELECT 1 as count", Demo.class);
		Assert.assertTrue(demos.size() == 1);
		Assert.assertTrue(demos.get(0).getCount() == 1);
	}

	public void testExecUpdate() {

	}

}
