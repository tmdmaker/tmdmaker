package jp.sourceforge.tmdmaker.property;

import jp.sourceforge.tmdmaker.TMDEditor;
import jp.sourceforge.tmdmaker.model.KeyModel;

import org.eclipse.gef.commands.Command;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

public class KeyModelPropertySource extends AbstractPropertySource {
	
	private KeyModel keymodel;
	
	public KeyModelPropertySource(TMDEditor editor, KeyModel keymodel) {
		super(editor);
		this.keymodel = keymodel;
	}

	@Override
	public Object getEditableValue() {
		return this.keymodel;
	}
	
	static private IPropertyDescriptor[] propertyFields;
	static {
		propertyFields = new IPropertyDescriptor[] {
				new TextPropertyDescriptor("Name", "名前"),
				new TextPropertyDescriptor("IsMasterKey", "マスターキー"),
				new TextPropertyDescriptor("IsUnique", "ユニーク制約")
		};
	}
	
	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		return propertyFields;
	}

	@Override
	public Object getPropertyValue(Object id) {
		if (id.equals("Name")) {
			return canonicalize(keymodel.getName());
		}
		if (id.equals("IsMasterKey")) {
			return keymodel.isMasterKey() ? "はい" : "いいえ";
		}
		if (id.equals("IsUnique")) {
			return keymodel.isUnique() ? "はい" : "いいえ";
		}
		return null;
	}

	@Override
	protected Command createSetPropertyCommand(Object id, Object value) {
		// TODO Auto-generated method stub
		return null;
	}

}
