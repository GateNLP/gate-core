/*
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Valentin Tablan, 13 Sep 2007
 *
 *  $Id: JChoice.java 17874 2014-04-18 11:19:47Z markagreenwood $
 */
package gate.swing;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.ItemSelectable;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.AbstractButton;
import javax.swing.Box;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.event.ListDataListener;

/**
 * A GUI component intended to allow quick selection from a set of
 * options. When the number of choices is small (i.e less or equal to
 * {@link #maximumFastChoices}) then the options are represented as a
 * set of buttons in a flow layout. If more options are available, a
 * simple {@link JComboBox} is used instead.
 */
@SuppressWarnings("serial")
public class JChoice<E> extends JPanel implements ItemSelectable {

  @Override
  public Object[] getSelectedObjects() {
    return new Object[]{getSelectedItem()};
  }

  /**
   * The default value for the {@link #maximumWidth} parameter.
   */
  public static final int DEFAULT_MAX_WIDTH = 500;

  /**
   * The default value for the {@link #maximumFastChoices} parameter.
   */
  public static final int DEFAULT_MAX_FAST_CHOICES = 10;

  
  /**
   * The maximum number of options for which the flow of buttons is used
   * instead of a combobox. By default this value is
   * {@link #DEFAULT_MAX_FAST_CHOICES}
   */
  private int maximumFastChoices;


  /**
   * Margin used for choice buttons. 
   */
  private Insets defaultButtonMargin;
  
  /**
   * The maximum width allowed for this component. This value is only
   * used when the component appears as a flow of buttons. By default
   * this value is {@link #DEFAULT_MAX_WIDTH}. This is used to force the flow 
   * layout do a multi-line layout, as by default it prefers to lay all 
   * components in a single very wide line.
   */
  private int maximumWidth;

  /**
   * The layout used by this container.
   */
  private FlowLayout layout;

  /**
   * The combobox used for a large number of choices. 
   */
  private JComboBox<E> combo;
  
  /**
   * Internal item listener for both the combo and the buttons, used to keep
   * the two in sync. 
   */
  private ItemListener sharedItemListener; 
  
  /**
   * The data model used for choices and selection.
   */
  private ComboBoxModel<E> model;
  
  /**
   * Keeps a mapping between the button and the corresponding option from the
   * model.
   */
  private Map<AbstractButton, Object> buttonToValueMap;
  
  
  /**
   * Creates a FastChoice with a default empty data model.
   */
  public JChoice() {
    this(new DefaultComboBoxModel<E>());
  }
  
  /**
   * A map from wrapped action listeners to listener
   */
  private Map<EventListener, ListenerWrapper> listenersMap;
  
  /**
   * Creates a FastChoice with the given data model.
   */
  public JChoice(ComboBoxModel<E> model) {
    layout = new FlowLayout();
    layout.setHgap(0);
    layout.setVgap(0);
    layout.setAlignment(FlowLayout.LEFT);
    setLayout(layout);
    this.model = model;
    //by default nothing is selected
    setSelectedItem(null);
    initLocalData();
    buildGui();
  }

  /**
   * Creates a FastChoice with a default data model populated from the provided
   * array of objects.
   */
  public JChoice(E[] items) {
    this(new DefaultComboBoxModel<E>(items));
  }
  
  
  /**
   * Initialises some local values.
   */
  private void initLocalData(){
    maximumFastChoices = DEFAULT_MAX_FAST_CHOICES;
    maximumWidth = DEFAULT_MAX_WIDTH;
    listenersMap = new HashMap<EventListener, ListenerWrapper>();
    combo = new JComboBox<E>(model);
    buttonToValueMap = new HashMap<AbstractButton, Object>();
    sharedItemListener = new ItemListener(){
      /**
       * Flag used to disable event handling while generating events. Used as an
       * exit mechanism from event handling loops. 
       */
      private boolean disabled = false;
      
      @Override
      public void itemStateChanged(ItemEvent e) {
        if(disabled) return;
        if(e.getSource() == combo){
          //event from the combo
          //disable event handling, to avoid unwanted cycles
          disabled = true;
          if(e.getStateChange() == ItemEvent.SELECTED){
            //update the state of all buttons
            for(AbstractButton aBtn : buttonToValueMap.keySet()){
              Object aValue = buttonToValueMap.get(aBtn);
              if(aValue.equals(e.getItem())){
                //this is the selected button
                if(!aBtn.isSelected()){
                  aBtn.setSelected(true);
                  aBtn.requestFocusInWindow();
                }
              }else{
                //this is a button that should not be selected
                if(aBtn.isSelected()) aBtn.setSelected(false);
              }
            }
          }else if(e.getStateChange() == ItemEvent.DESELECTED){
            //deselections due to other value being selected are handled
            //above.
            //here we only need to handle the case when the selection was
            //removed, but not replaced (i.e. setSelectedItem(null)
            for(AbstractButton aBtn : buttonToValueMap.keySet()){
              Object aValue = buttonToValueMap.get(aBtn);
              if(aValue.equals(e.getItem())){
                //this is the de-selected button
                if(aBtn.isSelected()) aBtn.setSelected(false);
              }
            }
          }
          //re-enable event handling
          disabled = false;
        }else if(e.getSource() instanceof AbstractButton){
          //event from the buttons
          if(buttonToValueMap.containsKey(e.getSource())){
            Object value = buttonToValueMap.get(e.getSource());
            if(e.getStateChange() == ItemEvent.SELECTED){
              model.setSelectedItem(value);
            }else if(e.getStateChange() == ItemEvent.DESELECTED){
              model.setSelectedItem(null);
            }
          }          
        }
      }      
    };
    combo.addItemListener(sharedItemListener);
  }
  
  public static void main(String[] args){
    final JChoice<String> fChoice = new JChoice<String>(new String[]{
            "Jan",
            "Feb",
            "Mar",
            "Apr",
            "May",
            "Jun",
            "Jul",
            "Aug",
            "Sep",
            "Oct",
            "Nov",
            "Dec"});
    fChoice.setMaximumFastChoices(20);
    fChoice.addActionListener(new ActionListener(){
      @Override
      public void actionPerformed(ActionEvent e) {
        System.out.println("Action (" + e.getActionCommand() + ") :" + fChoice.getSelectedItem() + " selected!");
      }
    });
    fChoice.addItemListener(new ItemListener(){
      @Override
      public void itemStateChanged(ItemEvent e) {
        System.out.println("Item " + e.getItem().toString() +
               (e.getStateChange() == ItemEvent.SELECTED ? " selected!" :
               " deselected!"));
      }
      
    });
    JFrame aFrame = new JFrame("Fast Chioce Test Frame");
    aFrame.getContentPane().add(fChoice);
    
    Box topBox = Box.createHorizontalBox();
    JButton aButn = new JButton("Clear");
    aButn.addActionListener(new ActionListener(){
      @Override
      public void actionPerformed(ActionEvent e) {
        System.out.println("Clearing");
        fChoice.setSelectedItem(null);
      }
    });
    topBox.add(Box.createHorizontalStrut(10));
    topBox.add(aButn);
    topBox.add(Box.createHorizontalStrut(10));
    topBox.add(new JToggleButton("GAGA"));
    
    aFrame.add(topBox, BorderLayout.NORTH);
    aFrame.pack();
    aFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    aFrame.setVisible(true);
  }
  
  /**
   * @param l
   * @see javax.swing.JComboBox#removeActionListener(java.awt.event.ActionListener)
   */
  public void removeActionListener(ActionListener l) {
    ListenerWrapper wrapper = listenersMap.remove(l);
    combo.removeActionListener(wrapper);
  }

  /**
   * @param listener
   * @see javax.swing.JComboBox#removeItemListener(java.awt.event.ItemListener)
   */
  @Override
  public void removeItemListener(ItemListener listener) {
    ListenerWrapper wrapper = listenersMap.remove(listener);
    combo.removeActionListener(wrapper);
  }

  /**
   * @param l
   * @see javax.swing.JComboBox#addActionListener(java.awt.event.ActionListener)
   */
  public void addActionListener(ActionListener l) {
    combo.addActionListener(new ListenerWrapper(l));
  }

  /**
   * @param listener
   * @see javax.swing.JComboBox#addItemListener(java.awt.event.ItemListener)
   */
  @Override
  public void addItemListener(ItemListener listener) {
    combo.addItemListener(new ListenerWrapper(listener));
  }

  /**
   * (Re)constructs the UI. This can be called many times, whenever a 
   * significant value (such as {@link #maximumFastChoices}, or the model)
   * has changed.
   */
  private void buildGui(){
    removeAll();
    if(model != null && model.getSize() > 0){
      if(model.getSize() > maximumFastChoices){
        //use combobox
        add(combo);
      }else{
        //use buttons
        //first clear the old buttons, if any exist
        if(buttonToValueMap.size() > 0){
          for(AbstractButton aBtn : buttonToValueMap.keySet()){
            aBtn.removeItemListener(sharedItemListener);
          }
        }
        //now create the new buttons
        buttonToValueMap.clear();
        for(int i = 0; i < model.getSize(); i++){
          Object aValue = model.getElementAt(i);
          JToggleButton aButton = new JToggleButton(aValue.toString());
          if(defaultButtonMargin != null) aButton.setMargin(defaultButtonMargin);
          aButton.addItemListener(sharedItemListener);
          buttonToValueMap.put(aButton, aValue);
          add(aButton);
        }
      }
    }
    revalidate();
  }
  
  
  /**
   * @param l
   * @see javax.swing.ListModel#addListDataListener(javax.swing.event.ListDataListener)
   */
  public void addListDataListener(ListDataListener l) {
    model.addListDataListener(l);
  }

  /**
   * @see javax.swing.ListModel#getElementAt(int)
   */
  public Object getElementAt(int index) {
    return model.getElementAt(index);
  }

  /**
   * @see javax.swing.ComboBoxModel#getSelectedItem()
   */
  public Object getSelectedItem() {
    return model.getSelectedItem();
  }

  /**
   * @see javax.swing.ListModel#getSize()
   */
  public int getItemCount() {
    return model.getSize();
  }

  /**
   * @param l
   * @see javax.swing.ListModel#removeListDataListener(javax.swing.event.ListDataListener)
   */
  public void removeListDataListener(ListDataListener l) {
    model.removeListDataListener(l);
  }

  /**
   * @param anItem
   * @see javax.swing.ComboBoxModel#setSelectedItem(java.lang.Object)
   */
  public void setSelectedItem(Object anItem) {
    model.setSelectedItem(anItem);
  }

  /*
   * (non-Javadoc)
   * 
   * @see javax.swing.JComponent#getPreferredSize()
   */
  @Override
  public Dimension getPreferredSize() {
    Dimension size = super.getPreferredSize();
    if(getItemCount() <= maximumFastChoices && size.width > maximumWidth) {
      setSize(maximumWidth, Integer.MAX_VALUE);
      doLayout();
      int compCnt = getComponentCount();
      if(compCnt > 0) {
        Component lastComp = getComponent(compCnt - 1);
        Point compLoc = lastComp.getLocation();
        Dimension compSize = lastComp.getSize();
        size.width = maximumWidth;
        size.height = compLoc.y + compSize.height + getInsets().bottom;
      }
    }
    return size;
  }
  

  /**
   * @return the maximumFastChoices
   */
  public int getMaximumFastChoices() {
    return maximumFastChoices;
  }

  /**
   * @param maximumFastChoices the maximumFastChoices to set
   */
  public void setMaximumFastChoices(int maximumFastChoices) {
    this.maximumFastChoices = maximumFastChoices;
    buildGui();
  }

  
  /**
   * @return the model
   */
  public ComboBoxModel<E> getModel() {
    return model;
  }

  /**
   * @param model the model to set
   */
  public void setModel(ComboBoxModel<E> model) {
    this.model = model;
    combo.setModel(model);
    buildGui();
  }

  /**
   * @return the maximumWidth
   */
  public int getMaximumWidth() {
    return maximumWidth;
  }

  /**
   * @param maximumWidth the maximumWidth to set
   */
  public void setMaximumWidth(int maximumWidth) {
    this.maximumWidth = maximumWidth;
  }
  
  /**
   * An action listener that changes the source of events to be this object.
   */
  private class ListenerWrapper implements ActionListener, ItemListener{
    public ListenerWrapper(EventListener originalListener) {
      this.originalListener = originalListener;
      listenersMap.put(originalListener, this);
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
      //generate a new event with this as source
      ((ItemListener)originalListener).itemStateChanged(
              new ItemEvent(JChoice.this, e.getID(), e.getItem(), 
                      e.getStateChange()));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      //generate a new event
      ((ActionListener)originalListener).actionPerformed(new ActionEvent(
              JChoice.this, e.getID(), e.getActionCommand(), e.getWhen(), 
              e.getModifiers()));
    }
    private EventListener originalListener;
  }

  /**
   * @return the defaultButtonMargin
   */
  public Insets getDefaultButtonMargin() {
    return defaultButtonMargin;
  }

  /**
   * @param defaultButtonMargin the defaultButtonMargin to set
   */
  public void setDefaultButtonMargin(Insets defaultButtonMargin) {
    this.defaultButtonMargin = defaultButtonMargin;
    buildGui();
  }
}
