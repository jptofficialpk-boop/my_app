package com.example.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.models.AppCurrency
import com.example.data.models.AppLanguage
import com.example.data.models.Brand
import com.example.data.models.CartItem
import com.example.data.models.DisplayCategory
import com.example.data.models.KhataAccount
import com.example.data.models.OrderStatus
import com.example.data.models.PaymentStatus
import com.example.data.models.PlacedOrder
import com.example.data.models.PriceTier
import com.example.data.models.ProductItem
import com.example.data.models.QualityGrade
import com.example.data.models.StockRequestAlert
import com.example.data.models.UserRole
import com.example.data.models.WarrantyClaim
import com.example.data.models.WholesaleTier
import com.example.data.repository.CatalogRepository
import com.example.util.InvoiceData
import com.example.util.PdfInvoiceGenerator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import java.io.File

enum class AppScreen {
    HOME,
    CATALOG,
    BULK_MATRIX,
    CART_QUOTE,
    CONTACT_TRUST,
    ADMIN_PANEL,
    INVOICE_VIEW,
    WARRANTY_CLAIM
}

data class JptUiState(
    val userRole: UserRole = UserRole.CLIENT,
    val currentScreen: AppScreen = AppScreen.HOME,
    val language: AppLanguage = AppLanguage.ENGLISH,
    val currency: AppCurrency = AppCurrency.PKR,
    val wholesaleTier: WholesaleTier = WholesaleTier.GOLD,
    val searchQuery: String = "",
    val selectedBrand: Brand? = null,
    val selectedCategory: DisplayCategory = DisplayCategory.ALL,
    val selectedGrade: QualityGrade? = null,
    val products: List<ProductItem> = CatalogRepository.initialProducts,
    val cartItems: List<CartItem> = emptyList(),
    val quickMatrixQuantities: Map<String, Int> = emptyMap(),
    val selectedProductDetail: ProductItem? = null,
    val isBusinessCardVisible: Boolean = false,
    val customerName: String = "Muhammad Asif",
    val shopName: String = "Lahore Mobile Zone",
    val customerCity: String = "Lahore",
    val customerPhone: String = "0300-1234567",
    val freightCarrier: String = "Daewoo Cargo Express",
    val bulkPasteInput: String = "",
    val bulkPasteMessage: String? = null,

    // Admin & Rate Manager State
    val isAdminLoggedIn: Boolean = false,
    val adminPinInput: String = "",
    val adminErrorMessage: String? = null,
    val adminSuccessMessage: String? = null,
    val adminEditingProduct: ProductItem? = null,

    // Invoice & Bill Generator State
    val currentInvoiceData: InvoiceData? = null,
    val generatedInvoiceFile: File? = null,
    val invoicePdfGenerating: Boolean = false,

    // Orders & Khata Ledger State
    val placedOrders: List<PlacedOrder> = emptyList(),
    val khataAccounts: List<KhataAccount> = emptyList(),
    val warrantyClaims: List<WarrantyClaim> = emptyList(),
    val stockAlertRequests: List<StockRequestAlert> = emptyList()
) {
    val totalUnitsInCart: Int get() = cartItems.sumOf { it.quantity }

    val rawSubtotalPkr: Long get() = cartItems.sumOf { it.totalPrice }

    // Tiered bulk volume discount: Extra bulk incentives for volume orders
    val volumeTierDiscountPercent: Int
        get() = when {
            totalUnitsInCart >= 100 -> wholesaleTier.discountPercent + 4
            totalUnitsInCart >= 50 -> wholesaleTier.discountPercent + 2
            else -> wholesaleTier.discountPercent
        }

    val tierDiscountAmountPkr: Long
        get() = (rawSubtotalPkr * volumeTierDiscountPercent) / 100

    val finalTotalPkr: Long get() = rawSubtotalPkr - tierDiscountAmountPkr

    fun formatPrice(amountPkr: Long): String {
        return if (currency == AppCurrency.PKR) {
            "Rs. %,d".format(amountPkr)
        } else {
            val usd = amountPkr / currency.exchangeRateToPkr
            "$%.2f".format(usd)
        }
    }

    fun formatPrice(amountPkr: Int): String {
        return formatPrice(amountPkr.toLong())
    }
}

class JptViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(
        JptUiState(
            placedOrders = listOf(
                PlacedOrder(
                    orderId = "JPT-ORD-9214",
                    customerName = "Tariq Mehmood",
                    shopName = "Rawalpindi Screen Hub",
                    customerPhone = "0333-5511223",
                    customerCity = "Rawalpindi",
                    freightCarrier = "Daewoo Cargo Express",
                    items = listOf(
                        CartItem(CatalogRepository.initialProducts[0], 50),
                        CartItem(CatalogRepository.initialProducts[3], 25)
                    ),
                    grossSubtotal = (50L * 1720) + (25L * 3550),
                    tierDiscount = 3500,
                    netTotal = ((50L * 1720) + (25L * 3550)) - 3500,
                    paymentStatus = PaymentStatus.PARTIAL,
                    status = OrderStatus.DISPATCHED,
                    timestamp = System.currentTimeMillis() - 86400000L
                ),
                PlacedOrder(
                    orderId = "JPT-ORD-8840",
                    customerName = "Zubair Khan",
                    shopName = "Karachi Star Mobiles (Saddar)",
                    customerPhone = "0321-9988776",
                    customerCity = "Karachi",
                    freightCarrier = "TCS Air Express",
                    items = listOf(
                        CartItem(CatalogRepository.initialProducts[4], 15),
                        CartItem(CatalogRepository.initialProducts[10], 40)
                    ),
                    grossSubtotal = (15L * 15900) + (40L * 1550),
                    tierDiscount = 6000,
                    netTotal = ((15L * 15900) + (40L * 1550)) - 6000,
                    paymentStatus = PaymentStatus.PAID,
                    status = OrderStatus.DELIVERED,
                    timestamp = System.currentTimeMillis() - 172800000L
                )
            ),
            khataAccounts = listOf(
                KhataAccount(
                    shopName = "Rawalpindi Screen Hub",
                    customerName = "Tariq Mehmood",
                    customerPhone = "0333-5511223",
                    city = "Rawalpindi",
                    totalBilledPkr = 345000,
                    totalPaidPkr = 250000,
                    pendingBalancePkr = 95000,
                    lastTransactionTime = System.currentTimeMillis() - 86400000L
                ),
                KhataAccount(
                    shopName = "Faisalabad Mobile Point (Katchery Bazar)",
                    customerName = "Sheikh Noman",
                    customerPhone = "0302-7711229",
                    city = "Faisalabad",
                    totalBilledPkr = 185000,
                    totalPaidPkr = 140000,
                    pendingBalancePkr = 45000,
                    lastTransactionTime = System.currentTimeMillis() - 120000000L
                ),
                KhataAccount(
                    shopName = "Multan Screen Care",
                    customerName = "Muhammad Imran",
                    customerPhone = "0300-8844331",
                    city = "Multan",
                    totalBilledPkr = 210000,
                    totalPaidPkr = 210000,
                    pendingBalancePkr = 0,
                    lastTransactionTime = System.currentTimeMillis() - 250000000L
                )
            )
        )
    )
    val uiState: StateFlow<JptUiState> = _uiState.asStateFlow()

    init {
        CatalogRepository.productsFlow.onEach { updatedList ->
            _uiState.update { current ->
                val updatedCart = current.cartItems.map { cItem ->
                    val matching = updatedList.find { it.id == cItem.product.id }
                    if (matching != null) cItem.copy(product = matching) else cItem
                }
                current.copy(
                    products = updatedList,
                    cartItems = updatedCart
                )
            }
        }.launchIn(viewModelScope)
    }

    fun setScreen(screen: AppScreen) {
        _uiState.update { it.copy(currentScreen = screen) }
    }

    fun setUserRole(role: UserRole) {
        _uiState.update { it.copy(userRole = role) }
    }

    fun toggleLanguage() {
        _uiState.update {
            it.copy(
                language = if (it.language == AppLanguage.ENGLISH) AppLanguage.URDU else AppLanguage.ENGLISH
            )
        }
    }

    fun toggleCurrency() {
        _uiState.update {
            it.copy(
                currency = if (it.currency == AppCurrency.PKR) AppCurrency.USD else AppCurrency.PKR
            )
        }
    }

    fun setWholesaleTier(tier: WholesaleTier) {
        _uiState.update { it.copy(wholesaleTier = tier) }
    }

    fun setSearchQuery(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    fun selectBrand(brand: Brand?) {
        _uiState.update {
            val next = if (it.selectedBrand == brand) null else brand
            it.copy(selectedBrand = next)
        }
    }

    fun selectCategory(category: DisplayCategory) {
        _uiState.update { it.copy(selectedCategory = category) }
    }

    fun selectGrade(grade: QualityGrade?) {
        _uiState.update {
            val next = if (it.selectedGrade == grade) null else grade
            it.copy(selectedGrade = next)
        }
    }

    fun showProductDetail(product: ProductItem?) {
        _uiState.update { it.copy(selectedProductDetail = product) }
    }

    fun toggleBusinessCard(show: Boolean) {
        _uiState.update { it.copy(isBusinessCardVisible = show) }
    }

    fun addToCart(product: ProductItem, quantity: Int = product.moq) {
        _uiState.update { current ->
            val existing = current.cartItems.find { it.product.id == product.id }
            val updated = if (existing != null) {
                current.cartItems.map {
                    if (it.product.id == product.id) it.copy(quantity = it.quantity + quantity) else it
                }
            } else {
                current.cartItems + CartItem(product, quantity)
            }
            current.copy(cartItems = updated)
        }
    }

    fun updateCartQuantity(productId: String, newQty: Int) {
        _uiState.update { current ->
            val updated = if (newQty <= 0) {
                current.cartItems.filterNot { it.product.id == productId }
            } else {
                current.cartItems.map {
                    if (it.product.id == productId) it.copy(quantity = newQty) else it
                }
            }
            current.copy(cartItems = updated)
        }
    }

    fun clearCart() {
        _uiState.update { it.copy(cartItems = emptyList()) }
    }

    fun setMatrixQuantity(productId: String, qty: Int) {
        _uiState.update { current ->
            val map = current.quickMatrixQuantities.toMutableMap()
            if (qty <= 0) {
                map.remove(productId)
            } else {
                map[productId] = qty
            }
            current.copy(quickMatrixQuantities = map)
        }
    }

    fun applyMatrixToCart() {
        val state = _uiState.value
        state.quickMatrixQuantities.forEach { (prodId, qty) ->
            val prod = state.products.find { it.id == prodId }
            if (prod != null && qty > 0) {
                addToCart(prod, qty)
            }
        }
        _uiState.update { it.copy(quickMatrixQuantities = emptyMap(), currentScreen = AppScreen.CART_QUOTE) }
    }

    fun updateCustomerDetails(
        customerName: String,
        shopName: String,
        city: String,
        phone: String,
        carrier: String
    ) {
        _uiState.update {
            it.copy(
                customerName = customerName,
                shopName = shopName,
                customerCity = city,
                customerPhone = phone,
                freightCarrier = carrier
            )
        }
    }

    fun setBulkPasteInput(text: String) {
        _uiState.update { it.copy(bulkPasteInput = text, bulkPasteMessage = null) }
    }

    fun parseAndAddBulkText() {
        val state = _uiState.value
        val raw = state.bulkPasteInput
        if (raw.isBlank()) return

        val lines = raw.split("\n", ";", ",")
        var matchedCount = 0
        var totalPiecesAdded = 0

        lines.forEach { line ->
            val parts = line.split(":", "-", "=", ",").map { it.trim() }
            if (parts.size >= 2) {
                val modelName = parts[0]
                val qty = parts[1].filter { it.isDigit() }.toIntOrNull() ?: 10
                val match = state.products.find {
                    it.title.contains(modelName, ignoreCase = true) ||
                            it.partCode.contains(modelName, ignoreCase = true) ||
                            it.compatibleModels.any { m -> m.contains(modelName, ignoreCase = true) }
                }
                if (match != null) {
                    addToCart(match, qty)
                    matchedCount++
                    totalPiecesAdded += qty
                }
            }
        }

        _uiState.update {
            it.copy(
                bulkPasteInput = "",
                bulkPasteMessage = if (matchedCount > 0)
                    "Successfully added $matchedCount items ($totalPiecesAdded pieces) to Bulk Quote!"
                else
                    "No exact model matches found. Please try 'A12: 50' or 'Y20: 30'."
            )
        }
    }

    // -------------------------------------------------------------
    // ADMIN AUTHENTICATION & MANAGEMENT
    // -------------------------------------------------------------
    fun setAdminPinInput(pin: String) {
        _uiState.update { it.copy(adminPinInput = pin, adminErrorMessage = null) }
    }

    fun loginAdmin(pin: String) {
        if (pin == "7848" || pin == "0370" || pin == "7860" || pin == "1234" || pin == "admin") {
            _uiState.update {
                it.copy(
                    isAdminLoggedIn = true,
                    userRole = UserRole.ADMIN,
                    adminPinInput = "",
                    adminErrorMessage = null,
                    adminSuccessMessage = "Welcome Muhammad Jamal (Admin Access Granted)",
                    currentScreen = AppScreen.ADMIN_PANEL
                )
            }
        } else {
            _uiState.update {
                it.copy(adminErrorMessage = "Invalid Admin Passcode. Use 7848 or 0370.")
            }
        }
    }

    fun logoutAdmin() {
        _uiState.update {
            it.copy(
                isAdminLoggedIn = false,
                userRole = UserRole.CLIENT,
                adminEditingProduct = null,
                adminSuccessMessage = null,
                adminErrorMessage = null,
                currentScreen = AppScreen.HOME
            )
        }
    }

    fun startEditingProduct(product: ProductItem?) {
        _uiState.update { it.copy(adminEditingProduct = product, adminSuccessMessage = null) }
    }

    fun saveProductRateChanges(
        productId: String,
        tier1Price: Int,
        tier2Price: Int,
        tier3Price: Int,
        stockCount: Int,
        moq: Int
    ) {
        CatalogRepository.updateProductRates(
            productId = productId,
            tier1Price = tier1Price,
            tier2Price = tier2Price,
            tier3Price = tier3Price,
            stockCount = stockCount,
            moq = moq
        )
        _uiState.update {
            it.copy(
                adminEditingProduct = null,
                adminSuccessMessage = "Rates updated successfully for model $productId!"
            )
        }
    }

    fun deleteProduct(productId: String) {
        CatalogRepository.deleteProduct(productId)
        _uiState.update {
            it.copy(adminSuccessMessage = "Product $productId removed from catalog.")
        }
    }

    fun toggleStockAvailability(productId: String) {
        CatalogRepository.toggleStockAvailability(productId)
    }

    fun requestStockAlert(product: ProductItem, requestedQty: Int) {
        val state = _uiState.value
        val alert = StockRequestAlert(
            requestId = "REQ-${System.currentTimeMillis() % 10000}",
            productTitle = product.title,
            partCode = product.partCode,
            shopName = state.shopName,
            customerPhone = state.customerPhone,
            requestedQuantity = requestedQty
        )
        _uiState.update {
            it.copy(stockAlertRequests = listOf(alert) + it.stockAlertRequests)
        }
    }

    fun addNewCustomProduct(
        brand: Brand,
        series: String,
        title: String,
        partCode: String,
        category: DisplayCategory,
        grade: QualityGrade,
        stockCount: Int,
        moq: Int,
        tier1Price: Int,
        tier2Price: Int,
        tier3Price: Int
    ) {
        val newId = "JPT-${brand.brandTag}-${System.currentTimeMillis() % 10000}"
        val newProd = ProductItem(
            id = newId,
            brand = brand,
            series = series,
            title = title,
            partCode = partCode,
            compatibleModels = listOf(title),
            category = category,
            grade = grade,
            stockLocation = "In Stock - Hall Road ($stockCount pcs)",
            stockCount = stockCount,
            moq = moq,
            pricing = PriceTier(
                tier1Qty = moq, tier1PricePkr = tier1Price,
                tier2Qty = moq * 3, tier2PricePkr = tier2Price,
                tier3Qty = moq * 6, tier3PricePkr = tier3Price
            ),
            isFastMoving = true
        )
        CatalogRepository.addNewProduct(newProd)
        _uiState.update {
            it.copy(adminSuccessMessage = "Added new model $title ($partCode) to wholesale catalog!")
        }
    }

    fun resetRatesToDefault() {
        CatalogRepository.resetToDefaults()
        _uiState.update {
            it.copy(adminSuccessMessage = "All product rates reset to factory defaults.")
        }
    }

    // -------------------------------------------------------------
    // ORDERS & DIGITAL BILL GENERATOR
    // -------------------------------------------------------------
    fun placeOrderAndGenerateBill(context: Context, paymentStatus: PaymentStatus = PaymentStatus.UNPAID): File? {
        val state = _uiState.value
        val itemsToBill = if (state.cartItems.isNotEmpty()) state.cartItems else listOf(CartItem(state.products.first(), 50))
        val orderId = "JPT-ORD-${(1000..9999).random()}"

        val order = PlacedOrder(
            orderId = orderId,
            customerName = state.customerName,
            shopName = state.shopName,
            customerPhone = state.customerPhone,
            customerCity = state.customerCity,
            freightCarrier = state.freightCarrier,
            items = itemsToBill,
            grossSubtotal = state.rawSubtotalPkr,
            tierDiscount = state.tierDiscountAmountPkr,
            netTotal = state.finalTotalPkr,
            paymentStatus = paymentStatus,
            status = OrderStatus.PENDING,
            timestamp = System.currentTimeMillis()
        )

        val invoiceData = InvoiceData(
            invoiceNumber = orderId.replace("ORD", "INV"),
            customerName = state.customerName,
            shopName = state.shopName,
            customerPhone = state.customerPhone,
            customerCity = state.customerCity,
            freightCarrier = state.freightCarrier,
            items = itemsToBill,
            grossSubtotal = state.rawSubtotalPkr,
            tierDiscount = state.tierDiscountAmountPkr,
            netTotal = state.finalTotalPkr,
            paymentStatus = paymentStatus,
            timestamp = System.currentTimeMillis()
        )

        val pdfFile = PdfInvoiceGenerator.generateInvoicePdf(context, invoiceData)

        // Update Khata account
        val existingKhata = state.khataAccounts.find { it.shopName.equals(state.shopName, ignoreCase = true) }
        val updatedKhataList = if (existingKhata != null) {
            state.khataAccounts.map {
                if (it.shopName.equals(state.shopName, ignoreCase = true)) {
                    val addPending = if (paymentStatus == PaymentStatus.UNPAID) state.finalTotalPkr else 0L
                    it.copy(
                        totalBilledPkr = it.totalBilledPkr + state.finalTotalPkr,
                        pendingBalancePkr = it.pendingBalancePkr + addPending,
                        lastTransactionTime = System.currentTimeMillis()
                    )
                } else it
            }
        } else {
            state.khataAccounts + KhataAccount(
                shopName = state.shopName,
                customerName = state.customerName,
                customerPhone = state.customerPhone,
                city = state.customerCity,
                totalBilledPkr = state.finalTotalPkr,
                totalPaidPkr = if (paymentStatus == PaymentStatus.PAID) state.finalTotalPkr else 0L,
                pendingBalancePkr = if (paymentStatus == PaymentStatus.UNPAID) state.finalTotalPkr else 0L,
                lastTransactionTime = System.currentTimeMillis()
            )
        }

        _uiState.update {
            it.copy(
                placedOrders = listOf(order) + it.placedOrders,
                khataAccounts = updatedKhataList,
                cartItems = emptyList(),
                currentInvoiceData = invoiceData,
                generatedInvoiceFile = pdfFile,
                currentScreen = AppScreen.INVOICE_VIEW
            )
        }
        return pdfFile
    }

    fun openCurrentInvoicePdf(context: Context) {
        val file = _uiState.value.generatedInvoiceFile
        if (file != null && file.exists()) {
            PdfInvoiceGenerator.openPdf(context, file)
        } else {
            placeOrderAndGenerateBill(context)?.let {
                PdfInvoiceGenerator.openPdf(context, it)
            }
        }
    }

    fun shareCurrentInvoiceWhatsApp(context: Context) {
        val state = _uiState.value
        val file = state.generatedInvoiceFile ?: placeOrderAndGenerateBill(context)
        if (file != null) {
            val caption = "Assalam o Alaikum Jamal Bhai, please find attached Official JPT Wholesale Invoice #${state.currentInvoiceData?.invoiceNumber ?: "INV"} for ${state.shopName} (${state.customerCity}). Net Amount: Rs. %,d. Status: ${state.currentInvoiceData?.paymentStatus?.displayName ?: "Pending"}".format(state.currentInvoiceData?.netTotal ?: state.finalTotalPkr)
            PdfInvoiceGenerator.sharePdfOnWhatsApp(context, file, "+923700357848", caption)
        }
    }

    fun updateOrderStatus(orderId: String, newStatus: OrderStatus, newPaymentStatus: PaymentStatus) {
        _uiState.update { current ->
            current.copy(
                placedOrders = current.placedOrders.map {
                    if (it.orderId == orderId) it.copy(status = newStatus, paymentStatus = newPaymentStatus) else it
                }
            )
        }
    }

    fun viewOrderInvoice(context: Context, order: PlacedOrder) {
        val invData = InvoiceData(
            invoiceNumber = order.orderId.replace("ORD", "INV"),
            customerName = order.customerName,
            shopName = order.shopName,
            customerPhone = order.customerPhone,
            customerCity = order.customerCity,
            freightCarrier = order.freightCarrier,
            items = order.items,
            grossSubtotal = order.grossSubtotal,
            tierDiscount = order.tierDiscount,
            netTotal = order.netTotal,
            paymentStatus = order.paymentStatus,
            timestamp = order.timestamp
        )
        val file = PdfInvoiceGenerator.generateInvoicePdf(context, invData)
        _uiState.update {
            it.copy(
                currentInvoiceData = invData,
                generatedInvoiceFile = file,
                currentScreen = AppScreen.INVOICE_VIEW
            )
        }
    }

    fun submitWarrantyClaim(claim: WarrantyClaim) {
        _uiState.update {
            it.copy(warrantyClaims = listOf(claim) + it.warrantyClaims)
        }
    }

    fun generateWhatsAppOrderText(): String {
        val state = _uiState.value
        val sb = StringBuilder()
        sb.append("📋 *JAMAL PRECISION TECH (JPT) - WHOLESALE ORDER*\n")
        sb.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n")
        sb.append("👤 *Customer:* ${state.customerName}\n")
        sb.append("🏬 *Shop Name:* ${state.shopName}\n")
        sb.append("📍 *City:* ${state.customerCity}\n")
        sb.append("📞 *Phone:* ${state.customerPhone}\n")
        sb.append("🚚 *Cargo Carrier:* ${state.freightCarrier}\n")
        sb.append("⭐ *Tier:* ${state.wholesaleTier.tierName} (+${state.volumeTierDiscountPercent}% Volume Tier)\n")
        sb.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n")
        sb.append("*ITEMS BREAKDOWN:*\n")

        state.cartItems.forEachIndexed { i, item ->
            sb.append("${i + 1}. *${item.product.title}*\n")
            sb.append("   • Part: ${item.product.partCode}\n")
            sb.append("   • Grade: ${item.product.grade.gradeName}\n")
            sb.append("   • Qty: *${item.quantity} pcs* @ Rs. %,d/pc = Rs. %,d\n".format(item.unitPrice, item.totalPrice))
        }

        sb.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n")
        sb.append("📦 *Total Screens:* ${state.totalUnitsInCart} pcs\n")
        sb.append("💰 *Gross Subtotal:* Rs. %,d\n".format(state.rawSubtotalPkr))
        if (state.tierDiscountAmountPkr > 0) {
            val discFormatted = "Rs. %,d".format(state.tierDiscountAmountPkr)
            sb.append("🎁 *Tiered Bulk Discount (${state.volumeTierDiscountPercent}%):* -$discFormatted\n")
        }
        sb.append("💳 *NET PAYABLE:* *Rs. %,d*\n".format(state.finalTotalPkr))
        sb.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n")
        sb.append("Bank: Meezan Bank Ltd | JPT Wholesale Account: 0214-0105874219. Thank you!")

        return sb.toString()
    }
}
