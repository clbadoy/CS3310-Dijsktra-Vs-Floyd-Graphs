/**
 * Christian Badoy
 * CS 3310
 * Professor Huong
 * 20 May 2022
 * 
 * Project 2: Djikstra vs Floyd
 * The purpose of this project is to code and visualize the runtime of two shortest paths algorithms on a dense graph and
 * sparse graphs:
 * 1. Dijkstra's Algorithm
 * 2. Floyd's Algorithm
 * 
 * This file is the Graph file to hold the method calls for conducting Algorithm Calculation and Experimenting.
 */

import java.util.Random;

public class Graph 
{
	private int[][] graph;
	private int[] dist;
	private int[] prev;
	private int[] mark;
	private int root;
	private int minDistInd;
	private Random rand;


	// Initialize a Graph with a given root.
	public Graph(int[][] gr, int roo)
	{
		int[][] matOne = new int[gr.length][gr[0].length];
		int[] arrTwo = new int[gr.length];
		int[] arrThree = new int[gr.length];
		int[] arrFour = new int[gr.length];
		
		// Traverse through each matrix slot in adjacency matrix and insert a random number.
		for(int ro = 0; ro < matOne.length ; ro++)
			for(int co = 0; co < matOne[ro].length ; co++)
				{
					matOne[ro][co] = gr[ro][co]; // -1 to 100
				}
	
		root = roo;
		minDistInd = -1;
		graph = matOne;
		dist = arrTwo;
		prev = arrThree;
		mark = arrFour;
		rand = new Random();
	}
	// Initialize a Graph with "n" size for the graph.
	public Graph(int size)
	{
		int[][] matOne = new int[size][size];
		int[] arrTwo = new int[size];
		int[] arrThree = new int[size];
		int[] arrFour = new int[size];
		
		root = 0;
		minDistInd = -1;
		graph = matOne;
		dist = arrTwo;
		prev = arrThree;
		mark = arrFour;
		rand = new Random();
	}

	// Generates a dense graph using Adjacency Matrix with values ranging from 1 to 100 and a back-slash diagnal with the values of 0.
	public void generateDense()
	{
		for(int ro = 0; ro < graph.length ; ro++)
			for(int co = 0; co < graph[ro].length ; co++)
				{
					graph[ro][co] = (int) (rand.nextInt(100) + 1); // 
				}
		for(int i = 0; i < graph.length; i++)
		{
			int j = i;
			graph[i][j] = 0;
		}
	}


	// Generates a sparse graph with Adjacency Matrix. May potentially contain disconnected graphs as I am unsure on how to implement a proper sparse graph.
	public void generateSparse()
	{
		for(int ro = 0; ro < graph.length ; ro++)
			for(int co = 0; co < graph[ro].length ; co++)
				{
					graph[ro][co] = 1000;
				}

		int count = 0;
		int ind = 0;
		int num = 0;
		Random randTwo = new Random();
		for(int numgen = 0; numgen < randTwo.nextInt(graph.length)/2; numgen++) //Random number of columns will be filled per row.
			for(int i = 0; i < graph.length; i++)
			{
				ind = randTwo.nextInt(graph.length);
				num = randTwo.nextInt(100);
				graph[i][ind] = num;
				count++;
			}
		
		for(int i = 0; i < graph.length; i++)
		{
			int j = i;
			graph[i][j] = 0;
		}

	}

	// Prints any Matrix onto Console.
	// int[][] mat is the Matrix that is to be printed. If the value is too high, then the value in each element will be replaced with "inf " meaning not
	// possible to traverse that edge.
	public static void printMatrix(int[][] mat)
	{
		for(int i = 0; i < mat.length; i++)
			for(int j = 0; j < mat[i].length; j++)
				{
					if(mat[i][j] > 300)
						System.out.print("inf ");
					else
						System.out.print(mat[i][j] + " ");
					if(j == mat[i].length - 1)
						System.out.println();
				}
	}

	// Prints array onto Console.
	public static void printArr(int[] arr)
	{
		for(int i = 0; i < arr.length; i++)
		{
			System.out.print(arr[i] + " ");
			if(i == arr.length - 1)
				System.out.println();
		}
	}

	// QoL function that prints dist[] and prev[] onto console.
	public void printInfo()
	{
		System.out.print("\nResults:\nDistance: ");
		printArr(dist);
		System.out.print("\nPrevious: ");
		printArr(prev);
	}

	// Function that calculates Dijkstra's Algorithm
	// Overall Runtime: O(n^2) with nested loops that consist of two for loops.
	// Overall Space: O(n) due to allocation of space for dist[], prev[], and mark[].
	public void calcDijAlg()
	{
		// Runtime: O(n)
		for(int i = 0; i < dist.length; i++)
		{
			dist[i] = 1000;
			mark[i] = 0;
		}

		dist[root] = 0; 
		// Runtime: O(n)
		for(int j = 0; j < graph[0].length; j++)
		{
			if(graph[root][j] != 0 && graph[root][j] < 100)
			{
				dist[j] = graph[root][j];
				prev[j] = root;
				mark[j] = 0;
			}
		}
		mark[root] = 1;
		prev[root] = root;

		for(int i = 0; i < graph.length-1; i++)
		{
			//Runtime for getMin(): O(n^2) with outer for loop and inner for loop within the function getMin().
			getMin();	
			mark[minDistInd] = 1;
			
			//Runtime for this for loop: O(n^2) to relax vertex.
			for(int j = 0; j < graph[0].length; j++)
			{
				if(mark[j] == 0 && dist[minDistInd] < 100)
					relax(i,j);
			}
		}
	}

	// Function that calculates the sanity problem and prints the information to console.
	public void calcDijAlgSanity()
	{
		calcDijAlg();
		printInfo();
	}

	// Function that relaxes the edge if there exists an edge that is shorter than the other pathway.
	private void relax(int i, int j)
	{
		if(dist[j] > dist[i] + graph[i][j])
		{
			dist[j] = dist[i] + graph[i][j];
			prev[j] = i;
		}
	}
	
	// Function that obtains the minimum pathway from a part of the vertex.
	// Runtime on calcDijAlg() becomes O(n^2) with this function's runtime.
	private void getMin()
	{
		int min = 10000;
		for(int i = 1; i < dist.length; i++)
			if(mark[i] != 1 && dist[i] < 1000 && min >= dist[i])
			{
				min = dist[i];
				minDistInd = i;
			}
	}

	// Function that calculates an adjacency matrix through Floyd's Algorithm
	// Overall runtime: O(n^3) due to the three nested loops traversing from 0 to n.
	// Overall Space: O(n^2) to initialize a new 2-dimensional matrix.
	public int[][] calcFloyd()
	{
		int[][] arrDist = new int[graph.length][graph[0].length];

		// O(n^2) Space and Time
		for(int i = 0; i < arrDist.length; i++)
			for(int j = 0; j < arrDist[0].length; j++)
				arrDist[i][j] = graph[i][j];

		// O(n^3) Runtime
		for(int k = 0; k < arrDist.length; k++)
			for(int u = 0; u < arrDist.length; u++)
				for(int v = 0; v < arrDist.length; v++)
					arrDist[u][v] = Math.min(arrDist[u][v], arrDist[u][k] + arrDist[k][v]);
		
		return arrDist;

		
	}
	
	// Static method that prints sanity check to the console.
	// Refer to Problem 3 Solution 3 from the homework for comparison.
	public static void sanityCheckOne()
	{
		int inf = 10000;
		int[][] arrTemp = {{0, 6,inf, 1, inf, inf, inf}, 
								  {inf, 0, inf, inf, 1, inf, inf},
								  {2, inf, 0, 3, inf, inf, inf},
								  {inf, inf, inf, 0, 4, inf, inf},
								  {inf, inf, inf, inf, 0, inf, inf},
								  {inf, inf, inf, inf, 2, 0, inf},
								  {3, inf, 2, inf, inf, 6, 0}};
		
		Graph temp = new Graph(arrTemp, 6);

		System.out.println("Sanity Check using Problem 3 on Homework 3. Seven nodes from 0 to 6, with Node #6 as the Root.");
		temp.calcDijAlgSanity();
		System.out.println("\n");
		printMatrix(temp.calcFloyd());
		

	}
}