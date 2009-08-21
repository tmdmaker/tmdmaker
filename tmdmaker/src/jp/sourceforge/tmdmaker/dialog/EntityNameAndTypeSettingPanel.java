package jp.sourceforge.tmdmaker.dialog;

import jp.sourceforge.tmdmaker.model.EntityType;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class EntityNameAndTypeSettingPanel extends Composite {

	private Label identifierLabel = null;
	private Text identifierText = null;
	private Label typeLabel = null;
	private Combo typeCombo = null;
	private Button nameAutoCreateCheckBox = null;
	private Label nameLabel = null;
	private Text nameText = null;
	public EntityNameAndTypeSettingPanel(Composite parent, int style) {
		super(parent, style);
		initialize();
		this.nameAutoCreateCheckBox.setSelection(true);
		this.nameText.setEnabled(false);
		this.typeCombo.select(0);
	}
	public void initializeValue(String identifierName, String entityName, EntityType type) {
		setIdentifierNameText(identifierName);
//		this.identifierText.setText(identifierName);
		setEntityNameText(entityName);
//		this.nameText.setText(entityName);
		selectEntityTypeCombo(type);
//		if (EntityType.RESOURCE.equals(type)) {
//			this.typeCombo.select(0);
//		} else {
//			this.typeCombo.select(1);			
//		}
		selectAutoCreateCheckBox(identifierName, entityName);
//		String autoCreateEntityName = createEntityName(identifierName);
//		if (autoCreateEntityName.equals(entityName)) {
//			this.nameAutoCreateCheckBox.setSelection(true);
//			this.nameText.setEnabled(false);
//		} else {
//			this.nameAutoCreateCheckBox.setSelection(false);
//			this.nameText.setEnabled(true);
//		}
	}
	public void setEntityNameText(String entityName) {
		this.nameText.setText(entityName);		
	}
	public void setIdentifierNameText(String identifierName) {
		this.identifierText.setText(identifierName);		
	}
	public void selectAutoCreateCheckBox(String identifierName, String entityName) {
		String autoCreateEntityName = createEntityName(identifierName);
		if (autoCreateEntityName.equals(entityName)) {
			this.nameAutoCreateCheckBox.setSelection(true);
			this.nameText.setEnabled(false);
		} else {
			this.nameAutoCreateCheckBox.setSelection(false);
			this.nameText.setEnabled(true);
		}		
	}
	public void selectEntityTypeCombo(EntityType type) {
		if (EntityType.RESOURCE.equals(type)) {
			this.typeCombo.select(0);
		} else {
			this.typeCombo.select(1);			
		}
	}
	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        GridData gridData1 = new GridData();
        gridData1.grabExcessHorizontalSpace = false;
        gridData1.verticalAlignment = GridData.CENTER;
        gridData1.horizontalAlignment = GridData.FILL;
        GridData gridData = new GridData();
        gridData.horizontalAlignment = GridData.FILL;
        gridData.grabExcessHorizontalSpace = true;
        gridData.widthHint = -1;
        gridData.verticalAlignment = GridData.CENTER;
        identifierLabel = new Label(this, SWT.NONE);
        identifierLabel.setText("個体指示子");
        identifierText = new Text(this, SWT.BORDER);
        identifierText.setLayoutData(gridData);
        identifierText
        		.addModifyListener(new org.eclipse.swt.events.ModifyListener() {
        			public void modifyText(org.eclipse.swt.events.ModifyEvent e) {
        				System.out.println("modifyText()"); // TODO Auto-generated Event stub modifyText()
        				if (nameAutoCreateCheckBox.getSelection()) {
	        				Text t = (Text) e.widget;
	        				nameText.setText(createEntityName(t.getText()));
        				}
        			}
        		});
        Label filler1 = new Label(this, SWT.NONE);
        nameAutoCreateCheckBox = new Button(this, SWT.CHECK);
        nameAutoCreateCheckBox.setText("個体指示子からエンティティ名を自動生成");
        nameAutoCreateCheckBox
        		.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
        			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
        				System.out.println("widgetSelected()"); // TODO Auto-generated Event stub widgetSelected()
        				Button b = (Button) e.widget;
        				if (b.getSelection()) {
        					nameText.setEnabled(false);
        				} else {
        					nameText.setEnabled(true);
        				}
        			}
        		});
        nameLabel = new Label(this, SWT.NONE);
        nameLabel.setText("エンティティ名");
        nameText = new Text(this, SWT.BORDER);
        nameText.setLayoutData(gridData1);
        typeLabel = new Label(this, SWT.NONE);
        typeLabel.setText("類別");
        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 2;
        this.setLayout(gridLayout);
        createTypeCombo();
        this.setSize(new Point(315, 100));
			
	}

	/**
	 * This method initializes typeCombo	
	 *
	 */
	private void createTypeCombo() {
		typeCombo = new Combo(this, SWT.NONE);
		typeCombo.add("リソース");
		typeCombo.add("イベント");
	}
	/**
	 * 
	 * @param identifierName
	 * @return
	 */
	private String createEntityName(String identifierName) {
		String[] suffixes = { "コード", "ID", "ＩＤ", "id", "ｉｄ", "番号", "No" };
		String[] reportSuffixes = { "伝票", "報告書", "書", "レポート" };
		String entityName = identifierName;
		for (String suffix : suffixes) {
			if (identifierName.endsWith(suffix)) {
				entityName = identifierName.substring(0, identifierName
						.lastIndexOf(suffix));
				break;
			}
		}
		for (String reportSuffix : reportSuffixes) {
			if (entityName.endsWith(reportSuffix)) {
				entityName = entityName.substring(0, entityName
						.lastIndexOf(reportSuffix));
				break;
			}
		}
		return entityName;
	}
	/**
	 * @return the entityName
	 */
	public String getEntityName() {
		return this.nameText.getText();
	}
	/**
	 * 
	 * @return the identifierName
	 */
	public String getIdentifierName() {
		return this.identifierText.getText();
	}
	/**
	 * 
	 * @return the entityType
	 */
	public EntityType getSelectedType() {
		if (typeCombo.getSelectionIndex() == 0) {
			return EntityType.RESOURCE;
		} else {
			return EntityType.EVENT;
		}
	}
	public void setEntityTypeComboEnabled(boolean enabled) {
		this.typeCombo.setEnabled(enabled);
	}
}  //  @jve:decl-index=0:visual-constraint="10,10"
