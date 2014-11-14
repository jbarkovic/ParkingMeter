package widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.hc3.Start;
import org.hc3.Values;

public class FlatButton extends Canvas{

    private int mouse = 0;
    private boolean hit = false; 
    private String text = "";
    
	public FlatButton(Composite parent, int style) {
        super(parent, SWT.NONE);        
        this.addPaintListener(new PaintListener() {
            public void paintControl(PaintEvent e) {
                Rectangle rect = ((Canvas) e.widget).getClientArea();
                Color fillColor = getBackgroundColor();
                Color borderColor = new Color (getDisplay (),Values.greenSecondary);
                RGB fillRGB = fillColor.getRGB();
                Color selectColor = new Color (e.display, fillRGB.red-20,fillRGB.green-20,fillRGB.blue-20);
                switch (mouse) {
                case 0:
                    // Default state
                    break;
                case 1:
                	System.out.println("Button enabled = " + getEnabled ());
                	System.out.println("Button enabled = " + isEnabled ());
                    // Mouse over
                    break;
                case 2:
                    // Mouse down
                	if (!isEnabled()) return; fillColor = selectColor;
                    break;
                }
                
                if (isEnabled ()) {
                	e.gc.setBackground(fillColor);
                } else {
                	e.gc.setBackground(Values.enabledFalseBackgroundColor(getDisplay()));
                }
                
                e.gc.fillRectangle(rect);
                int textWidth = e.gc.stringExtent(text).x; 
                int textHeight = e.gc.stringExtent(text).y;
                e.gc.drawText(text,rect.width/2 - textWidth/2, rect.height/2 - textHeight/2);
                e.gc.dispose();               
            }       
        });
        this.addMouseMoveListener(new MouseMoveListener() {
            public void mouseMove(MouseEvent e) {
            	if (!isEnabled()) return; 
                if (!hit)
                    return;
                mouse = 2;
                if (e.x < 0 || e.y < 0 || e.x > getBounds().width
                        || e.y > getBounds().height) {
                    mouse = 0;
                }
                redraw();
            }
        });
        this.addMouseTrackListener(new MouseTrackAdapter() {
            public void mouseEnter(MouseEvent e) {
            	if (!isEnabled()) return; 
                mouse = 1;
                redraw();
            }

            public void mouseExit(MouseEvent e) {
            	if (!isEnabled()) return; 
                mouse = 0;
                redraw();
            }
        });
        this.addMouseListener(new MouseAdapter() {
            public void mouseDown(MouseEvent e) {
            	if (!isEnabled()) return; 
                hit = true;
                mouse = 2;
                redraw();
            }

            public void mouseUp(MouseEvent e) {
            	if (!isEnabled()) return; 
                hit = false;
                mouse = 1;
                if (e.x < 0 || e.y < 0 || e.x > getBounds().width
                        || e.y > getBounds().height) {
                    mouse = 0;
                }
                redraw();
                if (mouse == 1)
                    notifyListeners(SWT.Selection, new Event());
            }
        });
        this.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
            	if (!isEnabled()) return; 
                if (e.keyCode == '\r' || e.character == ' ') {
                    Event event = new Event();
                    if (isEnabled()) notifyListeners(SWT.Selection, event);
                }
            }
        });
        this.setForeground(getDisplay ().getSystemColor(SWT.COLOR_WHITE));
    	setFont(Start.changeFontSize(this.getDisplay (),this.getFont(),15));
    }
	@Override
	public void setEnabled (boolean value) {
		this.setBackground(Values.enabledFalseBackgroundColor(getDisplay()));
		redraw ();
		super.setEnabled(value);
	}
    public Color getBackgroundColor() {
		return this.getBackground();
	}
    public void setText (String text) {
    	this.text = text;
    }
    public String getText () {
    	return this.text;
    }

	public void setBackgroundColor(Color backgroundColor) {
		this.setBackground(backgroundColor);
	}

    public Color getForegroundColor() {
    	return this.getForeground();
	}
    
	public void setForegroundColor(Color foregroundColor) {
		this.setForeground(foregroundColor);
	}
}
