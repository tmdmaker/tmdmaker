package jp.sourceforge.tmdmaker.property;

import jp.sourceforge.tmdmaker.TMDEditor;
import jp.sourceforge.tmdmaker.model.Attribute;

import org.eclipse.gef.commands.Command;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

public class AttributePropertySource extends AbstractPropertySource {
	
	private Attribute attribute;
	
	public AttributePropertySource(TMDEditor editor, Attribute attribute) {
		super(editor);
		this.attribute = attribute;
	}

	@Override
	public Object getEditableValue() {
		return this.attribute;
	}

	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		return new IPropertyDescriptor[] {
				new TextPropertyDescriptor("Name", "名前"),
				new TextPropertyDescriptor("ImplementName",	"実装名"),
				new TextPropertyDescriptor("DataTypeDeclaration",	"型"),
				new TextPropertyDescriptor("Size",	"長さ"),
				new TextPropertyDescriptor("Scale",	"少数")
				};
	}

	@Override
	public Object getPropertyValue(Object id) {
		if (id.equals("Name")) {
			return attribute.getName() != null ? attribute.getName() : "";
		}
		if (id.equals("ImplementName")) {
			return attribute.getImplementName() != null ? attribute.getImplementName() : "";
		}
		if (id.equals("DataTypeDeclaration")) {
			return attribute.getDataTypeDeclaration() != null ? attribute.getDataTypeDeclaration().getLogicalType().toString() : "";
		}
		if (id.equals("Size")){
		    return attribute.getDataTypeDeclaration() != null ? attribute.getDataTypeDeclaration().getSize() : "";
		}
		if (id.equals("Scale")){
		    return attribute.getDataTypeDeclaration() != null ? attribute.getDataTypeDeclaration().getScale() : "";
		}
		return null;
		}

	@Override
	public boolean isPropertySet(Object id) {
		if (id.equals("Name")) {
			return true;
		}
		if (id.equals("ImplementName")) {
			return true;
		}
		if (id.equals("DataTypeDeclaration")) {
			return true;
		}
		if (id.equals("Size")){
		    return true;
		}
		if (id.equals("Scale")){
		    return true;
		}
		return false;
	}

	@Override
	protected Command createSetPropertyCommand(Object id, Object value) {
		// TODO Auto-generated method stub
		return null;
	}

}
