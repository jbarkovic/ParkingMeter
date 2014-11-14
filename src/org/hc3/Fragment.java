package org.hc3;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;


public class Fragment extends Composite implements Updateable {

	public Fragment(Composite parent, int style) {
		super(parent, style);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void updateItems() {
		// TODO Auto-generated method stub
		
	}
	protected void setBackgroundOfChildren (Control child) {
		Composite node = this;
		if (child != null) {
			node = (Composite) child;
		}
		if (child instanceof Composite){
			for (Control ctrl : node.getChildren() ) {
				ctrl.setBackground(Values.defaultBackground(node.getDisplay()));
				setBackgroundOfChildren (ctrl);
			}
		} else {
			return;
		}

	}
	
}
