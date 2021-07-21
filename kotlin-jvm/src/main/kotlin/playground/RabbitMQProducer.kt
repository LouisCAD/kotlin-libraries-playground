@file:Suppress("PackageDirectoryMismatch")

package playground.rabbitmq.producer

import com.rabbitmq.client.ConnectionFactory
import com.uchuhimo.konf.Config
import com.uchuhimo.konf.ConfigSpec
import java.nio.charset.StandardCharsets

fun main() {
    println()
    println("# rabbitmq producer")

    val config = Config {
        addSpec(AmqpSpec)
    }
    val spec = config.from.env()
    println(spec)
    val factory = ConnectionFactory()
    factory.newConnection(spec[AmqpSpec.url]).use { connection ->
        connection.createChannel().use { channel ->
            channel.queueDeclare(spec[AmqpSpec.queue], false, false, false, null)
            val message = "Hello World!"
            channel.basicPublish(
                "",
                spec[AmqpSpec.queue],
                null,
                message.toByteArray(StandardCharsets.UTF_8)
            )
            println(" [x] Sent '$message'")
        }
    }
}

object AmqpSpec : ConfigSpec() {
    val url by required<String>()
    val queue by required<String>()
}
