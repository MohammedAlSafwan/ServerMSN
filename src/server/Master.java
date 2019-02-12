package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import util.Announcement;
import util.FilterFileReader;

/**
 * @author Mohammed Al-Safwan
 * @date Jan 30, 2019
 */
public class Master extends Thread {

	private final int mPort = 6666; 								//Main port
	private ServerSocket mSeverSocket; 					//The server socket
	private Resource mRes; 										//Shared resources with the slaves
	private ArrayList<Slave> mSlaves; 						//List of slaves

	
	public Master() throws IOException {
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	public Master() throws IOException
		//
		// Method parameters	:	
		//
		// Method return		:	
		//
		// Synopsis				:   Construct method that throws IOException if an I/O error occurs when opening the socket.
		//							
		//
		// Modifications		:
		//									Date			    			Developer								Notes
		//							  		----			     			 ---------			 	    				 -----
		//							Jan 30, 2019		    Mohammed Al-Safwan				Initial setup
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		mSeverSocket = new ServerSocket(mPort);
		mSeverSocket.setSoTimeout(0);
		mRes = new Resource();
		mSlaves = new ArrayList<>();

	}

	@Override
	public void run() {

		while (true) {
			try {

				// create slaves:
				System.out.println("start connection");
				Socket connectionSocket = mSeverSocket.accept();

				// start a slaves:
				Slave addSlave = new Slave(connectionSocket, mRes);
				addSlave.start();
				mSlaves.add(addSlave);
				System.out.println("connection accepted");

			} catch (IOException e) {
				e.printStackTrace();
				break;
			}
		}
		
		//In case anything wrong happens then kill all slaves:
		for (Slave slave : mSlaves) {
			try {
				slave.join();
			} catch (InterruptedException ie) {
				System.err.println(ie.getMessage());
			} finally {
				System.out.println(slave.getName() + " has died");
				try {
					mSeverSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

		System.out.println("The master will now die ... ");

	}
}
