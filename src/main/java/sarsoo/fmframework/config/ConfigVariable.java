package sarsoo.fmframework.config;

import java.util.ArrayList;
import java.util.List;

public class ConfigVariable {
	
	protected String key;
	protected String value;
	
	protected List<VariableListener> listeners;
	
	public ConfigVariable(String key, String value) {
		this.key = key;
		this.value = value;
		
		this.listeners = new ArrayList<VariableListener>();
	}
	
	public String getKey() {
		return key;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		
		this.value = value;
		
		VariableEvent event = new VariableEvent(this);
		
		for(VariableListener i: listeners) {
			i.listen(event);
		}
	}
	
	public void addListener(VariableListener listener) {
		listeners.add(listener);
	}

	public List<VariableListener> getListeners() {
		return listeners;
	}

}
