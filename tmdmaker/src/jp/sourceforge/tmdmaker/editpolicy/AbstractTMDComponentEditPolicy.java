package jp.sourceforge.tmdmaker.editpolicy;

import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.jface.dialogs.Dialog;

public abstract class AbstractTMDComponentEditPolicy<T> extends ComponentEditPolicy {
	
	Dialog dialog;
	
	@Override
	public Command getCommand(Request request) {
		if (REQ_OPEN.equals(request.getType()))
		{
			dialog = getDialog();
			if (dialog.open() != Dialog.OK) return null;
			Command ccommand = createEditCommand();
			return ccommand;
		}
		else{
			return super.getCommand(request);
		}
	}
	
	protected abstract Command createEditCommand();

	/**
	 * 編集用ダイアログを表示する。
	 * 
	 * @return ダイアログ
	 */
	protected abstract Dialog getDialog();
	
}
