package org.hc3;
import java.util.Calendar;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Color;

import widgets.FlatButton;
import widgets.SashForm;


public class Receipt extends Shell {


	protected long id = -1;
	private String message = "Default";
	private String title = "Default";
	
	private Calendar endTime = null;
	private boolean usedCard = false;
	private long ticketId = 0;
	private Start.Ticket ourTicket = null;
	
	FlatButton insertIntoButton;
	private Receipt () {		
	}
	private Receipt (long id, boolean usedCard, Calendar parkUntil) {
		this.ticketId = id;
		this.usedCard = usedCard;
		this.endTime = parkUntil;
		//createContents();
	}
	public Receipt (Start.Ticket ticket) {
		this (ticket.id,ticket.usedCard,ticket.endTime);
		this.ourTicket = ticket;
		//createContents();
	}
	public void printReciept ( String title, String message) {
		this.setMessage(title,message);
		doOperration(true);
	}
	public static void printVoid ( String title, String message) {
		Receipt r = new Receipt ();
		
		r.setMessage(title,message);
		r.doOperration(false) ;
	}

	/**
	 * Open the window.
	 */
	private Calendar getEndTime () {
		return this.endTime;
	}
	private boolean usedCard () {
		return this.usedCard;
	}
	private long getId () {
		return this.ticketId;
	}
	private void setMessage (String title, String message) {
		this.title = title;
		this.message = message;
	}
	public void doOperration(boolean isTicket) {
		Display display = Display.getDefault();
		createContents(isTicket);
		open();
		layout();
//		while (!shlReceiptPrinter.isDisposed()) {
//			if (!display.readAndDispatch()) {
//				display.sleep();
//			}
//		}
	}
	public void close () {
		dispose();
	}

	
	/**
	 * Create contents of the window.
	 * @wbp.parser.entryPoint
	 */
	protected void createContents(boolean isTicket) {
		this.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		this.setSize(530, 295);
		this.setText("Receipt Printer");
		this.setLayout(new FillLayout(SWT.HORIZONTAL));
		setBackground(Values.defaultBackground(getDisplay()));
		
		SashForm sashForm = new SashForm(this, SWT.VERTICAL);
		sashForm.setBackground(Values.defaultBackground(getDisplay()));
		
		
		final Label lblTitle = new Label(sashForm, SWT.NONE);
		lblTitle.setBackground(Values.defaultBackground(getDisplay()));
		lblTitle.setFont(SWTResourceManager.getFont("Ubuntu", 28, SWT.NORMAL));
		lblTitle.setAlignment(SWT.CENTER);
		lblTitle.setText(this.title);
		
		
		StyledText styledTextReceiptDetails = new StyledText(sashForm, SWT.BORDER);
		styledTextReceiptDetails.setFont(SWTResourceManager.getFont("Ubuntu", 14, SWT.NORMAL));
		styledTextReceiptDetails.setText(this.message);
		styledTextReceiptDetails.setEditable(false);
		styledTextReceiptDetails.setEnabled(false);
		
		insertIntoButton = new FlatButton(sashForm, SWT.CENTER);
		insertIntoButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				Start.startRefundScreen (ourTicket);
				close ();
			}
		});
		insertIntoButton.setBackground(new Color (this.getDisplay(),50,255,160));
		insertIntoButton.setForeground(this.getDisplay ().getSystemColor(SWT.COLOR_WHITE));
		insertIntoButton.setText("Insert Into Machine");
		insertIntoButton.setFont(Start.changeFontSize(this.getDisplay (),insertIntoButton.getFont(),32));
		
		
		sashForm.setWeights(new int[] {2,7,3});

	}
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
