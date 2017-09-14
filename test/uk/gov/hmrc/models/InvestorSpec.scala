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

package uk.gov.hmrc.models

import org.joda.time.DateTime
import org.scalatestplus.play.PlaySpec
import play.api.libs.json.Json
import uk.gov.hmrc.lisa.models.Investor

class InvestorSpec extends PlaySpec  {

  val investor = Investor("Me", "Person", new DateTime(1985,12,14,0,0), "AA123456A")

  val investorFromJson = Json.toJson(
    """{
    \"investorNINO\": \"AA123456A\",
    \"firstName\": \"First Name\",
    \"lastName\": \"Last Name\",
    \"dateOfBirth\": \"1985-03-25\"
  }"""
  )

  "The Investor model "  must {
    "return the correct firstname " in {
      investor.firstname mustBe "Me"
    }
    "return the correct lastname " in {
      investor.lastname mustBe "Person"
    }
    "return the correct nino" in {
      investor.nino mustBe "AA123456A"
    }
    "return the correct dob" in {
      investor.dob.toString("MM-dd-YYYY") mustBe "12-14-1985"
    }

  }
}
