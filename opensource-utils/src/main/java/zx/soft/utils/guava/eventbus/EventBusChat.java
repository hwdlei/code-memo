package zx.soft.utils.guava.eventbus;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.google.common.eventbus.EventBus;

public class EventBusChat {
	public static void main(String[] args) {
		EventBus channel = new EventBus();
		ServerSocket socket;
		try {
			socket = new ServerSocket(8912);
			while (true) {
				Socket connection = socket.accept();
				UserThread newUser = new UserThread(connection, channel);
				channel.register(newUser);
				newUser.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}