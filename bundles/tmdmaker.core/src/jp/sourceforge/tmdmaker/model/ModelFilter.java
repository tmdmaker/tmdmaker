package jp.sourceforge.tmdmaker.model;

public interface ModelFilter {
	<T extends ModelElement> T filter(T model);
}
