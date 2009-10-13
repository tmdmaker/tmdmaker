package jp.sourceforge.tmdmaker.dialog.component;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;

public class TableNameSettingPanel extends Composite {

	private Label nameLabel = null;
	private Text nameText = null;

	public TableNameSettingPanel(Composite parent, int style) {
		super(parent, style);
		initialize();
	}

	private void initialize() {
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.verticalAlignment = GridData.CENTER;
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		nameLabel = new Label(this, SWT.NONE);
		nameLabel.setText("名称");
		nameText = new Text(this, SWT.BORDER);
		nameText.setLayoutData(gridData);
		this.setLayout(gridLayout);
		setSize(new Point(315, 30));
	}

	public String getTableName() {
		return this.nameText.getText();
	}
	public void setTableName(String tableName) {
		this.nameText.setText(tableName);
	}
}  //  @jve:decl-index=0:visual-constraint="0,0"
