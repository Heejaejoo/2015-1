
import java.util.ArrayList;

import java.util.List;


public class Station implements Comparable<Station>{

	private String line;
	private String name;
	private String code;
	private List<Route> adjacent;
	
	private int distancefromstart;
	private Station previous;

	public Station(String code, String name, String line)
	{
		this.line = line;
		this.name = name;
		this.code = code;
		adjacent = new ArrayList<Route>();
		distancefromstart = Integer.MAX_VALUE;
		previous = null;
	}
	
	public void setAdjacent(Route route)
	{
		this.adjacent.add(route);
	}
	
	public void clear()
	{
		distancefromstart = Integer.MAX_VALUE;
	}
	
	public String getname(){
		return this.name;
	}
	
	
	public void setPrevious(Station s)
	{
		previous = s;
	}
	
	public Station getPrevious()
	{
		return previous;
	}
	
	public String getcode(){
		if(code == null)
		{
			return null;
		}
		else
		return this.code;
	}
	
	public String getline(){
		return this.line;
	}
	
	public void updatedistance(int distance)
	{
		distancefromstart = distance;
	}
	
	public int getdistancefromstart()
	{
		return distancefromstart;
	}
	

	public List<Route> getAdjacent()
	{
		return this.adjacent;
	}
	
	
	@Override
	public int compareTo(Station other)
	{
		if(this.code == other.code)
		{
			return 0;
		}
		else return 1;
	}
	@Override
	public String toString()
	{
			return name;
	}
}
