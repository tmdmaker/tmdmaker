/*
 * Copyright 2009-2013 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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
package jp.sourceforge.tmdmaker;

import java.lang.reflect.InvocationTargetException;
import java.util.EventObject;
import java.util.List;

import jp.sourceforge.tmdmaker.action.AutoSizeSettingAction;
import jp.sourceforge.tmdmaker.action.CommonAttributeSettingAction;
import jp.sourceforge.tmdmaker.action.DatabaseSelectAction;
import jp.sourceforge.tmdmaker.action.DiagramImageGenerateAction;
import jp.sourceforge.tmdmaker.action.FileImportAction;
import jp.sourceforge.tmdmaker.action.GenerateAction;
import jp.sourceforge.tmdmaker.action.ImplementInfoEditAction;
import jp.sourceforge.tmdmaker.action.MultivalueAndCreateAction;
import jp.sourceforge.tmdmaker.action.MultivalueOrCreateAction;
import jp.sourceforge.tmdmaker.action.SubsetCreateAction;
import jp.sourceforge.tmdmaker.action.SubsetTypeTurnAction;
import jp.sourceforge.tmdmaker.action.VirtualEntityCreateAction;
import jp.sourceforge.tmdmaker.action.VirtualSupersetCreateAction;
import jp.sourceforge.tmdmaker.editpart.AbstractEntityEditPart;
import jp.sourceforge.tmdmaker.editpart.DiagramEditPart;
import jp.sourceforge.tmdmaker.editpart.TMDEditPartFactory;
import jp.sourceforge.tmdmaker.extension.GeneratorFactory;
import jp.sourceforge.tmdmaker.extension.SerializerFactory;
import jp.sourceforge.tmdmaker.importer.impl.AttributeFileImporter;
import jp.sourceforge.tmdmaker.importer.impl.EntityFileImporter;
import jp.sourceforge.tmdmaker.model.Diagram;
import jp.sourceforge.tmdmaker.model.Entity;
import jp.sourceforge.tmdmaker.model.Version;
import jp.sourceforge.tmdmaker.model.generate.Generator;
import jp.sourceforge.tmdmaker.model.persistence.SerializationException;
import jp.sourceforge.tmdmaker.model.persistence.Serializer;
import jp.sourceforge.tmdmaker.ruler.TMDRulerProvider;
import jp.sourceforge.tmdmaker.ruler.model.RulerModel;
import jp.sourceforge.tmdmaker.tool.EntityCreationTool;
import jp.sourceforge.tmdmaker.tool.MovableSelectionTool;
import jp.sourceforge.tmdmaker.tool.TMDConnectionCreationTool;

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
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.SnapToGeometry;
import org.eclipse.gef.editparts.FreeformGraphicalRootEditPart;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
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
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.gef.ui.actions.ToggleGridAction;
import org.eclipse.gef.ui.actions.ToggleRulerVisibilityAction;
import org.eclipse.gef.ui.actions.ToggleSnapToGeometryAction;
import org.eclipse.gef.ui.parts.ContentOutlinePage;
import org.eclipse.gef.ui.parts.GraphicalEditorWithPalette;
import org.eclipse.gef.ui.parts.ScrollingGraphicalViewer;
import org.eclipse.gef.ui.parts.TreeViewer;
import org.eclipse.gef.ui.rulers.RulerComposite;
import org.eclipse.jface.action.IAction;
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
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;
import org.osgi.framework.Bundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TMDエディター
 * 
 * @author nakaG
 * 
 */
public class TMDEditor extends GraphicalEditorWithPalette implements
		IResourceChangeListener {

	/**
	 * アウトラインページ
	 * 
	 * @author nakaG
	 * 
	 */
	private class TMDContentOutlinePage extends ContentOutlinePage {
		private SashForm sash;
		private ScrollableThumbnail thumbnail;
		private DisposeListener disposeListener;

		public TMDContentOutlinePage() {
			super(new TreeViewer());
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
			thumbnail = new ScrollableThumbnail(
					(Viewport) ((FreeformGraphicalRootEditPart) getGraphicalViewer()
							.getRootEditPart()).getFigure());
			thumbnail
					.setSource(((FreeformGraphicalRootEditPart) getGraphicalViewer()
							.getRootEditPart())
							.getLayer(LayerConstants.PRINTABLE_LAYERS));

			lws.setContents(thumbnail);

			disposeListener = new DisposeListener() {

				@Override
				public void widgetDisposed(DisposeEvent e) {
					if (thumbnail != null) {
						thumbnail.deactivate();
						thumbnail = null;
					}
				}
			};
			getGraphicalViewer().getControl().addDisposeListener(
					disposeListener);

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
			// getSelectionSynchronizer().removeViewer(getViewer());
			if (getGraphicalViewer().getControl() != null
					&& !getGraphicalViewer().getControl().isDisposed()) {
				getGraphicalViewer().getControl().removeDisposeListener(
						disposeListener);
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
		logger.debug("{} is instanciate.", TMDEditor.class);
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
		rulerComp
				.setGraphicalViewer((ScrollingGraphicalViewer) getGraphicalViewer());
	}

	// @Override
	// protected Control getGraphicalControl() {
	// return rulerComp;
	// }

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
			Serializer serializer = SerializerFactory.getInstance();
			diagram = serializer.deserialize(file.getContents());
		} catch (Exception e) {
			TMDPlugin.showErrorDialog("読み込み時にエラーが発生しました。", e);
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
		Bundle bundle = TMDPlugin.getDefault().getBundle();
		return new Version(bundle.getHeaders().get("Bundle-Version"));
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
		// カーソルキーでモデルを移動できるようにSelectionToolを拡張
		tool.setToolClass(MovableSelectionTool.class);

		toolGroup.add(tool);
		root.setDefaultEntry(tool);

		tool = new MarqueeToolEntry();
		toolGroup.add(tool);

		PaletteDrawer drawer = new PaletteDrawer("作成");

		ImageDescriptor descriptor = TMDPlugin
				.getImageDescriptor("icons/new_entity.gif");

		CreationToolEntry creationEntry = new CreationToolEntry("エンティティ",
				"エンティティ", new SimpleFactory(Entity.class), descriptor,
				descriptor);
		creationEntry.setToolClass(EntityCreationTool.class);

		drawer.add(creationEntry);

		descriptor = TMDPlugin.getImageDescriptor("icons/new_relationship.gif");

		ConnectionCreationToolEntry connxCCreationEntry = new ConnectionCreationToolEntry(
				"リレーションシップ", "リレーションシップ", null, descriptor, descriptor);
		connxCCreationEntry.setToolClass(TMDConnectionCreationTool.class);
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
			TMDPlugin.showErrorDialog("保存時にエラーが発生しました。", e);
			logger.warn("IFile#deleteMarkers()." + e);
		}
		try {
			Serializer serializer = SerializerFactory.getInstance();
			file.setContents(serializer.serialize(diagram), true, true, monitor);
		} catch (SerializationException e) {
			TMDPlugin.showErrorDialog("保存時にエラーが発生しました。", e);
			logger.warn("IFile#setContents().", e);
		} catch (CoreException e) {
			TMDPlugin.showErrorDialog("保存時にエラーが発生しました。", e);
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
		logger.debug("doSaveAs() called");
		Shell shell = getSite().getWorkbenchWindow().getShell();
		SaveAsDialog dialog = new SaveAsDialog(shell);
		dialog.setOriginalFile(((IFileEditorInput) getEditorInput()).getFile());
		dialog.open();

		IPath path = dialog.getResult();
		if (path == null) {
			return;
		}
		final IFile file = ResourcesPlugin.getWorkspace().getRoot()
				.getFile(path);
		try {
			new ProgressMonitorDialog(shell).run(false, // don't fork
					false, // not cancelable
					new WorkspaceModifyOperation() { // run this operation

						@Override
						public void execute(IProgressMonitor monitor) {
							Diagram diagram = (Diagram) getGraphicalViewer()
									.getContents().getModel();
							try {
								Serializer serializer = SerializerFactory
										.getInstance();
								file.create(serializer.serialize(diagram),
										true, monitor);
							} catch (SerializationException e) {
								TMDPlugin.showErrorDialog("保存時にエラーが発生しました。", e);
								logger.warn("IFile#setContents().", e);
							} catch (CoreException e) {
								TMDPlugin.showErrorDialog("保存時にエラーが発生しました。", e);
								logger.warn("IFile#setContents().", e);
							}
							getCommandStack().markSaveLocation();
							setInput(new FileEditorInput(file));

						}
					});

			// setInput(new FileEditorInput(file));
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

		SelectionAction selectionAction = new SubsetCreateAction(this);
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

		IAction action = null;
		// 水平方向の整列アクション
		action = new AlignmentAction((IWorkbenchPart) this,
				PositionConstants.LEFT);
		registry.registerAction(action);
		selectionActions.add(action.getId());

		action = new AlignmentAction((IWorkbenchPart) this,
				PositionConstants.CENTER);
		registry.registerAction(action);
		selectionActions.add(action.getId());

		action = new AlignmentAction((IWorkbenchPart) this,
				PositionConstants.RIGHT);
		registry.registerAction(action);
		selectionActions.add(action.getId());

		// 垂直方向の整列アクション
		action = new AlignmentAction((IWorkbenchPart) this,
				PositionConstants.TOP);
		registry.registerAction(action);
		selectionActions.add(action.getId());

		action = new AlignmentAction((IWorkbenchPart) this,
				PositionConstants.MIDDLE);
		registry.registerAction(action);
		selectionActions.add(action.getId());

		action = new AlignmentAction((IWorkbenchPart) this,
				PositionConstants.BOTTOM);
		registry.registerAction(action);
		selectionActions.add(action.getId());

		action = new AutoSizeSettingAction(this);
		registry.registerAction(action);
		selectionActions.add(action.getId());
	}

	private void setupSelectionAction(ActionRegistry registry,
			List<String> selectionActions, SelectionAction selectionAction) {
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
		logger.debug("configureGraphicalViewer() called");
		super.configureGraphicalViewer();
		GraphicalViewer viewer = getGraphicalViewer();
		viewer.setRootEditPart(new ScalableFreeformRootEditPart());
		viewer.setEditPartFactory(new TMDEditPartFactory());

		ContextMenuProvider provider = new TMDContextMenuProvider(viewer,
				getActionRegistry());
		viewer.setContextMenu(provider);

		// ContextMenuにRun as等を表示しないようにするためIWorkbenchPartSiteに登録しない
		// getSite().registerContextMenu("tmd.contextmenu", provider, viewer);

		// viewerを取得するためcreateActionsメソッドではなくここでアクションを登録
		ActionRegistry registry = getActionRegistry();
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

		action = new FileImportAction(viewer, new EntityFileImporter());
		registry.registerAction(action);

		action = new FileImportAction(viewer, new AttributeFileImporter());
		registry.registerAction(action);

		action = new ToggleGridAction(viewer);
		registry.registerAction(action);

		action = new ToggleRulerVisibilityAction(viewer);
		getActionRegistry().registerAction(action);

		action = new ToggleSnapToGeometryAction(viewer);
		getActionRegistry().registerAction(action);

		loadProperties();
	}

	private void loadProperties() {
		// getGraphicalViewer().setProperty(SnapToGrid.PROPERTY_GRID_ENABLED,
		// true);
		// getGraphicalViewer().setProperty(SnapToGrid.PROPERTY_GRID_VISIBLE,
		// true);

		// ルーラーは垂直と水平位置に表示させる。
		TMDRulerProvider provider = new TMDRulerProvider(new RulerModel());
		getGraphicalViewer().setProperty(
				RulerProvider.PROPERTY_HORIZONTAL_RULER, provider);
		provider = new TMDRulerProvider(new RulerModel());
		getGraphicalViewer().setProperty(RulerProvider.PROPERTY_VERTICAL_RULER,
				provider);
		// ルーラーは初期表示しない。
		getGraphicalViewer().setProperty(
				RulerProvider.PROPERTY_RULER_VISIBILITY, Boolean.FALSE);

		// スナップ機能はデフォルトでは無効とする。
		getGraphicalViewer().setProperty(SnapToGeometry.PROPERTY_SNAP_ENABLED,
				Boolean.FALSE);

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.ui.parts.GraphicalEditor#getAdapter(java.lang.Class)
	 */
	@Override
	public Object getAdapter(@SuppressWarnings("rawtypes") Class type) {
		if (type == IContentOutlinePage.class) {
			return new TMDContentOutlinePage();
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
		List editParts = getGraphicalViewer().getRootEditPart().getChildren();
		
		for (Object o: editParts) {
			logger.debug(o.getClass().getName());
			if (o instanceof AbstractEntityEditPart) {
				((AbstractEntityEditPart)o).updateAppearance();
			} else if (o instanceof DiagramEditPart) {
				for (Object ob : ((DiagramEditPart)o).getChildren()) {
					if (ob instanceof AbstractEntityEditPart) {
						((AbstractEntityEditPart)ob).updateAppearance();
					}		
				}
			}
		}
	}
}
