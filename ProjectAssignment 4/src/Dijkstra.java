import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

public class Dijkstra {

	public static PriorityQueue<Vertex> queue = new PriorityQueue<Vertex>(); 
	public static ArrayList<String> finalpath = new ArrayList<String>();
	HashMap<Integer,Vertex> VertexList = new HashMap<Integer,Vertex>();
	Dijkstra(HashMap<Integer,Vertex> VertexList)
	{
		this.VertexList=VertexList;
	}
	public void generatePath()
	{
		Vertex source;
		source = Graph.vertices.get(0);
		source.shortPathEstimate=0;
		source.visited=1;
		source.parentID=0;
		queue.add(source);
		while(!queue.isEmpty())
		{
			Vertex v;
			v=queue.poll();
			VertexList.get(v.id).visited=1;
			finalpath.add(v.Label+v.parentID+"-->");
			for(int i=0; i<Graph.graph.get(v.id).size(); i++)
			{
				Edge e;
				Vertex target;
				e=Graph.graph.get(v.id).get(i);
				if(v.id==e.sourceID)
				{
					target=VertexList.get(e.targetID);
				}
				else
				{
					target=VertexList.get(e.sourceID);
				}
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
