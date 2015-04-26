package jp.sourceforge.tmdmaker.property;

import jp.sourceforge.tmdmaker.TMDEditor;

import org.eclipse.gef.commands.Command;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;

public abstract class AbstractPropertySource implements IPropertySource {
	
	private TMDEditor editor;

	public AbstractPropertySource(TMDEditor editor) {
		this.editor = editor;
	}

	public void resetPropertyValue(Object paramObject) {
	}

	public void setPropertyValue(Object id, Object value) {
	}

	abstract protected Command createSetPropertyCommand(Object id, Object value);
	
	@Override
	public boolean isPropertySet(Object id) {
		for(IPropertyDescriptor descriptor: getPropertyDescriptors())
		{
			if (descriptor.equals(id))
			{
				return true;
			}
		}
		return false;
	}
	
	protected String canonicalize(String property)
	{
		return property != null ? property : "";
	}
}
