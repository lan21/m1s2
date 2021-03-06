package mst.manager;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import mst.bean.Edge;
import mst.bean.Vertex;
import mst.bean.WeightedGraph;
import mst.sort.EdgesComparator;

public class NeighborsManager {

	private Map<Vertex, List<Vertex>> vertexesToNeighbors;
	private Queue<Edge> graphEdges;
	private List<Edge> visitedEdges;

	public NeighborsManager() {
		vertexesToNeighbors = new HashMap<Vertex, List<Vertex>>();
	}

	public void addNeighbors(Vertex vertex1, Vertex vertex2) {
		List<Vertex> vertex1Neighbors = vertexesToNeighbors.get(vertex1);
		if (vertex1Neighbors == null) {
			vertex1Neighbors = new LinkedList<Vertex>();
		}
		vertex1Neighbors.add(vertex2);
		vertexesToNeighbors.put(vertex1, vertex1Neighbors);
	}

	public List<Vertex> getNeighbors(Vertex vertex) {
		final List<Vertex> neighbors = vertexesToNeighbors.get(vertex);
		if (neighbors == null) {
			return new LinkedList<Vertex>();
		}
		return neighbors;
	}

	public void addNeighbors(Edge edge) {
		addNeighbors(edge.getVertex1(), edge.getVertex2());
		addNeighbors(edge.getVertex2(), edge.getVertex1());
	}

	public void initNeighbors(WeightedGraph graph) {
		vertexesToNeighbors.clear();
		graphEdges = new PriorityQueue<Edge>(graph.getSize(),
				new EdgesComparator());
		visitedEdges = new LinkedList<Edge>();
		final Queue<Edge> graphEdges = new PriorityQueue<Edge>(graph.getSize(),
				new EdgesComparator());
		graphEdges.addAll(graph.getEdges());
		while (!graphEdges.isEmpty()) {
			final Edge poll = graphEdges.poll();
			addNeighbors(poll);
		}
	}

	public Edge getWeakerOutgoingEdge(WeightedGraph subGraph,
			WeightedGraph graph, Vertex lastAddedVertex) {
		for (final Vertex neighbor : getNeighbors(lastAddedVertex)) {
			final Edge edge = graph.getEdge(lastAddedVertex, neighbor);
			if (!subGraph.containsEdge(edge) && !graphEdges.contains(edge) && !subGraph.containsVertexes(edge)) {
				graphEdges.add(edge);
			}
		}
		final Edge weakerEdge = graphEdges.poll();
		return weakerEdge;
	}

	public void removeNeighbors(WeightedGraph mst, Vertex lastAddedVertex) {
		for (final Vertex neighbor : mst.getVertexes()) {
			if (graphEdges.contains(new Edge(lastAddedVertex, neighbor, 0))) {
				graphEdges.remove(new Edge(lastAddedVertex, neighbor, 0));
			}
		}
	}

}