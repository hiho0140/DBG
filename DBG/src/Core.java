/*
 * Core.java
 * 
 * Team Members:
 * Steven Zuchowski
 * Kevin Mahoney
 * Ian Smudde
 * 
 * 
 * Core provides the bulk of the JDBCGUI's GUI functionality, as well
 * as the entirety of its interactions with the SQL database. All queries
 * are run via calls to methods within Core, and Core keeps track of the
 * ResultSet returned by that query.
 * 
 */

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import org.postgresql.PGResultSetMetaData;

public class Core {

	public static Core core;
	private static Connection con;
	private ResultSet curResults;
	private SQLFrame dia;
	
	public ArrayList<String> tables, attribNames;
	public ArrayList<Integer> attribTypes;
	
	private NameManager nameMan = new NameManager();
	
	//GUI components
	private JButton newEntry, editEntry, delEntry, retEntry;
	private JTable table;
	private JScrollPane scroller;
	private DefaultTableModel model;
	private QuickPanel quickPanel;
	private ViewPanel viewPanel;
	private Sidebar quickBar;
	private JPanel centerPanel;
	private CardLayout cl;
	private JPanel rawPanel;
	private JTextField rawField;
	private JButton rawButton;
	public JFrame window;
	
	public static final String TABLE = "table", QUICKPANEL = "quickpanel", VIEWPANEL = "viewpanel";
	
	private ArrayList<SQLDialog> dialogs = new ArrayList<SQLDialog>();
	
	public static void main(String[] args){
		
		SQLLogin log = new SQLLogin();
		if(JOptionPane.showConfirmDialog(null, log, "Login", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION){
			//Connect to the database
			try {
				Class.forName("org.postgresql.Driver");
				
				String un = log.getUsername();
				String ps = log.getPassword();
				con = DriverManager.getConnection(
				        "jdbc:postgresql://reddwarf.cs.rit.edu/" + un,
				        un,
				        ps);
				core = new Core();
				core.initGUI();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				JOptionPane.showMessageDialog(null, e.getMessage());
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}

	}
	
	public Core(){
		//Initialize Shtuff
		tables = new ArrayList<String>();
		attribNames = new ArrayList<String>();
		attribTypes = new ArrayList<Integer>();
	}
	
	private void initGUI(){
		
		window = new JFrame("JDBC GUI");
		window.setLayout(new BorderLayout());
		
		//========================//
		//======= MENU BAR =======//
		//========================//
		
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		
		JMenuItem exitItem = new JMenuItem("Exit");
		exitItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				Core.core.quitProgram();
			}	
		});

		fileMenu.add(exitItem);
		
		menuBar.add(fileMenu);
		
		
		//=========================//
		//==== RESULTS DISPLAY ====//
		//=========================//
		
		//Put the table in a scrollpane so the column headers show up properly
		model = new DefaultTableModel(null, attribNames.toArray()){
			public boolean isCellEditable(int row, int col){
				return false;
			}
		};
		table = new JTable(model);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		scroller = new JScrollPane(table);
		quickPanel = new QuickPanel();
		viewPanel = new ViewPanel();
		
		cl = new CardLayout();
		centerPanel = new JPanel(cl);
		centerPanel.add(scroller, Core.TABLE);
		centerPanel.add(quickPanel, Core.QUICKPANEL);
		centerPanel.add(viewPanel, Core.VIEWPANEL);

		//========================//
		//====== SIDE PANEL ======//
		//========================//
		
		quickBar = new Sidebar();
		
		
		//=========================//
		//======= RAW QUERY =======//
		//=========================//
					
		rawPanel = new JPanel(new BorderLayout());
		rawField = new JTextField();
		rawButton = new JButton("GO");
		rawButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent paramActionEvent) {
				Core.core.runRawQuery();
			}
		});
		
		rawPanel.add(rawField, BorderLayout.CENTER);
		rawPanel.add(rawButton, BorderLayout.EAST);
		
					
		//========================//
		//===== CRUD BUTTONS =====//
		//========================//
		
		newEntry = new JButton("Add Entry");
		newEntry.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				Core.core.spawnDialog(1);
			}
		});
		
		editEntry = new JButton("Update Entries");
		editEntry.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				Core.core.spawnDialog(2);
			}
		});
		
		retEntry = new JButton("View Entries");
		retEntry.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				Core.core.spawnDialog(3);
			}
		});
		
		delEntry = new JButton("Delete Entries");
		delEntry.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				Core.core.spawnDialog(4);
			}
		});
		
		JPanel buttonPanel = new JPanel();
		GridLayout bpLayout = new GridLayout(2, 2);
		bpLayout.setHgap(10);
		bpLayout.setVgap(10);
		buttonPanel.setLayout(bpLayout);
		buttonPanel.add(newEntry);
		buttonPanel.add(retEntry);
		buttonPanel.add(editEntry);
		buttonPanel.add(delEntry);
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new BorderLayout());
		bottomPanel.add(buttonPanel, BorderLayout.CENTER);
		bottomPanel.add(rawPanel, BorderLayout.NORTH);

			
		//========================//
		//====== FINALIZING ======//
		//========================//
		window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		window.addWindowListener(new WindowListener(){
			public void windowClosing(WindowEvent arg0) {
				Core.core.quitProgram();
			}
			public void windowActivated(WindowEvent arg0) {}
			public void windowClosed(WindowEvent arg0) {}
			public void windowDeactivated(WindowEvent arg0) {}
			public void windowDeiconified(WindowEvent arg0) {}
			public void windowIconified(WindowEvent arg0) {}
			public void windowOpened(WindowEvent arg0) {}
		});

		window.add(centerPanel, BorderLayout.CENTER);
		window.add(bottomPanel, BorderLayout.SOUTH);
		window.add(quickBar, BorderLayout.WEST);
		window.setJMenuBar(menuBar);
		window.setSize(1000, 600);
		window.setResizable(false);
		window.setVisible(true);
				
	}
	
	public synchronized void spawnDialog(int type){
		//not sure if memory leak :<
		if(dia == null){
			dia = new SQLFrame();
		}
		switch(type){
			case 1:
				//dia = new CreateDialog();
				dia.setDialog(new CreateDialog(dia));
				break;
			case 2:
				dia.setDialog(new UpdateDialog(dia));
				//dia = new UpdateDialog();
				break;
			case 3:
				dia.setDialog(new RetrieveDialog(dia));
				//dia = new RetrieveDialog();
				break;
			case 4:
				dia.setDialog(new DeleteDialog(dia));
				//dia = new DeleteDialog();
				break;
			default:
				break;
		}
	}
	
	public ArrayList<String> getTableNames(boolean includeViews) throws SQLException{
		ResultSet rs;
		if(includeViews){
			rs = runQuery("SELECT table_name FROM information_schema.tables WHERE table_schema = 'public';");
		}else{
			rs = runQuery("SELECT table_name FROM information_schema.tables WHERE table_schema = 'public' and table_type != 'VIEW';");
		}
		tables.clear();
		
		while(rs.next()){
			tables.add(rs.getString(1));
		}
		
		return tables;
	}
	
	public void updateAttributeNames() throws SQLException{
		attribNames.clear();
		
		for(int i = 1; i <= curResults.getMetaData().getColumnCount(); i++){
			attribNames.add(curResults.getMetaData().getColumnName(i));
		}
	}
	
	public ArrayList<String> getAttributeNames(){
		return attribNames;
	}
	
	public ArrayList<String> getAttributeNames(String name) throws SQLException{
		ResultSet rs = runQuery("SELECT column_name FROM information_schema.columns WHERE table_name = '" + name + "';");
		ArrayList<String> result = new ArrayList<String>();
		ArrayList<String> temp = new ArrayList<String>();
		
		while(rs.next()){
			temp.add(rs.getString(1));
		}
		
		//Need to reverse the order of attribute names because the DB gives them to us backwards.
		for(int i = temp.size() - 1; i >= 0; i--){
			result.add(temp.get(i));
		}
		
		return result;
	}
	
	public void updateDataTypes() throws SQLException{
		attribTypes.clear();
		
		for(int i = 1; i <= curResults.getMetaData().getColumnCount(); i++){
			attribTypes.add(new Integer(curResults.getMetaData().getColumnType(i)));
		}
	}
	
	public ArrayList<Integer> getDataTypes(){
		return attribTypes;
	}
	
	public ArrayList<Integer> getDataTypes(String name) throws SQLException{
		//This needs to query the database for all attribute
		//datatypes in the specified table and return a
		//suitable arraylist of them.
		
		ResultSet rs = runQuery("SELECT * from " + name + ";");
		ResultSetMetaData rsmd = rs.getMetaData();
		ArrayList<Integer> result = new ArrayList<Integer>();
		
		for(int i = 1; i <= rsmd.getColumnCount(); i++){
			result.add(new Integer(rsmd.getColumnType(i)));
		}
		return result;
	}
	
	public synchronized ResultSet runQuery(String q){
		Statement stmt;
		try {
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			curResults = stmt.executeQuery(q);
			updateAttributeNames();
			updateDataTypes();
		} catch (SQLException e) {
			if(!(!q.startsWith("SELECT") && e.getMessage().equals("No results were returned by the query."))){
				//e.printStackTrace();
				JOptionPane.showMessageDialog(null, e.getMessage());
			}
		}
		rawField.setText(q);
		return curResults;
	}
	
	public synchronized ResultSet runSilentQuery(String q){
		Statement stmt;
		try {
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			curResults = stmt.executeQuery(q);
			updateAttributeNames();
			updateDataTypes();
		} catch (SQLException e) {
			//e.printStackTrace();
		}
		return curResults;
	}
	
	public synchronized void runDialogQuery(Query q, String tn){
		runQuery(q.toString());
		populateTable(curResults, tn);
		switchToTable();
	}
	
	public void runDialogQuery(String string, String tn) {
		runQuery(string);
		populateTable(curResults, tn);
		switchToTable();
	}
	
	public synchronized void runQuickQuery(String q, String n, boolean b){
		runQuery(q);
		generateQuickPanel(0, n, b, q);
		switchToQuickPanel();
	}
	
	public synchronized void runQuickQuery(String q, String[] n, boolean b){
		runQuery(q);
		generateQuickPanel(0, n, b, q);
		switchToQuickPanel();
	}
	
	public void runQuickView(String v, String n){
		runQuery("SELECT * FROM " + v + ";");
		generateViewPanel(v, n);
		switchToViewPanel();
	}
	
	public void runQuickView(String v, String[] n){
		runQuery("SELECT * FROM " + v + ";");
		generateViewPanel(v, n);
		switchToViewPanel();
	}
	
	public synchronized void runRawQuery(){
		runQuery(rawField.getText());
		populateTable(curResults);
		switchToTable();
	}
	
	public void populateTable(ResultSet rs, String tn){
		Vector<Vector<String>> data = new Vector<Vector<String>>();
		Vector<String> next;
		attribNames.clear();
		
		try {
			while(rs.next()){
				next = new Vector<String>();
				for(int i = 1; i <= rs.getMetaData().getColumnCount(); i++){
					next.add(rs.getString(i));
				}
				data.add(next);
			}
			for(int i = 1; i <= rs.getMetaData().getColumnCount(); i++){
				attribNames.add(rs.getMetaData().getColumnName(i));
			}
			populateTable(getNiceNames(tn, attribNames), data);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}
	
	public void populateTable(ResultSet rs){
		Vector<Vector<String>> data = new Vector<Vector<String>>();
		Vector<String> next;
		attribNames.clear();
		
		try {
			while(rs.next()){
				next = new Vector<String>();
				for(int i = 1; i <= rs.getMetaData().getColumnCount(); i++){
					next.add(rs.getString(i));
				}
				data.add(next);
			}
			for(int i = 1; i <= rs.getMetaData().getColumnCount(); i++){
				attribNames.add(rs.getMetaData().getColumnName(i));
			}
			populateTable(new Vector<String>(attribNames), data);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}
	
	public void populateTable(Vector<String> colNames, Vector<Vector<String>> data){
		model.setDataVector(data, colNames);
		table.repaint();
	}
	
	public void generateQuickPanel(int index, String n, boolean b, String q){
		try {
			if(curResults.getMetaData().getColumnCount() > 0){
				quickPanel.setTableName((((PGResultSetMetaData)curResults.getMetaData()).getBaseTableName(1)));
				populateQuickPanel(getAttributeNames(), getDataTypes(), getRowArrayList(index));
				quickPanel.setListName(n);
				quickPanel.updateList(getResultList(n));
				quickPanel.setQuery(q);
				quickPanel.setCanApply(b);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}
	
	public void generateQuickPanel(int index, String[] n, boolean b, String q){
		try {
			if(curResults.getMetaData().getColumnCount() > 0){
				quickPanel.setTableName((((PGResultSetMetaData)curResults.getMetaData()).getBaseTableName(1)));
				populateQuickPanel(getAttributeNames(), getDataTypes(), getRowArrayList(index));
				quickPanel.setListName(n);
				quickPanel.updateList(getResultList(n));
				quickPanel.setQuery(q);
				quickPanel.setCanApply(b);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}
	
	public void populateQuickPanel(int index){
		if(index >= 0){
			ArrayList<String> data = new ArrayList<String>();
			
			try {
				curResults.absolute(index + 1);
				for(int i = 1; i <= curResults.getMetaData().getColumnCount(); i++){
					data.add(curResults.getString(i));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
			
			quickPanel.populateFields(data);
		}
	}
		
	public void populateQuickPanel(ArrayList<String> colNames, ArrayList<Integer> types, ArrayList<String> data){
		quickPanel.generateFields(colNames, types);
		quickPanel.populateFields(data);
	}
	
	public void generateViewPanel(String viewName, String groupBy){
		viewPanel.generateList(viewName, groupBy);
	}
	
	public void generateViewPanel(String viewName, String[] groupBy){
		viewPanel.generateList(viewName, groupBy);
	}
	
	public void populateViewPanel(int index){
		if(index >= 0){
			Vector<Vector<String>> data = new Vector<Vector<String>>();
			Vector<String> next;
			String groupVal = viewPanel.getGroupValue(index), groupName = viewPanel.getGroupName();
			try {
				curResults.first();
				do{
					next = new Vector<String>();
					if(curResults.getString(groupName).equals(groupVal)){
						for(int i = 1; i <= curResults.getMetaData().getColumnCount(); i++){
							next.add(curResults.getString(i));
						}
						data.add(next);
					}
				}while(curResults.next());
				viewPanel.populateTable(data);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
	}
	
	public void switchView(){
		cl.next(centerPanel);
	}

	public void switchToTable(){
		cl.show(centerPanel, Core.TABLE);
	}
	
	public void switchToQuickPanel(){
		cl.show(centerPanel, Core.QUICKPANEL);
	}
	
	public void switchToViewPanel(){
		cl.show(centerPanel, Core.VIEWPANEL);
	}
	
	public String getNiceName(String tableName, String attributeName){
		return nameMan.getNameFor(tableName, attributeName);
	}
	
	public Vector<String> getNiceNames(String tableName, ArrayList<String> attributeNames){
		Vector<String> result = new Vector<String>();
		for(String s : attributeNames){
			if(nameMan.getNameFor(tableName, s).length() > 0){
				result.add(nameMan.getNameFor(tableName, s));
			}
		}
		return result;
	}
	
	public ArrayList<String> getRowArrayList(int index){
		ArrayList<String> results = new ArrayList<String>();
		try {
			curResults.absolute(index + 1);
			for(int i = 1; i <= curResults.getMetaData().getColumnCount(); i++){
				results.add(curResults.getString(i));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		return results;
	}
	
	public Vector<String> getRowVector(int index){
		Vector<String> results = new Vector<String>();
		try {
			curResults.absolute(index);
			for(int i = 1; i <= curResults.getMetaData().getColumnCount(); i++){
				results.add(curResults.getString(i));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		return results;
	}
	
	public ArrayList<String> getResultList(String n){
		ArrayList<String> result = new ArrayList<String>();
		
		try {
			curResults.first();
			do{
				result.add(curResults.getString(n));
			}while(curResults.next());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		
		return result;
	}
	
	public ArrayList<String> getViewList(String n){
		ArrayList<String> result = new ArrayList<String>();
		
		try {
			curResults.first();
			do{
				if(!result.contains(curResults.getString(n))){
					result.add(curResults.getString(n));
				}
			}while(curResults.next());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		
		return result;
	}
	
	public ArrayList<String> getResultList(String[] n){
		ArrayList<String> result = new ArrayList<String>();
		ArrayList<String> names = new ArrayList<String>(Arrays.asList(n));
		
		try {
			curResults.first();
			do{
				String temp = new String();
				for(int i = 0; i < names.size(); i++ ){
					temp = temp + curResults.getString(names.get(i));
					if(i < names.size() - 1){
						temp = temp + ", ";
					}
				}
				result.add(temp);
			}while(curResults.next());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		
		return result;
	}
	
	public void quitProgram(){
		//ABANDON THREAD!! ABANDON THREAD!!
		// #rocketsnail
		
		//Shouldn't need any save checks here, since everything is
		//stored serverside. Maybe just a quick "are you sure?"
		
		if(dia != null){
			dia.dispose();
		}
		window.dispose();
	}

}
