import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.HashSet;
import java.util.Set;

public class Subway {

	final static Map<String, Station> codeMap = new HashMap<String, Station>();
	final static Map<String, Station> nameMap = new HashMap<String, Station>();
	
	public static void main(String args[]) throws Exception
	{
		// drawing graph by using two hashmaps
		
		BufferedReader br = new BufferedReader(new FileReader(args[0]));
		String line = null;
		while(!((line = br.readLine()).equals("")))
		{
			String[] arga = line.split(" ");
			Station newStation = new Station(arga[0], arga[1], arga[2]);
			codeMap.put(arga[0], newStation);
			
			if(!nameMap.containsKey(arga[1]))
			{
				nameMap.put(arga[1], newStation);
			}else if(nameMap.get(arga[1]).getcode() == null)
			{
				// when more than two lines are linked, add link to transfer station 
				Station prev = nameMap.get(arga[1]);
				prev.setAdjacent(new Route(prev, newStation, 0));
				newStation.setAdjacent(new Route(newStation, prev, 5));
			}
			else
			{
				//first time when transfer station occurs -> making arbitrary transfer station(trans)
				Station prev = nameMap.get(arga[1]);
				Station trans = new Station(null, arga[1], null);
				
				prev.setAdjacent(new Route(prev, trans, 5));
				trans.setAdjacent(new Route(trans, prev, 0));
				newStation.setAdjacent(new Route(newStation, trans, 5));
				trans.setAdjacent(new Route(trans, newStation, 0));
				
				nameMap.put(arga[1], trans);

			}
		}
		
		while((line = br.readLine()) != null)
		{
			String[] arga = line.split(" ");
			Route tempRoute = new Route(codeMap.get(arga[0]), codeMap.get(arga[1]), arga[2]);
			codeMap.get(arga[0]).setAdjacent(tempRoute);
		}
		br.close();
		
		//Command line
		BufferedReader br2 = new BufferedReader(new InputStreamReader(System.in));
		
		while (true)
		{
			try
			{
				String input = br2.readLine();
				if (input.compareTo("QUIT") == 0)
					break;

				command(input);
			}
			catch (IOException e)
			{
				System.out.println("입력이 잘못되었습니다. 오류 : " + e.toString());
			}
		}
	}

	private static void command(String input)
	{
		String[] startandend = input.split(" ");
		boolean Whatisthis = true;
		if(startandend.length == 2) // minimum time consume 
		{
			Dijkstra(startandend[0], startandend[1], Whatisthis);
		}else // minimum transfer
		{
			Dijkstra(startandend[0], startandend[1], !Whatisthis);
		}
	}
	
	private static void Dijkstra(String start, String end, boolean what)
	{
		Station from = nameMap.get(start);
		Station to = nameMap.get(end);
		FibonacciHeap<Station> routes = new FibonacciHeap<Station>();
		Set<Station> forclear = new HashSet<Station>();
		from.updatedistance(0);
		routes.enqueue(from, from.getdistancefromstart());
		forclear.add(from);
		
		while(!routes.isEmpty())
		{
			Station current = routes.dequeueMin().getValue();
			
			for(Route path : current.getAdjacent())
			{
				Station s = path.getTo();
				int weight = path.getweight();
				int tweight;
				if(s.getcode() == null)
				{
					tweight = 1000000;
				}else
				{
					tweight = 0;
				}
				
				int distancethroughcurrent;
				if(what)
					distancethroughcurrent = current.getdistancefromstart() + weight;
				else
					// if transfer, add sufficiently large integer(1000000) to avoid transfer 
					distancethroughcurrent = current.getdistancefromstart() + weight + tweight;
				
				if(distancethroughcurrent < s.getdistancefromstart())
				{
					s.updatedistance(distancethroughcurrent);
					s.setPrevious(current);				
					routes.enqueue(s, s.getdistancefromstart());
					forclear.add(s);
				}
			}
		}
		Print(from, to, what);
		for(Station s : forclear)
		{
			s.clear();
		}
	}

	
	public static void Print(Station start, Station end, boolean what)
	{
	Station to = end;
	String result = "";
	int time;
	if(to.getcode() == null)
	{
		to = to.getPrevious();
	}
	time = to.getdistancefromstart();
	
	while(to.getname() != start.getname())
	{
		if(to.getPrevious().getcode() == null && to.getPrevious().getPrevious() == null && to.getPrevious() != null){
			// if the destination is transferstation
			break;
		}
		
		else if(to.getPrevious().getcode() == null && to.getPrevious().getPrevious()!= null && to.getPrevious() != null)
		{
			if(to.getPrevious().getPrevious().getPrevious().getname() == start.getname() && to.getPrevious().getPrevious().getPrevious() == to.getPrevious())
			{
				//remove double circle made by transfer
				break;
			}
			result = "[" + to.toString() + "] " + result;
			to = to.getPrevious().getPrevious().getPrevious();
			
		}else
		{
			result = to.toString() +" " + result;
			to = to.getPrevious();
		}
		
	}
	
	result = start.toString() +" " + result;
	
	System.out.println(result.trim());
	if(what)
	System.out.println(time);
	else
		System.out.println(time%1000000);
	}
	
	
	
}
