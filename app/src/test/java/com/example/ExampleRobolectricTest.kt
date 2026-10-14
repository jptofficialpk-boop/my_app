package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.models.Brand
import com.example.data.models.DisplayCategory
import com.example.data.repository.CatalogRepository
import com.example.ui.JptViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

    @Test
    fun `read string from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("Jamal Precision Tech", appName)
    }

    @Test
    fun `test wholesale catalog filtering`() {
        val allProducts = CatalogRepository.sampleProducts
        assertTrue(allProducts.isNotEmpty())

        val samsungProducts = CatalogRepository.filterProducts(brand = Brand.SAMSUNG)
        assertTrue(samsungProducts.all { it.brand == Brand.SAMSUNG })

        val oledProducts = CatalogRepository.filterProducts(category = DisplayCategory.ORIGINAL_OLED)
        assertTrue(oledProducts.all { it.category == DisplayCategory.ORIGINAL_OLED })
    }

    @Test
    fun `test wholesale quote generator and volume tier pricing`() {
        val viewModel = JptViewModel()
        val a12 = CatalogRepository.sampleProducts.first { it.id == "JPT-SAM-A125" }

        // MOQ is 10 pcs
        viewModel.addToCart(a12, 10)
        assertEquals(10, viewModel.uiState.value.totalUnitsInCart)
        assertEquals(1850 * 10L, viewModel.uiState.value.rawSubtotalPkr)

        // Volume tier discount when reaching 50 pcs
        viewModel.updateCartQuantity(a12.id, 50)
        assertEquals(50, viewModel.uiState.value.totalUnitsInCart)
        assertEquals(1720 * 50L, viewModel.uiState.value.rawSubtotalPkr)

        val whatsAppText = viewModel.generateWhatsAppOrderText()
        assertTrue(whatsAppText.contains("JAMAL PRECISION TECH"))
        assertTrue(whatsAppText.contains("50 pcs"))
    }
}
