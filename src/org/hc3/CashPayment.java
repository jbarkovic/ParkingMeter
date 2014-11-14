package org.hc3;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;

import widgets.FlatButton;
import org.eclipse.swt.widgets.Group;


public class CashPayment extends Fragment {
	private Label lblRequired;
	private Label lblBalance;
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public CashPayment(Composite parent, int style) {
		super(parent, style);
		setLayout(new FillLayout(SWT.HORIZONTAL));
		setBackground(Values.defaultBackground(getDisplay()));
		
		Group group = new Group(this, SWT.NONE);
		
		group.setLayout(new FillLayout ());
		FlatButton fltbtnCancel = new FlatButton(group, SWT.CENTER);
		fltbtnCancel.setSize(225, 59);
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
		
		lblBalance = new Label(group, SWT.LEFT);
		lblBalance.setSize(225, 74);
		lblBalance.setFont(Start.changeFontSize(this.getDisplay (),lblBalance.getFont(),22));
		lblBalance.setBackground(Values.defaultBackground(getDisplay()));
		
		lblRequired = new Label(group, SWT.LEFT);
		lblRequired.setSize(225, 100);
		lblRequired.setFont(Start.changeFontSize(this.getDisplay (),lblRequired.getFont(),22));
		lblRequired.setBackground(Values.defaultBackground(getDisplay()));
		Label lblTitle = new Label(group, SWT.NONE);
		lblTitle.setSize(225, 300);
		lblTitle.setAlignment(SWT.CENTER);
		lblTitle.setFont(SWTResourceManager.getFont("Ubuntu", 26, SWT.NORMAL));
		lblTitle.setText("Cash Payment");
		lblTitle.setBackground(Values.defaultBackground(getDisplay()));

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
	
	@Override
	public void updateItems() {
		String required = String.format("Payment Due: \t$%2.2f", Start.paymentDue);

		lblRequired.setText(required);
		
		long dollars = Start.cashContained/100;
		long cents   = Start.cashContained%100;
		String balanceMsg = "Balance: \t$"+dollars+"."+cents;
		lblBalance.setText(balanceMsg);
				
		
		double balance = (dollars) + ((double)cents/100);
		if (balance >= Start.paymentDue) {
			Start.leaveTransaction(true);
		}
	}

}
