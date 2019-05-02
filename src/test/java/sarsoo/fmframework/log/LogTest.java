package sarsoo.fmframework.log;

import static org.junit.Assert.*;

import org.junit.Test;

import sarsoo.fmframework.log.entry.ErrorEntry;
import sarsoo.fmframework.log.entry.InfoEntry;
import sarsoo.fmframework.log.entry.LogEntry;

public class LogTest {

	@Test
	public void testDump() {
		
		Log log = Logger.getLog();
		
		log.log(new LogEntry("log test"));
		log.logInfo(new InfoEntry("log test"));
		log.logError(new ErrorEntry("log test"));
		
		log.dumpLog();
		log.dumpInfoLog();
		log.dumpErrorLog();
		
		assertTrue(true);
	}

}
