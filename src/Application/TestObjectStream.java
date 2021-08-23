package Application;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class TestObjectStream<T extends Serializable> {
	// The used for temp container for demo but not needed
	private ArrayList<T> objectList = new ArrayList<T>();
	private String fileName = null;

	@Override
	public String toString() {
		return "TestObjectStream [objectList=" + objectList + ", fileName=" + fileName + "]";
	}


	void addObject(T obj) {
		objectList.add(obj);
	}

	public TestObjectStream(String fileName) {
		super();
		this.fileName = fileName;
	}

	void storeObject() {
		FileOutputStream fOut = null;
		ObjectOutputStream oOut = null;
		try {
			fOut = new FileOutputStream(fileName,true);
			oOut = new ObjectOutputStream(fOut);
			for (T obj : objectList) {
			oOut.writeObject(obj);
		}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fOut.close();
				oOut.close();
				// Making sure no more object in the list, so only way to
				// get back the list is by loading from file
				objectList.clear();

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}

	}

	void restoreObject() {
		FileInputStream fIn = null;
		ObjectInputStream oIn = null;
		objectList = new ArrayList<T>();
		try {
			fIn = new FileInputStream(fileName);
			oIn = new ObjectInputStream(fIn);

			Object obj = null;
			// !=null is while express need boolean
			while ((obj = oIn.readObject()) !=null ) {
				T t = (T) obj;
				System.out.println("RESTORING Object " + t.toString() );
				this.addObject(t);
			}
		} catch (EOFException eof) {
			//End Of file reached
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				oIn.close();
				fIn.close();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}
	
	private static class AppendableObjectOutputStream extends
    ObjectOutputStream {

		public AppendableObjectOutputStream(OutputStream out)
			throws IOException {
			super(out);
		}
	
		@Override
		protected void writeStreamHeader() throws IOException {
		// do not write a header
		}
	}
	
	private static class AppendableObjectInputStream extends ObjectInputStream {
	
		public AppendableObjectInputStream(InputStream in) throws IOException {
			super(in);
		}
		
		@Override
		protected void readStreamHeader() throws IOException {
			// do not read a header
		}
	}

	/**
	* @param args
	*/
	public static void main(String[] args) {
		TestObjectStream<Car> testObjectStream = new TestObjectStream<Car>("userDB/userDB.txt");
		System.out.println("model");
		Scanner welcome = new Scanner(System.in);				
		String model 	= welcome.nextLine();
		System.out.println("year");
		Scanner welcome2 = new Scanner(System.in);				
		int year 	= welcome2.nextInt();
		
		testObjectStream.addObject(new Car(year,model + year));
		System.out.println("BEFORE SAVING" + testObjectStream.toString());
		testObjectStream.storeObject();
		testObjectStream.restoreObject();
		System.out.println("Afer loading the list from file" + testObjectStream.toString());
		System.out.println( testObjectStream.toString());
	}
}

