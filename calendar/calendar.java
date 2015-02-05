import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class calendar{
	static JLabel monthL, yearL;
	static JButton btnPrev, btnNext;
	static JTable tblCalendar;
	static JComboBox cmbYear;
	static JFrame frame;
	static Container pane;
	static DefaultTableModel mtblCalendar; //Table model
	static JScrollPane stblCalendar; //The scrollpane
	static int realDay, realMonth, realYear, currentMonth, currentYear;

	public static void main( String[] args ){
		try{
			UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() ); }
		catch ( ClassNotFoundException e ) {}
		catch ( InstantiationException e ) {}
		catch ( IllegalAccessException e ) {}
		catch ( UnsupportedLookAndFeelException e ) {}

		frame = new JFrame( "Calendar" );
		frame.setSize( 330, 375 );
		pane = frame.getContentPane();
		pane.setLayout( null );
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		monthL = new JLabel( "January" );
		yearL = new JLabel( "Change year: " );
		cmbYear = new JComboBox();
		btnPrev = new JButton( "Previous" );
		btnNext = new JButton( ">>" );
		mtblCalendar = new DefaultTableModel();
		tblCalendar = new JTable(mtblCalendar);
		stblCalendar = new JScrollPane(tblCalendar);
		pnlCalendar = new JPanel( null );
		pnlCalendar.setBorder( BorderFactory.createTitledBorder( "Calendar" ) );
	}
}
