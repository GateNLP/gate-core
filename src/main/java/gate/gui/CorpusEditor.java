/*
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Valentin Tablan 12/07/2001
 *
 *  $Id: CorpusEditor.java 19641 2016-10-06 07:24:25Z markagreenwood $
 *
 */
package gate.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.*;
import java.util.*;
import java.io.IOException;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.*;

import gate.*;
import gate.creole.AbstractVisualResource;
import gate.event.CorpusEvent;
import gate.creole.metadata.CreoleResource;
import gate.creole.metadata.GuiType;
import gate.event.CorpusListener;
import gate.event.CreoleListener;
import gate.event.CreoleEvent;
import gate.swing.XJTable;
import gate.swing.XJPopupMenu;
import gate.util.GateException;
import gate.util.GateRuntimeException;

/**
 * A simple viewer/editor for corpora. It will allow the visualisation of the
 * list of documents inside a corpus along with their features.
 * It will also allow addition and removal of documents.
 */
@SuppressWarnings("serial")
@CreoleResource(name = "Corpus editor", guiType = GuiType.LARGE,
    resourceDisplayed = "gate.Corpus", mainViewer = true)
public class CorpusEditor extends AbstractVisualResource
  implements CorpusListener {

  @Override
  public Resource init(){
    initLocalData();
    initGuiComponents();
    initListeners();
    return this;
  }


  protected void initLocalData(){
    docTableModel = new DocumentTableModel();
    try {
      documentsLoadedCount = Gate.getCreoleRegister()
        .getAllInstances("gate.Document").size();
    } catch (GateException exception) {
      exception.printStackTrace();
    }
  }

  protected void initGuiComponents(){
    setLayout(new BorderLayout());
    renderer = new DocumentNameRenderer();
    
    docTable = new XJTable(docTableModel);
    docTable.setSortable(true);
    docTable.setSortedColumn(DocumentTableModel.COL_INDEX);
    docTable.setAutoResizeMode(XJTable.AUTO_RESIZE_LAST_COLUMN);
    docTable.getColumnModel().getColumn(DocumentTableModel.COL_NAME).
        setCellRenderer(renderer);
    docTable.setDragEnabled(true);
    docTable.setTransferHandler(new TransferHandler() {
      // drag and drop to move up and down the table rows
      // import selected documents from the resources tree
      String source = "";
      @Override
      public int getSourceActions(JComponent c) {
        return MOVE;
      }
      @Override
      protected Transferable createTransferable(JComponent c) {
        int selectedRows[] = docTable.getSelectedRows();
        Arrays.sort(selectedRows);
        return new StringSelection("CorpusEditor"
          + Arrays.toString(selectedRows));
      }
      @Override
      protected void exportDone(JComponent c, Transferable data, int action) {
      }
      @Override
      public boolean canImport(JComponent c, DataFlavor[] flavors) {
        for(DataFlavor flavor : flavors) {
          if(DataFlavor.stringFlavor.equals(flavor)) {
            return true;
          }
        }
        return false;
      }
      @Override
      public boolean importData(JComponent c, Transferable t) {
        if (!canImport(c, t.getTransferDataFlavors())) {
          return false;
        }
        try {
          source = (String)t.getTransferData(DataFlavor.stringFlavor);
          if (source.startsWith("ResourcesTree")) {
            int insertion = docTable.getSelectedRow();
            List<Document> documents = new ArrayList<Document>();
            source = source.replaceFirst("^ResourcesTree\\[", "");
            source = source.replaceFirst("\\]$", "");
            final String documentsNames[] = source.split(", ");
            List<Resource> loadedDocuments;
            try {
              loadedDocuments =
                Gate.getCreoleRegister().getAllInstances("gate.Document");
            } catch(GateException e) {
              e.printStackTrace();
              return false;
            }
            // get the list of documents selected when dragging started
            for(String documentName : documentsNames) {
              for (Resource loadedDocument : loadedDocuments) {
                if (loadedDocument.getName().equals(documentName)
                 && !corpus.contains(loadedDocument)) {
                  documents.add((Document) loadedDocument);
                }
              }
            }
            // add the documents at the insertion point
            for (Document document : documents) {
              if (insertion != -1) {
                corpus.add(docTable.rowViewToModel(insertion), document);
                if (insertion == docTable.getRowCount()) { insertion++; }
              } else {
                corpus.add(document);
              }
            }
            // select the moved/already existing documents
            SwingUtilities.invokeLater(new Runnable() {
              @Override
              public void run() {
                docTable.clearSelection();
                for (String documentName : documentsNames) {
                  for (int row = 0; row < docTable.getRowCount(); row++) {
                    if (docTable.getValueAt(
                          row, docTable.convertColumnIndexToView(1))
                        .equals(documentName)) {
                      docTable.addRowSelectionInterval(row, row);
                    }
                  }
                }
              }
            });
            changeMessage();
            return true;

          } else if (source.startsWith("CorpusEditor")) {
            int insertion = docTable.getSelectedRow();
            int initialInsertion = insertion;
            List<Document> documents = new ArrayList<Document>();
            source = source.replaceFirst("^CorpusEditor\\[", "");
            source = source.replaceFirst("\\]$", "");
            String selectedRows[] = source.split(", ");
            if (Integer.parseInt(selectedRows[0]) < insertion) { insertion++; }
            // get the list of documents selected when dragging started
            for(String row : selectedRows) {
              if (Integer.parseInt(row) == initialInsertion) {
                // the user dragged the selected rows on themselves, do nothing
                return false;
              }
              documents.add(corpus.get(
                docTable.rowViewToModel(Integer.parseInt(row))));
              if (Integer.parseInt(row) < initialInsertion) { insertion--; }
            }
            // remove the documents selected when dragging started
            for(Document document : documents) {
              corpus.remove(document);
            }
            // add the documents at the insertion point
            for (Document document : documents) {
              corpus.add(docTable.rowViewToModel(insertion), document);
              insertion++;
            }
            // select the moved documents
            docTable.addRowSelectionInterval(
              insertion - selectedRows.length, insertion - 1);
            return true;

          } else {
            return false;
          }

        } catch (UnsupportedFlavorException ufe) {
          return false;
        } catch (IOException ioe) {
          return false;
        }
      }
    });

    JScrollPane scroller = new JScrollPane(docTable);
    scroller.setHorizontalScrollBarPolicy(
            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    scroller.getViewport().setBackground(docTable.getBackground());
    add(scroller, BorderLayout.CENTER);

    toolbar = new JToolBar();
    toolbar.setFloatable(false);
    toolbar.add(newDocumentAction = new NewDocumentAction());
    toolbar.add(removeDocumentsAction = new RemoveDocumentsAction());
    toolbar.addSeparator();
    toolbar.add(moveUpAction = new MoveUpAction());
    toolbar.add(moveDownAction = new MoveDownAction());
    toolbar.addSeparator();
    toolbar.add(openDocumentsAction = new OpenDocumentsAction());

    removeDocumentsAction.setEnabled(false);
    moveUpAction.setEnabled(false);
    moveDownAction.setEnabled(false);
    openDocumentsAction.setEnabled(false);

    JPanel topPanel = new JPanel(new BorderLayout());
    topPanel.add(toolbar, BorderLayout.NORTH);

    messageLabel = new JLabel();
    changeMessage();
    topPanel.add(messageLabel, BorderLayout.SOUTH);

    add(topPanel, BorderLayout.NORTH);
  }

  protected void initListeners(){

    // mouse double-click to open the document
    // context menu to get the actions for the selection
    docTable.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        processMouseEvent(e);
      }
      @Override
      public void mousePressed(MouseEvent e) {
        if(e.isPopupTrigger()) { processMouseEvent(e); }
      }
      @Override
      public void mouseReleased(MouseEvent e) {
        if(e.isPopupTrigger()) { processMouseEvent(e); }
      }
      private void processMouseEvent(MouseEvent e) {
        int row = docTable.rowAtPoint(e.getPoint());
        if(row == -1) { return; }

        if(e.isPopupTrigger()) {
          // context menu
          if(!docTable.isRowSelected(row)) {
            // if right click outside the selection then reset selection
            docTable.getSelectionModel().setSelectionInterval(row, row);
          }
          JPopupMenu popup = new XJPopupMenu();
          popup.add(openDocumentsAction);
          popup.add(removeDocumentsAction);
          popup.show(docTable, e.getPoint().x, e.getPoint().y);

        } else if(e.getID() == MouseEvent.MOUSE_CLICKED
               && e.getClickCount() == 2) {
          // open document on double-click
          openDocumentsAction.actionPerformed(null);
        }
      }
    });

    // Enter key opens the selected documents
    docTable.addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
          openDocumentsAction.actionPerformed(null);
        }
      }
    });

    docTable.getSelectionModel().addListSelectionListener(
      new ListSelectionListener() {
        @Override
        public void valueChanged(ListSelectionEvent e) {
          // enable/disable buttons according to the selection
          removeDocumentsAction.setEnabled(docTable.getSelectedRowCount() > 0);
          openDocumentsAction.setEnabled(docTable.getSelectedRowCount() > 0);
          moveUpAction.setEnabled(docTable.getSelectedRowCount() > 0
            && !docTable.isRowSelected(0));
          moveDownAction.setEnabled(docTable.getSelectedRowCount() > 0
            && !docTable.isRowSelected(docTable.getRowCount() - 1));
        }
      });

    Gate.getCreoleRegister().addCreoleListener(new CreoleListener() {
      @Override
      public void resourceLoaded(CreoleEvent e) {
        if (e.getResource() instanceof Document) {
          documentsLoadedCount++;
          changeMessage();
        }
      }
      @Override
      public void resourceUnloaded(CreoleEvent e) {
        if (e.getResource() instanceof Document) {
          documentsLoadedCount--;
          changeMessage();
        }
      }
      @Override
      public void datastoreOpened(CreoleEvent e) { /* do nothing */ }
      @Override
      public void datastoreCreated(CreoleEvent e) { /* do nothing */ }
      @Override
      public void datastoreClosed(CreoleEvent e) { /* do nothing */ }
      @Override
      public void resourceRenamed(Resource resource, String oldName,
                                  String newName) { /* do nothing */ }
    });
  }

  @Override
  public void cleanup(){
    super.cleanup();
    corpus = null;
  }

  @Override
  public void setTarget(Object target){
    if(corpus != null && corpus != target){
      //we already had a different corpus
      corpus.removeCorpusListener(this);
    }
    if(!(target instanceof Corpus)){
      throw new IllegalArgumentException(
        "The GATE corpus editor can only be used with a GATE corpus!\n" +
        target.getClass().toString() + " is not a GATE corpus!");
    }
    this.corpus = (Corpus)target;
    corpus.addCorpusListener(this);
    docTableModel.dataChanged();
    SwingUtilities.invokeLater(new Runnable(){
      @Override
      public void run(){
        docTableModel.fireTableDataChanged();
      }
    });
  }

  @Override
  public void documentAdded(final CorpusEvent e) {
    docTableModel.dataChanged();
    SwingUtilities.invokeLater(new Runnable(){
      @Override
      public void run(){
        changeMessage();
        docTableModel.fireTableRowsInserted(e.getDocumentIndex(),
                e.getDocumentIndex());
      }
    });
  }

  @Override
  public void documentRemoved(final CorpusEvent e) {
    docTableModel.dataChanged();
    SwingUtilities.invokeLater(new Runnable(){
      @Override
      public void run(){
        changeMessage();
        docTableModel.fireTableRowsDeleted(e.getDocumentIndex(), 
                e.getDocumentIndex());
      }
    });
  }

  class DocumentTableModel extends AbstractTableModel{
    public DocumentTableModel(){
      documentNames = new ArrayList<String>();
    }
    
    /**
     * Called externally when the underlying corpus has changed.
     */
    private void dataChanged(){
      List<String> newDocs = new ArrayList<String>();
      if(corpus != null){
        newDocs.addAll(corpus.getDocumentNames());
     }
      List<String> oldDocs = documentNames;
      documentNames = newDocs;
      oldDocs.clear();
    }
    
    @Override
    public int getColumnCount() {
      return COLUMN_COUNT;
    }

    @Override
    public int getRowCount() {
      return documentNames.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
      //invalid indexes might appear when update events are slow to act 
      if(rowIndex < 0 || rowIndex >= documentNames.size() || 
         columnIndex < 0 || columnIndex > COLUMN_COUNT) return null;
      switch(columnIndex) {
        case COL_INDEX:
          return rowIndex;
        case COL_NAME:
          return documentNames.get(rowIndex);
        default:
          return null;
      }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
      switch(columnIndex) {
        case COL_INDEX:
          return Integer.class;
        case COL_NAME:
          return String.class;
        default:
          return String.class;
      }
    }

    @Override
    public String getColumnName(int column) {
      return COLUMN_NAMES[column];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
      return false;
    }
    
    private List<String> documentNames;
    private final String[] COLUMN_NAMES = {"Index", "Document name"}; 
    private static final int COL_INDEX = 0;
    private static final int COL_NAME = 1;
    private static final int COLUMN_COUNT = 2;
  }

  class DocumentNameRenderer extends DefaultTableCellRenderer implements 
      ListCellRenderer<String>{
    public DocumentNameRenderer(){
      super();
      setIcon(MainFrame.getIcon("document"));
    }
    
    @Override
    public Component getListCellRendererComponent(JList<? extends String> list, String value,
            int index, boolean isSelected, boolean cellHasFocus) {
      // prepare the renderer

      return getTableCellRendererComponent(docTable, value, isSelected, 
              cellHasFocus, index, DocumentTableModel.COL_NAME);
    }

    @Override
    public Dimension getMaximumSize() {
      //we don't mind being extended horizontally
      Dimension dim = super.getMaximumSize();
      if(dim != null){
        dim.width = Integer.MAX_VALUE;
        setMaximumSize(dim);
      }
      return dim;
    }

    @Override
    public Dimension getMinimumSize() {
      //we don't like being squashed!
      return getPreferredSize();
    }
    
    
  }
  
  class MoveUpAction extends AbstractAction{
    public MoveUpAction(){
      super("Move up", MainFrame.getIcon("up"));
      putValue(SHORT_DESCRIPTION, "Moves selected document(s) up");
      putValue(MNEMONIC_KEY, KeyEvent.VK_UP);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      int[] rowsTable = docTable.getSelectedRows();
      int[] rowsCorpus = new int[rowsTable.length];
      for(int i = 0; i < rowsTable.length; i++)
        rowsCorpus[i] = docTable.rowViewToModel(rowsTable[i]);
      Arrays.sort(rowsCorpus);
      //starting from the smallest one, move each element up
      for(int i = 0; i < rowsCorpus.length; i++){
        if(rowsCorpus[i] > 0){
          //swap the doc with the one before
          //serial corpus does not load the document on remove, so we need
          //to load the document explicitly
          boolean wasLoaded = corpus.isDocumentLoaded(rowsCorpus[i]);
          Document doc = corpus.get(rowsCorpus[i]);
          corpus.remove(rowsCorpus[i]);
          rowsCorpus[i] = rowsCorpus[i] - 1;
          corpus.add(rowsCorpus[i], doc);
          if(!wasLoaded){
            corpus.unloadDocument(doc);
            Factory.deleteResource(doc);
          }
        }
      }
      //restore selection
      //the remove / add events will cause the table to be updated
      //we need to only restore the selection after that happened
      final int[] selectedRowsCorpus = new int[rowsCorpus.length];
      System.arraycopy(rowsCorpus, 0, selectedRowsCorpus, 0, rowsCorpus.length);
      SwingUtilities.invokeLater(new Runnable(){
        @Override
        public void run(){
          docTable.clearSelection();
          for(int i = 0; i < selectedRowsCorpus.length; i++){
            int rowTable = docTable.rowModelToView(selectedRowsCorpus[i]);
            docTable.getSelectionModel().addSelectionInterval(rowTable, 
                    rowTable);
          }                
        }
      });
    }
  }

  class MoveDownAction extends AbstractAction{
    public MoveDownAction(){
      super("Move down", MainFrame.getIcon("down"));
      putValue(SHORT_DESCRIPTION, "Moves selected document(s) down");
      putValue(MNEMONIC_KEY, KeyEvent.VK_DOWN);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      int[] rowsTable = docTable.getSelectedRows();
      int[] rowsCorpus = new int[rowsTable.length];
      for(int i = 0; i < rowsTable.length; i++)
        rowsCorpus[i] = docTable.rowViewToModel(rowsTable[i]);
      Arrays.sort(rowsCorpus);
      //starting from the largest one, move each element down
      for(int i = rowsCorpus.length -1; i >=0; i--){
        if(rowsCorpus[i] < corpus.size() -1){
          //swap the doc with the one before
          //serial corpus does not load the document on remove, so we need
          //to load the document explicitly
          boolean wasLoaded = corpus.isDocumentLoaded(rowsCorpus[i]);
          Document doc = corpus.get(rowsCorpus[i]);
          corpus.remove(rowsCorpus[i]);
          rowsCorpus[i]++;
          corpus.add(rowsCorpus[i], doc);
          if(!wasLoaded){
            corpus.unloadDocument(doc);
            Factory.deleteResource(doc);
          }
        }
      }
      //restore selection
      //the remove / add events will cause the table to be updated
      //we need to only restore the selection after that happened
      final int[] selectedRowsCorpus = new int[rowsCorpus.length];
      System.arraycopy(rowsCorpus, 0, selectedRowsCorpus, 0, rowsCorpus.length);
      SwingUtilities.invokeLater(new Runnable(){
        @Override
        public void run(){
          docTable.clearSelection();
          for(int i = 0; i < selectedRowsCorpus.length; i++){
            int rowTable = docTable.rowModelToView(selectedRowsCorpus[i]);
            docTable.getSelectionModel().addSelectionInterval(rowTable, 
                    rowTable);
          }                
        }
      });
    }
  }

  class NewDocumentAction extends AbstractAction{
    public NewDocumentAction(){
      super("Add document", MainFrame.getIcon("add-document"));
      putValue(SHORT_DESCRIPTION, "Add new document(s) to this corpus");
      putValue(MNEMONIC_KEY, KeyEvent.VK_ENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e){
      List<Resource> loadedDocuments;
      try {
        // get all the documents loaded in the system
        loadedDocuments = Gate.getCreoleRegister()
          .getAllInstances("gate.Document");
      } catch(GateException ge) {
        //gate.Document is not registered in creole.xml....what is!?
        throw new GateRuntimeException(
          "gate.Document is not registered in the creole register!\n" +
          "Something must be terribly wrong...take a vacation!");
      }
      Vector<String> docNames = new Vector<String>();
      for (Resource loadedDocument : new ArrayList<Resource>(loadedDocuments)) {
        if (corpus.contains(loadedDocument)) {
          loadedDocuments.remove(loadedDocument);
        } else {
          docNames.add(loadedDocument.getName());
        }
      }
      JList<String> docList = new JList<String>(docNames);
      docList.getSelectionModel().setSelectionInterval(0, docNames.size()-1);
      docList.setCellRenderer(renderer);
      final JOptionPane optionPane = new JOptionPane(new JScrollPane(docList),
        JOptionPane.QUESTION_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
      final JDialog dialog = optionPane.createDialog(CorpusEditor.this,
        "Add document(s) to this corpus");
      docList.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
          if (e.getClickCount() == 2) {
            optionPane.setValue(JOptionPane.OK_OPTION);
            dialog.dispose();
          }
        }
      });
      dialog.setVisible(true);
      if(optionPane.getValue().equals(JOptionPane.OK_OPTION)){
        int[] selectedIndices = docList.getSelectedIndices();
        for (int selectedIndice : selectedIndices) {
          corpus.add((Document)loadedDocuments.get(selectedIndice));
        }
      }
      changeMessage();
    }
  }

  class RemoveDocumentsAction extends AbstractAction{
    public RemoveDocumentsAction(){
      super("Remove documents", MainFrame.getIcon("remove-document"));
      putValue(SHORT_DESCRIPTION,
        "Removes selected document(s) from this corpus");
      putValue(MNEMONIC_KEY, KeyEvent.VK_DELETE);
    }

    @Override
    public void actionPerformed(ActionEvent e){
      int[] selectedIndexes = docTable.getSelectedRows();
      int[] corpusIndexes = new int[selectedIndexes.length];
      for(int i = 0; i < selectedIndexes.length; i++)
        corpusIndexes[i] = docTable.rowViewToModel(selectedIndexes[i]);
      Arrays.sort(corpusIndexes);
      //remove the document starting with the one with the highest index
      for(int i = corpusIndexes.length-1; i >= 0; i--){
        corpus.remove(corpusIndexes[i]);
      }
      docTable.clearSelection();
      changeMessage();
    }
  }

  class OpenDocumentsAction extends AbstractAction{
    public OpenDocumentsAction(){
      super("Open documents", MainFrame.getIcon("document"));
      putValue(SHORT_DESCRIPTION,
        "Opens selected document(s) in a document editor");
    }

    @Override
    public void actionPerformed(ActionEvent e){
      Component root = SwingUtilities.getRoot(CorpusEditor.this);
      if (!(root instanceof MainFrame)) { return; }
      final MainFrame mainFrame = (MainFrame) root;
      final int[] selectedRows = docTable.getSelectedRows();
      if (selectedRows.length > 10) {
        Object[] possibleValues =
          { "Open the "+selectedRows.length+" documents", "Don't open" };
        int selectedValue =
          JOptionPane.showOptionDialog(docTable, "Do you want to open "
          +selectedRows.length+" documents in the central tabbed pane ?",
          "Warning", JOptionPane.DEFAULT_OPTION,
          JOptionPane.QUESTION_MESSAGE, null,
          possibleValues, possibleValues[1]);
        if (selectedValue == 1
         || selectedValue == JOptionPane.CLOSED_OPTION) {
          return;
        }
      }
      for (int row : selectedRows) {
        // load the document if inside a datastore
        corpus.get(docTable.rowViewToModel(row));
      }
      SwingUtilities.invokeLater(new Runnable() { @Override
      public void run() {
        for (int row : selectedRows) {
          Document doc = corpus.get(docTable.rowViewToModel(row));
          // show the document in the central view
          mainFrame.select(doc);
        }
      }});
    }
  }

  protected void changeMessage() {
    SwingUtilities.invokeLater(new Runnable(){ @Override
    public void run() {
    if (corpus == null || corpus.size() == 0) {
      newDocumentAction.setEnabled(true);
      messageLabel.setText(
        "<html>To add or remove documents to this corpus:<ul>" +
        "<li>use the toolbar buttons at the top of this view" +
        "<li>drag documents from the left resources tree and drop them below" +
        "<li>right click on the corpus in the resources tree and choose 'Populate'" +
        "</ul></html>");
      messageLabel.setVisible(true);
    } 
    // This is a really stupid way of checking if all the open documents are in the
    //corpus and it seems to be causing more problems than it might possibly be trying
    //to solve
    /*else if (documentsLoadedCount > 0
            && documentsLoadedCount == corpus.size()) {
      newDocumentAction.setEnabled(false);
      messageLabel.setText("All the documents loaded in the " +
        "system are in this corpus.");
      messageLabel.setVisible(true);
    } */
    else if (documentsLoadedCount == 0) {
      newDocumentAction.setEnabled(false);
      if (corpus.getDataStore() == null) {
        messageLabel.setText(
          "There are no documents loaded in the system. " +
          "Press F1 for help.");
      } else {
        messageLabel.setText("Open a document to load it from the datastore.");
      }
      messageLabel.setVisible(true);
    } else {
      newDocumentAction.setEnabled(true);
      messageLabel.setVisible(false);
    }
    }});
  }

  protected XJTable docTable;
  protected DocumentTableModel docTableModel;
  protected DocumentNameRenderer renderer;
  protected JToolBar toolbar;
  protected Corpus corpus;
  protected NewDocumentAction newDocumentAction;
  protected RemoveDocumentsAction removeDocumentsAction;
  protected MoveUpAction moveUpAction;
  protected MoveDownAction moveDownAction;
  protected OpenDocumentsAction openDocumentsAction;
  protected JLabel messageLabel;
  protected int documentsLoadedCount;
}
