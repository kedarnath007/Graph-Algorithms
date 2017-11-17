
public class Vertex  implements Comparable<Vertex>{

	String Label;
	int id;
	int shortPathEstimate;
	int visited;
	int parentID;
	Vertex(String Label, int id,int shortPathEstimate,int visited)
	{
		this.Label=Label;
		this.id = id;
		this.shortPathEstimate=shortPathEstimate;
		this.visited=visited;
	}
	@Override
	public int compareTo(Vertex v) {
		return this.shortPathEstimate-v.shortPathEstimate;
	}
}
