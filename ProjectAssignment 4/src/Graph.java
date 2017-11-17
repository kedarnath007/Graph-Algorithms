/*Graph class*/
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
	public static ArrayList<Vertex> vertices = new ArrayList<Vertex>();
	public static ArrayList<Edge> edges = new ArrayList<Edge>(); 
	public static HashMap<Integer,ArrayList<Edge>> graph = new HashMap<Integer,ArrayList<Edge>>();
	public static HashMap<Integer,Vertex> VertexList = new HashMap<Integer,Vertex>();
	public static void generateGraph()
	{
		for (int i=0; i<edges.size(); i++)
		{
			Edge e;
			e= edges.get(i);
			if(!graph.containsKey(e.sourceID))
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
			if(!graph.containsKey(e.targetID))
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
					System.out.print(VertexList.get(e.targetID).Label+"("+e.weight+")"+", ");
				}
				else
				{
					System.out.print(VertexList.get(e.sourceID).Label+"("+e.weight+")"+", ");
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
		Scanner sc=new Scanner(System.in);
		inputfile = new File("C:\\Users\\kedar\\eclipse-workspace\\ProjectAssignment 4\\src\\dijkstra_ex.gml");
		br = new BufferedReader(new FileReader(inputfile));
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
			case "graph": generateGraph();
						  System.out.println("Graph generated Successfully");
						  break;
			case "dijkstra": if(graph.isEmpty())
								{
									System.out.println("Please construct the graph first by using graph keyword");
									break;
								}
							
							d.generatePath();
							for(int i =0;i< Dijkstra.finalpath.size();i++)
							{
								System.out.println(d.finalpath.get(i));
							}
							break;
			case "prims": if(graph.isEmpty())
							{
								System.out.println("Please construct the graph first by using graph keyword");
								break;
							}
						  Prims.findPath();
						  System.out.println("Minimum Spanning Tree : ");
						  for (int key : Prims.visitedmap.keySet()) {
								System.out.println(key + "->" + Prims.visitedmap.get(key));
							}
						  break;
			case "quit": return;
			case "print": if(graph.isEmpty())
							{
								System.out.println("Please construct the graph first by using graph keyword");
								break;
							}
							graphDisplay();
						  break;
			case "performance":if(graph.isEmpty())
								{
									System.out.println("Please construct the graph first by using graph keyword");
									break;
								} 
								long startTime=System.currentTimeMillis();
								d.generatePath();
								long endTime=System.currentTimeMillis();
								System.out.println("Time Taken in milli seconds for Dijkstra Algorithm: "+(endTime-startTime));
								long startTime2=System.currentTimeMillis();
								Prims.findPath();
								long endTime2=System.currentTimeMillis();
								System.out.println("Time Taken in milli seconds: "+(endTime2-startTime2));
			}
		}
	}
}
