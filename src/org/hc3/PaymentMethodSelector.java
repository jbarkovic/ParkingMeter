package org.hc3;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;

import widgets.FlatButton;
import widgets.SashForm;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.wb.swt.SWTResourceManager;


public class PaymentMethodSelector extends Fragment {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public PaymentMethodSelector(Composite parent, int style) {
		super(parent, style);
		setLayout(new FillLayout(SWT.HORIZONTAL));
		setBackground(Values.defaultBackground(getDisplay()));
		SashForm sashForm = new SashForm(this, SWT.VERTICAL);
		
		sashForm.setBackground(Values.defaultBackground(getDisplay()));
		Label lblNewLabel = new Label(sashForm, SWT.NONE);
		lblNewLabel.setAlignment(SWT.CENTER);
		lblNewLabel.setFont(SWTResourceManager.getFont("Ubuntu", 26, SWT.NORMAL));
		lblNewLabel.setText("Payment Method");
		lblNewLabel.setBackground(Values.defaultBackground(getDisplay()));
		
		FlatButton fltbtnCash = new FlatButton(sashForm, SWT.CENTER);
		fltbtnCash.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				Start.startCashPay();
			}
		});
		fltbtnCash.setBackground(new Color (this.getDisplay(),50,255,160));
		fltbtnCash.setForeground(this.getDisplay ().getSystemColor(SWT.COLOR_WHITE));
		fltbtnCash.setText("Cash");
		fltbtnCash.setFont(Start.changeFontSize(this.getDisplay (),fltbtnCash.getFont(),32));
		
		FlatButton fltbtnCard = new FlatButton(sashForm, SWT.CENTER);
		fltbtnCard.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				Start.startCardPay();
			}
		});
		fltbtnCard.setBackground(new Color (this.getDisplay(),50,255,160));
		fltbtnCard.setForeground(this.getDisplay ().getSystemColor(SWT.COLOR_WHITE));
		fltbtnCard.setText("Card");
		fltbtnCard.setFont(Start.changeFontSize(this.getDisplay (),fltbtnCard.getFont(),32));
		
		sashForm.setWeights(new int[] {1,3,3});

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
