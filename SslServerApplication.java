package com.snhu.sslserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@SpringBootApplication
public class SslServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SslServerApplication.class, args);
	}

}
//FIXME: Add route to enable check sum return of static data example:  String data = "Hello World Check Sum!";

@RestController
class ServerController {
	private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

	private String getHash(String input) {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
			byte[] messageDigestMD5 = messageDigest.digest();
			return bytesToHex(messageDigestMD5);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return input;
	}

	public static String bytesToHex(byte[] bytes) {
	
		char[] hexChars = new char[bytes.length * 2];
		for (int j = 0; j < bytes.length; j++) {
			int v = bytes[j] & 0xFF;
			hexChars[j * 2] = HEX_ARRAY[v >>> 4];
			hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
		}
		return new String(hexChars);
	}

	@RequestMapping("/hash")
	public String myHash() {
		String data = "Carlos Stockl Sum!!";
		String hash = getHash(data);
		return "<p>data: " + data + "</p><p> Name of Cipher Used: SHA-256 Value: " + hash;
	}
}