package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Collections;

import org.json.JSONObject;

import util.Announcement;
import util.MessageType;

/**
 * @author Mohammed Al-Safwan
 * @date Jan 30, 2019
 */
public class Slave extends Thread {

	private Resource mSharedResource; //The shared resource from the master thread
	private Socket mConnectionSocket; //The socket connection from the master
//	private final String MESSAGES = "MESSAGES";

	public Slave(Socket connectionSocket, Resource rcs) {
		//		mAnnouncement = announcer;
		mConnectionSocket = connectionSocket;
		mSharedResource = rcs;
	}

	@Override
	public void run() {
		System.out.println("run " + this.getName() + "-Waiting for User input:");

		try {
			DataInputStream inFromClient = new DataInputStream(mConnectionSocket.getInputStream());
			String utf = "";

			System.out.println(this.getName() + "-Waiting for User input:");
			if ((utf = inFromClient.readUTF()) != null) {

				System.out.println(utf);
				Message incomingMsg = new Message();
				JSONObject jsonUTF = new JSONObject(utf);

				incomingMsg.toMessage(jsonUTF);
				System.out.println(incomingMsg.toPrettyString());

				switch (incomingMsg.getType()) {
				case EMPTY:
					doEmpty(incomingMsg);
					break;
				case NULL:
					doNull(incomingMsg);
					break;
				case SEND:
					doSend(incomingMsg);
					break;
				case GET_ALL_MSGS:
					doGetAllMsgs(incomingMsg);
					break;
				case GET_MSG_AFTER_DATE:
					doGetMsgAfterDate(incomingMsg);
					break;
				case CLOSE:
					doCloseAllThreads();
					break;
				default:
					System.out.println("Something went wrong with the msg:");
					break;
				}
				//				mSharedResource.write(utf);

				System.out.println(this.getName() + "-Time to close");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeEverything();
		}

	}

	private void doSend(Message incomingMsg) {
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	void doSend()
		//
		// Method parameters	:	args - the method permits String command line parameters to be entered
		//
		// Method return		:	void
		//
		// Synopsis				:   
		//							
		//
		// Modifications		:
		//							Date			    Developer				Notes
		//							  ----			      ---------			 	     -----
		//							Feb 1, 2019		    Mohammed Al-Safwan				Initial setup
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

		//send empty response
		Message responseMsg = new Message();
		responseMsg.setType(MessageType.EMPTY);
		sendResponse(responseMsg.toString());

		mSharedResource.write(incomingMsg);

	}

	private void doCloseAllThreads() {
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	void doCloseAllThreads()
		//
		// Method parameters	:	args - the method permits String command line parameters to be entered
		//
		// Method return		:	void
		//
		// Synopsis				:   
		//							
		//
		// Modifications		:
		//							Date			    Developer				Notes
		//							  ----			      ---------			 	     -----
		//							Jan 31, 2019		    Mohammed Al-Safwan				Initial setup
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

	}

	private void doGetMsgAfterDate(Message incomingMsg) {
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	void doGetMsgAfterDate()
		//
		// Method parameters	:	args - the method permits String command line parameters to be entered
		//
		// Method return		:	void
		//
		// Synopsis				:   
		//							
		//
		// Modifications		:
		//							Date			    Developer				Notes
		//							  ----			      ---------			 	     -----
		//							Jan 31, 2019		    Mohammed Al-Safwan				Initial setup
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		JSONObject allMsgs = mSharedResource.getMsgsAfter(String.valueOf(incomingMsg.getMessageDate().getTime()));
		Message responseMsg = new Message();
		responseMsg.setType(MessageType.GET_MSG_AFTER_DATE);
		responseMsg.setSender("server");
		responseMsg.setMessage(allMsgs.toString());
		sendResponse(responseMsg.toString());
	}

	private void doGetAllMsgs(Message incomingMsg) {
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	void doGetAllMsgs()
		//
		// Method parameters	:	args - the method permits String command line parameters to be entered
		//
		// Method return		:	void
		//
		// Synopsis				:   
		//							
		//
		// Modifications		:
		//							Date			    Developer				Notes
		//							  ----			      ---------			 	     -----
		//							Jan 31, 2019		    Mohammed Al-Safwan				Initial setup
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		JSONObject allMsgs = mSharedResource.readAllMessages();
		Message responseMsg = new Message();
		responseMsg.setType(MessageType.GET_ALL_MSGS);
		responseMsg.setSender("server");
		responseMsg.setMessage(allMsgs.toString());
		sendResponse(responseMsg.toString());

	}

	private void doNull(Message incomingMsg) {
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	void doNull()
		//
		// Method parameters	:	args - the method permits String command line parameters to be entered
		//
		// Method return		:	void
		//
		// Synopsis				:   
		//							
		//
		// Modifications		:
		//							Date			    Developer				Notes
		//							  ----			      ---------			 	     -----
		//							Jan 31, 2019		    Mohammed Al-Safwan				Initial setup
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

	}

	private void doEmpty(Message incomingMsg) {
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	void doEmpty()
		//
		// Method parameters	:	args - the method permits String command line parameters to be entered
		//
		// Method return		:	void
		//
		// Synopsis				:   
		//							
		//
		// Modifications		:
		//							Date			    Developer				Notes
		//							  ----			      ---------			 	     -----
		//							Jan 31, 2019		    Mohammed Al-Safwan				Initial setup
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

	}

	private void closeEverything() {
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	void closeEverything()
		//
		// Method parameters	:	args - the method permits String command line parameters to be entered
		//
		// Method return		:	void
		//
		// Synopsis				:   
		//							
		//
		// Modifications		:
		//							Date			    Developer				Notes
		//							  ----			      ---------			 	     -----
		//							Jan 30, 2019		    Mohammed Al-Safwan				Initial setup
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		//Close 
		try {
			mConnectionSocket.close();
			System.out.println(this.getName() + "-closting thread");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void sendResponse(String response) {
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	void sendLastMessage()
		//
		// Method parameters	:	args - the method permits String command line parameters to be entered
		//
		// Method return		:	void
		//
		// Synopsis				:   
		//							
		//
		// Modifications		:
		//							Date			    Developer				Notes
		//							  ----			      ---------			 	     -----
		//							Jan 30, 2019		    Mohammed Al-Safwan				Initial setup
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		DataOutputStream outToClient;
		try {

			outToClient = new DataOutputStream(mConnectionSocket.getOutputStream());

			System.out.println("Sending: " + response);
			outToClient.writeUTF(response);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}