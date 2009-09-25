package jp.sourceforge.tmdmaker.dialog;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.layout.GridData;

public class AttributePanel extends Composite {

	private Label descLabel = null;
	private Text descText = null;
	private Label accessLabel = null;
	private Text accessText = null;
	private Text validationRuleTextArea = null;
	private Label validationRuleLabel = null;
	private Label lockLabel = null;
	private Text lockTextArea = null;
	private Label dataLabel = null;
	private Combo dataCombo = null;
	private Label derivationRuleLabel = null;
	private Text derivationRuleTextArea = null;
	public AttributePanel(Composite parent, int style) {
		super(parent, style);
		initialize();
	}

	private void initialize() {
		GridData gridData9 = new GridData();
		gridData9.widthHint = -1;
		gridData9.verticalAlignment = GridData.CENTER;
		gridData9.horizontalAlignment = GridData.FILL;
		GridData gridData8 = new GridData();
		gridData8.widthHint = -1;
		gridData8.verticalAlignment = GridData.CENTER;
		gridData8.horizontalAlignment = GridData.FILL;
		GridData gridData7 = new GridData();
		gridData7.heightHint = 30;
		gridData7.verticalAlignment = GridData.FILL;
		gridData7.horizontalAlignment = GridData.FILL;
		GridData gridData5 = new GridData();
		gridData5.horizontalAlignment = GridData.FILL;
		gridData5.heightHint = 30;
		gridData5.verticalAlignment = GridData.FILL;
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = false;
		gridData.heightHint = 30;
		gridData.widthHint = 200;
		gridData.verticalAlignment = GridData.FILL;
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		descLabel = new Label(this, SWT.NONE);
		descLabel.setText("論理名");
		descText = new Text(this, SWT.BORDER);
		descText.setLayoutData(gridData8);
		accessLabel = new Label(this, SWT.NONE);
		accessLabel.setText("物理名");
		accessText = new Text(this, SWT.BORDER);
		accessText.setLayoutData(gridData9);
		dataLabel = new Label(this, SWT.NONE);
		dataLabel.setText("データ属性");
		createDataCombo();
		validationRuleLabel = new Label(this, SWT.NONE);
		validationRuleLabel.setText("前提");
		validationRuleTextArea = new Text(this, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.BORDER);
		validationRuleTextArea.setLayoutData(gridData);
		lockLabel = new Label(this, SWT.NONE);
		lockLabel.setText("機密性");
		lockTextArea = new Text(this, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.BORDER);
		lockTextArea.setLayoutData(gridData5);
		derivationRuleLabel = new Label(this, SWT.NONE);
		derivationRuleLabel.setText("計算式");
		derivationRuleTextArea = new Text(this, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.BORDER);
		derivationRuleTextArea.setLayoutData(gridData7);
		this.setLayout(gridLayout);
		this.setSize(new Point(289, 199));
	}

	/**
	 * This method initializes dataCombo	
	 *
	 */
	private void createDataCombo() {
		GridData gridData1 = new GridData();
		gridData1.widthHint = -1;
		gridData1.verticalAlignment = GridData.CENTER;
		gridData1.horizontalAlignment = GridData.FILL;
		dataCombo = new Combo(this, SWT.NONE);
		dataCombo.setLayoutData(gridData1);
	}

}  //  @jve:decl-index=0:visual-constraint="0,0"
