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
package org.tmdmaker.ui.dialogs;

import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.tmdmaker.core.model.AbstractEntityModel;
import org.tmdmaker.ui.Messages;
import org.tmdmaker.ui.dialogs.components.ModelSelectPanel;

/**
 * ダイアグラムを元に何かを生成する際のダイアログ
 * 
 * @author nakaG
 * 
 */
public class GeneratorDialog extends Dialog {
	private Text savePathInputText;
	private ModelSelectPanel panel;
	private String defaultSavePath;
	private String generatorName;
	private List<AbstractEntityModel> notSelectedModels;
	private List<AbstractEntityModel> selectedModels;
	private String savePath;

	/**
	 * コンストラクタ
	 * 
	 * @param parentShell
	 *            親
	 */
	public GeneratorDialog(Shell parentShell, String defaultSavePath, String generatorName,
			List<AbstractEntityModel> selectModels, List<AbstractEntityModel> notSelectModels) {
		super(parentShell);
		this.defaultSavePath = defaultSavePath;
		this.generatorName = generatorName;
		this.selectedModels = selectModels;
		this.notSelectedModels = notSelectModels;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		getShell().setText(generatorName);

		Composite composite = new Composite(parent, SWT.NULL);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		composite.setLayout(gridLayout);

		GridData gridData = new GridData();
		Label pathLabel = new Label(composite, SWT.NONE);
		pathLabel.setText(Messages.Destination);
		gridData.horizontalSpan = 2;
		pathLabel.setLayoutData(gridData);

		gridData = new GridData(GridData.FILL_HORIZONTAL);
		savePathInputText = new Text(composite, SWT.BORDER);
		gridData.widthHint = 150;
		savePathInputText.setLayoutData(gridData);
		savePathInputText.setText(defaultSavePath);
		savePathInputText.setEditable(false);

		Button button = new Button(composite, SWT.NULL);
		button.setText(Messages.Redirection);
		button.addSelectionListener(new SelectionAdapter() {

			/**
			 * {@inheritDoc}
			 * 
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				super.widgetSelected(e);
				DirectoryDialog dialog = new DirectoryDialog(getShell(), SWT.SAVE);
				String rootDir = dialog.open();
				if (rootDir != null) {
					savePathInputText.setText(rootDir);
				}
			}

		});

		gridData = new GridData();
		Label filler = new Label(composite, SWT.NONE);
		gridData.horizontalSpan = 2;
		filler.setLayoutData(gridData);

		gridData = new GridData();
		Label modelLabel = new Label(composite, SWT.NONE);
		modelLabel.setText(Messages.OutputModel);
		gridData.horizontalSpan = 2;
		modelLabel.setLayoutData(gridData);

		gridData = new GridData(GridData.FILL_HORIZONTAL);
		panel = new ModelSelectPanel(composite, SWT.NULL);
		gridData.horizontalSpan = 2;
		panel.setLayoutData(gridData);
		panel.initializeValue(selectedModels, notSelectedModels);

		composite.pack();
		return composite;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 */
	@Override
	protected void okPressed() {
		savePath = savePathInputText.getText();
		selectedModels = panel.getSelectModels();
		super.okPressed();
	}

	/**
	 * @return the savePath
	 */
	public String getSavePath() {
		return savePath;
	}

	/**
	 * @return the selectedModels
	 */
	public List<AbstractEntityModel> getSelectedModels() {
		return selectedModels;
	}
}
