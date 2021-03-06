package mst.random;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import mst.bean.Edge;
import mst.bean.Vertex;
import mst.bean.WeightedGraph;
import mst.manager.NeighborsManager;

public class ErdosRenyiGraphGenerator {
	
	public WeightedGraph generateErdosRenyiGraph(int n, float p) {
		final Vertex[] vertexes = new Vertex[n];
		for (int i = 0 ; i < n ; i++) {
			vertexes[i] = new Vertex((i + 1) + "");
		}
		final int threshold = (int)Math.pow(n, 4) + 1;
		final List<Edge> edges = new LinkedList<Edge>();
		final Random random = new Random();
		for (int i = 0 ; i < n ; i++) {
			for (int j = 0 ; j < n ; j++) {
				final Vertex vertex1 = vertexes[i];
				final Vertex vertex2 = vertexes[j];
				final float edgeProba = random.nextFloat();
				final int randomWeight = random.nextInt(threshold);
				final boolean condition1 = !vertex1.equals(vertex2);
				final boolean condition2 = !edges.contains(new Edge(vertex1, vertex2, randomWeight));
				final boolean condition3 = edgeProba < p;
				if (condition1 && condition2 && condition3)
					edges.add(new Edge(vertexes[i], vertexes[j], randomWeight));
			}
		}
		final Set<Vertex> setVertexes = new HashSet<Vertex>(Arrays.asList(vertexes)); 
		final WeightedGraph graph = new WeightedGraph(setVertexes, edges);
//		final NeighborsManager neighborsManager = new NeighborsManager();
//		neighborsManager.initNeighbors(graph);
//		for (int i = 0 ; i < vertexes.length ; i++) {
//			if (neighborsManager.getNeighbors(vertexes[i]).size() == 0) {
//				final Vertex currentVertex = vertexes[i];
//				final Vertex nextVertex = vertexes[(i + 1) % vertexes.length];
//				graph.addEdge(currentVertex, nextVertex, random.nextInt(threshold));
//				neighborsManager.addNeighbors(vertexes[i], vertexes[(i + 1) % vertexes.length]);
//			}
//		}
		return graph;
	}

}
