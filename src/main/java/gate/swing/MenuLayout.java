/*
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Valentin Tablan 20 Feb 2003
 *
 *  $Id: MenuLayout.java 17530 2014-03-04 15:57:43Z markagreenwood $
 */

package gate.swing;

import java.awt.*;
import java.util.Arrays;
import java.util.ArrayList;

import javax.swing.JPopupMenu;


/**
 * A layout designed to allow Java menus to make better use of the screen
 * real-estate. It will lay out the menu components in columns going from top
 * to bottom and from left to right.
 */
public class MenuLayout implements LayoutManager {

  /**
   * Adds the specified component to the layout. Not used by this class.
   * @param name the name of the component
   * @param comp the the component to be added
   */
  @Override
  public void addLayoutComponent(String name, Component comp) {}

  /**
   * Removes the specified component from the layout. Not used by this class.
   * @param comp the component to remove
   */
  @Override
  public void removeLayoutComponent(Component comp) {}

  /**
   * Returns the preferred dimensions for this layout given the components
   * in the specified target container.
   * @param target the component which needs to be laid out
   * @see Container
   * @see #minimumLayoutSize
   */
  @Override
  public Dimension preferredLayoutSize(Container target) {
    int membersCnt = target.getComponentCount();
    Dimension[] componentPrefSizes = new Dimension[membersCnt];
    //store the sizes
    for(int i = 0; i < membersCnt; i++){
      componentPrefSizes[i] = target.getComponent(i).getPreferredSize();
    }
    return getCompositeSize(target, componentPrefSizes);
  }

  /**
   * Calculates the size of the target container given the sizes of the
   * components.
   * If the doLayout is <b>true</b> it will also lay out the container.
   * Used by {@link #minimumLayoutSize} and {@link #preferredLayoutSize}.
   * @param target  the component which needs to be laid out
   * @param componentSizes array of dimensions for each menu component
   * @return a {@link Dimension} value.
   */
  protected Dimension getCompositeSize(Container target,
                                       Dimension[] componentSizes){
    //find the origin of the popup
    Point location = new Point(0, 0);
    if(target.isShowing()){
      location = target.getLocationOnScreen();
    }else{
      if(target instanceof JPopupMenu){
        Component invoker = ((JPopupMenu)target).getInvoker();
        if(invoker != null) location = invoker.getLocationOnScreen();
      }
    }

    //find the maximum size
    Toolkit toolkit = Toolkit.getDefaultToolkit();
    Rectangle contentsBounds = new Rectangle(toolkit.getScreenSize());
    Insets screenInsets = new Insets(0, 0, 0, 0);
    GraphicsConfiguration gc = findGraphicsConfiguration(target);
    if (gc != null) {
      contentsBounds = gc.getBounds();
      screenInsets = toolkit.getScreenInsets(gc);
    }

    // take screen insets (e.g. taskbar) into account
    contentsBounds.width -= screenInsets.left + screenInsets.right;
    contentsBounds.height -= screenInsets.top + screenInsets.bottom;
    //take the location into account assuming that the largest side will be used
    contentsBounds.height = Math.max(location.y,
                                     contentsBounds.height - location.y);

    // take component insets into account
    Insets insets = target.getInsets();
    contentsBounds.width -= insets.left + insets.right;
    contentsBounds.height -= insets.top + insets.bottom;
    Dimension dim = new Dimension(0, 0);
    int previousColumnsWidth = 0;
    int previousColumnsHeight = 0;
    int columnIndex = 1;
    int firstComponentIndexForColumn = 0;
    columnForComponentIndex = new int[componentSizes.length];
    preferredWidthForColumn = new ArrayList<Integer>();
    for (int i = 0; i < componentSizes.length; i++) {
      if ( (dim.height +
            componentSizes[i].height) <= contentsBounds.height) {
        //we can fit the current component in the current row
        dim.height += componentSizes[i].height;
        dim.width = Math.max(dim.width, componentSizes[i].width);
      }
      else {
        //we need to start a new column
        Arrays.fill(columnForComponentIndex, firstComponentIndexForColumn,
          i, columnIndex);
        preferredWidthForColumn.add(dim.width);
        firstComponentIndexForColumn = i;
        columnIndex++;
        previousColumnsWidth += dim.width;
        previousColumnsHeight = Math.max(previousColumnsHeight, dim.height);
        dim.height = componentSizes[i].height;
        dim.width = componentSizes[i].width;
      }
    }

    Arrays.fill(columnForComponentIndex, firstComponentIndexForColumn,
      columnForComponentIndex.length, columnIndex);
    preferredWidthForColumn.add(dim.width);
    //Now dim contains the sizes for the last column
    dim.height = Math.max(previousColumnsHeight, dim.height);
    dim.width += previousColumnsWidth;
    //add the target insets
    dim.width += insets.left + insets.right;
    dim.height += insets.top + insets.bottom;
    return dim;
  }

  /**
   * Find the graphics configuration for the target popup (useful in case of
   * multiple screens).
   * @param target the component for which the configuration needs to be found.
   * @return a GraphicsConfiguration value.
   */
  protected GraphicsConfiguration findGraphicsConfiguration(Component target){
    GraphicsConfiguration gc = null;
    if(!target.isShowing()){
      if(target instanceof JPopupMenu){
        Component invoker = ((JPopupMenu)target).getInvoker();
        if(invoker != null) target = invoker;
      }
    }
    if(target.isShowing()){
      Point position = target.getLocationOnScreen();
      gc = target.getGraphicsConfiguration();
      GraphicsEnvironment ge =
        GraphicsEnvironment.getLocalGraphicsEnvironment();
      for (GraphicsDevice gd : ge.getScreenDevices()) {
        if (gd.getType() == GraphicsDevice.TYPE_RASTER_SCREEN) {
          GraphicsConfiguration dgc = gd.getDefaultConfiguration();
          if (dgc.getBounds().contains(position)) {
            gc = dgc;
            break;
          }
        }
      }
    }
    return gc;
  }

  /**
   * Returns the minimum dimensions needed to layout the components
   * contained in the specified target container.
   * @param target the component which needs to be laid out
   * @see #preferredLayoutSize
   */
  @Override
  public Dimension minimumLayoutSize(Container target) {
    int membersCnt = target.getComponentCount();
    Dimension[] componentMinSizes = new Dimension[membersCnt];
    //store the sizes
    for(int i = 0; i < membersCnt; i++){
      componentMinSizes[i] = target.getComponent(i).getMinimumSize();
    }
    return getCompositeSize(target, componentMinSizes);
  }


  @Override
  public void layoutContainer(Container target) {
    Insets insets = target.getInsets();
    Rectangle bounds = target.getBounds();
    int maxheight = bounds.height - insets.bottom;
    int compCnt = target.getComponentCount();
    int y = insets.top;
    int x = insets.left;
    int rowWidth = 0;

    for (int i = 0; i < compCnt; i++) {
      Component comp = target.getComponent(i);
      if (comp.isVisible()) {
        Dimension d = comp.getPreferredSize();
        comp.setSize(d);
        if (y + d.height <= maxheight) {
          comp.setLocation(x, y);
          y += d.height;
          rowWidth = Math.max(rowWidth, d.width);
        }
        else {
          //we need to start a new column
          x += rowWidth;
          rowWidth = 0;
          y = insets.top;
          comp.setLocation(x, y);
          y += d.height;
          rowWidth = Math.max(rowWidth, d.width);
        }
      }
    }
  }

  public int getColumnForComponentIndex(int index) {
    return columnForComponentIndex[index];
  }

  public int getPreferredWidthForColumn(int index) {
    return preferredWidthForColumn.get(index-1);
  }

  private int[] columnForComponentIndex;
  private ArrayList<Integer> preferredWidthForColumn;
}
