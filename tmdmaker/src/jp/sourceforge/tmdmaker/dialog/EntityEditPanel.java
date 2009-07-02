package jp.sourceforge.tmdmaker.dialog;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

public class EntityEditPanel extends Composite {

	private TabFolder tabFolder = null;
	public EntityEditPanel(Composite parent, int style) {
		super(parent, style);
		initialize();
	}

	private void initialize() {
		FillLayout fillLayout = new FillLayout();
		fillLayout.type = org.eclipse.swt.SWT.HORIZONTAL;
		createTabFolder();
		this.setLayout(fillLayout);
		setSize(new Point(300, 200));
	}

	/**
	 * This method initializes tabFolder	
	 *
	 */
	private void createTabFolder() {
		tabFolder = new TabFolder(this, SWT.NONE);
		TabItem tabItem1 = new TabItem(tabFolder, SWT.NULL);
		tabItem1.setText("エンティティ");
		TabItem tabItem2 = new TabItem(tabFolder, SWT.NULL);
		tabItem2.setText("アトリビュート");
		TabItem tabItem3 = new TabItem(tabFolder, SWT.NULL);
		tabItem3.setText("リレーションシップ");
	}

}
