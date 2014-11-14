package org.hc3;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;

import widgets.FlatButton;
import widgets.SashForm;



public class CardPayment extends Fragment {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public CardPayment(Composite parent, int style) {
		super(parent, style);
		setLayout(new FillLayout(SWT.VERTICAL));
		setBackground(Values.defaultBackground(getDisplay()));
		
//		SashForm sashForm = new SashForm(this, SWT.VERTICAL|SWT.NO_SCROLL);
//		sashForm.setBackground(Values.defaultBackground(getDisplay()));
		
		
		Label lblNewLabel = new Label(this, SWT.CENTER);
		lblNewLabel.setFont(SWTResourceManager.getFont("Ubuntu", 26, SWT.NORMAL));
		lblNewLabel.setText("Card Payment");
		lblNewLabel.setBackground(Values.defaultBackground(getDisplay()));
		
		//sashForm.setDragDetect(false);
		FillLayout fl = new FillLayout ();
		//sashForm.setLayout(fl);
		FlatButton fltbtnPurchaseButton = new FlatButton(this, SWT.CENTER|SWT.NO_SCROLL);
		fltbtnPurchaseButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				if (Start.cardInsertedFlag != 2) return;
				Start.leaveTransaction(true);
			}
		});
		
		fltbtnPurchaseButton.setBackground(new Color (this.getDisplay(),50,255,160));
		fltbtnPurchaseButton.setForeground(this.getDisplay ().getSystemColor(SWT.COLOR_WHITE));
		fltbtnPurchaseButton.setText("Payyy");
		fltbtnPurchaseButton.setFont(Start.changeFontSize(this.getDisplay (),fltbtnPurchaseButton.getFont(),24));
		
		FlatButton fltbtnCancel = new FlatButton(this, SWT.CENTER);
		fltbtnCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				Start.leaveTransaction(false);
			}
		});
		fltbtnCancel.setBackground(new Color (this.getDisplay(),50,255,160));
		fltbtnCancel.setForeground(this.getDisplay ().getSystemColor(SWT.COLOR_WHITE));
		fltbtnCancel.setText("Cancel");
		fltbtnCancel.setFont(Start.changeFontSize(this.getDisplay (),fltbtnCancel.getFont(),26));

		//sashForm.setWeights(new int[] {2, 3,1});
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
