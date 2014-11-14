package org.hc3;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.wb.swt.SWTResourceManager;

import widgets.FlatButton;
import widgets.SashForm;


public class PaymentPanel extends Shell implements Updateable {
	private Label lblCashReturn;
	private Label lblCashInserted;
	private Label lblCardState;
	private SashForm sashFormCash;
	private SashForm sashBills;
	private SashForm sashCoins;
	private Button btnCardInsertButton;
	private Button btnCardRemoveButton;
	private Button btnTwentyDollars;
	private Button btnTenDollars;
	private Button btnFiveDollars;
	private Button btnToonie;
	private Button btnLoony;
	private Button btnQuarter;
	private Button btnDime;
	private Button btnNickle;
	private Button btnFifty;
	private Button btnCoinReturn;
	private Button btnEmptyCashReturn;
	private FlashingComposite cardContainer;
	private Label lblCashInsert;
	private SashForm sashForm_1;
	private FlashingComposite addCashContainer;
	private FlashingComposite cashReturnContainer;
	private Label label;
	private Label lblCashPanel;
	private Font buttonFont;
	/**
	 * Create the shell.
	 * @param display
	 */
	public PaymentPanel(Display display) {
		super(display);
		createContents();
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		this.setBackground(Values.defaultBackground(getDisplay()));
		final int width = 350;
		this.setSize(width, 600);
		this.setText("Payment Panel");
		setLayout(new FillLayout(SWT.HORIZONTAL));
		
		buttonFont = getDisplay ().getSystemFont();
		buttonFont = Start.changeFontSize(this.getDisplay (),buttonFont,13);
		SashForm sashForm = new SashForm(this, SWT.VERTICAL);
		sashForm.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		
		//sashCashReturn = new 
		cashReturnContainer = FlashingComposite.setUpFlashingSlot (sashForm);
		cashReturnContainer.setBackground(Values.defaultBackground(getDisplay()));
		
		SashForm sashCashReturn = new SashForm(cashReturnContainer,SWT.VERTICAL);
		sashCashReturn.setBackground(Values.defaultBackground(getDisplay()));
		
		lblCashReturn = generateLabel(sashCashReturn, SWT.NONE);
		lblCashReturn.setText("Cash Return: $00.00");
		lblCashReturn.setFont(Start.changeFontSize(this.getDisplay (),lblCashReturn.getFont(),14));
		lblCashReturn.setBackground(Values.defaultBackground(getDisplay()));
		
		sashForm_1 = new SashForm(sashCashReturn, SWT.NONE);
		sashForm_1.setBackground(Values.defaultBackground(getDisplay()));
		
		btnCoinReturn = new Button(sashForm_1, SWT.NONE);
		btnCoinReturn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent arg0) {
				Start.activateCoinReturn();
			}
		});
		btnCoinReturn.setText("Press Coin Return");
		
		btnEmptyCashReturn = new Button(sashForm_1, SWT.NONE);
		btnEmptyCashReturn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent arg0) {
				Start.emptyCoinReturn();
			}
		});
		btnEmptyCashReturn.setText("Take Cash");
		sashForm_1.setWeights(new int[] {1, 1});
		sashCashReturn.setWeights(new int[] {1, 1});
		
		lblCashPanel = generateLabel(sashForm, SWT.SEPARATOR | SWT.HORIZONTAL);
		
		cardContainer = FlashingComposite.setUpFlashingSlot (sashForm);
		SashForm sashCard = new SashForm (cardContainer,SWT.VERTICAL);
		sashCard.setBackground(Values.defaultBackground(getDisplay()));
		
		lblCardState = generateLabel(sashCard, SWT.NONE);
		lblCardState.setText("CardState");
		lblCardState.setFont(Start.changeFontSize(this.getDisplay (),lblCardState.getFont(),14));
		
		SashForm sashCardButtons = new SashForm (sashCard,SWT.HORIZONTAL);
		btnCardInsertButton = new Button(sashCardButtons, SWT.NONE);
		btnCardInsertButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				if (Start.cardInsertedFlag != 2) {
					Start.cardInsertedFlag = 1;
				}
			}
		});
		btnCardInsertButton.setText("Insert Card");
		
		btnCardRemoveButton = new Button(sashCardButtons, SWT.NONE);
		btnCardRemoveButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				if (Start.cardInsertedFlag != 2) {
					Start.cardInsertedFlag = 0;
				}
			}
		});
		btnCardRemoveButton.setText("Remove Card");
		sashCard.setWeights(new int[] {1, 1});
		
		lblCashPanel = generateLabel(sashForm, SWT.SEPARATOR | SWT.HORIZONTAL);
		

		addCashContainer = FlashingComposite.setUpFlashingSlot (sashForm);
		
		sashFormCash = new SashForm(addCashContainer, SWT.VERTICAL);
		sashFormCash.setBackground(Values.defaultBackground(getDisplay()));
		
		lblCashInserted = generateLabel(sashFormCash, SWT.NONE);
		lblCashInserted.setText("CashInserted");
		lblCashInserted.setFont(Start.changeFontSize(this.getDisplay (),lblCashInserted.getFont(),14));

		SashForm sashAddCash = new SashForm (sashFormCash, SWT.NONE);
		sashAddCash.setBackground(Values.defaultBackground(getDisplay()));
		StyledText instructions = new StyledText (sashAddCash, SWT.NONE);
		instructions.setBackground(Values.defaultBackground(getDisplay()));
		instructions.setText("Insert\nMoney:");
		
		sashBills = new SashForm(sashAddCash, SWT.VERTICAL);
		sashBills.setBackground(Values.defaultBackground(getDisplay()));
		
		btnTwentyDollars = new Button(sashBills, SWT.NONE);
		btnTwentyDollars.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				Start.cashInserted += 2000;
			}
		});
		btnTwentyDollars.setText("$20");
		
		btnTenDollars = new Button(sashBills, SWT.NONE);
		btnTenDollars.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				Start.cashInserted += 1000;
			}
		});
		btnTenDollars.setText("$10");
		
		btnFiveDollars = new Button(sashBills, SWT.NONE);
		btnFiveDollars.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				Start.cashInserted += 500;
			}
		});
		btnFiveDollars.setText("$5");
		
		btnToonie = new Button(sashBills, SWT.NONE);
		btnToonie.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				Start.cashInserted += 200;
			}
		});
		btnToonie.setText("$2");
		
		sashBills.setWeights(new int[] {1, 1, 1, 1});
		
		sashCoins = new SashForm(sashAddCash, SWT.VERTICAL);
		
		sashCoins.setBackground(Values.defaultBackground(getDisplay()));
		
		btnLoony = new Button(sashCoins, SWT.NONE);
		btnLoony.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				Start.cashInserted += 100;
			}
		});
		btnLoony.setText("$1");
		
		btnQuarter = new Button(sashCoins, SWT.NONE);
		btnQuarter.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				Start.cashInserted += 25;
			}
		});
		btnQuarter.setText("$0.25");
		
		btnDime = new Button(sashCoins, SWT.NONE);
		btnDime.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				Start.cashInserted += 10;
			}
		});
		btnDime.setText("$0.10");
		
		btnNickle = new Button(sashCoins, SWT.NONE);
		btnNickle.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				Start.cashInserted += 5;
			}
		});
		btnNickle.setText("$0.05");
		sashCoins.setWeights(new int[] {1, 1, 1, 1});
		sashAddCash.setWeights(new int[] {1, 1, 1});
		sashFormCash.setWeights(new int[] {1, 2});
		sashForm.setWeights(new int[] { 5, 1, 5, 1, 8});

	}
	public void updateItems () {
		long dollarsReturned = Start.cashReturned / 100;
		long centsReturned   = Start.cashReturned % 100;
		lblCashReturn.setText("  Cash Return Slot: \t$"+dollarsReturned+"."+centsReturned);
		
		long dollarsInserted = Start.cashContained / 100;
		long centsInserted   = Start.cashContained % 100;
		lblCashInserted.setText("  Cash Inserted: \t$"+dollarsInserted+"."+centsInserted);
		
		if (Start.cashReturned > 0) {
			cashReturnContainer.startFlash();
		} else {
			cashReturnContainer.cancelFlash();
		}
		
		if (Start.needCardPayment() && Start.cardInsertedFlag % 3 == 0) {
			cardContainer.startFlash();
		} else {
			cardContainer.cancelFlash();
		}	
		
		if (Start.needCashPayment()) {
			addCashContainer.startFlash();
		} else {
			addCashContainer.cancelFlash();
		}
		
		String cardState = "";
		String btnText = "";
		boolean newEnabledState = false;
		if (Start.cardInsertedFlag == 0) {
			cardState = "No Card Inserted";
			btnText = "Insert Card";
			newEnabledState = true;
		}
		else if (Start.cardInsertedFlag == 1) {
			cardState = "Card Inserted";
			btnText = "Eject Card";
			newEnabledState = true;
		}
		else if (Start.cardInsertedFlag == 2) {
			cardState = "Card Locked";
			btnText = "Card Locked";
			newEnabledState = false;
		}
		else if (Start.cardInsertedFlag == 3) {
			cardState = "Card Ejected";
			btnText = "Insert Card";
			newEnabledState = true;
		}
		else {
			cardState = "Card Error";
			newEnabledState = true;
		}
		//btnCardButton.setText(btnText);
		lblCardState.setText("  Card State: " + "\t" + cardState);
		//if (btnCardButton.getEnabled() != newEnabledState) btnCardButton.setEnabled(newEnabledState);
	}
	private Label generateLabel (Composite parent, int arg0) {		
		Label out = new Label (parent,arg0);
		out.setBackground(Values.defaultBackground(getDisplay()));
		out.setFont(buttonFont);
		return out; 
	}
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
	private class Button extends FlatButton {

		public Button(Composite parent, int style) {
			super(parent, style);
			this.setBackground(new Color (getDisplay (),Values.greenPrimary));
			if (buttonFont != null) this.setFont(buttonFont);
			// TODO Auto-generated constructor stub
		}
		
	}
}
