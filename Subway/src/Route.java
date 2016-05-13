
public class Route implements Comparable<Route>{

	private Station from;
	private Station to;
	private int weight;
	
	public Route(Station from, Station to, int weight)
	{
		this.from = from;
		this.to = to;
		this.weight = weight;
	}
	
	public Route(Station from, Station to, String weight)
	{
		this.from = from;
		this.to = to;
		this.weight = Integer.parseInt(weight);
	}
	
	public Station getFrom()
	{
		return this.from;
	}
	
	public Station getTo()
	{
		return this.to;
	}
	
	public int getweight()
	{
		return weight;
	}

	@Override
	public int compareTo(Route other) {
		if(this.from == other.from && this.to == other.to)
		return 0;
		else
		return 1;
	}
	

	
}
