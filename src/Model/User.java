package Model;

import java.io.Serializable;
import java.util.Comparator;

public class User implements Serializable,Comparable  {
	
	private static final long serialVersionUID = 1L;

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
	
	public static Comparator<User> UserNameComparator = new Comparator<User>() {

		public int compare(User s1, User s2) {
		   String StudentName1 = s1.getUsername().toUpperCase();
		   String StudentName2 = s2.getUsername().toUpperCase();

		   //ascending order
		   return StudentName1.compareTo(StudentName2);

		   //descending order
		   //return StudentName2.compareTo(StudentName1);
	    }};


	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
