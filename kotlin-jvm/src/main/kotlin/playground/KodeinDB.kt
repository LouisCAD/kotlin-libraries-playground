@file:Suppress("PackageDirectoryMismatch")

package playground.kodein.db

import kotlinx.serialization.Serializable
import org.kodein.db.*
import org.kodein.db.impl.open
import org.kodein.db.model.ModelDB
import org.kodein.db.model.orm.HasMetadata
import org.kodein.db.model.orm.Metadata
import org.kodein.db.orm.kotlinx.KotlinxSerializer
import org.kodein.memory.io.ReadMemory
import playground.shouldBe
import playground.shouldThrow


/**
 * Kodein DB : painless embedded NoSQL database
 *
- [GitHub](https://github.com/Kodein-Framework/Kodein-DB)
- [Official Website](https://docs.kodein.org/kodein-db)
 */
fun main() {
    val db = DB.open("users.db",
        KotlinxSerializer {
            +User.serializer()
            +City.serializer()
        }
    )
    db.on<City>().register(UnsupportedCountryListener())
    val keyParis = db.put(City("Paris", "France"))
    val keyBerlin = db.put(City("Berlin", "Germany"))
    db[keyBerlin]?.city shouldBe "Berlin"

    shouldThrow(message = "Invalid country=USA - supported: [France, Germany]") {
        db.put(City("New York", country = "USA"))
    }

    db.put(User("Salomon", "Brys", keyParis, "kodein-koder"))
    db.put(User("Jean-Michel", "Fayard", keyBerlin, "jmfayard"))

    db.find<User>().all().useModels { userSequence ->
        val users = userSequence.toList().map { "${it.firstName} ${it.lastName}" }.sorted()
        users shouldBe listOf("Jean-Michel Fayard", "Salomon Brys")
    }
}

@Serializable
internal data class User(
    val firstName: String,
    val lastName: String,
    val city: Key<City>,
    override val id: String
) : Metadata

@Serializable
internal data class City(
    val city: String,
    val country: String
) : HasMetadata {
    override fun getMetadata(db: ModelDB, vararg options: Options.Write): Metadata =
        Metadata(city, "country" to country)
}

internal class UnsupportedCountryListener : DBListener<City> {
    private val suppoortedCountries = listOf("France", "Germany")

    override fun didPut(
        model: City,
        key: Key<City>,
        typeName: ReadMemory,
        metadata: Metadata,
        size: Int,
        options: Array<out Options.Write>
    ) {
        require(model.country in suppoortedCountries) { "Invalid country=${model.country} - supported: $suppoortedCountries" }
    }

}
