package com.example.data.models

enum class UserRole {
    CLIENT,
    ADMIN
}

enum class Brand(val displayName: String, val brandTag: String) {
    SAMSUNG("Samsung", "SAM"),
    APPLE("Apple iPhone", "IPH"),
    INFINIX("Infinix", "INF"),
    TECNO("Tecno", "TEC"),
    VIVO("Vivo", "VIV"),
    OPPO("Oppo", "OPP"),
    XIAOMI("Xiaomi / Redmi", "XIA"),
    REALME("Realme", "REA")
}

enum class DisplayCategory(val displayName: String, val urduName: String) {
    ALL("All Categories", "تمام کیٹیگریز"),
    LCD_PANEL("LCD Panels", "ایل سی ڈی پینلز"),
    ORIGINAL_OLED("Original OLED", "اوریجنل او ایل ای ڈی"),
    AMOLED("AMOLED", "امولیڈ ڈسپلے"),
    HIGH_COPY("High-Copy Screens", "ہائی کاپی اسکرینز"),
    DIGITIZER("Touch Digitizers", "ٹچ ڈیجیٹائزرز"),
    FLEX_CABLE("Flex Cables", "ڈسپلے فلیکس کیبلز")
}

enum class QualityGrade(val gradeName: String, val badgeColorHex: Long) {
    ORIGINAL_SERVICE_PACK("Original Service Pack", 0xFFD4AF37),
    ORIGINAL_PULL("100% Original Disassembled", 0xFF38BDF8),
    OLED_INCELL("OLED In-Cell High Bright", 0xFFA855F7),
    CROWN_HIGH_COPY("Crown High-Copy Premium", 0xFF10B981),
    GRADE_AAA("Grade AAA+ Precision", 0xFFF59E0B)
}

enum class PaymentStatus(val displayName: String, val badgeColorHex: Long) {
    PAID("Paid in Full", 0xFF10B981),
    UNPAID("Payment Pending", 0xFFEF4444),
    PARTIAL("Partial Advance", 0xFFF59E0B)
}

data class PriceTier(
    val tier1Qty: Int = 10,
    val tier1PricePkr: Int,
    val tier2Qty: Int = 50,
    val tier2PricePkr: Int,
    val tier3Qty: Int = 100,
    val tier3PricePkr: Int
) {
    fun getUnitPriceForQty(qty: Int): Int {
        return when {
            qty >= tier3Qty -> tier3PricePkr
            qty >= tier2Qty -> tier2PricePkr
            else -> tier1PricePkr
        }
    }
}

data class ProductItem(
    val id: String,
    val brand: Brand,
    val series: String,
    val title: String,
    val partCode: String,
    val compatibleModels: List<String>,
    val category: DisplayCategory,
    val grade: QualityGrade,
    val stockLocation: String = "In Stock - Hall Road",
    val stockCount: Int,
    val isAvailable: Boolean = true,
    val moq: Int = 10,
    val pricing: PriceTier,
    val brightnessNits: Int = 850,
    val refreshRate: String = "60Hz",
    val isIcTransferable: Boolean = true,
    val isWithFrame: Boolean = false,
    val warranty: String = "7-Day Checking Warranty",
    val isFastMoving: Boolean = false,
    val urduTitle: String = ""
)

data class CartItem(
    val product: ProductItem,
    val quantity: Int
) {
    val unitPrice: Int get() = product.pricing.getUnitPriceForQty(quantity)
    val totalPrice: Long get() = unitPrice.toLong() * quantity
}

enum class OrderStatus(val displayName: String, val badgeColorHex: Long) {
    PENDING("Order Received", 0xFFF59E0B),
    CONFIRMED("Confirmed by Jamal", 0xFF3B82F6),
    DISPATCHED("Dispatched via Cargo", 0xFF10B981),
    DELIVERED("Delivered to Shop", 0xFF6366F1)
}

data class PlacedOrder(
    val orderId: String,
    val customerName: String,
    val shopName: String,
    val customerPhone: String,
    val customerCity: String,
    val freightCarrier: String,
    val items: List<CartItem>,
    val grossSubtotal: Long,
    val tierDiscount: Long,
    val netTotal: Long,
    val paymentStatus: PaymentStatus = PaymentStatus.UNPAID,
    val status: OrderStatus = OrderStatus.PENDING,
    val timestamp: Long = System.currentTimeMillis()
)

data class KhataAccount(
    val shopName: String,
    val customerName: String,
    val customerPhone: String,
    val city: String,
    val totalBilledPkr: Long,
    val totalPaidPkr: Long,
    val pendingBalancePkr: Long,
    val lastTransactionTime: Long
)

data class WarrantyClaim(
    val claimId: String,
    val customerName: String,
    val shopName: String,
    val phone: String,
    val productTitle: String,
    val partCode: String,
    val quantityDefective: Int,
    val faultType: String,
    val details: String,
    val timestamp: Long = System.currentTimeMillis()
)

data class StockRequestAlert(
    val requestId: String,
    val productTitle: String,
    val partCode: String,
    val shopName: String,
    val customerPhone: String,
    val requestedQuantity: Int,
    val timestamp: Long = System.currentTimeMillis()
)

enum class AppLanguage {
    ENGLISH,
    URDU
}

enum class AppCurrency(val symbol: String, val exchangeRateToPkr: Double) {
    PKR("Rs.", 1.0),
    USD("$", 280.0)
}

enum class WholesaleTier(val tierName: String, val discountPercent: Int, val minMonthlyUnits: Int) {
    GOLD("Wholesale Gold Tier", 0, 10),
    PLATINUM("Wholesale Platinum VIP", 3, 50),
    DIAMOND("Distributor Diamond Partner", 6, 200)
}
