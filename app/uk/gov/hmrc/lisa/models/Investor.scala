/*
 * Copyright 2017 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.lisa.models

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import play.api.Logger
import play.api.data.validation.ValidationError
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json._

case class Investor(firstname: String, lastname: String, dob: DateTime, nino: String)


object Investor {

  implicit val investorread: Reads[Investor] = ((JsPath \ "firstname").read[String] and
    (JsPath \ "lastname").read[String] and
    (JsPath \ "dob").read(isoDateReads).map(new DateTime(_)) and
    (JsPath \ "nino").read[String](Reads.pattern("^((?!(BG|GB|KN|NK|NT|TN|ZZ)|(D|F|I|Q|U|V)[A-Z]|[A-Z](D|F|I|O|Q|U|V))[A-Z]{2})[0-9]{6}[A-D]?$".r,
      "error.formatting.nino"))
    ) (Investor.apply _)

  implicit val investorwrite: Writes[Investor] = (
    (JsPath \ "lastname").write[String] and
      (JsPath \ "firstname").write[String] and
      (JsPath \ "dob").write[String].contramap[DateTime](d => d.toString("yyyy-MM-dd")) and
      (JsPath \ "nino").write[String])(unlift(Investor.unapply))

  private def isoDateReads: Reads[DateTime] = new Reads[DateTime] {
    val dateformat = "yyyy-MM-dd"
    val dateValidationMessaqe = "error.formatting.dob"

    def parseDate(input: String): Option[DateTime] =
      scala.util.control.Exception.allCatch[DateTime] opt (DateTime.parse(input, DateTimeFormat.forPattern(dateformat)))

    def reads(json: JsValue): JsResult[DateTime] = json match {
      case JsString(s) => parseDate(s) match {
        case Some(d) => {Logger.info("The date parsed as " + d.toString );JsSuccess(d)}
        case None => JsError(Seq(JsPath()->Seq(ValidationError(dateValidationMessaqe))))
      }
      case _ => JsError(Seq(JsPath() -> Seq(ValidationError("error.expected.jsstring"))))
    }

  }
}