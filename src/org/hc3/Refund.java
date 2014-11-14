package org.hc3;

import java.util.Calendar;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;

import widgets.FlatButton;

public class Refund extends Fragment {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	long refundAmount = 0;
	Label lblFundsAvailable;
	Label lblTimeRemaining;
	
	FlatButton fltbtnPurchaseButton;
	
	public Refund(Composite parent, int style) {
		super(parent, style);
		setLayout(new FillLayout(SWT.HORIZONTAL));
		this.setBackground(Values.defaultBackground(getDisplay()));
		
		SashForm sashForm = new SashForm(this, SWT.VERTICAL);
		sashForm.setBackground(Values.defaultBackground(getDisplay()));
		
		lblTimeRemaining = new Label(sashForm, SWT.NONE);
		lblTimeRemaining.setFont(SWTResourceManager.getFont("Ubuntu", 19, SWT.NORMAL));
		lblTimeRemaining.setText("Time Remaining:");
		lblTimeRemaining.setBackground(Values.defaultBackground(getDisplay()));
		
		
		lblFundsAvailable = new Label(sashForm, SWT.NONE);
		lblFundsAvailable.setFont(SWTResourceManager.getFont("Ubuntu", 19, SWT.NORMAL));
		lblFundsAvailable.setText("Funds:");
		
		lblFundsAvailable.setBackground(Values.defaultBackground(getDisplay()));
		fltbtnPurchaseButton = new FlatButton(sashForm, SWT.CENTER);
		fltbtnPurchaseButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				Start.processRefund(refundAmount);
			}
		});
		fltbtnPurchaseButton.setBackground(new Color (this.getDisplay(),Values.greenPrimary));
		fltbtnPurchaseButton.setForeground(this.getDisplay ().getSystemColor(SWT.COLOR_WHITE));
		fltbtnPurchaseButton.setText("Process Refund");
		fltbtnPurchaseButton.setFont(Start.changeFontSize(this.getDisplay (),fltbtnPurchaseButton.getFont(),32));

		
		FlatButton fltbtnCancelButton = new FlatButton(sashForm, SWT.CENTER);
		fltbtnCancelButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				Start.startNewTransaction();
			}
		});
		fltbtnCancelButton.setBackground(new Color (this.getDisplay(),Values.greenPrimary));
		fltbtnCancelButton.setForeground(this.getDisplay ().getSystemColor(SWT.COLOR_WHITE));
		fltbtnCancelButton.setText("Cancel");
		fltbtnCancelButton.setFont(Start.changeFontSize(this.getDisplay (),fltbtnCancelButton.getFont(),32));
		
		
		sashForm.setWeights(new int[] {2,2,3,3});
		System.out.println("Starting refund screen");
		
		this.setBackgroundOfChildren(null);
	}

	@Override
	public void updateItems () {
		if (Start.insertedTicket == null) {
			lblTimeRemaining.setText("Please Insert Ticket");
			lblFundsAvailable.setText("For Refund");
			fltbtnPurchaseButton.setVisible(false);
			fltbtnPurchaseButton.setEnabled(false);		
		} else if (Start.insertedTicket.endTime.compareTo(Calendar.getInstance()) >= 0) {
			if (!fltbtnPurchaseButton.getEnabled()) fltbtnPurchaseButton.setEnabled(true);
			if (!fltbtnPurchaseButton.getVisible()) fltbtnPurchaseButton.setVisible(true);
			long diff = Start.insertedTicket.endTime.getTimeInMillis() - Calendar.getInstance().getTimeInMillis() ;
			long diffMinutes = diff / (60 * 1000) % 60;
			long diffHours = diff / (60 * 60 * 1000) % 24;
			long diffDays = diff / (24 * 60 * 60 * 1000);
		
			long totalHours = (diffDays > 0) ? diffDays*24 + diffHours : diffHours;
			double refundDouble = Start.calculatePayment(new int [] {(int) totalHours, (int) diffMinutes});
			refundAmount = Start.convertDoubleCash(refundDouble);
			lblTimeRemaining.setText("Time Remaining:  " + diffDays + " D, " + diffHours + " H, " + diffMinutes + " M");
			String fundsMsg = String.format("Refund Available \t%2.2f", refundDouble);
			lblFundsAvailable.setText(fundsMsg);
		} else {
			lblTimeRemaining.setText("Time Order Error:");
			lblFundsAvailable.setText("Are you a time traveler?");
		}
	}
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
