package com.mediapadtech.guestxp.models

import reactivemongo.bson.{BSONDocumentWriter, BSONDocument, BSONDocumentReader}
import com.mediapadtech.guestxp.models.common._
import com.mediapadtech.guestxp.models.restaurant.Menu.Category
import com.mediapadtech.guestxp.models.common.Coordinates
import com.mediapadtech.guestxp.models.common.Media
import com.mediapadtech.guestxp.models.common.Comment
import com.mediapadtech.guestxp.models.common.Address
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
package object restaurant {

  object JsFormat {

    import com.mediapadtech.guestxp.models.common.JsFormats._

    implicit object MenuItemBSONReader extends BSONDocumentReader[MenuItem] {

      def read(document: BSONDocument): MenuItem = MenuItem(
        document.getAs[String]("name").get,
        document.getAs[String]("description").get,
        document.getAs[Media]("thumbnail"),
        document.getAs[Set[Media]]("medias").getOrElse(Set.empty[Media]),
        document.getAs[List[Comment]]("comments").getOrElse(List.empty[Comment]),
        document.getAs[Double]("rating")
      )

    }

    implicit object MenuItemBSONWriter extends BSONDocumentWriter[MenuItem] {

      def write(menuItem: MenuItem): BSONDocument = BSONDocument(
        "name" -> menuItem.name,
        "description" -> menuItem.description,
        "thumbnail" -> menuItem.thumbnail,
        "medias" -> menuItem.medias,
        "comments" -> menuItem.comments,
        "rating" -> menuItem.rating
      )

    }

    implicit object MenuBSONReader extends BSONDocumentReader[Menu] {

      def read(document: BSONDocument): Menu = Menu(
        document.getAs[String]("name").get,
        document.getAs[Map[Category, MenuItem]]("items").getOrElse(Map.empty[Category, MenuItem])
      )

    }

    implicit object MenuBSONWriter extends BSONDocumentWriter[Menu] {

      def write(menu: Menu): BSONDocument = BSONDocument(
        "name" -> menu.name,
        "items" -> menu.items
      )

    }

    implicit object ScheduleEntryBSONReader extends BSONDocumentReader[ScheduleEntry] {

      def read(document: BSONDocument): ScheduleEntry = ScheduleEntry(
        document.getAs[Int]("openingHour").get,
        document.getAs[Int]("closingHour").get
      )

    }

    implicit object ScheduleEntryBSONWriter extends BSONDocumentWriter[ScheduleEntry] {

      def write(scheduleEntry: ScheduleEntry): BSONDocument = BSONDocument(
        "openingHour" -> scheduleEntry.openingHour,
        "closingHour" -> scheduleEntry.closingHour
      )

    }

    implicit object RestaurantBSONReader extends BSONDocumentReader[Restaurant] {

      def read(document: BSONDocument): Restaurant = Restaurant(
        document.getAs[String]("name").get,
        document.getAs[Address]("address").get,
        document.getAs[Coordinates]("coordinates"),
        document.getAs[Contact]("contact"),
        document.getAs[Map[Day, ScheduleEntry]]("schedule").getOrElse(Map.empty[Day, ScheduleEntry]),
        document.getAs[Map[Menu.Name, Menu]]("menus").getOrElse(Map.empty[Menu.Name, Menu])
      )

    }

    implicit object RestaurantBSONWriter extends BSONDocumentWriter[Restaurant] {

      def write(restaurant: Restaurant): BSONDocument = BSONDocument(
        "name" -> restaurant.name,
        "address" -> restaurant.address,
        "coordinates" -> restaurant.coordinates,
        "contact" -> restaurant.contact,
        "schedule" -> restaurant.schedule,
        "menus" -> restaurant.menus
      )

    }

  }

}
