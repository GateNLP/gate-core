/*
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Ian Roberts 14/02/2009
 *
 *  $Id: TeamwareUtils.java 17618 2014-03-11 08:59:05Z markagreenwood $
 *
 */

package gate.gui.teamware;

import gate.Controller;
import gate.Gate;
import gate.ProcessingResource;
import gate.creole.Parameter;
import gate.creole.ResourceData;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Class containing utility methods for GATE teamware.
 */
public class TeamwareUtils {

  public static final String INPUT_ANNOTATION_SETS_FEATURE = "gate.teamware.inputAnnotationSets";

  public static final String OUTPUT_ANNOTATION_SETS_FEATURE = "gate.teamware.outputAnnotationSets";

  /**
   * Private constructor - this class should not be instantiated.
   */
  private TeamwareUtils() {

  }

  /**
   * Get the set of annotation set names that an application requires
   * for input. If the controller does not yet have a set of input
   * annotation set names, an initial one is constructed using some
   * simple heuristics. The returned set is a reference to the
   * controller's stored set of annotation set names, and changes will
   * propagate back to the controller. The set may be empty, and it may
   * contain a <code>null</code> entry (denoting the default
   * annotation set).
   */
  @SuppressWarnings("unchecked")
  public static Set<String> getInputAnnotationSets(Controller c) {
    Object setNamesObj = c.getFeatures().get(INPUT_ANNOTATION_SETS_FEATURE);
    if(setNamesObj != null && setNamesObj instanceof Set) {
      return (Set<String>)setNamesObj;
    }
    else {
      Set<String> setNames = new HashSet<String>();
      c.getFeatures().put(INPUT_ANNOTATION_SETS_FEATURE, setNames);
      populateInputSetNamesForController(setNames, c, true);

      return setNames;
    }
  }

  /**
   * Analyse the given controller and return a list of likely candidate
   * input annotation sets. The set will not be empty, as it will always
   * include at least a <code>null</code> entry, denoting the default
   * annotation set.
   */
  public static Set<String> getLikelyInputAnnotationSets(Controller c) {
    Set<String> likelySets = new HashSet<String>();
    populateInputSetNamesForController(likelySets, c, false);
    return likelySets;
  }

  /**
   * Populate the set of input annotation set names for a controller
   * based on heuristics. In the strict case we assume that if a PR in
   * the controller has an inputASName parameter whose value is not the
   * default set, and there is no PR earlier in the pipeline which has
   * the same set as its outputASName or annotationSetName parameter,
   * then it is probably a set that needs to be provided externally. In
   * the non-strict case we simply take all inputASName and
   * annotationSetName parameter values from the controller's PRs,
   * without regard for whether they may have been satisfied by an
   * earlier PR in the pipeline.
   * 
   * @param setNames
   * @param c
   * @param strict
   */
  private static void populateInputSetNamesForController(Set<String> setNames,
          Controller c, boolean strict) {
    Set<String> outputSetNamesSoFar = new HashSet<String>();
    Collection<ProcessingResource> prs = c.getPRs();
    try {
      for(ProcessingResource pr : prs) {
        ResourceData rd = Gate.getCreoleRegister().get(pr.getClass().getName());
        List<List<Parameter>> runtimeParams = rd.getParameterList()
                .getRuntimeParameters();
        // check for inputASName and annotationSetName params
        for(List<Parameter> disjunction : runtimeParams) {
          for(Parameter param : disjunction) {
            if(param.getName().equals("inputASName")) {
              String setName = (String)pr.getParameterValue(param.getName());
              if(!strict
                      || (setName != null && !outputSetNamesSoFar
                              .contains(setName))) {
                setNames.add(setName);
              }
            }
            else if(!strict && param.getName().equals("annotationSetName")) {
              setNames.add((String)pr.getParameterValue(param.getName()));
            }
          }
        }

        // check for output set names and update that set
        if(strict) {
          for(List<Parameter> disjunction : runtimeParams) {
            for(Parameter param : disjunction) {
              if(param.getName().equals("outputASName")
                      || param.getName().equals("annotationSetName")) {
                outputSetNamesSoFar.add(String.valueOf(pr
                        .getParameterValue(param.getName())));
              }
            }
          }
        }
      }
    }
    catch(Exception e) {
      // ignore - this is all heuristics, after all
    }
  }

  /**
   * Get the set of annotation set names that an application uses for
   * output. If the controller does not yet have a set of output
   * annotation set names, an initial one is constructed using some
   * simple heuristics. The returned set is a reference to the
   * controller's stored set of annotation set names, and changes will
   * propagate back to the controller. The set may be empty, and it may
   * contain a <code>null</code> entry (denoting the default
   * annotation set).
   */
  @SuppressWarnings("unchecked")
  public static Set<String> getOutputAnnotationSets(Controller c) {
    Object setNamesObj = c.getFeatures().get(OUTPUT_ANNOTATION_SETS_FEATURE);
    if(setNamesObj != null && setNamesObj instanceof Set) {
      return (Set<String>)setNamesObj;
    }
    else {
      Set<String> setNames = new HashSet<String>();
      c.getFeatures().put(OUTPUT_ANNOTATION_SETS_FEATURE, setNames);
      populateOutputSetNamesForController(setNames, c, true);

      return setNames;
    }
  }

  /**
   * Analyse the given controller and return a list of likely candidate
   * output annotation sets. The set will not be empty, as it will
   * always include at least a <code>null</code> entry, denoting the
   * default annotation set.
   */
  public static Set<String> getLikelyOutputAnnotationSets(Controller c) {
    Set<String> likelySets = new HashSet<String>();
    populateOutputSetNamesForController(likelySets, c, false);
    return likelySets;
  }

  /**
   * Populate the set of output annotation set names for a controller
   * based on heuristics. In the strict case, we assume that if a PR in
   * the controller has an outputASName or annotationSetName parameter
   * whose value is not used as the inputASName or annotationSetName of
   * a later PR then it is probably a set that will be output from the
   * controller. In the non-strict case we simply take all outputASName
   * and annotationSetName parameter values from the controller's PRs
   * without regard for which ones may simply be feeding later PRs in
   * the controller (also, the default annotation set is always included
   * in non-strict mode).
   */
  private static void populateOutputSetNamesForController(Set<String> setNames,
          Controller c, boolean strict) {
    Collection<ProcessingResource> prs = c.getPRs();
    try {
      for(ProcessingResource pr : prs) {
        ResourceData rd = Gate.getCreoleRegister().get(pr.getClass().getName());
        List<List<Parameter>> runtimeParams = rd.getParameterList()
                .getRuntimeParameters();
        // (strict mode) remove any candidates from the list which are
        // used as the inputASName or annotationSetName of the current
        // PR.
        if(strict) {
          for(List<Parameter> disjunction : runtimeParams) {
            for(Parameter param : disjunction) {
              if(param.getName().equals("inputASName")
                      || param.getName().equals("annotationSetName")) {
                setNames.remove(pr.getParameterValue(param.getName()));
              }
            }
          }
        }

        // now add all values from outputASName and annotationSetName
        // parameters
        for(List<Parameter> disjunction : runtimeParams) {
          for(Parameter param : disjunction) {
            if(param.getName().equals("outputASName")
                    || param.getName().equals("annotationSetName")) {
              setNames.add((String)pr.getParameterValue(param.getName()));
            }
          }
        }
      }
      
      // finally, add the default set for non-strict mode
      if(!strict) {
        setNames.add(null);
      }
    }
    catch(Exception e) {
      // ignore - this is all heuristics, after all
    }
  }
}
