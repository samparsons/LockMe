package Application;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

class Bank {
	private String username;
	private String password;
	private boolean auth;
	private int accNo;
	private String name;
	private String email;
	private long phone;
	private int creditScore;
	private double balance;
	private String state;
	private int count = 0;
		
	
	protected String getUsername() {
		return username;
	}

	protected void setUsername(String username) {
		this.username = username;
	}

	protected String getPassword() {
		return password;
	}

	protected void setPassword(String password) {
		this.password = password;
	}
	
	public synchronized boolean getAuth() {
		notify();
		return auth;
	}
	
	public int getCount() {
		return count;
	}

	public void setAuth(boolean auth) {
		this.auth = auth;
	}
	
	public int getAccNo() {
		return accNo;
	}

	public void setAccNo(int accNo) {
		this.accNo = accNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getPhone() {
		return phone;
	}

	public void setPhone(long phone) {
		this.phone = phone;
	}

	public int getCreditScore() {
		return creditScore;
	}

	public void setCreditScore(int creditScore) {
		this.creditScore = creditScore;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Bank(String username, String password, boolean auth, int accNo, String name, String email, long phone,
			int creditScore, double balance, String state, int count) {
		super();
		this.username = username;
		this.password = password;
		this.auth = auth;
		this.accNo = accNo;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.creditScore = creditScore;
		this.balance = balance;
		this.state = state;
		this.count = count;
	}

	public Bank() {
			super();
		}

	@Override
	public String toString() {
		return "Bank [username=" + username + ", password=" + password + ", auth=" + auth + ", accNo=" + accNo
				+ ", name=" + name + ", email=" + email + ", phone=" + phone + ", creditScore=" + creditScore
				+ ", balance=" + balance + ", state=" + state + ", count=" + count + "]";
	}
	
	//**********************************************************************//
	//*****************************BEGIN CUSTOM CLASES**********************//
	//**********************************************************************//
	
	public synchronized void auth(Bank b,String un,String pw) {
		if(un.equalsIgnoreCase(b.username)) {
			if(pw.equals(b.password)) {
				System.out.println("Success, logging in now.");
				b.setAuth(true);
			} else {
				System.out.println("Invaid password. Try again.");
				count++;
			}
		}else {
			System.out.println("Invalid Username. Try again.");
			count++;
		}
	}
	
	public synchronized double showBalance(Bank b) {
		return b.balance;
	}
	
	public synchronized void withdraw(Bank b,double withdrawAmt) {
		if(withdrawAmt > b.balance) {
			System.out.println("Your withdraw amount is greater than your current balance. Withdraw cancelled.");
			
		}else if(withdrawAmt < 0) {
			System.out.println("Invalid withdraw amount. Must be greater than $0.00");
			try {
				wait(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.getMessage();
			}
		}else {
			System.out.println("Withdraw of "+withdrawAmt+" commencing...");
			b.balance -= withdrawAmt;
			System.out.println("Your new balance is $"+b.balance);
		}
	}
	
	public synchronized void deposit(Bank b,double depositAmt) {
		if(depositAmt>0) {
			System.out.println("Deposit of "+depositAmt+"  commencing...");
			b.balance += depositAmt;
			System.out.println("Your new balance is $"+b.balance);
		}else {
			System.out.println("You must deposit a positive dollar amount. If you wish to make a withdraw, please type 'withdraw' in the interface.");
		}
		
	}
	
	public synchronized void closeAccount(Map<Long,Bank> m,long id) {
		System.out.println("Removing your account number "+id+" now...");
		m.remove(id);
		System.out.println("verification step. Account number "+id+"check result:"+m.get(id));
	}

	
	
}

public class BankOperations {
	
	int authAccNo = 0;
	
	boolean auth = false;
	public static void main(String[] args) {
		// write a thread safe operation for a bank
		// showBalance, withdraw, deposit, etc. 
		//use sleep, wait, notify, notify all
		
		Bank bank1 = new Bank("js","pw",false, 1001,"Jon Snow","bastarts@gmail.com",	5555551234L, 780, 5123.09,"open", 0);
		Bank bank2 = new Bank("as","pw",false, 1002,"Arya Stark",		"needle@gmail.com", 	5555551234L, 640, 1243.09,"open",0);
		Bank bank3 = new Bank("dt","pw",false, 1003,"Danerys Targaryen",	"dragonz@yahoo.com", 	5555551234L, 500, 1223.09,"open",0);
		Bank bank4 = new Bank("rb","pw",false, 1004,"Renly Baratheon",	"rendo@gmail.com", 		5555551234L, 310, 1213.09,"open",0);
		
		Map<Long,Bank> bankMap = new LinkedHashMap<Long,Bank>();
		
		bankMap.put(1L,bank1);
		bankMap.put(2L,bank2);
		bankMap.put(3L,bank3);
		bankMap.put(4L,bank4);
		
		
		
		System.out.println("------------------------------------------------------------------");
		System.out.println(" :: Welcome to the Iron Bank of Braavos				::");
		System.out.println(" :: Rated the #1 bank in Westeros for 100 years!		::");
		System.out.println("------------------------------------------------------------------");
		Thread thread1 = new Thread(() -> {
			//set variables
			Bank account = null;
			boolean until = true;
			int innerCount = 0;
			boolean menuOptions = false;
			while(until) {
				if(Objects.isNull(account)) {
					if(innerCount<=2) {
						System.out.println("Enter your bank account number below.");
						Scanner input1 			= new Scanner(System.in);				
						long bankid 	= input1.nextLong();
						account = bankMap.get(bankid);
						System.out.println("------------------------------------------------------------------");
						if(Objects.isNull(account)){
							System.out.print("Account not found. ");
							innerCount++;
							if(innerCount<=2) {
								System.out.println("Please try again.");
								System.out.println(innerCount);
							}
						}
					}else {
						System.out.print("Session Terminated.");
						until=false;
					}
				}
				if(!Objects.isNull(account) && account.getAuth()==false) {
					if(account.getCount()>=3) {
						System.out.println("You've exceeded the allowable attempts. Terminating this session.");
						until=false;
					}else {
					
					System.out.println("Enter your bank username below.");
					Scanner input2 			= new Scanner(System.in);
					String un 	= input2.nextLine();
					
					System.out.println("Enter your bank password below.");
					Scanner input3 			= new Scanner(System.in);
					String pw 	= input3.nextLine();
					
					account.auth(account, un, pw);						
					}
				}
				
				if(account.getAuth()==true && menuOptions==false) {
					Integer accNo = account.getAccNo();
					String name = account.getName();				
					System.out.println("------------------------------------------------------------------");
					System.out.println(" :: Hello "+name+"! What would you like to do today?		::");
					System.out.println("------------------------------------------------------------------");
					System.out.println(" :: Options Below						::");
					System.out.println("------------------------------------------------------------------");
					System.out.println(" :: AccountNo: 		"+accNo+" 					::");
					//Banking
					System.out.println(" :: See balance: 	type 'balance' 				::");
					System.out.println(" :: Make deposit: 	type 'deposit' 				::");
					System.out.println(" :: Make withdrawal: 	type 'withdraw' 			::");
					//Finance
					//others - need to check (insurance?)
					System.out.println(" :: Close account: 	type 'close' 				::");
					System.out.println(" :: Quit: 		type 'quit' 				::");
					System.out.println("------------------------------------------------------------------");
					menuOptions = true;
				}
				if(account.getAuth()==true && menuOptions==true) {
					System.out.println("Choose an option above. What would you like to do today?");
					Scanner input4 			= new Scanner(System.in);
					String userResponse 	= input4.nextLine();

					// Get user balance
					if(userResponse.toLowerCase().equals("balance")) {
						System.out.println("Your balance is "+account.getBalance()+".");
						System.out.println("------------------------------------------------------------------");
					}
					// Make a deposit
					else if(userResponse.toLowerCase().equals("deposit")) {
						System.out.println("Your balance is "+account.getBalance()+".");
						System.out.println("How much would you like to deposit?");
						System.out.println("------------------------------------------------------------------");
						
						Scanner deposit 	= new Scanner(System.in);
						double depositAmt 	= deposit.nextDouble();
						//Banking method deposit.
						account.deposit(account,depositAmt);
						System.out.println("------------------------------------------------------------------");
					}
					
					// Make a withdrawal
					else if (userResponse.toLowerCase().equals("withdraw")) {
						System.out.println("Your balance is "+account.getBalance()+".");
						System.out.println("How much would you like to withdraw?");
						System.out.println("------------------------------------------------------------------");
						Scanner withdraw 	= new Scanner(System.in);
						double withdrawAmt 	= withdraw.nextDouble();
						account.withdraw(account, withdrawAmt);			
						System.out.println("------------------------------------------------------------------");
					}
					// close account
					else if (userResponse.toLowerCase().equals("close")) {
						if(innerCount<=2) {
							System.out.println("Enter your bank account number below.");
							Scanner closeAcct	= new Scanner(System.in);				
							long bankid 		= closeAcct.nextLong();
							account 			= bankMap.get(bankid);
							System.out.println("------------------------------------------------------------------");
							if(Objects.isNull(account)){
								System.out.print("Account not found. ");
								innerCount++;
								if(innerCount<=2) {
									System.out.println("Please try again.");
									System.out.println(innerCount);
								}
							}else {
								account.closeAccount(bankMap,bankid);
								System.out.println("You're now being logged out. Thank you, we're sorry to lose you!");
								System.out.println("Goodbye!");
								System.out.println("------------------------------------------------------------------");
								until = false;
							}
							
						}
					}
					// quit
					else if (userResponse.toLowerCase().equals("quit")) {
						System.out.println("------------------------------------------------------------------");
						System.out.println("Goodbye!");
						System.out.println("------------------------------------------------------------------");
						until = false;
					} else {
						System.out.println("I did not recognize the command: "+userResponse);
					}
				}
				
			}			
		});
		try {
			thread1.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.getMessage();
		}
		thread1.start();
		
		
		
	}

}
