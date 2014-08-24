package jp.sourceforge.tmdmaker.property;

import org.eclipse.ui.views.properties.IPropertySource;
import jp.sourceforge.tmdmaker.TMDEditor;

public interface IPropertyAvailable {
	IPropertySource getPropertySource(TMDEditor editor);
}
