package org.tmdmaker.core.model;

public interface ModelFilter {
	<T extends ModelElement> T filter(T model);
}
