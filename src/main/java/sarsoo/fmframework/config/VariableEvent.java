package sarsoo.fmframework.config;

public class VariableEvent {
	
	protected ConfigVariable variable;
	
	public VariableEvent(ConfigVariable variable) {
		
		this.variable = variable;
		
	}
	
	public ConfigVariable getVariable() {
		return variable;
	}

}
