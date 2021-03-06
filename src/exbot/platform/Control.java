package exbot.platform;

import controller.Controller;
import exbot.dev.core.interfaces.Operator;
import exbot.platform.devices.AppFinder;
import exbot.platform.devices.USBWatcher;
import exbot.platform.devices.tables.LookupTableWrapper;
import exbot.platform.download.Path;

public class Control {

	public static void main(String[] args) throws InterruptedException {
		
		LookupTableWrapper.initTable(Path.lookupTablePath);
		new USBWatcher();
		
		Operator control = new Controller("control");
		AppFinder.registOperator(control);
		control.run();
	}
}