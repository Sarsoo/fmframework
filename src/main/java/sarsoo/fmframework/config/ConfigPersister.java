package sarsoo.fmframework.config;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import sarsoo.fmframework.file.JSONPersister;
import sarsoo.fmframework.log.Logger;
import sarsoo.fmframework.log.entry.ErrorEntry;
import sarsoo.fmframework.log.entry.InfoEntry;
import sarsoo.fmframework.log.entry.LogEntry;

public class ConfigPersister {

	protected String variableTag = "vars";

	public Config readConfig(String path) {

		Logger.getLog().log(new LogEntry("read config").addArg(path));

		JSONPersister persist = new JSONPersister();

		JSONObject obj = persist.readJSONFromFile(path + "config.json");

		Config config = new Config();

		if (obj != null) {
			JSONArray array = obj.getJSONArray(variableTag);

			for (int i = 0; i < array.length(); i++) {
				JSONObject var = (JSONObject) array.get(i);

				String key = var.keys().next();

				config.addVariable(new ConfigVariable(key, var.getString(key)));

//				Logger.getLog().logInfo(
//						new InfoEntry("read config").addArg(path).addArg("inserted " + key + " " + var.getString(key)));
			}
		} else {
			Logger.getLog().logError(new ErrorEntry("read config").addArg(path).addArg("null json read"));
		}

		return config;

	}

	public void saveConfig(String path, Config config) {

		JSONObject object = new JSONObject();

		JSONArray array = new JSONArray();

		List<ConfigVariable> variables = config.getVariables();

		for (ConfigVariable i : variables) {
			if (!i.isTemporary()) {
				JSONObject obj = new JSONObject();
				obj.put(i.getKey(), i.getValue());
				array.put(obj);
			}
		}

		object.put(variableTag, array);

		JSONPersister persist = new JSONPersister();

		persist.saveJSONtoFile(object, path + "config.json");

	}

}
