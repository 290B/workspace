package system;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import api.Computer;
import api.Task;

public class ComputerImpl implements Computer {
	
	public <T> T execute(Task<T> t) {
        return t.execute();
    }
	
	public static void main(String[] args) {
		if (System.getSecurityManager() == null ) 
		{ 
		   System.setSecurityManager(new java.rmi.RMISecurityManager() ); 
		}
		try{
			String name = "Computer";
			Computer computer = new ComputerImpl();
			Computer stub = (Computer) UnicastRemoteObject.exportObject(computer, 0);
			Registry registry = LocateRegistry.createRegistry( 1099 );
			registry.rebind(name, stub);
			System.out.println("ComputerImpl bound");
		} catch (Exception e) {
            System.err.println("ComputerImpl exception:");
            e.printStackTrace();
        }
	}

}
