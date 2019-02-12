package server;

import java.io.IOException;

/**
 * @author Mohammed Al-Safwan
 * @date Jan 30, 2019
 */
public class Main {

	public static void main(String[] args) {
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	void main()
		//
		// Method parameters	:	args - the method permits String command line parameters to be entered
		//
		// Method return		:	void
		//
		// Synopsis				:   Start the application
		//							
		//
		// Modifications		:
		//									Date			    			Developer								Notes
		//							  		----			     			 ---------			 	    				 -----
		//							Jan 30, 2019		    Mohammed Al-Safwan				Initial setup
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		Master master = null;
		try {
			master = new Master();
			master.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
