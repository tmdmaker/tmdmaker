/*
 * Copyright 2009-2011 TMD-Maker Project <http://tmdmaker.osdn.jp/>
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
package org.tmdmaker.ui.editor.gef3.editpolicies;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.LayerManager;
import org.eclipse.gef.editpolicies.ConnectionEditPolicy;
import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.gef.requests.LocationRequest;
import org.tmdmaker.core.model.AbstractConnectionModel;
import org.tmdmaker.ui.editor.gef3.editparts.relationship.AbstractRelationshipEditPart;

/**
 * リレーションシップEditPolicy
 * 
 * @author nakaG
 * 
 */
public class RelationshipEditPolicy extends ConnectionEditPolicy {
	/** リレーションを表示するfigure */
	private RelationHint hint;

	/** フィードバック時に表示するfigure */
	class RelationHint extends Figure {
		private Label from;
		private Label to;

		public RelationHint() {
			from = new Label();
			from.setBorder(new MarginBorder(2, 2, 2, 2));
			to = new Label();
			to.setBorder(new MarginBorder(2, 2, 2, 2));

			ToolbarLayout layout = new ToolbarLayout();
			layout.setStretchMinorAxis(false);
			layout.setMinorAlignment(ToolbarLayout.ALIGN_TOPLEFT);
			layout.setStretchMinorAxis(false);
			layout.setSpacing(2);

			setLayoutManager(layout);
			setBorder(new LineBorder());
			setOpaque(true);
			setBackgroundColor(ColorConstants.yellow);
			add(from);
			add(to);
		}

		public void setFromText(String text) {
			from.setText("from:" + text); //$NON-NLS-1$
		}

		public void setToText(String text) {
			to.setText("to:" + text); //$NON-NLS-1$
		}

		private Dimension max(Dimension from, Dimension to) {
			if (from.width >= to.width) {
				return from;
			} else {
				return to;
			}
		}

		/**
		 * 
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.draw2d.Figure#setLocation(org.eclipse.draw2d.geometry.Point)
		 */
		@Override
		public void setLocation(Point p) {

			// エディタをスクロールした場合を考慮して相対パスに変換
			Point np = p.getCopy();
			((AbstractRelationshipEditPart) getHost()).getConnectionFigure()
					.translateToRelative(np);
			np.x = np.x + 10;

			if (getLocation().equals(np))
				return;

			Dimension d = max(from.getPreferredSize(), to.getPreferredSize()).getCopy();

			d.width = (int) (d.width * 1.25);
			d.height = d.height * 2 + 2;
			setBounds(new Rectangle(np, d));
			super.setLocation(np);

		}

	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.editpolicies.AbstractEditPolicy#eraseTargetFeedback(org.eclipse.gef.Request)
	 */
	@Override
	public void eraseTargetFeedback(Request request) {
		if (request instanceof LocationRequest) {
			eraseHintFeedback((LocationRequest) request);
		} else {
			super.eraseTargetFeedback(request);
		}
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.editpolicies.AbstractEditPolicy#showTargetFeedback(org.eclipse.gef.Request)
	 */
	@Override
	public void showTargetFeedback(Request request) {
		if (request instanceof LocationRequest) {
			showHintFeedback((LocationRequest) request);
		} else {
			super.showTargetFeedback(request);
		}
	}

	/**
	 * リレーションヒントのフィードバックを表示する
	 * 
	 * @param request
	 *            リクエスト
	 */
	protected void showHintFeedback(LocationRequest request) {
		showHint();
		getHint().setFromText(((AbstractConnectionModel) getHost().getModel()).getSourceName());
		getHint().setToText(((AbstractConnectionModel) getHost().getModel()).getTargetName());
		getHint().setLocation(request.getLocation());
	}

	/**
	 * リレーションヒントのフィードバックを消す
	 * 
	 * @param request
	 *            リクエスト
	 */
	protected void eraseHintFeedback(LocationRequest request) {
		hideHint();
	}

	/**
	 * リレーションヒントを表示する
	 */
	protected void showHint() {
		if (hint == null) {
			hint = new RelationHint();
			addFeedback(hint);
		}
	}

	/**
	 * リレーションヒントを取得する
	 * 
	 * @return リレーションヒント
	 */
	protected RelationHint getHint() {
		return hint;
	}

	/**
	 * リレーションヒントを消す
	 */
	protected void hideHint() {
		if (hint != null) {
			removeFeedback(hint);
			hint = null;
		}
	}

	/**
	 * フィードバック用のfigureをレイヤーfigureに追加する
	 * 
	 * @param figure
	 *            フィードバックとして表示するfigure
	 */
	protected void addFeedback(IFigure figure) {
		getFeedbackLayer().add(figure);
	}

	/**
	 * フィードバック用のfigureをレイヤーfigureから削除する
	 * 
	 * @param figure
	 *            レイヤーから削除するfigure
	 */
	protected void removeFeedback(IFigure figure) {
		getFeedbackLayer().remove(figure);
	}

	/**
	 * フィードバック表示用のレイヤーを取得する
	 * 
	 * @return レイヤーfigure
	 */
	protected IFigure getFeedbackLayer() {
		return getLayer(LayerConstants.FEEDBACK_LAYER);
	}

	/**
	 * レイヤーを取得する
	 * 
	 * @param layer
	 *            レイヤーを指定する定数
	 * @return 指定したレイヤーfigure
	 */
	protected IFigure getLayer(Object layer) {
		return LayerManager.Helper.find(getHost()).getLayer(layer);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.editpolicies.ConnectionEditPolicy#getDeleteCommand(org.eclipse.gef.requests.GroupRequest)
	 */
	@Override
	protected Command getDeleteCommand(GroupRequest request) {
		return null;
	}

}
