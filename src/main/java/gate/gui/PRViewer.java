/*
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Valentin Tablan 27/02/2002
 *
 *  $Id: PRViewer.java 17606 2014-03-09 12:12:49Z markagreenwood $
 *
 */
package gate.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractAction;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;

import gate.Gate;
import gate.Resource;
import gate.creole.AbstractVisualResource;
import gate.creole.ResourceData;
import gate.creole.ResourceInstantiationException;
import gate.creole.metadata.CreoleResource;
import gate.creole.metadata.GuiType;
import gate.swing.XJTable;
import gate.util.GateRuntimeException;

@SuppressWarnings("serial")
@CreoleResource(name = "Initialisation Parameters", guiType = GuiType.LARGE,
    resourceDisplayed = "gate.Resource", mainViewer = true)
public class PRViewer extends AbstractVisualResource {

  public PRViewer() {
  }

  
  @Override
  public Resource init() throws ResourceInstantiationException {
    initLocalData();
    initGuiComponents();
    initListeners();
    return this;
  }


  protected void initLocalData(){
  }

  protected void initGuiComponents(){
    setLayout(new BorderLayout());
    editor = new ResourceParametersEditor();
    editor.setEditable(false);
    editor.setAutoResizeMode(XJTable.AUTO_RESIZE_LAST_COLUMN);
    JScrollPane scroller = new JScrollPane(editor);
    scroller.setAlignmentX(Component.LEFT_ALIGNMENT);
    scroller.setAlignmentY(Component.TOP_ALIGNMENT);
    add(scroller, BorderLayout.CENTER);
  }

  protected void initListeners(){
    editor.addMouseListener(new MouseAdapter() {
      private void handleMouseEvent(MouseEvent e){
        if(e.isPopupTrigger()){
          int row = editor.rowAtPoint(e.getPoint());
          int col = editor.columnAtPoint(e.getPoint());
          JPopupMenu popup =new JPopupMenu();
          popup.add(new CopyValueAction(row, col));
          popup.show(editor,e.getX(), e.getY());
        }
      }
      
      @Override
      public void mousePressed(MouseEvent e) {
        handleMouseEvent(e);
      }

      @Override
      public void mouseReleased(MouseEvent e) {
        handleMouseEvent(e);
      }

      @Override
      public void mouseClicked(MouseEvent e) {
        handleMouseEvent(e);
      }
    });
  }

  @Override
  public void cleanup(){
    super.cleanup();
    editor.cleanup();
  }

  @Override
  public void setTarget(Object target){
    if(target == null) return;
    if(!(target instanceof Resource)){
      throw new GateRuntimeException(this.getClass().getName() +
                                     " can only be used to display " +
                                     Resource.class.getName() +
                                     "\n" + target.getClass().getName() +
                                     " is not a " +
                                     Resource.class.getName() + "!");
    }

    Resource pr = (Resource)target;
    ResourceData rData =
        Gate.getCreoleRegister().get(pr.getClass().getName());
    if(rData != null) {
      editor.init(pr, rData.getParameterList().getInitimeParameters());
    } else {
      editor.init(pr, null);
    }

    editor.removeCreoleListenerLink();
  }

  protected ResourceParametersEditor editor;
  
  protected class CopyValueAction extends AbstractAction{
    private int row, column;
    
    public CopyValueAction (int row, int column){
      super("Copy value");
      putValue(SHORT_DESCRIPTION, 
              "Copies the value of the cell to the clipboard.");
      this.row = row;
      this.column = column;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
      Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
      Object value = editor.getValueAt(row, column);
      String valStr;
      if(value instanceof ParameterDisjunction){
        valStr = ((ParameterDisjunction)value).getName();
      }else if(value instanceof Boolean){
        valStr = ((Boolean)value) ? "Required parameter" : "Optional parameter";
      }else{
        valStr = value.toString();
      }
      StringSelection data = new StringSelection(valStr);
      clipboard.setContents(data, data);
    }
  }
}
