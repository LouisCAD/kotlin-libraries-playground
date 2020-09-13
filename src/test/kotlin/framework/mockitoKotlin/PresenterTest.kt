package framework.mockitoKotlin

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import framework.common.DataProvider
import framework.common.Element
import framework.common.Presenter
import framework.common.View
import org.junit.jupiter.api.Test

class PresenterTest {

    val view: View = mock()

    @Test
    fun `given data exists when start presenter then display elements on view`() {
        val elements = listOf(
                Element(1, "first"),
                Element(2, "second")
        )

        val dataProvider: DataProvider = mock {
            on { getAll() } doReturn elements
        }

        val presenter = Presenter(view, dataProvider)

        presenter.start()

        verify(view).displayItems(elements)

    }

    @Test
    fun `given data load fail when start presenter then display error on view`() {
        val dataProvider: DataProvider = mock {
            on { getAll() } doReturn emptyList<Element>()
        }

        val presenter = Presenter(view, dataProvider)

        presenter.start()

        verify(view).displayError()
    }

    @Test
    fun `given element with id=1 exists when get one then display elements`() {
        val givenElement = Element(1, "first")

        val dataProvider: DataProvider = mock {
            on { getOne(1) } doReturn givenElement
        }

        val presenter = Presenter(view, dataProvider)

        presenter.getOne(1)

        verify(view).displayDetails(givenElement)
    }

    @Test
    fun `given element with id=3 don't exists when get one then display error`() {

        val givenElement: Element? = null

        val dataProvider: DataProvider = mock {
            on { getOne(3) } doReturn givenElement
        }

        val presenter = Presenter(view, dataProvider)

        presenter.getOne(3)

        verify(view).displayError()
    }
}