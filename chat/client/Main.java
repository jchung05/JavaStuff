import java.io.IOException;
import java.net.Socket;

public class Main{
	private final static int PORT = 6677;
	private final static String HOST = "localhost";

	public static void main( String[] args ){
		try{
			Socket s = new Socket( HOST, PORT );
			System.out.print( "You connected to: " + HOST );
			
			Client client = new Client( s );

			Thread t = new Thread( client );
			t.start();
		}
		catch( Exception e ){
			System.out.println( "A server error occurred. Please try again." );
		}
	}
}
