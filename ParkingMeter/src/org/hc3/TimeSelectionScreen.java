package org.hc3;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import widgets.FlatButton;

import org.eclipse.wb.swt.SWTResourceManager;



public class TimeSelectionScreen extends Fragment implements Updateable {
	private Text textHours;
	private Text textMin;
	private Label lblCost;	
	
	private static final int MIN_MINUTE_INCREMENT = 15;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public TimeSelectionScreen(Composite parent, int style) {
		super(parent, style);
		createContents (parent);
	}
	private void createContents (Composite parent) {
		this.setLayout(new FillLayout(SWT.VERTICAL));
		this.setBackground(Values.defaultBackground(getDisplay()));
		SashForm sashForm = new SashForm(this, SWT.VERTICAL);
		sashForm.setLocation(0, 0);
		sashForm.setBackground(Values.defaultBackground(getDisplay()));
		
		Label lblTitle = new Label(sashForm, SWT.CENTER);
		lblTitle.setText("Select Parking Time");
		lblTitle.setFont(Start.changeFontSize(this.getDisplay (),lblTitle.getFont(),26));
		lblTitle.setBackground(Values.defaultBackground(getDisplay()));
		
		lblCost = new Label(sashForm, SWT.CENTER);	
		lblCost.setFont(Start.changeFontSize(this.getDisplay (),lblCost.getFont(),20));
		lblCost.setBackground(Values.defaultBackground(getDisplay()));
		
		Composite doneButtonContainer = new Composite (sashForm, SWT.NO_SCROLL);
		doneButtonContainer.setBackground(Values.defaultBackground(getDisplay()));
		FillLayout layout = new FillLayout ();		
		layout.marginHeight = 15;
		layout.marginWidth = 30;
		doneButtonContainer.setLayout(layout);
		FlatButton fltbtnDone = generateButton (doneButtonContainer, "Done");	
		fltbtnDone.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				if(Start.hours > 0 || Start.minutes > 0){
					Start.paymentDue = Start.calculatePayment (new int [] {Start.hours,Start.minutes});
					Start.startPayment();
				}
				else{
					Start.startErrorScreen();
				}
			}
		});
		FlashingComposite buttonContainer = FlashingComposite.setUpFlashingSlot(sashForm);
		SashForm sashFormTimeChoose = new SashForm(buttonContainer, SWT.NONE);

		
		sashFormTimeChoose.setBackground(Values.defaultBackground(getDisplay()));
		//SashForm sashFormHours = new SashForm(sashFormTimeChoose, SWT.VERTICAL);
		
		  // Create the SashForm
	    Composite containerHours = new Composite(sashFormTimeChoose, SWT.NONE);
	    containerHours.setBackground(Values.defaultBackground(getDisplay()));
	    containerHours.setLayout(new FillLayout());
	    containerHours.setLayoutData(new GridData(GridData.FILL_BOTH));
	    final SashForm sashFormHours = new SashForm(containerHours, SWT.VERTICAL);
	    sashFormHours.setBackground(Values.defaultBackground(getDisplay()));

		Label lblHours = new Label(sashFormHours, SWT.NONE);
		lblHours.setBackground(Values.defaultBackground(getDisplay()));
		lblHours.setAlignment(SWT.CENTER);
		lblHours.setFont(Start.changeFontSize(this.getDisplay (),lblHours.getFont(),18));
		lblHours.setText("Hours");
		
		generateButton (sashFormHours, "+").addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				Start.hours++;
				Start.hours = Start.hours % 60;
			}
		});	

		textHours = new Text(sashFormHours,  SWT.READ_ONLY | SWT.CENTER);
		textHours.setFont(Start.changeFontSize(this.getDisplay (),textHours.getFont(),35));
		textHours.setBackground(Values.defaultBackground(getDisplay()));
		
		generateButton (sashFormHours, "-").addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				Start.hours--;
				Start.hours = Math.max(0, Start.hours);
			}
		});
		buttonContainer = FlashingComposite.setUpFlashingSlot(sashForm);

		FlatButton fltbtnCancel = new FlatButton(buttonContainer, SWT.CENTER);
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
		sashFormHours.setWeights(new int[] {2, 4, 3, 4});
		
		  // Create the SashForm
	    Composite containerMinutes = new Composite(sashFormTimeChoose, SWT.NONE);
	    containerMinutes.setBackground(Values.defaultBackground(getDisplay()));
	    containerMinutes.setLayout(new FillLayout());
	    containerMinutes.setLayoutData(new GridData(GridData.FILL_BOTH));
	    final SashForm sashFormMinutes = new SashForm(containerMinutes, SWT.VERTICAL);
	    sashFormMinutes.setBackground(Values.defaultBackground(getDisplay()));
		
		Label lblMinutes = new Label(sashFormMinutes, SWT.NONE);
		lblMinutes.setBackground(Values.defaultBackground(getDisplay()));
		lblMinutes.setFont(Start.changeFontSize(this.getDisplay (),lblMinutes.getFont(),18));
		lblMinutes.setAlignment(SWT.CENTER);
		lblMinutes.setText("Minutes");
		generateButton (sashFormMinutes, "+").addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				Start.minutes += MIN_MINUTE_INCREMENT;
				Start.hours += Start.minutes / 60;
				Start.hours = Start.hours % 60;
				Start.minutes = Start.minutes % 60;
			}
		});	
	
		textMin = new Text(sashFormMinutes, SWT.READ_ONLY | SWT.CENTER);
		textMin.setBackground(Values.defaultBackground(getDisplay()));
		textMin.setFont(Start.changeFontSize(this.getDisplay (),textMin.getFont(),35));		
		generateButton (sashFormMinutes, "-").addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				Start.minutes -= MIN_MINUTE_INCREMENT ;
				if (Start.minutes < 0) {
					if (Start.hours > 0) {
						Start.minutes += 60;
						Start.hours--;
					}
					
				}
				Start.minutes = Math.max(Start.minutes, 0);
			}
		});
		sashFormMinutes.setWeights(new int[] {2, 4,3,4});
		sashFormTimeChoose.setWeights(new int[] {1, 1});
		sashForm.setWeights(new int[] {3, 3, 3, 10, 3});

	}
	private FlatButton generateButton (Composite parent, String label) {		
		FlatButton fltbtnPurchaseButton = new FlatButton(parent, SWT.CENTER);
	
		fltbtnPurchaseButton.setBackground(new Color (this.getDisplay(),50,255,160));
		fltbtnPurchaseButton.setForeground(this.getDisplay ().getSystemColor(SWT.COLOR_WHITE));
		fltbtnPurchaseButton.setText(label);
		fltbtnPurchaseButton.setFont(Start.changeFontSize(this.getDisplay (),fltbtnPurchaseButton.getFont(),24));
		return fltbtnPurchaseButton;
	}
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}	
	@Override
	public void updateItems() {
		String label = String.format("Cost: %2.2f",Start.calculatePayment(new int [] {Start.hours,Start.minutes}));
		lblCost.setText(label);
		if(!textMin.getText().equals(Integer.toString(Start.minutes))){
			textMin.setText(Integer.toString(Start.minutes));
		}
		if(!textHours.getText().equals(Integer.toString(Start.hours))){
			textHours.setText(Integer.toString(Start.hours));
		}
	}
}
