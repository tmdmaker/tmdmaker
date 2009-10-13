package jp.sourceforge.tmdmaker;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.EventObject;
import java.util.List;

import jp.sourceforge.tmdmaker.action.MultivalueAndCreateAction;
import jp.sourceforge.tmdmaker.action.MultivalueOrCreateAction;
import jp.sourceforge.tmdmaker.action.SubsetEditAction;
import jp.sourceforge.tmdmaker.action.VirtualEntityCreateAction;
import jp.sourceforge.tmdmaker.action.VirtualSupersetCreateAction;
import jp.sourceforge.tmdmaker.dialog.EditAttribute;
import jp.sourceforge.tmdmaker.dialog.EntityNameAndTypeSettingPanel;
import jp.sourceforge.tmdmaker.dialog.RelationshipEditDialog;
import jp.sourceforge.tmdmaker.editpart.TMDEditPartFactory;
import jp.sourceforge.tmdmaker.model.AbstractConnectionModel;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Diagram;
import jp.sourceforge.tmdmaker.model.Entity;
import jp.sourceforge.tmdmaker.model.EntityType;
import jp.sourceforge.tmdmaker.model.Event2EventRelationship;
import jp.sourceforge.tmdmaker.model.command.ConnectionCreateCommand;
import jp.sourceforge.tmdmaker.model.command.EntityCreateCommand;
import jp.sourceforge.tmdmaker.tool.MovableSelectionTool;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CommandStackEvent;
import org.eclipse.gef.commands.CommandStackEventListener;
import org.eclipse.gef.editparts.FreeformGraphicalRootEditPart;
import org.eclipse.gef.palette.ConnectionCreationToolEntry;
import org.eclipse.gef.palette.CreationToolEntry;
import org.eclipse.gef.palette.MarqueeToolEntry;
import org.eclipse.gef.palette.PaletteDrawer;
import org.eclipse.gef.palette.PaletteGroup;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.SelectionToolEntry;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.gef.requests.SimpleFactory;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.parts.GraphicalEditorWithPalette;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.dialogs.SaveAsDialog;
import org.eclipse.ui.part.FileEditorInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TMDエディター
 * 
 * @author nakaG
 * 
 */
public class TMDEditor extends GraphicalEditorWithPalette {
	// TODO ソースの精査（常に！）
	// TODO アトリビュートリストを作成する
	// TODO アトリビュートにデリベーションの(D)を表示する？
	// TODO R:E関係間のN:Nリレーションシップの(R)に対してMOを作成する
	// TODO みなしスーパーセットを作成する
	// TODO 物理実装用のダイアログ（タブ？）を作成する
	// TODO HDR-DTLをエンティティ（R or E）のみに適用？
	// TODO 実装階層をコネクションに表示する（サブセットとVEだけ？）
	// TODO キーの定義書を作成する
	// TODO リレーションシップの検証表を表示する
	// TODO アルゴリズムの指示書を作成する？
	// TODO サムネイル作成
	
	/** logging */
	private static Logger logger = LoggerFactory.getLogger(TMDEditor.class);

	/**
	 * Default Constructor
	 */
	public TMDEditor() {
		super();
		logger.debug("{} is instanciate.", TMDEditor.class);
		setEditDomain(new DefaultEditDomain(this));
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.ui.parts.GraphicalEditor#initializeGraphicalViewer()
	 */
	@Override
	protected void initializeGraphicalViewer() {
		logger.debug(getClass() + "#initializeGraphicalViewer()");
		GraphicalViewer viewer = getGraphicalViewer();

		IFile file = ((IFileEditorInput) getEditorInput()).getFile();
		Diagram diagram = null;
		try {
			diagram = (Diagram) XStreamSerializer.deserialize(file
					.getContents(), this.getClass().getClassLoader());
		} catch (Exception e) {
			logger.warn("load error.", e);
			diagram = new Diagram();
		}
		viewer.setContents(diagram);

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.part.EditorPart#setInput(org.eclipse.ui.IEditorInput)
	 */
	@Override
	protected void setInput(IEditorInput input) {
		super.setInput(input);
		setPartName(input.getName());
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.ui.parts.GraphicalEditorWithPalette#getPaletteRoot()
	 */
	@Override
	protected PaletteRoot getPaletteRoot() {
		logger.debug("getPaletteRoot() called");
		PaletteRoot root = new PaletteRoot();

		PaletteGroup toolGroup = new PaletteGroup("ツール");

		ToolEntry tool = new SelectionToolEntry();
		// テンキーでモデルを移動できるようにSelectionToolを拡張
		tool.setToolClass(MovableSelectionTool.class);

		toolGroup.add(tool);
		root.setDefaultEntry(tool);

		tool = new MarqueeToolEntry();
		toolGroup.add(tool);

		PaletteDrawer drawer = new PaletteDrawer("作成");

		ImageDescriptor descriptor = TMDPlugin
				.getImageDescriptor("icons/newModel.gif");

		CreationToolEntry creationEntry = new CreationToolEntry("エンティティ",
				"エンティティ", new SimpleFactory(Entity.class), descriptor,
				descriptor);
		drawer.add(creationEntry);

		descriptor = TMDPlugin.getImageDescriptor("icons/newConnection.gif");

		ConnectionCreationToolEntry connxCCreationEntry = new ConnectionCreationToolEntry(
				"リレーションシップ", "リレーションシップ", null, descriptor, descriptor);
		// new SimpleFactory(AbstractRelationship.class), descriptor,
		// descriptor);
		drawer.add(connxCCreationEntry);

		root.add(toolGroup);
		root.add(drawer);

		return root;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.part.EditorPart#doSave(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public void doSave(IProgressMonitor monitor) {
		logger.debug("doSave() called");

		Diagram diagram = (Diagram) getGraphicalViewer().getContents()
				.getModel();
		IFile file = ((IFileEditorInput) getEditorInput()).getFile();
		try {
			file.deleteMarkers(IMarker.PROBLEM, false, 0);
		} catch (CoreException e) {
			logger.warn("IFile#deleteMarkers()." + e);
		}
		try {
			file.setContents(XStreamSerializer.serializeStream(diagram, this
					.getClass().getClassLoader()), true, true, monitor);
		} catch (UnsupportedEncodingException e) {
			logger.warn("IFile#setContents().", e);
		} catch (CoreException e) {
			logger.warn("IFile#setContents().", e);
		}
		getCommandStack().markSaveLocation();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.ui.parts.GraphicalEditor#isSaveAsAllowed()
	 */
	@Override
	public boolean isSaveAsAllowed() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.ui.parts.GraphicalEditor#doSaveAs()
	 */
	@Override
	public void doSaveAs() {
		Shell shell = getSite().getWorkbenchWindow().getShell();
		SaveAsDialog dialog = new SaveAsDialog(shell);
		dialog.setOriginalFile(((IFileEditorInput) getEditorInput()).getFile());
		dialog.open();

		IPath path = dialog.getResult();
		if (path == null) {
			return;
		}
		final IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(
				path);
		try {
			new ProgressMonitorDialog(shell).run(false, // don't fork
					false, // not cancelable
					new WorkspaceModifyOperation() { // run this operation

						@Override
						public void execute(IProgressMonitor monitor) {
							Diagram diagram = (Diagram) getGraphicalViewer()
									.getContents().getModel();
							try {
								file.create(XStreamSerializer.serializeStream(
										diagram, this.getClass()
												.getClassLoader()), true,
										monitor);
							} catch (UnsupportedEncodingException e) {
								logger.warn("IFile#setContents().", e);
							} catch (CoreException e) {
								logger.warn("IFile#setContents().", e);
							}
							getCommandStack().markSaveLocation();

						}
					});

			setInput(new FileEditorInput(file));
		} catch (InterruptedException e) {
			logger.warn("ProgressMonitorDialog#run().", e);
		} catch (InvocationTargetException e) {
			logger.warn("ProgressMonitorDialog#run().", e);
		}

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.ui.parts.GraphicalEditor#commandStackChanged(java.util.EventObject)
	 */
	@Override
	public void commandStackChanged(EventObject event) {
		this.firePropertyChange(IEditorPart.PROP_DIRTY);
		super.commandStackChanged(event);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.ui.parts.GraphicalEditor#createActions()
	 */
	@Override
	protected void createActions() {
		logger.debug("createAction() called");

		super.createActions();
		ActionRegistry registry = getActionRegistry();

		@SuppressWarnings("unchecked")
		List<String> selectionActions = getSelectionActions();

		SubsetEditAction action1 = new SubsetEditAction(this);
		registry.registerAction(action1);
		selectionActions.add(action1.getId());
		action1.setSelectionProvider(getGraphicalViewer());

		MultivalueOrCreateAction action2 = new MultivalueOrCreateAction(this);
		registry.registerAction(action2);
		selectionActions.add(action2.getId());
		action2.setSelectionProvider(getGraphicalViewer());

		MultivalueAndCreateAction action3 = new MultivalueAndCreateAction(this);
		registry.registerAction(action3);
		selectionActions.add(action3.getId());
		action3.setSelectionProvider(getGraphicalViewer());

		VirtualEntityCreateAction action4 = new VirtualEntityCreateAction(this);
		registry.registerAction(action4);
		selectionActions.add(action4.getId());
		action4.setSelectionProvider(getGraphicalViewer());

		VirtualSupersetCreateAction action5 = new VirtualSupersetCreateAction(
				this);
		registry.registerAction(action5);
		selectionActions.add(action5.getId());
		action5.setSelectionProvider(getGraphicalViewer());
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.ui.parts.GraphicalEditor#configureGraphicalViewer()
	 */
	@Override
	protected void configureGraphicalViewer() {
		logger.debug("configureGraphicalViewer() called");
		super.configureGraphicalViewer();
		GraphicalViewer viewer = getGraphicalViewer();
		viewer.setRootEditPart(new FreeformGraphicalRootEditPart());
		viewer.setEditPartFactory(new TMDEditPartFactory());

		ContextMenuProvider provider = new TMDContextMenuProvider(viewer,
				getActionRegistry());
		viewer.setContextMenu(provider);
		// ContextMenuにRun as等を表示しないようにするためIWorkbenchPartSiteに登録しない
		// getSite().registerContextMenu("tmd.contextmenu", provider, viewer);

		// when entity create, show dialog and set properties.

		getCommandStack().addCommandStackEventListener(
				new CommandStackEventListener() {

					/**
					 * {@inheritDoc}
					 */
					@Override
					public void stackChanged(CommandStackEvent event) {
						EntityCreateCommand command = null;
						if (event.getCommand() instanceof EntityCreateCommand) {
							command = (EntityCreateCommand) event.getCommand();
						} else {
							return;
						}

						logger.debug(getClass().toString()
								+ "#stackChanged():PreChangeEvent");
						if (event.getDetail() == CommandStack.PRE_EXECUTE
								|| event.getDetail() == CommandStack.PRE_REDO) {
							if (command.getEntityName() == null) {
								EntityCreateDialog dialog = new EntityCreateDialog(
										getGraphicalViewer().getControl()
												.getShell());
								if (dialog.open() == Dialog.OK) {
									logger
											.debug(getClass()
													+ "#stackChanged():dialog.open() == Dialog.OK)");
									command.setIdentifierName(dialog
											.getInputIdentifierName());
									command.setEntityName(dialog
											.getInputEntityName());
									EntityType entityType = dialog
											.getInputEntityType();
									command.setEntityType(entityType);
									if (entityType.equals(EntityType.EVENT)) {
										command.setTransactionDate(command
												.getEntityName()
												+ "日");
									} else {
										command.setTransactionDate("名称"); // ダミー。後で消す。
									}
								}
							}
						}
					}
				});
		getCommandStack().addCommandStackEventListener(
				new CommandStackEventListener() {
					/**
					 * {@inheritDoc}
					 */
					@Override
					public void stackChanged(CommandStackEvent event) {
						Command cmd = event.getCommand();
						if (cmd instanceof ConnectionCreateCommand) {
							ConnectionCreateCommand command = (ConnectionCreateCommand) cmd;
							AbstractConnectionModel cnt = command
									.getConnection();
							if (cnt instanceof Event2EventRelationship) {
								Event2EventRelationship relationship = (Event2EventRelationship) cnt;
								if (event.getDetail() == CommandStack.PRE_EXECUTE
										|| event.getDetail() == CommandStack.PRE_REDO) {
									AbstractEntityModel source = relationship
											.getSource();
									AbstractEntityModel target = relationship
											.getTarget();
									RelationshipEditDialog dialog = new RelationshipEditDialog(
											getGraphicalViewer().getControl()
													.getShell(), source
													.getName(), target
													.getName());
									if (dialog.open() == Dialog.OK) {
										relationship
												.setSourceCardinality(dialog
														.getSourceCardinality());
										relationship
												.setTargetCardinality(dialog
														.getTargetCardinality());
									}
								}

							}
						} else {
							return;
						}
					}
				});
	}

	/**
	 * エンティティ新規作成ダイアログ
	 * 
	 * @author nakaG
	 * 
	 */
	private static class EntityCreateDialog extends Dialog {
		/** 個体指示子名称 */
		private String inputIdentifierName;
		/** エンティティ名称 */
		private String inputEntityName;
		/** 類別 */
		private EntityType inputEntityType = EntityType.RESOURCE;
		/** エンティティ名称・種類設定用パネル */
		private EntityNameAndTypeSettingPanel panel;

		/**
		 * コンストラクタ
		 * 
		 * @param parentShell
		 *            親
		 */
		protected EntityCreateDialog(Shell parentShell) {
			super(parentShell);
		}

		/**
		 * 
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
		 */
		@Override
		protected Control createDialogArea(Composite parent) {
			getShell().setText("エンティティ新規作成");

			Composite composite = new Composite(parent, SWT.NULL);

			panel = new EntityNameAndTypeSettingPanel(composite, SWT.NULL);
			panel.setEditIdentifier(new EditAttribute());
			panel.setInitialFocus();
			
			return composite;
		}

		/**
		 * 
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
		 */
		@Override
		protected void okPressed() {
			this.inputIdentifierName = this.panel.getIdentifierName();
			this.inputEntityType = this.panel.getSelectedType();
			this.inputEntityName = this.panel.getEntityName();
			if (validate()) {
				super.okPressed();
			} else {

				return;
			}
		}

		/**
		 * ダイアログ検証
		 * 
		 * @return 必須事項が全て入力されている場合にtrueを返す
		 */
		private boolean validate() {
			return this.inputIdentifierName != null
					&& this.inputIdentifierName.length() > 0
					&& this.inputEntityName != null
					&& this.inputEntityName.length() > 0;
		}

		/**
		 * @return the inputIdentifierName
		 */
		public String getInputIdentifierName() {
			return inputIdentifierName;
		}

		/**
		 * @return the inputEntityType
		 */
		public EntityType getInputEntityType() {
			return inputEntityType;
		}

		/**
		 * @return the inputEntityName
		 */
		public String getInputEntityName() {
			return inputEntityName;
		}

	}
}
