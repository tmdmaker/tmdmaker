package jp.sourceforge.tmdmaker.treeeditpart;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.sourceforge.tmdmaker.TMDPlugin;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.CombinationTable;
import jp.sourceforge.tmdmaker.model.ConnectableElement;
import jp.sourceforge.tmdmaker.model.Detail;
import jp.sourceforge.tmdmaker.model.IAttribute;
import jp.sourceforge.tmdmaker.model.Identifier;
import jp.sourceforge.tmdmaker.model.KeyModel;
import jp.sourceforge.tmdmaker.model.Laputa;
import jp.sourceforge.tmdmaker.model.MappingList;
import jp.sourceforge.tmdmaker.model.ModelElement;
import jp.sourceforge.tmdmaker.model.MultivalueOrEntity;
import jp.sourceforge.tmdmaker.model.RecursiveTable;
import jp.sourceforge.tmdmaker.model.ReusedIdentifier;
import jp.sourceforge.tmdmaker.model.SubsetEntity;
import jp.sourceforge.tmdmaker.model.VirtualEntity;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.editparts.AbstractTreeEditPart;
import org.eclipse.swt.graphics.Image;
import org.slf4j.LoggerFactory;

/**
 * @author ny@cosmichorror.org
 *
 */
public class AbstractEntityModelTreeEditPart<T extends AbstractEntityModel> extends AbstractTreeEditPart implements
		PropertyChangeListener {
	
	private static org.slf4j.Logger logger = LoggerFactory.getLogger(AbstractEntityModelTreeEditPart.class);
	
	private static Map<Type,String> icons = new HashMap<Type,String>();
	
	static {
	    icons.put(CombinationTable.class,   "icons/outline/combination_table.png");
	    icons.put(SubsetEntity.class,       "icons/outline/subset_resource.png");
	    icons.put(VirtualEntity.class,      "icons/outline/virtual_entity.png");
	    icons.put(MultivalueOrEntity.class, "icons/outline/multivalue_or.png");
	    icons.put(Detail.class,             "icons/outline/detail.png");
	    icons.put(RecursiveTable.class,     "icons/outline/recursive_table.png");
	    icons.put(MappingList.class,        "icons/outline/mapping_list.png");
	    icons.put(Laputa.class,             "icons/outline/laputa.png");
	}

	//getModel()でツリー要素の対象モデルのインスタンスが取れる。
	@Override
	public T getModel() {
		return (T)super.getModel();
	}

	List<List<?>> children = new ArrayList<List<?>>();
	List<Identifier> identifiers = new ArrayList<Identifier>();

	//子要素があるときは、getModelChildren()で子要素の一覧を返す。無いときは空のリストを返す。
	@Override
	protected List<List<?>> getModelChildren() {
		return children;
	}
	
	@Override
	public void setModel(Object model) {
		super.setModel(model);
		updateChildren();
	}
	
	protected void clearChildren(){
		children.clear();
		identifiers.clear();
	}
	
	protected void updateChildren() {
		clearChildren();
		setIdentifiers();
		setAttributes();
		setKeyModels();
	}
	
	protected void setIdentifiers(){
		for (ReusedIdentifier r: getModel().getReusedIdentifieres().values()){
			for (Identifier i : r.getIdentifires()){
				identifiers.add(i);
			}
		}
		if (identifiers != null && identifiers.size() != 0){
		    children.add(identifiers);
		}	
	}
	
	protected void setAttributes(){
		List<IAttribute> attributes = getModel().getAttributes();
		if (attributes != null && attributes.size() != 0){
			children.add(attributes);
		}
	}
	
	protected void setKeyModels(){
		List<KeyModel> keymodels = new ArrayList<KeyModel>();
		for (KeyModel key : getModel().getKeyModels()){
		    keymodels.add(key);
		}
		if (keymodels.size() > 0){
		    children.add(keymodels);
		}
	}
	
	@Override
	protected String getText() {
		ModelElement model = getModel();
		if (model.getName() == null)
		{
			return "";
		}
		else{
			return model.getName();		
		}
	}

	@Override
	protected Image getImage() {		
		if (icons.containsKey(getModel().getClass())){
		     String path = icons.get(getModel().getClass());
		     return TMDPlugin.getImage(path);
		}
		return super.getImage();
	}
	
	@Override
	public void activate() {
		super.activate();
		getModel().addPropertyChangeListener(this);
	}

	@Override
	public void deactivate() {
		getModel().removePropertyChangeListener(this);
		super.deactivate();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		logger.debug(getClass() + "." + evt.getPropertyName());

		if (evt.getPropertyName().equals(ModelElement.PROPERTY_NAME)) {
			handleNameChange(evt);
		} else if (evt.getPropertyName().equals(ModelElement.PROPERTY_CONSTRAINT)) {
			handleConstraintChange(evt);
		} else if (evt.getPropertyName().equals(AbstractEntityModel.PROPERTY_ATTRIBUTE)) {
			handleAttributeChange(evt);
		} else if (evt.getPropertyName().equals(ConnectableElement.P_SOURCE_CONNECTION)) {
			handleSourceConnectionChange(evt);
		} else if (evt.getPropertyName().equals(ConnectableElement.P_TARGET_CONNECTION)) {
			handleTargetConnectionChange(evt);
		} else if (evt.getPropertyName().equals(AbstractEntityModel.PROPERTY_REUSED)) {
			handleReUseKeyChange(evt);
		} else if (evt.getPropertyName().equals(AbstractEntityModel.PROPERTY_IDENTIFIER)) {
			handleIdentifierChange(evt);
		} else if (evt.getPropertyName().equals(AbstractEntityModel.PROPERTY_ATTRIBUTE_REORDER)) {
			logger.warn("Handle Reorder Occured.");
			refreshChildren();
		} else {
			logger.warn("Not Handle Event Occured.");
		}
	}
	
	/**
	 * 名称変更イベント処理
	 * 
	 * @param evt
	 *            発生したイベント情報
	 */
	protected void handleNameChange(PropertyChangeEvent evt) {
		refreshVisuals();
	}

	/**
	 * 制約変更イベント処理
	 * 
	 * @param evt
	 *            発生したイベント情報
	 */
	protected void handleConstraintChange(PropertyChangeEvent evt) {
		refreshVisuals();
	}

	/**
	 * 属性変更イベント処理
	 * 
	 * @param evt
	 *            発生したイベント情報
	 */
	protected void handleAttributeChange(PropertyChangeEvent evt) {
		refreshVisuals();
		refreshChildren();
	}

	/**
	 * 個体指定子変更イベント処理
	 * 
	 * @param evt
	 *            発生したイベント情報
	 */
	protected void handleIdentifierChange(PropertyChangeEvent evt) {
		refreshVisuals();
	}

	/**
	 * 属性順序変更イベント処理
	 * 
	 * @param evt
	 *            発生したイベント情報
	 */
	protected void handleAttributeReorder(PropertyChangeEvent evt) {
		refreshVisuals();
	}

	/**
	 * ReUseKey変更イベント処理
	 * 
	 * @param evt
	 *            発生したイベント情報
	 */
	protected void handleReUseKeyChange(PropertyChangeEvent evt) {
		refreshVisuals();
	}
	/**
	 * 接続元コネクション変更イベント処理
	 * 
	 * @param evt
	 *            発生したイベント情報
	 */
	protected void handleSourceConnectionChange(PropertyChangeEvent evt) {
		//refreshSourceConnections();
	}

	/**
	 * 接続先コネクション変更イベント処理
	 * 
	 * @param evt
	 *            発生したイベント情報
	 */
	protected void handleTargetConnectionChange(PropertyChangeEvent evt) {
		//refreshTargetConnections();
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#refreshVisuals()
	 */
	@Override
	protected void refreshVisuals() {
		logger.debug(getClass().toString() + "#refreshVisuals()");
		super.refreshVisuals();
		refreshChildren();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#refreshChildren()
	 */
	@Override
	protected void refreshChildren() {
		logger.debug(getClass().toString() + "#refreshChildren()");
		updateChildren();
		int i;

		@SuppressWarnings("unchecked")
		List<FolderTreeEditPart<?>> children = getChildren();
		int size = children.size();
		Map<List<?>,FolderTreeEditPart<?>> modelToEditPart = new HashMap<List<?>,FolderTreeEditPart<?>>();
		if (size > 0) {
			modelToEditPart = new HashMap<List<?>,FolderTreeEditPart<?>>(size);
			for (FolderTreeEditPart<?> e: children) {
				modelToEditPart.put(e.getModel(), e);
			}
		}

		List<List<?>> modelObjects = getModelChildren();
		for (i = 0; i < modelObjects.size(); i++) {
			List<?> model = modelObjects.get(i);

			//TODO: 要調査AbstractEditPart#refreshChildren では、EditPartの再作成回避のためのチェックがあったが、ここでそれをやると新規作成時に ReUsedのキーがうまく反映されないので、チェックは行わない。

			// Look to see if the EditPart is already around but in the
			// wrong location
			FolderTreeEditPart<?> editPart = modelToEditPart.get(model);

			if (editPart != null){
				reorderChild(editPart, i);
			}
			else {
				// An EditPart for this model doesn't exist yet. Create and
				// insert one.
				editPart = (FolderTreeEditPart<?>)createChild(model);
				addChild(editPart, i);
			}
		}

		// remove the remaining EditParts
		size = children.size();
		if (i < size) {
			List<FolderTreeEditPart<?>> trash = new ArrayList<FolderTreeEditPart<?>>(size - i);
			for (; i < size; i++)
				trash.add(children.get(i));
			for (i = 0; i < trash.size(); i++) {
				EditPart ep = (EditPart) trash.get(i);
				removeChild(ep);
			}
		}
	}

}
