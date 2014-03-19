package com.mediapadtech.guestxp.models.restaurant

import com.mediapadtech.guestxp.models.common.{Contact, Coordinates, Address}

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
case class Restaurant(ownerId: String,
                      name: String,
                      address: Address,
                      coordinates: Option[Coordinates] = None,
                      contact: Option[Contact] = None,
                      schedule: Map[Day, ScheduleEntry] = Map.empty[Day, ScheduleEntry],
                      menus: Map[Name, Menu] = Map.empty[Name, Menu])

case class ScheduleEntry(openingHour: Int, closingHour: Int)