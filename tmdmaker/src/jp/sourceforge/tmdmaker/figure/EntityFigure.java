package jp.sourceforge.tmdmaker.figure;

import org.eclipse.draw2d.AbstractBorder;
import org.eclipse.draw2d.BorderLayout;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.geometry.Insets;

public class EntityFigure extends Figure {
	private Label name;
	private Label type;
	private EntityTitleCompartmentFigure titleCompartmentFigure;
	private EntityLayoutCompartmentFigure compartmentFigure;
	private CompartmentFigure identifierCompartmentFigure;
	private CompartmentFigure attributeCompartmentFigure;
	
	public EntityFigure() {
		this.name = new Label();
		this.name.setBorder(new MarginBorder(2, 2, 0, 2));
		this.type = new Label();
		this.titleCompartmentFigure = new EntityTitleCompartmentFigure();
		this.compartmentFigure = new EntityLayoutCompartmentFigure();
		this.identifierCompartmentFigure = new CompartmentFigure();
		this.attributeCompartmentFigure = new CompartmentFigure();

		ToolbarLayout layout = new ToolbarLayout();
		setLayoutManager(layout);
		setBorder(new LineBorder(ColorConstants.black, 1));
		setOpaque(true);
		
		this.titleCompartmentFigure.setEntityName(name);
		this.titleCompartmentFigure.setEntityType(type);
		add(this.titleCompartmentFigure);
		add(this.compartmentFigure);
		this.identifierCompartmentFigure.setBorder(new IdentifierCompartmentFigureBorder());
		this.attributeCompartmentFigure.setBorder(new AttributeCompartmentFigureBorder());
		this.compartmentFigure.add(identifierCompartmentFigure);
		this.compartmentFigure.add(attributeCompartmentFigure);
//		Label tmp = new Label("日本語");
//		tmp.setBorder(new MarginBorder(2, 2, 0, 2));
//		attributeCompartmentFigure.add(tmp);
//		tmp = new Label("attribute2");
//		tmp.setBorder(new MarginBorder(2, 2, 0, 2));
//		attributeCompartmentFigure.add(tmp);
	}
	
	public void setEntityName(String entityName) {
		this.name.setText(entityName);
	}

	public void setEntityType(String entityType) {
		this.type.setText(entityType);
//		setColor(entityType);
	}
	private void setColor(String entityType) {
		if (entityType.equals("R")) {
			setBackgroundColor(ColorConstants.lightBlue);
		} else if (entityType.equals("E")) {
			setBackgroundColor(ColorConstants.red);
		}
	}
	private Label createAttributeLabel(String name) {
		Label tmp = new Label(name);
		tmp.setBorder(new MarginBorder(2, 2, 0, 2));
		return tmp;
	}
	public void setIdentifier(String identifier) {

		this.identifierCompartmentFigure.add(createAttributeLabel(identifier), 0);
	}
	public void addRelationship(String relationship) {
		this.identifierCompartmentFigure.add(createAttributeLabel(relationship + "(R)"));
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
	private static class EntityTitleCompartmentFigure extends Figure {
		public EntityTitleCompartmentFigure() {
			setLayoutManager(new BorderLayout());
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
	private static class EntityLayoutCompartmentFigure extends Figure {
		public EntityLayoutCompartmentFigure(){
			ToolbarLayout layout = new ToolbarLayout(true);
			layout.setMinorAlignment(ToolbarLayout.ALIGN_TOPLEFT);
			layout.setStretchMinorAxis(true);
			layout.setSpacing(2);
			setLayoutManager(layout);
			setBorder(new EntityLayoutCompartmentFigureBorder());
		}
	}
	
	private static class EntityLayoutCompartmentFigureBorder extends AbstractBorder {
		public Insets getInsets(IFigure figure) {
			return new Insets(1, 0, 0, 0);
		}
		public void paint(IFigure figure, Graphics graphics, Insets insets) {
			graphics.drawLine(getPaintRectangle(figure, insets).getTopLeft(), tempRect.getTopRight());
		}
	}
	private static class CompartmentFigure extends Figure {
		
		public CompartmentFigure() {
			ToolbarLayout layout = new ToolbarLayout();
			layout.setMinorAlignment(ToolbarLayout.ALIGN_TOPLEFT);
			layout.setStretchMinorAxis(false);
			layout.setSpacing(2);
			setLayoutManager(layout);
		}

	}
	private static class IdentifierCompartmentFigureBorder extends AbstractBorder {
		public Insets getInsets(IFigure figure) {
			return new Insets(0, 1, 0, 1);
		}
		public void paint(IFigure figure, Graphics graphics, Insets insets) {
			graphics.drawLine(getPaintRectangle(figure, insets).getTopRight(), tempRect.getBottomRight());
//			graphics.drawLine(getPaintRectangle(figure, insets).getTopLeft(), tempRect.getBottomRight());
//			graphics.drawRectangle(getPaintRectangle(figure, insets));
		}
	}
	private static class AttributeCompartmentFigureBorder extends AbstractBorder {

		public Insets getInsets(IFigure figure) {
			return new Insets(0, 1, 0, 1);
		}

		public void paint(IFigure figure, Graphics graphics, Insets insets) {
			graphics.drawLine(getPaintRectangle(figure, insets).getTopLeft(), tempRect.getBottomLeft());
		}
	
	}

}

