package jp.sourceforge.tmdmaker;

import java.io.UnsupportedEncodingException;
import java.util.EventObject;
import java.util.List;

import jp.sourceforge.tmdmaker.action.MultivalueAndCreateAction;
import jp.sourceforge.tmdmaker.action.MultivalueOrCreateAction;
import jp.sourceforge.tmdmaker.action.SubsetEditAction;
import jp.sourceforge.tmdmaker.action.VirtualEntityCreateAction;
import jp.sourceforge.tmdmaker.action.VirtualSupersetCreateAction;
import jp.sourceforge.tmdmaker.dialog.EntityCreateDialog1;
import jp.sourceforge.tmdmaker.dialog.RelationshipEditDialog;
import jp.sourceforge.tmdmaker.editpart.TMDEditPartFactory;
import jp.sourceforge.tmdmaker.model.AbstractConnectionModel;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Diagram;
import jp.sourceforge.tmdmaker.model.Entity;
import jp.sourceforge.tmdmaker.model.Event2EventRelationship;
import jp.sourceforge.tmdmaker.model.AbstractRelationship;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel.EntityType;
import jp.sourceforge.tmdmaker.model.command.ConnectionCreateCommand;
import jp.sourceforge.tmdmaker.model.command.EntityCreateCommand;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
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
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author nakaG
 * 
 */
public class TMDEditor extends GraphicalEditorWithPalette {
	/** logging */
	private static Logger logger = LoggerFactory.getLogger(TMDEditor.class);

	/**
	 * Default Constructor
	 */
	public TMDEditor() {
		super();
		logger.debug("{} is instanciate.", TMDEditor.class);
		setEditDomain(new DefaultEditDomain(this));
		// getActionRegistry().registerAction(new UndoRetargetAction());
		// getActionRegistry().registerAction(new RedoRetargetAction());
		// getActionRegistry().registerAction(new DeleteRetargetAction());
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.ui.parts.GraphicalEditor#initializeGraphicalViewer()
	 */
	@Override
	protected void initializeGraphicalViewer() {
		logger.debug("initializeGraphicalViewer() called");
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
				"リレーションシップ", "リレーションシップ",
				null, descriptor, descriptor);
//		new SimpleFactory(AbstractRelationship.class), descriptor, descriptor);
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			file.setContents(XStreamSerializer.serializeStream(diagram, this
					.getClass().getClassLoader()), true, true, monitor);
		} catch (UnsupportedEncodingException e) {
			logger.warn("IFile#setContents:", e);
		} catch (CoreException e) {
			logger.warn("IFile#setContents:", e);
		}
		getCommandStack().markSaveLocation();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.ui.parts.GraphicalEditor#doSaveAs()
	 */
	@Override
	public void doSaveAs() {
		doSave(new NullProgressMonitor());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.ui.parts.GraphicalEditor#isSaveAsAllowed()
	 */
	// @Override
	// public boolean isSaveAsAllowed() {
	// return true;
	// }
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

					/*
					 * (non-Javadoc)
					 * 
					 * @seeorg.eclipse.gef.commands.CommandStackEventListener#
					 * stackChanged(org.eclipse.gef.commands.CommandStackEvent)
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
								// EntityCreateDialog1 dialog = new
								// EntityCreateDialog1(getGraphicalViewer().getControl().getShell());
								// if (dialog.open() == Dialog.OK) {
								//									
								// }
								EntityCreateDialog dialog = new EntityCreateDialog(
										getGraphicalViewer().getControl()
												.getShell());
								if (dialog.open() == Dialog.OK) {
									logger
											.debug(getClass().toString()
													+ "#stackChanged():dialog.open() == Dialog.OK)");
									command.setIdentifierName(dialog
											.getIdentifierName());
									command
											.setEntityName(createEntityName(dialog
													.getIdentifierName()));
									EntityType entityType = dialog
											.getEntityType();
									command.setEntityType(entityType);
									if (entityType.equals(EntityType.E)) {
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
					@Override
					public void stackChanged(CommandStackEvent event) {
						Command cmd = event.getCommand();
						if (cmd instanceof ConnectionCreateCommand) {
							ConnectionCreateCommand command = (ConnectionCreateCommand) cmd;
							AbstractConnectionModel<?> cnt = command
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

	private String createEntityName(String identifierName) {
		String[] suffixes = { "コード", "ID", "ＩＤ", "id", "ｉｄ", "番号", "No" };
		String[] reportSuffixes = { "伝票", "報告書", "書", "レポート" };
		String entityName = identifierName;
		for (String suffix : suffixes) {
			if (identifierName.endsWith(suffix)) {
				entityName = identifierName.substring(0, identifierName
						.lastIndexOf(suffix));
				break;
			}
		}
		for (String reportSuffix : reportSuffixes) {
			if (entityName.endsWith(reportSuffix)) {
				entityName = entityName.substring(0, entityName
						.lastIndexOf(reportSuffix));
				break;
			}
		}
		return entityName;
	}

	private static class EntityCreateDialog extends Dialog {
		private String identifierName;
		private Text identifierNameText;
		private EntityType entityType = EntityType.R;

		protected EntityCreateDialog(Shell parentShell) {
			super(parentShell);
			// TODO Auto-generated constructor stub
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt
		 * .widgets.Composite)
		 */
		@Override
		protected Control createDialogArea(Composite parent) {
			getShell().setText("エンティティ新規作成");
			Composite composite = new Composite(parent, SWT.NULL);
			composite.setLayout(new GridLayout(2, false));
			composite.setLayoutData(new GridData(GridData.FILL_BOTH));

			Label label = new Label(composite, SWT.NULL);
			label.setText("個体指示子");
			identifierNameText = new Text(composite, SWT.BORDER);
			identifierNameText.setLayoutData(new GridData(
					GridData.FILL_HORIZONTAL));

			label = new Label(composite, SWT.NULL);
			label.setText("類別");

			Group group = new Group(composite, SWT.SHADOW_OUT);
			group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			group.setBounds(-1, -1, -1, -1);
			Button r = new Button(group, SWT.RADIO);
			r.setText("リソース");
			r.setBounds(5, 10, 55, 20);
			r.addSelectionListener(new SelectionAdapter() {

				/*
				 * (non-Javadoc)
				 * 
				 * @see
				 * org.eclipse.swt.events.SelectionAdapter#widgetSelected(org
				 * .eclipse.swt.events.SelectionEvent)
				 */
				@Override
				public void widgetSelected(SelectionEvent e) {
					System.out.println("R widgetSelected");
					Button bBut = (Button) e.widget;
					if (bBut.getSelection()) {
						entityType = EntityType.R;
					}
				}

			});
			Button e = new Button(group, SWT.RADIO);
			e.setText("イベント");
			e.setBounds(80, 10, 55, 20);
			e.addSelectionListener(new SelectionAdapter() {

				/*
				 * (non-Javadoc)
				 * 
				 * @see
				 * org.eclipse.swt.events.SelectionAdapter#widgetSelected(org
				 * .eclipse.swt.events.SelectionEvent)
				 */
				@Override
				public void widgetSelected(SelectionEvent e) {
					System.out.println("E widgetSelected");
					Button bBut = (Button) e.widget;
					if (bBut.getSelection()) {
						entityType = EntityType.E;
					}
				}

			});
			r.setSelection(true);

			return composite;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
		 */
		@Override
		protected void okPressed() {
			this.identifierName = this.identifierNameText.getText();
			super.okPressed();
		}

		/**
		 * @return the identifierName
		 */
		public String getIdentifierName() {
			return identifierName;
		}

		/**
		 * @return the entityType
		 */
		public EntityType getEntityType() {
			return entityType;
		}

	}
}
