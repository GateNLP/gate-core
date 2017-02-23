package gate.gui.ontology;

import java.util.List;
import java.util.Locale;
import java.util.Set;

import gate.creole.ontology.AnnotationProperty;
import gate.creole.ontology.Literal;
import gate.creole.ontology.OConstants.Closure;
import gate.creole.ontology.ONodeID;
import gate.creole.ontology.OResource;
import gate.creole.ontology.OURI;
import gate.creole.ontology.Ontology;
import gate.creole.ontology.RDFProperty;

/**
 * An empty impl of RDFProperty that can be used as a prototype in a JComboBox etc.
 * @author Mark A. Greenwood
 */
@SuppressWarnings("deprecation")
class RDFPropertyPrototype implements RDFProperty {

  String prototype;
  
  public RDFPropertyPrototype(String prototype) {
    this.prototype = prototype;
  }
  
  @Override
  public gate.creole.ontology.URI getURI() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ONodeID getONodeID() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void setURI(gate.creole.ontology.URI uri) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public Set<Literal> getLabels() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Set<Literal> getComments() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getComment(Locale language) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void setComment(String aComment, Locale Locale) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public String getLabel(Locale language) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void setLabel(String aLabel, Locale language) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public String getName() {
    return prototype;
  }

  @Override
  public Ontology getOntology() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void addAnnotationPropertyValue(
          AnnotationProperty theAnnotationProperty, Literal literal) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public List<Literal> getAnnotationPropertyValues(
          AnnotationProperty theAnnotationProperty) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Set<AnnotationProperty> getSetAnnotationProperties() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Set<RDFProperty> getAllSetProperties() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Set<RDFProperty> getPropertiesWithResourceAsDomain() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Set<RDFProperty> getPropertiesWithResourceAsRange() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean hasAnnotationPropertyWithValue(AnnotationProperty aProperty,
          Literal aValue) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public void removeAnnotationPropertyValue(
          AnnotationProperty theAnnotationProperty, Literal literal) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void removeAnnotationPropertyValues(AnnotationProperty theProperty) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void setEquivalentPropertyAs(RDFProperty theProperty) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public Set<RDFProperty> getEquivalentPropertyAs() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean isEquivalentPropertyAs(RDFProperty theProperty) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public Set<RDFProperty> getSuperProperties(byte closure) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Set<RDFProperty> getSuperProperties(Closure closure) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean isSuperPropertyOf(RDFProperty theProperty, byte closure) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean isSuperPropertyOf(RDFProperty theProperty, Closure closure) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public void addSubProperty(RDFProperty property) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void removeSubProperty(RDFProperty property) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public Set<RDFProperty> getSubProperties(byte closure) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Set<RDFProperty> getSubProperties(Closure closure) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean isSubPropertyOf(RDFProperty theProperty, byte closure) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean isSubPropertyOf(RDFProperty theProperty, Closure closure) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean isFunctional() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public void setFunctional(boolean functional) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public boolean isInverseFunctional() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public void setInverseFunctional(boolean inverseFunctional) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public boolean isValidRange(OResource aResource) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean isValidDomain(OResource aResource) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public Set<OResource> getDomain() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Set<OResource> getRange() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public OURI getOURI() {
    // TODO Auto-generated method stub
    return null;
  }
  
  @Override
  public String toString() {
    return prototype;
  }

}
