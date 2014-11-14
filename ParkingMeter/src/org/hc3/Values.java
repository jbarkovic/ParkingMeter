package org.hc3;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

public class Values {
	public static final int parkingRate = 15;
	public static String font = "Ubuntu";
	public static RGB greenPrimary = new RGB (50,255,160);
	public static RGB greenSecondary = new RGB (11,179,55);
	public static RGB greenBackgroundUnEnabled = new RGB (153,196,165); 
	public static RGB greenForegroundUnEnabled = new RGB (153,196,165); 
	public static Color defaultBackground (Display display) {
		return display.getSystemColor(SWT.COLOR_WHITE);
	}
	public static Color defaultButtonForeground (Display display) {
		return display.getSystemColor(SWT.COLOR_WHITE);
	}
	public static Color enabledFalseBackgroundColor (Display display) {
		return new Color (display, greenBackgroundUnEnabled);
	}
	public static Color enabledFalseForegroundColor (Display display) {
		return new Color (display, greenForegroundUnEnabled);
	}	
}