package testing.failing

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import testing.common.DataProvider
import testing.common.Element
import testing.common.Presenter
import testing.common.View
import org.junit.jupiter.api.Test

class FailingMockitoKotlin {

    val view: View = mock()

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
