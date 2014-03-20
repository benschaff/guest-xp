package com.mediapadtech.guestxp.controllers

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
case class PersistenceError(code: Int, messageKey: String)

object PersistenceError {

  val RESTAURANT_CREATION_FAILED = PersistenceError(2000, "persistence.error.message.restaurant.creation.failed")

}