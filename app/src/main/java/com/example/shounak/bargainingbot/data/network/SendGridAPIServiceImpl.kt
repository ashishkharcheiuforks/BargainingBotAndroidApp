package com.example.shounak.bargainingbot.data.network

import com.example.shounak.bargainingbot.data.db.entity.User
import com.example.shounak.bargainingbot.internal.APIToken
import com.google.gson.JsonParser
import com.sendgrid.SendGrid
import com.sendgrid.SendGridException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*


private const val API_KEY = APIToken.SEND_GRID_API_KEY


class SendGridAPIServiceImpl : SendGridAPIService {


    override suspend fun sendEmail(data: HashMap<String, Any>, user: User) {
        withContext(Dispatchers.IO) {
            val sendgrid = SendGrid(API_KEY)
            val total = data["Total"] as Int

            val parser = JsonParser()
            val orderArray = parser.parse(data["Order"] as String).asJsonArray


            val email = SendGrid.Email()
            email.addTo(user.email)
            email.from = "theSocialBaristro@orders.com"
            email.subject = "Your Order"

            val text = StringBuilder()
            text.apply {
                appendln("Thank You for visiting!")
                appendln("")
                appendln("Here is the invoice for your order:")
                appendln("")
                appendln("Name -- Quantity -- Cost")
                appendln("")
                appendln("")
                for (orderJson in orderArray) {
                    val order = orderJson.asJsonObject
                    val name = order["name"]
                    val quantity = order["quantity"]
                    val cost = order["cost"]
                    appendln("$name -- $quantity -- $cost")
                    appendln("")
                }
                appendln("")
                appendln("")
                appendln("Total :                        $total")
            }
            email.text = text.toString()


            try {
                val response = sendgrid.send(email)
                println(response.message)
            } catch (e: SendGridException) {
                System.err.println(e)
            }
        }

    }
//    override suspend fun sendGrid() {
//        withContext(Dispatchers.IO) {
//            val from = Email("test@example.com")
//            val subject = "Sending with SendGrid is Fun"
//            val to = Email("shounakmulay2@gmail.com")
//            val content = Content("text/plain", "and easy to do anywhere, even with Java")
//            val mail = Mail(from, subject, to, content)
//
//            val sg = SendGrid(API_KEY)
//            val request = Request()
//            try {
//                request.method = Method.POST
//                request.endpoint = "mail/send"
//                request.body = mail.build()
//                val response = sg.api(request)
//                System.out.println(response.statusCode)
//                System.out.println(response.body)
//                System.out.println(response.headers)
//            } catch (ex: IOException) {
//                throw ex
//            }
//        }
//    }
}

