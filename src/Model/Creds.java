package Model;

import java.io.Serializable;
import java.util.Comparator;

public class Creds implements Serializable,Comparable  {
	
	// properties
	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	private String site;
	
	
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



	public String getSite() {
		return site;
	}



	public void setSite(String site) {
		this.site = site;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Creds(String username, String password, String site) {
		super();
		this.username = username;
		this.password = password;
		this.site = site;
	}
	
	public Creds() {
		super();
	}
	
	@Override
	public String toString() {
		return "Creds [username=" + username + ", password=" + password + ", site=" + site + "]";
	}

	public byte[] getBytes() {
	  String str = "User [username=" + username + ", password=" + password + "]";
      byte[] bytes = str.getBytes();
      return bytes;
	}
	
	public static Comparator<Creds> CredNameComparator = new Comparator<Creds>() {

		public int compare(Creds s1, Creds s2) {
		   String StudentName1 = s1.getUsername().toUpperCase();
		   String StudentName2 = s2.getUsername().toUpperCase();

		   //ascending order
		   return StudentName1.compareTo(StudentName2);

		   //descending order
		   //return StudentName2.compareTo(StudentName1);
	    }};


	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
