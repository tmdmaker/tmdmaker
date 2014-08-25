/*
 * Copyright 2009-2011 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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
package jp.sourceforge.tmdmaker.figure;

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
import org.eclipse.swt.graphics.Color;

/**
 * エンティティ（表）Figure
 * 
 * @author nakaG
 * 
 */
public class EntityFigure extends Figure {
	private Label name;
	private Label type;
	private EntityTitleCompartmentFigure titleCompartmentFigure;
	private EntityLayoutCompartmentFigure compartmentFigure;
	private CompartmentFigure identifierCompartmentFigure;
	private CompartmentFigure attributeCompartmentFigure;

	/**
	 * コンストラクタ
	 */
	public EntityFigure() {
		this(false);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param notImplement
	 *            実装するか？
	 */
	public EntityFigure(boolean notImplement) {
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
	 * @return the attributeCompartmentFigure
	 */
	public CompartmentFigure getAttributeCompartmentFigure() {
		return attributeCompartmentFigure;
	}

	public void setEntityName(String entityName) {
		this.name.setText(entityName);
	}

	public void setEntityType(String entityType) {
		this.type.setText(entityType);
	}

	private Label createAttributeLabel(String name) {
		Label tmp = new Label(name);
		tmp.setBorder(new MarginBorder(2, 2, 2, 2));
		tmp.setLabelAlignment(PositionConstants.LEFT);
		return tmp;
	}

	public void setIdentifier(String identifier) {
		this.identifierCompartmentFigure.add(createAttributeLabel(identifier));
	}

	public void addRelationship(String relationship) {
		this.identifierCompartmentFigure.add(createAttributeLabel(relationship + "(R)"));
	}
	
	public void addRelationship(List<String> relationship) {
		for(String r:relationship){
			addRelationship(r);
		}
	}
	
	public void removeAllRelationship() {
		this.identifierCompartmentFigure.removeAll();
	}

	public void addAttribute(String attribute) {
		this.attributeCompartmentFigure.add(createAttributeLabel(attribute));
	}

	public void removeAllAttributes() {
		this.attributeCompartmentFigure.removeAll();
	}

	/**
	 * @param notImplement
	 *            the notImplement to set
	 */
	public void setNotImplement(boolean notImplement) {
		this.setBorder(new EntityFigureBorder(notImplement));
	}
	
	/**
	 * モデルの色を設定する
	 * @param foreGround 前景色
	 * @param backGround 背景色
	 */
	public void setColor(Color foregroundColor, Color backgroundColor)
	{
		setForegroundColor(foregroundColor);
		setBackgroundColor(backgroundColor);
	}

	private static class EntityTitleCompartmentFigure extends Figure {
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

	private class EntityFigureBorder extends LineBorder {
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
			if (identifierCompartmentFigure.getChildren().size() > 0
					|| attributeCompartmentFigure.getChildren().size() > 0) {
				Rectangle rect1 = titleCompartmentFigure.getBounds();
				Rectangle rect2 = identifierCompartmentFigure.getBounds();
				Point p = tempRect.getTopLeft().getCopy();
				p.x = p.x + rect2.width + 2;
				p.y = p.y + rect1.height + 2;
				Point p2 = tempRect.getBottomLeft().getCopy();
				p2.x = p2.x + rect2.width + 2;
				graphics.drawLine(p, p2);
			}
			if (notImplement) {
				graphics.drawLine(tempRect.getTopLeft(), tempRect.getBottomRight());
				graphics.drawLine(tempRect.getBottomLeft(), tempRect.getTopRight());
			}
		}

	}

	private static class EntityLayoutCompartmentFigure extends Figure {
		public EntityLayoutCompartmentFigure() {
			ToolbarLayout layout = new ToolbarLayout(true);
			layout.setMinorAlignment(ToolbarLayout.ALIGN_TOPLEFT);
			layout.setStretchMinorAxis(true);
			layout.setSpacing(0);
			setLayoutManager(layout);
			setBorder(new EntityLayoutCompartmentFigureBorder());
		}
	}

	private static class EntityLayoutCompartmentFigureBorder extends AbstractBorder {
		public Insets getInsets(IFigure figure) {
			return new Insets(0, 1, 0, 1);
		}

		public void paint(IFigure figure, Graphics graphics, Insets insets) {
			if (((IFigure) figure.getChildren().get(0)).getChildren().size() > 0) {
				graphics.drawLine(getPaintRectangle(figure, insets).getTopLeft(),
						tempRect.getTopRight());
			}
		}
	}

	private static class CompartmentFigure extends Figure {

		public CompartmentFigure() {
			ToolbarLayout layout = new ToolbarLayout();
			layout.setMinorAlignment(ToolbarLayout.ALIGN_TOPLEFT);
			layout.setStretchMinorAxis(true);
			layout.setSpacing(0);
			setLayoutManager(layout);
		}

	}
}
