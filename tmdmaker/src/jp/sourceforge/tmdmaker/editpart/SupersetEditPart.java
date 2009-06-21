package jp.sourceforge.tmdmaker.editpart;

import java.util.List;
import java.util.Map;

import jp.sourceforge.tmdmaker.figure.EntityFigure;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Attribute;
import jp.sourceforge.tmdmaker.model.Identifier;
import jp.sourceforge.tmdmaker.model.ReUseKeys;
import jp.sourceforge.tmdmaker.model.Superset;

import org.eclipse.draw2d.IFigure;

public class SupersetEditPart extends AbstractEntityEditPart {

	@Override
	protected void onDoubleClicked() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void updateFigure(IFigure figure) {
		EntityFigure entityFigure = (EntityFigure) figure;
		Superset entity = (Superset) getModel();
		
		List<Attribute> atts = entity.getAttributes();
		entityFigure.removeAllRelationship();
		entityFigure.removeAllAttributes();

		entityFigure.setEntityName(entity.getName());
		// entityFigure.setEntityType(entity.getEntityType().toString());
		// figure.setIdentifier(entity.getIdentifier().getName());
		for (Map.Entry<AbstractEntityModel, ReUseKeys> rk : entity
				.getReuseKeys().entrySet()) {
			for (Identifier i : rk.getValue().getIdentifires()) {
				entityFigure.addRelationship(i.getName());
			}
		}
		for (Attribute a : atts) {
			entityFigure.addAttribute(a.getName());
		}
	}

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

	@Override
	protected void createEditPolicies() {
		// TODO Auto-generated method stub

	}

}
