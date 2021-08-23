package Application;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;
import java.util.Vector;

import Model.User;
/*
 * public FileOutputStream(String name, boolean append)
 *                              throws FileNotFoundException 
 *  
 * Parameters:
 * ----------- 
 * 
 * name - the system-dependent file name
 * 
 * append - if true, then bytes will be written to the
 * end of the file rather than the beginning
 */
public class FileOutputStreamDemo
{
	public static byte[] addUser() throws IOException, ClassNotFoundException, InterruptedException{
		/*
		System.out.println("username");
		Scanner welcome = new Scanner(System.in);				
		String username 	= welcome.nextLine();
		System.out.println("password");
		Scanner welcome2 = new Scanner(System.in);				
		String password 	= welcome2.nextLine();
		User user = new User(username, password);
		System.out.println("check 1");
		*/
		byte[] bArr = null;
		try {
			FileOutputStream fos;
			fos = new FileOutputStream("userDB/myfile.txt",true);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			
			try {
				ObjectOutput out;
				out = new ObjectOutputStream(bos);
				out.writeObject(fos);
				bArr = bos.toByteArray();
				out.close();
				bos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	   
		
		for (Byte b : bArr) {
            System.out.print(b);
        }
		
		return bArr;
	
	}
    
	public static void searilizeUser(Vector<User> users) {
		
			Vector<User> SerUsers = new Vector<User>(users);
	        System.out.println("check 2");
	 
	        try
	        {
	        	System.out.println("check 4");
	            FileOutputStream fos = new FileOutputStream("userDB/myfile.txt",true);
	            ObjectOutputStream oos = new ObjectOutputStream(fos);
	            oos.writeObject(users);
	            oos.close();
	            fos.close();
	            System.out.println("check 5");
	        } 
	        catch (IOException ioe) 
	        {
	            ioe.printStackTrace();
	        }
	}

	public void testByteArray() throws IOException, ClassNotFoundException, InterruptedException {
	    
        /*
	    Hello h2;
	    ByteArrayInputStream bis = new ByteArrayInputStream(b);
	    ObjectInput in = new ObjectInputStream(bis);
	    h2 = (Hello) in.readObject();

	    assertTrue(10 == h2.getX());
	    assertTrue(20 == h2.getY());*/
	}
	
	public static Vector<User> deserializeUser() throws InterruptedException {
		
		Vector<User> users = new Vector<User>();
        
        try
        {
            FileInputStream fis = new FileInputStream("userDB/myfile.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            System.out.println("check 6");
            Object obj = null;
            obj = ois.readObject();
            System.out.println("check 7");
            ois.close();
            fis.close();
        } 
        catch (IOException ioe) 
        {
            ioe.printStackTrace();
        } 
        catch (ClassNotFoundException c) 
        {
            System.out.println("Class not found");
            c.printStackTrace();
        }
        
        //Verify list data
        System.out.println("check 6");
        for (User user : users) {
            System.out.println(user);
        }
        int check = 7;
		for (int i = 0; i < users.size(); i++) {
			System.out.println("check "+check);
			System.out.println(users.get(i));
			check++;
		}
		
		

		for (int i = 0; i < users.size(); i++) {
			System.out.println("check "+check);
			System.out.println(users.get(i));
			check++;
		}
        
        return users;
	    
	}
	
	public static void main(String[] args) throws IOException
	{
	        User student = new User("John Snow2","Password");
	        User clone = null;
	        
	        
            Path path = Paths.get("userDB/myfile.txt");
            String str = student.toString()+"\n";
            
            byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
     
            try {
                Files.write(path, bytes, StandardOpenOption.APPEND);    // Java 7+ only
                System.out.println("Successfully written data to the file");
            }
            catch (IOException e) {
                e.printStackTrace();
            }
       
            
            System.out.println("bytes2");
            
	        try {
	            // Create a byte array output stream
	            ByteArrayOutputStream bos = new ByteArrayOutputStream();
	 
	            // Use byte array output stream to create an object output stream
	            ObjectOutputStream oos = new ObjectOutputStream(bos);
	 
	            // Serialization – Pass the object that we want to copy to the
	            // ObjectOutputStream's `writeObject()` method
	            oos.writeObject(student);
	            oos.flush();
	 
	            // toByteArray creates & returns a copy of the stream's byte array
	            byte[] bytes2 = bos.toByteArray();
	 
	            // Create a byte array input stream
	            ByteArrayInputStream bis = new ByteArrayInputStream(bytes2);
	 
	            // Use byte array input stream to create an object input stream
	            ObjectInputStream ois = new ObjectInputStream(bis);
	 
	            // deserialize the serialized object using ObjectInputStream's
	            // `readObject()` method and typecast it to the class of the object
	            clone = (User) ois.readObject();
	            
	            
	 
	            System.out.println(clone.toString());
	        }
	        catch (ClassNotFoundException e) {
	            e.printStackTrace();
	        }
	        catch (IOException e) {
	            e.printStackTrace();
	        }
	        System.out.println("bytes3");
	        File file = new File("userDB/myfile.txt");
	        Charset charset = StandardCharsets.UTF_8;
	 
	        try (FileInputStream fis = new FileInputStream(file))
	        {
	            byte[] bytes2 = new byte[(int) file.length()];
	            fis.read(bytes2);
	           	 
	            String fileContent = new String(bytes2, charset);
	            System.out.println(fileContent);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
    }
