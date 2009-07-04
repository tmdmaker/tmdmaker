package jp.sourceforge.tmdmaker.model.command.strategy;

import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.ConnectableElement;
import jp.sourceforge.tmdmaker.model.EntityType;
import jp.sourceforge.tmdmaker.model.command.ConnectingModelSwitchStrategy;

/**
 * 
 * @author nakaG
 * 
 */
public class ResourceAndEventEntitiesSwitchStrategy implements
		ConnectingModelSwitchStrategy {

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.command.ConnectingModelSwitchStrategy#switchModel(jp.sourceforge.tmdmaker.model.ConnectableElement,
	 *      jp.sourceforge.tmdmaker.model.ConnectableElement)
	 */
	@Override
	public ModelPair switchModel(ConnectableElement tempSource,
			ConnectableElement tempTarget) {
		assert tempSource instanceof AbstractEntityModel
				&& tempTarget instanceof AbstractEntityModel;

		ModelPair pair = new ModelPair();
		AbstractEntityModel tempSourceEntityModel = (AbstractEntityModel) tempSource;
		if (tempSourceEntityModel.getEntityType().equals(EntityType.RESOURCE)) {
			pair.source = tempSource;
			pair.target = tempTarget;
		} else {
			pair.source = tempTarget;
			pair.target = tempSource;
		}
		assert ((AbstractEntityModel) pair.source).getEntityType().equals(
				EntityType.RESOURCE)
				&& ((AbstractEntityModel) pair.target).getEntityType().equals(
						EntityType.EVENT);
		return pair;
	}
}
