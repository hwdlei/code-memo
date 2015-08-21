package zx.soft.utils.http;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpClientConnection;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ConnectionRequest;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;

public class ConnectionManagerDemo {
	public static void main(String[] args) throws InterruptedException, ExecutionException, IOException, HttpException {
		HttpClientContext context = HttpClientContext.create();
		HttpClientConnectionManager connMrg = new BasicHttpClientConnectionManager();
		HttpRoute route = new HttpRoute(new HttpHost("192.168.32.16", 10000));
		// Request new connection. This can be a long process
		ConnectionRequest connRequest = connMrg.requestConnection(route, null);
		// Wait for connection up to 10 sec
		HttpClientConnection conn = connRequest.get(10, TimeUnit.SECONDS);
		try {
			// If not open
			if (!conn.isOpen()) {
				// establish connection based on its route info
				connMrg.connect(conn, route, 1000, context);
				// and mark it as route complete
				connMrg.routeComplete(conn, route, context);
			}
			// Do useful things with the connection.
		} finally {
			connMrg.releaseConnection(conn, null, 1, TimeUnit.MINUTES);
		}

	}
}
