package jp.sourceforge.tmdmaker.ui.editor.gef3.editpolicies;

import org.eclipse.gef.commands.Command;
import org.eclipse.jface.dialogs.Dialog;

import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Attribute;
import jp.sourceforge.tmdmaker.model.IAttribute;
import jp.sourceforge.tmdmaker.ui.dialogs.AttributeDialog;
import jp.sourceforge.tmdmaker.ui.dialogs.models.EditAttribute;
import jp.sourceforge.tmdmaker.ui.editor.gef3.commands.AttributeEditCommand;

public class AttributeComponentEditPolicy extends AbstractTMDComponentEditPolicy<Attribute> {

	@Override
	protected Command createEditCommand() {
		EditAttribute edited = ((AttributeDialog)dialog).getEditedValue();
		if (edited.isEdited()) {
			Attribute editedValueAttribute = new Attribute();
			edited.copyTo(editedValueAttribute);
			IAttribute original = edited.getOriginalAttribute();
			AbstractEntityModel entity = (AbstractEntityModel) getHost().getParent().getModel();
			return new AttributeEditCommand(original, editedValueAttribute, entity);
		}
		else{
			return null;
		}
	}

	@Override
	protected Dialog getDialog() {
		return new AttributeDialog(getHost().getViewer().getControl().getShell(), new EditAttribute(getModel()));
	}
	
	private IAttribute getModel()
	{
		return (IAttribute)(getHost().getModel());
	}
}
