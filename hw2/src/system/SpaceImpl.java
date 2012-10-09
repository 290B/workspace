package system;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.*;

import api.*;

public class SpaceImpl extends UnicastRemoteObject implements Space{

	
	/**
	 * 
	 */
	private static final BlockingQueue taskQueue = new LinkedBlockingQueue();
	private static final long serialVersionUID = 1L;
	protected SpaceImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (System.getSecurityManager() == null ) 
		{ 
		   System.setSecurityManager(new java.rmi.RMISecurityManager() ); 
		}
		try {
			Space space = new SpaceImpl();
			Space stub = (Space)UnicastRemoteObject.exportObject(space, 0);
			Registry registry = LocateRegistry.createRegistry( 1099 );
			registry.rebind(SERVICE_NAME, stub);
			System.out.println("SpaceImpl bound");
		} catch (RemoteException e) {
			e.printStackTrace();
		}

	}
	@Override
	public void put(Task task) throws RemoteException {
		
		
	}
	@Override
	public Result take() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void exit() throws RemoteException {
		// TODO Auto-generated method stub
		
	}
	
}
