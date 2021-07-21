@file:Suppress("PackageDirectoryMismatch")

package playground.velocity

import org.apache.velocity.Template
import org.apache.velocity.VelocityContext
import org.apache.velocity.app.VelocityEngine
import playground.shouldBe
import java.io.StringWriter
import java.util.*

/**
 * Velocity is a Java-based template engine. It permits anyone to use a simple yet powerful template language to reference objects defined in Java code.
 *
 * [Apache Velocity Engine - User Guide](https://velocity.apache.org/engine/2.3/user-guide.html)
 * [Apache Velocity Engine - Developer Guide](https://velocity.apache.org/engine/2.3/developer-guide.html)
 */
fun main() {
    println()
    println("# Velocity")

    val engine = velocityEngine()
    helloWorld(engine)
    complexExample(engine)
}


fun helloWorld(engine: VelocityEngine) {
    val context = VelocityContext()
    context.put("name", "Velocity")
    val template: Template = engine.getTemplate("/hello.vm")
    template.apply(context) shouldBe """
        <body>
        Hello Velocity World!
        </body>
        """.trimIndent()

}


fun complexExample(engine: VelocityEngine) {
    val context = VelocityContext().apply {
        put("name", "Velocity")
        put("details", true)
        put("customer", VelocityCustomer("Jean-Michel", 39))
        put("groceries", listOf("apple", "milk"))
    }
    engine.getTemplate("/complex.vm").apply(context) shouldBe """
        Hello Velocity
        I speak French
        Customer Jean-Michel is 41 years old.
        Is he old? true enough
        Groceries:
            - apple
            - milk
    """.trimIndent()
}

data class VelocityCustomer(val name: String, var age: Int) {
    fun isOld() = age >= 40
}


private fun Template.apply(context: VelocityContext) : String {
    val sw = StringWriter()
    merge(context, sw)
    return sw.toString().trim()
}

private fun velocityEngine(): VelocityEngine {
    val properties = Properties().also {
        it.setProperty(
            "resource.loader.file.path",
            "/Users/jmfayard/Documents/GitHub/kotlin-libraries-playground/kotlin-jvm/src/main/resources"
        )
    }
    val engine = VelocityEngine(properties)
    engine.init();
    return engine
}
