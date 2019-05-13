package sarsoo.fmframework.config;

import java.util.ArrayList;
import java.util.List;

public class Config {

	protected List<ConfigVariable> variables;

	public Config() {
		this.variables = new ArrayList<ConfigVariable>();
	}

	public Config(ArrayList<ConfigVariable> variables) {
		for (ConfigVariable i : variables) {
			addVariable(i);
		}
	}

	public Config addVariable(ConfigVariable variable) {
		for (ConfigVariable i : variables) {
			if (variable.getKey().equalsIgnoreCase(i.getKey())) {
				i.setValue(variable.getValue());

				for (VariableListener j : variable.getListeners()) {
					i.addListener(j);
				}

				return this;
			}
		}

		variables.add(variable);

		return this;

	}

	public ConfigVariable getVariable(String key) {

		for (ConfigVariable i : variables) {
			if (i.getKey().equalsIgnoreCase(key)) {
				return i;
			}
		}

		return null;
	}

	public List<ConfigVariable> getVariables() {
		return variables;
	}

	public String getValue(String key) {
		ConfigVariable variable = getVariable(key);

		if (variable != null) {
			return variable.getValue();
		}

		return null;
	}
	
	public String toString() {
		String string = "config:";
		
		for(ConfigVariable i: variables) {
			string += " " + i.getKey() + " " + i.getValue(); 
		}
		
		return string;
	}

}
