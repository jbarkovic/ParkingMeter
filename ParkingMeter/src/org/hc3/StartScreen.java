package org.hc3;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ScrollBar;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import widgets.FlatButton;
import org.hc3.Values;

public class StartScreen extends Fragment implements Updateable {
	private static Table table;
	private SashForm sashForm;
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public StartScreen(Composite parent, int style) {
		super(parent, style);
		createContents (parent);
	}

	private void createContents (Composite parent) {
		this.setLayout(new FillLayout(SWT.VERTICAL));
		this.setBackground(Values.defaultBackground(getDisplay()));
		sashForm = new SashForm(this, SWT.VERTICAL);
		sashForm.setBackground(Values.defaultBackground(getDisplay()));
		sashForm.setLocation(0, 0);
		
		setupRates (sashForm);
		//Canvas drw = new Canvas (sashForm,SWT.NO_BACKGROUND);
		/** Set up Buttons **/
		
		
		FlatButton fltbtnPurchaseButton = new FlatButton(sashForm, SWT.CENTER);
		fltbtnPurchaseButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				Start.startTimeSelectScreen ();
			}
		});
		fltbtnPurchaseButton.setBackground(new Color (this.getDisplay(),50,255,160));
		fltbtnPurchaseButton.setForeground(this.getDisplay ().getSystemColor(SWT.COLOR_WHITE));
		fltbtnPurchaseButton.setText(StartScreen.LocalValues.purchaseTicketButtonLabel);
		fltbtnPurchaseButton.setFont(Start.changeFontSize(this.getDisplay (),fltbtnPurchaseButton.getFont(),32));
		
		FlatButton fltbtnRefundButton = new FlatButton(sashForm, SWT.CENTER);
		fltbtnRefundButton.setBackground(new Color (this.getDisplay (),50,255,160));
		fltbtnRefundButton.setForeground(this.getDisplay ().getSystemColor(SWT.COLOR_WHITE));
		fltbtnRefundButton.setText(StartScreen.LocalValues.refundTicketButtonLabel);
		fltbtnRefundButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				Start.startRefundScreen(null);
			}
		});
		fltbtnRefundButton.setFont(Start.changeFontSize(this.getDisplay (),fltbtnRefundButton.getFont(),32));
			
		sashForm.setWeights(new int[] {3, 2, 2});
	}
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
	private void setupRates (Composite parent) {
		
		final SashForm sashFormTable = new SashForm(parent, SWT.NONE);
		sashFormTable.setEnabled(false);
		//final Composite sashFormTable = (Composite) parent;
		/**
		 *  Sources:
		 *  http://git.eclipse.org/c/platform/eclipse.platform.swt.git/tree/examples/org.eclipse.swt.snippets/src/org/eclipse/swt/snippets/Snippet77.java
		 */
		//StyledText styledText = new StyledText(parent, SWT.BORDER);
	 

		table = new Table(sashFormTable, SWT.NONE);		
		table.setBackground(Values.defaultBackground(getDisplay()));
		//table.setForeground(Values.defaultBackground(getDisplay()));
		//table.setLinesVisible(true);


		
		//table.setBounds(10,10,150,200);
		table.setFont(Start.changeFontSize(getDisplay(), table.getFont(), 20));
		final TableColumn [] columns = new TableColumn [StartScreen.LocalValues.rates[0].length];
		for (int i=0; i < StartScreen.LocalValues.rates [0].length;i++) {			
			columns [i] = new TableColumn (table,SWT.CENTER);			
		}
		int maxTotalWidth = 1;
		for (int i = 0; i < StartScreen.LocalValues.rates.length; i++) {
			int width = 0;
			for (int j=0; j < StartScreen.LocalValues.rates [0].length;j++) {
				width += StartScreen.LocalValues.rates[i][j].length ();
			}
			maxTotalWidth = Math.max (maxTotalWidth, width);
			TableItem item = new TableItem(table, SWT.NONE);
			item.setText(StartScreen.LocalValues.rates [i]);
			item.setBackground(Values.defaultBackground(getDisplay()));			
			
		}
		final int totalWidth = maxTotalWidth;
		table.addListener(SWT.MeasureItem, new Listener() {
				public void handleEvent(Event event) {
					int clientWidth = table.getClientArea().width;
					event.height = event.gc.getFontMetrics().getHeight() * 2;
					event.width = clientWidth * 2;
				}
		});
		sashFormTable.addControlListener(new ControlAdapter() {
			@Override
			public void controlResized(ControlEvent e) {
				Rectangle area = sashFormTable.getClientArea();
				Point size = table.computeSize(SWT.DEFAULT, SWT.DEFAULT);
				ScrollBar vBar = table.getVerticalBar();
				int width = area.width - table.computeTrim(0,0,0,0).width - vBar.getSize().x;
				if (size.y > area.height + table.getHeaderHeight()) {
					// Subtract the scrollbar width from the total column width
					// if a vertical scrollbar will be required
					Point vBarSize = vBar.getSize();
					width -= vBarSize.x;
				}
				Point oldSize = table.getSize();
				if (oldSize.x > area.width) {
					// table is getting smaller so make the columns 
					// smaller first and then resize the table to
					// match the client area width
					int textWidth = 0;
					for (int col=0;col<columns.length;col++) {
						for (int row=0;row<StartScreen.LocalValues.rates.length;row++) {
							textWidth = Math.max(textWidth,StartScreen.LocalValues.rates [row][col].length());
						}
						System.out.println("Text Width: " + textWidth);
						System.out.println("Total Width: " + totalWidth);
						System.out.println("Area Width: " + area.width);
						System.out.println("Scaled width: " + (int)(area.width * ( textWidth / (double) totalWidth)-1));

						columns[col].setWidth((int)(area.width * ( textWidth / (double) totalWidth)-1));
					}
					table.setSize(area.width, area.height);
				} else {
					// table is getting bigger so make the table 
					// bigger first and then make the columns wider
					// to match the client area width
					table.setSize(area.width, area.height);
					int textWidth = 0;
					for (int col=0;col<columns.length;col++) {
						for (int row=0;row<StartScreen.LocalValues.rates.length;row++) {
							textWidth = Math.max(textWidth,StartScreen.LocalValues.rates [row][col].length());
						}
						System.out.println("Text Width: " + textWidth);
						System.out.println("Total Width: " + totalWidth);
						System.out.println("Scaled width: " + (int)(area.width * ( textWidth / (double) totalWidth)-1));
						System.out.println("Area Width: " + area.width);
						columns[col].setWidth((int)(area.width * ( textWidth / (double) totalWidth)-1));
					}
				}
			}
		});
	}
	@Override
	public void updateItems() {
		// TODO Auto-generated method stub
		
	}
	private static class LocalValues {
		static final String ratesLabel = "Rates";
		static final String purchaseTicketButtonLabel = "Purchase Ticket";
		static final String refundTicketButtonLabel = "Refund";
		static final String INSTRUCTIONS = "Insert Ticket For refund,\n Or click " + purchaseTicketButtonLabel + " to buy a ticket";
		static final String [][] rates = new String [][] {
			{"Mon - Fri:", "$15.00 per hour"},
			{"Sat - Sun:", "$9.00  per hour"}};	
	}

}
