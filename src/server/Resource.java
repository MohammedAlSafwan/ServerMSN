package server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

import util.MessageType;

/**
 * @author Mohammed Al-Safwan
 * @date Jan 30, 2019
 */
public class Resource {

	private ArrayList<Message> mMessages;
	private final String MESSAGES = "MESSAGES";

	public Resource() {
		mMessages = new ArrayList<>();
	}

	public synchronized void write(Message incomingMsg) {
		boolean containsMsg = false;

		//Check if the message already in
		for (Message currentMsg : mMessages) {
			if (incomingMsg.equals(currentMsg)) {
				containsMsg = true;
				break;
			}
		}
		//Add to list if message is new
		incomingMsg.setType(MessageType.NULL);
		if (!containsMsg) {
			mMessages.add(incomingMsg);
		}
		Collections.sort(mMessages);
		System.out.println("Resource - write - Done writing");
	}

	public Message readLastMessage() {
		return mMessages.get(mMessages.size() - 1);
	}

	public boolean isResourceEmpty() {
		return mMessages.size() == 0;
	}

	public JSONObject readAllMessages() {
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	ArrayList<Message> readAllMessages()
		//
		// Method parameters	:	args - the method permits String command line parameters to be entered
		//
		// Method return		:	ArrayList<Message>
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
		JSONObject response = new JSONObject();
		JSONArray msgs = new JSONArray();
		for (Message current : mMessages) {
			msgs.put(current.toJSON());
		}
		response.put(MESSAGES, msgs);
		return response;
	}

	// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
	// Method				:	boolean checkLastDate()
	//
	// Method parameters	:	args - the method permits String command line parameters to be entered
	//
	// Method return		:	boolean
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
	public boolean isThereMsgAfter(String lastDate) {
		if (isResourceEmpty())
			return false;

		Date dateToCheck = new Date(Long.parseLong(lastDate));
		for (Message current : mMessages) {
			if (current.getMessageDate().compareTo(dateToCheck) > 0) {
				return true;
			}
		}
		return false;
	}

	public JSONObject getMsgsAfter(String lastDate) {
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	JSONObject getMsgsAfter()
		//
		// Method parameters	:	args - the method permits String command line parameters to be entered
		//
		// Method return		:	JSONObject
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
		Date dateToCheck = new Date(Long.parseLong(lastDate));
		JSONObject response = new JSONObject();
		JSONArray msgs = new JSONArray();

		for (Message current : mMessages) {
			if (current.getMessageDate().compareTo(dateToCheck) > 0) {
				msgs.put(current.toJSON());
			}
		}

		response.put(MESSAGES, msgs);
		return response;
	}

}