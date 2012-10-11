package system;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import system.Computer;
import api.Task;

public class ComputerImpl implements Computer {
	
	public <T> T execute(Task t) {
        return (T)t.execute();
    }
	
	public static void main(String[] args) {
		String spaceHost = "boris";
		String name = "Computer";
		String spaceName = "Space";
		if (System.getSecurityManager() == null ) 
		{ 
		   System.setSecurityManager(new java.rmi.RMISecurityManager() ); 
		}
		try{
			
			Computer computer = new ComputerImpl();
			Computer stub = (Computer) UnicastRemoteObject.exportObject(computer, 0);
			Registry registry = LocateRegistry.createRegistry( 1099 );
			registry.rebind(name, stub);
			
			System.out.println("Connecting to space: " + spaceHost);
			String spaceLookup = "Space";
			Registry registrySpace = LocateRegistry.getRegistry(spaceHost);
			Computer2Space space = (Computer2Space)registrySpace.lookup(spaceName);
			space.register(computer);
			System.out.println("ComputerImpl bound");
		} catch (Exception e) {
            System.err.println("ComputerImpl exception:");
            e.printStackTrace();
        }
	}

	@Override
	public void exit() throws RemoteException {
		System.exit(0);
		
	}

}
