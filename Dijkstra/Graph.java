
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
    private int[] heaps;
    private int[] nodes;
    private int size;
    private int maxSize;
    
    private static final int FRONT = 1;
    
    public BinaryHeap(int maxSize) {
        this.maxSize = maxSize;
        this.size = 0;
        
        heaps = new int[this.maxSize + 1];
        nodes = new int[this.maxSize + 1];
    }
    private int getParent(int pos) {
        return pos / 2;
    }
    private int getLeftChild(int pos) {
        return pos * 2;
    }
    private int getRightChild(int pos) { 
        return (pos * 2) + 1;
    } 
    private boolean isLeaf(int pos) {
        if (pos > (size / 2) && pos <= size) {
            return true;
        }
        else return false;
    }
    private void swap(int firstPos, int secondPos) {
        int temp;
        temp = heaps[firstPos];
        heaps[firstPos] = heaps[secondPos];
        heaps[secondPos] = temp;

        temp = nodes[firstPos];
        nodes[firstPos] = nodes[secondPos];
        nodes[secondPos] = temp;
    }
    private void minHeap(int pos) {
        if (!isLeaf(pos)) {
            if (heaps[pos] > heaps[getLeftChild(pos)] || heaps[pos] > heaps[getRightChild(pos)]) {
                if (heaps[getLeftChild(pos)] < heaps[getRightChild(pos)]) {
                    swap(pos, getLeftChild(pos));
                    minHeap(getLeftChild(pos));
                }
                else {
                    swap(pos, getRightChild(pos));
                    minHeap(getRightChild(pos));
                }
            }
        }
    }
    public void insert(int element) {
        if (size >= maxSize) return;
        size ++;
        heaps[size] = element;
        nodes[size] = size;

        int current = size;

        while (heaps[current]  < heaps[getParent(current)]) { 
            swap(current, getParent(current));
            current = getParent(current);
        }
    }
    public int extractMin() {
        int popped = heaps[FRONT];
        int index = -1;
        heaps[FRONT] = heaps[size];
        for (int i = 0; i < maxSize; i ++) {
            if (nodes[i] == 1) {
                nodes[i] = -1;
                nodes[size] = 1;
                index = size;
            }
            System.out.println(nodes[i]);
        }
        size --;
        minHeap(FRONT);
        
        return index;
    }
    public void print()
    {
        for (int i = 1; i <= size / 2; i++) {
 
            // Printing the parent and both childrens
            System.out.print(
                " PARENT : " + heaps[i] 
                + " LEFT CHILD : " + heaps[2 * i] 
                + " RIGHT CHILD :" + heaps[2 * i + 1] );
 
            // By here new line is required
            System.out.println();
        }
    }
    public void changeKey(int pos, int weight) {
        heaps[nodes[pos]] = weight;
        minHeap(nodes[pos]);
    }
    public boolean isEmpty() {
        return size == 0;
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
        // Creating object opf class in main() methodn
        BinaryHeap Q = new BinaryHeap(GRAPHVERTICES + 1);
        int[] pi = new int[GRAPHVERTICES + 1];
        final int MAXINFIN = 1000000;
        Vector<Integer> S = new Vector<Integer>();
        
        int v = 0;
        int weight = 0;

        for(int i = 0; i < GRAPHVERTICES; i ++) {
            Q.insert(MAXINFIN);
            pi[i] = MAXINFIN;
        }

        S.clear();
        pi[0] = 0;
        Q.changeKey(startNode, 0);
        while(!Q.isEmpty()) {
            Q.print(); 
            weight = Q.extractMin();
            for (int i = 0; i < GRAPHVERTICES; i ++) {
                if(pi[i] == weight && S.indexOf(i + 1) == -1) {
                    v = i + 1;
                }
            }
            S.add(v);
            System.out.printf("Node %d included in S with the shortest path length x on the path ", S.lastElement());
            for (int i = 0; i < S.size() - 1; i ++) { 
                System.out.printf("%d -", S.get(i));
            }
            System.out.println(S.lastElement());
            for (Vector<Integer> e : graph.getAdjLists().get(v - 1)) {
                System.out.println(e);
                if (S.indexOf(e.get(0)) == -1) {
                    if (pi[v - 1] + e.get(1) < pi[e.get(0) - 1]) {
                        pi[e.get(0) - 1] = pi[v - 1] + e.get(1);
                        Q.changeKey(e.get(0), pi[e.get(0) - 1]);
                    }
                }
            }
        }
    }
}