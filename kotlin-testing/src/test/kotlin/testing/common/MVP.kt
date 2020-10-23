package testing.common

interface View {
    fun displayItems(list: List<Element>)
    fun displayDetails(element: Element)
    fun displayError()
}

data class Element(val id: Int, val content: String)

interface DataProvider {
    fun getAll(): List<Element>
    fun getOne(id: Int): Element?
}

class Presenter(private val view: View, private val dataProvider: DataProvider) {
    fun start() {
        with(dataProvider.getAll()) {
            if (this.isNotEmpty()) {
                view.displayItems(this)
            } else {
                view.displayError()
            }
        }
    }

    fun getOne(id: Int) {
        dataProvider.getOne(id)?.let {
            view.displayDetails(it)
        }
    }
}
