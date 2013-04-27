import java.util.ArrayList;

public class Query {

	static final int UPDATE = 0;
	static final int DELETE = 1;
	static final int SELECT = 2;
	static final int CREATE = 3;
	
	private ArrayList<String> conditions = new ArrayList<String>(); //Conditions to be met for this query
	private ArrayList<String> updates = new ArrayList<String>(); //Attributes and the values this query updates them to
	private int queryType;
	
	public Query(int t){
		queryType = t;
	}
	
	//Add a condition that this query must meet
	//name = attribute name, cond = conditional phrase
	//Ex: addCondition("firstName", "= 'derp'");
	public void addCondtion(String name, String cond){
		conditions.add(new String(name + " " + cond));
	}
	
	//Add a value to update a matching attribute to
	//name = attribute name, cond = conditional phrase
	//Ex: addUpdate("firstName", "'derp'");
	public void addUpdate(String name, String val){
		updates.add(new String(name + " " + val));
	}
	
	public int getType(){
		return queryType;
	}
	
	public void setType(int t){
		queryType = t;
	}
	
}
