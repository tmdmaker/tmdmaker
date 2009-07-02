package jp.sourceforge.tmdmaker.dialog;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Button;

public class EntityNameAndTypeSettingPanel extends Composite {

	private Label identifierLabel = null;
	private Text inputIdentifierText = null;
	private Label typeLabel = null;
	private Group typeGroup = null;
	private Button resourceRadioButton = null;
	private Button eventRadioButton = null;

	public EntityNameAndTypeSettingPanel(Composite parent, int style) {
		super(parent, style);
		initialize();
		// TODO Auto-generated constructor stub
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        GridData gridData = new GridData();
        gridData.horizontalAlignment = GridData.FILL;
        gridData.grabExcessHorizontalSpace = true;
        gridData.widthHint = -1;
        gridData.verticalAlignment = GridData.CENTER;
        identifierLabel = new Label(this, SWT.NONE);
        identifierLabel.setText("個体指示子");
        inputIdentifierText = new Text(this, SWT.BORDER);
        inputIdentifierText.setLayoutData(gridData);
        typeLabel = new Label(this, SWT.NONE);
        typeLabel.setText("類別");
        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 2;
        this.setLayout(gridLayout);
        createTypeGroup();
        this.setSize(new Point(209, 74));
			
	}

	/**
	 * This method initializes typeGroup	
	 *
	 */
	private void createTypeGroup() {
		GridData gridData3 = new GridData();
		gridData3.grabExcessHorizontalSpace = true;
		GridData gridData2 = new GridData();
		gridData2.horizontalAlignment = GridData.FILL;
		gridData2.grabExcessHorizontalSpace = false;
		gridData2.verticalAlignment = GridData.CENTER;
		GridLayout gridLayout1 = new GridLayout();
		gridLayout1.numColumns = 2;
		gridLayout1.verticalSpacing = 15;
		GridData gridData1 = new GridData();
		gridData1.horizontalAlignment = GridData.FILL;
		gridData1.grabExcessHorizontalSpace = true;
		gridData1.grabExcessVerticalSpace = false;
		gridData1.verticalAlignment = GridData.CENTER;
		typeGroup = new Group(this, SWT.NONE);
		typeGroup.setLayoutData(gridData1);
		typeGroup.setLayout(gridLayout1);
		resourceRadioButton = new Button(typeGroup, SWT.RADIO);
		resourceRadioButton.setText("リソース");
		resourceRadioButton.setLayoutData(gridData3);
		resourceRadioButton
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						System.out.println("widgetSelected()"); // TODO Auto-generated Event stub widgetSelected()
					}
				});
		eventRadioButton = new Button(typeGroup, SWT.RADIO);
		eventRadioButton.setText("イベント");
		eventRadioButton.setLayoutData(gridData2);
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
