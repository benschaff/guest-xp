package com.mediapadtech.guestxp.models

import reactivemongo.bson.{BSONDocumentWriter, BSONObjectID, BSONDocument, BSONDocumentReader}

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
package object common {

  object JsFormats {

    import play.api.libs.json._

    implicit val contactFormat = Json.format[Contact]
    implicit val coordinatesFormat = Json.format[Coordinates]
    implicit val userFormat = Json.format[User]

    implicit def MapReader[V](implicit vr: BSONDocumentReader[V]): BSONDocumentReader[Map[String, V]] = new BSONDocumentReader[Map[String, V]] {
      def read(document: BSONDocument): Map[String, V] = {
        val elements = document.elements.map {
          tuple =>
            tuple._1 -> vr.read(tuple._2.seeAsTry[BSONDocument].get)
        }
        elements.toMap
      }
    }

    implicit def MapWriter[V](implicit vw: BSONDocumentWriter[V]): BSONDocumentWriter[Map[String, V]] = new BSONDocumentWriter[Map[String, V]] {
      def write(map: Map[String, V]): BSONDocument = {
        val elements = map.toStream.map {
          tuple =>
            tuple._1 -> vw.write(tuple._2)
        }
        BSONDocument(elements)
      }
    }

    implicit def IntMapReader[V](implicit vr: BSONDocumentReader[V]): BSONDocumentReader[Map[Int, V]] = new BSONDocumentReader[Map[Int, V]] {
      def read(document: BSONDocument): Map[Int, V] = {
        val elements = document.elements.map {
          tuple =>
            tuple._1.toInt -> vr.read(tuple._2.seeAsTry[BSONDocument].get)
        }
        elements.toMap
      }
    }

    implicit def IntMapWriter[V](implicit vw: BSONDocumentWriter[V]): BSONDocumentWriter[Map[Int, V]] = new BSONDocumentWriter[Map[Int, V]] {
      def write(map: Map[Int, V]): BSONDocument = {
        val elements = map.toStream.map {
          tuple =>
            tuple._1.toString -> vw.write(tuple._2)
        }
        BSONDocument(elements)
      }
    }

    implicit object MediaBSONReader extends BSONDocumentReader[Media] {

      def read(document: BSONDocument): Media = Media(
        document.getAs[BSONObjectID]("_id"),
        document.getAs[String]("title").get,
        document.getAs[String]("description").get,
        document.getAs[String]("mimeType").get,
        document.getAs[String]("extension")
      )

    }

    implicit object MediaBSONWriter extends BSONDocumentWriter[Media] {

      def write(media: Media): BSONDocument = BSONDocument(
        "_id" -> media.id.getOrElse(BSONObjectID.generate),
        "title" -> media.title,
        "description" -> media.description,
        "mimeType" -> media.mimeType,
        "extension" -> media.extension
      )

    }

    implicit object CommentBSONReader extends BSONDocumentReader[Comment] {

      def read(document: BSONDocument): Comment = Comment(
        document.getAs[String]("owner").get,
        document.getAs[String]("text").get,
        document.getAs[Boolean]("moderated").get,
        document.getAs[Boolean]("official").get
      )

    }

    implicit object CommentBSONWriter extends BSONDocumentWriter[Comment] {

      def write(comment: Comment): BSONDocument = BSONDocument(
        "owner" -> comment.owner,
        "text" -> comment.text,
        "moderated" -> comment.moderated,
        "official" -> comment.official
      )

    }

    implicit object AddressBSONReader extends BSONDocumentReader[Address] {

      def read(document: BSONDocument): Address = Address(
        document.getAs[Int]("number").get,
        document.getAs[String]("street").get,
        document.getAs[String]("postalCode").get,
        document.getAs[String]("city").get,
        document.getAs[String]("state").get,
        document.getAs[String]("country").get
      )

    }

    implicit object AddressBSONWriter extends BSONDocumentWriter[Address] {

      def write(address: Address): BSONDocument = BSONDocument(
        "number" -> address.number,
        "street" -> address.street,
        "postalCode" -> address.postalCode,
        "city" -> address.city,
        "state" -> address.state,
        "country" -> address.country
      )

    }

    implicit object CoordinatesBSONReader extends BSONDocumentReader[Coordinates] {

      def read(document: BSONDocument): Coordinates = Coordinates(
        document.getAs[Double]("longitude").get,
        document.getAs[Double]("latitude").get
      )

    }

    implicit object CoordinatesBSONWriter extends BSONDocumentWriter[Coordinates] {

      def write(coordinates: Coordinates): BSONDocument = BSONDocument(
        "longitude" -> coordinates.longitude,
        "latitude" -> coordinates.latitude
      )

    }

    implicit object ContactBSONReader extends BSONDocumentReader[Contact] {

      def read(document: BSONDocument): Contact = Contact(
        document.getAs[String]("name").get,
        document.getAs[String]("lastName").get,
        document.getAs[String]("mail").get,
        document.getAs[String]("phoneNumber").get,
        document.getAs[String]("faxNumber").get
      )

    }

    implicit object ContactBSONWriter extends BSONDocumentWriter[Contact] {

      def write(contact: Contact): BSONDocument = BSONDocument(
        "name" -> contact.name,
        "lastName" -> contact.lastName,
        "mail" -> contact.mail,
        "phoneNumber" -> contact.phoneNumber,
        "faxNumber" -> contact.faxNumber
      )

    }

  }

}
