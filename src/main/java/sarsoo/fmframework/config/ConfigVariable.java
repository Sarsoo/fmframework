package sarsoo.fmframework.config;

import java.util.ArrayList;
import java.util.List;

public class ConfigVariable {
	
	protected String key;
	protected String value;
	
	protected boolean temporary;
	
	protected List<VariableListener> listeners;
	
	public ConfigVariable(String key, String value) {
		this.key = key;
		this.value = value;
		this.temporary = false;
		
		this.listeners = new ArrayList<VariableListener>();
	}
	
	public ConfigVariable(String key, String value, boolean temporary) {
		this.key = key;
		this.value = value;
		this.temporary = temporary;
		
		this.listeners = new ArrayList<VariableListener>();
	}
	
	public String getKey() {
		return key;
	}
	
	public String getValue() {
		return value;
	}
	
	public boolean isTemporary() {
		return temporary;
	}
	
	public void setTemporary(boolean temporary) {
		this.temporary = temporary;
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
