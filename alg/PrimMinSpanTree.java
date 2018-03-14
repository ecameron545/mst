package alg;

import impl.BasicHashSet;
import impl.HeapPriorityQueue;
import adt.PriorityQueue;
import adt.Set;
import adt.WeightedGraph;
import adt.WeightedGraph.WeightedEdge;

/**
 * PrimMinSpanTree
 * 
 * Implementation of Prim's algorithm for computing the minimum spanning tree of
 * a graph.
 * 
 * @author Thomas VanDrunen CSCI 345, Wheaton College June 24, 2015
 */
public class PrimMinSpanTree implements MinSpanTree {

	/**
	 * Compute the minimum spanning tree of a given graph.
	 * 
	 * @param g
	 *            The given graph
	 * @return A set of the edges in the minimum spanning tree
	 */
	public Set<WeightedEdge> minSpanTree(WeightedGraph g) {
		Set<WeightedEdge> mstEdges = new BasicHashSet<WeightedEdge>(g.numVertices());
		VertexRecord[] records = new VertexRecord[g.numVertices()];
		for (int i = 0; i < g.numVertices(); i++)
			records[i] = new VertexRecord(i, Double.POSITIVE_INFINITY);
		PriorityQueue<VertexRecord> pq = new HeapPriorityQueue<VertexRecord>(records, new VertexRecord.VRComparator());

		int[] parents = new int[g.numVertices()];
		for (int i = 0; i < g.numVertices(); i++)
			parents[i] = -1;

		while (!pq.isEmpty()) {

			VertexRecord vr = pq.extractMax();

			// if the lowest weight vertex (vr) has a parent, add it to the MST
			if (parents[vr.id] != -1)
				mstEdges.add(new WeightedEdge(parents[vr.id], vr.id, g.weight(parents[vr.id], vr.id), true));

			// loop through the current vertex's adjacents
			for (int vertex : g.adjacents(vr.id)) {

				// record the weight of the current vertex (vr.id) to its
				// adjacent vertex (vertex)
				double weight = g.weight(vertex, vr.id);

				// if the priority queue contians the adjacent vertex (which
				// means it still needs
				// to be added to the MST, and the weight to get to that vertex
				// is less
				// then the distance previously held there, update it.
				if (pq.contains(records[vertex]) && weight < records[vertex].getDistance()) {
					parents[vertex] = vr.id;
					records[vertex].setDistance(weight);
					pq.increaseKey(records[vertex]);
				}

			}

		}

		return mstEdges;
	}

}
