package jp.sourceforge.tmdmaker.dialog;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

public class AttributeSettingPanel extends Composite {

	private List<EditAttribute> attributeList = null;  //  @jve:decl-index=0:
	private static final int EDIT_COLUMN = 0;
	private List<EditAttribute> deletedAttributes = new ArrayList<EditAttribute>();  //  @jve:decl-index=0:
	private int selectedIndex = -1;
	private TableEditor tableEditor = null;
	private Table attributeTable = null;
	private Composite controlComposite = null;
	private Button newButton = null;
	private Button deleteButton = null;
	private Button upButton = null;
	private Button downButton = null;
	public AttributeSettingPanel(Composite parent, int style) {
		super(parent, style);
		initialize();
	}

	private void initialize() {
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = GridData.FILL;
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		attributeTable = new Table(this, SWT.FULL_SELECTION);
		tableEditor = new TableEditor(attributeTable);
		tableEditor.grabHorizontal = true;
		tableEditor.horizontalAlignment = SWT.LEFT;
		tableEditor.minimumWidth = 50;
		attributeTable.setHeaderVisible(true);
		attributeTable.setLayoutData(gridData);
		attributeTable.setLinesVisible(true);
		attributeTable
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						System.out.println("widgetSelected()"); // TODO Auto-generated Event stub widgetSelected()
						selectedIndex = attributeTable.getSelectionIndex();
						if (selectedIndex == -1) {
							System.out.println("no selected");
							return;
						}
//						attributeTable.setSelection(new int[0]);
						Control oldEditor = tableEditor.getEditor();
						if (oldEditor != null) {
							System.out.println("oldEditor exists");
							oldEditor.dispose();
						}

						TableItem item = (TableItem)e.item;
						final Text text = new Text(attributeTable, SWT.NONE);
						text.setText(item.getText(EDIT_COLUMN));
						text.addFocusListener(new FocusAdapter(){

							/**
							 * {@inheritDoc}
							 *
							 * @see org.eclipse.swt.events.FocusAdapter#focusLost(org.eclipse.swt.events.FocusEvent)
							 */
							@Override
							public void focusLost(FocusEvent e) {
								TableItem item = tableEditor.getItem();
								String editValue = text.getText();
								if (editValue == null) {
									editValue = "";
								}
								item.setText(EDIT_COLUMN, editValue);
								EditAttribute ea = attributeList.get(selectedIndex);
								ea.setName(editValue);
								text.dispose();
							}
							
						});
						text.addModifyListener(new ModifyListener(){

							/**
							 * {@inheritDoc}
							 *
							 * @see org.eclipse.swt.events.ModifyListener#modifyText(org.eclipse.swt.events.ModifyEvent)
							 */
							@Override
							public void modifyText(ModifyEvent e) {
								System.out.println("modifyText()");
								TableItem item = tableEditor.getItem();
								String editValue = text.getText();
								if (editValue == null) {
									editValue = "";
								}
								item.setText(EDIT_COLUMN, editValue);
								EditAttribute ea = attributeList.get(selectedIndex);
								ea.setName(editValue);
							}
							
						});
						text.selectAll();
						text.setFocus();
						
						tableEditor.setEditor(text, item, EDIT_COLUMN);
					}
				});
		TableColumn tableColumn = new TableColumn(attributeTable, SWT.NONE);
		tableColumn.setWidth(200);
		tableColumn.setText("アトリビュート名");
		this.setLayout(gridLayout);
		createControlComposite();
		this.setSize(new Point(315, 131));
	}

	/**
	 * This method initializes controlComposite	
	 *
	 */
	private void createControlComposite() {
		controlComposite = new Composite(this, SWT.NONE);
		controlComposite.setLayout(new GridLayout());
		newButton = new Button(controlComposite, SWT.NONE);
		newButton.setText("新規");
		newButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				System.out.println("widgetSelected()"); // TODO Auto-generated Event stub widgetSelected()
				EditAttribute ea = new EditAttribute();
				ea.setName("アトリビュート" + String.valueOf(attributeList.size() + 1));
				attributeList.add(ea);
				TableItem item = new TableItem(attributeTable, SWT.NULL);
				item.setText(ea.getName());
				selectedIndex = attributeList.size() - 1;
				updateSelection();
			}
		});
		upButton = new Button(controlComposite, SWT.NONE);
		upButton.setText("上へ");
		upButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				System.out.println("widgetSelected()"); // TODO Auto-generated Event stub widgetSelected()
				if (selectedIndex == -1 || selectedIndex == 0) {
					return;
				}
				EditAttribute move = attributeList.remove(selectedIndex);
				selectedIndex--;
				attributeList.add(selectedIndex, move);
				updateAttributeTable();
				updateSelection();
			}
		});
		downButton = new Button(controlComposite, SWT.NONE);
		downButton.setText("下へ");
		downButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				System.out.println("widgetSelected()"); // TODO Auto-generated Event stub widgetSelected()
				if (selectedIndex == -1 || selectedIndex == attributeList.size() -1) {
					return;
				}
				EditAttribute move = attributeList.remove(selectedIndex);
				selectedIndex++;
				attributeList.add(selectedIndex, move);
				updateAttributeTable();
				updateSelection();
			}
		});
		deleteButton = new Button(controlComposite, SWT.NONE);
		deleteButton.setText("削除");
		deleteButton
		.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				System.out.println("widgetSelected()"); // TODO Auto-generated Event stub widgetSelected()
				if (selectedIndex == -1) {
					return;
				}
				EditAttribute deleted = attributeList.remove(selectedIndex);
				deletedAttributes.add(deleted);
				if (attributeList.size() <= selectedIndex) {
					selectedIndex--;
				}
				updateAttributeTable();
				updateSelection();
			}
		});
	}
	public void initializeTableValue(List<EditAttribute> attributeList) {
		this.attributeList = attributeList;
		updateAttributeTable();
	}
	private void updateAttributeTable() {
		attributeTable.removeAll();
		for (EditAttribute ea : attributeList) {
			TableItem item = new TableItem(attributeTable, SWT.NULL);
			item.setText(0, ea.getName());
		}
	}
	private void updateSelection() {
		attributeTable.select(selectedIndex);
	}

	/**
	 * @return the attributeList
	 */
	public List<EditAttribute> getAttributeList() {
		return attributeList;
	}

	/**
	 * @return the deletedAttributes
	 */
	public List<EditAttribute> getDeletedAttributeList() {
		return deletedAttributes;
	}
	
}  //  @jve:decl-index=0:visual-constraint="10,10"
