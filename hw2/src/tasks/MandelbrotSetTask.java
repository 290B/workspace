package tasks;

import api.Task;

/**
 * This class is a implementations that solves the Mandelbrot set.
 * @author orein
 *
 */

public class MandelbrotSetTask implements Task<int[][]>, java.io.Serializable{
	private final double cornerX;
	private final double cornerY;
	private final double edgeLength;
	private final int n;
	private final int iterLimit;
	
	private static final long serialVersionUID = 227L;
	
	/**
	 * 
	 * @param setCornerX is the x coordinate describing the left bottom corner of the square to calculate, typical value -2
	 * @param setCornerY is the y coordinate describing the left bottom corner of the square to calculate, typical value -2
	 * @param setEdgeLength is the length of the sides of the square starting in the left bottom corner, typical value 4
	 * @param setN is the resolution of the calculation of the image of the Mandelbrot set. Typical value 256
	 * @param setIterLimit is the iteration limit for each pixel in the calculation. Exceeding this limit stops the calculation. Typical value 64
	 */
	
	public MandelbrotSetTask(double setCornerX, double setCornerY, double setEdgeLength, int setN, int setIterLimit){
		this.cornerX = setCornerX;
		this.cornerY = setCornerY;
		this.edgeLength = setEdgeLength;
		this.n = setN;
		this.iterLimit = setIterLimit;
	}
	
	/**
	 * To execute the calculations 
	 * @return the 2-dim count array that describes the number of iterations for each point in the Mandelbrot set.
	 */
	
	public int[][] execute(){
		int[][] count = new int[n][n]; 
		for (int i = 0; i < n; i++){
			for (int j = 0; j < n; j++){
				double cRe = cornerX+(edgeLength/n)*i;
				double cIm = cornerY+(edgeLength/n)*j;
				double re = cRe;
				double im = cIm;
				double sqrRe;
				double sqrIm;
				int k = 0;
				while (re*re + im*im <= 4 && k < iterLimit){
					sqrRe = (re*re - im*im);
					sqrIm = (2*im*re);
					re = sqrRe + cRe;
					im = sqrIm + cIm;
					k++;
				}
				count[i][n-j-1] = k; 
			}
		}
		return count;
	}
}
