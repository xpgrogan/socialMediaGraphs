import java.io.*;
import java.util.*;
 
public class Graph 
{
    Scanner scan;
    int isDirected, isWeighted, nodes, edges, n1, n2, value;
    String line;
    int[][] graphMatrix;
    int count, following, followedBy;
    double popularity;
    int DFSCount, BFSCount;
    int[] V, B;
    Queue<Integer> Q = new LinkedList<Integer>();
     
    public void readGraph(String filename) throws FileNotFoundException
    {
        //This section reads the first line of the file and detects if it is directed or not
    	//Then reads the next line to determine nodes and edges
    	long start = System.currentTimeMillis();
    	scan = new Scanner(new File(filename));
        line = scan.nextLine();
        String[] str = line.split(" ");
        isDirected = Integer.parseInt(str[0]);
        isWeighted = Integer.parseInt(str[1]);
        System.out.println("isDirected: " + isDirected + "  isWeighted: " + isWeighted);
        line = scan.nextLine();
        str = line.split(" ");
        nodes = Integer.parseInt(str[0]);
        edges = Integer.parseInt(str[1]);
        System.out.println("nodes: " + nodes + "  edges: " + edges);
        graphMatrix = new int[nodes][nodes];
         
        //If the graph is directed, then we split into reading a directed graph
        if(isDirected == 1) //twitterMatrix
        {
            //Populates the matrix
        	for(int m = 0; m < edges; m++)
            {
                line = scan.nextLine();
                str = line.split(" ");
                n1 = Integer.parseInt(str[0]);
                n2 = Integer.parseInt(str[1]);
                graphMatrix[n1][n2] = 1;
                for(int i = 0; i < nodes; i++)
                {
                    for(int j = 0; j < nodes; j++)
                    {
                        if(graphMatrix[i][j] != 1)
                            graphMatrix[i][j] = 0;
                    }
                }
            }
            System.out.println();
            //DFS and BFS the graph
            //DFS
            DFSCount = 0;
            V = new int[nodes];
            System.out.print("DFS: ");
            for(int g = 0; g < nodes; g++)
            {
                V[g] = 0;
            }
            for(int h = 0; h < nodes; h++)
            {
                if(V[h] == 0)
                {
                	dfs(h);
                }
            }
            System.out.println();
            //BFS
            BFSCount = 0;
            B = new int[nodes];
            System.out.print("BFS: ");
            for(int g = 0; g < nodes; g++)
            {
                B[g] = 0;
            }
            bfs(0);
             
            System.out.println();
            //This section of the code works through the information in the matrix and pulls out the specific answers
            count = 0;
            while(count < nodes)
            {
                following = 0;
                followedBy = 0;
                for(int x = 0; x < nodes; x++)
                {
                    followedBy = followedBy + graphMatrix[x][count];
                }
                for(int y = 0; y < nodes; y++)
                {
                    following = following + graphMatrix[count][y];
                }
                if(following == 0)
                {
                    if(followedBy >= 3)
                        System.out.println("Person " + count + " IS popular.  Followed by: " + followedBy + "   Follows: " + following);
                    else
                        System.out.println("Person " + count + " is NOT popular.  Followed by: " + followedBy + "   Follows: " + following);
                }
                else
                {
                    popularity = followedBy / following;
                    if(popularity >= 2)
                        System.out.println("Person " + count + " IS popular.  Popularity Score: " + popularity + ".  Followed by: " + followedBy + "   Follows: " + following);
                    else
                        System.out.println("Person " + count + " is NOT popular.  Popularity Score: " + popularity + ".   Followed by: " + followedBy + "   Follows: " + following);
                }
                count++;
            }
        }
        //If the graph is not directed, then we split into reading a non-directed graph
        else //facebookMatrix
        {
            //Populating the matrix
        	for(int m = 0; m < edges; m++)
            {
                line = scan.nextLine();
                str = line.split(" ");
                n1 = Integer.parseInt(str[0]);
                n2 = Integer.parseInt(str[1]);
                value = Integer.parseInt(str[2]);
                graphMatrix[n1][n2] = graphMatrix[n2][n1] = value;
            }
            System.out.println();
            //DFS and BFS the graph
            //DFS
            DFSCount = 0;
            V = new int[nodes];
            System.out.print("DFS: ");
            for(int g = 0; g < nodes; g++)
            {
                V[g] = 0;
            }
            for(int h = 0; h < nodes; h++)
            {
                if(V[h] == 0)
                {
                	dfs(h);
                }
            }
            System.out.println();
            //BFS
            BFSCount = 0;
            B = new int[nodes];
            System.out.print("BFS: ");
            for(int g = 0; g < nodes; g++)
            {
                B[g] = 0;
            }
            for(int h = 0; h < nodes; h++)
            {
                if(B[h] == 0)
                {
                	bfs(h);
                }
            }
             
            //This section of the code works through the matrix and pulls out the specific information
            System.out.println();
            count = 0;
            while(count < nodes)
            {
                int friends[] = new int[nodes];
                int friendCount;
                int oldestFriend[] = new int[nodes];
                int max = 0;
                int[] maxHolder = new int[nodes];
                int[] fOFriends = new int[nodes];
                 
                friendCount = 0;
                for(int y = 0; y < nodes; y++)
                {
                    if(graphMatrix[count][y] > 0)
                    {
                        friendCount += 1;
                        if(graphMatrix[count][y] > max)
                        {
                            max = graphMatrix[count][y];
                            oldestFriend[count] = y;
                            maxHolder[count] = max;
                        }
                    }
                    friends[count] = friendCount;
                }
                int[] fofList = new int[nodes];
                for(int i = 0; i < nodes; i++)
                {
                    fofList[i] = 0;
                }
                for(int c = 0; c < nodes; c++)
                {
                    if(graphMatrix[count][c] > 0)
                    {
                        for(int d = 0; d < nodes; d++)
                        {
                            if(graphMatrix[c][d] > 0)
                            {
                                fofList[d] = 1;
                            }
                        }
                    }
                }
                int fof = 0;
                for(int e = 0; e < nodes; e++)
                {
                    if(fofList[e] == 1)
                    {
                        fof = fof + 1;
                    }
                }
                fOFriends[count] = fof - 1;
                System.out.println("Person " + count + " has " + friends[count] + " friends and " + fOFriends[count] + " FoFs,  oldest friend is " + oldestFriend[count] + " (" + maxHolder[count] + " days)");
                count++;
            }
        }
        long end = System.currentTimeMillis();
		long fin = end - start;
		System.out.println("\nTime in milliseconds for program to run : " + fin);
    }
    //dfs and bfs methods
    public void dfs(int v)
    {
        DFSCount = DFSCount + 1;
        if(DFSCount == 1)
        {
        	System.out.print(v);
        }
        else
        {
        	System.out.print(", " + v);
        }
        V[v] = DFSCount;
        for(int i = 0; i < nodes; i++)
        {
            if(graphMatrix[v][i] >= 1)
            {
                if(V[i] == 0)
                {
                    dfs(i);
                }
            }
        }
    }
    
    public void bfs(int b)
    {
    	int test = b;
    	BFSCount = BFSCount + 1;
    	if(BFSCount == 1)
    	{
    		System.out.print(test);
    	}
    	else
    	{
    		System.out.print(", " + test);
    	}
    	B[test] = BFSCount;
    	Q.add(test);
    	while(!Q.isEmpty())
    	{
    		test = Q.remove();
    		for(int i = 0; i < nodes; i++)
    		{
    			if(graphMatrix[test][i] >= 1)
    			{
    				if(B[i] == 0)
    				{
    					BFSCount = BFSCount + 1;
    					System.out.print(", " + i);
    					B[i] = BFSCount;
    					Q.add(i);
    				}
    			}
    		}
    	}
    }
}
