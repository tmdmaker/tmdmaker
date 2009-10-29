package jp.sourceforge.tmdmaker.dialog.component;

import jp.sourceforge.tmdmaker.dialog.AttributeDialog;
import jp.sourceforge.tmdmaker.model.EditAttribute;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;

public class DetailIdentifierSettingPanel extends Composite {
	private EditAttribute editIdentifier = null;  //  @jve:decl-index=0:
	private Label identifierLabel = null;
	private Text identifierText = null;
	private Button descButton = null;

	public DetailIdentifierSettingPanel(Composite parent, int style) {
		super(parent, style);
		initialize();
	}

	private void initialize() {
		GridData gridData1 = new GridData();
		gridData1.horizontalAlignment = GridData.FILL;
		gridData1.widthHint = 60;
		gridData1.grabExcessHorizontalSpace = false;
		gridData1.verticalAlignment = GridData.CENTER;
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.widthHint = -1;
		gridData.verticalAlignment = GridData.CENTER;
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 3;
		identifierLabel = new Label(this, SWT.NONE);
		identifierLabel.setText("個体指示子");
		identifierText = new Text(this, SWT.BORDER);
		identifierText.setLayoutData(gridData);
		identifierText.addModifyListener(new org.eclipse.swt.events.ModifyListener() {
			public void modifyText(org.eclipse.swt.events.ModifyEvent e) {
				System.out.println("modifyText()"); // TODO Auto-generated Event stub modifyText()
				editIdentifier.setName(((Text)e.widget).getText());
			}
		});
		descButton = new Button(this, SWT.NONE);
		descButton.setText("詳細");
		descButton.setLayoutData(gridData1);
		descButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				System.out.println("widgetSelected()"); // TODO Auto-generated Event stub widgetSelected()
				AttributeDialog dialog = new AttributeDialog(getShell(), editIdentifier);
				if (dialog.open() == Dialog.OK) {
					EditAttribute edited = dialog.getEditedValue();
					if (edited.isEdited()) {
						identifierText.setText(edited.getName());
					}
				}
			}
		});
		this.setLayout(gridLayout);
		setSize(new Point(321, 32));
	}
	public void setIdentifierName(String name) {
		identifierText.setText(name);
	}
	public String getIdentifierName() {
		return identifierText.getText();
	}

	/**
	 * @return the editIdentifier
	 */
	public EditAttribute getEditIdentifier() {
		return editIdentifier;
	}

	/**
	 * @param editIdentifier the editIdentifier to set
	 */
	public void setEditIdentifier(EditAttribute editIdentifier) {
		this.editIdentifier = editIdentifier;
	}
	
}  //  @jve:decl-index=0:visual-constraint="0,0"
