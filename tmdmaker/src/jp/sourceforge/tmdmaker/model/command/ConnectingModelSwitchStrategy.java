package jp.sourceforge.tmdmaker.model.command;

import jp.sourceforge.tmdmaker.model.ConnectableElement;

/**
 * 
 * @author nakaG
 * 
 */
public interface ConnectingModelSwitchStrategy {
	/**
	 * どちらモデルをソースモデルとするか選択する
	 * 
	 * @param tempSource
	 *            仮のソースモデル
	 * @param tempTarget
	 *            仮のターゲットモデル
	 * @return 選別後のソースとターゲットモデルの対
	 */
	ModelPair switchModel(ConnectableElement tempSource, ConnectableElement tempTarget);

	/**
	 * ソースとターゲットモデルの対
	 * 
	 * @author nakaG
	 * 
	 */
	public static class ModelPair {
		/** ソース */
		public ConnectableElement source;
		/** ターゲット */
		public ConnectableElement target;
	}
}
