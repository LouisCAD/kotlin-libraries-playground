@file:Suppress("PackageDirectoryMismatch")

package playground.barber

import app.cash.barber.Barber
import app.cash.barber.Barbershop
import app.cash.barber.BarbershopBuilder
import app.cash.barber.getBarber
import app.cash.barber.locale.Locale.Companion.EN_US
import app.cash.barber.models.Document
import app.cash.barber.models.DocumentData
import app.cash.barber.models.DocumentTemplate
import playground.shouldBe
import playground.shouldThrow
import java.time.Instant
import java.util.*

/***
 * cashapp/barber
 * A type safe Kotlin JVM library for building up localized, fillable, themed documents using Mustache templating.
 *
 * - [GitHub](https://github.com/cashapp/barber)
 * - [Documentation](https://cashapp.github.io/barber/)
 */
fun main() {
    println()
    println("# cashapp/barber")
    val recipientReceiptSmsDocumentTemplateEN_US = DocumentTemplate(
        fields = mapOf(
            "subject" to "{{sender}} sent you {{amount}}",
            "headline" to "New transfer",
            "short_description" to "{{sender}} sent {{amount}}",
            "secondary_button_url" to "{{cancelUrl}}"
        ),
        source = RecipientReceipt::class,
        targets = setOf(TransactionalSmsDocument::class),
        locale = EN_US
    )

    val barbershop = BarbershopBuilder()
        .installDocumentTemplate<RecipientReceipt>(recipientReceiptSmsDocumentTemplateEN_US)
        .installDocument<TransactionalSmsDocument>()
        .build()

    // Get a Barber who knows how to render RecipientReceipt data into a TransactionalSmsDocument
    val recipientReceiptSms = barbershop.getBarber<RecipientReceipt, TransactionalSmsDocument>()
    val recept = RecipientReceipt(
        "Patrick", "14€", "http://example.com", Instant.now()
    )
    val transactionalSmsDocument: TransactionalSmsDocument = recipientReceiptSms.render(recept, EN_US)
    transactionalSmsDocument shouldBe TransactionalSmsDocument(
        "Patrick sent you 14€","New transfer", "Patrick sent 14€", null, null, null, "http://example.com"
    )

    shouldThrow("DocumentData [unregistered] and corresponding DocumentTemplate(s) are not installed in Barbershop") {
        barbershop.getBarber<UnregisteredDocumentData, TransactionalSmsDocument>()
    }

}

// Define DocumentData
data class RecipientReceipt(
    val sender: String,
    val amount: String,
    val cancelUrl: String,
    val deposit_expected_at: Instant
) : DocumentData

data class TransactionalSmsDocument(
    val subject: String,
    val headline: String,
    val short_description: String,
    val primary_button: String?,
    val primary_button_url: String?,
    val secondary_button: String?,
    val secondary_button_url: String?
) : Document

data class UnregisteredDocumentData(
    val id: Long
): DocumentData
