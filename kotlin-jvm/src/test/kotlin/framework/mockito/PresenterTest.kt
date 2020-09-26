package framework.mockito

import framework.common.DataProvider
import framework.common.Element
import framework.common.Presenter
import framework.common.View
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class PresenterTest {

    @Mock
    lateinit var view: View

    @Mock
    lateinit var dataProvider: DataProvider

    @Test
    fun `given data exists when start presenter then display elements on view`() {
        val elements = listOf(
                Element(1, "first"),
                Element(2, "second")
        )

        `when`(dataProvider.getAll()).thenReturn(elements)

        val presenter = Presenter(view, dataProvider)

        presenter.start()

        verify(view).displayItems(elements)

    }

    @Test
    fun `given data load fail when start presenter then display error on view`() {

        `when`(dataProvider.getAll()).thenReturn(emptyList())

        val presenter = Presenter(view, dataProvider)

        presenter.start()

        verify(view).displayError()
    }

    @Test
    fun `given element with id=1 exists when get one then display elements`() {
        val givenElement = Element(1, "first")

        `when`(dataProvider.getOne(1)).thenReturn(givenElement)

        val presenter = Presenter(view, dataProvider)

        presenter.getOne(1)

        verify(view).displayDetails(givenElement)
    }

    @Test
    fun `given element with id=3 don't exists when get one then display error`() {

        val givenElement: Element? = null

        `when`(dataProvider.getOne(3)).thenReturn(givenElement)

        val presenter = Presenter(view, dataProvider)

        presenter.getOne(3)

        verify(view).displayError()
    }
}