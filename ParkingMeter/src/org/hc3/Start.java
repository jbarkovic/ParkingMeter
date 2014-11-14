package org.hc3;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;


public class Start {
	private static Shell mainShell;
	static PaymentPanel paymentShell;
	private static STATE currentState = STATE.START_SCREEN;
	
	private static ArrayList<Ticket> knownTickets = new ArrayList<Ticket> ();
	protected static int cardInsertedFlag = 0; /* 0 => No Card, 1 => CardInsertedSignal, 2 => CardHeldByMachine*/ 
	protected static long cardRefund = 0; /* In Cents*/
	protected static long cashReturned = 0; /* In Cents*/
	protected static long cashInserted = 0; /* Used only to signal that money has been inserted, In Cents */
	protected static long cashContained = 0; /* Money that the machine has collected from user */
	
	protected static int minutes = 0;
	protected static int hours = 0;
	
	protected static double paymentDue = 0;
	
	protected static Ticket insertedTicket = null;
	private static Fragment composite;
	private static Fragment previousScreen;
	
	/**
	 * Launch the application.
	 * @param args
	 */
	private enum STATE {
		START_SCREEN, TIME_SELECT, PAYMENT_SELECT, CARD_PAY, CASH_PAY, REFUND
	}
	public Start () {
		//loadSwtJar();
		run ();
	}
	public static void main(String[] args) {
		new Start ();
	}
	private void run () {
		Display display = Display.getDefault();
		mainShell = new Shell(display);
		mainShell.setSize(420, 647);
		mainShell.setText("Parking Meter");
		mainShell.setLayout(new FillLayout(SWT.VERTICAL));
		mainShell.setBackground(Values.defaultBackground(display));
		composite = new StartScreen(mainShell, SWT.TOP);
	
		mainShell.open();
		mainShell.layout();
		paymentShell = new PaymentPanel (display);
		paymentShell.open();
		paymentShell.layout();
		while (!mainShell.isDisposed()) {
			if (cardInsertedFlag == 1) {				
				handleCardInsertion ();	
			}
			if (cashInserted > 0) {
				handleCashInsertion ();
			}			
			if (!display.readAndDispatch()) {
				composite.updateItems();
				paymentShell.updateItems();
				display.sleep();
			}
		}
	}
	private static void handleCardInsertion () {
		switch (currentState) {
		case START_SCREEN : {
			startTimeSelectScreen ();
			cardInsertedFlag = 2; // Card locked in machine now
			break;
		}
		case TIME_SELECT : {
			cardInsertedFlag = 3;
			break;
		}
		case CARD_PAY : {
			cardInsertedFlag = 2;
			break;
		}
		case CASH_PAY : {
			cardInsertedFlag = 3;
			break;
		}
		case REFUND : {
			cardInsertedFlag = 3;
			break;
		}
		default : {
			cardInsertedFlag = 3;
		}
		}
	}
	private static void handleCashInsertion () {
		switch (currentState) {
		case START_SCREEN : {
			cashReturned += cashInserted;
			break;
		}
		case TIME_SELECT : {
			cashReturned += cashInserted;
			break;			
		}
		case CARD_PAY : {
			cashReturned += cashInserted;
			break;			
		}
		case CASH_PAY : {
			cashContained += cashInserted;
			break;
		}
		case REFUND : {
			cashReturned += cashInserted;
			break;
		}
		default : {
			cashReturned += cashInserted;
		}
		}
		cashInserted = 0;		
	}
	protected static void startTimeSelectScreen () {
		System.out.println("Going to Time Selection Screen ...");
		switchScreens (new TimeSelectionScreen (mainShell, SWT.NONE));
		currentState = STATE.TIME_SELECT;
	}
	protected static void startNewTransaction () {
		switchScreens (new StartScreen (mainShell, SWT.NONE));
		insertedTicket = null;
		cardInsertedFlag = 0; /* 0 => No Card, 1 => CardInsertedSignal, 2 => CardHeldByMachine*/ 
		cardRefund = 0; /* In Cents*/
		cashReturned += cashContained + cashInserted; /* In Cents*/
		cashInserted = 0; /* Used only to signal that money has been inserted, In Cents */
		cashContained = 0;
		
		paymentDue = 0;
		
		minutes = 0;
		hours = 0;
		
		currentState = STATE.START_SCREEN;
	}
	protected static void startPayment () {		
		if (cardInsertedFlag == 2) {
			startCardPay ();
		} else {
			switchScreens (new PaymentMethodSelector (mainShell, SWT.NONE));
			currentState = STATE.PAYMENT_SELECT;
		}
	}
	protected static void startRefundScreen (Ticket ticket) {
		insertedTicket = ticket;
		switchScreens (new Refund (mainShell, SWT.NONE));
		System.out.println("Starting Refund");
		currentState = STATE.REFUND;
	}
	protected static void processRefund (long amount) {
		System.out.println("cashReturned: " + cashReturned);
		System.out.println("refundAmount: " + amount);
		cashReturned += amount;
		startNewTransaction ();
	}
	protected static void startCashPay () {
		currentState = STATE.CASH_PAY;
		switchScreens (new CashPayment (mainShell, SWT.NONE));
	}
	protected static void startErrorScreen () {
		previousScreen = composite;
		switchScreens (new ErrorMessage (mainShell, SWT.NONE, new String [] {"Please enter a nonzero\n time interval"}));
	}
	protected static void endErrorScreen (){
		previousScreen = composite;
		switchScreens (new TimeSelectionScreen (mainShell, SWT.NONE));
	}
	protected static void startCardPay () {
		currentState = STATE.CARD_PAY;
		switchScreens (new CardPayment (mainShell, SWT.NONE));
	}
	protected static void activateCoinReturn () {
		cashReturned += cashContained;
		cashContained = 0;		
	}
	protected static void emptyCoinReturn () {
		cashReturned = 0;
	}
	protected static void leaveTransaction(boolean isSuccess) {
		Calendar parkUntil = Calendar.getInstance();
		parkUntil.add(Calendar.MINUTE, minutes);
		parkUntil.add(Calendar.HOUR, hours);
		Calendar current = Calendar.getInstance();
		final Date currentDate = new Date (current.getTimeInMillis());
		final Date parkUntilDate = new Date (parkUntil.getTimeInMillis());
		final DateFormat df = DateFormat.getDateTimeInstance();
		
		if (cardInsertedFlag > 0) cardInsertedFlag = 3;
		paymentShell.updateItems();
		final Ticket newTicket = new Ticket ();
		newTicket.id = current.getTimeInMillis()*100;
		while (knownTickets.contains(newTicket)) {
			newTicket.id ++;
		}
		newTicket.usedCard = currentState == STATE.CARD_PAY;
		newTicket.endTime = parkUntil;	
		if (isSuccess) {
		    mainShell.getDisplay ().asyncExec(new Runnable() {
		          public void run() {
		
					System.out.println("Creating Ticket");
		  			Receipt receipt = new Receipt  (newTicket);
					receipt.printReciept("Ticket" , "\nTicket ID: " + newTicket.id + "\n\nTransaction Time: " + df.format(currentDate) + "\n\nYou can park until: " + df.format(parkUntilDate));
				//	receipt.layout();
		          }
		        });
			System.out.println("Printed Ticket");
			if (currentState != STATE.CARD_PAY) {
				cashReturned = cashContained - convertDoubleCash (paymentDue);
				System.out.println("Returning : " + cashReturned + " : From Calc: " + (cashContained - convertDoubleCash (paymentDue)));
				cashContained = 0;
			}
			knownTickets.add (newTicket);
		} else {
			Receipt.printVoid("Voided", "\n\nTransaction Canceled\n\nTransaction Time: " + df.format(currentDate));
		}

		System.out.println ("Ending Transaction");
		if (currentState != STATE.REFUND ) startNewTransaction ();
	}
	protected static boolean needCardPayment () {
		return currentState == STATE.CARD_PAY;
	}	
	protected static boolean needCashPayment () {
		return currentState == STATE.CASH_PAY;
	}
	private static void switchScreens (Fragment newScreen) {
		System.out.println("Switching Screens from: " + composite.toString() + " to: " + newScreen.toString());
		composite.dispose();
		composite = newScreen;
		mainShell.layout();
	}
	public static Font changeFontSize (Display display, Font font, int newSize) {
		FontData[] fD = font.getFontData();
		fD[0].setHeight(newSize);
		return new Font(display,fD[0]);
	}
	protected static double convertLongCash (long moneyInCents) {
		double out = moneyInCents / 100.0;
		return out;
	}
	protected static long convertDoubleCash (double moneyInDollars) {
		long out = (long) (moneyInDollars * 100.0);
		return out;
	}
	protected static double calculatePayment (int [] time) {
		if (time.length > 2) return 0;
		return ( Values.parkingRate*((double)time[0] + ((double)time[1]/60)));
	}
	public static class Ticket {
		public long id;
		public boolean usedCard;
		public Calendar endTime;
		@Override
		public boolean equals (Object obj) {
			if (obj instanceof Ticket) {
				return (((Ticket) obj).id == this.id);
			}
			return false;
		}
	}
}
