/****************************************************/
// Filename: Edge.java
// Change history:
// 11.17.2017 
/****************************************************/
/* This class represents the edge of the graph
/****************************************************/
public class Edge {

	public int sourceID;
	public int targetID;
	public int weight;
	Edge(int sourceID,int targetID,int weight)
	{
		this.sourceID=sourceID;
		this.targetID=targetID;
		this.weight = weight;
	}
}
