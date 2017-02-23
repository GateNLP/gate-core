/*
 *  SearchableDataStore.java
 *
 *  Niraj Aswani, 19/March/07
 *
 *  $Id: SearchableDataStore.html,v 1.0 2007/03/19 16:22:01 niraj Exp $
 */
package gate.creole.annic;

import gate.DataStore;

/**
 * Datastores want to become indexable and searchable should implement this interface.
 * @author niraj
 */
public interface SearchableDataStore extends Searchable, DataStore {
  // it doesn't specify its own methods, may be later when recognized
}
