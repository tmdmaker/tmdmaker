package jp.sourceforge.tmdmaker.property;

import jp.sourceforge.tmdmaker.TMDEditor;
import jp.sourceforge.tmdmaker.model.Identifier;

import org.eclipse.gef.commands.Command;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

public class IdentifierPropertySource extends AbstractPropertySource {
	
	private Identifier identifier;
	
	public IdentifierPropertySource(TMDEditor editor, Identifier identifier) {
		super(editor);
		this.identifier = identifier;
	}

	@Override
	public Object getEditableValue() {
		return this.identifier;
	}
	
	static private IPropertyDescriptor[] propertyFields;
	static {
		propertyFields = new IPropertyDescriptor[] {
				new TextPropertyDescriptor("Name", "名前"),
				new TextPropertyDescriptor("ImplementName",	"実装名"),
				new TextPropertyDescriptor("DataTypeDeclaration",	"型"),
				new TextPropertyDescriptor("Size",	"長さ"),
				new TextPropertyDescriptor("Scale",	"少数")
		};
	}
	
	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		return propertyFields;
	}

	@Override
	public Object getPropertyValue(Object id) {
		if (id.equals("Name")) {
			return canonicalize(identifier.getName());
		}
		if (id.equals("ImplementName")) {
			return canonicalize(identifier.getImplementName());
		}
		if (id.equals("DataTypeDeclaration")) {
			return identifier.getDataTypeDeclaration() != null ? identifier.getDataTypeDeclaration().getLogicalType().toString() : "";
		}
		if (id.equals("Size")){
		    return identifier.getDataTypeDeclaration() != null ? identifier.getDataTypeDeclaration().getSize() : "";
		}
		if (id.equals("Scale")){
		    return identifier.getDataTypeDeclaration() != null ? identifier.getDataTypeDeclaration().getScale() : "";
		}
		return null;
	}

	@Override
	protected Command createSetPropertyCommand(Object id, Object value) {
		// TODO Auto-generated method stub
		return null;
	}
}
