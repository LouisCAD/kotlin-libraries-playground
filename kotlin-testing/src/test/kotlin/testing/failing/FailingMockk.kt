package testing.failing

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import testing.common.DataProvider
import testing.common.Element
import testing.common.Presenter
import testing.common.View

class FailingMockk {

    val view: View = mockk(relaxUnitFun = true)

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
