package com.mediapadtech.guestxp.models


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
package object common {

  object JsFormats {

    import play.api.libs.json._

    implicit val contactFormat = Json.format[Contact]
    implicit val coordinatesFormat = Json.format[Coordinates]
    implicit val userFormat = Json.format[User]
    implicit val mediaFormat = Json.format[Media]
    implicit val commentFormat = Json.format[Comment]
    implicit val addressFormat = Json.format[Address]

  }

}
