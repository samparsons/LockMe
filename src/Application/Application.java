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
import Model.Creds;
import Model.User;

public class Application {

	public static void main(String[] args) {
		ArrayList<User> userList = new ArrayList<User>();
		userList = welcomeScreen();
		userPortal(userList);
		
	}
	
	// *********************
	// WELCOME SCREEN
	// *********************
	private static ArrayList<User> welcomeScreen() {
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
		
		// LOGIN OR REGISTER
		ArrayList<User> userList = new ArrayList<User>();
		if(choice.equals("register")) {
			userList = register();
		} else {
			userList = login(choice);
		}
		return userList;
	}
	
	// ***********************
	// LOGIN METHOD
	// ***********************
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
						System.out.println("LOGIN SUCCESSFUL...");
						//readUsers(choice);
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
		return userList;
	}
	
	// ***********************
	// REGISTRATION MAIN METHOD - create usernames if they don't exist
	// ***********************
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
					//System.out.println("| reg validUser: "+validUser+"                                                               |");
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
					//System.out.println("|-------------------------------------------------------------------------------|");
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
			} catch (UserExistsException e) {
				System.out.println(e);
				System.out.println("|-------------------------------------------------------------------------------|");
			}
			
			
		} return userList;
	}
	
	// ***********************
	// UserPortal MAIN METHOD - create usernames if they don't exist
	// ***********************
	private static void userPortal(ArrayList<User> userList) {
		checkDirCreds();
		createCredDB(userList);
		System.out.println("|-------------------------------------------------------------------------------|");
		System.out.println(repeatCharSpace("WELCOME TO LOCKME! CHOOSE AN OPTION:"));
		// add a counter if you have time. YOU HAVE X SAVED CREDENTIALS!
		System.out.println(repeatCharSpace("1: SEE SAVED CREDENTIALS"));
		System.out.println(repeatCharSpace("2. SAVE NEW CREDENTIALS"));
		System.out.println(repeatCharSpace("3: SEARCH FOR SAVED CREDENTIALS"));
		System.out.println(repeatCharSpace("4: DELETE A SAVED CREDENTIAL"));
		System.out.println(repeatCharSpace("5: RETURN TO MAIN MENU"));
		System.out.println(repeatCharSpace("6: QUIT"));
		System.out.println("|-------------------------------------------------------------------------------|");
		boolean menu=true;
		int counter = 0;
		while(menu) {
			if(counter>0) {
				System.out.println(repeatCharSpace("CHOOSE AN OPTION (CHOOSE 7 TO SEE CHOICES)"));
			}
			Scanner userPortal = new Scanner(System.in);				
			int choice 	= userPortal.nextInt();
			if (choice == 1) {
				System.out.println(repeatCharSpace("1: SEE SAVED CREDENTIALS"));
				System.out.println(userList.get(0).getUsername());
				readCreds(userList);
				counter++;
			} else if (choice == 2) {
				System.out.println(repeatCharSpace("2. SAVE NEW CREDENTIALS"));
				saveCreds(userList);
				counter++;
			} else if (choice == 3) {
				System.out.println(repeatCharSpace("3: SEARCH FOR SAVED CREDENTIALS"));
				searchCreds(userList);
				counter++;
			} else if (choice == 4) {
				System.out.println(repeatCharSpace("4: DELETE A SAVED CREDENTIAL"));
				deleteCred(userList);
				counter++;
			} else if (choice == 5) {
				System.out.println(repeatCharSpace("5: RETURN TO MAIN MENU"));
				menu=false;
				welcomeScreen();
			} else if (choice == 6) {
				System.out.println(repeatCharSpace("6: QUIT"));
				menu=false;
				break;
			} else if (choice == 7) {
				System.out.println("|-------------------------------------------------------------------------------|");
				System.out.println(repeatCharSpace("CHOOSE AN OPTION:"));
				System.out.println(repeatCharSpace("1: SEE SAVED CREDENTIALS"));
				System.out.println(repeatCharSpace("2. SAVE NEW CREDENTIALS"));
				System.out.println(repeatCharSpace("3: SEARCH FOR SAVED CREDENTIALS"));
				System.out.println(repeatCharSpace("4: DELETE A SAVED CREDENTIAL"));
				System.out.println(repeatCharSpace("5: RETURN TO MAIN MENU"));
				System.out.println(repeatCharSpace("6: QUIT"));
				System.out.println("|-------------------------------------------------------------------------------|");
			}
			
		}
	}
	// *********************
	// deleteCred method - for a given user, delete all creds that match key value given.
	// *********************
	private static void deleteCred(ArrayList<User> userList) {
		// DO DELETE NEXT
		
	}

	// *********************
	// searchCreds method - for a given user, find all creds that match key value given.
	// *********************
	private static void searchCreds(ArrayList<User> userList) {
		System.out.println(repeatCharSpace("SEARCH YOUR CREDENTIALS! FIRST, CHOOSE A SEARCH TYPE..."));
		System.out.println("|-------------------------------------------------------------------------------|");
		System.out.println(repeatCharSpace("SEARCH TYPES:"));
		System.out.println(repeatCharSpace("1: USERNAME SEARCH"));
		System.out.println(repeatCharSpace("2. PASSWORD SEARCH"));
		System.out.println(repeatCharSpace("3: SITE SEARCH"));
		System.out.println(repeatCharSpace("4: QUIT"));
		System.out.println("|-------------------------------------------------------------------------------|");
		
		
		
		ArrayList<Creds> existingCreds = new ArrayList<Creds>(getCreds(userList));
		boolean searching = true;
		while(searching) {
			System.out.print("| CHOOSE SEARCH TYPE: ");
			Scanner choice   = new Scanner(System.in);				
			int type = choice.nextInt();
			if(type == 1) {
				System.out.println(repeatCharSpace("USERNAME SEARCH: ENTER USERNAME..."));
				System.out.print("| USERNAME: ");
				Scanner username  = new Scanner(System.in);				
				String searchUn = 	username.nextLine();
				for (Creds c : existingCreds) {
					if(c.getUsername()!=null&&c.getUsername().equals(searchUn)) {
						int counter = 1;
						System.out.print("| USERNAME MATCH: "+counter);
						System.out.println(repeatCharSpace("USERNAME: "+c.getUsername()));
						System.out.println(repeatCharSpace("PASSWORD: "+c.getPassword()));
						System.out.println(repeatCharSpace("SITENAME: "+c.getSite()));
						System.out.println("|-------------------------------------------------------------------------------|");
						counter++;
					}
				}
			
			} else if (type == 2) {
				System.out.println(repeatCharSpace("PASSWORD SEARCH: ENTER PASSWORD..."));
				System.out.print("| PASSWORD: ");
				Scanner password  = new Scanner(System.in);				
				String searchPw = 	password.nextLine();
				for (Creds c : existingCreds) {
					if(c.getPassword()!=null&&c.getPassword().equals(searchPw)) {
						int counter = 1;
						System.out.print("| PASSWORD MATCH: "+counter);
						System.out.println(repeatCharSpace("USERNAME: "+c.getUsername()));
						System.out.println(repeatCharSpace("PASSWORD: "+c.getPassword()));
						System.out.println(repeatCharSpace("SITENAME: "+c.getSite()));
						System.out.println("|-------------------------------------------------------------------------------|");
						counter++;
					}
				}
				
			} else if (type == 3) {
				System.out.println(repeatCharSpace("SITE SEARCH: ENTER SITENAME..."));
				System.out.print("| SITE: ");
				Scanner site  = new Scanner(System.in);				
				String searchSite = 	site.nextLine();
				for (Creds c : existingCreds) {
					if(c.getSite()!=null&&c.getSite().equals(searchSite)) {
						int counter = 1;
						System.out.print("| SITE MATCH: "+counter);
						System.out.println(repeatCharSpace("USERNAME: "+c.getUsername()));
						System.out.println(repeatCharSpace("PASSWORD: "+c.getPassword()));
						System.out.println(repeatCharSpace("SITENAME: "+c.getSite()));
						System.out.println("|-------------------------------------------------------------------------------|");
						counter++;
					}
				}
			} else if (type == 4) {
				System.out.println("| QUITTING SEARCH. RETURNING TO USER PORTAL...");
				searching = false;
			} 
		}
	}

	private static ArrayList<Creds> getCreds(ArrayList<User> userList) {
		ArrayList<Creds> existingCreds = new ArrayList<Creds>();
		String username = userList.get(0).getUsername();
		try {
			File f = new File("credDB/"+username+".txt");			
			if(f.length()==0) {
				Creds creds = new Creds(userList.get(0).getUsername(),userList.get(0).getPassword(),"htpps://lockme.com");
				existingCreds.add(creds);
			} else {
			FileInputStream file = new FileInputStream("credDB/"+username+".txt");
			ObjectInputStream in = new ObjectInputStream(file);
			@SuppressWarnings("unchecked")
			ArrayList<Creds> existingCreds1 = (ArrayList<Creds>) in.readObject();
			existingCreds = existingCreds1;
			in.close();
			file.close();
			}
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
		
		return existingCreds;
		
	}

	// *********************
	// saveCreds method - for a given user, create new creds.
	// *********************
	private static void saveCreds(ArrayList<User> userList) {
		String username = userList.get(0).getUsername();
	
		// show all users saved
		System.out.println(repeatCharSpace("SAVE NEW CREDENTIALS..."));
		System.out.print("| SITENAME: ");
		Scanner newSite  = new Scanner(System.in);				
		String newSiteIn = 	newSite.nextLine();
		
		System.out.print("| NEW USERNAME: ");
		Scanner newUsername  = new Scanner(System.in);				
		String newUsernameIn = newUsername.nextLine();
		
		System.out.print("| NEW PASSWORD: ");
		Scanner newPassword  = new Scanner(System.in);				
		String newPasswordIn = 	newPassword.nextLine();
		
		System.out.println("|-------------------------------------------------------------------------------|");
		System.out.println("| CONFIRM CREDENTIALS: ");
		System.out.println("|-------------------------------------------------------------------------------|");
		System.out.println(repeatCharSpace("USERNAME: "+newUsernameIn));
		System.out.println(repeatCharSpace("PASSWORD: "+newPasswordIn));
		System.out.println(repeatCharSpace("SITENAME: "+newSiteIn));
		
		System.out.print("| ARE THESE CORRECT? (Y/N): ");
		Scanner confirm   = new Scanner(System.in);				
		String confChoice = confirm.nextLine();
		try {
			FileInputStream file = new FileInputStream("credDB/"+username+".txt");
			ObjectInputStream in = new ObjectInputStream(file);
			ArrayList<Creds> existingCreds = (ArrayList<Creds>) in.readObject();
			if(confChoice.equals("Y")) {
				Creds cred = new Creds(newUsernameIn,newPasswordIn,newSiteIn);
				existingCreds.add(cred);
				// create file output stream, make appendable.				
				FileOutputStream fos = new FileOutputStream("credDB/"+username+".txt");				
				// create object output stream
				ObjectOutputStream out = new ObjectOutputStream(fos);		
				// method to serialize object
				out.writeObject(existingCreds);
				out.close();
				fos.close();
				System.out.println(repeatCharSpace("CREDENTIALS SAVED!"));
			} else { // else do nothing
				System.out.println(repeatCharSpace("CANCELLING SAVE NOW..."));
			}
			
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}

	// *********************
	// readCreds method - outputs all credentials in the credDB file for the user.
	// *********************
	private static void readCreds(ArrayList<User> userList) {
		ArrayList<Creds> existingCreds = new ArrayList<Creds>();
		String username = userList.get(0).getUsername();
		try {
			File f = new File("credDB/"+username+".txt");			
			if(f.length()==0) {
				Creds creds = new Creds(userList.get(0).getUsername(),userList.get(0).getPassword(),"htpps://lockme.com");
				existingCreds.add(creds);
			} else {
			FileInputStream file = new FileInputStream("credDB/"+username+".txt");
			ObjectInputStream in = new ObjectInputStream(file);
			@SuppressWarnings("unchecked")
			ArrayList<Creds> existingCreds1 = (ArrayList<Creds>) in.readObject();
			existingCreds = existingCreds1;
			in.close();
			file.close();
			}
			// show all users saved
			System.out.println(repeatCharSpace("GETTING CREDENTIALS..."));
			int counter = 0;
			for(Creds cred : existingCreds) {
				System.out.println(repeatCharSpace("USERNAME: "+cred.getUsername()));
				System.out.println(repeatCharSpace("PASSWORD: "+cred.getPassword()));
				System.out.println(repeatCharSpace("SITENAME: "+cred.getSite()));
				counter++;
			}
			if(counter==0) {
				System.out.println(repeatCharSpace("NO CREDENTIALS FOUND. SAVE SOME!"));
			}
			
			
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}

	// *********************
	// checkDirCreds method - creates the credDB folder if it doesn't exist.
	// *********************
	private static void checkDirCreds(){
		File file = new File("credDB");
		boolean ckDir = file.exists();
		if(!ckDir) {
			//Creating the directory
			boolean bool = file.mkdir();
		    if(bool){
		    	String message = "SUCCESS: userDB file created. LockMe is ready to run.";
		    	System.out.println(repeatCharSpace(message));
		    }else{
		    	String message = "ERROR: Could not create credDB file.";
		    	System.out.println(repeatCharSpace(message));
		    }
		}
	}
	
	// *********************
	// createCredDB method - creates a credDB file for the user.
	// *********************
	private static void createCredDB(ArrayList<User> userList) {
		String path = "credDB/"+userList.get(0).getUsername()+".txt";
		try {
			File file = new File(path);
			boolean chkFile = file.exists();
			if(!chkFile) {
				file.createNewFile();
				ArrayList<Creds> newCreds = new ArrayList<Creds>();
				Creds creds = new Creds(userList.get(0).getUsername(),userList.get(0).getPassword(),"htpps://lockme.com");
				newCreds.add(creds);
				// create file output stream, make appendable.				
				FileOutputStream fos = new FileOutputStream(path);				
				// create object output stream
				ObjectOutputStream out = new ObjectOutputStream(fos);		
				// method to serialize object
				out.writeObject(newCreds);
				System.out.println("|-------------------------------------------------------------------------------|");
				System.out.println("| New credentials storage created...                                            |");	
				System.out.println("|-------------------------------------------------------------------------------|");
				out.close();
				fos.close();
			} else {
				System.out.println("|-------------------------------------------------------------------------------|");
				System.out.println("| Saved credentials found...                                                    |");
				System.out.println("|-------------------------------------------------------------------------------|");
			}
			
		} catch(IOException ex) {
				System.out.println(ex.getMessage());
		}
	}
	
	// ***********************
	// checkDir method - check for directory, create if it doesn't exist.
	// ***********************
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
	
	// ***********************
	// checkUser method - check for user existence
	// ***********************
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
	
	// *********************
	// createUser method - create the user object so it can be added to ArrayList
	// *********************
	private static User createUser(String username,String password) throws UserExistsException {
		User user = new User(username, password);
		return user;
	}
	// *********************
	// saveUser method - saves a username and password to file as an ArrayList
	// *********************
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
	
	// *********************
	// getUsers method - get userList from file.
	// *********************
	@SuppressWarnings("unchecked")
	private static ArrayList<User> getUsers(String username) {
		ArrayList<User> userList = new ArrayList<User>();
		try {
			FileInputStream file = new FileInputStream("userDB/"+username+".txt");
			ObjectInputStream in = new ObjectInputStream(file);
			userList = (ArrayList<User>) in.readObject();	
			file.close();
			in.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return userList;
	}
	
	// *********************
	// readUsers method - read the user file.
	// *********************
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
			file.close();
			in.close();
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		} 
	}
	
	// *********************
	// ReadObjectFromFile method - QA purposes to see the file bitstream
	// *********************
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
