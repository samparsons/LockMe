package Application;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Pattern;

import Model.User;
import Model.UserExistsException;
import Model.UserNotFoundException;
import Model.PatternNotValidException;

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
	private static void login(String choice) {
		//System.out.println("|-------------------------------------------------------------------------------|");
		System.out.println("| Logging in...                                                                 |");
		System.out.println("|-------------------------------------------------------------------------------|");
		checkDir();
		
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
					//System.out.println("|-------------------------------------------------------------------------------|");
					System.out.println("| Exiting program. Thank you for using LockMe!                                  |");
					System.out.println("|-------------------------------------------------------------------------------|");
					break;
				}else if(choice.equals("register")) {
					register();
				} else {
				
					try {
						//System.out.println("|-------------------------------------------------------------------------------|");
						validUser = validateString(choice);
						//System.out.println("| validUser: "+validUser+"                                                               |");
						System.out.println("|-------------------------------------------------------------------------------|");
					} catch (PatternNotValidException e) {
						System.out.println(e);
						System.out.println("|-------------------------------------------------------------------------------|");
					}
					
					try {
						newUser = checkUser(choice);
						System.out.println("new user?: "+newUser);
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
			
			
			//STILL NEED PASSWORD AFTER I CREATE REG DB
			
			
			
			//kill code for dev
			login = false;
		}		
	}
	
	// LOGIN 1: check to see if directory exists, and if not, create it.
	private static void checkDir(){
		File file = new File("userDB");
		boolean ckDir = file.exists();
		if(!ckDir) {
			//Creating the directory
			boolean bool = file.mkdir();
		    if(bool){
				System.out.println("| userDB created...                                                             |");
		    }else{
		    	System.out.println("| Error: could not create userDB...                                             |");
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
	private static void register() {
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
			//kill code for dev
			try {
				User newUser = createUser(username,password);
				System.out.println("new user?: "+newUser);
			} catch (UserExistsException e) {
				System.out.println(e);
				System.out.println("|-------------------------------------------------------------------------------|");
			}
			
			registration = false;
		}
	}
	
	private static User createUser(String username,String password) throws UserExistsException {
		Integer id = 0;
		User user = new User(id, username, password);
		System.out.println(user);
		try {

			File file = new File("userDB/"+username+".txt");
			boolean chkFile = file.exists();
			if(!chkFile) {
				file.createNewFile();
				// create file output stream, make appendable.
				
				FileOutputStream fos = new FileOutputStream("userDB/"+username+".txt");
				
				// create object output stream
				ObjectOutputStream out = new ObjectOutputStream(fos);
				
				// method to serialize object
				out.writeObject(user);
				
				System.out.println("Serialization is completed.");
				
				out.close();
				fos.close();
			} else {
				String message = "ERROR: Username "+username+" already exists.";
				throw new UserExistsException(repeatCharSpace(message));
			}
			
			
			
		} catch(IOException ex) {
			System.out.println(ex.getMessage());
		}
		
		return user;
		
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
