package org.swordess.javamisc.nio;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CodingErrorAction;

public class CharsetDemo {

	public void printDefaultCharset() {
		System.getProperties().list(System.out);
		System.out.println(Charset.defaultCharset());
	}
	
	public void encodeAndDecode() {
		Charset charset = Charset.forName("ISO-8859-1");
		
		CharsetEncoder encoder = charset.newEncoder();
		// this will filter out characters which cannot be recognized by the
		// ISO-8859-1 charset
		encoder.onUnmappableCharacter(CodingErrorAction.IGNORE);
		
		CharsetDecoder decoder = charset.newDecoder();
		
		String input = "你123好";
		CharBuffer buffer = CharBuffer.allocate(32);
		buffer.put(input);
		buffer.flip();
		
		try {
			// encode: characters -> bytes
			ByteBuffer byteBuffer = encoder.encode(buffer);
			
			// decode: bytes -> characters
			CharBuffer cbuf = decoder.decode(byteBuffer);
			
			// as unmappable characters are IGNOREd, the output is "123"
			System.out.println(cbuf);
		} catch (CharacterCodingException e) {
			e.printStackTrace();
		}
	}
	
}
