# lisa

[![Build Status](https://travis-ci.org/hmrc/lisa.svg)](https://travis-ci.org/hmrc/lisa) [ ![Download](https://api.bintray.com/packages/hmrc/releases/lisa/images/download.svg) ](https://bintray.com/hmrc/releases/lisa/_latestVersion)

Practice microservice

Specification to read the following JSON representing a create investor

payload and LISA Manager Reference Number

lisaManagerReferenceNumber: Use your test user profile
    {
    "investorNINO": "AA123456A",
    "firstName": "First Name",
    "lastName": "Last Name",
    "dateOfBirth": "1985-03-25"
    }

This should return 201 created

The code should validate the NINO as ^((?!(BG|GB|KN|NK|NT|TN|ZZ)|(D|F|I|Q|U|V)[A-Z]|[A-Z](D|F|I|O|Q|U|V))[A-Z]{2})[0-9]{6}[A-D]?$
The code should validate the date as YYYY-MM-DD

Invalidate data should return a 400 with a message saying which field is wrong so the following json submission should return 400 with a 2 messages saying what is wrong
{
"investorNINO": "A1234567A",
"firstName": "first name",
"dateOfBirth": "25-03-85"
}

{
"code": "BAD_REQUEST",
"message": "Bad Request",
"errors": [
{
"code": "MISSING_FIELD",
"message": "This field is required",
"path": "/lastName"
},
{
"code": "INVALID_DATE",
"message": "Date is invalid",
"path": "/dateOfBirth"
},
{
"code": "INVALID_FORMAT",
"message": "Invalid format has been used",
"path": "/investorNINO"
}
]
}

IT should call out the lisa-stub and return the data returned from it and then log it.


### License

This code is open source software licensed under the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html")