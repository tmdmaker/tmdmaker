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
package org.tmdmaker.ui.editor.gef3.editparts.node;

import java.beans.PropertyChangeEvent;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.draw2d.text.TextFlow;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.editpolicies.DirectEditPolicy;
import org.eclipse.gef.requests.DirectEditRequest;
import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.gef.tools.CellEditorLocator;
import org.eclipse.gef.tools.DirectEditManager;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.tmdmaker.core.model.Diagram;
import org.tmdmaker.core.model.other.Memo;
import org.tmdmaker.ui.editor.draw2d.figure.node.MemoFigure;
import org.tmdmaker.ui.editor.gef3.commands.MemoChangeCommand;

/**
 * メモのコントローラ
 * 
 * @author nakag
 *
 */
public class MemoEditPart extends AbstractModelEditPart<Memo> {

	/**
	 * コンストラクタ
	 * 
	 * @param model
	 *            メモ
	 */
	public MemoEditPart(Memo model) {
		super();
		setModel(model);
	}

	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#createFigure()
	 */
	@Override
	protected IFigure createFigure() {
		logger.debug("{}#createFigure()", getClass());
		return new MemoFigure();
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see org.tmdmaker.ui.editor.gef3.editparts.node.AbstractModelEditPart#updateFigure(org.eclipse.draw2d.IFigure)
	 */
	@Override
	protected void updateFigure(IFigure figure) {
		logger.debug("{}#updateFigure()", getClass());
		MemoFigure memoFigure = (MemoFigure) getFigure();
		Memo memo = getModel();
		memoFigure.setMemo(memo.getMemo());
		memoFigure.setupColor();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#performRequest(org.eclipse.gef.Request)
	 */
	@Override
	public void performRequest(Request request) {
		logger.debug("{} {}", getClass(), request.getType());
		if (REQ_OPEN.equals(request.getType())) {
			onDirectEdit();
		} else if (REQ_DIRECT_EDIT.equals(request.getType())) {
			onDirectEdit();
		} else {
			super.performRequest(request);
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.tmdmaker.ui.editor.gef3.editparts.node.AbstractTMDEditPart#onDirectEdit()
	 */
	@Override
	protected void onDirectEdit() {
		logger.debug("{}#onDirectEdit()", getClass());
		MemoFigure figure = (MemoFigure) getFigure();
		TextFlow label = figure.getMemoTextFlow();
		MemoDirectEditManager manager = new MemoDirectEditManager(this, label);
		manager.show();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.tmdmaker.ui.editor.gef3.editparts.node.AbstractModelEditPart#propertyChange(java.beans.PropertyChangeEvent)
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		logger.debug("{}#propertyChange() {}", getClass(), evt.getPropertyName());

		if (evt.getPropertyName().equals(Memo.PROPERTY_MEMO)) {
			refreshVisuals();
		} else {
			super.propertyChange(evt);
		}
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
	 */
	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new MemoDirectEditPolicy());
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new MemoDeleteEditPolicy());
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.tmdmaker.ui.editor.gef3.editparts.node.AbstractModelEditPart#canAutoSize()
	 */
	@Override
	public boolean canAutoSize() {
		return ((MemoFigure) getFigure()).canAutoSize();
	}

	private static class MemoDirectEditPolicy extends DirectEditPolicy {

		@Override
		protected Command getDirectEditCommand(DirectEditRequest request) {
			String newValue = (String) request.getCellEditor().getValue();
			Memo memo = (Memo) getHost().getModel();
			return new MemoChangeCommand(memo, newValue);
		}

		@Override
		protected void showCurrentEditValue(DirectEditRequest request) {
			MemoFigure figure = (MemoFigure) getHostFigure();
			String value = (String) request.getCellEditor().getValue();
			figure.setMemo(value);
		}
	}

	private static class MemoDirectEditManager extends DirectEditManager {

		public MemoDirectEditManager(GraphicalEditPart source, TextFlow label) {
			super(source, TextCellEditor.class, new Locator(label), label);
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.gef.tools.DirectEditManager#createCellEditorOn(org.eclipse.swt.widgets.Composite)
		 */
		@Override
		protected CellEditor createCellEditorOn(Composite composite) {
			return new TextCellEditor(composite, SWT.MULTI | SWT.WRAP);
		}

		@Override
		protected void initCellEditor() {
			TextFlow label = (TextFlow) getDirectEditFeature();
			String initialLabelText = label.getText();
			getCellEditor().setValue(initialLabelText);
		}
	}

	private static class Locator implements CellEditorLocator {
		private TextFlow label;

		public Locator(TextFlow label) {
			this.label = label;
		}

		@Override
		public void relocate(CellEditor celleditor) {
			Text text = (Text) celleditor.getControl();
			Point pref = text.computeSize(SWT.DEFAULT, SWT.DEFAULT);
			Rectangle rect = label.getBounds().getCopy();
			label.translateToAbsolute(rect);
			text.setBounds(rect.x - 1, rect.y - 1, pref.x + 1, pref.y + 1);
		}
	}

	private static class MemoDeleteEditPolicy extends ComponentEditPolicy {

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.gef.editpolicies.ComponentEditPolicy#createDeleteCommand(org.eclipse.gef.requests.GroupRequest)
		 */
		@Override
		protected Command createDeleteCommand(GroupRequest deleteRequest) {

			return new MemoDeleteCommand((Diagram) getHost().getParent().getModel(),
					(Memo) getHost().getModel());
		}

	}

	private static class MemoDeleteCommand extends Command {
		private Diagram diagram;
		private Memo memo;

		public MemoDeleteCommand(Diagram diagram, Memo memo) {
			super();
			this.diagram = diagram;
			this.memo = memo;
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.gef.commands.Command#execute()
		 */
		@Override
		public void execute() {
			diagram.removeChild(memo);
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.gef.commands.Command#undo()
		 */
		@Override
		public void undo() {
			diagram.addChild(memo);
		}
	}

	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see org.tmdmaker.ui.editor.gef3.editparts.node.AbstractModelEditPart#canCallSelectionAction()
	 */
	@Override
	public boolean canCallSelectionAction() {
		return false;
	}
}
