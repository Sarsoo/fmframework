package sarsoo.fmframework.log.console;

public class STDOutConsole implements Console {

	@Override
	public void write(String string) {
		System.out.println(string);
	}

}
