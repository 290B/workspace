package api;
import java.io.Serializable;

public class Result implements Serializable
{
    static final long serialVersionUID = 227L; // Was missing 
	private Object taskReturnValue;
    private long taskRunTime;
    private int taskIndex;

    public Result( Object taskReturnValue, long taskRunTime , int taskIndex)
    {
        assert taskReturnValue != null;
        assert taskRunTime >= 0;
        this.taskReturnValue = taskReturnValue;
        this.taskRunTime = taskRunTime;
        this.taskIndex = taskIndex;
    }

    public Object getTaskReturnValue() { return taskReturnValue; }

    public long getTaskRunTime() { return taskRunTime; }
    
    public int getTaskIndex() {return taskIndex; }
}