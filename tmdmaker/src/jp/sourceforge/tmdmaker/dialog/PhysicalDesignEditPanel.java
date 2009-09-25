package jp.sourceforge.tmdmaker.dialog;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;

public class PhysicalDesignEditPanel extends Composite {

	private Label physicalTableNameLabel = null;
	private Text physicalTableNametText = null;
	private Table columnTable = null;

	public PhysicalDesignEditPanel(Composite parent, int style) {
		super(parent, style);
		initialize();
	}

	private void initialize() {
		GridData gridData1 = new GridData();
		gridData1.grabExcessHorizontalSpace = false;
		gridData1.verticalAlignment = GridData.CENTER;
		gridData1.horizontalAlignment = GridData.FILL;
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = false;
		gridData.horizontalSpan = 2;
		gridData.horizontalIndent = 0;
		gridData.heightHint = 200;
		gridData.verticalAlignment = GridData.CENTER;
		physicalTableNameLabel = new Label(this, SWT.NONE);
		physicalTableNameLabel.setText("物理名");
		physicalTableNametText = new Text(this, SWT.BORDER);
		physicalTableNametText.setLayoutData(gridData1);
		columnTable = new Table(this, SWT.NONE);
		columnTable.setHeaderVisible(true);
		columnTable.setLayoutData(gridData);
		columnTable.setLinesVisible(true);
		TableColumn tableColumn = new TableColumn(columnTable, SWT.NONE);
		tableColumn.setWidth(150);
		tableColumn.setText("性質");
		TableColumn tableColumn1 = new TableColumn(columnTable, SWT.NONE);
		tableColumn1.setWidth(150);
		tableColumn1.setText("物理名");
		TableColumn tableColumn2 = new TableColumn(columnTable, SWT.NONE);
		tableColumn2.setWidth(50);
		tableColumn2.setText("型");
		TableColumn tableColumn21 = new TableColumn(columnTable, SWT.NONE);
		tableColumn21.setWidth(40);
		tableColumn21.setText("長さ");
		TableColumn tableColumn31 = new TableColumn(columnTable, SWT.NONE);
		tableColumn31.setWidth(40);
		tableColumn31.setText("少数");
		TableColumn tableColumn3 = new TableColumn(columnTable, SWT.NONE);
		tableColumn3.setWidth(40);
		tableColumn3.setText("Null");
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 3;
		this.setLayout(gridLayout);
		this.setSize(new Point(501, 249));
	}

}  //  @jve:decl-index=0:visual-constraint="-18,-16"
