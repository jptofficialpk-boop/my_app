package com.example.data.repository

import com.example.data.models.Brand
import com.example.data.models.DisplayCategory
import com.example.data.models.PriceTier
import com.example.data.models.ProductItem
import com.example.data.models.QualityGrade

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

object CatalogRepository {

    val initialProducts: List<ProductItem> = listOf(
        ProductItem(
            id = "JPT-SAM-A125",
            brand = Brand.SAMSUNG,
            series = "Galaxy A Series",
            title = "Samsung Galaxy A12 (A125F) Complete LCD Display Assembly",
            partCode = "JPT-SM-A12-INCELL",
            compatibleModels = listOf("Galaxy A12", "SM-A125F", "SM-A127F", "Galaxy M12"),
            category = DisplayCategory.LCD_PANEL,
            grade = QualityGrade.CROWN_HIGH_COPY,
            stockLocation = "In Stock - Hall Road (540+ pcs)",
            stockCount = 540,
            moq = 10,
            pricing = PriceTier(
                tier1Qty = 10, tier1PricePkr = 1850,
                tier2Qty = 50, tier2PricePkr = 1720,
                tier3Qty = 100, tier3PricePkr = 1620
            ),
            brightnessNits = 550,
            refreshRate = "60Hz",
            isIcTransferable = true,
            isWithFrame = false,
            warranty = "7-Day Checking Warranty (Original Flex)",
            isFastMoving = true,
            urduTitle = "سام سنگ گلیکسی اے 12 ایل سی ڈی ڈسپلے اسمبلی"
        ),
        ProductItem(
            id = "JPT-SAM-A32-AMOLED",
            brand = Brand.SAMSUNG,
            series = "Galaxy A Series",
            title = "Samsung Galaxy A32 4G Super AMOLED 90Hz Display Module",
            partCode = "JPT-SM-A325-OLED",
            compatibleModels = listOf("Galaxy A32 4G", "SM-A325F", "SM-A325M"),
            category = DisplayCategory.AMOLED,
            grade = QualityGrade.ORIGINAL_SERVICE_PACK,
            stockLocation = "In Stock - Hall Road (180+ pcs)",
            stockCount = 180,
            moq = 5,
            pricing = PriceTier(
                tier1Qty = 5, tier1PricePkr = 6800,
                tier2Qty = 25, tier2PricePkr = 6450,
                tier3Qty = 50, tier3PricePkr = 6150
            ),
            brightnessNits = 800,
            refreshRate = "90Hz",
            isIcTransferable = true,
            isWithFrame = true,
            warranty = "7-Day Checking Warranty (Original Flex)",
            isFastMoving = true,
            urduTitle = "سام سنگ اے 32 سپر امولیڈ 90 ہرٹز ڈسپلے"
        ),
        ProductItem(
            id = "JPT-SAM-S23U-OLED",
            brand = Brand.SAMSUNG,
            series = "Galaxy S Flagship",
            title = "Samsung Galaxy S23 Ultra Dynamic AMOLED 2X Curved Service Pack",
            partCode = "JPT-SM-S918-SP",
            compatibleModels = listOf("Galaxy S23 Ultra", "SM-S918B", "SM-S918U"),
            category = DisplayCategory.ORIGINAL_OLED,
            grade = QualityGrade.ORIGINAL_SERVICE_PACK,
            stockLocation = "In Stock - Hall Road (45 pcs)",
            stockCount = 45,
            moq = 2,
            pricing = PriceTier(
                tier1Qty = 2, tier1PricePkr = 48500,
                tier2Qty = 10, tier2PricePkr = 46000,
                tier3Qty = 20, tier3PricePkr = 44500
            ),
            brightnessNits = 1750,
            refreshRate = "120Hz LTPO",
            isIcTransferable = true,
            isWithFrame = true,
            warranty = "Original Service Pack Testing Warranty",
            isFastMoving = false,
            urduTitle = "سام سنگ ایس 23 الٹرا ڈائنامک امولیڈ سروس پیک"
        ),
        ProductItem(
            id = "JPT-IPH-11-INCELL",
            brand = Brand.APPLE,
            series = "iPhone 11 Series",
            title = "Apple iPhone 11 In-Cell High Color Gamut LCD Display Touch",
            partCode = "JPT-IP11-INCELL-PRO",
            compatibleModels = listOf("iPhone 11", "A2111", "A2223", "A2221"),
            category = DisplayCategory.LCD_PANEL,
            grade = QualityGrade.CROWN_HIGH_COPY,
            stockLocation = "In Stock - Hall Road (620+ pcs)",
            stockCount = 620,
            moq = 10,
            pricing = PriceTier(
                tier1Qty = 10, tier1PricePkr = 3850,
                tier2Qty = 50, tier2PricePkr = 3550,
                tier3Qty = 100, tier3PricePkr = 3300
            ),
            brightnessNits = 625,
            refreshRate = "60Hz TrueTone",
            isIcTransferable = true,
            isWithFrame = true,
            warranty = "7-Day Checking Warranty",
            isFastMoving = true,
            urduTitle = "آئی فون 11 ان سیل ہائی کلر ایل سی ڈی"
        ),
        ProductItem(
            id = "JPT-IPH-13PM-OLED",
            brand = Brand.APPLE,
            series = "iPhone 13 Series",
            title = "Apple iPhone 13 Pro Max Super Retina XDR Hard OLED Screen",
            partCode = "JPT-IP13PM-OLED-IC",
            compatibleModels = listOf("iPhone 13 Pro Max", "A2484", "A2641", "A2644"),
            category = DisplayCategory.ORIGINAL_OLED,
            grade = QualityGrade.OLED_INCELL,
            stockLocation = "In Stock - Hall Road (110 pcs)",
            stockCount = 110,
            moq = 5,
            pricing = PriceTier(
                tier1Qty = 5, tier1PricePkr = 16800,
                tier2Qty = 20, tier2PricePkr = 15900,
                tier3Qty = 50, tier3PricePkr = 15200
            ),
            brightnessNits = 1200,
            refreshRate = "120Hz ProMotion Ready",
            isIcTransferable = true,
            isWithFrame = true,
            warranty = "7-Day Checking Warranty (IC Swap Allowed)",
            isFastMoving = true,
            urduTitle = "آئی فون 13 پرو میکس سپر ریٹینا ایکس ڈی آر او ایل ای ڈی"
        ),
        ProductItem(
            id = "JPT-IPH-14P-OLED",
            brand = Brand.APPLE,
            series = "iPhone 14 Series",
            title = "Apple iPhone 14 Pro Dynamic Island OLED Assembly (Original IC)",
            partCode = "JPT-IP14P-OLED",
            compatibleModels = listOf("iPhone 14 Pro", "A2650", "A2889"),
            category = DisplayCategory.ORIGINAL_OLED,
            grade = QualityGrade.ORIGINAL_PULL,
            stockLocation = "In Stock - Hall Road (75 pcs)",
            stockCount = 75,
            moq = 3,
            pricing = PriceTier(
                tier1Qty = 3, tier1PricePkr = 27500,
                tier2Qty = 10, tier2PricePkr = 26200,
                tier3Qty = 25, tier3PricePkr = 25000
            ),
            brightnessNits = 2000,
            refreshRate = "120Hz ProMotion",
            isIcTransferable = true,
            isWithFrame = true,
            warranty = "Original Pull 100% Tested Guarantee",
            isFastMoving = false,
            urduTitle = "آئی فون 14 پرو ڈائنامک آئی لینڈ او ایل ای ڈی"
        ),
        ProductItem(
            id = "JPT-INF-HOT10-LCD",
            brand = Brand.INFINIX,
            series = "Infinix Hot Series",
            title = "Infinix Hot 10 / Hot 10 Play (X682 / X688) LCD Touch Digitizer",
            partCode = "JPT-INF-H10P-LCD",
            compatibleModels = listOf("Infinix Hot 10", "Hot 10 Play", "X682", "X688B"),
            category = DisplayCategory.LCD_PANEL,
            grade = QualityGrade.CROWN_HIGH_COPY,
            stockLocation = "In Stock - Hall Road (850+ pcs)",
            stockCount = 850,
            moq = 10,
            pricing = PriceTier(
                tier1Qty = 10, tier1PricePkr = 1650,
                tier2Qty = 50, tier2PricePkr = 1520,
                tier3Qty = 100, tier3PricePkr = 1420
            ),
            brightnessNits = 480,
            refreshRate = "60Hz",
            isIcTransferable = false,
            isWithFrame = false,
            warranty = "7-Day Checking Warranty",
            isFastMoving = true,
            urduTitle = "انفنکس ہاٹ 10 / ہاٹ 10 پلے ایل سی ڈی ٹچ پینل"
        ),
        ProductItem(
            id = "JPT-INF-NOTE30-LCD",
            brand = Brand.INFINIX,
            series = "Infinix Note Series",
            title = "Infinix Note 30 (X6833B) 120Hz FHD+ High-Speed LCD Display",
            partCode = "JPT-INF-N30-120HZ",
            compatibleModels = listOf("Infinix Note 30", "Note 30 5G", "X6833", "X6711"),
            category = DisplayCategory.LCD_PANEL,
            grade = QualityGrade.GRADE_AAA,
            stockLocation = "In Stock - Hall Road (320 pcs)",
            stockCount = 320,
            moq = 10,
            pricing = PriceTier(
                tier1Qty = 10, tier1PricePkr = 2650,
                tier2Qty = 50, tier2PricePkr = 2480,
                tier3Qty = 100, tier3PricePkr = 2350
            ),
            brightnessNits = 580,
            refreshRate = "120Hz",
            isIcTransferable = false,
            isWithFrame = false,
            warranty = "7-Day Checking Warranty",
            isFastMoving = true,
            urduTitle = "انفنکس نوٹ 30 120 ہرٹز ایف ایچ ڈی ڈسپلے"
        ),
        ProductItem(
            id = "JPT-TEC-SPARK8-LCD",
            brand = Brand.TECNO,
            series = "Tecno Spark Series",
            title = "Tecno Spark 8 / Spark 8C (KG5 / KG5k) Touch Screen Assembly",
            partCode = "JPT-TEC-SP8-LCD",
            compatibleModels = listOf("Tecno Spark 8", "Spark 8C", "KG5", "KG5k"),
            category = DisplayCategory.LCD_PANEL,
            grade = QualityGrade.CROWN_HIGH_COPY,
            stockLocation = "In Stock - Hall Road (490 pcs)",
            stockCount = 490,
            moq = 10,
            pricing = PriceTier(
                tier1Qty = 10, tier1PricePkr = 1580,
                tier2Qty = 50, tier2PricePkr = 1450,
                tier3Qty = 100, tier3PricePkr = 1380
            ),
            brightnessNits = 480,
            refreshRate = "90Hz",
            isIcTransferable = false,
            isWithFrame = false,
            warranty = "7-Day Checking Warranty",
            isFastMoving = true,
            urduTitle = "ٹیکنو اسپارک 8 / 8 سی ٹچ اسکرین اسمبلی"
        ),
        ProductItem(
            id = "JPT-TEC-CAMON20-OLED",
            brand = Brand.TECNO,
            series = "Tecno Camon Series",
            title = "Tecno Camon 20 Pro 5G AMOLED Display with In-Display Fingerprint",
            partCode = "JPT-TEC-C20P-OLED",
            compatibleModels = listOf("Tecno Camon 20", "Camon 20 Pro 5G", "CK7n", "CK8n"),
            category = DisplayCategory.AMOLED,
            grade = QualityGrade.ORIGINAL_SERVICE_PACK,
            stockLocation = "In Stock - Hall Road (140 pcs)",
            stockCount = 140,
            moq = 5,
            pricing = PriceTier(
                tier1Qty = 5, tier1PricePkr = 5900,
                tier2Qty = 20, tier2PricePkr = 5600,
                tier3Qty = 50, tier3PricePkr = 5300
            ),
            brightnessNits = 750,
            refreshRate = "120Hz",
            isIcTransferable = true,
            isWithFrame = true,
            warranty = "7-Day Checking Warranty (Original Flex)",
            isFastMoving = false,
            urduTitle = "ٹیکنو کیمون 20 پرو امولیڈ فنگر پرنٹ ڈسپلے"
        ),
        ProductItem(
            id = "JPT-VIV-Y20-LCD",
            brand = Brand.VIVO,
            series = "Vivo Y Series",
            title = "Vivo Y20 / Y20s / Y12s (V2029 / V2027) Complete LCD Assembly",
            partCode = "JPT-VIV-Y20-CROWN",
            compatibleModels = listOf("Vivo Y20", "Vivo Y20s", "Vivo Y12s", "Vivo Y20G"),
            category = DisplayCategory.LCD_PANEL,
            grade = QualityGrade.CROWN_HIGH_COPY,
            stockLocation = "In Stock - Hall Road (720+ pcs)",
            stockCount = 720,
            moq = 10,
            pricing = PriceTier(
                tier1Qty = 10, tier1PricePkr = 1680,
                tier2Qty = 50, tier2PricePkr = 1550,
                tier3Qty = 100, tier3PricePkr = 1450
            ),
            brightnessNits = 500,
            refreshRate = "60Hz",
            isIcTransferable = false,
            isWithFrame = false,
            warranty = "7-Day Checking Warranty",
            isFastMoving = true,
            urduTitle = "ویوو وائی 20 / وائی 20 ایس ایل سی ڈی اسمبلی"
        ),
        ProductItem(
            id = "JPT-VIV-V23-AMOLED",
            brand = Brand.VIVO,
            series = "Vivo V Series",
            title = "Vivo V23 5G / V23e Full AMOLED Original Display Screen",
            partCode = "JPT-VIV-V23-OLED",
            compatibleModels = listOf("Vivo V23 5G", "Vivo V23e", "V2130", "V2116"),
            category = DisplayCategory.AMOLED,
            grade = QualityGrade.ORIGINAL_SERVICE_PACK,
            stockLocation = "In Stock - Hall Road (110 pcs)",
            stockCount = 110,
            moq = 5,
            pricing = PriceTier(
                tier1Qty = 5, tier1PricePkr = 7200,
                tier2Qty = 20, tier2PricePkr = 6800,
                tier3Qty = 50, tier3PricePkr = 6500
            ),
            brightnessNits = 800,
            refreshRate = "90Hz",
            isIcTransferable = true,
            isWithFrame = true,
            warranty = "7-Day Checking Warranty (Original Flex)",
            isFastMoving = false,
            urduTitle = "ویوو وی 23 5 جی امولیڈ اوریجنل اسکرین"
        ),
        ProductItem(
            id = "JPT-OPP-A16-LCD",
            brand = Brand.OPPO,
            series = "Oppo A Series",
            title = "Oppo A16 / A16s / A54 (CPH2269 / CPH2239) LCD Screen Assembly",
            partCode = "JPT-OPP-A16-AAA",
            compatibleModels = listOf("Oppo A16", "Oppo A16s", "Oppo A54", "CPH2269"),
            category = DisplayCategory.LCD_PANEL,
            grade = QualityGrade.GRADE_AAA,
            stockLocation = "In Stock - Hall Road (580 pcs)",
            stockCount = 580,
            moq = 10,
            pricing = PriceTier(
                tier1Qty = 10, tier1PricePkr = 1750,
                tier2Qty = 50, tier2PricePkr = 1620,
                tier3Qty = 100, tier3PricePkr = 1520
            ),
            brightnessNits = 520,
            refreshRate = "60Hz",
            isIcTransferable = false,
            isWithFrame = false,
            warranty = "7-Day Checking Warranty",
            isFastMoving = true,
            urduTitle = "اوپو اے 16 / اے 54 ایل سی ڈی اسکرین اسمبلی"
        ),
        ProductItem(
            id = "JPT-OPP-RENO8-OLED",
            brand = Brand.OPPO,
            series = "Oppo Reno Series",
            title = "Oppo Reno 8 5G Original Rigid AMOLED Display with In-Screen Touch",
            partCode = "JPT-OPP-R8-OLED",
            compatibleModels = listOf("Oppo Reno 8", "Reno 8 5G", "CPH2359"),
            category = DisplayCategory.AMOLED,
            grade = QualityGrade.ORIGINAL_SERVICE_PACK,
            stockLocation = "In Stock - Hall Road (85 pcs)",
            stockCount = 85,
            moq = 5,
            pricing = PriceTier(
                tier1Qty = 5, tier1PricePkr = 7900,
                tier2Qty = 20, tier2PricePkr = 7450,
                tier3Qty = 50, tier3PricePkr = 7100
            ),
            brightnessNits = 800,
            refreshRate = "90Hz",
            isIcTransferable = true,
            isWithFrame = true,
            warranty = "7-Day Checking Warranty (Original Flex)",
            isFastMoving = false,
            urduTitle = "اوپو رینو 8 امولیڈ اوریجنل ڈسپلے"
        ),
        ProductItem(
            id = "JPT-XIA-NOTE11-AMOLED",
            brand = Brand.XIAOMI,
            series = "Redmi Note Series",
            title = "Xiaomi Redmi Note 11 4G AMOLED 90Hz Display Assembly (Full Original)",
            partCode = "JPT-XIA-RN11-OLED",
            compatibleModels = listOf("Redmi Note 11", "2201117TG", "2201117TI"),
            category = DisplayCategory.AMOLED,
            grade = QualityGrade.ORIGINAL_SERVICE_PACK,
            stockLocation = "In Stock - Hall Road (260 pcs)",
            stockCount = 260,
            moq = 5,
            pricing = PriceTier(
                tier1Qty = 5, tier1PricePkr = 5200,
                tier2Qty = 25, tier2PricePkr = 4850,
                tier3Qty = 50, tier3PricePkr = 4550
            ),
            brightnessNits = 1000,
            refreshRate = "90Hz",
            isIcTransferable = true,
            isWithFrame = true,
            warranty = "7-Day Checking Warranty",
            isFastMoving = true,
            urduTitle = "ریڈمی نوٹ 11 امولیڈ 90 ہرٹز ڈسپلے اسمبلی"
        ),
        ProductItem(
            id = "JPT-XIA-RED9A-LCD",
            brand = Brand.XIAOMI,
            series = "Redmi Budget Series",
            title = "Xiaomi Redmi 9A / 9C / 10A IPS HD LCD Display + Digitizer",
            partCode = "JPT-XIA-R9A-LCD",
            compatibleModels = listOf("Redmi 9A", "Redmi 9C", "Redmi 10A", "M2006C3LG"),
            category = DisplayCategory.LCD_PANEL,
            grade = QualityGrade.CROWN_HIGH_COPY,
            stockLocation = "In Stock - Hall Road (680 pcs)",
            stockCount = 680,
            moq = 10,
            pricing = PriceTier(
                tier1Qty = 10, tier1PricePkr = 1450,
                tier2Qty = 50, tier2PricePkr = 1340,
                tier3Qty = 100, tier3PricePkr = 1250
            ),
            brightnessNits = 450,
            refreshRate = "60Hz",
            isIcTransferable = false,
            isWithFrame = false,
            warranty = "7-Day Checking Warranty",
            isFastMoving = true,
            urduTitle = "ریڈمی 9 اے / 9 سی ایل سی ڈی ڈسپلے"
        ),
        ProductItem(
            id = "JPT-REA-C33-LCD",
            brand = Brand.REALME,
            series = "Realme C Series",
            title = "Realme C33 / C35 / C31 Complete LCD Touch Digitizer Screen",
            partCode = "JPT-REA-C33-LCD",
            compatibleModels = listOf("Realme C33", "Realme C35", "Realme C31", "RMX3624"),
            category = DisplayCategory.LCD_PANEL,
            grade = QualityGrade.GRADE_AAA,
            stockLocation = "In Stock - Hall Road (410 pcs)",
            stockCount = 410,
            moq = 10,
            pricing = PriceTier(
                tier1Qty = 10, tier1PricePkr = 1650,
                tier2Qty = 50, tier2PricePkr = 1520,
                tier3Qty = 100, tier3PricePkr = 1420
            ),
            brightnessNits = 480,
            refreshRate = "60Hz",
            isIcTransferable = false,
            isWithFrame = false,
            warranty = "7-Day Checking Warranty",
            isFastMoving = true,
            urduTitle = "ریئل می سی 33 / سی 35 ایل سی ڈی پینل"
        ),
        ProductItem(
            id = "JPT-FLEX-SAM-MAIN",
            brand = Brand.SAMSUNG,
            series = "Flex Cables & IC",
            title = "OEM Samsung Galaxy A & S Series Motherboard to LCD Main Interconnect Flex Cable (Pack of 10)",
            partCode = "JPT-FLEX-SAM-10PK",
            compatibleModels = listOf("A12", "A32", "A52", "S20 FE", "S21"),
            category = DisplayCategory.FLEX_CABLE,
            grade = QualityGrade.ORIGINAL_SERVICE_PACK,
            stockLocation = "In Stock - Hall Road (1500+ packs)",
            stockCount = 1500,
            moq = 5,
            pricing = PriceTier(
                tier1Qty = 5, tier1PricePkr = 1200,
                tier2Qty = 20, tier2PricePkr = 1050,
                tier3Qty = 50, tier3PricePkr = 950
            ),
            brightnessNits = 0,
            refreshRate = "N/A",
            isIcTransferable = true,
            isWithFrame = false,
            warranty = "100% Tested Gold Plated Contacts",
            isFastMoving = false,
            urduTitle = "سام سنگ مین مدر بورڈ فلیکس کیبل پیک"
        ),
        ProductItem(
            id = "JPT-DIGI-OCA-PRO",
            brand = Brand.APPLE,
            series = "Digitizer & Glass",
            title = "iPhone 11 to 14 Pro Max Outer Glass with OCA Optical Clear Adhesive & Cold Glue Frame",
            partCode = "JPT-DIGI-OCA-GLUE",
            compatibleModels = listOf("iPhone 11", "iPhone 12", "iPhone 13", "iPhone 14 Pro"),
            category = DisplayCategory.DIGITIZER,
            grade = QualityGrade.CROWN_HIGH_COPY,
            stockLocation = "In Stock - Hall Road (900+ pcs)",
            stockCount = 900,
            moq = 10,
            pricing = PriceTier(
                tier1Qty = 10, tier1PricePkr = 850,
                tier2Qty = 50, tier2PricePkr = 750,
                tier3Qty = 100, tier3PricePkr = 680
            ),
            brightnessNits = 0,
            refreshRate = "N/A",
            isIcTransferable = true,
            isWithFrame = true,
            warranty = "Bubble-Free 250um Mitsubishi OCA Film",
            isFastMoving = false,
            urduTitle = "آئی فون او سی اے گلاس و فریم ڈیجیٹائزر"
        )
    )

    private val _productsFlow = MutableStateFlow<List<ProductItem>>(initialProducts)
    val productsFlow: StateFlow<List<ProductItem>> = _productsFlow.asStateFlow()

    val sampleProducts: List<ProductItem> get() = _productsFlow.value

    fun updateProductRates(
        productId: String,
        tier1Price: Int,
        tier2Price: Int,
        tier3Price: Int,
        stockCount: Int,
        moq: Int
    ) {
        _productsFlow.update { currentList ->
            currentList.map { item ->
                if (item.id == productId) {
                    item.copy(
                        pricing = item.pricing.copy(
                            tier1PricePkr = tier1Price,
                            tier2PricePkr = tier2Price,
                            tier3PricePkr = tier3Price
                        ),
                        stockCount = stockCount,
                        moq = moq,
                        stockLocation = "In Stock - Hall Road ($stockCount pcs)"
                    )
                } else {
                    item
                }
            }
        }
    }

    fun addNewProduct(newItem: ProductItem) {
        _productsFlow.update { current ->
            listOf(newItem) + current
        }
    }

    fun deleteProduct(productId: String) {
        _productsFlow.update { current ->
            current.filterNot { it.id == productId }
        }
    }

    fun toggleStockAvailability(productId: String) {
        _productsFlow.update { current ->
            current.map { item ->
                if (item.id == productId) {
                    val newAvailable = !item.isAvailable
                    item.copy(
                        isAvailable = newAvailable,
                        stockLocation = if (newAvailable) "In Stock - Hall Road (${item.stockCount} pcs)" else "Out of Stock (Transit)"
                    )
                } else {
                    item
                }
            }
        }
    }

    fun resetToDefaults() {
        _productsFlow.value = initialProducts
    }

    fun filterProducts(
        products: List<ProductItem> = sampleProducts,
        query: String = "",
        brand: Brand? = null,
        category: DisplayCategory? = null,
        grade: QualityGrade? = null
    ): List<ProductItem> {
        return products.filter { item ->
            val matchesQuery = query.isBlank() ||
                    item.title.contains(query, ignoreCase = true) ||
                    item.partCode.contains(query, ignoreCase = true) ||
                    item.series.contains(query, ignoreCase = true) ||
                    item.urduTitle.contains(query, ignoreCase = true) ||
                    item.compatibleModels.any { it.contains(query, ignoreCase = true) }

            val matchesBrand = brand == null || item.brand == brand
            val matchesCategory = category == null || category == DisplayCategory.ALL || item.category == category
            val matchesGrade = grade == null || item.grade == grade

            matchesQuery && matchesBrand && matchesCategory && matchesGrade
        }
    }
}
