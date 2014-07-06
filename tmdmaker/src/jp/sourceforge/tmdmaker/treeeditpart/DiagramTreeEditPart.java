package jp.sourceforge.tmdmaker.treeeditpart;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Diagram;
import jp.sourceforge.tmdmaker.model.ModelElement;
import jp.sourceforge.tmdmaker.model.MultivalueAndSuperset;

import org.eclipse.gef.editparts.AbstractTreeEditPart;
import org.eclipse.swt.graphics.Image;

/**
 * @author ny@cosmichorror.org
 *
 */
public class DiagramTreeEditPart extends AbstractTreeEditPart implements
		PropertyChangeListener {
	
	@Override
	public Diagram getModel() {
		return (Diagram) super.getModel();
	}

	   //子要素があるときは、getModelChildren()で子要素の一覧を返す。無いときは空のリストを返す。
	@Override
	protected List<AbstractEntityModel> getModelChildren() {
		List<AbstractEntityModel> children = new ArrayList<AbstractEntityModel>(
				getModel().getChildren().size());

		for (ModelElement m : getModel().getChildren()) {
			if (m instanceof AbstractEntityModel && !(m instanceof MultivalueAndSuperset)){
				children.add((AbstractEntityModel)m);
			}
		}
		return children;
	}

	@Override
	protected String getText() {
		return getModel().getName();
	}

	@Override
	protected Image getImage() {
		return super.getImage(); // TODO アイコン
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
	public void propertyChange(PropertyChangeEvent event) {
		if (event.getPropertyName().equals(Diagram.PROPERTY_CHILDREN)) {
			refreshChildren();
		}
	}
}
