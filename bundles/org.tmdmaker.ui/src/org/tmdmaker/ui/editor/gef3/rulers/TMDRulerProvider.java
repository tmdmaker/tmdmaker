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
package org.tmdmaker.ui.editor.gef3.rulers;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.rulers.RulerChangeListener;
import org.eclipse.gef.rulers.RulerProvider;
import org.tmdmaker.ui.editor.gef3.rulers.commands.CreateGuideCommand;
import org.tmdmaker.ui.editor.gef3.rulers.commands.DeleteGuideCommand;
import org.tmdmaker.ui.editor.gef3.rulers.commands.MoveGuideCommand;
import org.tmdmaker.ui.editor.gef3.rulers.model.GuideModel;
import org.tmdmaker.ui.editor.gef3.rulers.model.RulerModel;

/**
 * TMDエディタ用のRulerProvider
 * 
 * @author nakaG
 * 
 */
public class TMDRulerProvider extends RulerProvider {
	/** ルーラーのモデル */
	private RulerModel ruler;
	/** ルーラーモデルの変更通知を受けるリスナー */
	private PropertyChangeListener rulerListener = new PropertyChangeListener() {

		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			if (evt.getPropertyName().equals(RulerModel.PROPERTY_CHILDREN)) {
				GuideModel guide = getGuide(evt);
				handleChildrenChanged(guide);
			}
		}

		private GuideModel getGuide(PropertyChangeEvent evt) {
			GuideModel guide = (GuideModel) evt.getNewValue();
			if (getGuides().contains(guide)) {
				guide.addPropertyChangeListener(guideListener);
			} else {
				guide = (GuideModel) evt.getOldValue();
				guide.removePropertyChangeListener(guideListener);
			}
			return guide;
		}

		private void handleChildrenChanged(GuideModel guide) {
			for (int i = 0; i < listeners.size(); i++) {
				((RulerChangeListener) listeners.get(i))
						.notifyGuideReparented(guide);
			}
		}
	};
	/** ガイドモデルの変更通知を受けるリスナー */
	private PropertyChangeListener guideListener = new PropertyChangeListener() {

		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			if (evt.getPropertyName().equals(GuideModel.PROPERTY_POSITION)) {
				handleGuideMoved((GuideModel) evt.getSource());
			}
		}

		private void handleGuideMoved(GuideModel guide) {
			for (int i = 0; i < listeners.size(); i++) {
				((RulerChangeListener) listeners.get(i))
						.notifyGuideMoved(guide);
			}
		}
	};

	/**
	 * コンストラクタ
	 * 
	 * @param ruler
	 *            ルーラのモデル
	 */
	public TMDRulerProvider(RulerModel ruler) {
		this.ruler = ruler;
		this.ruler.addPropertyChangeListener(rulerListener);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.rulers.RulerProvider#setUnit(int)
	 */
	@Override
	public void setUnit(int newUnit) {
		ruler.setUnit(newUnit);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.rulers.RulerProvider#getUnit()
	 */
	@Override
	public int getUnit() {
		return ruler.getUnit();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.rulers.RulerProvider#getRuler()
	 */
	@Override
	public Object getRuler() {
		return ruler;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.rulers.RulerProvider#getGuidePositions()
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public int[] getGuidePositions() {
		List guides = getGuides();
		int[] results = new int[guides.size()];

		for (int i = 0; i < guides.size(); i++) {
			results[i] = ((GuideModel) guides.get(i)).getPosition();
		}
		return results;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.rulers.RulerProvider#getGuidePosition(java.lang.Object)
	 */
	@Override
	public int getGuidePosition(Object guide) {
		return ((GuideModel) guide).getPosition();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.rulers.RulerProvider#getGuides()
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List getGuides() {
		return ruler.getGuides();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.rulers.RulerProvider#getCreateGuideCommand(int)
	 */
	@Override
	public Command getCreateGuideCommand(int position) {
		return new CreateGuideCommand(ruler, position);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.rulers.RulerProvider#getDeleteGuideCommand(java.lang.Object)
	 */
	@Override
	public Command getDeleteGuideCommand(Object guide) {
		return new DeleteGuideCommand(ruler, (GuideModel) guide);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.rulers.RulerProvider#getMoveGuideCommand(java.lang.Object,
	 *      int)
	 */
	@Override
	public Command getMoveGuideCommand(Object guide, int positionDelta) {
		return new MoveGuideCommand((GuideModel) guide, positionDelta);
	}

}
