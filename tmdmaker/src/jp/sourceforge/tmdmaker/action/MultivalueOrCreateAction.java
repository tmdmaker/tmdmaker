package jp.sourceforge.tmdmaker.action;

import jp.sourceforge.tmdmaker.dialog.MultivalueOrEntityCreateDialog;
import jp.sourceforge.tmdmaker.editpart.SubsetTypeEditPart;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Attribute;
import jp.sourceforge.tmdmaker.model.Cardinality;
import jp.sourceforge.tmdmaker.model.Diagram;
import jp.sourceforge.tmdmaker.model.MultivalueOrEntity;
import jp.sourceforge.tmdmaker.model.MultivalueOrRelationship;

import org.eclipse.draw2d.geometry.Rectangle;
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
//		AbstractEntityEditPart part = getPart();
		MultivalueOrEntityCreateDialog dialog = new MultivalueOrEntityCreateDialog(
				getPart().getViewer().getControl().getShell());
		if (dialog.open() == Dialog.OK) {
			String typeName = dialog.getInputTypeName();
			AbstractEntityModel model = getModel();
			MultivalueOrEntity mo = new MultivalueOrEntity();
			Rectangle constraint = model.getConstraint().getTranslated(50, 0);
			mo.setConstraint(constraint);
			mo.setName(model.getName() + "." + typeName);
			Attribute attribute = new Attribute();
			attribute.setName(typeName + "コード");
			mo.addAttribute(attribute);
			mo.setEntityType(model.getEntityType());
			Diagram diagram = model.getDiagram();
			MultivalueOrCreateCommand command = new MultivalueOrCreateCommand(
					diagram, model, mo);

			execute(command);
		}
	}

	/**
	 * 
	 * @author nakaG
	 * 
	 */
	private static class MultivalueOrCreateCommand extends Command {
		private MultivalueOrEntity mo;
		private AbstractEntityModel model;
		private Diagram diagram;
		private MultivalueOrRelationship relationship;

		public MultivalueOrCreateCommand(Diagram diagram,
				AbstractEntityModel model, MultivalueOrEntity mo) {
			this.model = model;
			this.mo = mo;
			this.diagram = diagram;
			this.relationship = new MultivalueOrRelationship(model, mo);
			this.relationship.setTargetCardinality(Cardinality.MANY);
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
