package org.tmdmaker.ui.editor.draw2d.figure.node;

import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.PositionConstants;
import org.tmdmaker.core.model.Identifier;

public class IdentifierFigure extends Label {

	public IdentifierFigure() {
		this.setBorder(new MarginBorder(2, 2, 2, 2));
		this.setLabelAlignment(PositionConstants.LEFT);
	}

	public void update(Identifier identifier) {
		this.setText(this.createIdentifierName(identifier));
	}

	private String createIdentifierName(Identifier identifier) {
		StringBuilder name = new StringBuilder(identifier.getName());
		if (identifier.isDerivation()) {
			name.append("(D)"); //$NON-NLS-1$
		}
		return name.toString();
	}

}
