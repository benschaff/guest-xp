package com.mediapadtech.guestxp.controllers

import play.api.mvc.{Action, Controller}
import play.modules.reactivemongo.MongoController
import play.modules.reactivemongo.json.collection.JSONCollection
import scala.concurrent.ExecutionContext.Implicits.global
import play.api.libs.json._
import com.mediapadtech.guestxp.models.restaurant.Restaurant
import com.mediapadtech.guestxp.models.restaurant.JsFormat.restaurantFormat
import scala.concurrent.Future

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
trait RestaurantAPI {
  this: Controller =>

  def restaurants: JSONCollection

  def list(owner: String) = Action.async {
    restaurants.find(Json.obj("owner" -> owner)).cursor[Restaurant].collect[List]() map {
      restaurants => Ok(Json.toJson(restaurants))
    }
  }

}

object RestaurantAPI extends Controller with RestaurantAPI with MongoController {

  override def restaurants: JSONCollection = db.collection[JSONCollection]("restaurant")

}
