package jp.sourceforge.tmdmaker.editpart;

import java.util.List;
import java.util.Map;

import jp.sourceforge.tmdmaker.editpolicy.AbstractEntityGraphicalNodeEditPolicy;
import jp.sourceforge.tmdmaker.figure.EntityFigure;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Attribute;
import jp.sourceforge.tmdmaker.model.Diagram;
import jp.sourceforge.tmdmaker.model.Entity;
import jp.sourceforge.tmdmaker.model.Identifier;
import jp.sourceforge.tmdmaker.model.ReuseKey;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel.EntityType;
import jp.sourceforge.tmdmaker.model.command.ConnectableElementDeleteCommand;
import jp.sourceforge.tmdmaker.model.command.EntityEditCommand;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.gef.requests.ReconnectRequest;
import org.eclipse.jface.dialogs.Dialog;

public class EntityEditPart extends AbstractEntityEditPart implements DoubleClickSupport {

	@Override
	protected IFigure createFigure() {
		EntityFigure figure = new EntityFigure();
		updateFigure(figure);
		return figure;
	}

	private void updateFigure(EntityFigure figure) {
		Entity entity = (Entity) getModel();
		
//		List<Identifier> ids = entity.getReuseKeys().;
		
		List<Attribute> atts = entity.getAttributes();
		figure.removeAllRelationship();
		figure.removeAllAttributes();

		figure.setEntityName(entity.getName());
		figure.setEntityType(entity.getEntityType().toString());
		figure.setIdentifier(entity.getIdentifier().getName());
		for (Map.Entry<AbstractEntityModel, ReuseKey> rk : entity.getReuseKeys().entrySet()) {
			for (Identifier i : rk.getValue().getIdentifires()) {
				figure.addRelationship(i.getName());
			}
		}
		for (Attribute a : atts) {
			figure.addAttribute(a.getName());
		}
	}

	@Override
	protected void refreshVisuals() {
		System.out.println(getClass().toString() + "#refreshVisuals()");
		super.refreshVisuals();
		Object model = getModel();
		Rectangle bounds = new Rectangle(((AbstractEntityModel) model)
				.getConstraint());
		((GraphicalEditPart) getParent()).setLayoutConstraint(this,
				getFigure(), bounds);
		updateFigure((EntityFigure) getFigure());
		refreshChildren();
	}

	/**
	 * 
	 */
	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE,
				new EntityComponentEditPolicy());
		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE,
				new EntityGraphicalNodeEditPolicy());
	}

	private static class EntityGraphicalNodeEditPolicy extends AbstractEntityGraphicalNodeEditPolicy {

//		@Override
//		protected Command getConnectionCompleteCommand(
//				CreateConnectionRequest request) {
//			System.out.println(getClass().toString()
//					+ "#getConnectionCompleteCommand()");
//
//			ConnectionCreateCommand startCommand = (ConnectionCreateCommand) request
//					.getStartCommand();
//			AbstractEntityModel source = (AbstractEntityModel) startCommand.getSource();
//			AbstractEntityModel target = (AbstractEntityModel) getHost().getModel();
//
//			Command command = null;
//			// 再帰
//			if (source == target) {
//				System.out.println("Recursive");
//				command = createRecursiveTableCommand(request);
//			} else if (isR2E(source, target)) {
//				System.out.println("R:E");
//				command = createR2ERelationshipCommand(startCommand);
//				command.setLabel("R:E");
//			} else if (isR2R(source, target)) {
//				System.out.println("R:R");
//				/* 対照表作成 */
//				command = createCombinationTableCommand(request);
//				command.setLabel("R:R");
//			} else if (isE2E(source, target)) {
//				System.out.println("E:E");
//				/* 通常コネクション */
//				command = createE2ERelationshipCommand(request);
//				command.setLabel("E:E");
//			}
//			return command;
//		}

		/* 先エンティティとのコネクション作成 */
//		private Command createRelationshipCommand(
//				ConnectionCreateCommand command) {
//			command.setTarget(getHost().getModel());
//			return command;
//		}
		@Override
		protected Command getReconnectSourceCommand(ReconnectRequest request) {
			// TODO Auto-generated method stub
			System.out.println(getClass().toString()
					+ "#getReconnectSourceCommand()");
			return null;
		}

		@Override
		protected Command getReconnectTargetCommand(ReconnectRequest request) {
			// TODO Auto-generated method stub
			System.out.println(getClass().toString()
					+ "#getReconnectTargetCommand()");
			return null;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#refresh()
	 */
	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		System.out.println(getClass().toString() + "#refresh()");
		super.refresh();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#refreshSourceConnections()
	 */
	@Override
	protected void refreshSourceConnections() {
		// TODO Auto-generated method stub
		System.out.println(getClass().toString()
				+ "#refreshSourceConnections()");
		super.refreshSourceConnections();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#refreshTargetConnections()
	 */
	@Override
	protected void refreshTargetConnections() {
		// TODO Auto-generated method stub
		System.out.println(getClass().toString()
				+ "#refreshTargetConnections()");
		super.refreshTargetConnections();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#refreshChildren()
	 */
	@Override
	protected void refreshChildren() {
		// TODO Auto-generated method stub
		System.out.println(getClass().toString() + "#refreshChildren()");
		super.refreshChildren();
	}

	
	/* (non-Javadoc)
	 * @see tm.tmdiagram.tmdeditor.editpart.DoubleClickSupport#doubleClicked()
	 */
	@Override
	public void doubleClicked() {
		Entity entity = (Entity) getModel();
		EntityEditDialog dialog = new EntityEditDialog(getViewer().getControl().getShell(), entity.getIdentifier().getName(), entity.getName(), entity.getEntityType(), entity.getReuseKeys(), entity.getAttributes());
		if (dialog.open() == Dialog.OK) {
			EntityEditCommand command = new EntityEditCommand();
			command.setAttributes(dialog.getAttributes());
			command.setEntityName(dialog.getEntityName());
			command.setEntityType(dialog.getEntityType());
			command.setIdentifierName(dialog.getIdentifierName());
			command.setReuseKeys(dialog.getReuseKeys());
			command.setEntity(entity);
			getViewer().getEditDomain().getCommandStack().execute(command);
		}
	}

//	public static class CreateBendPointCommand extends Command {
//		private RecursiveMarkConnection model;
//		private Rectangle bounds;
//		public void setModel(RecursiveMarkConnection model) {
//			this.model = model;
//		}
//		public void setSourceBounds(Rectangle bounds) {
//			this.bounds = bounds;
//		}
//		/* (non-Javadoc)
//		 * @see org.eclipse.gef.commands.Command#execute()
//		 */
//		@Override
//		public void execute() {
//			int width = bounds.width / 2 + 20;
//			int height = bounds.height / 2;
//			ConnectionBendpoint bendpoint = new ConnectionBendpoint(
//					new Dimension(0, 0), new Dimension(width, 0));
//			model.addBendpoint(0, bendpoint);
//			bendpoint = new ConnectionBendpoint(new Dimension(width, 0),
//					new Dimension(width, height));
//			model.addBendpoint(1, bendpoint);
//			bendpoint = new ConnectionBendpoint(new Dimension(width, height),
//					new Dimension(0, height));
//			model.addBendpoint(2, bendpoint);
//
//		}
//		
//	}
	public static class EntityComponentEditPolicy extends ComponentEditPolicy {

		@Override
		protected Command createDeleteCommand(GroupRequest deleteRequest) {
			EntityDeleteCommand command = 
				new EntityDeleteCommand(
						(Diagram) getHost().getParent().getModel(),
						(AbstractEntityModel) getHost().getModel());
			return command;
		}
	}
	private static class EntityDeleteCommand extends ConnectableElementDeleteCommand {
		private AbstractEntityModel model;
		
		
		public EntityDeleteCommand(Diagram diagram, AbstractEntityModel model) {
			super();
			this.diagram = diagram;
			this.model = model;
			this.sourceConnections.addAll(model.getModelSourceConnections());
			this.targetConnections.addAll(model.getModelTargetConnections());
		}
		@Override
		public boolean canExecute() {
			if (model.getEntityType() == EntityType.E) {
				return model.getModelSourceConnections().size() == 0 ;
			}
			return model.getModelSourceConnections().size() == 0 && model.getModelTargetConnections().size() == 0;
		}
		@Override
		public void execute() {
			System.out.println(getClass().toString() + "#execute()");
			diagram.removeChild(model);

			detachConnections(sourceConnections);
			detachConnections(targetConnections);
		}
		@Override
		public void undo() {
			diagram.addChild(model);
			attathConnections(sourceConnections);
			attathConnections(targetConnections);
		}
		public void setModel(Object model) {
			this.model = (AbstractEntityModel) model;
		}
	}
}
