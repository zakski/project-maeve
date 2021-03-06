// Copyright 2016 zakski.
// See the LICENCE.txt file distributed with this work for additional
// information regarding copyright ownership.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package com.szadowsz.maeve.core.instruction.target.multi

import com.szadowsz.common.net.Uri


/**
  * Class for Uri targeting during the scraping process, if we are expecting to differentiate web pages by their query parameters.
  *
  * Created on 24/05/2016.
  */
case class QueryTarget(
                         override val baseUrl: Uri,
                         key: String,
                         override val seq: Seq[String],
                         override val hist: Seq[String] = Nil
                       ) extends MultiTarget[String,QueryTarget] {

  /**
    * Builds and returns the current Uri
    *
    * @return the current uri
    */
  override def currentUrl() = baseUrl.appendQuery(key, seq.head)

  /**
    * Method to update the url to the next in sequence.
    *
    * @return a fresh instance, updated to the next target in the sequence
    */
  override def next() = copy(seq = seq.tail, hist = hist :+ seq.head)

  /**
    * Method to reset the target.
    *
    * @return a fresh version of the target sequence with no history.
    */
  override def reset() = copy(seq = hist ++ seq, hist = Nil)
}
