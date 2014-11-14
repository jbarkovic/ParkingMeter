package org.hc3;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.FillLayout;

import widgets.FlatButton;

import org.eclipse.swt.layout.RowLayout;
import org.eclipse.wb.swt.SWTResourceManager;

public class ErrorMessage extends Fragment {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public ErrorMessage(Composite parent, int style, String[] argument) {
		super(parent, style);
		setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		setLayout(new FillLayout(SWT.HORIZONTAL));
		
		SashForm sashForm = new SashForm(this, SWT.VERTICAL);
		sashForm.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		
		Label lblErrorMessage = new Label(sashForm, SWT.CENTER);
		lblErrorMessage.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblErrorMessage.setFont(Start.changeFontSize(this.getDisplay (),this.getFont(),18));
		lblErrorMessage.setText(argument[0]);
		sashForm.setWeights(new int[] {1});
		
		FlashingComposite buttonContainer = FlashingComposite.setUpFlashingSlot(sashForm);
		FlatButton fltbtnClose = new FlatButton(buttonContainer, SWT.CENTER);
		fltbtnClose.setSize(225, 59);
		fltbtnClose.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				Start.endErrorScreen();
			}
		});
		fltbtnClose.setBackground(new Color (this.getDisplay(),50,255,160));
		fltbtnClose.setForeground(this.getDisplay ().getSystemColor(SWT.COLOR_WHITE));
		fltbtnClose.setText("Close");
		fltbtnClose.setFont(Start.changeFontSize(this.getDisplay (),fltbtnClose.getFont(),26));

	}
	
	@Override
	public void updateItems(){
		
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
