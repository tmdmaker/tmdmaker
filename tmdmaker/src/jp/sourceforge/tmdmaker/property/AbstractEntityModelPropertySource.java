package jp.sourceforge.tmdmaker.property;

import org.eclipse.gef.commands.Command;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import jp.sourceforge.tmdmaker.TMDEditor;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.CombinationTable;
import jp.sourceforge.tmdmaker.model.Detail;
import jp.sourceforge.tmdmaker.model.Laputa;
import jp.sourceforge.tmdmaker.model.MappingList;
import jp.sourceforge.tmdmaker.model.MultivalueOrEntity;
import jp.sourceforge.tmdmaker.model.RecursiveTable;
import jp.sourceforge.tmdmaker.model.SubsetEntity;
import jp.sourceforge.tmdmaker.model.VirtualEntity;

public class AbstractEntityModelPropertySource extends AbstractPropertySource {

	private AbstractEntityModel model;
	
	public AbstractEntityModelPropertySource(TMDEditor editor, AbstractEntityModel model) {
		super(editor);
		this.model = model;
	}

	@Override
	public Object getEditableValue() {
		return this.model;
	}

	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		return new IPropertyDescriptor[] {
				new TextPropertyDescriptor("Name", "名前"),
				new TextPropertyDescriptor("ImplementName",	"実装名"),
        		new TextPropertyDescriptor("EntityType",	"種類"),
        		new TextPropertyDescriptor("Implement",	"実装")};
	}

	@Override
	public Object getPropertyValue(Object id) {
		if (id.equals("Name")) {
			return model.getName() != null ? model.getName() : "";
		}
		if (id.equals("ImplementName")) {
			return model.getImplementName() != null ? model.getImplementName() : "";
		}
		if (id.equals("Implement")) {
			return model.isNotImplement() ? "実装しない" : "実装する";
		}
		if (id.equals("EntityType")) {
			if (model instanceof CombinationTable){
				return "対照表";
			}else if (model instanceof SubsetEntity){
				return "サブセット";
			}else if (model instanceof VirtualEntity){
				return "みなしエンティティ";
			}else if (model instanceof MultivalueOrEntity){
				return "多値のOR";
			}else if (model instanceof Detail){
				return "多値のAND(DTL)";		
			}else if (model instanceof RecursiveTable){
				return "再帰表";
			}else if (model instanceof MappingList){
				return "対応表";
			}else if (model instanceof Laputa){
				return "ラピュタ";
			}
		}
		return null;
	}

	@Override
	public boolean isPropertySet(Object id) {
		if (id.equals("Name")) {
			return true;
		}
		if (id.equals("Implement")) {
			return true;
		}
		if (id.equals("ImplementName")) {
			return true;
		}
		if (id.equals("EntityType")) {
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
