/*
 * Copyright 2009 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jp.sourceforge.tmdmaker.editpart;

import java.util.List;
import java.util.Map;

import jp.sourceforge.tmdmaker.dialog.EntityEditDialog;
import jp.sourceforge.tmdmaker.editpolicy.TMDModelGraphicalNodeEditPolicy;
import jp.sourceforge.tmdmaker.editpolicy.EntityLayoutEditPolicy;
import jp.sourceforge.tmdmaker.figure.EntityFigure;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Attribute;
import jp.sourceforge.tmdmaker.model.Diagram;
import jp.sourceforge.tmdmaker.model.EditAttribute;
import jp.sourceforge.tmdmaker.model.Entity;
import jp.sourceforge.tmdmaker.model.EntityType;
import jp.sourceforge.tmdmaker.model.Identifier;
import jp.sourceforge.tmdmaker.model.ReusedIdentifier;
import jp.sourceforge.tmdmaker.model.command.ConnectableElementDeleteCommand;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.jface.dialogs.Dialog;

/**
 * エンティティのコントローラ
 * 
 * @author nakaG
 * 
 */
public class EntityEditPart extends AbstractEntityEditPart {

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#createFigure()
	 */
	@Override
	protected IFigure createFigure() {
		EntityFigure figure = new EntityFigure();
		updateFigure(figure);
		return figure;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.editpart.AbstractEntityEditPart#updateFigure(org.eclipse.draw2d.IFigure)
	 */
	@Override
	protected void updateFigure(IFigure figure) {
		logger.debug(getClass() +"#updateFigure()");
		EntityFigure entityFigure = (EntityFigure) figure;
		Entity entity = (Entity) getModel();
		entityFigure.setNotImplement(entity.isNotImplement());

		// List<Identifier> ids = entity.getReuseKeys().;

//		List<Attribute> atts = entity.getAttributes();
		entityFigure.removeAllRelationship();
//		entityFigure.removeAllAttributes();

		entityFigure.setEntityName(entity.getName());
		entityFigure.setEntityType(entity.getEntityType().getLabel());
		entityFigure.setIdentifier(entity.getIdentifier().getName());
		for (Map.Entry<AbstractEntityModel, ReusedIdentifier> rk : entity
				.getReusedIdentifieres().entrySet()) {
			for (Identifier i : rk.getValue().getIdentifires()) {
				entityFigure.addRelationship(i.getName());
			}
		}
//		for (Attribute a : atts) {
//			entityFigure.addAttribute(a.getName());
//		}
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
	 */
	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE,
				new EntityComponentEditPolicy());
		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE,
				new TMDModelGraphicalNodeEditPolicy());
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new EntityLayoutEditPolicy());
	}


	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#getContentPane()
	 */
	@Override
	public IFigure getContentPane() {
		return ((EntityFigure) getFigure()).getAttributeCompartmentFigure();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.gef.editparts.AbstractEditPart#getModelChildren()
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected List getModelChildren() {
		return 
		((AbstractEntityModel) getModel()).getAttributes();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.editpart.AbstractEntityEditPart#onDoubleClicked()
	 */
	@Override
	protected void onDoubleClicked() {
		logger.debug(getClass() + "#onDoubleClicked()");
		Entity entity = (Entity) getModel();
		EntityEditDialog dialog = new EntityEditDialog(getViewer().getControl().getShell(), entity);
		if (dialog.open() == Dialog.OK) {
			CompoundCommand ccommand = new CompoundCommand();

			List<EditAttribute> editAttributeList = dialog.getEditAttributeList();
			addAttributeEditCommands(ccommand, entity, editAttributeList);
//			List<Attribute> newAttributes = new ArrayList<Attribute>();
//			for (EditAttribute ea : dialog.getEditAttributeList()) {
//				Attribute original = ea.getOriginalAttribute();
//				if (original == null) {
//					original = new Attribute(ea.getName());
//				} else {
//					if (original.getName().equals(ea.getName()) == false) {
//						AttributeEditCommand editCommand = new AttributeEditCommand(original, ea.getName());
//						ccommand.add(editCommand);
//					}
//				}
//				newAttributes.add(original);
//
//			}
//			EditCommand command = new EditCommand(dialog
//					.getEditEntityName(), dialog.getEditEntityType(), dialog
//					.getEditIdentifierName(), newAttributes,
//					entity);
			EditCommand command = new EditCommand(entity, dialog.getEditedValueEntity());
			ccommand.add(command);
			getViewer().getEditDomain().getCommandStack().execute(ccommand);
		}
	}

	public static class EntityComponentEditPolicy extends ComponentEditPolicy {
		/**
		 * 
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.gef.editpolicies.ComponentEditPolicy#createDeleteCommand(org.eclipse.gef.requests.GroupRequest)
		 */
		@Override
		protected Command createDeleteCommand(GroupRequest deleteRequest) {
			EntityDeleteCommand command = new EntityDeleteCommand(
					(Diagram) getHost().getParent().getModel(),
					(AbstractEntityModel) getHost().getModel());
			return command;
		}
	}

	private static class EntityDeleteCommand extends
			ConnectableElementDeleteCommand {
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
			return model.isDeletable();
		}

		@Override
		public void execute() {
			logger.debug(getClass().toString() + "#execute()");
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
	/**
	 * 
	 * @author nakaG
	 *
	 */
	private static class EditCommand extends Command {
		private String newEntityName;
		private EntityType newEntityType;
//		private String newIdentifierName;
		private boolean newNotImplement;
		private Entity toBeEditEntity;
		private AbstractEntityModel newValueEntity;
		private String oldEntityName;
		private EntityType oldEntityType;
//		private String oldIdentifierName;
		private boolean oldNotImplement;
		private List<Attribute> newAttributes;
		private List<Attribute> oldAttributes;
		private Identifier oldIdentifier;
		private Identifier newIdentifier;

//		public EditCommand(String newEntityName,
//				EntityType newEntityType, String newIdentifierName,
//				List<Attribute> newAttributes, Entity toBeEditEntity) {
//			this.newEntityName = newEntityName;
//			this.newEntityType = newEntityType;
//			this.newIdentifierName = newIdentifierName;
//			this.newAttributes = newAttributes;
//			this.toBeEditedEntity = toBeEditEntity;
//			this.oldEntityName = toBeEditEntity.getName();
//			this.oldEntityType = toBeEditEntity.getEntityType();
//			this.oldIdentifierName = toBeEditEntity.getIdentifier().getName();
//			this.oldAttributes = toBeEditEntity.getAttributes();
//		}
		public EditCommand(Entity toBeEditEntity, Entity newValueEntity) {
			this.toBeEditEntity = toBeEditEntity;
			this.newValueEntity = newValueEntity;
			this.newEntityName = newValueEntity.getName();
			this.newEntityType = newValueEntity.getEntityType();
//			this.newIdentifierName = newValueEntity.getIdentifier().getName();
			this.newAttributes = newValueEntity.getAttributes();
			this.newNotImplement = newValueEntity.isNotImplement();
			this.newIdentifier = newValueEntity.getIdentifier();
			this.oldEntityName = toBeEditEntity.getName();
			this.oldEntityType = toBeEditEntity.getEntityType();
//			this.oldIdentifierName = toBeEditEntity.getIdentifier().getName();
			this.oldAttributes = toBeEditEntity.getAttributes();
			this.oldNotImplement = toBeEditEntity.isNotImplement();
			this.oldIdentifier = new Identifier();
			toBeEditEntity.getIdentifier().copyTo(oldIdentifier);
		}
		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.gef.commands.Command#execute()
		 */
		@Override
		public void execute() {
			toBeEditEntity.setEntityType(newEntityType);
//			toBeEditEntity.setIdentifierName(newIdentifierName);
			toBeEditEntity.setIdentifierName(newIdentifier.getName());// ID変更伝播
			toBeEditEntity.getIdentifier().copyFrom(newIdentifier);
			toBeEditEntity.setAttributes(newAttributes);
			toBeEditEntity.setNotImplement(newNotImplement);
			toBeEditEntity.setName(newEntityName);
//			toBeEditEntity.setEntityType(newValueEntity.getEntityType());
//			toBeEditEntity.setIdentifierName(newValueEntity.getIdentifier().getName());
//			toBeEditEntity.setAttributes(newValueEntity.getAttributes());
//			toBeEditEntity.setName(newValueEntity.getName());
//			List<Attribute> newAttributes = new ArrayList<Attribute>();
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.gef.commands.Command#undo()
		 */
		@Override
		public void undo() {
			toBeEditEntity.setAttributes(oldAttributes);
			toBeEditEntity.setEntityType(oldEntityType);
//			toBeEditEntity.setIdentifierName(oldIdentifierName);
			toBeEditEntity.setIdentifierName(oldIdentifier.getName());
			toBeEditEntity.getIdentifier().copyFrom(oldIdentifier);
			toBeEditEntity.setNotImplement(oldNotImplement);
			toBeEditEntity.setName(oldEntityName);
		}
		
		
	}
//	private static class EditService {
//		private final Entity original;
//		private Entity edited;
//		
//		public EditService(Entity original) {
//			this.original = original;
//			this.edited = new Entity();
//			try {
//				BeanUtils.copyProperties(edited, original);
//			} catch (IllegalAccessException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (InvocationTargetException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//	}
}
