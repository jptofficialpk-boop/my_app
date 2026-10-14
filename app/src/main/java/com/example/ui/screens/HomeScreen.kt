package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.models.AppLanguage
import com.example.data.models.Brand
import com.example.data.models.DisplayCategory
import com.example.data.models.ProductItem
import com.example.data.repository.CatalogRepository
import com.example.ui.AppScreen
import com.example.ui.JptUiState
import com.example.ui.JptViewModel
import com.example.ui.components.CircuitBackground
import com.example.ui.components.openWhatsApp
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.GoldBorder
import com.example.ui.theme.GoldDark
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.NavyBorder
import com.example.ui.theme.NavyDarkest
import com.example.ui.theme.NavyDeep
import com.example.ui.theme.NavyElevated
import com.example.ui.theme.NavySurface
import com.example.ui.theme.PureWhite
import com.example.ui.theme.SlateLight
import com.example.ui.theme.SlateMuted
import com.example.ui.theme.StatusInStock
import com.example.ui.theme.WhatsAppGreen

@Composable
fun HomeScreen(
    state: JptUiState,
    viewModel: JptViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val fastMovingItems = CatalogRepository.sampleProducts.filter { it.isFastMoving }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(NavyDeep)
            .padding(horizontal = 14.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(8.dp))

            // HERO SECTION
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("hero_section_card"),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = NavyDarkest),
                border = BorderStroke(1.dp, GoldBorder)
            ) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    CircuitBackground(circuitAlpha = 0.5f)

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        // Badge: Pakistan's Trusted B2B Wholesale Partner
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clip(RoundedCornerShape(30.dp))
                                .background(NavyElevated)
                                .border(1.dp, GoldBorder, RoundedCornerShape(30.dp))
                                .padding(horizontal = 10.dp, vertical = 5.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Verified,
                                contentDescription = null,
                                tint = GoldPrimary,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (state.language == AppLanguage.URDU)
                                    "پاکستان کا معتبر بی 2 بی ہول سیل پارٹنر"
                                else
                                    "Pakistan's Trusted B2B Wholesale Partner",
                                style = MaterialTheme.typography.labelSmall,
                                color = GoldLight,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Headline
                        Text(
                            text = if (state.language == AppLanguage.URDU)
                                "پریسیژن ڈسپلے۔ بے مثال ہول سیل ریٹس۔"
                            else
                                "Precision Displays.\nUnmatched Wholesale Rates.",
                            style = MaterialTheme.typography.displayMedium,
                            fontWeight = FontWeight.ExtraBold,
                            color = PureWhite
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        // Sub-headline
                        Text(
                            text = if (state.language == AppLanguage.URDU)
                                "سام سنگ، آئی فون، ویوو، اوپو، ٹیکنو، انفنکس کے لیے ہال روڈ سے ڈائریکٹ بلک سپلائی"
                            else
                                "Direct Bulk Supply for Samsung, iPhone, Vivo, Oppo, Tecno, Infinix & More.",
                            style = MaterialTheme.typography.bodyLarge,
                            color = SlateLight
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        // Hero 3D Smartphone Exploded Layer Visual
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(190.dp)
                                .clip(RoundedCornerShape(14.dp))
                                .border(1.2.dp, GoldBorder, RoundedCornerShape(14.dp))
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.img_hero_display),
                                contentDescription = "Smartphone Exploded Display Layers",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )

                            // Overlay info pill
                            Box(
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .padding(8.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(NavyDarkest.copy(alpha = 0.85f))
                                    .border(1.dp, GoldBorder, RoundedCornerShape(8.dp))
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = "Hall Road Stock • Batch 2026",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = GoldLight,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // CTAs
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = { viewModel.setScreen(AppScreen.CATALOG) },
                                modifier = Modifier
                                    .weight(1.3f)
                                    .testTag("explore_inventory_btn"),
                                colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text(
                                    text = if (state.language == AppLanguage.URDU) "ہول سیل اسٹاک دیکھیں" else "Explore Wholesale",
                                    color = NavyDarkest,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Icon(
                                    imageVector = Icons.Default.ArrowForward,
                                    contentDescription = null,
                                    tint = NavyDarkest,
                                    modifier = Modifier.size(16.dp)
                                )
                            }

                            Button(
                                onClick = {
                                    openWhatsApp(
                                        context,
                                        "+923700357848",
                                        "Assalam o Alaikum Muhammad Jamal, I need current wholesale screen rate list."
                                    )
                                },
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("instant_whatsapp_btn"),
                                colors = ButtonDefaults.buttonColors(containerColor = WhatsAppGreen),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text(
                                    text = "WhatsApp",
                                    color = PureWhite,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }

        // BRAND CAROUSEL
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (state.language == AppLanguage.URDU) "اسمارٹ فون برانڈز" else "SUPPORTED BRANDS",
                    style = MaterialTheme.typography.labelLarge,
                    color = GoldLight,
                    letterSpacing = 1.sp
                )
                Text(
                    text = if (state.language == AppLanguage.URDU) "تمام برانڈز" else "View All",
                    style = MaterialTheme.typography.labelSmall,
                    color = SlateMuted,
                    modifier = Modifier.clickable { viewModel.setScreen(AppScreen.CATALOG) }
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(Brand.values()) { brand ->
                    val isSelected = state.selectedBrand == brand
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (isSelected) GoldPrimary else NavySurface)
                            .border(
                                1.dp,
                                if (isSelected) GoldLight else NavyBorder,
                                RoundedCornerShape(12.dp)
                            )
                            .clickable {
                                viewModel.selectBrand(brand)
                                viewModel.setScreen(AppScreen.CATALOG)
                            }
                            .padding(horizontal = 14.dp, vertical = 10.dp)
                            .testTag("brand_chip_${brand.name}")
                    ) {
                        Text(
                            text = brand.displayName,
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = if (isSelected) NavyDarkest else PureWhite
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }

        // CATEGORY SELECTION TILES
        item {
            Text(
                text = if (state.language == AppLanguage.URDU) "ڈسپلے کیٹیگریز" else "SCREEN CATEGORIES",
                style = MaterialTheme.typography.labelLarge,
                color = GoldLight,
                letterSpacing = 1.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CategoryCard(
                    title = if (state.language == AppLanguage.URDU) "ایل سی ڈی پینلز" else "LCD Panels",
                    subtitle = "High Bright In-Cell",
                    modifier = Modifier.weight(1f),
                    onClick = {
                        viewModel.selectCategory(DisplayCategory.LCD_PANEL)
                        viewModel.setScreen(AppScreen.CATALOG)
                    }
                )
                CategoryCard(
                    title = if (state.language == AppLanguage.URDU) "اوریجنل او ایل ای ڈی" else "Original OLED",
                    subtitle = "Super Retina & LTPO",
                    modifier = Modifier.weight(1f),
                    onClick = {
                        viewModel.selectCategory(DisplayCategory.ORIGINAL_OLED)
                        viewModel.setScreen(AppScreen.CATALOG)
                    }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CategoryCard(
                    title = if (state.language == AppLanguage.URDU) "امولیڈ ڈسپلے" else "AMOLED Display",
                    subtitle = "Curved & In-Screen",
                    modifier = Modifier.weight(1f),
                    onClick = {
                        viewModel.selectCategory(DisplayCategory.AMOLED)
                        viewModel.setScreen(AppScreen.CATALOG)
                    }
                )
                CategoryCard(
                    title = if (state.language == AppLanguage.URDU) "فلیکس و ڈیجیٹائزر" else "Digitizer & Flex",
                    subtitle = "IC Cables & OCA Glass",
                    modifier = Modifier.weight(1f),
                    onClick = {
                        viewModel.selectCategory(DisplayCategory.FLEX_CABLE)
                        viewModel.setScreen(AppScreen.CATALOG)
                    }
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
        }

        // FAST-MOVING WHOLESALE INVENTORY
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (state.language == AppLanguage.URDU) "تیز ترین فروخت ہونے والے ماڈلز" else "FAST-MOVING WHOLESALE SPECIALS",
                    style = MaterialTheme.typography.labelLarge,
                    color = GoldLight,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "${fastMovingItems.size} models",
                    style = MaterialTheme.typography.labelSmall,
                    color = SlateMuted
                )
            }

            Spacer(modifier = Modifier.height(10.dp))
        }

        items(fastMovingItems) { product ->
            HomeProductItemCard(
                product = product,
                state = state,
                onViewDetail = { viewModel.showProductDetail(product) },
                onAddToCart = { viewModel.addToCart(product, product.moq) }
            )
            Spacer(modifier = Modifier.height(10.dp))
        }

        // TRUST BADGES SECTION
        item {
            Spacer(modifier = Modifier.height(14.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = NavySurface),
                border = BorderStroke(1.dp, NavyBorder)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = if (state.language == AppLanguage.URDU) "جے پی ٹی کوالٹی و اعتماد کی ضمانت" else "JPT B2B WHOLESALE GUARANTEE",
                        style = MaterialTheme.typography.labelLarge,
                        color = GoldAccent,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    TrustFeatureRow(
                        icon = Icons.Default.CheckCircle,
                        title = "Zero Dead-Pixel Testing",
                        desc = "Every screen tested on factory motherboards prior to dispatch."
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TrustFeatureRow(
                        icon = Icons.Default.LocalShipping,
                        title = "Express Nationwide Dispatch",
                        desc = "Daily cargo bookings via Daewoo & TCS Express across all Pakistan cities."
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TrustFeatureRow(
                        icon = Icons.Default.Security,
                        title = "Anti-Static Wooden Packaging",
                        desc = "Bubble wrapped and rigid foam casing to prevent flex or corner damage."
                    )
                }
            }

            Spacer(modifier = Modifier.height(28.dp))
        }
    }
}

@Composable
private fun CategoryCard(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = NavySurface),
        border = BorderStroke(1.dp, GoldBorder.copy(alpha = 0.4f))
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = PureWhite
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = subtitle,
                style = MaterialTheme.typography.labelSmall,
                color = GoldAccent
            )
        }
    }
}

@Composable
private fun HomeProductItemCard(
    product: ProductItem,
    state: JptUiState,
    onViewDetail: () -> Unit,
    onAddToCart: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onViewDetail() }
            .testTag("home_product_${product.id}"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = NavySurface),
        border = BorderStroke(1.dp, GoldBorder.copy(alpha = 0.5f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(NavyElevated)
                            .border(0.8.dp, GoldBorder, RoundedCornerShape(4.dp))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = product.brand.displayName,
                            style = MaterialTheme.typography.labelSmall,
                            color = GoldLight,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = product.grade.gradeName,
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(product.grade.badgeColorHex),
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = if (state.language == AppLanguage.URDU && product.urduTitle.isNotEmpty())
                        product.urduTitle
                    else
                        product.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = PureWhite,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Wholesale: ${state.formatPrice(product.pricing.tier1PricePkr)}",
                        style = MaterialTheme.typography.labelLarge,
                        color = GoldAccent,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "MOQ: ${product.moq} pcs",
                        style = MaterialTheme.typography.labelSmall,
                        color = SlateMuted
                    )
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            IconButton(
                onClick = onAddToCart,
                modifier = Modifier
                    .size(42.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(GoldPrimary)
                    .testTag("quick_add_${product.id}")
            ) {
                Icon(
                    imageVector = Icons.Default.AddShoppingCart,
                    contentDescription = "Add to wholesale cart",
                    tint = NavyDarkest
                )
            }
        }
    }
}

@Composable
private fun TrustFeatureRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    desc: String
) {
    Row(verticalAlignment = Alignment.Top) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = GoldPrimary,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = PureWhite
            )
            Text(
                text = desc,
                style = MaterialTheme.typography.bodyMedium,
                color = SlateMuted
            )
        }
    }
}
