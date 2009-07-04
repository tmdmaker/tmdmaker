package jp.sourceforge.tmdmaker.dialog;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Attribute;
import jp.sourceforge.tmdmaker.model.Identifier;
import jp.sourceforge.tmdmaker.model.ReUseKeys;

import org.apache.commons.beanutils.BeanUtils;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

/**
 * 表編集ダイアログ
 * 
 * @author nakaG
 * 
 */
public class TableEditDialog extends Dialog {
	/** エンティティ名称 */
	private String tableName;
	/** Re-usedキーリスト */
	private List<Identifier> reuseKeys = new ArrayList<Identifier>();
	/** 属性リスト */
	private List<Attribute> attributes = new ArrayList<Attribute>();

	/** 表名称編集用 */
	private Text entityNameText;
	private Text attributeNameText;
	private Table tblAttributes;
	private int attributeEditIndex = -1;
	private Button attributeAddButton;
	private Button attributeDeleteButton;
	private Button attributeUpButton;
	private Button attributeDownButton;

	
	public TableEditDialog(Shell parentShell,
			String entityName, 
			Map<AbstractEntityModel, ReUseKeys> reuseKeys,
			List<Attribute> attributes) {
		super(parentShell);
//		this.identifierName = identifierName;
		this.tableName = entityName;
//		this.entityType = entityType;
//		this.canEditEntityType = canEditEntityType;
		for (Map.Entry<AbstractEntityModel, ReUseKeys> entry : reuseKeys
				.entrySet()) {
			for (Identifier i : entry.getValue().getIdentifires()) {
				Identifier copy = new Identifier();
				try {
					BeanUtils.copyProperties(copy, i);
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				this.reuseKeys.add(copy);

			}
			attributeEditIndex++;
		}
		for (Attribute a : attributes) {
			Attribute copy = new Attribute();
			try {
				BeanUtils.copyProperties(copy, a);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.attributes.add(copy);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets
	 * .Composite)
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		getShell().setText("表編集");
		Composite composite = new Composite(parent, SWT.NULL);
		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));

//		Label label = new Label(composite, SWT.NULL);
//		label.setText("認知番号");
//		identifierNameText = new Text(composite, SWT.BORDER);
//		identifierNameText
//				.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
//		identifierNameText.setText(this.identifierName);

//		label = new Label(composite, SWT.NULL);
//		label.setText("類別");
//
//		Group group = new Group(composite, SWT.SHADOW_OUT);
//		group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
//		group.setBounds(-1, -1, -1, -1);
//		Button r = new Button(group, SWT.RADIO);
//		r.setText("リソース");
//		r.setBounds(5, 10, 55, 20);
//		r.addSelectionListener(new SelectionAdapter() {
//
//			/*
//			 * (non-Javadoc)
//			 * 
//			 * @see
//			 * org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse
//			 * .swt.events.SelectionEvent)
//			 */
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//				Button bBut = (Button) e.widget;
//				if (bBut.getSelection()) {
//					entityType = EntityType.RESOURCE;
//				}
//			}
//		});
//		Button e = new Button(group, SWT.RADIO);
//		e.setText("イベント");
//		e.setBounds(80, 10, 55, 20);
//		e.addSelectionListener(new SelectionAdapter() {
//
//			/*
//			 * (non-Javadoc)
//			 * 
//			 * @see
//			 * org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse
//			 * .swt.events.SelectionEvent)
//			 */
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//				Button bBut = (Button) e.widget;
//				if (bBut.getSelection()) {
//					entityType = EntityType.EVENT;
//				}
//			}
//
//		});
//
//		r.setSelection(this.entityType.equals(EntityType.RESOURCE));
//		e.setSelection(this.entityType.equals(EntityType.EVENT));
//		r.setEnabled(canEditEntityType);
//		e.setEnabled(canEditEntityType);
		Label label = new Label(composite, SWT.NULL);
		label.setText("表名");
		entityNameText = new Text(composite, SWT.BORDER);
		entityNameText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		entityNameText.setText(tableName);

		Composite tableArea = new Composite(composite, SWT.NULL);
		GridLayout layout = new GridLayout(2, false);
		layout.horizontalSpacing = 0;
		layout.verticalSpacing = 0;
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		tableArea.setLayout(layout);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 2;
		tableArea.setLayoutData(gd);
		tblAttributes = new Table(tableArea, SWT.BORDER | SWT.SINGLE
				| SWT.FULL_SELECTION);
		tblAttributes.setLayoutData(new GridData(GridData.FILL_BOTH));
		tblAttributes.setHeaderVisible(true);

		TableColumn column = new TableColumn(tblAttributes, SWT.NULL);
		column.setText("アトリビュート名");
		column.setWidth(100);

		column = new TableColumn(tblAttributes, SWT.NULL);
		column.setText("物理名");
		column.setWidth(100);

		column = new TableColumn(tblAttributes, SWT.NULL);
		column.setText("データ型");
		column.setWidth(100);

		for (Attribute a : attributes) {
			TableItem item = new TableItem(tblAttributes, SWT.NULL);
			item.setText(0, a.getName());
			item.setText(1, a.getPhysicalName());
			item.setText(2, a.getType());
		}
		tblAttributes.addSelectionListener(new SelectionAdapter() {

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse
			 * .swt.events.SelectionEvent)
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				int index = tblAttributes.getSelectionIndex();
				if (index >= 0) {
					Attribute attribute = attributes.get(index);
					attributeEditIndex = index;
					attributeNameText.setText(attribute.getName());
				}
			}

		});
		Composite buttons = new Composite(tableArea, SWT.NULL);

		GridLayout buttonsLayout = new GridLayout(1, false);
		buttonsLayout.horizontalSpacing = 0;
		buttonsLayout.verticalSpacing = 0;
		buttonsLayout.marginHeight = 0;
		buttonsLayout.marginWidth = 2;
		buttons.setLayout(buttonsLayout);
		buttons.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));

		attributeAddButton = new Button(buttons, SWT.PUSH);
		attributeAddButton.setText("追加");
		attributeAddButton.addSelectionListener(new SelectionAdapter() {

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse
			 * .swt.events.SelectionEvent)
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				Attribute newAttribute = new Attribute("属性_"
						+ (attributes.size() + 1));
				attributes.add(newAttribute);
				TableItem item = new TableItem(tblAttributes, SWT.NULL);
				updateAttributeColumn(item, newAttribute);
			}
		});

		attributeDeleteButton = new Button(buttons, SWT.PUSH);
		attributeDeleteButton.setText("削除");
		attributeDeleteButton.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				attributes.remove(attributeEditIndex);
				tblAttributes.remove(attributeEditIndex);
			}

		});
		attributeUpButton = new Button(buttons, SWT.PUSH);
		attributeUpButton.setText("上へ");

		attributeDownButton = new Button(buttons, SWT.PUSH);
		attributeDownButton.setText("下へ");

		Composite columnEditArea = new Composite(composite, SWT.NULL);
		layout = new GridLayout(2, false);
		layout.horizontalSpacing = 0;
		layout.verticalSpacing = 0;
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		columnEditArea.setLayout(layout);
		gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 2;
		columnEditArea.setLayoutData(gd);

		Group group = new Group(columnEditArea, SWT.NULL);
		group.setText("属性編集");
		group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		group.setLayout(new GridLayout(6, false));
		label = new Label(group, SWT.NULL);
		label.setText("属性名");
		attributeNameText = new Text(group, SWT.BORDER);
		attributeNameText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		attributeNameText.addFocusListener(new FocusAdapter() {

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * org.eclipse.swt.events.FocusAdapter#focusLost(org.eclipse.swt
			 * .events.FocusEvent)
			 */
			@Override
			public void focusLost(FocusEvent e) {
				Attribute attribute = attributes.get(attributeEditIndex);
				attribute.setName(attributeNameText.getText());
				TableItem item = tblAttributes.getItem(attributeEditIndex);
				updateAttributeColumn(item, attribute);
			}

		});
		label = new Label(group, SWT.NULL);
		label.setText("型");
		new Combo(group, SWT.READ_ONLY);

		new Label(group, SWT.NULL);

		return composite;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 */
	@Override
	protected void okPressed() {
		this.tableName = this.entityNameText.getText();
//		this.identifierName = this.identifierNameText.getText();

		super.okPressed();
	}

//	/**
//	 * @return the identifierName
//	 */
//	public String getIdentifierName() {
//		return identifierName;
//	}

	/**
	 * @return the entityName
	 */
	public String getEntityName() {
		return tableName;
	}

//	/**
//	 * @return the entityType
//	 */
//	public EntityType getEntityType() {
//		return entityType;
//	}

	/**
	 * @return the reuseKeys
	 */
	public List<Identifier> getReuseKeys() {
		return reuseKeys;
	}

	/**
	 * @return the attributes
	 */
	public List<Attribute> getAttributes() {
		return attributes;
	}

	private void updateAttributeColumn(TableItem item, Attribute attribute) {
		item.setText(0, attribute.getName());
		item.setText(1, attribute.getPhysicalName());
		item.setText(2, attribute.getType());
	}

}
