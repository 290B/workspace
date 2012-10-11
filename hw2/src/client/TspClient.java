package client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import api.Space;
import api.Result;
import tasks.TspTask;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class TspClient {
	
	private static final int N_PIXELS = 256;
	private static final double[][] cities =
		{
			{ 1, 1 },
			{ 8, 1 },
			{ 8, 8 },
			{ 1, 8 },
			{ 2, 2 },
			{ 7, 2 },
			{ 7, 7 },
			{ 2, 7 },
			{ 3, 3 },
			{ 6, 3 },
			{ 6, 6 },
			{ 3, 6 }
		};
	private static final int numTasks = cities.length-1;
	
    public static void main(String args[]) {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
        	
        	double[][] distances = tasks.TspTask.calcAllDistances(cities);
        	
        	String name = "Space";
    		Registry registry = LocateRegistry.getRegistry(args[0]);
    		Space space = (Space) registry.lookup(name);
    		        	
    		// every task begins in "the last" city and then computes all routes that begin with that city and "secondCity"
        	for (int secondCity = 1;secondCity<cities.length;secondCity++){
        		space.put(new TspTask(cities,secondCity));
        	}
        	
        	Result[] results = new Result[numTasks];
        	for (int i = 0; i < numTasks;i++){
        		results[i] = space.take();
        	}
        	
        	int [][] routes = new int [numTasks][cities.length];
        	for (int i = 0; i < numTasks ; i++){
        		routes[i] = (int[])results[i].getTaskReturnValue();
        	}
        	
        	int [] tour = tasks.TspTask.findBestRoute(routes,distances);

            JLabel euclideanTspLabel = displayEuclideanTspTaskReturnValue( cities, tour );
            JFrame frame = new JFrame( "Result Visualizations" );
            frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
            Container container = frame.getContentPane();
            container.setLayout( new BorderLayout() );
            container.add( new JScrollPane( euclideanTspLabel ), BorderLayout.EAST );
            frame.pack();
            frame.setVisible( true );
            space.exit();
        } catch (Exception e) {
            System.err.println("ComputeTSP exception:");
            e.printStackTrace();
        }
        
        
        
    }    
            
    private static JLabel displayEuclideanTspTaskReturnValue( double[][] cities, int[] tour )
    {
        System.out.print( "Tour: ");
        for ( int city: tour )
        {
        	System.out.print( city + " ");
        }
        System.out.println("");

        // display the graph graphically, as it were
        // get minX, maxX, minY, maxY, assuming they 0.0 <= mins
        double minX = cities[0][0], maxX = cities[0][0];
        double minY = cities[0][1], maxY = cities[0][1];
        for ( int i = 0; i < cities.length; i++ )
        {
            if ( cities[i][0] < minX ) minX = cities[i][0];
            if ( cities[i][0] > maxX ) maxX = cities[i][0];
            if ( cities[i][1] < minY ) minY = cities[i][1];
            if ( cities[i][1] > maxY ) maxY = cities[i][1];
        }
    	
        // scale points to fit in unit square
        double side = Math.max( maxX - minX, maxY - minY );
        double[][] scaledCities = new double[cities.length][2];
        for ( int i = 0; i < cities.length; i++ )
        {
            scaledCities[i][0] = ( cities[i][0] - minX ) / side;
            scaledCities[i][1] = ( cities[i][1] - minY ) / side;
        }

        Image image = new BufferedImage( N_PIXELS, N_PIXELS, BufferedImage.TYPE_INT_ARGB );
        Graphics graphics = image.getGraphics();

        int margin = 10;
        int field = N_PIXELS - 2*margin;
        // draw edges
        graphics.setColor( Color.BLUE );
        int x1, y1, x2, y2;
        int city1 = tour[0], city2;
        x1 = margin + (int) ( scaledCities[city1][0]*field );
        y1 = margin + (int) ( scaledCities[city1][1]*field );
        for ( int i = 1; i < cities.length; i++ )
        {
            city2 = tour[i];
            x2 = margin + (int) ( scaledCities[city2][0]*field );
            y2 = margin + (int) ( scaledCities[city2][1]*field );
            graphics.drawLine( x1, y1, x2, y2 );
            x1 = x2;
            y1 = y2;
        }
        city2 = tour[0];
        x2 = margin + (int) ( scaledCities[city2][0]*field );
        y2 = margin + (int) ( scaledCities[city2][1]*field );
        graphics.drawLine( x1, y1, x2, y2 );

        // draw vertices
        int VERTEX_DIAMETER = 6;
        graphics.setColor( Color.RED );
        for ( int i = 0; i < cities.length; i++ )
        {
            int x = margin + (int) ( scaledCities[i][0]*field );
            int y = margin + (int) ( scaledCities[i][1]*field );
            graphics.fillOval( x - VERTEX_DIAMETER/2,
                               y - VERTEX_DIAMETER/2,
                              VERTEX_DIAMETER, VERTEX_DIAMETER);
        }
        ImageIcon imageIcon = new ImageIcon( image );
        return new JLabel( imageIcon );
    }
    
}