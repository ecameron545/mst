package alg;

import impl.BasicHashSet;
import impl.OptimizedHeapPriorityQueue;
import adt.PriorityQueue;
import adt.Set;
import adt.WeightedGraph;
import adt.WeightedGraph.WeightedEdge;

/**
 * OptimizedPrimMinSpanTree
 * 
 * Implementation of Prim's algorithm for computing
 * the minimum spanning tree of a graph, using a
 * more heavily optimized priority queue.
 * 
 * @author Thomas VanDrunen
 * CSCI 345, Wheaton College
 * June 25, 2015
 */
public class OptimizedPrimMinSpanTree implements MinSpanTree {


    public Set<WeightedEdge> minSpanTree(WeightedGraph g) {
        HPAVertexRecord[] records = new HPAVertexRecord[g.numVertices()];
        for (int i = 0; i < g.numVertices(); i++)
            records[i] = new HPAVertexRecord(i, Double.POSITIVE_INFINITY);
        PriorityQueue<HPAVertexRecord> pq = 
                new OptimizedHeapPriorityQueue<HPAVertexRecord>(records, new HPAVertexRecord.VRComparator());
        Set<WeightedEdge> mstEdges = new BasicHashSet<WeightedEdge>(g.numVertices());
        int[] parents = new int[g.numVertices()];
        for (int i = 0; i < g.numVertices(); i++)
            parents[i] = -1;
        
        while (!pq.isEmpty()) {

			HPAVertexRecord vr = pq.extractMax();

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
