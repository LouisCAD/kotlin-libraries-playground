package framework.mockk

import framework.common.DataProvider
import framework.common.Element
import framework.common.Presenter
import framework.common.View
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

class PresenterTest {

    val view: View = mockk(relaxUnitFun = true)


    @Test
    fun `given data exists when start presenter then display elements on view`() {
        val elements = listOf(
                Element(1, "first"),
                Element(2, "second")
        )

        val dataProvider = mockk<DataProvider>()

        every { dataProvider.getAll() } returns elements

        val presenter = Presenter(view, dataProvider)

        presenter.start()

        verify { view.displayItems(elements) }

    }

    @Test
    fun `given data load fail when start presenter then display error on view`() {
        val dataProvider = mockk<DataProvider>()

        every { dataProvider.getAll() } returns emptyList()

        val presenter = Presenter(view, dataProvider)

        presenter.start()

        verify { view.displayError() }
    }

    @Test
    fun `given element with id=1 exists when get one then display elements`() {
        val givenElement = Element(1, "first")

        val dataProvider = mockk<DataProvider>()

        every { dataProvider.getOne(1) } returns givenElement

        val presenter = Presenter(view, dataProvider)

        presenter.getOne(1)

        verify { view.displayDetails(givenElement) }
    }

    @Test
    fun `given element with id=3 don't exists when get one then display error`() {

        val givenElement: Element? = null

        val dataProvider = mockk<DataProvider>()

        every { dataProvider.getOne(any()) } returns givenElement

        val presenter = Presenter(view, dataProvider)

        presenter.getOne(3)

        verify { view.displayError() }
    }
}