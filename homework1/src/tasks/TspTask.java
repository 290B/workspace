package tasks;

import api.Task;
import java.util.Arrays;
import java.io.Serializable;

/**
 * This class implements a Traveling Salesman Problem solver as a task
 * which fits into the RMI framework implemented in the API.
 * 
 * The solver simply brute forces and finds all possible routes in both 
 * directions and then evaluates them all to find the most efficient one.
 * 
 * @author torgel
 *
 */

public class TspTask implements Task<int[]>, Serializable{

    private static final long serialVersionUID = 227L;
    
    private final double[][] towns;
    
    /**
     * Starts the TSP computation
     * 
     * @return an int[] tour that lists the order of the cities of a minimal distance tour. 
     */
    
    public int [] execute() {
    	return computeRoute(towns);
    }
    
    /**
	 * @param towns is the input sequence of the towns in the coordinate system. 
	 * takes a double[][] cities that codes the x and y coordinates of city[i]: 
	 * cities[i][0] is the x-coordinate of city[i] and cities[i][1] is the 
	 * y-coordinate of city[i]. 
	 * 
	 */
    
    public TspTask(double[][] towns) {
        this.towns = towns;
    }
    
    /**
     * Finds the best route by calling helper functions.
     * 
     * @param towns is the input sequence of the towns in the coordinate system
     * @return the sequence of towns to visit for the shorters route
     */
        
    public static int[] computeRoute(double[][] towns){
    	
    	double[][] distances = calcAllDistances(towns);	  	
    	int [][] allRoutes = generateRoutes(distances[0].length);
    	int []bestRoute = findBestRoute(allRoutes,distances);
    	return bestRoute;
    }
    
    /**
     * Iterates over all the routes to find the shortest one
     * 
     * @param allRoutes is a list of all possible ways to visit towns on the map
     * @param distances is a list of distances between each pair of town
     * @return the shortest of the routes that were inspected.
     */

    public static int[] findBestRoute(int[][] allRoutes,double[][] distances){
    	
    	double bestSoFar = 1000000;
    	int bestIndex = 0;
    	
    	for (int i = 0;i<allRoutes.length;i++){ //which length
    		double temp = calcRouteLength(allRoutes[i],distances);
    		if (temp<bestSoFar){
    			bestSoFar = temp;
    			bestIndex = i;
    		}
    		
    	}
    	
    	return allRoutes[bestIndex];
    }
    /**
     * This methods finds all possible permutations or ways to traverse
     * the map. 
     * 
     * The code to generate permutations is borrowed from:
     * borrowed from http://stackoverflow.com/questions/5692107/tips-implementing-permutation-algorithm-in-java
     * 
     * @param length the number of towns to visit
     * @return a list of all possible routes 
     */
    
     public static int [][] generateRoutes(int length){
    	int [][] temp = new int [factorial(length)][length];
    	for (int i = 0; i < length; i++){
    		temp[0][i] = i;
    	}
    	for (int i = 1;i<temp.length;i++){
    		temp[i] = Arrays.copyOf(temp[i-1],length);
    		int k,j;
    		for (k = length - 2; temp[i][k] >= temp[i][k+1]; k--);
    		for (j = length - 1; temp[i][k] >= temp[i][j]; j--);
    		swap(temp[i],k,j);
    		for(int l = 1; k+l < length-l; l++) swap(temp[i],k+l,length-l);
    		
    	}
    	return temp;
    	
    }
    
    static void swap(int[] a, int i, int j) {
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
     }
    
    public static int factorial(int n)
    {
        if (n == 0) return 1;
        return n * factorial(n-1);
    }
    
    /**
     * Calculates the length of one route.
     * 
     * @param route gives the sequence of towns to visits.
     * @param distances gives the distances between each town
     * @return
     */

    public static double calcRouteLength(int[] route,double[][] distances){
    	double sum = 0;
    	for (int i = 0;i<route.length-1;i++){
    		sum += distances[route[i]][route[i+1]];
    	}
    	return sum;
    	
    }
    
    /**   
	 * This methods simply calculates the distance between each and every
	 * pair of towns on the map. 
	 *
	 * @param towns , the coordinates of all towns on the map
	 * @return a list of distances between every pair of towns
	 */
    
    public static double[][] calcAllDistances(double[][] towns){
    	int inf = 10000;
    	double[][] distances = new double[towns.length][towns.length];
    	for (int i = 0; i< towns.length;i++){
    		for (int j = 0; j<towns.length;j++){
    			if (i != j){
    				distances[i][j] = calcOneDistance(towns[i][0],towns[i][1],towns[j][0],towns[j][1]);
    			}
    			else distances[i][j] = inf;
    			
    		}
    	}
    	return distances;
    }
    /**
     * Calculates the distance between two towns from their position 
     * as x and y coordinates on the map.
     * 
     * @param town1X
     * @param town1Y
     * @param town2X
     * @param town2Y
     * @return the length between two towns
     */
    
    public static double calcOneDistance(double town1X, double town1Y, double town2X, double town2Y){
    	return Math.sqrt((town1X-town2X)*(town1X-town2X) + (town1Y-town2Y)*(town1Y-town2Y));
    }

	
}