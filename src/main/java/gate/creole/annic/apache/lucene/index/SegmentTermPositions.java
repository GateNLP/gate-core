package gate.creole.annic.apache.lucene.index;

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

import gate.creole.annic.apache.lucene.store.InputStream;

final class SegmentTermPositions
extends SegmentTermDocs implements TermPositions {
  private InputStream proxStream;
  private int proxCount;
  private int position;

  SegmentTermPositions(SegmentReader p) throws IOException {
    super(p);
    this.proxStream = (InputStream)parent.proxStream.clone();
  }

  @Override
  final void seek(TermInfo ti) throws IOException {
    super.seek(ti);
    if (ti != null)
      proxStream.seek(ti.proxPointer);
    proxCount = 0;
  }

  @Override
  public final void close() throws IOException {
    super.close();
    proxStream.close();
  }

  @Override
  public final int nextPosition() throws IOException {
    proxCount--;
    return position += proxStream.readVInt();
  }

  @Override
  protected final void skippingDoc() throws IOException {
    for (int f = freq; f > 0; f--)		  // skip all positions
      proxStream.readVInt();
  }

  @Override
  public final boolean next() throws IOException {
    for (int f = proxCount; f > 0; f--)		  // skip unread positions
      proxStream.readVInt();

    if (super.next()) {				  // run super
      proxCount = freq;				  // note frequency
      position = 0;				  // reset position
      return true;
    }
    return false;
  }

  @Override
  public final int read(final int[] docs, final int[] freqs)
      throws IOException {
    throw new UnsupportedOperationException("TermPositions does not support processing multiple documents in one call. Use TermDocs instead.");
  }


  /** Called by super.skipTo(). */
  @Override
  protected void skipProx(long proxPointer) throws IOException {
    proxStream.seek(proxPointer);
    proxCount = 0;
  }

}
