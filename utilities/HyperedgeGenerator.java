package hypergraph.utilities;

import hypergraph.Annotation;
import java.util.Random;
import java.util.ArrayList;

import hypergraph.Hyperedge;
import hypergraph.Hypergraph;

//
// Implements the singleton design pattern
//
public class HyperedgeGenerator<T, A>
{
    private Hypergraph _HG;
    
    public HyperedgeGenerator(Hypergraph theHypergraph)
    {
        _HG = theHypergraph;
    }
    
    public Hyperedge<A> createRandomEdge(Random gen, int numNodes, A annotation)
    {
        //to have random number of source nodes, put random generator as first parameter of genSubset
        //currently at size 3 subset of sources for simple testing
        Hyperedge<A> newEdge = new Hyperedge<A>(utilities.Utilities.genSubset(gen, 3, 0, numNodes), gen.nextInt(numNodes), annotation);
        
        boolean success = true;
        for(int source: newEdge.sourceNodes)
        {
            if(newEdge.targetNode == source)
            {
                success = false;
                break;
            }
        }
        
        if (!success) newEdge = createRandomEdge(gen, numNodes, annotation);
        
        return newEdge;
    }
    
    public void genBoundedHyperedges(int currNode, String sourceNumberOrder)
    {
        if(currNode == 0) return;
        
        int sourceBound = Character.getNumericValue(sourceNumberOrder.charAt(currNode - 1));
        
        if((currNode - sourceBound) < 0) return;
        
        int[] sources;
        
        sources = new int[sourceBound];
        int index = 0;
        for(int count = 1; count <= sourceBound; count++)
        {
            sources[index] = currNode - count;
            index++;
        }
        
        Annotation edgeAnnotation = new Annotation();
        
        Hyperedge boundedEdge = new Hyperedge(sources, currNode, edgeAnnotation);
        _HG.addEdge(boundedEdge);
        
        if(currNode == 1) return;
        genBoundedHyperedges(currNode - 1, sourceNumberOrder);
    }
//    
//    public void genBoundedHyperedges(int currNode, int sourceBound)
//    {
//        Random gen = new Random();
//        
//        int bound = gen.nextInt(sourceBound) + 1;
//        
//        if((currNode - bound) < 0 && currNode == 0) return;
//        
//        int[] sources;
//        
//        if((currNode - bound) < 0 && currNode != 0)
//        {
//            sources = new int[1];
//            sources[0] = currNode - 1;
//        }
//        else
//        {
//            sources = new int[bound];
//            int index = 0;
//            for(int count = 1; count <= bound; count++)
//            {
//                sources[index] = currNode - count;
//                index++;
//            }
//        }
//        
//        Annotation edgeAnnotation = new Annotation();
//        
//        Hyperedge boundedEdge = new Hyperedge(sources, currNode, edgeAnnotation);
//        _HG.addEdge(boundedEdge);
//        
//        genBoundedHyperedges(currNode - 1, sourceBound);
//    }
}