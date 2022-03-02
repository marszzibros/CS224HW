
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
    // https://www.geeksforgeeks.org/min-heap-in-java/

    // heaps vectors; [weight, nodes]
    private Vector<Vector<Integer>> heaps;

    // heaps size, since I did not popped the useless tree
    // by implementing this, it will automatically set the boundries
    private int size;

    // max size given by usesrs
    private int maxSize;
    
    // when min heap...
    private static final int FRONT = 1;
    
    /**
     * constructors
     * @param maxSize
     */
    public BinaryHeap(int maxSize) {
        this.maxSize = maxSize;
        this.size = 0;
        
        heaps = new Vector<Vector<Integer>>();
        Vector<Integer> temp = new Vector<Integer>(maxSize + 1);
        temp.add(0);
        temp.add(0);
        heaps.add(temp);
    }
    /**
     * get parent methods
     * @param pos
     * @return parent position in the heap
     */
    private int getParent(int pos) {
        return pos / 2;
    }
    /**
     * get left child of the heap
     * @param pos
     * @return left child
     */
    private int getLeftChild(int pos) {
        return pos * 2;
    }
    /**
     * get right child of the heap
     * @param pos
     * @return right child
     */
    private int getRightChild(int pos) { 
        return (pos * 2) + 1;
    } 
    /**
     * check if it is a leaf.
     * @param pos
     * @return
     */
    private boolean isLeaf(int pos) {
        if (pos > (size / 2) && pos <= size) {
            return true;
        }
        else return false;
    }
    /**
     * swaping when min heap
     * @param firstPos
     * @param secondPos
     */
    private void swap(int firstPos, int secondPos) {
    
        Vector<Integer> temp = new Vector<Integer>();
        temp.add(heaps.get(firstPos).get(0));
        temp.add(heaps.get(firstPos).get(1));
        heaps.set(firstPos,heaps.get(secondPos));
        heaps.set(secondPos, temp);
    }
    /**
     * conduct min heap using recursion
     * @param pos
     */
    private void minHeap(int pos) {
        //if it is not a leaf, conduct heap down
        if (!isLeaf(pos) && (heaps.get(pos).get(0) > heaps.get(getLeftChild(pos)).get(0) 
        || heaps.get(pos).get(0) > heaps.get(getRightChild(pos)).get(0))) {
            // left child < right child, then change left child with current nodes
            if (heaps.get(getLeftChild(pos)).get(0) < heaps.get(getRightChild(pos)).get(0)) {
                swap(pos, getLeftChild(pos));
                minHeap(getLeftChild(pos));
            }
            // left child > right child, then change left child with current nodes
            else {
                swap(pos, getRightChild(pos));
                minHeap(getRightChild(pos));
            }
        }
       
        // if it is a leaf, conduct heap up
        // get parents 
        else if(heaps.get(pos).get(0) < heaps.get(getParent(pos)).get(0)) {
            swap(pos, getParent(pos));
            minHeap(getParent(pos));
            minHeap(pos);
        }
    }
    /**
     * insert function when initialize
     * @param element
     */
    public void insert(int element) {
        if (size >= maxSize) return;
        Vector<Integer> temp = new Vector<Integer>();
        temp.add(element);
        temp.add(size + 1);
        size ++;
        heaps.add(temp);

        int current = size;

        while (heaps.get(current).get(0) < heaps.get(getParent(current)).get(0)) { 
            swap(current, getParent(current));
            current = getParent(current);
        }
    }
    /**
     * extract Min function that gets minimum value of the binary heap
     * @return
     */
    public int extractMin() {
        int popped = heaps.get(FRONT).get(1);
        heaps.set(FRONT, heaps.get(size));
        size --;
        minHeap(FRONT);
        
        return popped;
    }
    /**
     * changing key, and see if binary heap is still valid
     * @param pos
     * @param weight
     */
    public void changeKey(int pos, int weight) {
        int index = -1;
        for (int i = 1; i <= size; i ++) {
            if (pos == heaps.get(i).get(1)) {
                index = i;
            }
        }
        
        heaps.get(index).set(0, weight);
        minHeap(index);
    }
    /**
     * check is heap is empty
     */ 
    public boolean isEmpty() {
        return size == 0;
    }
}
/**
 * Dijkstra algorithm based on the pseudocode
 */
class Dijkstra {
    public static final int GRAPHVERTICES = 8;

    public static void main(String[] args) {
        Graph graph = new Graph(GRAPHVERTICES);
        graphInitiate(graph);
        
        System.out.println("Checking if all nodes and weights are correctly connected");

        // adjacency list 
        System.out.println("Graphs is represented using an adjacency list.");

        // checking adjacency list
        for(int i = 0; i < GRAPHVERTICES; i ++) {
            System.out.println(i + 1);
            System.out.println(graph.getAdjLists().get(i));
        }

        // call dijkstra algorithm
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
        graph.addEdges(5, 8, 16);
        graph.addEdges(6, 7, 6);
        graph.addEdges(6, 8, 6);
        graph.addEdges(7, 5, 2);
        graph.addEdges(7, 8, 19);
        
    }
    /**
     * dijkstra algorithm
     * @param graph
     * @param startNode
     */
    static void dijkstraAlgorithm(Graph graph, int startNode){
        // Creating object opf class in main() methodn
        BinaryHeap Q = new BinaryHeap(GRAPHVERTICES + 1);

        // creating pi
        int[] pi = new int[GRAPHVERTICES + 1];

        // max weight (infinity)
        final int MAXINFIN = 1000000;
        
        // S declaration
        Vector<Integer> S = new Vector<Integer>();

        // adding path for each nodes
        Vector<Vector<Integer>> s = new Vector<Vector<Integer>>();

        // initialization of s
        for (int i = 0;i < GRAPHVERTICES; i++) {
            s.add(new Vector<Integer>());
        }
        
        int v = 0;

        // set Q infinity
        for(int i = 0; i < GRAPHVERTICES; i ++) {
            Q.insert(MAXINFIN);
            pi[i] = MAXINFIN;
        }
        Vector<Integer> temp = new Vector<Integer>();

        // set all path to be started with s
        temp.add(startNode);
        for (int i = 0;i < GRAPHVERTICES; i++) {
            s.set(i,new Vector<Integer>(temp));
        }
        S.clear();
        pi[0] = 0;

        // changekey s infinity to 0
        Q.changeKey(startNode, 0);
        
        // dijkstra algorithm
        while(!Q.isEmpty()) {
            // extracting min from binary heap
            v = Q.extractMin();
            
            // adding to S
            S.add(v);

            // printing out the path with weight
            System.out.printf("Node %d included in S with the shortest path length %d on the path ", S.lastElement(), pi[v - 1]);
            for (int i = 0; i < s.get(v - 1).size() - 1; i ++) { 
                System.out.printf("%d - ", s.get(v - 1).get(i));
            }
            System.out.println(v);

            // retrieve all the nodes connected to node v 
            for (Vector<Integer> e : graph.getAdjLists().get(v - 1)) {

                // if v is not added to S 
                if (S.indexOf(e.get(0)) == -1) {

                    // compare pi(w) + l < pi(v)
                    if (pi[v - 1] + e.get(1) < pi[e.get(0) - 1]) {
                        // add to the path s
                        s.set(e.get(0) - 1, new Vector<Integer>(s.get(v - 1)));
                        s.get(e.get(0) - 1).add(e.get(0));

                        // change pi
                        pi[e.get(0) - 1] = pi[v - 1] + e.get(1);

                        // change key
                        Q.changeKey(e.get(0), pi[e.get(0) - 1]);
                    }
                }
            }
        }
        // printing weights at last
        for (int i = 0; i < GRAPHVERTICES; i ++) {
            System.out.print(pi[i] + " ");
        }
    }
}