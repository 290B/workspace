package client;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import tasks.MandelbrotSetTask;
import java.rmi.registry.*;
import api.Task;
import api.Computer; 

public class MandelbrotClient {
	private static final int N_PIXELS = 256;
	private static final int ITERATION_LIMIT = 64;
	private static final double CORNER_X = -2.0;
	private static final double CORNER_Y = -2.0;
	private static final double EDGE_LENGTH = 4.0;
	
	public static void main(String[] args) {
		if (System.getSecurityManager() == null ){ 
		   System.setSecurityManager(new java.rmi.RMISecurityManager() ); 
		}
		try{
			MandelbrotSetTask mandelbrotSetTask = new MandelbrotSetTask(CORNER_X, CORNER_Y, EDGE_LENGTH, N_PIXELS, ITERATION_LIMIT);
			int[][] counts = (int[][]) runTask(mandelbrotSetTask, args[0]);
			
			JLabel mandelbrotLabel = displayMandelbrotSetTaskReturnValue( counts );
			JFrame frame = new JFrame( "Result Visualizations" );
			frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
			Container container = frame.getContentPane();
			container.setLayout( new BorderLayout() );
			container.add( new JScrollPane( mandelbrotLabel ), BorderLayout.WEST );
			frame.pack();
			frame.setVisible( true );
		}catch (Exception e) {
			System.err.println("MandelbrotClient exception:");
			e.printStackTrace();
		}
	}

	private static Object runTask(Task<?> t, String host)  {
	try{	
		System.out.println("Connecting to : " + host);
		String name = "Computer";
		Registry registry = LocateRegistry.getRegistry(host);
		Computer computer = (Computer) registry.lookup(name);
		int total = 0;
		
		for (int i = 0; i < 5; i++){
			long start = System.currentTimeMillis();
			computer.execute(t);
			long stop = System.currentTimeMillis();
			System.out.println("Mandelbrot, " + (i+1) +" try: " +(stop-start) +" milliseconds");
			total += (stop-start);
		}
		System.out.println("Average time: " + total/5);
		return computer.execute(t);
	
	}catch (Exception e){
		System.err.println("MandelbrotClient exception:");
		e.printStackTrace();
		return 0;
		}
	}
	
	private static JLabel displayMandelbrotSetTaskReturnValue( int[][] counts )
	{
	    Image image = new BufferedImage( N_PIXELS, N_PIXELS, BufferedImage.TYPE_INT_ARGB );
	    Graphics graphics = image.getGraphics();
	    for ( int i = 0; i < counts.length; i++ )
	    for ( int j = 0; j < counts.length; j++ )
	    {
	        graphics.setColor( getColor( counts[i][j] ) );
	        graphics.fillRect(i, j, 1, 1);
	    }
	    ImageIcon imageIcon = new ImageIcon( image );
	    return new JLabel( imageIcon );
	}

	private static Color getColor( int i )
	{
	    if ( i == ITERATION_LIMIT )
	        return Color.BLACK;
	    return Color.getHSBColor((float)i/ITERATION_LIMIT, 1F, 1F);
	}
	

}
