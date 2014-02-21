package com.mediapadtech.guestxp.models.restaurant

import com.mediapadtech.guestxp.models.common.{Contact, Coordinates, Address}
import com.mediapadtech.guestxp.models.restaurant.Restaurant.Day

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
case class Restaurant(name: String,
                      address: Address,
                      coordinates: Option[Coordinates] = None,
                      contact: Option[Contact] = None,
                      schedule: Map[Day, ScheduleEntry] = Map.empty[Day, ScheduleEntry],
                      menus: Map[Menu.Name, Menu] = Map.empty[Menu.Name, Menu])

case class ScheduleEntry(openingHour: Int, closingHour: Int)

object Restaurant {

  type Day = Int

}