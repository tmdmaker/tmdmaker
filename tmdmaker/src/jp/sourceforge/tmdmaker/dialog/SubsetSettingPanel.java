package jp.sourceforge.tmdmaker.dialog;

import java.util.List;

import jp.sourceforge.tmdmaker.model.Attribute;
import jp.sourceforge.tmdmaker.model.SubsetEntity;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

public class SubsetSettingPanel extends Composite {

	private Label partitionCodeLabel = null;
	private Combo partitionSelectCombo = null;
	private Group typeGroup = null;
	private Button sameRadioButton = null;
	private Button differenceRadioButton = null;
	private Button nullCheckBox = null;
	private Composite subsetContainerComposite = null;
	private Table subsetTable = null;
	private Composite subsetControlComposite = null;
	private Button newButton = null;
	private Button deleteButton = null;
	public SubsetSettingPanel(Composite parent, int style) {
		super(parent, style);
		initialize();
	}

	private void initialize() {
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		partitionCodeLabel = new Label(this, SWT.NONE);
		partitionCodeLabel.setText("区分コード");
		this.setLayout(gridLayout);
		createPartitionSelectCombo();
		createTypeGroup();
		createSubsetContainerComposite();
		this.setSize(new Point(208, 150));
	}

	/**
	 * This method initializes partitionSelectCombo	
	 *
	 */
	private void createPartitionSelectCombo() {
		GridData gridData3 = new GridData();
		gridData3.horizontalAlignment = GridData.FILL;
		gridData3.verticalAlignment = GridData.CENTER;
		partitionSelectCombo = new Combo(this, SWT.NONE);
		partitionSelectCombo.setLayoutData(gridData3);
	}

	/**
	 * This method initializes typeGroup	
	 *
	 */
	private void createTypeGroup() {
		GridLayout gridLayout1 = new GridLayout();
		gridLayout1.numColumns = 3;
		GridData gridData = new GridData();
		gridData.horizontalSpan = 2;
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = GridData.CENTER;
		gridData.grabExcessHorizontalSpace = false;
		typeGroup = new Group(this, SWT.NONE);
		typeGroup.setText("サブセット種類");
		typeGroup.setLayout(gridLayout1);
		typeGroup.setLayoutData(gridData);
		sameRadioButton = new Button(typeGroup, SWT.RADIO);
		sameRadioButton.setText("同一");
		sameRadioButton
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						System.out.println("widgetSelected()"); // TODO Auto-generated Event stub widgetSelected()
						nullCheckBox.setSelection(false);
						nullCheckBox.setEnabled(false);
					}
				});
		differenceRadioButton = new Button(typeGroup, SWT.RADIO);
		differenceRadioButton.setText("相違");
		differenceRadioButton
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						System.out.println("widgetSelected()"); // TODO Auto-generated Event stub widgetSelected()
						nullCheckBox.setEnabled(true);
					}
				});
		nullCheckBox = new Button(typeGroup, SWT.CHECK);
		nullCheckBox.setText("NULLを排除");
	}

	/**
	 * This method initializes subsetContainerComposite	
	 *
	 */
	private void createSubsetContainerComposite() {
		GridData gridData2 = new GridData();
		gridData2.horizontalAlignment = GridData.FILL;
		gridData2.verticalAlignment = GridData.FILL;
		GridLayout gridLayout2 = new GridLayout();
		gridLayout2.numColumns = 2;
		GridData gridData1 = new GridData();
		gridData1.horizontalSpan = 2;
		gridData1.verticalAlignment = GridData.CENTER;
		gridData1.horizontalAlignment = GridData.FILL;
		subsetContainerComposite = new Composite(this, SWT.NONE);
		subsetContainerComposite.setLayoutData(gridData1);
		subsetContainerComposite.setLayout(gridLayout2);
		subsetTable = new Table(subsetContainerComposite, SWT.NONE);
		subsetTable.setHeaderVisible(true);
		subsetTable.setLayoutData(gridData2);
		subsetTable.setLinesVisible(true);
		createSubsetControlComposite();
		TableColumn tableColumn = new TableColumn(subsetTable, SWT.NONE);
		tableColumn.setWidth(120);
		tableColumn.setText("サブセット名");
	}

	/**
	 * This method initializes subsetControlComposite	
	 *
	 */
	private void createSubsetControlComposite() {
		subsetControlComposite = new Composite(subsetContainerComposite, SWT.NONE);
		subsetControlComposite.setLayout(new GridLayout());
		newButton = new Button(subsetControlComposite, SWT.NONE);
		newButton.setText("新規");
		deleteButton = new Button(subsetControlComposite, SWT.NONE);
		deleteButton.setText("削除");
	}

	public void initializeValue(boolean sameType, boolean exceptNull, List<Attribute> attributes, List<SubsetEntity> subsets, Attribute selectedAttribute) {
		if (sameType) {
			this.sameRadioButton.setSelection(true);
		} else {
			this.differenceRadioButton.setSelection(true);
		}
		this.nullCheckBox.setSelection(exceptNull);
		for (Attribute a : attributes) {
			this.partitionSelectCombo.add(a.getName());
			if (a.equals(selectedAttribute)) {
				this.partitionSelectCombo.select(this.partitionSelectCombo.getItemCount() - 1);
			}
		}
		for (SubsetEntity se : subsets) {
			
		}
	}
}  //  @jve:decl-index=0:visual-constraint="0,0"
