package com.mediapadtech.guestxp.models

import play.api.libs.json.Json.JsValueWrapper

/**
 * This file is part of guest-xp.
 *
 * guest-xp is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.

 * Foobar is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
 *
 * "Copyright 2014 Benjamin Schaff"
 */
package object restaurant {

  type Day = Int

  type Category = String

  type Name = String

  object JsFormat {

    import play.api.libs.json._
    import com.mediapadtech.guestxp.models.common.JsFormats._

    implicit val menuItemFormat = Json.format[MenuItem]
    implicit val menuFormat = Json.format[Menu]
    implicit val scheduleEntryFormat = Json.format[ScheduleEntry]

    implicit val scheduleFormat = new Format[Map[Day, ScheduleEntry]] {

      def writes(schedule: Map[Day, ScheduleEntry]): JsValue = {
        Json.obj(
          schedule.map {
            case (day, scheduleEntry) => day.toString -> Json.toJson(scheduleEntry).asInstanceOf[JsValueWrapper]
          }.toSeq: _*
        )
      }

      def reads(jsValue: JsValue): JsResult[Map[Day, ScheduleEntry]] = {
        JsSuccess(jsValue.as[Map[String, JsValue]].map {
          case (k, v) => k.toInt -> v.as[ScheduleEntry]
        })
      }

    }

    implicit val menusFormat = new Format[Map[Name, Menu]] {

      def writes(schedule: Map[Name, Menu]): JsValue = {
        Json.obj(
          schedule.map {
            case (name, menu) => name -> Json.toJson(menu).asInstanceOf[JsValueWrapper]
          }.toSeq: _*
        )
      }

      def reads(jsValue: JsValue): JsResult[Map[Name, Menu]] = {
        JsSuccess(jsValue.as[Map[String, JsValue]].map {
          case (k, v) => k -> v.as[Menu]
        })
      }

    }

    implicit val restaurantFormat = Json.format[Restaurant]

  }

}
