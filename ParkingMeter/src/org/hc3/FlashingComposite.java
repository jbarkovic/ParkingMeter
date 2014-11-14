package org.hc3;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class FlashingComposite extends Composite {
	private RGB defaultRGB = null;
	private RGB alternateRGB = null;
	private int time = 650;
	private boolean executeTimer = false;
	private Control [] children = null;
	private Color [] oldColors = null;
	private  FlashingComposite (Composite arg0, int arg1) {
		super(arg0, arg1);
	}
	private FlashingComposite (Composite arg0, int arg1, Color defaultBackground, Color alternate) {
		super(arg0, arg1);
		defaultRGB = (defaultBackground != null) ? defaultBackground.getRGB() : this.getBackground().getRGB();
		alternateRGB = alternate.getRGB();
		
		Runnable timer = new Runnable() {
	        public void run() {
	        	if (executeTimer) toggleBackground ();
	          getDisplay().timerExec(time, this);
	        }
	      };
	      getDisplay().timerExec(time, timer);
	}
	public void toggleBackground () {
		if (getBackground ().getRGB().equals(defaultRGB)) {
			children = this.getChildren();
			oldColors = new Color [children.length];
			setBackground (new Color (getDisplay (), alternateRGB));
			for (int i=0; i<children.length;i++) {
				oldColors [i] = children [i] .getBackground();					
			}
			for (int i=0; i<children.length;i++) {
				System.out.println("Child [" + i + "] is: " + children[i].handle + " , " + children[i].toString());
				children [i] .setBackground(getBackground());
				children [i].setBackground(new Color (getDisplay (), alternateRGB)); 
			}
		} else {
			for (int i=0; i<children.length;i++) {
				children [i].setBackground (oldColors [i]); 
			}
			setBackground (new Color (getDisplay (), defaultRGB));
		}
	}		
	public void startFlash () {
		executeTimer = true;
	}
	public void cancelFlash () {
		executeTimer = false;
		setBackground (new Color (getDisplay (), defaultRGB));
	}
	public static FlashingComposite setUpFlashingSlot (final Composite parent) {
		FlashingComposite fc = new FlashingComposite (parent, SWT.NO_SCROLL,Values.defaultBackground(parent.getDisplay()),new Color(parent.getDisplay (), Values.greenSecondary));
		fc.setBackground(parent.getDisplay().getSystemColor(SWT.COLOR_WHITE));
		FillLayout layout = new FillLayout ();		
		layout.marginHeight = 15;
		layout.marginWidth  = 15;
		fc.setLayout(layout);
		return fc;
	}
}