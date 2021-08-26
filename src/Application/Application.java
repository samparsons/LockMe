package Application;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;
import Exceptions.PatternNotValidException;
import Exceptions.UserExistsException;
import Exceptions.UserNotFoundException;
import Model.User;

public class Application {

	public static void main(String[] args) {
		welcomeScreen();

	}
	// NOTE: write a main() method to call all required programs in main class. welcomeScreen is in this class.
	
	private static void welcomeScreen() {
		System.out.println("Logo credit: https://patorjk.com/software/taag/#p=display&f=Isometric1&t=LockMe");
		System.out.println("");
		System.out.println("");
		System.out.println("      ___       ___           ___           ___           ___           ___     ");
		System.out.println("     /\\__\\     /\\  \\         /\\  \\         /\\__\\         /\\__\\         /\\  \\    ");
		System.out.println("    /:/  /    /::\\  \\       /::\\  \\       /:/  /        /::|  |       /::\\  \\   ");
		System.out.println("   /:/  /    /:/\\:\\  \\     /:/\\:\\  \\     /:/__/        /:|:|  |      /:/\\:\\  \\  ");
		System.out.println("  /:/  /    /:/  \\:\\  \\   /:/  \\:\\  \\   /::\\__\\____   /:/|:|__|__   /::\\~\\:\\  \\ ");
		System.out.println(" /:/__/    /:/__/ \\:\\__\\ /:/__/ \\:\\__\\ /:/\\:::::\\__\\ /:/ |::::\\__\\ /:/\\:\\ \\:\\__\\");
		System.out.println(" \\:\\  \\    \\:\\  \\ /:/  / \\:\\  \\  \\/__/ \\/_|:|~~|~    \\/__/~~/:/  / \\:\\~\\:\\ \\/__/");
		System.out.println("  \\:\\  \\    \\:\\  /:/  /   \\:\\  \\          |:|  |           /:/  /   \\:\\ \\:\\__\\  ");
		System.out.println("   \\:\\  \\    \\:\\/:/  /     \\:\\  \\         |:|  |          /:/  /     \\:\\ \\/__/  ");
		System.out.println("    \\:\\__\\    \\::/  /       \\:\\__\\        |:|  |         /:/  /       \\:\\__\\    ");
		System.out.println("     \\/__/     \\/__/         \\/__/         \\|__|         \\/__/         \\/__/    ");
		System.out.println(" Developed by Sam Parsons");
		System.out.println(" Class: Phase-1: Implement OOPS using JAVA with Data Structures and Beyond");
		System.out.println(" Due Date: Aug 29, 2021");
		System.out.println("");
		System.out.println("");		
		System.out.println("|-------------------------------------------------------------------------------|");
		System.out.println("| To login: Enter Username                                                      |");
		System.out.println("| To register: type 'register'                                                  |");
		System.out.println("|-------------------------------------------------------------------------------|");
		
		Scanner welcome = new Scanner(System.in);				
		String choice 	= welcome.nextLine();
		System.out.println("|-------------------------------------------------------------------------------|");
		if(choice.equals("register")) {
			register();
		} else {
			login(choice);
		}
	}
	// LOGIN METHOD:
	// 1 create the user db if doesn't exist
	// 2 scan user file for user object with matching username
	private static ArrayList<User> login(String choice) {
		System.out.println("|-------------------------------------------------------------------------------|");
		System.out.println("| Logging in...                                                                 |");
		System.out.println("|-------------------------------------------------------------------------------|");
		checkDir();
		ArrayList<User> userList = new ArrayList<User>();
		
		boolean login = true;
		while(login) {
			boolean user = true;
			boolean pass = true;
			int counter = 0;
			while(user) {
				boolean validUser 	= false;
				boolean newUser 	= false;
				if(counter > 0) {
					System.out.println("|-------------------------------------------------------------------------------|");
					System.out.println("| Re-enter Username or type 'Quit' to exit                                      |");
					System.out.println("|-------------------------------------------------------------------------------|");
					Scanner welcome	= new Scanner(System.in);				
					choice 			= welcome.nextLine();
				} 
				if(choice.equals("Quit")) {
					System.out.println("|-------------------------------------------------------------------------------|");
					System.out.println("| Exiting program. Thank you for using LockMe!                                  |");
					System.out.println("|-------------------------------------------------------------------------------|");
					break;
				}else if(choice.equals("register")) {
					register();
					user=false;
				} else {
				
					try {
						validUser = validateString(choice);
						System.out.println("|-------------------------------------------------------------------------------|");
					} catch (PatternNotValidException e) {
						System.out.println(e);
						System.out.println("|-------------------------------------------------------------------------------|");
					}
					
					try {
						newUser = checkUser(choice);
					} catch (UserNotFoundException e) {
						System.out.println(e);
						System.out.println("|-------------------------------------------------------------------------------|");
					}
					
					if(validUser && newUser) {
						user = false;
					}
					
				}
				counter++;
			}
			
			while(pass) {
				String password = "";
				if(choice.equals("Quit")) {
					System.out.println("|-------------------------------------------------------------------------------|");
					System.out.println("| Exiting program. Thank you for using LockMe!                                  |");
					System.out.println("|-------------------------------------------------------------------------------|");
					break;
				}
				if(counter == 0) {
					System.out.println("|-------------------------------------------------------------------------------|");
					System.out.println("| Enter Password or type 'Quit' to exit                                      |");
					System.out.println("|-------------------------------------------------------------------------------|");
					Scanner welcome	= new Scanner(System.in);				
					password = welcome.nextLine();
				} else if(counter > 0) {
					System.out.println("|-------------------------------------------------------------------------------|");
					System.out.println("| Try a New Password or type 'Quit' to exit                                      |");
					System.out.println("|-------------------------------------------------------------------------------|");
					Scanner welcome	= new Scanner(System.in);				
					password = welcome.nextLine();
				}
				
				userList = getUsers(choice);
				String savedPassword = userList.get(0).getPassword();
							
				if(password.equals(savedPassword)) {
					try {
						System.out.println("read users...");
						readUsers(choice);
						System.out.println("---------------------------------------------------------------------------------");
						ReadObjectFromFile(choice);
						System.out.println("---------------------------------------------------------------------------------");
					} catch (UserExistsException e) {
						System.out.println(e);
						System.out.println("|-------------------------------------------------------------------------------|");
					}
					pass = false;
					login = false;
				}
				counter++;
			}
			
		}
		System.out.println("LOGIN SUCCESS....");
		return userList;
	}
	
	// LOGIN 1: check to see if directory exists, and if not, create it.
	private static void checkDir(){
		File file = new File("userDB");
		boolean ckDir = file.exists();
		if(!ckDir) {
			//Creating the directory
			boolean bool = file.mkdir();
		    if(bool){
		    	String message = "SUCCESS: userDB file created. LockMe is ready to run.";
		    	System.out.println(repeatCharSpace(message));
		    }else{
		    	String message = "ERROR: Could not create userDB file.";
		    	System.out.println(repeatCharSpace(message));
		    }
		}
	}
	
	private static boolean checkUser(String choice) throws UserNotFoundException{
		File file = new File("UserDB/"+choice+".txt");
		boolean ckFile = file.exists();
		if(!ckFile) {
			String message = "ERROR: Username "+choice+" not found. Please register.";
			throw new UserNotFoundException(repeatCharSpace(message));
		} else {
			return true;
		}
	}
	
	// REGISTRATION MAIN METHOD
	private static ArrayList<User> register() {
		ArrayList<User> userList = new ArrayList<User>();
		System.out.println("| Begin registration...                                                         |");
		System.out.println("|-------------------------------------------------------------------------------|");
		checkDir();
		System.out.println("|-------------------------------------------------------------------------------|");
		System.out.println("| Enter Username to continue                                                     |");
		System.out.println("|-------------------------------------------------------------------------------|");
		Scanner welcome = new Scanner(System.in);				
		String username = welcome.nextLine();
		String password = "";
		
		boolean registration = true;
		while(registration) {
			boolean user 	= true;
			boolean pass 	= true;
			int counter 	= 0;
			while(user) {
				boolean validUser 	= false;
				if(counter > 0) {
					System.out.println("|-------------------------------------------------------------------------------|");
					System.out.println("| Re-enter Username or type 'Quit' to exit                                      |");
					System.out.println("|-------------------------------------------------------------------------------|");
					welcome		= new Scanner(System.in);				
					username 	= welcome.nextLine();
				}
				if(username.equals("Quit")) {
					//System.out.println("|-------------------------------------------------------------------------------|");
					System.out.println("| Exiting program. Thank you for using LockMe!                                  |");
					System.out.println("|-------------------------------------------------------------------------------|");
					break;
				}
				
				try {
					//System.out.println("|-------------------------------------------------------------------------------|");
					validUser = validateString(username);
					System.out.println("| reg validUser: "+validUser+"                                                               |");
					System.out.println("|-------------------------------------------------------------------------------|");
				} catch (PatternNotValidException e) {
					System.out.println(e);
					System.out.println("|-------------------------------------------------------------------------------|");
				}
				
				if(validUser) {
					user = false;
				}
				counter++;
			}
			
			while(pass) {
				boolean validPass 	= false;
				if(counter > 0) {
					System.out.println("|-------------------------------------------------------------------------------|");
					System.out.println("| Enter Password or type 'Quit' to exit                                      |");
					System.out.println("|-------------------------------------------------------------------------------|");
					welcome		= new Scanner(System.in);				
					password 	= welcome.nextLine();
				}
				if(password.equals("Quit")) {
					//System.out.println("|-------------------------------------------------------------------------------|");
					System.out.println("| Exiting program. Thank you for using LockMe!                                  |");
					System.out.println("|-------------------------------------------------------------------------------|");
					break;
				}
				
				try {
					//System.out.println("|-------------------------------------------------------------------------------|");
					validPass = validateString(password);
					System.out.println("| reg validPass: "+validPass+"                                                               |");
					System.out.println("|-------------------------------------------------------------------------------|");
				} catch (PatternNotValidException e) {
					System.out.println(e);
					System.out.println("|-------------------------------------------------------------------------------|");
				}
				
				if(validPass) {
					pass = false;
				}
				counter++;
			}
			
			
			try {
				userList.add(createUser(username,password));
				System.out.println("Save users...");
				saveUser(userList);
				System.out.println("read users...");
				readUsers(username);
				System.out.println("---------------------------------------------------------------------------------");
				ReadObjectFromFile(username);
				System.out.println("---------------------------------------------------------------------------------");
			} catch (UserExistsException e) {
				System.out.println(e);
				System.out.println("|-------------------------------------------------------------------------------|");
			}
			
			
		} return userList;
	}
	
	
	
	private static User createUser(String username,String password) throws UserExistsException {
		User user = new User(username, password);
		return user;
	}
	
	private static void saveUser(ArrayList<User> userList) throws UserExistsException {
		String path = "userDB/"+userList.get(0).getUsername()+".txt";
		try {
			File file = new File(path);
			boolean chkFile = file.exists();
			if(!chkFile) {
				file.createNewFile();
				// create file output stream, make appendable.				
				FileOutputStream fos = new FileOutputStream(path,true);				
				// create object output stream
				ObjectOutputStream out = new ObjectOutputStream(fos);		
				// method to serialize object
				out.writeObject(userList);
				System.out.println("New User Created. 1");	
				out.close();
				fos.close();
			} else {
				String message = "ERROR: User already exists.";
				throw new UserExistsException(repeatCharSpace(message));
			}
			
		} catch(IOException ex) {
				System.out.println(ex.getMessage());
		}
	}
	
	
	@SuppressWarnings("unchecked")
	private static ArrayList<User> getUsers(String username) {
		ArrayList<User> userList = new ArrayList<User>();
		try {
			FileInputStream file = new FileInputStream("userDB/"+username+".txt");
			ObjectInputStream in = new ObjectInputStream(file);
			userList = (ArrayList<User>) in.readObject();	
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return userList;
	}
	
	@SuppressWarnings("unchecked")
	private static void readUsers(String username) {
		try {
			FileInputStream file = new FileInputStream("userDB/"+username+".txt");
			ObjectInputStream in = new ObjectInputStream(file);
			ArrayList<User> user = (ArrayList<User>) in.readObject();	
			System.out.println(user);
			// show all users saved
			for(User u : user) {
				System.out.println(u.getUsername());
				System.out.println(u.getPassword());
				
			}
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}	
	}
	
	public static void ReadObjectFromFile(String username) {
		
		File file = new File("userDB/"+username+".txt");
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
	
	//ERRORS
	private static boolean validateString(String input) throws PatternNotValidException {
		String pattern = "[a-zA-Z0-9]+";
		boolean result = Pattern.matches(pattern, input);
		if(result==false) {
			String message = "ERROR: Username & Password may only contain alphanumeric characters. The provided string '"+input+"' contains invalid characters.";
			throw new PatternNotValidException(repeatCharSpace(message));               
 			                                    
		} else {
			return true;
		}
	}
	
	// UI HELPERS
	// only works for up to 2 line messages.
	public static String repeatCharSpace(String message) {
		char repeatChar = new Character(' ');
		String width = "|-------------------------------------------------------------------------------|";
		String terminator = "";
		//message = "| " + message;
		// 81 - 4 = 77 - gives space for leading "| " and ending " |"
		int interfaceWidth = width.length()-4;
		int messageLength  = message.length(); 
		if(messageLength > interfaceWidth) {
			if(message.substring(77,78)==" ") {
				terminator = "  |\n| ";
			} else {
				terminator = "- |\n| ";
			}
			message = "| "+message.substring(0,76)+terminator+message.substring(76);
			int repeatTimes = 81 - message.substring(80).length();
		    String result = "";
		    for(int i = 0; i < repeatTimes; i++) {
		        result += repeatChar;
		    }
		    message = message + result + " |";
		} else {
			message = "| "+message;
			int repeatTimes = 79 - message.length();
		    String result = "";
		    for(int i = 0; i < repeatTimes; i++) {
		        result += repeatChar;
		    }
		    message = message + result  + " |";
		}
	    return message;
	}

	

}
