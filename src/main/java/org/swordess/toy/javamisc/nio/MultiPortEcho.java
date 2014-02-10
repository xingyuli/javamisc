package org.swordess.toy.javamisc.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * Source:
 * http://www.javaworld.com/javaworld/jw-10-2012/121016-maximize-java-nio-and-nio2-for-application-responsiveness.html?page=3
 */
public class MultiPortEcho {

	private int[] ports;
	private ByteBuffer echoBuffer = ByteBuffer.allocate(1024);
	
	public MultiPortEcho(int[] ports) throws IOException {
		this.ports = ports;
		configureSelector();
	}

	private void configureSelector() throws IOException {
		// Create a new selector
		Selector selector = Selector.open();
		
		// Open a listener on each port, and register each one
		// with the selector
		for (int i = 0; i < ports.length; i++) {
			ServerSocketChannel ssc = ServerSocketChannel.open();
			ssc.configureBlocking(false);
			ServerSocket ss = ssc.socket();
			InetSocketAddress address = new InetSocketAddress(ports[i]);
			ss.bind(address);
			
			ssc.register(selector, SelectionKey.OP_ACCEPT);
			System.out.println("Going to listen on " + ports[i]);
		}
		
		while (true) {
			selector.select();
			
			for (Iterator<SelectionKey> iter = selector.selectedKeys().iterator(); iter.hasNext();) {
				SelectionKey key = iter.next();
				
				if (!key.isValid()) {
					continue;
				}
				
				if (key.isAcceptable()) {
					// Accept the new connection
					ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
					SocketChannel sc = ssc.accept();
					sc.configureBlocking(false);

					// Add the new connection to the selector
					sc.register(selector, SelectionKey.OP_READ);
					iter.remove();
					
				} else if (key.isReadable()) {
					// Read the data
					SocketChannel sc = (SocketChannel) key.channel();
					
					// Echo data
					int bytesEchoed = 0;
					while (true) {
						echoBuffer.clear();
						
						int numberOfBytes = sc.read(echoBuffer);
						if (-1 == numberOfBytes) {
							break;
						}
						
						echoBuffer.flip();
						sc.write(echoBuffer);
						bytesEchoed += numberOfBytes;
					}
					
					System.out.println("Echoed " + bytesEchoed + " from " + sc);
					sc.close();
					
					iter.remove();
				}
			}
		}
	}

	public static void main(String[] args) throws IOException {
		if (args.length <= 0) {
			System.err.println("Usage: java MultiPortEcho port [port port ...]");
			System.exit(1);
		}
		
		int[] ports = new int[args.length];
		for (int i = 0; i < args.length; i++) {
			ports[i] = Integer.valueOf(args[i]);
		}
		
		new MultiPortEcho(ports);
	}
	
}
