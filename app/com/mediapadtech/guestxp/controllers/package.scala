package com.mediapadtech.guestxp

import play.api.libs.json.{Writes, Json, JsObject}
import play.api.mvc.SimpleResult

/**
 * This file is part of guest-xp.
 *
 * guest-xp is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.

 * guest-xp is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with guest-xp.  If not, see <http://www.gnu.org/licenses/>.
 *
 * "Copyright 2014 Benjamin Schaff"
 */
package object controllers {

  def failure(code: Int, message: String, resultWrapper: (JsObject) => SimpleResult): SimpleResult = {
    resultWrapper(Json.obj("status" -> "failure", "code" -> code, "message" -> message))
  }

  def failure(code: Int, message: String, resultWrapper: (JsObject) => SimpleResult, reason: JsObject): SimpleResult = {
    resultWrapper(Json.obj("status" -> "failure", "code" -> code, "message" -> message, "reason" -> reason))
  }

  def success[T](content: T, resultWrapper: (JsObject) => SimpleResult)(implicit writes: Writes[T]): SimpleResult = {
    resultWrapper(Json.obj("status" -> "success", "content" -> Json.toJson(content)))
  }

}
