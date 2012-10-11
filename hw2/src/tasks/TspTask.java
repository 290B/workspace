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
    
    private final int secondTown;
    
    /**
     * Starts the TSP computation
     * 
     * @return an int[] tour that lists the order of the cities of a minimal distance tour. 
     */
    
    public int [] execute() {
    	return computeRoute(towns,secondTown);
    }
    
    /**
	 * @param towns is the input sequence of the towns in the coordinate system. 
	 * takes a double[][] cities that codes the x and y coordinates of city[i]: 
	 * cities[i][0] is the x-coordinate of city[i] and cities[i][1] is the 
	 * y-coordinate of city[i]. 
	 * @param secondTown is the second town to be visited by the algorithm to 
	 * find a partial solution to the problem. Each task only inspects the 
	 * routes that begins with town[0] -> secondTown
	 * 
	 */
    
    public TspTask(double[][] towns,int secondTown) {
        this.towns = towns;
        this.secondTown = secondTown;
    }
    
    /**
     * Finds the best route by calling helper functions.
     * 
     * @param towns is the input sequence of the towns in the coordinate system
     * @return the sequence of towns to visit for the shorters route
     */
        
    public static int[] computeRoute(double[][] towns,int secondTown){
    	
    	double[][] distances = calcAllDistances(towns);	  	
    	int [][] allRoutes = generateRoutes(towns.length,secondTown);
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
    
    public static int [][] generateRoutes(int length, int secondTown){
		length = length-2;
		int [][] temp = new int [factorial(length)][length];
		int [][] temp2  = new int [factorial(length)][length+2]; 
		
		int a = 0;
		int b = 1;
		while (a<length){
			
			if (b != 0 && b != secondTown){
				temp[0][a] = b;
				a++;
			}
			b++;
		}

		
		for (int i = 1;i<temp.length;i++){
			temp[i] = Arrays.copyOf(temp[i-1],length);
			int k,j;
			for (k = length - 2; temp[i][k] >= temp[i][k+1]; k--);
			for (j = length - 1; temp[i][k] >= temp[i][j]; j--);
			swap(temp[i],k,j);
			for(int l = 1; k+l < length-l; l++) swap(temp[i],k+l,length-l);
			
		}
		

		
		for (int i = 0 ; i < temp2.length;i++){
			temp2[i][0] = 0;
			temp2[i][1] = secondTown;
		}


		
		
		for (int c = 0; c<temp.length;c++){
			for (int d = 0;d<(temp2[0].length-2);d++ ){
				temp2[c][d+2] = temp[c][d];
			}
		}

		return temp2;
		
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