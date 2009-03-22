package jp.sourceforge.tmdmaker.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 
 * @author nakaG
 *
 */
public class ReuseKey {
	private List<Identifier> identifieres = new ArrayList<Identifier>();
	public ReuseKey() {}
	public ReuseKey(Identifier identifier) {
		this.identifieres.add(identifier);
	}
	public ReuseKey(List<Identifier> identifieres) {
		identifieres.addAll(identifieres);
	}
	public List<Identifier> getIdentifires() {
		return Collections.unmodifiableList(identifieres);
	}
	public void addAll(List identifieres) {
		this.identifieres.addAll(identifieres);
	}
	public void dispose() {
		identifieres.clear();
	}
}
