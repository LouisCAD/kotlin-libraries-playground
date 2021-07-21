@file:Suppress("PackageDirectoryMismatch")

package playground.rabbitmq.client

import com.rabbitmq.client.CancelCallback
import com.rabbitmq.client.Channel
import com.rabbitmq.client.ConnectionFactory
import com.rabbitmq.client.DeliverCallback
import com.rabbitmq.client.Delivery
import com.uchuhimo.konf.Config
import playground.rabbitmq.producer.AmqpSpec
import java.nio.charset.StandardCharsets

fun main() {
    println()
    println("# rabbitmq")

    val config = Config {
        addSpec(AmqpSpec)
    }
    val spec = config.from.env()
    println(spec)
    val factory = ConnectionFactory()
    factory.newConnection(spec[AmqpSpec.url]).use { connection ->
        connection.createChannel().use { channel ->
            consumeMessage(channel, spec[AmqpSpec.queue])
        }
    }
}

fun consumeMessage(channel: Channel, queueName: String) {
    val consumerTag = "SimpleConsumer"

    channel.queueDeclare(queueName, false, false, false, null)

    println("[$consumerTag] Waiting for messages...")
    val deliverCallback = DeliverCallback { consumerTag: String?, delivery: Delivery ->
        val message = String(delivery.body, StandardCharsets.UTF_8)
        println("[$consumerTag] Received message: '$message'")
    }
    val cancelCallback = CancelCallback { consumerTag: String? ->
        println("[$consumerTag] was canceled")
    }

    channel.basicConsume(queueName, true, consumerTag, deliverCallback, cancelCallback)

}

