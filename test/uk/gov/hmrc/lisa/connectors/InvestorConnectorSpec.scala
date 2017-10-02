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

package uk.gov.hmrc.lisa.connectors

import org.joda.time.DateTime
import org.scalatest.mock.MockitoSugar
import org.scalatestplus.play.{OneAppPerSuite, PlaySpec}
import uk.gov.hmrc.lisa.models.Investor
import uk.gov.hmrc.play.http.{HeaderCarrier, HttpPost}

import scala.concurrent.Await
import scala.concurrent.duration.Duration

class InvestorConnectorSpec  extends PlaySpec with OneAppPerSuite with MockitoSugar {

  val payload = Investor("Mark","Me",new DateTime(2017,9,10,0,0),"AA12334A")

  "Connecting with connector " should {
    "return 200 for a valid json" in {
        val res = Await.result(InvestorConnector.post("z123456", payload), Duration.Inf)
        res.status mustBe 200

   }
  }

  implicit val hc = HeaderCarrier()

  val mockHttpPost = mock[HttpPost]

  object SUT extends InvestorConnector {
    override val httpPost: HttpPost = mockHttpPost
  }

}
