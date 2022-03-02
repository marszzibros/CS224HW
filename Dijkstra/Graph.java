
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
    private Vector<Vector<Integer>> heaps;
    private int size;
    private int maxSize;
    
    private static final int FRONT = 1;
    
    public BinaryHeap(int maxSize) {
        this.maxSize = maxSize;
        this.size = 0;
        
        heaps = new Vector<Vector<Integer>>();
        Vector<Integer> temp = new Vector<Integer>(maxSize + 1);
        temp.add(0);
        temp.add(0);
        heaps.add(temp);
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
    
        Vector<Integer> temp = new Vector<Integer>();
        temp.add(heaps.get(firstPos).get(0));
        temp.add(heaps.get(firstPos).get(1));
        heaps.set(firstPos,heaps.get(secondPos));
        heaps.set(secondPos, temp);
    }
    private void minHeap(int pos) {
        if (!isLeaf(pos) && (heaps.get(pos).get(0) > heaps.get(getLeftChild(pos)).get(0) 
        || heaps.get(pos).get(0) > heaps.get(getRightChild(pos)).get(0))) {
            if (heaps.get(getLeftChild(pos)).get(0) < heaps.get(getRightChild(pos)).get(0)) {
                swap(pos, getLeftChild(pos));
                minHeap(getLeftChild(pos));
            }
            else {
                swap(pos, getRightChild(pos));
                minHeap(getRightChild(pos));
            }
        }
        else if(heaps.get(pos).get(0) < heaps.get(getParent(pos)).get(0)) {
            swap(pos, getParent(pos));
            minHeap(getParent(pos));
            minHeap(pos);
        }
    }
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
    public int extractMin() {
        int popped = heaps.get(FRONT).get(1);
        heaps.set(FRONT, heaps.get(size));
        size --;
        minHeap(FRONT);
        
        return popped;
    }
    public void print()
    {
        for (int i = 1; i <= size / 2; i++) {
 
            // Printing the parent and both childrens
            System.out.print(
                " PARENT : " + heaps.get(i).get(0) 
                + " LEFT CHILD : " + heaps.get(2 * i).get(0));
            if (2 * i + 1 < heaps.size()) {               
                System.out.print(" RIGHT CHILD :" + heaps.get(2 * i + 1).get(0));
            }
            // By here new line is required
            System.out.println();
        }
    }
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
        graph.addEdges(5, 8, 16);
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
        Vector<Vector<Integer>> s = new Vector<Vector<Integer>>();
        for (int i = 0;i < GRAPHVERTICES; i++) {
            s.add(new Vector<Integer>());
        }

        
        int v = 0;

        for(int i = 0; i < GRAPHVERTICES; i ++) {
            Q.insert(MAXINFIN);
            pi[i] = MAXINFIN;
        }
        Vector<Integer> temp = new Vector<Integer>();

        temp.add(startNode);
        for (int i = 0;i < GRAPHVERTICES; i++) {
            s.set(i,new Vector<Integer>(temp));
        }
        S.clear();
        pi[0] = 0;
        Q.changeKey(startNode, 0);
        
        while(!Q.isEmpty()) {
            v = Q.extractMin();
            
            S.add(v);
            System.out.printf("Node %d included in S with the shortest path length %d on the path ", S.lastElement(), pi[v - 1]);
            for (int i = 0; i < s.get(v - 1).size() - 1; i ++) { 
                System.out.printf("%d - ", s.get(v - 1).get(i));
            }
            System.out.println(v);
            for (Vector<Integer> e : graph.getAdjLists().get(v - 1)) {
                if (S.indexOf(e.get(0)) == -1) {
                    if (pi[v - 1] + e.get(1) < pi[e.get(0) - 1]) {
                        s.set(e.get(0) - 1, new Vector<Integer>(s.get(v - 1)));
                        s.get(e.get(0) - 1).add(e.get(0));
                        pi[e.get(0) - 1] = pi[v - 1] + e.get(1);
                        Q.changeKey(e.get(0), pi[e.get(0) - 1]);
                    }
                }
            }
        }
        for (int i = 0; i < GRAPHVERTICES; i ++) {
            System.out.print(pi[i] + " ");
        }
    }
}