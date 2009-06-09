package jp.sourceforge.tmdmaker.action;

import jp.sourceforge.tmdmaker.dialog.MultivalueOrEntityCreateDialog;
import jp.sourceforge.tmdmaker.editpart.AbstractEntityEditPart;
import jp.sourceforge.tmdmaker.editpart.SubsetTypeEditPart;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Attribute;
import jp.sourceforge.tmdmaker.model.Diagram;
import jp.sourceforge.tmdmaker.model.MultivalueOrEntity;
import jp.sourceforge.tmdmaker.model.MultivalueOrRelationship;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.ui.IWorkbenchPart;

/**
 * 
 * @author nakaG
 * 
 */
public class MultivalueOrCreateAction extends SelectionAction {
	/** 多値のOR作成アクションを表す定数 */
	public static final String MO = "_MO";

	/**
	 * コンストラクタ
	 * 
	 * @param part
	 *            エディター
	 */
	public MultivalueOrCreateAction(IWorkbenchPart part) {
		super(part);
		setText("多値のOR作成");
		setId(MO);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.ui.actions.WorkbenchPartAction#calculateEnabled()
	 */
	@Override
	protected boolean calculateEnabled() {
		if (getSelectedObjects().size() == 1) {
			Object selection = getSelectedObjects().get(0);
			if (selection instanceof AbstractEntityEditPart
					&& (selection instanceof SubsetTypeEditPart) == false) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		AbstractEntityEditPart part = getPart();
		MultivalueOrEntityCreateDialog dialog = new MultivalueOrEntityCreateDialog(getPart().getViewer().getControl().getShell());
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
	 * @return コントローラ(EditPart)
	 */
	protected AbstractEntityEditPart getPart() {
		return (AbstractEntityEditPart) getSelectedObjects().get(0);
	}

	/**
	 * 
	 * @return モデル
	 */
	protected AbstractEntityModel getModel() {
		return (AbstractEntityModel) getPart().getModel();
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
				AbstractEntityModel model,
				MultivalueOrEntity mo) {
			this.model = model;
			this.mo = mo;
			this.diagram = diagram;
			this.relationship = new MultivalueOrRelationship(model, mo);
			this.relationship.setTargetCardinality("N");
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
			relationship.disConnect();
		}

	}
}
