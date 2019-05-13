package sarsoo.fmframework.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONObject;

import sarsoo.fmframework.log.Logger;
import sarsoo.fmframework.log.entry.ErrorEntry;
import sarsoo.fmframework.log.entry.LogEntry;

public class JSONPersister {

	public void saveJSONtoFile(JSONObject obj, String path) {

		try (FileWriter file = new FileWriter(path)) {
			file.write(obj.toString());
			Logger.getLog().log(new LogEntry("save json").addArg("json saved"));
		} catch (IOException e) {
			Logger.getLog().logError(new ErrorEntry("save json").addArg("io exception"));
		}
		
	}
	
	public JSONObject readJSONFromFile(String path) {
		
		if (new File(path).isFile()) {
			File file = new File(path);
			return readJSONFromFile(file);
		}else {
			Logger.getLog().logError(new ErrorEntry("json load " + path).addArg("invalid path"));
		}
		
		return null;
	}

	public JSONObject readJSONFromFile(File file) {
		try {
			if (file.isFile()) {

				BufferedReader br = new BufferedReader(new FileReader(file));
				StringBuilder sb = new StringBuilder();
				String jsonLine = br.readLine();
				while (jsonLine != null) {
					sb.append(jsonLine);
					jsonLine = br.readLine();
				}

				br.close();

				String jsonString = sb.toString();

				JSONObject rootParsedJsonObj = new JSONObject(jsonString);

				return rootParsedJsonObj;

			}
		} catch (IOException e) {
			Logger.getLog().logError(new ErrorEntry("failed to load json " + file));
		}
		
		return null;
	}

}
