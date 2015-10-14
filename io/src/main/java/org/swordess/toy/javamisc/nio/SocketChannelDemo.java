package org.swordess.toy.javamisc.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.Iterator;
import java.util.concurrent.Executors;

public class SocketChannelDemo {

	private static class IOWorker implements Runnable {

		@Override
		public void run() {
			try {
				ServerSocketChannel channel = ServerSocketChannel.open();
				channel.configureBlocking(false);
				ServerSocket socket = channel.socket();
				socket.bind(new InetSocketAddress("localhost", 10800));
				
				Selector selector = Selector.open();
				channel.register(selector, channel.validOps());
				
				while (true) {
					selector.select();
					
					System.out.println("got it");
					
					for (Iterator<SelectionKey> iter = selector.selectedKeys().iterator(); iter.hasNext();) {
						SelectionKey key = iter.next();
						iter.remove();
						
						if (!key.isValid()) {
							continue;
						}
						
						if (key.isAcceptable()) {
							System.out.println("accept");
							
							ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
							SocketChannel sc = ssc.accept();
							sc.configureBlocking(false);
							sc.register(selector, sc.validOps());
						}
						
						if (key.isWritable()) {
							System.out.println("write");
							
							SocketChannel client = (SocketChannel) key.channel();
							
							Charset charset = Charset.forName("UTF-8");
							CharsetEncoder encoder = charset.newEncoder();
							CharBuffer charBuffer = CharBuffer.allocate(32);
							charBuffer.put("Hello World");
							charBuffer.flip();
							
							ByteBuffer content = encoder.encode(charBuffer);
							client.write(content);
							key.cancel();
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public static void main(String[] args) throws Exception {
		Executors.newSingleThreadExecutor().submit(new IOWorker()).get();
	}
	
}
