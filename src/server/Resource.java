package server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import util.FilterFileReader;
import util.MessageType;

/**
 * @author Mohammed Al-Safwan
 * @date Jan 30, 2019
 */
public class Resource {

	private ArrayList<Message> mMessages;
	private final String MESSAGES = "MESSAGES";
	private HashMap<String, String> mNonosFilter;
	private ClassLoader classLoader;
	
	public Resource() {
		mMessages = new ArrayList<>();
		classLoader = ClassLoader.getSystemClassLoader();
		mNonosFilter = FilterFileReader.getFilter(classLoader.getResource("filteredWords").getFile().replaceAll("%20"," "));
	}

	public synchronized void write(Message incomingMsg) {
		boolean containsMsg = false;
		
		//filter the message before storing it
		System.out.println("============================");
		System.out.println("Before");
		System.out.println(incomingMsg.toPrettyString());
		filterMessage(incomingMsg);
		System.out.println("After");
		System.out.println(incomingMsg.toPrettyString());
		System.out.println("============================");

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

	private void filterMessage(Message incomingMsg) {
		String msgBody = incomingMsg.getMessage();
		String[] splitedMsg = msgBody.split(" ");
		String outputMsg = "";
		
		for(int index = 0; index<splitedMsg.length; index++){
			if(mNonosFilter.containsKey(splitedMsg[index].toLowerCase())) {
				splitedMsg[index] = mNonosFilter.get(splitedMsg[index]);
			}
			if(index == 0) {
				outputMsg = outputMsg.concat(splitedMsg[index]);
			}else {
				outputMsg = outputMsg.concat(" " + splitedMsg[index]);
			}
			System.out.println(outputMsg);
		}
		
		incomingMsg.setMessage(outputMsg);
		
//		return incomingMsg;
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