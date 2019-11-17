/*
 * Copyright 2009-2019 TMD-Maker Project <https://www.tmdmaker.org/>
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
package org.tmdmaker.ui.editor;

import java.lang.reflect.InvocationTargetException;
import java.util.EventObject;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.Viewport;
import org.eclipse.draw2d.parts.ScrollableThumbnail;
import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.KeyHandler;
import org.eclipse.gef.KeyStroke;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.MouseWheelHandler;
import org.eclipse.gef.MouseWheelZoomHandler;
import org.eclipse.gef.SnapToGeometry;
import org.eclipse.gef.SnapToGrid;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.palette.ConnectionCreationToolEntry;
import org.eclipse.gef.palette.CreationToolEntry;
import org.eclipse.gef.palette.MarqueeToolEntry;
import org.eclipse.gef.palette.PaletteDrawer;
import org.eclipse.gef.palette.PaletteGroup;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.SelectionToolEntry;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.gef.requests.SimpleFactory;
import org.eclipse.gef.rulers.RulerProvider;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.AlignmentAction;
import org.eclipse.gef.ui.actions.DeleteAction;
import org.eclipse.gef.ui.actions.DirectEditAction;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.gef.ui.actions.ToggleGridAction;
import org.eclipse.gef.ui.actions.ToggleRulerVisibilityAction;
import org.eclipse.gef.ui.actions.ToggleSnapToGeometryAction;
import org.eclipse.gef.ui.actions.ZoomInAction;
import org.eclipse.gef.ui.actions.ZoomOutAction;
import org.eclipse.gef.ui.palette.FlyoutPaletteComposite;
import org.eclipse.gef.ui.palette.FlyoutPaletteComposite.FlyoutPreferences;
import org.eclipse.gef.ui.parts.ContentOutlinePage;
import org.eclipse.gef.ui.parts.GraphicalEditorWithFlyoutPalette;
import org.eclipse.gef.ui.parts.ScrollingGraphicalViewer;
import org.eclipse.gef.ui.parts.TreeViewer;
import org.eclipse.gef.ui.rulers.RulerComposite;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.commands.ActionHandler;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.dialogs.SaveAsDialog;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.osgi.framework.Bundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tmdmaker.core.model.Diagram;
import org.tmdmaker.core.model.Entity;
import org.tmdmaker.core.model.Version;
import org.tmdmaker.core.model.other.Memo;
import org.tmdmaker.core.model.other.TurboFile;
import org.tmdmaker.extension.GeneratorFactory;
import org.tmdmaker.extension.PluginExtensionPointFactory;
import org.tmdmaker.extension.SerializerFactory;
import org.tmdmaker.model.generate.Generator;
import org.tmdmaker.model.importer.FileImporter;
import org.tmdmaker.model.persistence.SerializationException;
import org.tmdmaker.model.persistence.Serializer;
import org.tmdmaker.ui.views.properties.gef3.TMDPropertySheetPage;
import org.tmdmaker.ui.Activator;
import org.tmdmaker.ui.Messages;
import org.tmdmaker.ui.actions.gef3.AutoSizeSettingAction;
import org.tmdmaker.ui.actions.gef3.CommonAttributeSettingAction;
import org.tmdmaker.ui.actions.gef3.CopyModelAction;
import org.tmdmaker.ui.actions.gef3.DatabaseSelectAction;
import org.tmdmaker.ui.actions.gef3.DiagramImageGenerateAction;
import org.tmdmaker.ui.actions.gef3.FileImportAction;
import org.tmdmaker.ui.actions.gef3.GenerateAction;
import org.tmdmaker.ui.actions.gef3.ImplementInfoEditAction;
import org.tmdmaker.ui.actions.gef3.MultivalueAndCreateAction;
import org.tmdmaker.ui.actions.gef3.MultivalueAndSupersetHideAction;
import org.tmdmaker.ui.actions.gef3.MultivalueAndSupersetShowAction;
import org.tmdmaker.ui.actions.gef3.MultivalueOrCreateAction;
import org.tmdmaker.ui.actions.gef3.OpenDialogAction;
import org.tmdmaker.ui.actions.gef3.PasteModelAction;
import org.tmdmaker.ui.actions.gef3.SubsetCreateAction;
import org.tmdmaker.ui.actions.gef3.SubsetTypeTurnAction;
import org.tmdmaker.ui.actions.gef3.VirtualEntityCreateAction;
import org.tmdmaker.ui.actions.gef3.VirtualSupersetCreateAction;
import org.tmdmaker.ui.editor.gef3.editparts.DiagramEditPart;
import org.tmdmaker.ui.editor.gef3.editparts.TMDEditPartFactory;
import org.tmdmaker.ui.editor.gef3.editparts.node.AbstractModelEditPart;
import org.tmdmaker.ui.editor.gef3.rulers.TMDRulerProvider;
import org.tmdmaker.ui.editor.gef3.rulers.model.RulerModel;
import org.tmdmaker.ui.editor.gef3.tools.EntityCreationTool;
import org.tmdmaker.ui.editor.gef3.tools.MovableSelectionTool;
import org.tmdmaker.ui.editor.gef3.tools.TMDConnectionCreationTool;
import org.tmdmaker.ui.editor.gef3.treeeditparts.TMDEditorOutlineTreePartFactory;

/**
 * TMDエディター
 * 
 * @author nakaG
 * 
 */
public class TMDEditor extends GraphicalEditorWithFlyoutPalette implements IResourceChangeListener {

	private static final String SAVE_ERROR = Messages.SaveError;
	private static final String READ_ERROR = Messages.ReadError;

	/**
	 * アウトラインページ
	 * 
	 * @author nakaG
	 * 
	 */
	private class TMDContentOutlinePage extends ContentOutlinePage {
		private final TMDEditor tmdEditor;
		private SashForm sash;
		private ScrollableThumbnail thumbnail;
		private DisposeListener disposeListener;

		public TMDContentOutlinePage(TMDEditor tmdEditor) {
			super(new TreeViewer());
			this.tmdEditor = tmdEditor;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.gef.ui.parts.ContentOutlinePage#createControl(org.eclipse.swt.widgets.Composite)
		 */
		@Override
		public void createControl(Composite parent) {
			sash = new SashForm(parent, SWT.VERTICAL);

			Canvas canvas = new Canvas(sash, SWT.BORDER);
			LightweightSystem lws = new LightweightSystem(canvas);

			ScalableFreeformRootEditPart root = tmdEditor.getScalableRootEditPart();

			thumbnail = new ScrollableThumbnail((Viewport) root.getFigure());
			thumbnail.setSource(root.getLayer(LayerConstants.PRINTABLE_LAYERS));

			lws.setContents(thumbnail);

			// tree
			logger.debug(Messages.TreeSettingsStartMessage);
			EditPartViewer viewer = this.getViewer();
			viewer.createControl(sash);
			viewer.setEditDomain(tmdEditor.getEditDomain());
			viewer.setEditPartFactory(new TMDEditorOutlineTreePartFactory());
			viewer.setContents(tmdEditor.getRootModel());

			tmdEditor.addSelectionSynchronizerViewer(viewer);

			sash.setWeights(new int[] { 3, 7 });

			disposeListener = new DisposeListener() {

				@Override
				public void widgetDisposed(DisposeEvent e) {
					if (thumbnail != null) {
						thumbnail.deactivate();
						thumbnail = null;
					}
				}
			};
			getGraphicalViewer().getControl().addDisposeListener(disposeListener);

		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.gef.ui.parts.ContentOutlinePage#getControl()
		 */
		@Override
		public Control getControl() {
			return sash;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.ui.part.Page#dispose()
		 */
		@Override
		public void dispose() {
			if (getGraphicalViewer().getControl() != null
					&& !getGraphicalViewer().getControl().isDisposed()) {
				getGraphicalViewer().getControl().removeDisposeListener(disposeListener);
			}
			super.dispose();
		}

	}

	/** logging */
	private static Logger logger = LoggerFactory.getLogger(TMDEditor.class);
	private RulerComposite rulerComp;

	/**
	 * Default Constructor
	 */
	public TMDEditor() {
		super();
		logger.debug("{} is instanciate.", TMDEditor.class); //$NON-NLS-1$
		setEditDomain(new DefaultEditDomain(this));
		ResourcesPlugin.getWorkspace().addResourceChangeListener(this);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.ui.parts.GraphicalEditor#createGraphicalViewer(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected void createGraphicalViewer(Composite parent) {
		rulerComp = new RulerComposite(parent, SWT.NONE);
		super.createGraphicalViewer(rulerComp);
		rulerComp.setGraphicalViewer((ScrollingGraphicalViewer) getGraphicalViewer());
	}

	public GraphicalViewer getViewer() {
		return getGraphicalViewer();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.ui.parts.GraphicalEditor#initializeGraphicalViewer()
	 */
	@Override
	protected void initializeGraphicalViewer() {
		super.initializeGraphicalViewer();
		logger.debug("{}#initializeGraphicalViewer()", getClass()); //$NON-NLS-1$
		GraphicalViewer viewer = getGraphicalViewer();

		IFile file = ((IFileEditorInput) getEditorInput()).getFile();
		Diagram diagram = null;
		try {
			Serializer serializer = SerializerFactory.getInstance();
			diagram = serializer.deserialize(file.getContents());
		} catch (Exception e) {
			Activator.showErrorDialog(READ_ERROR, e);
			diagram = new Diagram();
		}
		Version version = getPluginVersion();
		diagram.setVersion(version.getValue());
		viewer.setContents(diagram);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.ui.parts.GraphicalEditor#dispose()
	 */
	@Override
	public void dispose() {
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(this);
		super.dispose();
	}

	private Version getPluginVersion() {
		Bundle bundle = Activator.getDefault().getBundle();
		return new Version(bundle.getHeaders().get("Bundle-Version")); //$NON-NLS-1$
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
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.ui.parts.GraphicalEditorWithFlyoutPalette#getGraphicalControl()
	 */
	@Override
	protected Control getGraphicalControl() {
		return rulerComp;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.ui.parts.GraphicalEditorWithPalette#getPaletteRoot()
	 */
	@Override
	protected PaletteRoot getPaletteRoot() {
		logger.debug("getPaletteRoot() called"); //$NON-NLS-1$
		PaletteRoot root = new PaletteRoot();

		PaletteGroup toolGroup = new PaletteGroup(Messages.Tool);

		ToolEntry tool = new SelectionToolEntry();
		// カーソルキーでモデルを移動できるようにSelectionToolを拡張
		tool.setToolClass(MovableSelectionTool.class);

		toolGroup.add(tool);
		root.setDefaultEntry(tool);

		tool = new MarqueeToolEntry();
		toolGroup.add(tool);

		PaletteDrawer drawer = new PaletteDrawer(Messages.Create);

		ImageDescriptor descriptor = Activator.getImageDescriptor("icons/new_entity.gif"); //$NON-NLS-1$

		CreationToolEntry creationEntry = new CreationToolEntry(Messages.Entity, Messages.Entity,
				new SimpleFactory(Entity.class), descriptor, descriptor);
		creationEntry.setToolClass(EntityCreationTool.class);

		drawer.add(creationEntry);

		descriptor = Activator.getImageDescriptor("icons/new_relationship.gif"); //$NON-NLS-1$

		ConnectionCreationToolEntry connxCCreationEntry = new ConnectionCreationToolEntry(
				Messages.Relationship, Messages.Relationship, null, descriptor, descriptor);
		connxCCreationEntry.setToolClass(TMDConnectionCreationTool.class);

		drawer.add(connxCCreationEntry);

		PaletteDrawer otherDrawer = new PaletteDrawer(Messages.Other);
		// ターボファイル作成
		ImageDescriptor turboFileDescriptor = Activator.getImageDescriptor("icons/new_turbo.gif"); //$NON-NLS-1$
		CreationToolEntry turboFileCreationEntry = new CreationToolEntry(Messages.TurboFile,
				Messages.TurboFile, new SimpleFactory(TurboFile.class), turboFileDescriptor,
				turboFileDescriptor);
		otherDrawer.add(turboFileCreationEntry);

		// メモ作成
		ImageDescriptor memoDescriptor = Activator.getImageDescriptor("icons/new_memo.gif"); //$NON-NLS-1$
		CreationToolEntry memoCreationEntry = new CreationToolEntry(Messages.Memo, Messages.Memo,
				new SimpleFactory(Memo.class), memoDescriptor, memoDescriptor);
		otherDrawer.add(memoCreationEntry);

		root.add(toolGroup);
		root.add(drawer);
		root.add(otherDrawer);

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
		logger.debug("doSave() called"); //$NON-NLS-1$

		Diagram diagram = (Diagram) getGraphicalViewer().getContents().getModel();
		IFile file = ((IFileEditorInput) getEditorInput()).getFile();
		try {
			file.deleteMarkers(IMarker.PROBLEM, false, 0);
		} catch (CoreException e) {
			Activator.showErrorDialog(SAVE_ERROR, e);
			logger.warn("IFile#deleteMarkers().{}", e); //$NON-NLS-1$
		}
		try {
			Serializer serializer = SerializerFactory.getInstance();
			file.setContents(serializer.serialize(diagram), true, true, monitor);
		} catch (SerializationException e) {
			Activator.showErrorDialog(SAVE_ERROR, e);
			logger.warn("IFile#setContents().", e); //$NON-NLS-1$
		} catch (CoreException e) {
			Activator.showErrorDialog(SAVE_ERROR, e);
			logger.warn("IFile#setContents().", e); //$NON-NLS-1$
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
		logger.debug("doSaveAs() called"); //$NON-NLS-1$
		Shell shell = getSite().getWorkbenchWindow().getShell();
		SaveAsDialog dialog = new SaveAsDialog(shell);
		dialog.setOriginalFile(((IFileEditorInput) getEditorInput()).getFile());
		dialog.open();

		IPath path = dialog.getResult();
		if (path == null) {
			return;
		}
		final IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(path);
		try {
			new ProgressMonitorDialog(shell).run(false, // don't fork
					false, // not cancelable
					new WorkspaceModifyOperation() { // run this operation

						@Override
						public void execute(IProgressMonitor monitor) {
							Diagram diagram = (Diagram) getGraphicalViewer().getContents()
									.getModel();
							try {
								Serializer serializer = SerializerFactory.getInstance();
								file.create(serializer.serialize(diagram), true, monitor);
							} catch (SerializationException e) {
								Activator.showErrorDialog(SAVE_ERROR, e);
								logger.warn("IFile#setContents().", e); //$NON-NLS-1$
							} catch (CoreException e) {
								Activator.showErrorDialog(SAVE_ERROR, e);
								logger.warn("IFile#setContents().", e); //$NON-NLS-1$
							}
							getCommandStack().markSaveLocation();
							setInput(new FileEditorInput(file));

						}
					});

		} catch (InterruptedException e) {
			logger.warn("ProgressMonitorDialog#run().", e); //$NON-NLS-1$
		} catch (InvocationTargetException e) {
			logger.warn("ProgressMonitorDialog#run().", e); //$NON-NLS-1$
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
		logger.debug("createAction() called"); //$NON-NLS-1$

		super.createActions();
		ActionRegistry registry = getActionRegistry();

		@SuppressWarnings("unchecked")
		List<String> selectionActions = getSelectionActions();
		
		// オープンアクション
		SelectionAction selectionAction = new OpenDialogAction((IWorkbenchPart)this);
		setupSelectionAction(registry, selectionActions, selectionAction);
		
		// 削除アクション
		selectionAction = new DeleteAction((IWorkbenchPart)this);
		setupSelectionAction(registry, selectionActions, selectionAction);
		

		selectionAction = new SubsetCreateAction(this);
		setupSelectionAction(registry, selectionActions, selectionAction);

		selectionAction = new SubsetTypeTurnAction(this);
		setupSelectionAction(registry, selectionActions, selectionAction);

		selectionAction = new MultivalueOrCreateAction(this);
		setupSelectionAction(registry, selectionActions, selectionAction);

		selectionAction = new MultivalueAndCreateAction(this);
		setupSelectionAction(registry, selectionActions, selectionAction);

		selectionAction = new VirtualEntityCreateAction(this);
		setupSelectionAction(registry, selectionActions, selectionAction);

		selectionAction = new VirtualSupersetCreateAction(this);
		setupSelectionAction(registry, selectionActions, selectionAction);

		selectionAction = new ImplementInfoEditAction(this);
		setupSelectionAction(registry, selectionActions, selectionAction);

		selectionAction = new CopyModelAction(this);
		registry.registerAction(selectionAction);
		selectionActions.add(selectionAction.getId());

		selectionAction = new PasteModelAction(this);
		registry.registerAction(selectionAction);
		selectionActions.add(selectionAction.getId());

		IAction action = null;
		// 水平方向の整列アクション
		action = new AlignmentAction((IWorkbenchPart) this, PositionConstants.LEFT);
		registry.registerAction(action);
		selectionActions.add(action.getId());

		action = new AlignmentAction((IWorkbenchPart) this, PositionConstants.CENTER);
		registry.registerAction(action);
		selectionActions.add(action.getId());

		action = new AlignmentAction((IWorkbenchPart) this, PositionConstants.RIGHT);
		registry.registerAction(action);
		selectionActions.add(action.getId());

		// 垂直方向の整列アクション
		action = new AlignmentAction((IWorkbenchPart) this, PositionConstants.TOP);
		registry.registerAction(action);
		selectionActions.add(action.getId());

		action = new AlignmentAction((IWorkbenchPart) this, PositionConstants.MIDDLE);
		registry.registerAction(action);
		selectionActions.add(action.getId());

		action = new AlignmentAction((IWorkbenchPart) this, PositionConstants.BOTTOM);
		registry.registerAction(action);
		selectionActions.add(action.getId());

		action = new AutoSizeSettingAction(this);
		registry.registerAction(action);
		selectionActions.add(action.getId());

		action = new MultivalueAndSupersetHideAction(this);
		registry.registerAction(action);
		selectionActions.add(action.getId());

		action = new MultivalueAndSupersetShowAction(this);
		registry.registerAction(action);
		selectionActions.add(action.getId());

		action = new DirectEditAction(this);
		registry.registerAction(action);
		selectionActions.add(action.getId());

	}

	private void setupSelectionAction(ActionRegistry registry, List<String> selectionActions,
			SelectionAction selectionAction) {
		registry.registerAction(selectionAction);
		selectionActions.add(selectionAction.getId());
		selectionAction.setSelectionProvider(getGraphicalViewer());
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.ui.parts.GraphicalEditor#configureGraphicalViewer()
	 */
	@Override
	protected void configureGraphicalViewer() {
		logger.debug("configureGraphicalViewer() called"); //$NON-NLS-1$
		super.configureGraphicalViewer();
		GraphicalViewer viewer = getGraphicalViewer();
		ScalableFreeformRootEditPart rootEditPart = new ScalableFreeformRootEditPart();
		viewer.setRootEditPart(rootEditPart);
		viewer.setEditPartFactory(new TMDEditPartFactory());

		ContextMenuProvider provider = new TMDContextMenuProvider(viewer, getActionRegistry());
		viewer.setContextMenu(provider);

		// ContextMenuにRun as等を表示しないようにするためIWorkbenchPartSiteに登録しない
		// getSite().registerContextMenu("tmd.contextmenu", provider, viewer);

		// viewerを取得するためcreateActionsメソッドではなくここでアクションを登録
		ActionRegistry registry = getActionRegistry();
		registerActions(viewer, rootEditPart, registry);

		registerKeyHandleres(viewer, registry);

		loadProperties();
	}

	private void registerActions(GraphicalViewer viewer, ScalableFreeformRootEditPart rootEditPart,
			ActionRegistry registry) {
		DiagramImageGenerateAction action66 = new DiagramImageGenerateAction(viewer, this);
		registry.registerAction(action66);

		@SuppressWarnings("unchecked")
		List<String> selectionActions = getSelectionActions();
		for (Generator generator : GeneratorFactory.getGenerators()) {
			SelectionAction act = new GenerateAction(this, viewer, generator);
			registry.registerAction(act);
			selectionActions.add(act.getId());
		}

		IAction action = new DatabaseSelectAction(viewer);
		registry.registerAction(action);

		action = new CommonAttributeSettingAction(viewer);
		registry.registerAction(action);

		PluginExtensionPointFactory<FileImporter> fileImportFactory = new PluginExtensionPointFactory<FileImporter>(
				Activator.IMPORTER_PLUGIN_ID);
		for (FileImporter importer : fileImportFactory.getInstances()) {
			FileImportAction act = new FileImportAction(viewer, importer);
			registry.registerAction(act);
		}

		action = new ToggleGridAction(viewer);
		registry.registerAction(action);

		action = new ToggleRulerVisibilityAction(viewer);
		getActionRegistry().registerAction(action);

		action = new ToggleSnapToGeometryAction(viewer);
		getActionRegistry().registerAction(action);

		// zoom（キーバインディングとマウスホイールも）
		// FIXME:ZoomINのキーバインディングに不具合あり
		IHandlerService service = getSite().getService(IHandlerService.class);
		action = new ZoomInAction(rootEditPart.getZoomManager());
		getActionRegistry().registerAction(action);
		service.activateHandler(action.getActionDefinitionId(), new ActionHandler(action));

		action = new ZoomOutAction(rootEditPart.getZoomManager());
		getActionRegistry().registerAction(action);
		service.activateHandler(action.getActionDefinitionId(), new ActionHandler(action));

		viewer.setProperty(MouseWheelHandler.KeyGenerator.getKey(SWT.MOD1),
				MouseWheelZoomHandler.SINGLETON);
	}

	private void registerKeyHandleres(GraphicalViewer viewer, ActionRegistry registry) {
		KeyHandler handler = new KeyHandler();
		viewer.setKeyHandler(handler);
		handler.put(KeyStroke.getPressed(SWT.F2, 0),
				registry.getAction(GEFActionConstants.DIRECT_EDIT));
	}

	private void loadProperties() {

		// ルーラーは垂直と水平位置に表示させる。
		TMDRulerProvider provider = new TMDRulerProvider(new RulerModel());
		getGraphicalViewer().setProperty(RulerProvider.PROPERTY_HORIZONTAL_RULER, provider);
		provider = new TMDRulerProvider(new RulerModel());
		getGraphicalViewer().setProperty(RulerProvider.PROPERTY_VERTICAL_RULER, provider);

		// ルーラーは初期表示しない。
		getGraphicalViewer().setProperty(RulerProvider.PROPERTY_RULER_VISIBILITY, Boolean.FALSE);

		// スナップ機能はデフォルトでは無効とする。
		getGraphicalViewer().setProperty(SnapToGeometry.PROPERTY_SNAP_ENABLED, Boolean.FALSE);

		// グリッド機能はデフォルトでは無効とする。
		getGraphicalViewer().setProperty(SnapToGrid.PROPERTY_GRID_ENABLED, Boolean.FALSE);

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.ui.parts.GraphicalEditor#getAdapter(java.lang.Class)
	 */
	@Override
	public Object getAdapter(@SuppressWarnings("rawtypes") Class type) {
		if (type == IContentOutlinePage.class) {
			return new TMDContentOutlinePage(this);
		}
		if (type == ZoomManager.class) {
			return getGraphicalViewer().getProperty(ZoomManager.class.toString());
		}
		if (type == IPropertySheetPage.class) {
			return new TMDPropertySheetPage(this.getCommandStack());
		}
		return super.getAdapter(type);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.resources.IResourceChangeListener#resourceChanged(org.eclipse.core.resources.IResourceChangeEvent)
	 */
	@Override
	public void resourceChanged(IResourceChangeEvent event) {
		if (event.getType() == IResourceChangeEvent.POST_CHANGE) {
			final IEditorInput input = getEditorInput();
			if (input instanceof IFileEditorInput) {
				Display.getDefault().asyncExec(new Runnable() {
					@Override
					public void run() {
						IFile file = ((IFileEditorInput) input).getFile();
						if (!file.exists()) {
							IWorkbenchPage page = PlatformUI.getWorkbench()
									.getActiveWorkbenchWindow().getActivePage();
							page.closeEditor(TMDEditor.this, true);
						} else if (!getPartName().equals(file.getName())) {
							setPartName(file.getName());
						}
					}
				});
			}
		}
	}

	public void updateVisuals() {
		List<?> editParts = getGraphicalViewer().getRootEditPart().getChildren();

		for (Object o : editParts) {
			logger.debug(o.getClass().getName());
			if (o instanceof AbstractModelEditPart) {
				((AbstractModelEditPart<?>) o).updateAppearance();
			} else if (o instanceof DiagramEditPart) {
				for (Object ob : ((DiagramEditPart) o).getChildren()) {
					if (ob instanceof AbstractModelEditPart) {
						((AbstractModelEditPart<?>) ob).updateAppearance();
					}
				}
			}
		}
	}

	public Diagram getRootModel() {
		GraphicalViewer viewer = getGraphicalViewer();
		return (Diagram) viewer.getContents().getModel();
	}

	public ScalableFreeformRootEditPart getScalableRootEditPart() {
		return (ScalableFreeformRootEditPart) getGraphicalViewer().getRootEditPart();
	}

	public void addSelectionSynchronizerViewer(EditPartViewer viewer) {
		getSelectionSynchronizer().addViewer(viewer);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.ui.parts.GraphicalEditorWithFlyoutPalette#getPalettePreferences()
	 */
	@Override
	protected FlyoutPreferences getPalettePreferences() {
		FlyoutPreferences pref = super.getPalettePreferences();

		if (pref.getPaletteWidth() <= 0) {
			pref.setDockLocation(PositionConstants.EAST);
			pref.setPaletteState(FlyoutPaletteComposite.STATE_PINNED_OPEN);
			pref.setPaletteWidth(135);
		}
		return pref;
	}
}
