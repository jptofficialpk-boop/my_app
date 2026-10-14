package com.example

import com.example.data.models.Brand
import com.example.data.models.CartItem
import com.example.data.models.DisplayCategory
import com.example.data.models.PaymentStatus
import com.example.data.models.PlacedOrder
import com.example.data.models.PriceTier
import com.example.data.models.ProductItem
import com.example.data.models.QualityGrade
import com.example.data.models.UserRole
import com.example.data.repository.CatalogRepository
import com.example.ui.JptUiState
import com.example.ui.JptViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class JptWholesaleUnitTest {

    @Test
    fun testTieredPricingCalculations() {
        val tier = PriceTier(
            tier1Qty = 10,
            tier1PricePkr = 3200,
            tier2Qty = 50,
            tier2PricePkr = 2950,
            tier3Qty = 100,
            tier3PricePkr = 2750
        )

        assertEquals(3200, tier.getUnitPriceForQty(10))
        assertEquals(3200, tier.getUnitPriceForQty(49))
        assertEquals(2950, tier.getUnitPriceForQty(50))
        assertEquals(2950, tier.getUnitPriceForQty(99))
        assertEquals(2750, tier.getUnitPriceForQty(100))
        assertEquals(2750, tier.getUnitPriceForQty(250))
    }

    @Test
    fun testCartItemCalculation() {
        val testProduct = ProductItem(
            id = "SAM-A12-TEST",
            brand = Brand.SAMSUNG,
            series = "Galaxy A Series",
            title = "Samsung A12 LCD Screen",
            partCode = "GH82-24322A",
            compatibleModels = listOf("A125F", "A127F"),
            category = DisplayCategory.LCD_PANEL,
            grade = QualityGrade.GRADE_AAA,
            stockLocation = "In Stock - Hall Road",
            stockCount = 150,
            moq = 10,
            pricing = PriceTier(
                tier1Qty = 10,
                tier1PricePkr = 3000,
                tier2Qty = 50,
                tier2PricePkr = 2800,
                tier3Qty = 100,
                tier3PricePkr = 2600
            )
        )

        // 20 pieces -> tier 1
        val cartItem1 = CartItem(product = testProduct, quantity = 20)
        assertEquals(3000, cartItem1.unitPrice)
        assertEquals(60000L, cartItem1.totalPrice)

        // 50 pieces -> tier 2
        val cartItem2 = CartItem(product = testProduct, quantity = 50)
        assertEquals(2800, cartItem2.unitPrice)
        assertEquals(140000L, cartItem2.totalPrice)
    }

    @Test
    fun testVolumeDiscountCalculation() {
        val testProduct = ProductItem(
            id = "IPH-11-TEST",
            brand = Brand.APPLE,
            series = "iPhone 11",
            title = "iPhone 11 OLED InCell",
            partCode = "IPH11-OLED",
            compatibleModels = listOf("A2111", "A2223"),
            category = DisplayCategory.ORIGINAL_OLED,
            grade = QualityGrade.OLED_INCELL,
            stockLocation = "In Stock - Hall Road",
            stockCount = 80,
            moq = 5,
            pricing = PriceTier(
                tier1Qty = 5,
                tier1PricePkr = 4500,
                tier2Qty = 25,
                tier2PricePkr = 4200,
                tier3Qty = 50,
                tier3PricePkr = 3900
            )
        )

        val cart = listOf(CartItem(product = testProduct, quantity = 60))
        val state = JptUiState(cartItems = cart)

        assertEquals(60, state.totalUnitsInCart)
        // 60 pieces gives volumeTierDiscountPercent: wholesaleTier.discountPercent(0) + 2%
        assertEquals(2, state.volumeTierDiscountPercent)
        assertEquals(60 * 3900L, state.rawSubtotalPkr)
        val expectedDiscount = (60 * 3900L * 2) / 100
        assertEquals(expectedDiscount, state.tierDiscountAmountPkr)
        assertEquals((60 * 3900L) - expectedDiscount, state.finalTotalPkr)
    }

    @Test
    fun testAdminPinVerification() {
        val viewModel = JptViewModel()
        assertFalse(viewModel.uiState.value.isAdminLoggedIn)

        // Invalid PIN
        viewModel.loginAdmin("0000")
        assertFalse(viewModel.uiState.value.isAdminLoggedIn)
        assertNotNull(viewModel.uiState.value.adminErrorMessage)

        // Valid owner PIN (7848)
        viewModel.loginAdmin("7848")
        assertTrue(viewModel.uiState.value.isAdminLoggedIn)
        assertEquals(UserRole.ADMIN, viewModel.uiState.value.userRole)
    }

    @Test
    fun testCatalogRepositoryRateAdjustment() {
        val initialCount = CatalogRepository.initialProducts.size
        assertTrue(initialCount > 0)

        val firstProd = CatalogRepository.initialProducts.first()
        val originalPrice = firstProd.pricing.tier1PricePkr

        // Update rates
        CatalogRepository.updateProductRates(
            productId = firstProd.id,
            tier1Price = 9999,
            tier2Price = 9500,
            tier3Price = 9000,
            stockCount = 333,
            moq = 15
        )

        val updated = CatalogRepository.productsFlow.value.find { it.id == firstProd.id }
        assertNotNull(updated)
        assertEquals(9999, updated?.pricing?.tier1PricePkr)
        assertEquals(9500, updated?.pricing?.tier2PricePkr)
        assertEquals(333, updated?.stockCount)
        assertEquals(15, updated?.moq)
    }
}
