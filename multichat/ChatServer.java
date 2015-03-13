import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer{
	public ChatServer( int port ) throws IOException{
		ServerSocket server = new ServerSocket( port );
		System.out.println( "Waiting for a user..." );
		while( true ){
			Socket client = server.accept();
			System.out.println( "Accepted from " + client.getInetAddress() );
			ChatHandler c = new ChatHandler( client );
			c.start();
		}
	}

	public static void main( String args[] ) throws IOException{
/*		if( args.length != 1 ){
			System.out.println( args.length );
			throw new RuntimeException( "Syntax: Chatserver <port>" );
		}
		new ChatServer( Integer.parseInt( args[0] ) );*/
		try{
			final int PORT = 6677;
			new ChatServer( PORT );
		}
		catch( Exception e ){
			System.out.println( "An error occurred." );
			e.printStackTrace();
		}
	}
}
