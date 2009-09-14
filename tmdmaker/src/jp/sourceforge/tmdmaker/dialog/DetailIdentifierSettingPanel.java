package jp.sourceforge.tmdmaker.dialog;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;

public class DetailIdentifierSettingPanel extends Composite {

	private Label identifierLabel = null;
	private Text identifierText = null;

	public DetailIdentifierSettingPanel(Composite parent, int style) {
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
		identifierLabel = new Label(this, SWT.NONE);
		identifierLabel.setText("個体指示子");
		identifierText = new Text(this, SWT.BORDER);
		identifierText.setLayoutData(gridData);
		this.setLayout(gridLayout);
		setSize(new Point(147, 28));
	}
	public void setIdentifierName(String name) {
		identifierText.setText(name);
	}
	public String getIdentifierName() {
		return identifierText.getText();
	}
}  //  @jve:decl-index=0:visual-constraint="0,0"
