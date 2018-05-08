package gate.swing;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Window;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import gate.Gate;
import gate.creole.Plugin;
import gate.creole.Plugin.Maven;
import gate.event.PluginListener;
import gate.resources.img.svg.GATEIcon;
import gate.resources.img.svg.InvalidIcon;
import gate.util.ExtensionFileFilter;

@SuppressWarnings("serial")
public class ResourceReferenceChooser implements PluginListener, TreeWillExpandListener {

  private static final Logger log = Logger.getLogger(ResourceReferenceChooser.class);


  private static final int ERROR = -1;

  private static final int CANCEL = 0;

  private static final int APPROVE_FILE = 1;

  private static final int APPROVE_RESOURCE = 2;

  private final InvalidIcon invalidIcon = new InvalidIcon(16, 16);

  private XJFileChooser fileChooser;

  private JOptionPane pluginChooser;

  private JLabel pluginChooserStatus;

  private JComboBox<PluginFileFilter> pluginFileFilterBox;

  private JTree pluginTree;

  private DefaultTreeModel pluginTreeModel;

  private DefaultMutableTreeNode treeRoot;

  private JTabbedPane tabPane;

  /** set to true when setSelectedResource is called with a creole: URI */
  private boolean isResourceSelected = false;

  // only valid during a call to showDialog
  private int returnValue = ERROR;

  // only valid during a call to showDialog
  private JDialog dialog;

  public ResourceReferenceChooser() {
    this.fileChooser = new XJFileChooser();

    tabPane = new JTabbedPane();
    
    JPanel panel = new JPanel(new BorderLayout());
    panel.add(fileChooser, BorderLayout.CENTER);
    
    tabPane.addTab("File", panel);
    fileChooser.addActionListener((e) -> {
      if(JFileChooser.APPROVE_SELECTION.equals(e.getActionCommand())) {
        returnValue = APPROVE_FILE;
      } else {
        returnValue = CANCEL;
      }
      if(dialog != null) {
        dialog.setVisible(false);
      }
    });

    tabPane.addHierarchyListener(new HierarchyListener() {
      @Override
      public void hierarchyChanged(HierarchyEvent e) {
        if((e.getChangeFlags() & HierarchyEvent.DISPLAYABILITY_CHANGED) != 0 && !tabPane.isDisplayable()) {
          // reinitialise fields when the file chooser is hidden
          isResourceSelected = false;
          setSuffixes(null);
        }
      }
    });


    JPanel pluginPanel = new JPanel(new BorderLayout());
    pluginChooserStatus = new JLabel("Select a resource");
    pluginChooserStatus.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    pluginPanel.add(pluginChooserStatus, BorderLayout.NORTH);

    pluginFileFilterBox = new JComboBox<>();
    pluginFileFilterBox.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    Box fileTypeBox = Box.createHorizontalBox();
    fileTypeBox.add(Box.createHorizontalGlue());
    JLabel ffLabel = new JLabel("File format:");
    ffLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    fileTypeBox.add(ffLabel);
    fileTypeBox.add(pluginFileFilterBox);
    fileTypeBox.add(Box.createHorizontalGlue());
    pluginPanel.add(fileTypeBox, BorderLayout.SOUTH);
    pluginFileFilterBox.addActionListener((e) -> {
      // trick to force the tree to recalculate label widths
      //pluginTree.setRowHeight(pluginTree.getRowHeight());
      pluginTree.repaint();
    });

    treeRoot = new DefaultMutableTreeNode();
    pluginTreeModel = new DefaultTreeModel(treeRoot);
    pluginTree = new JTree(pluginTreeModel);
    pluginTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
    pluginTree.setRootVisible(false);
    pluginTree.setShowsRootHandles(true);
    pluginTree.setCellRenderer(new Renderer());
    JScrollPane treeScroller = new JScrollPane(pluginTree);
    pluginPanel.add(treeScroller, BorderLayout.CENTER);
    pluginChooser = new JOptionPane(pluginPanel, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
    pluginChooser.addPropertyChangeListener(JOptionPane.VALUE_PROPERTY, (e) -> {
      if(dialog != null && e.getNewValue() instanceof Integer) {
        if(((Integer)e.getNewValue()) == JOptionPane.OK_OPTION) {
          if(pluginTree.getSelectionCount() > 0) {
            try {
              String selected = ((AsUri)pluginTree.getSelectionPath().getLastPathComponent()).getAsURI();
              if(((PluginFileFilter)pluginFileFilterBox.getSelectedItem()).pattern.matcher(selected).find()) {
                returnValue = APPROVE_RESOURCE;
                dialog.setVisible(false);
              } else {
                pluginChooser.setValue(JOptionPane.UNINITIALIZED_VALUE);
                pluginChooserStatus.setIcon(invalidIcon);
                pluginChooserStatus.setText("Selected resource does not have the right suffix");
              }
            } catch(URISyntaxException ex) {
              // shouldn't happen
              pluginChooser.setValue(JOptionPane.UNINITIALIZED_VALUE);
              pluginChooserStatus.setIcon(invalidIcon);
              pluginChooserStatus.setText("Error extracting selected resource from tree");
            }
          } else {
            pluginChooser.setValue(JOptionPane.UNINITIALIZED_VALUE);
            pluginChooserStatus.setIcon(invalidIcon);
            pluginChooserStatus.setText("Nothing selected - please select a resource");
          }
        } else {
          returnValue = CANCEL;
          dialog.setVisible(false);
        }
      }
    });
    tabPane.addTab("Plugin resource", pluginChooser);
    refreshTreeForPlugins();
    pluginTree.expandPath(new TreePath(treeRoot));
    pluginTree.addTreeWillExpandListener(this);

    // handle double click on leaf node as equivalent to clicking OK
    pluginTree.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        if(e.getClickCount() == 2) {
          TreePath selPath = pluginTree.getPathForLocation(e.getX(), e.getY());
          if(((DefaultMutableTreeNode)selPath.getLastPathComponent()).isLeaf()) {
            pluginChooser.setValue(JOptionPane.OK_OPTION);
          }
        }
      }
    });

    Gate.getCreoleRegister().addPluginListener(this);
    // trigger initial populate of the file type drop downs
    setSuffixes(null);
  }

  @SuppressWarnings("unchecked")
  private void refreshTreeForPlugins() {
    List<Plugin.Maven> mavenPluginsWithResources = Gate.getCreoleRegister().getPlugins().stream()
            .filter(p -> (p instanceof Plugin.Maven) && p.hasResources())
            .map(p -> (Plugin.Maven)p)
            .sorted(PLUGIN_COMPARATOR).collect(Collectors.toList());

    // first, remove any nodes for plugins that are no longer loaded
    Enumeration<PluginTreeNode> nodes = treeRoot.children();
    Iterator<Plugin.Maven> listIt = mavenPluginsWithResources.iterator();
    Plugin.Maven curPlug = listIt.hasNext() ? listIt.next() : null;
    List<Integer> indicesToRemove = new ArrayList<>(treeRoot.getChildCount());
    List<PluginTreeNode> nodesToRemove = new ArrayList<>(treeRoot.getChildCount());
    int i = 0;
    while(nodes.hasMoreElements()) {
      PluginTreeNode n = nodes.nextElement();
      Plugin.Maven nodePlugin = (Plugin.Maven)n.getUserObject();
      int cmp = -1;
      // advance listIt until we find a curPlug that is >= nodePlugin
      while(curPlug != null && (cmp = PLUGIN_COMPARATOR.compare(nodePlugin, curPlug)) > 0) {
        curPlug = listIt.hasNext() ? listIt.next() : null;
      }
      if(cmp < 0) {
        // we haven't found a plugin in listIt that is == nodePlugin, so node needs to be removed
        indicesToRemove.add(i);
        nodesToRemove.add(n);
      }
      i++;
    }

    if(indicesToRemove.size() > 0) {
      // remove the dead nodes, in reverse order
      ListIterator<Integer> indicesIt = indicesToRemove.listIterator(indicesToRemove.size());
      while(indicesIt.hasPrevious()) {
        treeRoot.remove(indicesIt.previous());
      }
      // and notify the model
      pluginTreeModel.nodesWereRemoved(treeRoot,
              indicesToRemove.stream().mapToInt(j -> j).toArray(),
              nodesToRemove.toArray());
    }

    // now we need to add new nodes for plugins that have been loaded since last time we checked
    nodes = treeRoot.children();
    listIt = mavenPluginsWithResources.iterator();
    PluginTreeNode curNode = nodes.hasMoreElements() ? nodes.nextElement() : null;
    List<Integer> indicesToAdd = new ArrayList<>(mavenPluginsWithResources.size());
    List<PluginTreeNode> nodesToAdd = new ArrayList<>(mavenPluginsWithResources.size());
    i = 0;
    while(listIt.hasNext()) {
      Plugin.Maven p = listIt.next();
      int cmp = -1;
      while(curNode != null && (cmp = PLUGIN_COMPARATOR.compare(p, (Plugin.Maven)curNode.getUserObject())) > 0) {
        curNode = nodes.hasMoreElements() ? nodes.nextElement() : null;
        i++;
      }
      if(cmp < 0) {
        // we haven't found a node whose plugin is == p, so add a new node for p here
        indicesToAdd.add(i);
        nodesToAdd.add(new PluginTreeNode(p, 0));
        i++;
      }
    }

    if(indicesToAdd.size() > 0) {
      // add the new nodes
      Iterator<Integer> indicesIt = indicesToAdd.iterator();
      Iterator<PluginTreeNode> nodesIt = nodesToAdd.iterator();
      while(indicesIt.hasNext()) {
        treeRoot.insert(nodesIt.next(), indicesIt.next());
      }

      pluginTreeModel.nodesWereInserted(treeRoot, indicesToAdd.stream().mapToInt(j -> j).toArray());
    }

    // now disambiguate cases where there are two plugins with the same name.
    // Keep iterating over the plugin nodes grouping them by their toString().
    // If there are two or more nodes with the same toString() (which initially
    // means plugins with the same name) then increase their nameDepth and try
    // the grouping again.  Eventually we will reach complete disambiguation,
    // in the worst case all the plugins will be listed as full g:a:v triples.
    for(int d = 0; d < 3; d++) {
      Map<String, List<PluginTreeNode>> groupByDesc = new HashMap<>();
      nodes = treeRoot.children();
      while(nodes.hasMoreElements()) {
        PluginTreeNode n = nodes.nextElement();
        groupByDesc.computeIfAbsent(n.toString(), (k) -> new ArrayList<>()).add(n);
      }
      boolean anyDups = false;
      for(Map.Entry<String, List<PluginTreeNode>> entry : groupByDesc.entrySet()) {
        if(entry.getValue().size() > 1) {
          anyDups = true;
          for(PluginTreeNode groupNode : entry.getValue()) {
            groupNode.nameDepth++;
          }
        }
      }
    }
  }

  public void setResource(String resource) {
    fileChooser.setResource(resource);
  }

  public String getResource() {
    return fileChooser.getResource();
  }

  @SuppressWarnings("unchecked")
  public void setSelectedResource(String uriStr) {
    try {
      URI uri = new URI(uriStr);
      if("creole".equals(uri.getScheme())) {
        String path = uri.getPath();
        URI pluginBaseUri = uri.resolve("/");
        // find the plugin node matching this base URI
        PluginTreeNode pluginNode = null;
        Enumeration<PluginTreeNode> pluginNodes = treeRoot.children();
        while(pluginNodes.hasMoreElements()) {
          PluginTreeNode n = pluginNodes.nextElement();
          URI bu = ((Plugin.Maven) n.getUserObject()).getBaseURI();
          if(bu.equals(pluginBaseUri)) {
            pluginNode = n;
            break;
          }
        }

        if(pluginNode != null) {
          // this expand will trigger an enumerate of the plugin resources if necessary
          pluginTree.expandPath(new TreePath(new Object[] {treeRoot, pluginNode}));
          DefaultMutableTreeNode nearestNode = pluginNode;
          LinkedList<PluginResourceTreeNode> nodesToTry = new LinkedList<>();
          Enumeration<PluginResourceTreeNode> children = pluginNode.children();
          while(children.hasMoreElements()) {
            nodesToTry.add(children.nextElement());
          }
          ListIterator<PluginResourceTreeNode> iter = nodesToTry.listIterator();
          while(iter.hasNext()) {
            PluginResourceTreeNode n = iter.next();
            // if we might be able to go deeper here, add this node's children
            // after the current iterator cursor position
            if(path.startsWith(n.getFullPath())) {
              nearestNode = n;
              int stepsBack = 0;
              children = n.children();
              while(children.hasMoreElements()) {
                iter.add(children.nextElement());
                stepsBack++;
              }
              for(int i = 0; i < stepsBack; i++) {
                iter.previous();
              }
            }
          }

          // nearestNode is now either the entry itself or its nearest ancestor if not present
          // (e.g. if the URI is to /foo/bar/baz.txt and there isn't one of those but there is
          // a /foo/bar/ then nearestNode will point to that).  In the worst case it'll be the
          // root of the same plugin.

          TreeNode[] pathToRoot = nearestNode.getPath();
          // collapse siblings at each level except the very top
          for(int i = 1; i < pathToRoot.length; i++) {
            DefaultMutableTreeNode n = (DefaultMutableTreeNode)pathToRoot[i];
            Enumeration<DefaultMutableTreeNode> siblings = n.getParent().children();
            while(siblings.hasMoreElements()) {
              DefaultMutableTreeNode sib = siblings.nextElement();
              if(sib != n) {
                pluginTree.collapsePath(new TreePath(sib.getPath()));
              }
            }
          }

          // expand parent
          pluginTree.expandPath(new TreePath(Arrays.copyOf(pathToRoot, pathToRoot.length-1)));

          // and select me
          TreePath selPath = new TreePath(pathToRoot);
          pluginTree.setSelectionPath(selPath);
          pluginTree.scrollPathToVisible(selPath);

          // and make sure the plugin tree tab is selected
          tabPane.setSelectedIndex(1);
          isResourceSelected = true;
        }
      } else if("file".equals(uri.getScheme())) {
        fileChooser.setSelectedFile(new File(uri));
        tabPane.setSelectedIndex(0);
      }
    } catch(URISyntaxException e) {
      // oh well, we tried
    }
  }

  public void setSuffixes(Collection<String> suffixes) {
    setSuffixes((suffixes == null ? null : "Known file types " + suffixes.toString()), suffixes);
  }

  public void setSuffixes(String description, Collection<String> suffixes) {
    fileChooser.resetChoosableFileFilters();
    fileChooser.setAcceptAllFileFilterUsed(true);
    fileChooser.setFileFilter(fileChooser.getAcceptAllFileFilter());

    pluginFileFilterBox.removeAllItems();
    PluginFileFilter allFilter = new PluginFileFilter();
    allFilter.pattern = Pattern.compile("");
    allFilter.description = "All files";
    pluginFileFilterBox.addItem(allFilter);
    pluginFileFilterBox.setSelectedItem(allFilter);

    if(suffixes != null && !suffixes.isEmpty()) {
      ExtensionFileFilter fileFilter = new ExtensionFileFilter();
      suffixes.forEach((suf) -> fileFilter.addExtension(suf));
      fileFilter.setDescription(description);
      fileChooser.addChoosableFileFilter(fileFilter);
      fileChooser.setFileFilter(fileFilter);

      PluginFileFilter suffixFilter = new PluginFileFilter();
      suffixFilter.description = description;
      suffixFilter.pattern = Pattern.compile("(?:" +
              suffixes.stream().map((suf) -> Pattern.quote(suf)).collect(Collectors.joining("|"))
              + ")$");
      pluginFileFilterBox.addItem(suffixFilter);
      pluginFileFilterBox.setSelectedItem(suffixFilter);
    }
  }

  @SuppressWarnings("unchecked")
  public String showDialog(Window parent, String title) {
    if(dialog != null) {
      throw new IllegalStateException("ResourceReferenceChooser dialog already showing");
    }
    fileChooser.setSelectedFileFromPreferences();
    if(!isResourceSelected) {
      // nothing was selected, so prefer file chooser tab
      tabPane.setSelectedIndex(0);

      String resourceClass = getResource();
      if(resourceClass != null) {
        // attempt to find a plugin that defines this resource
        Enumeration<PluginTreeNode> nodes = treeRoot.children();
        while(nodes.hasMoreElements()) {
          PluginTreeNode node = nodes.nextElement();
          Plugin.Maven plugin = (Plugin.Maven)node.getUserObject();
          if(plugin.getResourceInfoList().stream().anyMatch(ri -> resourceClass.equals(ri.getResourceClassName()))) {
            TreePath path = new TreePath(node.getPath());
            pluginTree.setSelectionPath(path);
            pluginTree.expandPath(path);
            pluginTree.scrollPathToVisible(path);
            break;
          } else {
            // collapse everything else
            Enumeration<DefaultMutableTreeNode> descendants = node.postorderEnumeration();
            while(descendants.hasMoreElements()) {
              pluginTree.collapsePath(new TreePath(descendants.nextElement().getPath()));
            }
          }
        }
      }
    }

    dialog = new JDialog(parent, title, Dialog.DEFAULT_MODALITY_TYPE);
    try {
      pluginChooser.setValue(JOptionPane.UNINITIALIZED_VALUE);
      pluginChooserStatus.setIcon(null);
      pluginChooserStatus.setText("Select a resource");
      dialog.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
      dialog.addWindowListener(new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent e) {
          returnValue = CANCEL;
        }
      });
      returnValue = ERROR;
      dialog.getContentPane().setLayout(new BorderLayout());
      dialog.getContentPane().add(tabPane, BorderLayout.CENTER);
      dialog.pack();
      dialog.setLocationRelativeTo(parent);
      dialog.setVisible(true); // blocks until window closed

      if(returnValue == APPROVE_RESOURCE) {
        TreePath selected = pluginTree.getSelectionPath();
        if(selected== null) {
          return null;
        } else {
          return ((AsUri) selected.getLastPathComponent()).getAsURI();
        }
      } else if(returnValue == APPROVE_FILE) {
        return fileChooser.getSelectedFile().toURI().toString();
      } else {
        // cancel
        return null;
      }
    } catch(URISyntaxException ex) {
      // shouldn't happen
      throw new RuntimeException(ex);
    } finally {
      dialog.getContentPane().removeAll();
      dialog.dispose();
      dialog = null;
    }
  }



  private static Comparator<Plugin.Maven> PLUGIN_COMPARATOR =
          Comparator.comparing(Maven::getName)
                  .thenComparing(Maven::getGroup)
                  .thenComparing(Maven::getArtifact)
                  .thenComparing(Maven::getVersion);

  @Override
  public void pluginLoaded(URL url) {
    SwingUtilities.invokeLater(this::refreshTreeForPlugins);
  }

  @Override
  public void pluginUnloaded(URL url) {
    SwingUtilities.invokeLater(this::refreshTreeForPlugins);
  }

  @Override
  public void treeWillExpand(TreeExpansionEvent event) {
    DefaultMutableTreeNode expandingNode = (DefaultMutableTreeNode)event.getPath().getLastPathComponent();
    if(expandingNode instanceof PluginTreeNode) {
      try {
        ((PluginTreeNode)expandingNode).expanding();
      } catch(IOException | URISyntaxException e) {
        log.warn("Error processing resources for plugin " + expandingNode.getUserObject(), e);
      }
    }
  }

  @Override
  public void treeWillCollapse(TreeExpansionEvent event) {
  }

  static interface AsUri {
    String getAsURI() throws URISyntaxException;
  }
  static class PluginTreeNode extends DefaultMutableTreeNode implements AsUri {
    int nameDepth = 0;
    boolean hasExpanded = false;

    public PluginTreeNode(Plugin.Maven plugin, int nameDepth) {
      super(plugin, true);
      this.nameDepth = nameDepth;
    }

    public String getAsURI() throws URISyntaxException {
      return ((Plugin)getUserObject()).getBaseURI().toString();
    }

    public String toString() {
      Plugin.Maven p = (Plugin.Maven)getUserObject();
      if(nameDepth == 0) {
        return p.getName();
      } else if(nameDepth == 1) {
        return p.getName() + " (" + p.getVersion() + ")";
      } else if(nameDepth == 2) {
        return p.getName() + " (" + p.getArtifact() + ":" + p.getVersion() + ")";
      } else {
        return p.getName() + " (" + p.getGroup() + ":" + p.getArtifact() + ":" + p.getVersion() + ")";
      }
    }

    public boolean isLeaf() {
      return false;
    }

    void expanding() throws IOException, URISyntaxException {
      if(hasExpanded) return;
      hasExpanded = true;

      final Plugin.Maven thisPlugin = (Plugin.Maven)getUserObject();
      final LinkedList<DefaultMutableTreeNode> nodeStack = new LinkedList<>();
      final LinkedList<List<PluginResourceTreeNode>> childLists = new LinkedList<>();
      nodeStack.addLast(this);
      childLists.addLast(new ArrayList<>());
      thisPlugin.walkResources(new SimpleFileVisitor<Path>() {
        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
          if(dir.getNameCount() > 1) { // no node for the resources dir itself
            String sep = dir.getFileSystem().getSeparator();
            String name = dir.getFileName().toString();
            if(name.endsWith(sep)) {
              name = name.substring(0, name.length() - sep.length());
            }
            PluginResourceTreeNode dirNode = new PluginResourceTreeNode(thisPlugin, dir.toString(), name);
            childLists.getLast().add(dirNode); // add this dir to parent list
            childLists.addLast(new ArrayList<>()); // and start a new list for us
            nodeStack.addLast(dirNode);
          }
          return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
          PluginResourceTreeNode fileNode = new PluginResourceTreeNode(thisPlugin, file.toString(), file.getFileName().toString());
          childLists.getLast().add(fileNode);
          return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
          List<PluginResourceTreeNode> children = childLists.removeLast();
          DefaultMutableTreeNode thisDirNode = nodeStack.removeLast();
          Collections.sort(children);
          for(DefaultMutableTreeNode node : children) {
            thisDirNode.add(node);
          }
          return FileVisitResult.CONTINUE;
        }
      });
    }
  }

  static class PluginResourceTreeNode extends DefaultMutableTreeNode implements Comparable<PluginResourceTreeNode>, AsUri {
    private Plugin.Maven plugin;
    private String fullPath;

    PluginResourceTreeNode(Plugin.Maven plugin, String fullPath, String myName) {
      super(myName);
      this.plugin = plugin;
      this.fullPath = fullPath;
    }

    public String getFullPath() {
      return fullPath;
    }

    public String getAsURI() throws URISyntaxException {
      URI pluginBase = plugin.getBaseURI();
      return new URI(pluginBase.getScheme(), pluginBase.getAuthority(), fullPath, null, null).toString();
    }

    @Override
    public int compareTo(PluginResourceTreeNode other) {
      return ((String)getUserObject()).compareToIgnoreCase((String)other.getUserObject());
    }
  }

  class Renderer extends DefaultTreeCellRenderer {
    private Icon pluginIcon;

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
      JLabel renderer = (JLabel)super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
      if(value instanceof PluginTreeNode) {
        renderer.setEnabled(true);
        renderer.setIcon(pluginIcon);
      } else if(value instanceof PluginResourceTreeNode) {
        PluginResourceTreeNode node = (PluginResourceTreeNode)value;
        if(node.fullPath.endsWith("/") || ((PluginFileFilter)pluginFileFilterBox.getSelectedItem()).pattern.matcher(node.fullPath).find()) {
          renderer.setEnabled(true);
        } else {
          // trick lifted from DefaultTreeCellRenderer to get the right disabled icon
          Icon icon = renderer.getIcon();
          LookAndFeel laf = UIManager.getLookAndFeel();
          Icon disabledIcon = laf.getDisabledIcon(tree, icon);
          if (disabledIcon != null) icon = disabledIcon;
          renderer.setDisabledIcon(icon);
          renderer.setEnabled(false);
        }
      }
      return renderer;
    }

    @Override
    public void updateUI() {
      super.updateUI();
      Icon defaultIcon = getDefaultClosedIcon();
      // create plugin icon at the same size as the default closed folder icon from the L&F
      pluginIcon = new GATEIcon(defaultIcon.getIconWidth(), defaultIcon.getIconHeight());
    }
  }

  static class PluginFileFilter {
    Pattern pattern;

    String description;

    public String toString() {
      return description;
    }
  }

  public static void main(String... args) throws Exception {
    BasicConfigurator.configure();
    Logger.getLogger("org.apache.http").setLevel(Level.ERROR);
    Gate.runInSandbox(false);
    Gate.init();
    SwingUtilities.invokeLater(() -> {
      ResourceReferenceChooser chooser = new ResourceReferenceChooser();
      chooser.setSuffixes(Arrays.asList(".def", ".jape"));
      System.out.println(chooser.showDialog(null, "Select a resource"));
    });
  }
}
