package api;

import java.rmi.Remote;
import java.rmi.RemoteException;


/**
 * 
 *  Remote interface to use RMI to remotely compute a general task
 *  
 * @throws RemoteException if it cannot contact the remote computer
 * @param <T> is the task of type T which will be computed remotely
 * @see TspTask for an example of a task
 * @return an object of type <T> which is the result of the remote computation.
 * Should be type casted to the correct type.
 * @author torgel
 */

public interface Computer extends Remote{
	<T> T execute(Task<T> t) throws RemoteException;
}
