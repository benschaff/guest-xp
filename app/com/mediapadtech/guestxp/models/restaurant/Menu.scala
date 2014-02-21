package com.mediapadtech.guestxp.models.restaurant

import com.mediapadtech.guestxp.models.common.{Comment, Media}
import com.mediapadtech.guestxp.models.restaurant.Menu.{Name, Category}

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
case class Menu(name: Name, items: Map[Category, MenuItem] = Map.empty[Category, MenuItem])

case class MenuItem(name: String,
                    description: String,
                    thumbnail: Option[Media] = None,
                    medias: Set[Media] = Set.empty[Media],
                    comments: List[Comment] = List.empty[Comment],
                    rating: Option[Double])

object Menu {

  type Category = String

  type Name = String

}