package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class FilterFileReader {

	public static HashMap<String, String> getFilter(String filePath) {

		//output hashmap
		HashMap<String, String> outputFilter = new HashMap<>();

		//Create the file reader
		File file = null;
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			
			file = new File(filePath);
			fis = new FileInputStream(file);
			isr = new InputStreamReader(fis);
			br = new BufferedReader(isr);
			
			//Start reading
			String line;
			String[] splitedLine;
			while ((line = br.readLine()) != null) {
				//Split the line into 2 parts "searchWord alternativeWord"
				splitedLine = line.split(" ");
				if(splitedLine.length == 2) {
					outputFilter.put(splitedLine[0], splitedLine[1]);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}



		return outputFilter;
	}
}
