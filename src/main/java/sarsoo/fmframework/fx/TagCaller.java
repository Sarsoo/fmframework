package sarsoo.fmframework.fx;

import javafx.concurrent.*;
import sarsoo.fmframework.util.Getter;
import sarsoo.fmframework.util.Reference;

public class TagCaller extends Task{

	@Override
	protected Object call() throws Exception {

		return Getter.getUserTags(Reference.getUserName());
	}
}
