package api;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Space extends Remote
{
    public static String SERVICE_NAME = "Space";

    void put( Task task ) throws RemoteException;

    Result take() throws RemoteException;

    void exit() throws RemoteException;
}
