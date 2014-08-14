package jp.sourceforge.tmdmaker.property;

import jp.sourceforge.tmdmaker.TMDEditor;
import jp.sourceforge.tmdmaker.model.Entity;

public class EntityPropertySource extends AbstractEntityModelPropertySource {

	private Entity model;
	
	public EntityPropertySource(TMDEditor editor, Entity model) {
		super(editor, model);
		this.model = model;
	}

	@Override
	public Object getEditableValue() {
		return this.model;
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
			return model.getEntityType().getTypeName();
		}
		return null;
	}
}
