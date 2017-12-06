/****************************************************/
// Filename: Graph.java
// Change history:
// 11.17.2017 / 
/****************************************************/
/* This class is responsible for 
 * 1.Generating the algorithm,
 * 2.Displaying the graph in Vertex and its adjacency list
 * 3.Read the graph from the file
 * 4.Main function which will take input from the user and display the appropriate result.
 */
/****************************************************/
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Graph {
	public static ArrayList<Vertex> vertices = new ArrayList<Vertex>();//	
	public static ArrayList<Edge> edges = new ArrayList<Edge>(); //Stores all the edges of the graph
	//The below graph Hash map stores the vertexID in the key and Edge list in the value sections
	public static HashMap<Integer,ArrayList<Edge>> graph = new HashMap<Integer,ArrayList<Edge>>();
	//The below hash map stores the vertex id in the key and corresponding Vertex object in the Value section
	public static HashMap<Integer,Vertex> VertexList = new HashMap<Integer,Vertex>();
	//The below function insert values into the graph hash map by traversing all the edges of the graph
	public static void generateGraph()
	{
		for (int i=0; i<edges.size(); i++)
		{
			Edge e;
			e= edges.get(i);
			if(!graph.containsKey(e.sourceID))//if the source ID is not available in the graph hash map
			{
				ArrayList<Edge> elist = new ArrayList<Edge>();
				elist.add(e);
				graph.put(e.sourceID,elist);
			}
			else
			{
				if(!graph.get(e.sourceID).contains(e))
				{
					graph.get(e.sourceID).add(e);
				}
			}
			if(!graph.containsKey(e.targetID))//if the target ID is not available in the graph hash map
			{
				ArrayList<Edge> elist = new ArrayList<Edge>();
				elist.add(e);
				graph.put(e.targetID,elist);
			}
			else
			{
				if(!graph.get(e.targetID).contains(e))
				{
					graph.get(e.targetID).add(e);
				}
			}
		}
	}
	//Below function will display the graph as Vertex and its adjacency list
	public static void graphDisplay()
	{
		for(int key : graph.keySet())
		{
			System.out.print(VertexList.get(key).Label+"(Adjacancy List)-->");
			for(int i = 0;i<graph.get(key).size();i++)
			{
				Edge e;
				e = graph.get(key).get(i);
				if(VertexList.get(key).id==graph.get(key).get(i).sourceID)
				{
					System.out.print(VertexList.get(e.targetID).Label+"("+e.weight+")"+" ");
				}
				else
				{
					System.out.print(VertexList.get(e.sourceID).Label+"("+e.weight+")"+" ");
				}
				
			}
			System.out.println();
		}
	}
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = null;
		BufferedWriter bw = null;
		FileWriter fw = null;
		File inputfile = null;
		Pattern p = Pattern.compile("\"([^\"]*)\"");
		Matcher m;
		String str;
		Vertex v;
		Edge e;
		String input;
		int recentChoice=0;
		long timeDijkstra=0,timePrims=0;
		ArrayList<String> trackPath = new ArrayList<String>();
		Scanner sc=new Scanner(System.in);
		inputfile = new File("C:\\Users\\kedar\\eclipse-workspace\\ProjectAssignment 4\\src\\lesmis.gml");
		br = new BufferedReader(new FileReader(inputfile));
		//The below code will parse through the gml file and extract nodes and edges of the graph
		while((str=br.readLine())!= null)
		{
			String ltrim = str.replaceAll("^\\s+","");
			String rtrim = ltrim.replaceAll("\\s+$","");
			if(rtrim.equals("node"))
			{
				if(br.readLine().trim().equals("["))
				{
					String ID;
					ID = br.readLine().trim().replaceAll("\\D+","");
					m=p.matcher(br.readLine());
					while(m.find())
					{
						v=new Vertex(m.group(1),Integer.parseInt(ID),Integer.MAX_VALUE,0);
						vertices.add(v);
						VertexList.put(Integer.parseInt(ID), v);
					}
				}
			}
			if(rtrim.equals("edge"))
			{
				if(br.readLine().trim().equals("["))
				{
					int soureID,targetID,weight;
					soureID = Integer.parseInt(br.readLine().trim().replaceAll("\\D+",""));
					targetID= Integer.parseInt(br.readLine().trim().replaceAll("\\D+",""));
					weight = Integer.parseInt(br.readLine().trim().replaceAll("\\D+",""));
					e=new Edge(soureID,targetID,weight);
					edges.add(e);
				}
			}
		}
		br.close();
		Dijkstra d = new Dijkstra(VertexList);
		System.out.println("enter your keyword");
		while(true)
		{
			input=sc.nextLine();
			switch(input) {
			//The below case generates the graph
			case "graph": generateGraph();
						  System.out.println("Graph generated Successfully");
						  break;
			//The below case run the Dijkstra's algorithm and stored the output in a data structure
			case "dijkstra": if(graph.isEmpty())
								{
									System.out.println("Please construct the graph first by using graph keyword");
									break;
								}
							recentChoice=1;
							long startTime=System.currentTimeMillis();
							d.generatePath();
							long endTime=System.currentTimeMillis();
							timeDijkstra=endTime-startTime;
							System.out.println("Dijkstra's algorithm has been selected");
							break;
			//The below case run the Prim's algorithm and stored the output in a data structure
			case "prims": if(graph.isEmpty())
							{
								System.out.println("Please construct the graph first by using graph keyword");
								break;
							}
							long startTime2=System.currentTimeMillis();
							Prims.findPath();
							long endTime2=System.currentTimeMillis();
							timePrims=endTime2-startTime2;
						  recentChoice=2;
						  System.out.println("Prim's algorithm is selected");
						  break;
			case "quit": return;
			//The below case prints the output of the recent algorithm selected
			case "print": if(graph.isEmpty())
							{
								System.out.println("Please construct the graph first by using graph keyword");
								break;
							}
						  if(recentChoice==1)
						  {
							  for (int key : Dijkstra.VertexList.keySet()) {
								  if (key==0)
									  continue;
								  if(Dijkstra.VertexList.get(key).parentID==0)
								  {
									  System.out.print(Dijkstra.VertexList.get(0).Label+"-->");
									  System.out.print(Dijkstra.VertexList.get(key).Label);
									  System.out.println(" (Shortest path cost:"+Dijkstra.VertexList.get(key).shortPathEstimate+")");
								  }
								  else
								  {
									  trackPath.add(Dijkstra.VertexList.get(key).Label);
									  Vertex vertx;
									  vertx = Dijkstra.VertexList.get(key);
									  while (vertx.parentID!=0)
									  {
										  vertx = Dijkstra.VertexList.get(vertx.parentID);
										  trackPath.add(vertx.Label);
									  }
									  trackPath.add(VertexList.get(0).Label);
									  for (int i=trackPath.size()-1;i>=0;i--)
									  {
										  if(i==0)
										  {
											  System.out.print(trackPath.get(i));
											  System.out.print(" (Shortest path cost:"+Dijkstra.VertexList.get(key).shortPathEstimate+")");
											  continue;
										  }
										  System.out.print(trackPath.get(i)+"-->");
									  }
									  trackPath.clear();
									  System.out.println();
								  }
							  }
						  }
						  else if(recentChoice==2)
						  {
							System.out.println("Minimum Spanning Tree : ");
							for (int key : Prims.visitedmap.keySet()) {
									System.out.println(VertexList.get(key).Label + "---" + VertexList.get(Prims.visitedmap.get(key)).Label);
							}
							System.out.println("Total cost of the minimum spanning tree: "+Prims.totalWeight);
						  }
						  else
						  {
							  System.out.println("Please select an algorithm to print the output");
						  }
						  break;
			//The below case calculated the performance of the algorithm that is selected
			case "performance":if(graph.isEmpty())
								{
									System.out.println("Please construct the graph first by using graph keyword");
									break;
								} 
								if(recentChoice==1)
								{
									System.out.println("Time Taken in milli seconds for Dijkstra Algorithm: "+timeDijkstra);
								}
								else if(recentChoice==2)
								{
									System.out.println("Time Taken in milli seconds for Prim's algorithm: "+timePrims);
								}
								else
								{
									System.out.println("Please select an algorithm to print the Performance");
								}
								break;
			case "maingraph":if(graph.isEmpty())
							 {
								System.out.println("Please construct the graph first by using graph keyword");
								break;
							 } 
							graphDisplay();
							break;
			default: System.out.println("Please enter a proper key word");
					break;
			}
		}
	}
}
