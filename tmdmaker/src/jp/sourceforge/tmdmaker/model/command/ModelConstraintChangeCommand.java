package jp.sourceforge.tmdmaker.model.command;

import jp.sourceforge.tmdmaker.model.ModelElement;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.Command;

/**
 * モデルの制約変更コマンド
 * 
 * @author nakaG
 * 
 */
public class ModelConstraintChangeCommand extends Command {
	/** 移動対象 */
	private ModelElement model;
	/** 移動後の座標 */
	private Rectangle constraint;
	/** 移動前の座標 */
	private Rectangle oldConstraint;

	/**
	 * コンストラクタ（新規作成時やマウス操作時）
	 * 
	 * @param model
	 *            移動対象
	 * @param constraint
	 *            移動後の座標
	 */
	public ModelConstraintChangeCommand(ModelElement model, Rectangle constraint) {
		this.model = model;
		this.constraint = constraint;
		this.oldConstraint = model.getConstraint();
	}

	/**
	 * コンストラクタ（テンキー操作時）
	 * 
	 * @param model
	 *            移動対象
	 * @param newX
	 *            移動後のX座標
	 * @param newY
	 *            移動後のY座標
	 */
	public ModelConstraintChangeCommand(ModelElement model, int newX, int newY) {
		this.model = model;
		this.oldConstraint = model.getConstraint();
		this.constraint = model.getConstraint().getTranslated(newX, newY);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		model.setConstraint(constraint);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		model.setConstraint(oldConstraint);
	}
}
