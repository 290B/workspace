package tasks;
import java.io.Serializable;
public class MandelbrotReturn implements Serializable{

    static final long serialVersionUID = 227L; // Was missing 
	private int taskCoordX;
	private int taskCoordY;
    private int [][] MandelbrotResults;

    public MandelbrotReturn( int taskCoordX, int taskCoordY, int [][] MandelbrotResults)
    {
    	this.taskCoordY = taskCoordY;
        this.taskCoordX = taskCoordX;
        this.MandelbrotResults = MandelbrotResults;
    }

    public int getTaskCoordX() { return taskCoordX; }
    
    public int getTaskCoordY() { return taskCoordY; }

    public int [][] getMandelbrotResults() { return MandelbrotResults; }
    
	
}
