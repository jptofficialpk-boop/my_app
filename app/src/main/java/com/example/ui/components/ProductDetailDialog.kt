package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.R
import com.example.data.models.ProductItem
import com.example.ui.JptUiState
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
fun ProductDetailDialog(
    product: ProductItem,
    state: JptUiState,
    onDismiss: () -> Unit,
    onAddToCart: (ProductItem, Int) -> Unit
) {
    val context = LocalContext.current
    var selectedQty by remember { mutableIntStateOf(product.moq) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp),
            shape = RoundedCornerShape(20.dp),
            color = NavyDarkest,
            border = BorderStroke(1.5.dp, GoldPrimary)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(18.dp)
            ) {
                // Top Header Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(NavyElevated)
                            .border(1.dp, GoldBorder, RoundedCornerShape(6.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = product.brand.displayName.uppercase(),
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = GoldLight
                        )
                    }

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier
                            .size(32.dp)
                            .testTag("close_product_detail_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = SlateLight
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Title & Part Code
                Text(
                    text = product.title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = PureWhite
                )

                Text(
                    text = "Part Code: ${product.partCode} • Series: ${product.series}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = SlateMuted
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Display Layer Graphic
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .border(1.dp, GoldBorder, RoundedCornerShape(12.dp))
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_hero_display),
                        contentDescription = "Display Hardware Layers",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(8.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .background(NavyDarkest.copy(alpha = 0.85f))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = product.stockLocation,
                            style = MaterialTheme.typography.labelSmall,
                            color = StatusInStock,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Quality Grade Badge
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(product.grade.badgeColorHex).copy(alpha = 0.15f))
                        .border(1.dp, Color(product.grade.badgeColorHex), RoundedCornerShape(8.dp))
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Shield,
                            contentDescription = null,
                            tint = Color(product.grade.badgeColorHex),
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Quality Grade: ${product.grade.gradeName}",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color(product.grade.badgeColorHex)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Hardware Specifications Grid
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = NavyDeep),
                    border = BorderStroke(1.dp, NavyBorder)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = "TECHNICIAN SPECIFICATIONS",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = GoldAccent,
                            letterSpacing = 1.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        SpecRow("Display Tech", product.category.displayName)
                        SpecRow("Peak Brightness", "${product.brightnessNits} nits")
                        SpecRow("Refresh Rate", product.refreshRate)
                        SpecRow("IC Transferable", if (product.isIcTransferable) "Yes (No Display Warning)" else "N/A")
                        SpecRow("Frame Assembly", if (product.isWithFrame) "With Midframe" else "Screen Only (Without Frame)")
                        SpecRow("Warranty", product.warranty)
                        SpecRow("Compatible", product.compatibleModels.joinToString(", "))
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Tiered Wholesale Pricing Matrix Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = NavySurface),
                    border = BorderStroke(1.dp, GoldBorder)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = "WHOLESALE TIER PRICING (HALL ROAD RATES)",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = GoldLight,
                            letterSpacing = 1.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        PriceTierBadgeRow(
                            label = "MOQ (${product.pricing.tier1Qty}–${product.pricing.tier2Qty - 1} pcs)",
                            price = state.formatPrice(product.pricing.tier1PricePkr),
                            isCurrent = selectedQty in product.pricing.tier1Qty until product.pricing.tier2Qty
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        PriceTierBadgeRow(
                            label = "Volume Tier (${product.pricing.tier2Qty}–${product.pricing.tier3Qty - 1} pcs)",
                            price = state.formatPrice(product.pricing.tier2PricePkr),
                            isCurrent = selectedQty in product.pricing.tier2Qty until product.pricing.tier3Qty
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        PriceTierBadgeRow(
                            label = "Distributor Mega (${product.pricing.tier3Qty}+ pcs)",
                            price = state.formatPrice(product.pricing.tier3PricePkr),
                            isCurrent = selectedQty >= product.pricing.tier3Qty
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Quantity Stepper
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Wholesale Qty (MOQ: ${product.moq}):",
                        style = MaterialTheme.typography.bodyMedium,
                        color = PureWhite
                    )

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(
                            onClick = {
                                if (selectedQty > product.moq) selectedQty -= 5
                            },
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(NavyElevated)
                                .border(1.dp, NavyBorder, CircleShape)
                        ) {
                            Icon(Icons.Default.Remove, contentDescription = "Decrease", tint = PureWhite)
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Text(
                            text = "$selectedQty pcs",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = GoldLight
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        IconButton(
                            onClick = { selectedQty += 5 },
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(NavyElevated)
                                .border(1.dp, NavyBorder, CircleShape)
                        ) {
                            Icon(Icons.Default.Add, contentDescription = "Increase", tint = PureWhite)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                val lineTotal = product.pricing.getUnitPriceForQty(selectedQty).toLong() * selectedQty
                Text(
                    text = "Total: ${state.formatPrice(lineTotal)} (@ ${state.formatPrice(product.pricing.getUnitPriceForQty(selectedQty))}/pc)",
                    style = MaterialTheme.typography.labelLarge,
                    color = GoldAccent,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Action Buttons
                Button(
                    onClick = {
                        onAddToCart(product, selectedQty)
                        onDismiss()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("add_to_cart_from_detail_btn"),
                    colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "Add $selectedQty pcs to Wholesale Inquiry",
                        color = NavyDarkest,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedButton(
                    onClick = {
                        val msg = "Hello Muhammad Jamal, I need quotation for $selectedQty pcs of ${product.title} (${product.partCode})."
                        openWhatsApp(context, "+923700357848", msg)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("whatsapp_inquire_detail_btn"),
                    border = BorderStroke(1.dp, WhatsAppGreen),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Instant WhatsApp Inquire", color = WhatsAppGreen, fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}

@Composable
private fun SpecRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyMedium, color = SlateMuted)
        Text(text = value, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium, color = PureWhite)
    }
}

@Composable
private fun PriceTierBadgeRow(label: String, price: String, isCurrent: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(6.dp))
            .background(if (isCurrent) GoldDark.copy(alpha = 0.35f) else Color.Transparent)
            .border(
                1.dp,
                if (isCurrent) GoldPrimary else Color.Transparent,
                RoundedCornerShape(6.dp)
            )
            .padding(horizontal = 8.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (isCurrent) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = GoldAccent,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
            }
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Normal,
                color = if (isCurrent) GoldLight else SlateLight
            )
        }

        Text(
            text = price,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold,
            color = if (isCurrent) GoldPrimary else PureWhite
        )
    }
}
