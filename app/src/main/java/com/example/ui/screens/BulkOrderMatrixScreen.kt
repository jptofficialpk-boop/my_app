package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ContentPaste
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.models.AppLanguage
import com.example.data.models.ProductItem
import com.example.data.repository.CatalogRepository
import com.example.ui.AppScreen
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
fun BulkOrderMatrixScreen(
    state: JptUiState,
    viewModel: JptViewModel,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val products = CatalogRepository.sampleProducts

    val totalMatrixUnits = state.quickMatrixQuantities.values.sum()
    val estimatedMatrixTotalPkr = state.quickMatrixQuantities.entries.sumOf { (prodId, qty) ->
        val prod = products.find { it.id == prodId }
        (prod?.pricing?.getUnitPriceForQty(qty) ?: 0).toLong() * qty
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(NavyDeep)
    ) {
        // Tab selector: Quick-Add Matrix vs CSV / Paste Bulk List
        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = NavyDarkest,
            contentColor = GoldPrimary,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                    color = GoldPrimary,
                    height = 3.dp
                )
            }
        ) {
            Tab(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                text = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.ListAlt,
                            contentDescription = null,
                            tint = if (selectedTab == 0) GoldPrimary else SlateMuted,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (state.language == AppLanguage.URDU) "کوئک میٹرکس گرڈ" else "Quick-Add Matrix",
                            fontWeight = if (selectedTab == 0) FontWeight.Bold else FontWeight.Normal,
                            color = if (selectedTab == 0) GoldLight else SlateMuted
                        )
                    }
                }
            )

            Tab(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                text = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.ContentPaste,
                            contentDescription = null,
                            tint = if (selectedTab == 1) GoldPrimary else SlateMuted,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (state.language == AppLanguage.URDU) "بلک لسٹ / سی ایس وی" else "CSV / Text Parser",
                            fontWeight = if (selectedTab == 1) FontWeight.Bold else FontWeight.Normal,
                            color = if (selectedTab == 1) GoldLight else SlateMuted
                        )
                    }
                }
            )
        }

        if (selectedTab == 0) {
            // TAB 0: QUICK-ADD MATRIX
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 14.dp)
            ) {
                item {
                    Spacer(modifier = Modifier.height(12.dp))

                    // Instructions Card
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = NavySurface),
                        border = BorderStroke(1.dp, GoldBorder)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.FlashOn,
                                    contentDescription = null,
                                    tint = GoldPrimary,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = if (state.language == AppLanguage.URDU)
                                        "دکانداروں کے لیے بیک وقت ملٹیپل اسکرین آرڈر میٹرکس"
                                    else
                                        "MULTI-MODEL B2B WHOLESALE MATRIX",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = GoldLight,
                                    letterSpacing = 1.sp
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Adjust quantities across several smartphone models below. Rates drop automatically as total quantity reaches volume tiers.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = SlateLight
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Quick Preset Bundles
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        PresetBundleChip(
                            label = "50x A12 + 30x Y20",
                            onClick = {
                                viewModel.setMatrixQuantity("JPT-SAM-A125", 50)
                                viewModel.setMatrixQuantity("JPT-VIV-Y20-LCD", 30)
                            }
                        )
                        PresetBundleChip(
                            label = "iPhone Pro Pack",
                            onClick = {
                                viewModel.setMatrixQuantity("JPT-IPH-11-INCELL", 25)
                                viewModel.setMatrixQuantity("JPT-IPH-13PM-OLED", 15)
                            }
                        )
                        PresetBundleChip(
                            label = "Infinix/Tecno Pack",
                            onClick = {
                                viewModel.setMatrixQuantity("JPT-INF-HOT10-LCD", 50)
                                viewModel.setMatrixQuantity("JPT-TEC-SPARK8-LCD", 50)
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                }

                // Table Items
                items(products) { product ->
                    val qty = state.quickMatrixQuantities[product.id] ?: 0
                    MatrixItemRow(
                        product = product,
                        currentQty = qty,
                        state = state,
                        onQtyChange = { newQty -> viewModel.setMatrixQuantity(product.id, newQty) }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                item {
                    Spacer(modifier = Modifier.height(80.dp))
                }
            }
        } else {
            // TAB 1: CSV / TEXT BULK LIST PARSER
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 14.dp)
            ) {
                item {
                    Spacer(modifier = Modifier.height(12.dp))

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = NavySurface),
                        border = BorderStroke(1.dp, NavyBorder)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Text(
                                text = "BULK LIST / CSV TEXT PARSER",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = GoldAccent,
                                letterSpacing = 1.sp
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Shopkeepers can paste their requirement list directly (e.g. from WhatsApp or Excel):",
                                style = MaterialTheme.typography.bodyMedium,
                                color = SlateLight
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(NavyDarkest)
                                    .padding(8.dp)
                            ) {
                                Text(
                                    text = "Example input format:\nA12: 50\nY20: 30\nHot 10: 40\niPhone 11: 20\nNote 11: 15",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = GoldLight,
                                    lineHeight = 18.sp
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = state.bulkPasteInput,
                        onValueChange = { viewModel.setBulkPasteInput(it) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .testTag("bulk_paste_text_input"),
                        placeholder = {
                            Text(
                                text = "Paste bulk list here...\ne.g.\nA12: 50\nY20: 30\nHot 10: 40",
                                color = SlateMuted
                            )
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = NavySurface,
                            unfocusedContainerColor = NavyDeep,
                            focusedBorderColor = GoldPrimary,
                            unfocusedBorderColor = NavyBorder,
                            focusedTextColor = PureWhite,
                            unfocusedTextColor = PureWhite
                        ),
                        shape = RoundedCornerShape(12.dp)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Sample quick buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedButton(
                            onClick = {
                                viewModel.setBulkPasteInput("A12: 50\nY20: 30\nHot 10: 40\niPhone 11: 20\nNote 11: 15")
                            },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(8.dp),
                            border = BorderStroke(1.dp, GoldBorder)
                        ) {
                            Text("Load Sample List", color = GoldLight, fontSize = 12.sp)
                        }

                        Button(
                            onClick = { viewModel.parseAndAddBulkText() },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("parse_bulk_btn"),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary)
                        ) {
                            Text("Parse & Add to Quote", color = NavyDarkest, fontWeight = FontWeight.Bold)
                        }
                    }

                    if (state.bulkPasteMessage != null) {
                        Spacer(modifier = Modifier.height(12.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(StatusInStock.copy(alpha = 0.2f))
                                .border(1.dp, StatusInStock, RoundedCornerShape(8.dp))
                                .padding(12.dp)
                        ) {
                            Text(
                                text = state.bulkPasteMessage,
                                color = StatusInStock,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }

    // FLOATING BOTTOM ACTION BAR FOR MATRIX
    if (selectedTab == 0 && totalMatrixUnits > 0) {
        Box(modifier = Modifier.fillMaxSize()) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                color = NavyDarkest,
                shadowElevation = 16.dp,
                border = BorderStroke(1.dp, GoldPrimary)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Matrix Total: $totalMatrixUnits screens",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = PureWhite
                        )
                        Text(
                            text = "Est: ${state.formatPrice(estimatedMatrixTotalPkr)}",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.ExtraBold,
                            color = GoldAccent
                        )
                    }

                    Button(
                        onClick = { viewModel.applyMatrixToCart() },
                        colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.testTag("apply_matrix_btn")
                    ) {
                        Text(
                            text = "Review Wholesale Quote",
                            color = NavyDarkest,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PresetBundleChip(
    label: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(NavyElevated)
            .border(1.dp, GoldBorder, RoundedCornerShape(6.dp))
            .clickable { onClick() }
            .padding(horizontal = 8.dp, vertical = 6.dp)
    ) {
        Text(
            text = "+ $label",
            style = MaterialTheme.typography.labelSmall,
            color = GoldLight,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun MatrixItemRow(
    product: ProductItem,
    currentQty: Int,
    state: JptUiState,
    onQtyChange: (Int) -> Unit
) {
    val unitRate = product.pricing.getUnitPriceForQty(if (currentQty > 0) currentQty else product.moq)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (currentQty > 0) NavyElevated else NavySurface
        ),
        border = BorderStroke(
            1.dp,
            if (currentQty > 0) GoldPrimary else NavyBorder
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1.3f)) {
                Text(
                    text = product.title,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = PureWhite,
                    maxLines = 1
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "${product.partCode} • ${product.grade.gradeName}",
                        style = MaterialTheme.typography.labelSmall,
                        color = SlateMuted
                    )
                }
                Text(
                    text = "Rate: ${state.formatPrice(unitRate)}/pc (MOQ: ${product.moq})",
                    style = MaterialTheme.typography.labelSmall,
                    color = GoldAccent,
                    fontWeight = FontWeight.SemiBold
                )
            }

            // Quick Stepper (+10, +25, +50)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                if (currentQty > 0) {
                    IconButton(
                        onClick = { onQtyChange(maxOf(0, currentQty - 10)) },
                        modifier = Modifier.size(30.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Remove,
                            contentDescription = "Decrease",
                            tint = PureWhite
                        )
                    }

                    Text(
                        text = "$currentQty",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = GoldLight,
                        modifier = Modifier.padding(horizontal = 4.dp)
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(GoldPrimary)
                        .clickable { onQtyChange(currentQty + 10) }
                        .padding(horizontal = 8.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "+10",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = NavyDarkest
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(GoldDark)
                        .clickable { onQtyChange(currentQty + 25) }
                        .padding(horizontal = 8.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "+25",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = PureWhite
                    )
                }
            }
        }
    }
}
