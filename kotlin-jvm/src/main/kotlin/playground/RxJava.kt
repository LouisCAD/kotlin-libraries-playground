@file:Suppress("PackageDirectoryMismatch")

package playground.rxjava

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 * ReactiveX/RxJava - a library for composing asynchronous and event-based programs using observable sequences for the Java VM.
 *
 * -  [ReactiveX/RxJava: GitHub](https://github.com/ReactiveX/RxJava)
 * - [JavaDoc](http://reactivex.io/RxJava/3.x/javadoc/)
 */

fun main() {
    println()
    println("# RxJava – Reactive Extensions for the JVM – a library for composing asynchronous and event-based programs using observable sequences for the Java VM.")

    onlyAdultsObservable(persons()).subscribe(
        { v -> println("Received: $v") },
        { e -> println("Error: $e") },
        { println("Complete!") }
    )

    personsUnder30()
    concurrentPersonNames()
    numbers()
}

private fun onlyAdultsObservable(persons: List<Person>) =
    Observable.create<Person> { emitter ->
        persons.forEach { person ->
            if (person.age < 18) {
                emitter.onError(Exception("The person is underage"))
            }
            emitter.onNext(person)
        }
        emitter.onComplete()
    }

private fun numbers() {
    Observable.range(1, 10)
        .map { e -> e * 10 }
        .subscribeOn(Schedulers.newThread())
        .blockingSubscribe(
            { value -> println("Recived: $value") },
            { error -> println("Error: $error") },
            { println("Completed!") }
        )
}

private fun personsUnder30() {
    Observable.fromIterable(persons())
        .filter { p -> p.age < 30 }
        .map { it.name.toUpperCase() }
        .subscribe(
            { value -> println("Received: $value") },
            { error -> println("Error: $error") },
            { println("Completed!") }
        )
}

private fun concurrentPersonNames() {
    val sherlock = Person("Sherlock", "Holmes", 20)
    val mycroft = Person("Mycroft", "Holmes", 30)
    val john = Person("John", "Watson", 25)

    Observable.just(sherlock, mycroft, john)
        .flatMap { p -> Observable.just(p.name.toUpperCase()).subscribeOn(Schedulers.io()) }
        .subscribe(
            { value -> println("Recived: $value") },
            { error -> println("Error: $error") },
            { println("Completed!") }
        )
}

private fun persons() = listOf(
    Person("Sherlock", "Holmes", 20),
    Person("Mycroft", "Holmes", 30),
    Person("John", "Watson", 17)
)

data class Person(var name: String, var lastName: String, var age: Int)
