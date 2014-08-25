package jp.sourceforge.tmdmaker.dialog;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import jp.sourceforge.tmdmaker.dialog.model.EditAttribute;
import jp.sourceforge.tmdmaker.dialog.model.EditTable;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public abstract class ModelEditDialog<T extends AbstractEntityModel> extends Dialog implements PropertyChangeListener {

	public ModelEditDialog(Shell parentShell) {
		super(parentShell);
	}

	public ModelEditDialog(IShellProvider parentShell) {
		super(parentShell);
	}
	
	/** 編集元エンティティ */
	protected EditTable entity;
	
	/** 編集結果格納用 */
	protected T editedValue;

	protected abstract Control createDialogArea(Composite parent);

	public abstract void propertyChange(PropertyChangeEvent evt);

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 */
	@Override
	protected void okPressed()
	{
		super.okPressed();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#close()
	 */
	@Override
	public boolean close() {
		entity.removePropertyChangeListener(this);
		return super.close();
	}
	
	protected EditTable getEditModel()
	{
		return entity;
	}

	/**
	 * @return the editAttributeList
	 */
	public List<EditAttribute> getEditAttributeList() {
		return entity.getAttributes();
	}

	/**
	 * @return the editedValueEntity
	 */
	public T getEditedValue() {
		return editedValue;
	}

}