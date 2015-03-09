import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;

/*
	A tutorial from javaworld for making a multi-threaded
	chat application. I won't be making any alterations to
	this project with my own classes or mods minus updating
	deprecated method calls.

	Thread stop() -> interrupt()
	Text show()/hide() -> setVisible( boolean b )
	Component handleEvent( Event e ) -> processEvent( AWTEvent e )
*/

public class ChatClient extends Frame implements Runnable{
	protected DataInputStream i;
	protected DataOutputStream o;
	protected TextArea output;
	protected TextField input;
	protected Thread listener;

	public ChatClient( String title, InputStream i, OutputStream o ){
		super( title );
		this.i = new DataInputStream( new BufferedInputStream ( i ) );
		this.o = new DataOutputStream( new BufferedOutputStream( o ) );
		setLayout( new BorderLayout() );
		add( "Center", output = new TextArea() );
		output.setEditable( false );
		add( "South", input = new TextField() );
		pack();
		setVisible(true);
		input.requestFocus();
		listener = new Thread( this );
		listener.start();
	}

	public void run(){
		try{
			while( true ){
				String line = i.readUTF();
				output.append( line + "\n" );
			}
		}
		catch( IOException io ){
			io.printStackTrace();
		}
		finally{
			listener = null;
			input.setVisible(false);
			validate();
			try{
				o.close();
			}
			catch( IOException io ){
				io.printStackTrace();
			}
		}
	}

//	New non-deprecated replacement processEvent
/*	
	public void processEvent( AWTEvent e ){
		if( e.getSource() == input && e.getID() == AWTEvent.ACTION_EVENT_MASK ){
			try{
				o.writeUTF( ______ );
				o.flush();
			}
			catch( IOException io ){
				io.printStackTrace();
				listener.interrupt();
			}
			input.setText( "" );
		}
		else if( e.getSource() == this && e.getID() == WindowEvent.WINDOW_CLOSING ){
			if( listener != null ) listener.interrupt();
			setVisible( false );
		}
	}
*/
	public boolean handleEvent( Event e ){
		if( e.target == input && e.id == Event.ACTION_EVENT ){
			try{
				o.writeUTF( (String) e.arg );
				o.flush();
			}
			catch( IOException io ){
				io.printStackTrace();
				listener.interrupt();
			}
			input.setText( "" );
			return true;
		}
		else if( e.target == this && e.id == Event.WINDOW_DESTROY ){
			if( listener != null ) listener.interrupt();
			setVisible( false );
			return true;
		}
		return super.handleEvent( e );
	}

	public static void main( String args[] ) throws Exception{
		if( args.length != 2 ) throw new RuntimeException( "Syntax: "
			+ "ChatClient <host> <port>" );
		Socket s = new Socket( args[0], Integer.parseInt( args[1] ) );
		new ChatClient( "Chat " + args[0] + ":" + args[1], s.getInputStream(), s.getOutputStream() );
	}
}
