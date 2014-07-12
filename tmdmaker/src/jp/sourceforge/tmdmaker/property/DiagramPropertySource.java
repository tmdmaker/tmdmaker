package jp.sourceforge.tmdmaker.property;

import jp.sourceforge.tmdmaker.TMDEditor;
import jp.sourceforge.tmdmaker.model.Diagram;

import org.eclipse.gef.commands.Command;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

public class DiagramPropertySource extends AbstractPropertySource {
	
	Diagram diagram;
	
	public DiagramPropertySource(TMDEditor editor, Diagram diagram)
	{
		super(editor);
		this.diagram = diagram;
	}
	
	@Override
	public Object getEditableValue() {
		return this.diagram;
	}

	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		return new IPropertyDescriptor[] {
				new TextPropertyDescriptor("DatabaseName", "データベース名")};
	}

	@Override
	public Object getPropertyValue(Object id) {
		if (id.equals("DatabaseName")) {
			return diagram.getDatabaseName() != null ? diagram.getDatabaseName() : "";
		}
		return null;
	}

	@Override
	public boolean isPropertySet(Object id) {
		if (id.equals("DatabaseName")) {
			return true;
		}
		return false;
	}

	@Override
	public void resetPropertyValue(Object id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setPropertyValue(Object id, Object value) {
		// TODO Auto-generated method stub

	}

	@Override
	protected Command createSetPropertyCommand(Object id, Object value) {
		// TODO Auto-generated method stub
		return null;
	}

}
