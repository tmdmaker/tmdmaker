/*
 * Copyright 2009-2019 TMD-Maker Project <https://www.tmdmaker.org>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.tmdmaker.ui.editor.draw2d.figure.node;

import java.util.List;

import org.eclipse.draw2d.AbstractBorder;
import org.eclipse.draw2d.BorderLayout;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.tmdmaker.ui.preferences.appearance.ModelAppearance;

/**
 * T字形の箱を描画するFigure.
 * 
 * 形状描画に関する処理は本クラスに実装する.
 * 
 * @author nakag
 *
 */
public class TFormFigure extends Figure {

	protected Label name;
	protected Label type;
	protected EntityTitleCompartmentFigure titleCompartmentFigure;
	protected EntityLayoutCompartmentFigure compartmentFigure;
	protected CompartmentFigure identifierCompartmentFigure;
	protected CompartmentFigure attributeCompartmentFigure;

	/**
	 * コンストラクタ.
	 */
	public TFormFigure() {
		this(false);
	}

	/**
	 * コンストラクタ.
	 * 
	 * @param notImplement
	 */
	public TFormFigure(boolean notImplement) {
		this.name = new Label();
		this.type = new Label();
		this.titleCompartmentFigure = new EntityTitleCompartmentFigure();
		this.compartmentFigure = new EntityLayoutCompartmentFigure();
		this.identifierCompartmentFigure = new CompartmentFigure();
		this.attributeCompartmentFigure = new CompartmentFigure();

		ToolbarLayout layout = new ToolbarLayout();
		setLayoutManager(layout);
		setBorder(new EntityFigureBorder(notImplement));
		setOpaque(true);

		this.titleCompartmentFigure.setEntityName(name);
		this.titleCompartmentFigure.setEntityType(type);
		add(this.titleCompartmentFigure);
		add(this.compartmentFigure);
		this.attributeCompartmentFigure.setBorder(new MarginBorder(2, 2, 2, 2));

		this.compartmentFigure.add(identifierCompartmentFigure);
		this.compartmentFigure.add(attributeCompartmentFigure);

	}

	/**
	 * T字の上部タイトルの名称を設定する.
	 * 
	 * @param entityName
	 */
	protected void setEntityName(String entityName) {
		this.name.setText(entityName);
	}

	/**
	 * T字の上部右側の種類を設定する.
	 * 
	 * @param entityType
	 */
	protected void setEntityType(String entityType) {
		if (entityType != null) {
			this.type.setText(entityType);
		}
	}

	/**
	 * T字の左側に個体指定子を設定する.
	 * 
	 * @param identifier
	 */
	protected void addIdentifier(String identifier) {
		this.identifierCompartmentFigure.add(createAttributeLabel(identifier));
	}

	/**
	 * T字の左側に個体指定子を設定する.
	 * 
	 * @param identifierList
	 */
	protected void addIdentifier(List<String> identifierList) {
		for (String i : identifierList) {
			addIdentifier(i);
		}
	}

	/**
	 * T字の左側に個体指定子を設定する.
	 * 
	 * @param relationshipList
	 */
	protected void addRelationship(List<String> relationshipList) {
		for (String r : relationshipList) {
			addRelationship(r);
		}
	}

	/**
	 * T字の左側に(R)を設定する.
	 * 
	 * @param relationship
	 */
	protected void addRelationship(String relationship) {
		this.identifierCompartmentFigure.add(createAttributeLabel(relationship + "(R)")); //$NON-NLS-1$
	}

	/**
	 * T字の左側の個体指定子、(R)をすべて削除する.
	 */
	protected void removeAllRelationship() {
		this.identifierCompartmentFigure.removeAll();
	}

	/**
	 * モデルの実装可否を設定する.
	 * 
	 * @param notImplement
	 *            the notImplement to set
	 */
	protected void setNotImplement(boolean notImplement) {
		this.setBorder(new EntityFigureBorder(notImplement));
	}

	/**
	 * モデルの色を設定する.
	 * 
	 * @param appearance
	 *            モデルの外観設定情報.
	 */
	protected void setupColor(ModelAppearance appearance) {
		ColorConverter converter = new ColorConverter(appearance);
		setForegroundColor(converter.createForegroundColor());
		setBackgroundColor(converter.createBackgroundColor());
	}

	/**
	 * T字の上部タイトル部分のFigure.
	 */
	protected static class EntityTitleCompartmentFigure extends Figure {
		public EntityTitleCompartmentFigure() {
			setLayoutManager(new BorderLayout());
			setBorder(new MarginBorder(2, 2, 2, 2));
		}

		public void setEntityName(Figure name) {
			add(name);
			getLayoutManager().setConstraint(name, BorderLayout.CENTER);
		}

		public void setEntityType(Figure type) {
			add(type);
			getLayoutManager().setConstraint(type, BorderLayout.RIGHT);
		}
	}

	protected static class EntityLayoutCompartmentFigure extends Figure {
		public EntityLayoutCompartmentFigure() {
			ToolbarLayout layout = new ToolbarLayout(true);
			layout.setMinorAlignment(ToolbarLayout.ALIGN_TOPLEFT);
			layout.setStretchMinorAxis(true);
			layout.setSpacing(0);
			setLayoutManager(layout);
			setBorder(new EntityLayoutCompartmentFigureBorder());
		}
	}

	/**
	 * @return the attributeCompartmentFigure
	 */
	public CompartmentFigure getAttributeCompartmentFigure() {
		return attributeCompartmentFigure;
	}

	protected Label createAttributeLabel(String name) {
		Label tmp = new Label(name);
		tmp.setBorder(new MarginBorder(2, 2, 2, 2));
		tmp.setLabelAlignment(PositionConstants.LEFT);
		return tmp;
	}

	protected static class EntityLayoutCompartmentFigureBorder extends AbstractBorder {
		public Insets getInsets(IFigure figure) {
			return new Insets(0, 1, 0, 1);
		}

		public void paint(IFigure figure, Graphics graphics, Insets insets) {
			if (!((IFigure) figure.getChildren().get(0)).getChildren().isEmpty()
					|| !((IFigure) figure.getChildren().get(1)).getChildren().isEmpty()) {
				graphics.drawLine(getPaintRectangle(figure, insets).getTopLeft(),
						tempRect.getTopRight());
			}
		}
	}

	protected static class CompartmentFigure extends Figure {

		public CompartmentFigure() {
			ToolbarLayout layout = new ToolbarLayout();
			layout.setMinorAlignment(ToolbarLayout.ALIGN_TOPLEFT);
			layout.setStretchMinorAxis(true);
			layout.setSpacing(0);
			setLayoutManager(layout);
		}

	}

	protected class EntityFigureBorder extends LineBorder {
		private boolean notImplement;

		public EntityFigureBorder(boolean notImplement) {
			super();
			this.notImplement = notImplement;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.draw2d.Border#paint(org.eclipse.draw2d.IFigure,
		 *      org.eclipse.draw2d.Graphics, org.eclipse.draw2d.geometry.Insets)
		 */
		@Override
		public void paint(IFigure figure, Graphics graphics, Insets insets) {
			super.paint(figure, graphics, insets);
			boolean identifierNotEmpty = !identifierCompartmentFigure.getChildren().isEmpty();
			boolean attributeNotEmpty = !attributeCompartmentFigure.getChildren().isEmpty();
			if (identifierNotEmpty || attributeNotEmpty) {
				Rectangle rect1 = titleCompartmentFigure.getBounds();
				Rectangle rect2 = null;
				Point p = null;
				Point p2 = null;
				if (identifierNotEmpty) {
					rect2 = identifierCompartmentFigure.getBounds();
					p = tempRect.getTopLeft().getCopy();
					p.x = p.x + rect2.width + 2;
					p.y = p.y + rect1.height + 2;
					p2 = tempRect.getBottomLeft().getCopy();
					p2.x = p2.x + rect2.width + 2;
				} else {
					p = tempRect.getTopLeft().getCopy();
					p.x = p.x + 2;
					p.y = p.y + rect1.height + 2;
					p2 = tempRect.getBottomLeft().getCopy();
					p2.x = p2.x + 2;
				}
				graphics.drawLine(p, p2);
			}
			if (notImplement) {
				graphics.drawLine(tempRect.getTopLeft(), tempRect.getBottomRight());
				graphics.drawLine(tempRect.getBottomLeft(), tempRect.getTopRight());
			}
		}
	}
}