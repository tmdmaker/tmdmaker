package jp.sourceforge.tmdmaker.editpart;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Identifier;
import jp.sourceforge.tmdmaker.model.IdentifierRef;
import jp.sourceforge.tmdmaker.model.ReusedIdentifier;
import jp.sourceforge.tmdmaker.ui.preferences.appearance.AppearanceSetting;
import jp.sourceforge.tmdmaker.ui.preferences.appearance.ModelAppearance;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.IFigure;
import org.eclipse.swt.graphics.Color;

public abstract class AbstractEntityModelEditPart<T extends AbstractEntityModel> extends AbstractModelEditPart<T> {

	@Override
	abstract protected void updateFigure(IFigure figure);

	/**
	 * @param table
	 */
	protected List<String> extractRelationship(T table) {
		List<String> relationship = new ArrayList<String>();
		for (Map.Entry<AbstractEntityModel, ReusedIdentifier> rk : table.getReusedIdentifieres().entrySet()) {
			for (Identifier i : rk.getValue().getUniqueIdentifieres()) {
				relationship.add(i.getName());
			}
		}
		return relationship;
	}

	/**
	 * @param entityFigure
	 * @param entity
	 * @param original
	 */
	protected List<String> extractRelationship(T table, IdentifierRef original) {
		List<String> relationship = new ArrayList<String>();
		for (Map.Entry<AbstractEntityModel, ReusedIdentifier> rk : table.getReusedIdentifieres().entrySet()) {
			for (IdentifierRef i : rk.getValue().getUniqueIdentifieres()) {
				if (i.isSame(original)) {
					// nothing
				} else {
					relationship.add(i.getName());
				}
			}
		}
		return relationship;
	}
	
	@Override
	abstract protected void onDoubleClicked();
	
	@Override
	abstract protected void createEditPolicies();
	
	/**
	 * モデルの色を設定する
	 * 
	 * @param entityFigure
	 *            モデル
	 * @param appearance
	 *            モデル外観
	 */
	protected void setupColor(IFigure entityFigure) {
		entityFigure.setBackgroundColor(createBackgroundColor(getAppearance()));
		entityFigure.setForegroundColor(createForegroundColor(getAppearance()));
	}

	abstract protected ModelAppearance getAppearance();
	
	private Color createBackgroundColor(ModelAppearance appearance) {
		AppearanceSetting config = AppearanceSetting.getInstance();
		if (config.isColorEnabled()) {
			return new Color(null, config.getBackground(appearance));
		} else {
			return ColorConstants.white;
		}
	}

	private Color createForegroundColor(ModelAppearance appearance) {
		AppearanceSetting config = AppearanceSetting.getInstance();
		if (config.isColorEnabled()) {
			return new Color(null, config.getFont(appearance));
		} else {
			return ColorConstants.black;
		}
	}
}
