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
	static JPanel pnlCalendar; //The panel
	static int realDay, realMonth, realYear, currentMonth, currentYear;
	static String[] headers = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};

	public static void main( String[] args ){
		try{
			UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() ); }
		catch ( ClassNotFoundException e ) {}
		catch ( InstantiationException e ) {}
		catch ( IllegalAccessException e ) {}
		catch ( UnsupportedLookAndFeelException e ) {}

		//Create calendar
		instantiation();
		
		//Register action listeners
		actionListeners();
		
		//Add controls to pane
		addControls();

		//Set bounds
		setAllBounds();
		
		//Make frame visible
		frame.setResizable( false );
		frame.setVisible( true );

		//Get real month/year
		getRealMY();

		//Add headers
		for( String s : headers ) mtblCalendar.addColumn( s );

		populateCalendar();

		for( int i = realYear - 50; i <= realYear + 50; i++ ) cmbYear.addItem( String.valueOf(i) );
		
		refreshCalendar( realMonth, realYear );
	}
	
	public static void refreshCalendar( int month, int year ){
		String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
		int nod, som;
		
		//Prepare buttons
		btnPrev.setEnabled( true );
		btnNext.setEnabled( true );
		if( month == 0 && year <= realYear - 50 ) btnPrev.setEnabled( false );
		if( month == 11 && year >= realYear + 50 ) btnNext.setEnabled( false );
		monthL.setText( months[month] );
		monthL.setBounds( 160-monthL.getPreferredSize().width/2, 25, 180, 25 );
		cmbYear.setSelectedItem( String.valueOf( year ) );

		//Get number of days(nod) and start of month(som)
		GregorianCalendar cal = new GregorianCalendar( year, month, 1 );
		nod = cal.getActualMaximum( GregorianCalendar.DAY_OF_MONTH );
		som = cal.get( GregorianCalendar.DAY_OF_WEEK );

		//Clear table
		for( int i = 0; i < 6; i++ ){
			for( int j = 0; j < 7; j++ ) mtblCalendar.setValueAt( null, i, j );
		}

		for( int i = 1; i <= nod; i++ ){
			int row = new Integer( (i+som-2) / 7 );
			int column = ( i + som - 2 ) % 7;
			mtblCalendar.setValueAt( i, row, column );
		}
		tblCalendar.setDefaultRenderer(tblCalendar.getColumnClass(0), new tblCalendarRenderer());
	}
	
	static void instantiation( ){
		frame = new JFrame( "My First Calendar!" );
		frame.setSize( 330, 375 );
		pane = frame.getContentPane();
		pane.setLayout( null );
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		monthL = new JLabel(  );
		yearL = new JLabel( "Change year: " );
		cmbYear = new JComboBox();
		btnPrev = new JButton( "<" );
		btnNext = new JButton( ">" );
		mtblCalendar = new DefaultTableModel(){public boolean isCellEditable(int rowIndex, int mColIndex){return false;}};
		tblCalendar = new JTable(mtblCalendar);
		stblCalendar = new JScrollPane(tblCalendar);
		pnlCalendar = new JPanel( null );
		pnlCalendar.setBorder( BorderFactory.createTitledBorder( "Calendar" ) );
	}
	
	static void addControls( ){
		pane.add( pnlCalendar );
		pnlCalendar.add( monthL );
		pnlCalendar.add( yearL );
		pnlCalendar.add( cmbYear );
		pnlCalendar.add( btnPrev );
		pnlCalendar.add( btnNext );
		pnlCalendar.add( stblCalendar );
	}
	
	static void actionListeners( ){
		btnPrev.addActionListener( new btnPrev_Action() );
		btnNext.addActionListener( new btnNext_Action() );
		cmbYear.addActionListener( new cmbYear_Action() );
	}
	
	static void setAllBounds( ){
		pnlCalendar.setBounds( 0, 0, 320, 335 );
		monthL.setBounds( 160-monthL.getPreferredSize().width/2, 25, 100, 25 );
		yearL.setBounds( 130, 304, 120, 20 );
		cmbYear.setBounds( 230, 305, 80, 20 );
		btnPrev.setBounds( 10, 25, 50, 25 );
		btnNext.setBounds( 260, 25, 50, 25 );
		stblCalendar.setBounds( 10, 50, 300, 250 );
	}
	
	static void getRealMY( ){
		GregorianCalendar cal = new GregorianCalendar();
		realDay = cal.get( GregorianCalendar.DAY_OF_MONTH );
		realMonth = cal.get( GregorianCalendar.MONTH );
		realYear = cal.get( GregorianCalendar.YEAR );
		currentMonth = realMonth;
		currentYear = realYear;
	}
	
	static void populateCalendar( ){
		tblCalendar.getParent().setBackground( tblCalendar.getBackground() );

		tblCalendar.getTableHeader().setResizingAllowed( false );
		tblCalendar.getTableHeader().setReorderingAllowed( false );

		tblCalendar.setColumnSelectionAllowed( true );
		tblCalendar.setRowSelectionAllowed( true );
		tblCalendar.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );

		tblCalendar.setRowHeight( 38 );
		mtblCalendar.setColumnCount( 7 );
		mtblCalendar.setRowCount( 6 );
	}
	
	static class tblCalendarRenderer extends DefaultTableCellRenderer{
		public Component getTableCellRendererComponent (JTable table, Object value, boolean selected, boolean focused, int row, int column){
			super.getTableCellRendererComponent(table, value, selected, focused, row, column);
			if (column == 0 || column == 6){ //Week-end
			    setBackground(new Color(255, 220, 220));
			}
			else{ //Week
			    setBackground(new Color(255, 255, 255));
			}
			if (value != null){
			    if (Integer.parseInt(value.toString()) == realDay && currentMonth == realMonth && currentYear == realYear){ //Today
				setBackground(new Color(220, 220, 255));
			    }
			}
			setBorder(null);
			setForeground(Color.black);
			return this; 
		}
	}
 
	static class btnPrev_Action implements ActionListener{
		public void actionPerformed (ActionEvent e){
			if (currentMonth == 0){ //Back one year
				currentMonth = 11;
				currentYear -= 1;
			}
			else{ //Back one month
				currentMonth -= 1;
			}
			refreshCalendar(currentMonth, currentYear);
		}
	}
	static class btnNext_Action implements ActionListener{
		public void actionPerformed (ActionEvent e){
			if (currentMonth == 11){ //Foward one year
				currentMonth = 0;
				currentYear += 1;
			}
			else{ //Foward one month
				currentMonth += 1;
			}
			refreshCalendar(currentMonth, currentYear);
		}
	}
	static class cmbYear_Action implements ActionListener{
		public void actionPerformed (ActionEvent e){
			if (cmbYear.getSelectedItem() != null){
				String b = cmbYear.getSelectedItem().toString();
				currentYear = Integer.parseInt(b);
				refreshCalendar(currentMonth, currentYear);
			}
		}
	}
}
