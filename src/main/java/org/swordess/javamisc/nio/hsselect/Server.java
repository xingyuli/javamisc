package org.swordess.javamisc.nio.hsselect;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;


/**
 * Source:
 * http://www.javaworld.com/jw-04-2003/jw-0411-select.html
 */
public class Server implements Runnable {

	// The port we will listen on
	private final int port;
	
	// A pre-allocated buffer for encrypting data
	private final ByteBuffer buffer = ByteBuffer.allocate(16384);
	
	private Server(int port) {
		this.port = port;
		new Thread(this).start();
	}

	@Override
	public void run() {
		try {
			// Instead of creating a ServerSocket,
			// create a ServerSocketChannel
			ServerSocketChannel ssc = ServerSocketChannel.open();
			
			// Get the Socket connected to this channel, and bind it to the
			// listening port
			InetSocketAddress isa = new InetSocketAddress(port);
			ServerSocket ss = ssc.socket();
			ss.bind(isa);
			
			// Set it to non-blocking, so we can use select
			ssc.configureBlocking(false);
			
			// Create a new Selector for selecting
			Selector selector = Selector.open();
			
			// Register the ServerSocketChannel, so we can listen for incoming
			// connections
			ssc.register(selector, SelectionKey.OP_ACCEPT);
			System.out.println("Listening on port " + port);
			
			while (true) {
				// See if we've had any activity -- either an incoming
				// connection, or incoming data on an existing connection
				int num = selector.select();
				
				// If we don't have any activity, loop around and wait again
				if (num == 0) {
					continue;
				}
				
				// Get the keys corresponding to the activity that has been
				// detected, and process them one by one
				Set<SelectionKey> keys = selector.selectedKeys();
				for (Iterator<SelectionKey> iter = keys.iterator(); iter.hasNext();) {
					// Get a key representing one of bits of I/O activity
					SelectionKey key = iter.next();
					
					// What kind of activity is it?
					if (key.isAcceptable()) {
						// It's an incoming connection.
						// Register this socket with the selector so we can
						// listen for input on it
						
						Socket s = ss.accept();
						System.out.println("Got connection from " + s);
						
						// Make sure to make in non-blocking, so we can use a
						// selector on it.
						SocketChannel sc = s.getChannel();
						sc.configureBlocking(false);
						
						// Register it with the selector, for reading
						sc.register(selector, SelectionKey.OP_READ);
						
					} else if (key.isReadable()) {
						// It's incoming data on a connection, so process it
						SocketChannel sc = (SocketChannel) key.channel();
						try {
							boolean ok = processInput(sc);
							
							// If the connection is dead, then remove it from
							// the selector and close it
							if (!ok) {
								key.cancel();
								
								Socket s = sc.socket();
								try {
									s.close();
								} catch (IOException ie) {
									System.err.println("Error closing socket "
											+ s + ": " + ie);
								}
							}
							
						} catch (IOException ie) {
							// On exception, remove this channel from the
							// selector
							key.cancel();
							try {
								sc.close();
							} catch (IOException ie2) {
								System.out.println(ie2);
							}
							System.out.println("Closed " + sc);
						}
					}
				}
				
				// We remove the selected keys, because we've dealt with them.
				keys.clear();
			}
			
		} catch (IOException ie) {
			System.err.println(ie);
		}
	}
	
	// Do some cheesy encryption on the incoming data, and send it back out.
	private boolean processInput(SocketChannel sc) throws IOException {
		buffer.clear();
		sc.read(buffer);
		buffer.flip();
		
		// If no data, close the connection
		if (buffer.limit() == 0) {
			return false;
		}
		
		// Simple rot-13 encryption
		for (int i = 0; i < buffer.limit(); i++) {
			byte b = buffer.get(i);
			if ((b>='a' && b<='m') || (b>='A' || b<='M')) {
				b += 13;
			} else if ((b>='n' && b<='z') || (b>='N' && b<='Z')) {
				b -= 13;
			}
			buffer.put(i, b);
		}
		
		// buffer.get(int, byte) and buffer.put(int, byte) will not impact
		// the limit
		
		sc.write(buffer);
		
		System.out.println("Processed " + buffer.limit() + " from" + sc);
		
		return true;
	}

	public static void main(String[] args) {
		int port = Integer.parseInt(args[0]);
		new Server(port);
	}
	
}
