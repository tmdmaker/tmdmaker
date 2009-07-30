package jp.sourceforge.tmdmaker.dialog;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.FillLayout;

public class ReUseKeysEditPanel extends Composite {

	private Table reUseKeyTable = null;

	public ReUseKeysEditPanel(Composite parent, int style) {
		super(parent, style);
		initialize();
	}

	private void initialize() {
		reUseKeyTable = new Table(this, SWT.NONE);
		reUseKeyTable.setHeaderVisible(true);
		reUseKeyTable.setLinesVisible(true);
		TableColumn tableColumn = new TableColumn(reUseKeyTable, SWT.NONE);
		tableColumn.setWidth(100);
		tableColumn.setText("Re-Used属性名");
		TableColumn tableColumn1 = new TableColumn(reUseKeyTable, SWT.NONE);
		tableColumn1.setWidth(150);
		tableColumn1.setText("移送元");
		this.setLayout(new FillLayout());
		this.setSize(new Point(260, 146));
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
