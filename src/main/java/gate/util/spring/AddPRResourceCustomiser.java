package gate.util.spring;

import gate.Controller;
import gate.ProcessingResource;
import gate.Resource;
import gate.creole.SerialController;

/**
 * Resource customiser that customises a {@link SerialController} by
 * adding an extra PR. By default the PR is added to the end of the
 * controller's PR list, but can optionally be added at a specific
 * index, or before or after another named PR.
 */
public class AddPRResourceCustomiser implements ResourceCustomiser {

  private int index = -1;

  private String addBefore = null;

  private String addAfter = null;

  private ProcessingResource pr;

  @Override
  public void customiseResource(Resource res) throws Exception {
    if(!(res instanceof SerialController)) {
      throw new IllegalArgumentException(this.getClass().getName()
              + " can only customise serial controllers");
    }

    SerialController c = (SerialController)res;

    int indexToAdd = index;
    if(indexToAdd < 0) {
      if(addBefore != null) {
        if(addAfter != null) {
          throw new IllegalArgumentException(
                  "Use either addBefore or addAfter, but not both");
        }
        else {
          indexToAdd = findPR(addBefore, c);
        }
      }
      else {
        if(addAfter != null) {
          indexToAdd = findPR(addAfter, c) + 1;
        }
      }
    }

    if(indexToAdd >= 0) {
      c.add(indexToAdd, pr);
    }
    else {
      c.add(pr);
    }
  }

  /**
   * Find the index of the first PR with the given name in the
   * controller.
   * 
   * @param name the PR name to search for
   * @param c the controller
   * @return the index of the first PR with this name in the controller,
   *         or -1 if no such PR exists.
   */
  private int findPR(String name, Controller c) {
    int i = 0;
    for(Object pr : c.getPRs()) {
      if(name.equals(((ProcessingResource)pr).getName())) {
        return i;
      }
      i++;
    }

    return -1;
  }

  public void setIndex(int index) {
    this.index = index;
  }

  public void setAddBefore(String addBefore) {
    this.addBefore = addBefore;
  }

  public void setAddAfter(String addAfter) {
    this.addAfter = addAfter;
  }

  public void setPr(ProcessingResource pr) {
    this.pr = pr;
  }

}
