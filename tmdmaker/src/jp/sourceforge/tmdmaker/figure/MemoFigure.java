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
package jp.sourceforge.tmdmaker.figure;

import org.eclipse.draw2d.BorderLayout;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.text.FlowPage;
import org.eclipse.draw2d.text.ParagraphTextLayout;
import org.eclipse.draw2d.text.TextFlow;
import org.eclipse.swt.graphics.Color;

/**
 * メモFigure
 * 
 * @author nakag
 *
 */
public class MemoFigure extends Figure {
	private TextFlow memo;

	/**
	 * コンストラクタ
	 */
	public MemoFigure() {
		super();
		FlowPage page = new FlowPage();

		setOpaque(true);
		setBorder(new LineBorder());
		setLayoutManager(new BorderLayout());
		memo = new TextFlow();
		ParagraphTextLayout l = new ParagraphTextLayout(memo, ParagraphTextLayout.WORD_WRAP_SOFT);
		memo.setLayoutManager(l);
		page.add(memo);
		add(page, BorderLayout.CENTER);
	}

	public void setMemo(String memo) {
		this.memo.setText(memo);
	}

	/**
	 * DirectEdit時に利用
	 * 
	 * @return TextFlow
	 */
	public TextFlow getMemoTextFlow() {
		return memo;
	}

	/**
	 * モデルの色を設定する
	 *
	 * @param foregroundColor
	 *            前景色
	 * @param backgroundColor
	 *            背景色
	 */
	public void setColor(Color foregroundColor, Color backgroundColor) {
		setForegroundColor(foregroundColor);
		setBackgroundColor(backgroundColor);
	}
}
