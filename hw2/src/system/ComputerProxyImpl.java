package system;

import java.rmi.RemoteException;

import api.Result;
import api.Task;

public class ComputerProxyImpl extends Thread {
	final private Computer computer;
	final private SpaceImpl spaceImpl; 
	
	public ComputerProxyImpl(Computer computer, SpaceImpl spaceImpl){
		this.computer = computer;
		this.spaceImpl = spaceImpl;
	}
	public void exit() throws RemoteException{
		computer.exit();
		this.stop();
	}
	
	public void run(){
		System.out.println("Proxy started");
		while(true){
			Task t;
			try {
				t = spaceImpl.takeTaskQueue();
				try {
					long start = System.currentTimeMillis();
					Object o = computer.execute(t);
					long stop = System.currentTimeMillis();
				
					spaceImpl.putResultQueue(new Result(o , (stop-start)));
				} catch (RemoteException e) {
					spaceImpl.putTaskQueue(t);
					e.printStackTrace();
					spaceImpl.unRegister(this);
					return;
				}
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				spaceImpl.unRegister(this);
				return;
			}
		}
	}
}
