package jp.sourceforge.tmdmaker.action;

import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Entity2VirtualEntityRelationship;

import org.eclipse.gef.commands.Command;
import org.eclipse.ui.IWorkbenchPart;

/**
 * みなしエンティティ作成アクション
 * 
 * @author nakaG
 * 
 */
public class VirtualEntityCreateAction extends AbstractEntitySelectionAction {
	/** みなしエンティティ作成アクションを表す定数 */
	public static final String ID = "_VE";

	/**
	 * コンストラクタ
	 * 
	 * @param part
	 *            エディター
	 */
	public VirtualEntityCreateAction(IWorkbenchPart part) {
		super(part);
		setText("みなしEntity作成");
		setId(ID);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		execute(new VirtualEntityCreateCommand(getModel()));
	}

	/**
	 * みなしエンティティ作成
	 * 
	 * @author nakaG
	 * 
	 */
	private static class VirtualEntityCreateCommand extends Command {
		/** みなしエンティティへのリレーションシップ */
		private Entity2VirtualEntityRelationship relationship;

		/**
		 * コンストラクタ
		 * 
		 * @param model
		 *            みなしエンティティ作成対象
		 */
		public VirtualEntityCreateCommand(AbstractEntityModel model) {
			this.relationship = new Entity2VirtualEntityRelationship(model);
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
