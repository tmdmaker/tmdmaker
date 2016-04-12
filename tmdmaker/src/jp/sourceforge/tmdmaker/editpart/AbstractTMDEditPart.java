/*
 * Copyright 2009-2015 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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
package jp.sourceforge.tmdmaker.editpart;

import java.beans.PropertyChangeListener;

import jp.sourceforge.tmdmaker.model.ModelElement;

import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TMDモデルの基底クラス
 * 
 * @author nakaG
 * 
 */
public abstract class AbstractTMDEditPart<T extends ModelElement> extends AbstractGraphicalEditPart
		implements PropertyChangeListener {
	/** logging */
	protected static Logger logger;

	/**
	 * コンストラクタ
	 */
	public AbstractTMDEditPart() {
		logger = LoggerFactory.getLogger(getClass());
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#activate()
	 */
	@Override
	public void activate() {
		super.activate();
		getModel().addPropertyChangeListener(this);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#deactivate()
	 */
	@Override
	public void deactivate() {
		super.deactivate();
		getModel().removePropertyChangeListener(this);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#performRequest(org.eclipse.gef.Request)
	 */
	@Override
	public void performRequest(Request req) {
		Object requestType = req.getType();
		logger.debug(getClass() + " " + requestType);
		if (requestType.equals(RequestConstants.REQ_OPEN)) {
			onDoubleClicked();
		} else if (requestType.equals(RequestConstants.REQ_DIRECT_EDIT)) {
			onDirectEdit();
		} else {
			super.performRequest(req);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public T getModel() {
		return (T) super.getModel();
	}

	/**
	 * ダブルクリック時の処理をサブクラスで実装する
	 */
	protected abstract void onDoubleClicked();

	/**
	 * ダイレクトエディット時の処理（必要なサブクラスのみ実装）
	 */
	protected void onDirectEdit() {

	}
}
