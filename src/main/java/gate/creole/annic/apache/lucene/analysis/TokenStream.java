package gate.creole.annic.apache.lucene.analysis;

/**
 * Copyright 2004 The Apache Software Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.IOException;

/**
 * A TokenStream enumerates the sequence of tokens, either from fields of a
 * document or from query text.
 */
public abstract class TokenStream {
  /** Returns the next token in the stream, or null at EOS. */
  public abstract Token next() throws IOException;

  /** Releases resources associated with this stream. */
  public void close() throws IOException {}
}
