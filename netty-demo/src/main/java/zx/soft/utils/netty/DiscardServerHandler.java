package zx.soft.utils.netty;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * Handles a server-side channel.
 */
public class DiscardServerHandler extends ChannelHandlerAdapter { // (1)

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) { // (2)
		// DISCARD SERVER
		//		ByteBuf in = (ByteBuf) msg;
		//		try {
		//			while (in.isReadable()) { // (1)
		//				System.out.print((char) in.readByte());
		//				System.out.flush();
		//			}
		//		} finally {
		//			ReferenceCountUtil.release(msg); // (2)
		//		}
		ctx.write(msg); // (1)
		ctx.flush(); // (2)
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
		// Close the connection when an exception is raised.
		cause.printStackTrace();
		ctx.close();
	}
}