package sarsoo.fmframework.cache;

import java.util.ArrayList;

import sarsoo.fmframework.config.Config;
import sarsoo.fmframework.fm.FmUserNetwork;
import sarsoo.fmframework.fx.FmFramework;
import sarsoo.fmframework.util.FMObjList;

public class TagPool {

	private static TagPool instance;
	
	private ArrayList<FMObjList> tagList;

	private TagPool() {
		tagList = new ArrayList<FMObjList>();
	}

//	public static synchronized TagPool getInstance() {
//		if (instance == null) {
//			instance = new TagPool();
//		}
//		return instance;
//	}

	public static TagPool getPool() {
		if (instance == null) {
			synchronized (TagPool.class) {
				if (instance == null) {
					instance = new TagPool();
				}
			}
		}
		return instance;
	}

	public FMObjList getTag(String name) {
		System.out.println("gettag " + name);
		int i;
		boolean containedInPool = false;
		for (i = 0; i < tagList.size(); i++) {
			if (tagList.get(i).getGroupName().equals(name)) {
				containedInPool = true;
				System.out.println("found in pool");

				break;
			}
		}

		if (containedInPool) {
			System.out.println("returned from pool");
			return tagList.get(i);
		} else {
			Config config = FmFramework.getSessionConfig();

			FmUserNetwork net = new FmUserNetwork(config.getValue("api_key"), config.getValue("username"));
			FMObjList tag = net.getTag(name);
			tagList.add(tag);
			System.out.println("pulling tag");
			return tag;
		}
	}

	public void flush() {
		tagList.clear();
	}

}
