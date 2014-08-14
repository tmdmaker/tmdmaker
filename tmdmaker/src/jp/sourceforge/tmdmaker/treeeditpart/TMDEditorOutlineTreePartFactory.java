package jp.sourceforge.tmdmaker.treeeditpart;

import java.util.List;

import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Attribute;
import jp.sourceforge.tmdmaker.model.Detail;
import jp.sourceforge.tmdmaker.model.Diagram;
import jp.sourceforge.tmdmaker.model.Entity;
import jp.sourceforge.tmdmaker.model.Identifier;
import jp.sourceforge.tmdmaker.model.IdentifierRef;
import jp.sourceforge.tmdmaker.model.KeyModel;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;
import org.slf4j.LoggerFactory;

/*
 * TMDモデルのツリービュー用のEditPart
 * 
 * ツリービューでは、 entity、対照表、対応表を一覧することができる。
 * 各entityを展開すると、個体指定子、属性がぶらさがっている。 
 */
public class TMDEditorOutlineTreePartFactory implements EditPartFactory {
	
	/** logging */
	private static org.slf4j.Logger logger = LoggerFactory.getLogger(TMDEditorOutlineTreePartFactory.class);
	
	/**
	 * @param context
	 *            コンテキスト
	 * @param model
	 *            TMのモデル
	 * @return EditPart modelに対応したEditPart
	 */
	@Override
	public final EditPart createEditPart(final EditPart context, final Object model) {
		EditPart part = null;
		if (model instanceof Diagram) {
			logger.debug("モデル Diagram 用の EditPart を返しました");
			part = new DiagramTreeEditPart();
		}else if (model instanceof Entity) {
			logger.debug("モデルEntity 用の EditPart を返しました");
			logger.debug(((Entity)model).getName());
			part = new EntityTreeEditPart();
		}else if (model instanceof Detail) {
			logger.debug("モデルDetail 用の EditPart を返しました");
			logger.debug(((Detail)model).getName());
			part = new DetailTreeEditPart();
		}else if (model instanceof AbstractEntityModel) {
			logger.debug("モデルAbstractEntityModel 用の EditPart を返しました");
			logger.debug(((AbstractEntityModel)model).getName());
			part = new AbstractEntityModelTreeEditPart();
		}else if (model instanceof IdentifierRef) {
			logger.debug("モデルIdentifierRef 用の EditPart を返しました");
			logger.debug(((IdentifierRef)model).getName());
			part = new IdentifierRefTreeEditPart();
		}else if (model instanceof Identifier) {
			logger.debug("モデルIdentifier 用の EditPart を返しました");
			logger.debug(((Identifier)model).getName());
			part = new IdentifierTreeEditPart();
		}else if (model instanceof Attribute) {
			logger.debug("モデルAttribute 用の EditPart を返しました");
			logger.debug(((Attribute)model).getName());
			part = new AttributeTreeEditPart();
		}else if (model instanceof KeyModel) {
			logger.debug("KeyModel用の EditPart を返しました");
			logger.debug(((KeyModel)model).getName());
			part = new KeyModelTreeEditPart();
		}else if (model instanceof List<?>){
			logger.debug("フォルダー用EditPartを準備します。");
			List<?> list = (List<?>)model;
			if (list.size() > 0){
				if (list.get(0) instanceof Identifier){				
					logger.debug("個体指定子のフォルダー用EditPartを返しました。");
				    part = new FolderTreeEditPart<Identifier>("個体指定子");
				} else if (list.get(0) instanceof Attribute){
					logger.debug("属性のフォルダー用EditPartを返しました。");
				    part = new FolderTreeEditPart<Attribute>("属性");
				} else if (list.get(0) instanceof KeyModel){
					logger.debug("キーのフォルダー用EditPartを返しました。");
				    part = new FolderTreeEditPart<KeyModel>("キー定義");					
				}
			}
		}
		if (part != null) {
			part.setModel(model);
		}
		return part;
	}
}
