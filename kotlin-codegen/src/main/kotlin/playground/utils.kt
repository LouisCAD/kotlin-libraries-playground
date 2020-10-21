package playground


infix fun <T : Any?> T.shouldBe(expected: T) {
    println("Test: $expected")
    check(this == expected) { "Test failed!\nWanted: <<$expected>>\nGot:    <<$this>>\n" }
}

infix fun <T : Any?> Pair<T, String>.shouldBe(expected: T) {
    println("Test[$second]: $expected")
    check(this.first == expected) { "Test failed\n$second:\nWanted: <<$expected>>\nGot:    <<$first>>\n" }
}

infix fun <T : Any?> T.test(name: String): Pair<T, String> =
    Pair(this, name)

infix fun <T : Any?> List<T>.shouldHaveSameElementsAs(expected: List<T>) {
    println("Test: $expected")
    val aNotB = this.filter { it !in expected }
    val bNotA = expected.filter { it !in this }
    val presentInBoth = this.filter { it in expected }
    check(aNotB.isEmpty() && bNotA.isEmpty()) {
        """Test failed!
Both lists have:   $presentInBoth
Only actual has:   $aNotB
only expected has: $bNotA
"""
    }
}
