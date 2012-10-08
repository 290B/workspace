package api;
import java.io.Serializable;

public class Result implements Serializable
{
    static final long serialVersionUID = 227L; // Was missing 
	private Object taskReturnValue;
    private long taskRunTime;

    public Result( Object taskReturnValue, long taskRunTime )
    {
        assert taskReturnValue != null;
        assert taskRunTime >= 0;
        this.taskReturnValue = taskReturnValue;
        this.taskRunTime = taskRunTime;
    }

    public Object getTaskReturnValue() { return taskReturnValue; }

    public long getTaskRunTime() { return taskRunTime; }
}