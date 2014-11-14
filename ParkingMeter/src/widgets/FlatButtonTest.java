package widgets;


import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.custom.*;
import org.hc3.Start;

public class FlatButtonTest {

	protected Shell shell;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			FlatButtonTest window = new FlatButtonTest();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(450, 300);
		shell.setText("Flat Button Test");
		shell.setLayout(new FillLayout ());
		
		SashForm sashForm = new SashForm (shell, SWT.NO_SCROLL);
		
//		Composite doneButtonContainer = new Composite (sashForm, SWT.NO_SCROLL);
//		FillLayout layout = new FillLayout ();		
//		layout.marginHeight = 15;
//		layout.marginWidth 	= 30;
//		doneButtonContainer.setLayout(layout);
		
		
//		FlatButton fltbtnRefundButton = new FlatButton(sashForm, SWT.CENTER);
//		fltbtnRefundButton.setBackground(new Color (shell.getDisplay (),50,255,160));
//		fltbtnRefundButton.setForeground(shell.getDisplay ().getSystemColor(SWT.COLOR_WHITE));
//		fltbtnRefundButton.setText("Test");
//		fltbtnRefundButton.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseUp(MouseEvent e) {
//				System.out.println("Test Button Clicked");
//			}
//		});
		Label lblTest = new Label (sashForm, SWT.NONE);
		lblTest.setText("Test Label");
		//fltbtnRefundButton.setFont(Start.changeFontSize(shell.getDisplay (),fltbtnRefundButton.getFont(),32));
			

	}

}
