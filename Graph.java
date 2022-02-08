import java.util.*;
import java.util.Scanner;

/**
 *  Graph function that adds edges and construct edges 
 */
public class Graph {

    // number of nodes in graph
    private int numNodes;

    // adjacency list
    private ArrayList<LinkedList<Integer>> adjLists;

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
        this.adjLists = new ArrayList<LinkedList<Integer>>(numNodes);
        for(int i = 0; i < numNodes; i++)
        {
            adjLists.add(new LinkedList<Integer>());
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
    public ArrayList<LinkedList<Integer>> getAdjLists()
    {
        return adjLists;
    }

    /**
     * addEdges - add edges in adjLists
     * @param start
     * @param end
     */
    public void addEdges(int start, int end)
    {
        adjLists.get(start - 1).addLast(end);
        adjLists.get(end - 1).addLast(start);

    }
}

/**
 * BreadthFirstSearch class
 */
class BreadthFirstSearch{

    // constant
    final static int GRAPHVERTICES = 8;

    // graph object for getting graph from Graph
    public static Graph graph = new Graph(GRAPHVERTICES);

    // L for generation
    public static ArrayList<ArrayList<Integer>> L = new ArrayList<ArrayList<Integer>>(GRAPHVERTICES);

    // Discovered for checking if visited
    public static ArrayList<Boolean> Discovered = new ArrayList<Boolean>(GRAPHVERTICES);

    // T for constructed tree (store "(x,y)")
    public static ArrayList<String> T = new ArrayList<String>();
    public static void main(String[] args)
    {

        // prompt user to enter the start node
        int startingNodes = startInput();

        // draw edges and construct the graph
        graphInitiate();

        // call constructor
        new BreadthFirstSearch();

        // check if graph is okay
        System.out.println("check if everything is constructed well....");
        for(int i = 0; i < graph.getAdjLists().size(); i++)
        {
            for(int j = 0; j < graph.getAdjLists().get(i).size(); j ++)
            {
                System.out.print(graph.getAdjLists().get(i).get(j) + " ");
            }
            System.out.println();
        }
        System.out.println();

        // call function that draws DFS tree
        BFSfunction(startingNodes);
    }
    /**
     * graphInitiate - add edges
     */
    static void graphInitiate()
    {
        // since 1, 2 and 2, 1 is same, I add once, and the method above will process it
        graph.addEdges(1, 2);
        graph.addEdges(1, 3);
        graph.addEdges(2, 3);
        graph.addEdges(2, 4);
        graph.addEdges(2, 5);
        graph.addEdges(3, 5);
        graph.addEdges(3, 7);
        graph.addEdges(3, 8);
        graph.addEdges(4, 5);
        graph.addEdges(5, 6);
        graph.addEdges(7, 8);
    }
    /**
     * startInput - prompt users to enter their starting node
     * @return
     */
    static int startInput()
    {
        boolean inRange = false;
        Scanner keyboard = new Scanner(System.in);
        String getInput;
        int startingNodes = 0;

        // input validation
        while(inRange == false)
        {
            System.out.print("Which nodes will you be starting? : ");
            try{
                getInput = keyboard.nextLine();
                startingNodes = Integer.parseInt(getInput);
                if(startingNodes >= 1 && startingNodes <= 8)
                {
                    inRange = true;
                }
                else
                {
                    System.out.println("Please verify your input (range 1 - 8)");
                    startingNodes = 0;
                }
            }
            catch(Exception e)
            {
                System.out.println("Please verify your input (range 1 - 8)");
                startingNodes = 0;
            }
        }
        return startingNodes;
    }
    /**
     * BFSfunction: method that is based on the pseudocode in the textbook
     * @param s
     */
    static void BFSfunction(int s)
    {
        // Set Discovered[s] = true and Discovered[v] = false for all other v
        for(int z = 0; z < GRAPHVERTICES; z ++)
        {
            if(z == s - 1) Discovered.add(z, true);
            else Discovered.add(z, false);
        }
        // Initialize L[0] to consist of the single element
        L.add(new ArrayList<Integer>());
        L.get(0).add(s);
        System.out.printf("L0: %d \n", s);

        // set the layer counter i = 0
        int i = 0;

        // Set the current BFS tree T = 0
        T.clear();

        // while L[i] is not empty
        while(L.get(i).size() != 0)
        {
            // initialize an empty list L[i + 1]
            L.add(i + 1, new ArrayList<Integer>());
            System.out.printf("L%d: ", i + 1);
            // for each node u ( L[i]
            for(int u : L.get(i))
            {
                
                // consider each edge (u,v) incident to u
                for(int v: graph.getAdjLists().get(u - 1))
                {
                    // if Discovered[v] = false 
                    if(Discovered.get(v - 1) == false)
                    {
                        // then set discovered[v] = true
                        Discovered.set(v - 1, true);

                        // add edge (u ,v) to the tree T
                        T.add(String.format("(%d,%d)",u,v));

                        // add v to the list L[i + 1]
                        L.get(i + 1).add(v);
                        System.out.printf("%d ", v);
                    }
                }
            }
            // increment the layer counter i by one
            i += 1;
            System.out.println();
        }
        // print constructed BFS tree as a set of edges
        System.out.print(T);
    }
}
