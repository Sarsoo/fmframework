package sarsoo.fmframework.config;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ConfigPersisterTest {

	@Test
	public void testWrite() {
		
		Config config = new Config();
		config.addVariable(new ConfigVariable("test", "test1"));
		
		ConfigPersister persister = new ConfigPersister();
		
		persister.saveConfig(".fm/config.json", config);
		
		assertTrue(true);
	}
	
	@Test
	public void testRead() {
		
		ConfigPersister persister = new ConfigPersister();
		
		Config config = persister.readConfig(".fm/config.json");

		System.out.println(config);
		assertTrue(true);
	}

}
