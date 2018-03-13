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
 * Implementation of Prim's algorithm for computing
 * the minimum spanning tree of a graph.
 * 
 * @author Thomas VanDrunen
 * CSCI 345, Wheaton College
 * June 24, 2015
 */
public class PrimMinSpanTree implements MinSpanTree {

    /**
     * Compute the minimum spanning tree of a given graph.
     * @param g The given graph
     * @return A set of the edges in the minimum spanning tree
     */
    public Set<WeightedEdge> minSpanTree(WeightedGraph g) {
        Set<WeightedEdge> mstEdges = new BasicHashSet<WeightedEdge>(g.numVertices());
        VertexRecord[] records = new VertexRecord[g.numVertices()];
        for (int i = 0; i < g.numVertices(); i++)
            records[i] = new VertexRecord(i, Double.POSITIVE_INFINITY);
        PriorityQueue<VertexRecord> pq = 
                new HeapPriorityQueue<VertexRecord>(records, new VertexRecord.VRComparator());
        
        int[] parents = new int[g.numVertices()];
        for (int i = 0; i < g.numVertices(); i++)
            parents[i] = -1;
        
        records[0].setDistance(0);
        pq.increaseKey(records[0]);
        parents[0] = 0;
        
        // iterate through the vertices
        while(!pq.isEmpty()) {
        	VertexRecord vr = pq.extractMax();

        	int vertex = vr.id;


        	
        	for(Integer v: g.adjacents(vertex)) {
        		System.out.println("current vertex: " + vertex);
        		System.out.println("vertex: " + v + " has old parent: " + parents[v]);


        		if(v != vertex) {
        		double distOld = records[v].getDistance();
        		System.out.println("distOld for vertex (" + v + "):" + distOld);

        		double distNew = g.weight(vertex, v);
        		System.out.println("distNew for vertex (" + v + "):" + distNew);

        		
        		
        		
        		
        		if(distOld > distNew) {
        			records[v].setDistance(distNew);
        	        pq.increaseKey(records[v]);
        			parents[v] = vertex;
        		}
        		System.out.println("vertex: " + v + " has new parent: " + parents[v]);
        	}
        		
        	}
        	

        	WeightedEdge edge = new WeightedGraph.WeightedEdge(parents[vertex], vertex, records[vertex].getDistance(), true);

        	System.out.println("weight added: " + edge.weight);
        	System.out.println("__________________");

        	mstEdges.add(edge);
        	
        	
        }
        return mstEdges;
    }

}
