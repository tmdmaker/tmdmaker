package jp.sourceforge.tmdmaker.dialog;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

public class EntityAttributeSettingPanel extends Composite {
	private int selectedIndex;
	private Table attributeTable = null;
	private Button upButton = null;
	private Button downButton = null;
	private Button addButton = null;
	private Button deleteButton = null;
	public EntityAttributeSettingPanel(Composite parent, int style) {
		super(parent, style);
		initialize();
	}

	private void initialize() {
		GridData gridData1 = new GridData();
		gridData1.grabExcessHorizontalSpace = false;
		gridData1.verticalAlignment = GridData.CENTER;
		gridData1.horizontalAlignment = GridData.BEGINNING;
		GridData gridData = new GridData();
		gridData.grabExcessVerticalSpace = false;
		gridData.grabExcessHorizontalSpace = false;
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = GridData.FILL;
		gridData.verticalSpan = 4;
		gridData.horizontalIndent = 0;
		attributeTable = new Table(this, SWT.NONE);
		upButton = new Button(this, SWT.NONE);
		upButton.setText("上へ");
		downButton = new Button(this, SWT.NONE);
		downButton.setText("下へ");
		downButton.setLayoutData(gridData1);
		addButton = new Button(this, SWT.NONE);
		addButton.setText("追加");
		deleteButton = new Button(this, SWT.NONE);
		deleteButton.setText("削除");
		attributeTable.setHeaderVisible(true);
		attributeTable.setLayoutData(gridData);
		attributeTable.setLinesVisible(true);
		attributeTable
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						selectedIndex = attributeTable.getSelectionIndex();
					}
				});
		TableColumn tableColumn = new TableColumn(attributeTable, SWT.NONE);
		tableColumn.setWidth(60);
		tableColumn.setText("性質名");
		TableColumn tableColumn1 = new TableColumn(attributeTable, SWT.NONE);
		tableColumn1.setWidth(60);
		tableColumn1.setText("型");
		TableColumn tableColumn2 = new TableColumn(attributeTable, SWT.NONE);
		TableColumn tableColumn3 = new TableColumn(attributeTable, SWT.NONE);
		tableColumn3.setWidth(40);
		tableColumn3.setText("精度");
		tableColumn2.setWidth(40);
		tableColumn2.setText("長さ");
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		gridLayout.horizontalSpacing = 5;
		gridLayout.marginWidth = 0;
		gridLayout.marginHeight = 0;
		gridLayout.verticalSpacing = 10;
		this.setLayout(gridLayout);
		this.setSize(new Point(257, 118));
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
