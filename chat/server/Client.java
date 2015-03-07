import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Runnable{
	private Socket socket;

	public Client( Socket s ){
		socket = s;
	}

	@Override
	public void run(){
		try{
			//Person you are chatting with's stream
			Scanner in = new Scanner( socket.getInputStream() );
			//Your stream
			PrintWriter out = new PrintWriter( socket.getOutputStream() );

			while( true ){
				if( in.hasNext() ){
					String input = in.nextLine();
					System.out.println( "User: " + input );
					out.println( "You: " + input );
					out.flush();
				}
			}
		}
		catch( Exception e ){
			System.out.println( "A connection error has occurred." );
			e.printStackTrace();
		}
	}
}
