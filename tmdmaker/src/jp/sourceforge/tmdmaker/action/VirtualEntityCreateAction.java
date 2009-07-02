package jp.sourceforge.tmdmaker.action;

import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.ConnectableElement;
import jp.sourceforge.tmdmaker.model.Diagram;
import jp.sourceforge.tmdmaker.model.TransfarReuseKeysToTargetRelationship;
import jp.sourceforge.tmdmaker.model.VirtualEntity;
import jp.sourceforge.tmdmaker.model.command.ConnectionCreateCommand;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
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
		CompoundCommand ccommand = new CompoundCommand();

		AbstractEntityModel model = getModel();
		Diagram diagram = model.getDiagram();
		VirtualEntity entity = new VirtualEntity();
		entity.setName(model.getName() + ".VE" + model.getModelSourceConnections().size());
		entity.setConstraint(model.getConstraint().getTranslated(100, 0));
		
		VirtualEntityCreateCommand command1 = new VirtualEntityCreateCommand(diagram, entity);
		ccommand.add(command1);
		
		ConnectionCreateCommand command2 = new ConnectionCreateCommand();
		command2.setConnection(new TransfarReuseKeysToTargetRelationship());
		command2.setSource(model);
		command2.setTarget(entity);
		ccommand.add(command2);
		
		execute(ccommand);
	}

	/**
	 * みなしエンティティ作成
	 * @author nakaG
	 *
	 */
	private static class VirtualEntityCreateCommand extends Command {
		private Diagram diagram;
		private VirtualEntity model;
		/**
		 * コンストラクタ
		 * @param diagram ダイアグラム
		 * @param model みなしエンティティ
		 */
		public VirtualEntityCreateCommand(Diagram diagram, VirtualEntity model) {
			this.diagram = diagram;
			this.model = model;
		}
		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.gef.commands.Command#execute()
		 */
		@Override
		public void execute() {
			diagram.addChild(model);
//			model.setDiagram(diagram);
		}
		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.gef.commands.Command#undo()
		 */
		@Override
		public void undo() {
//			model.setDiagram(null);
			diagram.removeChild(model);
		}
		
	}
}
