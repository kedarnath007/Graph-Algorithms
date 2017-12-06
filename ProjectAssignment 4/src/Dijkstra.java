/****************************************************/
// Filename: Dijkstra.java
// Change history:
// 11.17.2017 / 
/****************************************************/
/* This class is responsible for performing Dijkstra algorithm on he given graph
/****************************************************/
import java.util.HashMap;
import java.util.PriorityQueue;

public class Dijkstra {
	//This queue is responsible to store vertices of the graph and set priority based on the shortest path estimate attribut
	public static PriorityQueue<Vertex> queue = new PriorityQueue<Vertex>();  
	public static HashMap<Integer,Vertex> VertexList = new HashMap<Integer,Vertex>();//This stores the vertex IDs is key and edge list in the value
	Dijkstra(HashMap<Integer,Vertex> VertexList)//This constructor will initialize with the vertex list
	{
		this.VertexList=VertexList;
	}
	//This function is responsible for generating the final path in Dijkstra algorithm
	public void generatePath()
	{
		Vertex source;
		source = Graph.vertices.get(0);//The first vertex in the graph is considered as the source node by default with id=0
		source.shortPathEstimate=0;//Setting the shortest path estimate d in the algorithm to 0 for source
		source.visited=1;
		source.parentID=0;
		queue.add(source);
		while(!queue.isEmpty())
		{
			Vertex v;
			v=queue.poll();//removes one vertex after another based on the shortestpathestimate attribute of the vertex
			VertexList.get(v.id).visited=1;
			//The below code is for checking all the associated edges of the source vertex and relaxing them
			for(int i=0; i<Graph.graph.get(v.id).size(); i++)
			{
				Edge e;
				Vertex target;
				e=Graph.graph.get(v.id).get(i);//selects each edge from the graph that is generated in Graph class for a specific vertex
				//The edge associated with a Vertex would contain sourceID and targetID and we need to check whether the Vertex is source or target of the edge
				if(v.id==e.sourceID)
				{
					target=VertexList.get(e.targetID);
				}
				else
				{
					target=VertexList.get(e.sourceID);
				}
				//If the vertex is already visited, that will not be taken into consideration
				if(target.visited!=1)
				{
					target=doRelax(v,target,e.weight);
					if(queue.contains(VertexList.get(target.id)))
					{
						if(VertexList.get(target.id).shortPathEstimate>target.shortPathEstimate)
						{
							queue.remove(VertexList.get(target.id));
							queue.add(target);
							VertexList.get(target.id).parentID=v.id;
						}
					}
					else
					{
						queue.add(target);
						target.parentID=v.id;
						VertexList.get(target.id).shortPathEstimate=target.shortPathEstimate;
						VertexList.get(target.id).parentID=target.parentID;
					}
					
				}
			}	
		}
	}
	//The below function relaxes the vertices passed.
	public static Vertex doRelax(Vertex u,Vertex v,int weight)
	{
		if(v.shortPathEstimate>u.shortPathEstimate+weight)
		{
			v.shortPathEstimate=u.shortPathEstimate+weight;
			v.parentID=u.id;
		}
		return v;
	}
}
