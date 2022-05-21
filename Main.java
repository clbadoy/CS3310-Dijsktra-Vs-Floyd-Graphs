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
 * This file is the runner file for the project to track time elapsed and insert input on size.
 */

import java.util.Scanner;

class Main {
  public static void main(String[] args) {

		// Prints Sanity Check using problem 3 on Homework 3.
		Graph.sanityCheckOne();

		Scanner keyboard = new Scanner(System.in);

		// Takes User input on size of initializing Matrix.
		System.out.print("\nWhat is the size of your matrix square: ");
		int size = keyboard.nextInt();
		keyboard.close();
		
		// Initialize a dense graph and sparse graph with inputted size.
		Graph denseA = new Graph(size);
		Graph sparseA = new Graph(size);
		
		// Generate two unique graphs for dense and sparse every different instance of running the program.
		denseA.generateDense();
		sparseA.generateSparse();
		
		// Calculates time with a dense graph on Djikstra's Algorithm.
		System.out.println("Calculating Dijkstra's dense...");
		long timeStart1 = System.nanoTime();
		denseA.calcDijAlg();
		long timeEnd1 = System.nanoTime();
		
		// Calculates time with a dense graph on Floyd's Algorithm.
		System.out.println("Calculating Floyd dense...");
		long timeStart2 = System.nanoTime();
		denseA.calcFloyd();
		long timeEnd2 = System.nanoTime();
		
		// Calculates time with a sparse graph on Djikstra's Algorithm.
		System.out.println("Calculating Dijkstra sparse...");
		long timeStart3 = System.nanoTime();
		sparseA.calcDijAlg();
		long timeEnd3 = System.nanoTime();

		// Calculates time with a sparse graph on Floyd's Algorithm.
		System.out.println("Calculating Floyd sparse...");
		long timeStart4 = System.nanoTime();
		sparseA.calcFloyd();
		long timeEnd4 = System.nanoTime();
		

		long elapse1 = timeEnd1 - timeStart1;
		long elapse2 = timeEnd2 - timeStart2;
		long elapse3 = timeEnd3 - timeStart3;
		long elapse4 = timeEnd4 - timeStart4;
		
		// Prints Time elapsed for each of the Algorithms onto console.
		System.out.println("\nAssume time is in nanoseconds. Time elapse for...\nDijkstra Dense: " + elapse1 + "\nFloyd Dense: " + elapse2 + "\nDijskra Sparse: " + elapse3 + "\nFloyd Sparse: " + elapse4);
  	}


}