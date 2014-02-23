package com.mediapadtech.guestxp

import play.api.libs.MimeTypes

/**
 * This file is part of Guest eXPerience.
 *
 * Guest eXPerience is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.

 * Guest eXPerience is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with Guest eXPerience.  If not, see <http://www.gnu.org/licenses/>.
 *
 * "Copyright 2014 Benjamin Schaff"
 */
package object utils {

  def extensionForMimeType(mimeType: String): Option[String] = MimeTypes.types.find(_._2 == mimeType) match {
    case Some(keyValue) => Some(keyValue._2)
    case None => None
  }

}
