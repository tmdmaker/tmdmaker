package jp.sourceforge.tmdmaker.model.command.strategy;

import jp.sourceforge.tmdmaker.model.ConnectableElement;
import jp.sourceforge.tmdmaker.model.command.ConnectingModelSwitchStrategy;
/**
 * 
 * @author nakaG
 *
 */
public class DefaultConnectingModelSwitchStrategy implements
		ConnectingModelSwitchStrategy {

	/**
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.model.command.ConnectingModelSwitchStrategy#switchModel(jp.sourceforge.tmdmaker.model.ConnectableElement, jp.sourceforge.tmdmaker.model.ConnectableElement)
	 */
	@Override
	public ModelPair switchModel(ConnectableElement tempSource,
			ConnectableElement tempTarget) {
		 ModelPair pair = new ModelPair();
		 pair.source = tempSource;
		 pair.target = tempTarget;
		 return pair;
	}
}
