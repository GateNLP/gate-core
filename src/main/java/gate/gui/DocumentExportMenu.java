/*
 *  Copyright (c) 1995-2014, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Mark A. Greenwood 11/07/2014
 *
 */

package gate.gui;

import gate.Corpus;
import gate.CorpusExporter;
import gate.Document;
import gate.DocumentExporter;
import gate.Factory;
import gate.FeatureMap;
import gate.Gate;
import gate.Resource;
import gate.corpora.export.GateXMLExporter;
import gate.creole.Parameter;
import gate.creole.ParameterException;
import gate.creole.ResourceData;
import gate.event.CreoleEvent;
import gate.event.CreoleListener;
import gate.swing.XJFileChooser;
import gate.swing.XJMenu;
import gate.util.Err;
import gate.util.Files;
import gate.util.InvalidOffsetException;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.table.TableCellEditor;

import org.apache.log4j.Logger;

/**
 * A menu which updates as plugins are (un)loaded to allow the export of
 * documents and corpora to any of the supported output formats.
 */
@SuppressWarnings("serial")
public class DocumentExportMenu extends XJMenu implements CreoleListener {

  private static final Logger log = Logger.getLogger(DocumentExportMenu.class);

  static DocumentExportDialog dialog = new DocumentExportDialog();

  protected IdentityHashMap<Resource, JMenuItem> itemByResource =
          new IdentityHashMap<Resource, JMenuItem>();

  private Handle handle;

  public DocumentExportMenu(NameBearerHandle handle) {
    super("Save as...", "", handle.sListenerProxy);
    if(!(handle.getTarget() instanceof Document)
            && !(handle.getTarget() instanceof Corpus))
      throw new IllegalArgumentException(
              "We only deal with documents and corpora");
    this.handle = handle;
    init();
  }

  private void init() {

    DocumentExporter gateXMLExporter =
            (DocumentExporter)Gate.getCreoleRegister()
                    .get(GateXMLExporter.class.getCanonicalName())
                    .getInstantiations().iterator().next();
    addExporter(gateXMLExporter);

    Set<String> toolTypes = Gate.getCreoleRegister().getToolTypes();
    for(String type : toolTypes) {
      List<Resource> instances =
              Gate.getCreoleRegister().get(type).getInstantiations();
      for(Resource res : instances) {
        if(res instanceof DocumentExporter) {
          addExporter((DocumentExporter)res);
        }
      }
    }
    Gate.getCreoleRegister().addCreoleListener(this);
  }

  private File getSelectedFile(List<List<Parameter>> params,
          DocumentExporter de, FeatureMap options) {
    File selectedFile = null;

    Document document =
            (handle.getTarget() instanceof Document ? (Document)handle
                    .getTarget() : null);
    // are we looking for a file or a directory?
    boolean singleFile = (document != null) || (de instanceof CorpusExporter);

    if(document != null && document.getSourceUrl() != null) {
      String fileName = "";
      try {
        fileName = document.getSourceUrl().toURI().getPath().trim();
      } catch(URISyntaxException e) {
        fileName = document.getSourceUrl().getPath().trim();
      }
      if(fileName.equals("") || fileName.equals("/")) {
        if(document.getNamedAnnotationSets().containsKey("Original markups")
                && !document.getAnnotations("Original markups").get("title")
                        .isEmpty()) {
          // use the title annotation if any
          try {
            fileName =
                    document.getContent()
                            .getContent(
                                    document.getAnnotations("Original markups")
                                            .get("title").firstNode()
                                            .getOffset(),
                                    document.getAnnotations("Original markups")
                                            .get("title").lastNode()
                                            .getOffset()).toString();
          } catch(InvalidOffsetException e) {
            e.printStackTrace();
          }
        } else {
          fileName = document.getSourceUrl().toString();
        }
        // cleans the file name
        fileName = fileName.replaceAll("/", "_");
      } else {
        // replaces the extension with the default
        fileName =
                fileName.replaceAll("\\.[a-zA-Z]{1,4}$",
                        "." + de.getDefaultExtension());
      }
      // cleans the file name
      fileName = fileName.replaceAll("[^/a-zA-Z0-9._-]", "_");
      fileName = fileName.replaceAll("__+", "_");
      // adds the default extension if not present
      if(!fileName.endsWith("." + de.getDefaultExtension())) {
        fileName += "." + de.getDefaultExtension();
      }

      selectedFile = new File(fileName);
    }

    if(params == null || params.isEmpty()) {
      XJFileChooser fileChooser = MainFrame.getFileChooser();
      fileChooser.resetChoosableFileFilters();
      fileChooser.setFileFilter(de.getFileFilter());
      fileChooser.setMultiSelectionEnabled(false);
      fileChooser.setDialogTitle("Save as " + de.getFileType());
      fileChooser.setFileSelectionMode(singleFile
              ? JFileChooser.FILES_ONLY
              : JFileChooser.DIRECTORIES_ONLY);

      if(selectedFile != null) {
        fileChooser.ensureFileIsVisible(selectedFile);
        fileChooser.setSelectedFile(selectedFile);
      }

      if(fileChooser.showSaveDialog(MainFrame.getInstance()) != JFileChooser.APPROVE_OPTION)
        return null;
      selectedFile = fileChooser.getSelectedFile();
    } else {
      if(!dialog.show(de, params, singleFile, selectedFile != null
              ? selectedFile.getAbsolutePath()
              : "")) return null;

      options.putAll(dialog.getSelectedParameters());
      selectedFile = new File(dialog.getSelectedFileName());
    }

    return selectedFile;
  }

  private void addExporter(final DocumentExporter de) {

    if(itemByResource.containsKey(de)) return;

    final ResourceData rd =
            Gate.getCreoleRegister().get(de.getClass().getCanonicalName());

    if(DocumentExportMenu.this.getItemCount() == 1) {
      DocumentExportMenu.this.addSeparator();
    }

    JMenuItem item =
            DocumentExportMenu.this.add(new AbstractAction(de.getFileType()
                    + " (." + de.getDefaultExtension() + ")", MainFrame
                    .getIcon(rd.getIcon())) {

              @Override
              public void actionPerformed(ActionEvent ae) {

                List<List<Parameter>> params =
                        rd.getParameterList().getRuntimeParameters();

                final FeatureMap options = Factory.newFeatureMap();

                final File selectedFile = getSelectedFile(params, de, options);

                if(selectedFile == null) return;

                Runnable runnable = new Runnable() {
                  public void run() {

                    if(handle.getTarget() instanceof Document) {

                      long start = System.currentTimeMillis();
                      listener.statusChanged("Saving as " + de.getFileType()
                              + " to " + selectedFile.toString() + "...");
                      try {
                        de.export((Document)handle.getTarget(), selectedFile,
                                options);
                      } catch(IOException e) {
                        e.printStackTrace();
                      }

                      long time = System.currentTimeMillis() - start;
                      listener.statusChanged("Finished saving as "
                              + de.getFileType() + " into " + " the file: "
                              + selectedFile.toString() + " in "
                              + ((double)time) / 1000 + "s");
                    } else { // corpus
                      if(de instanceof CorpusExporter) {

                        long start = System.currentTimeMillis();
                        listener.statusChanged("Saving as " + de.getFileType()
                                + " to " + selectedFile.toString() + "...");
                        try {
                          ((CorpusExporter)de).export((Corpus)handle.getTarget(), selectedFile,
                                  options);
                        } catch(IOException e) {
                          e.printStackTrace();
                        }

                        long time = System.currentTimeMillis() - start;
                        listener.statusChanged("Finished saving as "
                                + de.getFileType() + " into " + " the file: "
                                + selectedFile.toString() + " in "
                                + ((double)time) / 1000 + "s");
                      } else { // not a CorpusExporter
                        try {
                          File dir = selectedFile;
                          // create the top directory if needed
                          if(!dir.exists()) {
                            if(!dir.mkdirs()) {
                              JOptionPane.showMessageDialog(
                                      MainFrame.getInstance(),
                                      "Could not create top directory!", "GATE",
                                      JOptionPane.ERROR_MESSAGE);
                              return;
                            }
                          }
  
                          MainFrame.lockGUI("Saving...");
  
                          Corpus corpus = (Corpus)handle.getTarget();
  
                          // iterate through all the docs and save
                          // each of
                          // them
                          Iterator<Document> docIter = corpus.iterator();
                          boolean overwriteAll = false;
                          int docCnt = corpus.size();
                          int currentDocIndex = 0;
                          Set<String> usedFileNames = new HashSet<String>();
                          while(docIter.hasNext()) {
                            boolean docWasLoaded =
                                    corpus.isDocumentLoaded(currentDocIndex);
                            Document currentDoc = docIter.next();
  
                            URL sourceURL = currentDoc.getSourceUrl();
                            String fileName = null;
                            if(sourceURL != null) {
                              fileName = sourceURL.getPath();
                              fileName = Files.getLastPathComponent(fileName);
                            }
                            if(fileName == null || fileName.length() == 0) {
                              fileName = currentDoc.getName();
                            }
                            // makes sure that the filename does not
                            // contain
                            // any
                            // forbidden character
                            fileName =
                                    fileName.replaceAll("[\\/:\\*\\?\"<>|]", "_");
                            if(fileName.toLowerCase().endsWith(
                                    "." + de.getDefaultExtension())) {
                              fileName =
                                      fileName.substring(0,
                                              fileName.length()
                                                      - de.getDefaultExtension()
                                                              .length()
                                                      - 1);
                            }
                            if(usedFileNames.contains(fileName)) {
                              // name clash -> add unique ID
                              String fileNameBase = fileName;
                              int uniqId = 0;
                              fileName = fileNameBase + "-" + uniqId++;
                              while(usedFileNames.contains(fileName)) {
                                fileName = fileNameBase + "-" + uniqId++;
                              }
                            }
                            usedFileNames.add(fileName);
                            if(!fileName.toLowerCase().endsWith(
                                    "." + de.getDefaultExtension()))
                              fileName += "." + de.getDefaultExtension();
                            File docFile = null;
                            boolean nameOK = false;
                            do {
                              docFile = new File(dir, fileName);
                              if(docFile.exists() && !overwriteAll) {
                                // ask the user if we can overwrite
                                // the file
                                Object[] opts =
                                        new Object[] {"Yes", "All", "No",
                                            "Cancel"};
                                MainFrame.unlockGUI();
                                int answer =
                                        JOptionPane.showOptionDialog(
                                                MainFrame.getInstance(), "File "
                                                        + docFile.getName()
                                                        + " already exists!\n"
                                                        + "Overwrite?", "GATE",
                                                JOptionPane.DEFAULT_OPTION,
                                                JOptionPane.WARNING_MESSAGE,
                                                null, opts, opts[2]);
                                MainFrame.lockGUI("Saving...");
                                switch(answer) {
                                  case 0: {
                                    nameOK = true;
                                    break;
                                  }
                                  case 1: {
                                    nameOK = true;
                                    overwriteAll = true;
                                    break;
                                  }
                                  case 2: {
                                    // user said NO, allow them to
                                    // provide
                                    // an
                                    // alternative name;
                                    MainFrame.unlockGUI();
                                    fileName =
                                            (String)JOptionPane.showInputDialog(
                                                    MainFrame.getInstance(),
                                                    "Please provide an alternative file name",
                                                    "GATE",
                                                    JOptionPane.QUESTION_MESSAGE,
                                                    null, null, fileName);
                                    if(fileName == null) {
                                      handle.processFinished();
                                      return;
                                    }
                                    MainFrame.lockGUI("Saving");
                                    break;
                                  }
                                  case 3: {
                                    // user gave up; return
                                    handle.processFinished();
                                    return;
                                  }
                                }
  
                              } else {
                                nameOK = true;
                              }
                            } while(!nameOK);
                            // save the file
                            try {
                              // do the actual exporting
                              de.export(currentDoc, docFile, options);
                            } catch(Exception ioe) {
                              MainFrame.unlockGUI();
                              JOptionPane.showMessageDialog(
                                      MainFrame.getInstance(),
                                      "Could not create write file:"
                                              + ioe.toString(), "GATE",
                                      JOptionPane.ERROR_MESSAGE);
                              ioe.printStackTrace(Err.getPrintWriter());
                              return;
                            }
  
                            listener.statusChanged(currentDoc.getName()
                                    + " saved");
                            // close the doc if it wasn't already
                            // loaded
                            if(!docWasLoaded) {
                              corpus.unloadDocument(currentDoc);
                              Factory.deleteResource(currentDoc);
                            }
  
                            handle.progressChanged(100 * currentDocIndex++
                                    / docCnt);
                          }// while(docIter.hasNext())
                          listener.statusChanged("Corpus Saved");
                          handle.processFinished();
                        } finally {
                          MainFrame.unlockGUI();
                        }
                      }
                    }
                  }
                };

                Thread thread =
                        new Thread(Thread.currentThread().getThreadGroup(),
                                runnable, "Document Exporter Thread");
                thread.setPriority(Thread.MIN_PRIORITY);
                thread.start();
              }

            });

    itemByResource.put(de, item);

  }

  /**
   * If the resource just loaded is a tool (according to the creole
   * register) then see if it publishes any actions and if so, add them
   * to the menu in the appropriate places.
   */
  @Override
  public void resourceLoaded(CreoleEvent e) {
    final Resource res = e.getResource();

    if(res instanceof DocumentExporter) {
      Runnable runnable = new Runnable() {
        @Override
        public void run() {
          addExporter((DocumentExporter)res);
        }
      };
      
      if(SwingUtilities.isEventDispatchThread()) {
        runnable.run();
      } else {
        try {
          SwingUtilities.invokeAndWait(runnable);
        } catch(Exception ex) {
          log.warn("Exception registering document exporter", ex);
        }
      }
    }
  }

  @Override
  public void resourceUnloaded(CreoleEvent e) {
    final Resource res = e.getResource();

    if(res instanceof DocumentExporter) {
      SwingUtilities.invokeLater(new Runnable() {
        
        @Override
        public void run() {
          // TODO Auto-generated method stub
          JMenuItem item = itemByResource.get(res);

          if(item != null) {
            remove(item);
            itemByResource.remove(res);
          }
          
          if(getItemCount() == 2) {
            remove(1);
          }
        }
      });     
    }    
  }

  // remaining CreoleListener methods not used
  @Override
  public void datastoreClosed(CreoleEvent e) {
  }

  @Override
  public void datastoreCreated(CreoleEvent e) {
  }

  @Override
  public void datastoreOpened(CreoleEvent e) {
  }

  @Override
  public void resourceRenamed(Resource resource, String oldName, String newName) {
  }

  private static class DocumentExportDialog extends JDialog {

    private DocumentExporter de;

    private JButton okBtn, fileBtn, cancelBtn;

    private JTextField txtFileName;

    private ResourceParametersEditor parametersEditor;

    private boolean singleFile, userCanceled;
    
    private FeatureMap parameters;

    public DocumentExportDialog() {
      super(MainFrame.getInstance(), "Save As...", true);
      MainFrame.getGuiRoots().add(this);
      initGuiComponents();
      initListeners();
    }

    protected void initGuiComponents() {
      this.getContentPane().setLayout(
              new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

      // name field
      Box nameBox = Box.createHorizontalBox();
      nameBox.add(Box.createHorizontalStrut(5));
      nameBox.add(new JLabel("Save To:"));
      nameBox.add(Box.createHorizontalStrut(5));
      txtFileName = new JTextField(30);
      txtFileName.setMaximumSize(new Dimension(Integer.MAX_VALUE, txtFileName
              .getPreferredSize().height));
      txtFileName.setRequestFocusEnabled(true);
      txtFileName.setVerifyInputWhenFocusTarget(false);
      nameBox.add(txtFileName);
      // nameField.setToolTipText("Enter a name for the resource");

      nameBox.add(Box.createHorizontalStrut(5));
      nameBox.add(fileBtn = new JButton(MainFrame.getIcon("OpenFile")));
      nameBox.add(Box.createHorizontalGlue());
      this.getContentPane().add(nameBox);
      this.getContentPane().add(Box.createVerticalStrut(5));

      // parameters table
      parametersEditor = new ResourceParametersEditor();
      this.getContentPane().add(new JScrollPane(parametersEditor));
      this.getContentPane().add(Box.createVerticalStrut(5));
      this.getContentPane().add(Box.createVerticalGlue());
      // buttons box
      JPanel buttonsBox = new JPanel();
      buttonsBox.setLayout(new BoxLayout(buttonsBox, BoxLayout.X_AXIS));
      buttonsBox.add(Box.createHorizontalStrut(10));
      buttonsBox.add(okBtn = new JButton("OK"));
      buttonsBox.add(Box.createHorizontalStrut(10));
      buttonsBox.add(cancelBtn = new JButton("Cancel"));
      buttonsBox.add(Box.createHorizontalStrut(10));
      this.getContentPane().add(buttonsBox);
      this.getContentPane().add(Box.createVerticalStrut(5));
      setSize(400, 300);

      getRootPane().setDefaultButton(okBtn);
    }

    protected void initListeners() {
      Action fileAction = new AbstractAction() {

        @Override
        public void actionPerformed(ActionEvent ae) {
          XJFileChooser fileChooser = MainFrame.getFileChooser();
          fileChooser.resetChoosableFileFilters();
          fileChooser.setFileFilter(de.getFileFilter());
          fileChooser.setMultiSelectionEnabled(false);
          fileChooser.setDialogTitle("Save as " + de.getFileType());
          fileChooser.setFileSelectionMode(singleFile
                  ? JFileChooser.FILES_ONLY
                  : JFileChooser.DIRECTORIES_ONLY);

          try {
            File f = new File(txtFileName.getText());
            fileChooser.ensureFileIsVisible(f);
            fileChooser.setSelectedFile(f);
          } catch(Exception e) {
            // swallow and ignore
          }

          if(fileChooser.showSaveDialog(DocumentExportDialog.this) != JFileChooser.APPROVE_OPTION)
            return;

          File selectedFile = fileChooser.getSelectedFile();
          if(selectedFile == null) return;

          txtFileName.setText(selectedFile.getAbsolutePath());
        }
      };

      Action applyAction = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
          userCanceled = false;
          TableCellEditor cellEditor = parametersEditor.getCellEditor();
          if(cellEditor != null) {
            cellEditor.stopCellEditing();
          }
          setVisible(false);
        }
      };
      Action cancelAction = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
          userCanceled = true;
          setVisible(false);
        }
      };

      fileBtn.addActionListener(fileAction);
      okBtn.addActionListener(applyAction);
      cancelBtn.addActionListener(cancelAction);

      // disable Enter key in the table so this key will confirm the
      // dialog
      InputMap im =
              parametersEditor
                      .getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
      KeyStroke enter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
      im.put(enter, "none");

      // define keystrokes action bindings at the level of the main
      // window
      InputMap inputMap =
              ((JComponent)this.getContentPane())
                      .getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
      ActionMap actionMap = ((JComponent)this.getContentPane()).getActionMap();
      inputMap.put(KeyStroke.getKeyStroke("ENTER"), "Apply");
      actionMap.put("Apply", applyAction);
      inputMap.put(KeyStroke.getKeyStroke("ESCAPE"), "Cancel");
      actionMap.put("Cancel", cancelAction);
    }

    public synchronized boolean show(DocumentExporter de,
            List<List<Parameter>> params, boolean singleFile, String filePath) {

      
      
      this.singleFile = singleFile;
      this.de = de;
      this.parameters = null;

      setTitle("Save as " + de.getFileType());

      txtFileName.setText(filePath);
      parametersEditor.init(null, params);
      pack();
      txtFileName.requestFocusInWindow();
      userCanceled = true;
      setModal(true);
      setLocationRelativeTo(getOwner());
      super.setVisible(true);
      dispose();
      if(userCanceled)
        return false;
      
      //update the feature map to convert values to objects of the correct type.
      
      parameters = parametersEditor.getParameterValues();
      
      for (List<Parameter> disjunction : params) {
        for (Parameter param : disjunction) {
          if (!param.getTypeName().equals("java.lang.String") && parameters.containsKey(param.getName())) {
            Object value = parameters.get(param.getName());
            if (value instanceof String) {
              try {
                parameters.put(param.getName(), param.calculateValueFromString((String)value));
              }
              catch (ParameterException pe) {
                pe.printStackTrace();
                parameters = null;
                return false;
              }
            }
          }
        }
      }
      
      return true;
    }

    @Override
    public void dispose() {
      de = null;
    }

    /**
     * Returns the selected params for the resource or null if none was
     * selected or the user pressed cancel
     */
    public FeatureMap getSelectedParameters() {
      return parameters;
    }

    /**
     * Return the String entered into the resource name field of the
     * dialog.
     * 
     * @param rData
     */
    public String getSelectedFileName() {
      return txtFileName.getText();
    }
  }
}
