/****************************************************/
// Filename: Vertex.java
// Change history:
// 11.17.2017
/****************************************************/

/* This class represents a vertex in the graph
/****************************************************/
public class Vertex  implements Comparable<Vertex>{

	String Label;//Label of the corresponding Vertex in the graph
	int id;//Id of the Vertex 
	int shortPathEstimate;//This attribute is used for Dijkstra's algorithm where shortest path estimate is used
	int visited;//This attribute will mark the vertex as visited if the value is 1
	int parentID;
	Vertex(String Label, int id,int shortPathEstimate,int visited)
	{
		this.Label=Label;
		this.id = id;
		this.shortPathEstimate=shortPathEstimate;
		this.visited=visited;
	}
	//The below override function will set priority for the queue used in Dijkstra Algorithm.
	@Override
	public int compareTo(Vertex v) {
		return this.shortPathEstimate-v.shortPathEstimate;
	}
}
