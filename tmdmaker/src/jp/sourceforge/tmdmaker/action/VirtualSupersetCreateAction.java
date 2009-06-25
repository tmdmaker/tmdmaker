package jp.sourceforge.tmdmaker.action;

import org.eclipse.ui.IWorkbenchPart;

/**
 * みなしスーパーセット作成アクション
 * 
 * @author nakaG
 * 
 */
public class VirtualSupersetCreateAction extends AbstractEntitySelectionAction {
	/** 多値のOR作成アクションを表す定数 */
	public static final String ID = "_VS";

	/**
	 * コンストラクタ
	 * 
	 * @param part
	 *            エディター
	 */
	public VirtualSupersetCreateAction(IWorkbenchPart part) {
		super(part);
		setText("みなしSuperset作成");
		setId(ID);
	}
	
}
