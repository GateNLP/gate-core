package gate.util.spring;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import gate.Controller;
import gate.Corpus;
import gate.CorpusController;
import gate.Document;
import gate.Factory;
import gate.Gate;
import gate.LanguageAnalyser;
import gate.LanguageResource;
import gate.ProcessingResource;
import gate.Resource;
import gate.creole.ConditionalController;
import gate.creole.ResourceInstantiationException;
//import gate.creole.gazetteer.Gazetteer;
import gate.creole.ontology.Ontology;
import gate.util.GateException;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;

/**
 * Spring factory bean to create duplicate copies of a GATE resource.
 * This bean would typically be declared with singleton scope, but the
 * factory produces a new duplicate of its template resource each time
 * getBean is called (or each time it is injected as a dependency).
 */
public class DuplicateResourceFactoryBean extends GateAwareObject implements
                                                                 FactoryBean,
                                                                 DisposableBean {

  /**
   * The template resource which we will duplicate.
   */
  private Resource templateResource;

  /**
   * Should we return the template itself the first time
   * {@link #getObject()} is called, or should we keep it for use only
   * as a template and just return duplicates?
   */
  private boolean returnTemplate = false;

  /**
   * Customisers that are applied to the duplicated resource before it
   * is returned.
   */
  private List<ResourceCustomiser> customisers;

  /**
   * Set the template resource that this factory bean will duplicate.
   */
  public void setTemplate(Resource template) {
    this.templateResource = template;
  }

  /**
   * Should this factory bean return the template resource itself the
   * first time {@link #getObject()} is called, or should it always
   * return a duplicate, keeping the template in pristine condition.
   * Generally, if the duplicates will all be created up-front, and
   * there are no configured customisers, then it is safe to set this
   * option to true. In cases where the duplicates may be created
   * asynchronously (possibly creating one duplicate while another one
   * is in use in a different thread) or may be customised after
   * creation it is safer to set this option to false (the default) to
   * keep the template pristine and always return duplicates.
   */
  public void setReturnTemplate(boolean returnTemplate) {
    this.returnTemplate = returnTemplate;
  }

  /**
   * Optional customisers that will be applied to the duplicate resource
   * before it is returned. If customisers are specified then it is
   * strongly recommended to leave the returnTemplate option set to
   * <code>false</code>.
   */
  public void setCustomisers(List<ResourceCustomiser> customisers) {
    this.customisers = customisers;
  }

  /**
   * A list of weak references to the duplicates that have been
   * returned, so they can be freed when this factory bean is disposed.
   */
  private List<WeakReference<Resource>> resourcesReturned = Collections
          .synchronizedList(new ArrayList<WeakReference<Resource>>());

  private Class<?> typeClass = null;

  @Override
  public Object getObject() throws Exception {
    ensureGateInit();
    Resource toReturn = null;
    if(returnTemplate) {
      synchronized(resourcesReturned) {
        if(resourcesReturned.isEmpty()) {
          resourcesReturned.add(new WeakReference<Resource>(templateResource));
          toReturn = templateResource;
        }
      }
    }

    if(toReturn == null) {
      toReturn = Factory.duplicate(templateResource);
    }

    if(customisers != null) {
      for(ResourceCustomiser c : customisers) {
        try {
          c.customiseResource(toReturn);
        }
        catch(GateException gx) {
          throw gx;
        }
        catch(IOException ix) {
          throw ix;
        }
        catch(RuntimeException rx) {
          throw rx;
        }
        catch(Exception e) {
          throw new ResourceInstantiationException(
                  "Exception in resource customiser", e);
        }
      }
    }

    if(toReturn != templateResource) {
      resourcesReturned.add(new WeakReference<Resource>(toReturn));
    }

    return toReturn;
  }

  /**
   * Returns a proxy class that implements the same set of GATE
   * interfaces as the template resource. See
   * {@link Factory#duplicate(Resource)} for the list of interfaces that
   * will be considered.
   */
  @Override
  public Class<?> getObjectType() {
    if(templateResource != null && typeClass == null) {
      List<Class<?>> interfaces = new ArrayList<Class<?>>();
      interfaces.add(Resource.class);
      if(templateResource instanceof ProcessingResource) {
        interfaces.add(ProcessingResource.class);
      }
      if(templateResource instanceof LanguageAnalyser) {
        interfaces.add(LanguageAnalyser.class);
      }
      //TODO why was gazetteer being treated special here?
      /*if(templateResource instanceof Gazetteer) {
        interfaces.add(Gazetteer.class);
      }*/
      if(templateResource instanceof Controller) {
        interfaces.add(Controller.class);
      }
      if(templateResource instanceof CorpusController) {
        interfaces.add(CorpusController.class);
      }
      if(templateResource instanceof ConditionalController) {
        interfaces.add(ConditionalController.class);
      }
      if(templateResource instanceof LanguageResource) {
        interfaces.add(LanguageResource.class);
      }
      if(templateResource instanceof Ontology) {
        interfaces.add(Ontology.class);
      }
      if(templateResource instanceof Document) {
        interfaces.add(Document.class);
      }
      if(templateResource instanceof Corpus) {
        interfaces.add(Corpus.class);
      }
      typeClass = Proxy.getProxyClass(Gate.getClassLoader(), interfaces
              .toArray(new Class<?>[interfaces.size()]));
    }
    return typeClass;
  }

  /**
   * This factory is not a singleton - it produces a new object each
   * time {@link #getObject()} is called.
   */
  @Override
  public boolean isSingleton() {
    return false;
  }

  /**
   * Delete the duplicates we have returned, unless they have already
   * been freed.
   */
  @Override
  public void destroy() {
    for(WeakReference<Resource> res : resourcesReturned) {
      Resource r = res.get();
      if(r != null) {
        Factory.deleteResource(r);
      }
    }
  }
}
