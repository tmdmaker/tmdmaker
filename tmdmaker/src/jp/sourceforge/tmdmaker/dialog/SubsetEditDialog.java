package jp.sourceforge.tmdmaker.dialog;

import java.util.ArrayList;
import java.util.List;

import jp.sourceforge.tmdmaker.model.Attribute;
import jp.sourceforge.tmdmaker.model.SubsetEntity;

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
 * 
 * @author nakaG
 *
 */
public class SubsetEditDialog extends Dialog {
	/**
	 * 区分コード属性
	 */
	private Combo cmbAttributeNames;
	private Attribute selectedPartitionAttribute;

	/**
	 * 属性リスト
	 */
	private List<Attribute> attributes;

	/**
	 * 同一・相違のサブセット判定
	 */
	private boolean subsetSameType = true;

	/**
	 * 
	 */
	private Table tblAttributes;
	
	private Button attributeAddButton;
	
	private Button attributeDeleteButton;
	
	private Text attributeNameText;
	
	private int attributeEditIndex;

	private List<EditSubsetEntity> subsets = new ArrayList<EditSubsetEntity>();
	
	private List<SubsetEntity> addSubsets = new ArrayList<SubsetEntity>();
	private List<SubsetEntity> deleteSubsets = new ArrayList<SubsetEntity>();
		
	/**
	 * Constructor
	 * @param parentShell 親
	 */
	public SubsetEditDialog(Shell parentShell, boolean sameType, List<Attribute> attributes, List<SubsetEntity> subsets, Attribute selectedAttribute) {
		super(parentShell);
		this.attributes = attributes;
		this.subsetSameType = sameType;
		this.selectedPartitionAttribute = selectedAttribute;
		if (subsets != null) {
			for (SubsetEntity e : subsets) {
				EditSubsetEntity edit = new EditSubsetEntity(e);
				this.subsets.add(edit);
//				SubsetEntity copy = new SubsetEntity();
//				try {
//					BeanUtils.copyProperties(copy, e);
//				} catch (IllegalAccessException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				} catch (InvocationTargetException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//				this.subsets.add(copy);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		getShell().setText("サブセット編集");
		Composite composite = new Composite(parent, SWT.NULL);
		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		Label label = new Label(composite, SWT.NULL);
		label.setText("区分コード属性");
		cmbAttributeNames = new Combo(composite, SWT.READ_ONLY);
		for (Attribute a : attributes) {
			cmbAttributeNames.add(a.getName());
		}
		if (selectedPartitionAttribute != null) {
			cmbAttributeNames.setText(selectedPartitionAttribute.getName());
		}
		cmbAttributeNames.addSelectionListener(new SelectionAdapter() {

			/**
			 * {@inheritDoc}
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				Combo combo = (Combo) e.widget;
				selectedPartitionAttribute = attributes.get(combo.getSelectionIndex());
				super.widgetSelected(e);
			}
			
		});
		Group group = new Group(composite, SWT.SHADOW_OUT);
		group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		group.setBounds(-1, -1, -1, -1);
		Button same = new Button(group, SWT.RADIO);
		same.setText("同一");
		same.setBounds(5, 10, 55, 20);
		same.addSelectionListener(new SelectionAdapter() {

			/**
			 * {@inheritDoc}
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				Button bBut = (Button) e.widget;
				if (bBut.getSelection()) {
					subsetSameType = true;
				}
			}
			
		});
		Button different = new Button(group, SWT.RADIO);
		different.setText("相違");
		different.setBounds(80, 10, 55, 20);
		different.addSelectionListener(new SelectionAdapter() {

			/**
			 * {@inheritDoc}
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				Button bBut = (Button) e.widget;
				if (bBut.getSelection()) {
					subsetSameType = false;
				}
			}
			
		});
		if (subsetSameType) {
			same.setSelection(true);
		} else {
			different.setSelection(true);
		}
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

		tblAttributes = new Table(tableArea, SWT.BORDER | SWT.SINGLE | SWT.FULL_SELECTION);
		tblAttributes.setLayoutData(new GridData(GridData.FILL_BOTH));
		tblAttributes.setHeaderVisible(true);
		
		TableColumn column = new TableColumn(tblAttributes, SWT.NULL);
		column.setText("サブセット名");
		column.setWidth(100);

		for (EditSubsetEntity e : subsets) {
			TableItem item = new TableItem(tblAttributes, SWT.NULL);
			item.setText(e.getName());
		}
		
		tblAttributes.addSelectionListener(new SelectionAdapter() {


			/* (non-Javadoc)
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				int index = tblAttributes.getSelectionIndex();
				if (index >= 0) {
					EditSubsetEntity subset = subsets.get(index);
					attributeEditIndex = index;
					attributeNameText.setText(subset.getName());
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

			/* (non-Javadoc)
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				EditSubsetEntity subset = new EditSubsetEntity();
				subset.setName("サブセット_" + (subsets.size() + 1));
				subsets.add(subset);

				TableItem item = new TableItem(tblAttributes, SWT.NULL);
				item.setText(subset.getName());
			}
		});
		
		attributeDeleteButton = new Button(buttons, SWT.PUSH);
		attributeDeleteButton.setText("削除");
		attributeDeleteButton.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				EditSubsetEntity deleted = subsets.remove(attributeEditIndex);
				tblAttributes.remove(attributeEditIndex);
				deleteSubsets.add(deleted.getOriginal());
			}
			
		});
		// テーブル行幅調整
		new Label(buttons, SWT.NULL);
		new Label(buttons, SWT.NULL);
		
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
		
		group = new Group(columnEditArea, SWT.NULL);
		group.setText("サブセット編集");
		group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		group.setLayout(new GridLayout(6, false));
		label = new Label(group, SWT.NULL);
		label.setText("サブセット名");
		attributeNameText = new Text(group, SWT.BORDER);
		attributeNameText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		attributeNameText.addFocusListener(new FocusAdapter() {

			/* (non-Javadoc)
			 * @see org.eclipse.swt.events.FocusAdapter#focusLost(org.eclipse.swt.events.FocusEvent)
			 */
			@Override
			public void focusLost(FocusEvent e) {
				EditSubsetEntity subset = subsets.get(attributeEditIndex);
				subset.setName(attributeNameText.getText());
				TableItem item = tblAttributes.getItem(attributeEditIndex);
				item.setText(0, attributeNameText.getText());
			}
			
		});
		new Label(group, SWT.NULL);

		// サブセットリスト  追加ボタン
		//             削除ボタン
		// 名前編集テキストエリア
		
		return composite;
	}
	/**
	 * @return the subsetSameType
	 */
	public boolean isSubsetSameType() {
		return subsetSameType;
	}

	public List<SubsetEntity> getDeletedSubsetEntities() {
		return this.deleteSubsets;
	}

	/**
	 * @return the subsets
	 */
	public List<EditSubsetEntity> getSubsets() {
		return subsets;
	}


	/**
	 * @return the selectedPartitionAttribute
	 */
	public Attribute getSelectedPartitionAttribute() {
		return selectedPartitionAttribute;
	}



}
