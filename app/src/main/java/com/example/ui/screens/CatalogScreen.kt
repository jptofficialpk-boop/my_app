package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.models.AppLanguage
import com.example.data.models.Brand
import com.example.data.models.DisplayCategory
import com.example.data.models.ProductItem
import com.example.data.models.QualityGrade
import com.example.data.repository.CatalogRepository
import com.example.ui.JptUiState
import com.example.ui.JptViewModel
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

@Composable
fun CatalogScreen(
    state: JptUiState,
    viewModel: JptViewModel,
    modifier: Modifier = Modifier
) {
    val filteredProducts = CatalogRepository.filterProducts(
        query = state.searchQuery,
        brand = state.selectedBrand,
        category = state.selectedCategory,
        grade = state.selectedGrade
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(NavyDeep)
    ) {
        // FILTER HEADER BAR
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = NavyDarkest,
            shadowElevation = 4.dp
        ) {
            Column(modifier = Modifier.padding(vertical = 8.dp)) {
                // Brand filter row
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 14.dp)
                ) {
                    item {
                        FilterChip(
                            label = if (state.language == AppLanguage.URDU) "تمام برانڈز" else "All Brands",
                            isSelected = state.selectedBrand == null,
                            onClick = { viewModel.selectBrand(null) }
                        )
                    }
                    items(Brand.values()) { brand ->
                        FilterChip(
                            label = brand.displayName,
                            isSelected = state.selectedBrand == brand,
                            onClick = { viewModel.selectBrand(brand) }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                // Category filter row
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 14.dp)
                ) {
                    items(DisplayCategory.values()) { category ->
                        FilterChip(
                            label = if (state.language == AppLanguage.URDU) category.urduName else category.displayName,
                            isSelected = state.selectedCategory == category,
                            onClick = { viewModel.selectCategory(category) }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                // Quality Grade filter row
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 14.dp)
                ) {
                    item {
                        FilterChip(
                            label = "All Grades",
                            isSelected = state.selectedGrade == null,
                            onClick = { viewModel.selectGrade(null) }
                        )
                    }
                    items(QualityGrade.values()) { grade ->
                        FilterChip(
                            label = grade.gradeName,
                            isSelected = state.selectedGrade == grade,
                            onClick = { viewModel.selectGrade(grade) }
                        )
                    }
                }
            }
        }

        // INVENTORY LIST
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 14.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (state.language == AppLanguage.URDU)
                            "دستیاب ہول سیل ماڈلز (${filteredProducts.size})"
                        else
                            "WHOLESALE INVENTORY (${filteredProducts.size} MODELS)",
                        style = MaterialTheme.typography.labelLarge,
                        color = GoldLight,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Inventory,
                            contentDescription = null,
                            tint = StatusInStock,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Hall Road Stock",
                            style = MaterialTheme.typography.labelSmall,
                            color = StatusInStock
                        )
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
            }

            if (filteredProducts.isEmpty()) {
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 30.dp),
                        colors = CardDefaults.cardColors(containerColor = NavySurface)
                    ) {
                        Column(
                            modifier = Modifier.padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "No screens match current filters",
                                style = MaterialTheme.typography.titleMedium,
                                color = PureWhite
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "Try resetting filters or searching another model like 'A12' or 'iPhone 13'",
                                style = MaterialTheme.typography.bodyMedium,
                                color = SlateMuted
                            )
                        }
                    }
                }
            } else {
                items(filteredProducts) { product ->
                    CatalogProductCard(
                        product = product,
                        state = state,
                        onViewDetail = { viewModel.showProductDetail(product) },
                        onAddToCart = { qty -> viewModel.addToCart(product, qty) }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun FilterChip(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(if (isSelected) GoldPrimary else NavyElevated)
            .border(
                1.dp,
                if (isSelected) GoldLight else NavyBorder,
                RoundedCornerShape(8.dp)
            )
            .clickable { onClick() }
            .padding(horizontal = 10.dp, vertical = 6.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            color = if (isSelected) NavyDarkest else PureWhite
        )
    }
}

@Composable
private fun CatalogProductCard(
    product: ProductItem,
    state: JptUiState,
    onViewDetail: () -> Unit,
    onAddToCart: (Int) -> Unit
) {
    var quickQty by remember { mutableIntStateOf(product.moq) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("catalog_item_${product.id}"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = NavySurface),
        border = BorderStroke(1.dp, GoldBorder.copy(alpha = 0.5f))
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            // Brand & Grade Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(NavyDarkest)
                            .border(0.8.dp, GoldBorder, RoundedCornerShape(4.dp))
                            .padding(horizontal = 6.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = product.brand.displayName.uppercase(),
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
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(StatusInStock.copy(alpha = 0.15f))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = product.stockLocation,
                        style = MaterialTheme.typography.labelSmall,
                        color = StatusInStock,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Title & Part Code
            Text(
                text = if (state.language == AppLanguage.URDU && product.urduTitle.isNotEmpty())
                    product.urduTitle
                else
                    product.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = PureWhite
            )

            Text(
                text = "Part Code: ${product.partCode} • Series: ${product.series}",
                style = MaterialTheme.typography.bodyMedium,
                color = SlateMuted
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Tech specs badges
            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SpecPill(product.category.displayName)
                if (product.brightnessNits > 0) {
                    SpecPill("${product.brightnessNits} nits")
                }
                SpecPill(if (product.isWithFrame) "With Frame" else "No Frame")
                if (product.isIcTransferable) {
                    SpecPill("IC Ready")
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Tiered Pricing Grid
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(NavyElevated)
                    .border(0.8.dp, NavyBorder, RoundedCornerShape(8.dp))
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                PriceTierColumn(
                    label = "10–49 pcs",
                    price = state.formatPrice(product.pricing.tier1PricePkr)
                )
                PriceTierColumn(
                    label = "50–99 pcs",
                    price = state.formatPrice(product.pricing.tier2PricePkr)
                )
                PriceTierColumn(
                    label = "100+ pcs",
                    price = state.formatPrice(product.pricing.tier3PricePkr),
                    isBest = true
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Quantity Quick Stepper & Actions
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Quick Quantity selector buttons (+10, +25, +50)
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    listOf(10, 25, 50).forEach { qtyOption ->
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(if (quickQty == qtyOption) GoldDark else NavyElevated)
                                .border(
                                    1.dp,
                                    if (quickQty == qtyOption) GoldPrimary else NavyBorder,
                                    RoundedCornerShape(6.dp)
                                )
                                .clickable { quickQty = qtyOption }
                                .padding(horizontal = 8.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = "+$qtyOption",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = if (quickQty == qtyOption) GoldLight else PureWhite
                            )
                        }
                    }
                }

                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    OutlinedButton(
                        onClick = onViewDetail,
                        modifier = Modifier.height(36.dp),
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(1.dp, GoldBorder)
                    ) {
                        Text("Specs", color = GoldLight, fontSize = 12.sp)
                    }

                    Button(
                        onClick = { onAddToCart(quickQty) },
                        modifier = Modifier.height(36.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary)
                    ) {
                        Text(
                            text = "Add $quickQty pcs",
                            color = NavyDarkest,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SpecPill(text: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(NavyDeep)
            .padding(horizontal = 6.dp, vertical = 2.dp)
    ) {
        Text(text = text, style = MaterialTheme.typography.labelSmall, color = SlateLight)
    }
}

@Composable
private fun PriceTierColumn(label: String, price: String, isBest: Boolean = false) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = if (isBest) GoldLight else SlateMuted
        )
        Text(
            text = price,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold,
            color = if (isBest) GoldAccent else PureWhite
        )
    }
}
