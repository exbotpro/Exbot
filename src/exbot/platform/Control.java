package exbot.platform;

import exbot.dev.core.interfaces.Operator;
import exbot.example.controllers.Tracker;
import exbot.platform.devices.AppFinder;
import exbot.platform.devices.USBWatcher;
import exbot.platform.devices.tables.LookupTableWrapper;

public class Control {

	public static void main(String[] args) throws InterruptedException {
		
		LookupTableWrapper.initTable();
		new USBWatcher();
		
		Operator tracker = new Tracker("tracker");
		AppFinder.registOperator(tracker);
		tracker.run();
		
	}
}
