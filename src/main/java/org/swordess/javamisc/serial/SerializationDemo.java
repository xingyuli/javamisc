package org.swordess.javamisc.serial;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamField;
import java.io.Serializable;

public class SerializationDemo {

	public void serialization() {
		String path = SerializationDemo.class.getResource("").getPath() + "user.bin";
		ObjectOutputStream output = null;
		try {
			File outFile = new File(path);
			if (outFile.exists()) {
				outFile.delete();
			}
			outFile.createNewFile();
			
			output = new ObjectOutputStream(new FileOutputStream(outFile));
			output.writeObject(new User("Alex", "Cheng"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != output) {
				try {
					output.close();
				} catch (IOException e) {
				}
			}
		}
	}
	
	public void deserialization() {
		String path = SerializationDemo.class.getResource("").getPath() + "user.bin";
		ObjectInputStream input = null;
		try {
			input = new ObjectInputStream(new FileInputStream(path));
			User user = (User) input.readObject();
			System.out.println(user);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (null != input) {
				try {
					input.close();
				} catch (IOException e) {
				}
			}
		}
	}
	
	private static class User implements Serializable {
		
		private static final ObjectStreamField[] serialPersistentFields = {
			new ObjectStreamField("firstname", String.class)
		};
		
		private final String firstname;
		private final String lastname;
		
		User(String firstname, String lastname) {
			this.firstname = firstname;
			this.lastname = lastname;
		}

		public String getFirstname() {
			return firstname;
		}

		public String getLastname() {
			return lastname;
		}
		
		@Override
		public String toString() {
			return firstname + " " + lastname;
		}
		
	}
	
}
