import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
// Try my own ArrayList later!

/*
	This app is derived from a dreamincode chat tutorial. I will
	be attempting to put my own encryption algorithm into the
	application later on.
*/

public class Main{
	public static void main( String[] args ) throws IOException{
		try{
			final int PORT = 6677;
			ServerSocket server = new ServerSocket( PORT );
			System.out.println( "Waiting..." );
			
			while( true ){
				Socket s = server.accept();

				System.out.println( "User connected from " + s.getLocalAddress().getHostName() );

				Client chat = new Client( s );
				Thread t = new Thread( chat );
				t.start();
			}
		}
		catch( Exception e ){
			System.out.println( "An error occurred." );
			e.printStackTrace();
		}
	}
}
