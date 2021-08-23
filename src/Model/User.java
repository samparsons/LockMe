package Model;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Vector;

public class User implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	
	private Vector<User> arr;
	// properties
	private String username;
	private String password;
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public User(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + "]";
	}
	public byte[] getBytes() {
	  String str = "User [username=" + username + ", password=" + password + "]";
      byte[] bytes = str.getBytes();
      return bytes;
	}
}
