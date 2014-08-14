package jp.sourceforge.tmdmaker.treeeditpart;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import jp.sourceforge.tmdmaker.TMDPlugin;

import org.eclipse.swt.graphics.Image;
import org.slf4j.LoggerFactory;
import org.eclipse.gef.editparts.AbstractTreeEditPart;

public class FolderTreeEditPart<T> extends AbstractTreeEditPart implements PropertyChangeListener {
	
	private static org.slf4j.Logger logger = LoggerFactory.getLogger(FolderTreeEditPart.class);
	
	private String title;
	
	public FolderTreeEditPart(String text){
	    this.title = text;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> getModel() {
		return (List<T>) super.getModel();
	}

	@SuppressWarnings("unchecked")
	@Override
	protected List<T> getModelChildren() {
		return (List<T>)super.getModel();
	}
	
	@Override
	protected String getText() {
		return title;
	}
	
	@Override
	protected Image getImage() {
		return TMDPlugin.getImage("icons/outline/folder.png");
	}

	@Override
	public void activate() {
		super.activate();
	}

	@Override
	public void deactivate() {
		super.deactivate();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		logger.debug(getClass() + "." + evt.getPropertyName());
	}
}
