
/**
 * Jay Hwasung Jung
 * CS 224
 * programming homework 01 - BFS
 */
import java.util.*;

/**
 *  Graph function that adds edges and construct edges 
 */
class Graph {

    // number of nodes in graph
    private int numNodes;

    // adjacency list
    private ArrayList<LinkedList<Vector<Integer>>> adjLists;

    // Source: https://www.programiz.com/dsa/graph-adjacency-list
    /**
     * Default Constructor - constructing linked list
     * it will eventually construct adjacency list
     * @param numNodes
     */
    Graph(int numNodes)
    {
        this.numNodes = numNodes;

        // initialization
        this.adjLists = new ArrayList<LinkedList<Vector<Integer>>>(numNodes);
        for(int i = 0; i < numNodes; i++)
        {
            adjLists.add(new LinkedList<Vector<Integer>>());
        }
    }

    /**
     * getNumNodes - getter for numNodes
     * @return
     */
    public int getNumNodes()
    {
        return numNodes;
    }

    /**
     * getAdjLists - getter for adjLists
     * @return
     */
    public ArrayList<LinkedList<Vector<Integer>>> getAdjLists()
    {
        return adjLists;
    }

    /**
     * addEdges - add edges in adjLists
     * @param start
     * @param end
     */
    public void addEdges(int start, int end, int weight)
    {
        Vector<Integer> nodeKey = new Vector<Integer>();
        nodeKey.add(end);
        nodeKey.add(weight);
        adjLists.get(start - 1).addLast(nodeKey);
    }
}
class BinaryHeap {
    private int[] heaps;
    private int size;
    private int maxSize;
    
    private static final int FRONT = 1;
    
    public BinaryHeap(int maxSize) {
        this.maxSize = maxSize;
        this.size = 0;
        
        heaps = new int[this.maxSize + 1];
        
    }

}

class Dijkstra {
    public static final int GRAPHVERTICES = 8;

    public static void main(String[] args) {
        Graph graph = new Graph(GRAPHVERTICES);
        graphInitiate(graph);
        
        System.out.println("Checking if all nodes and weights are correctly connected");
        System.out.println("Graphs is represented using an adjacency list.");
        for(int i = 0; i < GRAPHVERTICES; i ++) {
            System.out.println(i + 1);
            System.out.println(graph.getAdjLists().get(i));
        }
        dijkstraAlgorithm(graph, 1);
    }
    /**
     * graphInitiate - add edges
     */
    static void graphInitiate(Graph graph)
    {
        graph.addEdges(1, 2, 9);
        graph.addEdges(1, 4, 14);
        graph.addEdges(1, 3, 15);
        graph.addEdges(2, 7, 23);
        graph.addEdges(4, 7, 18);
        graph.addEdges(4, 5, 30);
        graph.addEdges(4, 3, 5);
        graph.addEdges(3, 5, 20);
        graph.addEdges(3, 8, 44);
        graph.addEdges(5, 6, 11);
        graph.addEdges(5, 8, 11);
        graph.addEdges(6, 7, 6);
        graph.addEdges(6, 8, 6);
        graph.addEdges(7, 5, 2);
        graph.addEdges(7, 8, 19);
    }
    static void dijkstraAlgorithm(Graph graph, int startNode){
        
    }
    
}