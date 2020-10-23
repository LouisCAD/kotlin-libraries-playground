package testing.mocking

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import testing.common.DataProvider
import testing.common.Element
import testing.common.Presenter
import testing.common.View

class Mockk {

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

}
