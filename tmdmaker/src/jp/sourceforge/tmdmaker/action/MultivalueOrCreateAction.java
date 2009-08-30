package jp.sourceforge.tmdmaker.action;

import jp.sourceforge.tmdmaker.dialog.MultivalueOrEntityCreateDialog;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.MultivalueOrRelationship;

import org.eclipse.gef.commands.Command;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.ui.IWorkbenchPart;

/**
 * 多値のOR作成アクション
 * 
 * @author nakaG
 * 
 */
public class MultivalueOrCreateAction extends AbstractEntitySelectionAction {
	/** 多値のOR作成アクションを表す定数 */
	public static final String ID = "_MO";

	/**
	 * コンストラクタ
	 * 
	 * @param part
	 *            エディター
	 */
	public MultivalueOrCreateAction(IWorkbenchPart part) {
		super(part);
		setText("多値のOR作成");
		setId(ID);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		// AbstractEntityEditPart part = getPart();
		MultivalueOrEntityCreateDialog dialog = new MultivalueOrEntityCreateDialog(
				getPart().getViewer().getControl().getShell());
		if (dialog.open() == Dialog.OK) {
			MultivalueOrCreateCommand command = new MultivalueOrCreateCommand(
					getModel(), dialog.getInputTypeName());

			execute(command);
		}
	}

	/**
	 * 
	 * @author nakaG
	 * 
	 */
	private static class MultivalueOrCreateCommand extends Command {

		private MultivalueOrRelationship relationship;

		public MultivalueOrCreateCommand(AbstractEntityModel model,
				String typeName) {
			this.relationship = new MultivalueOrRelationship(model, typeName);
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.gef.commands.Command#execute()
		 */
		@Override
		public void execute() {
			relationship.connect();
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.gef.commands.Command#undo()
		 */
		@Override
		public void undo() {
			relationship.disconnect();
		}

	}
}
