package com.example.ui.screens

import android.content.Intent
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.models.AppLanguage
import com.example.data.models.CartItem
import com.example.data.models.WholesaleTier
import com.example.ui.AppScreen
import com.example.ui.JptUiState
import com.example.ui.JptViewModel
import com.example.ui.components.openDialer
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
fun CartAndQuoteScreen(
    state: JptUiState,
    viewModel: JptViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    if (state.cartItems.isEmpty()) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(NavyDeep)
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(NavyElevated)
                        .border(1.dp, GoldBorder, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = null,
                        tint = GoldPrimary,
                        modifier = Modifier.size(38.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = if (state.language == AppLanguage.URDU)
                        "آپ کی ہول سیل انکوائری خالی ہے"
                    else
                        "Your Bulk Wholesale Cart is Empty",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = PureWhite
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Select screen models from Wholesale Inventory or use Quick-Add Matrix to add multiple parts.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = SlateMuted,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = { viewModel.setScreen(AppScreen.CATALOG) },
                    colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Explore Wholesale Inventory", color = NavyDarkest, fontWeight = FontWeight.Bold)
                }
            }
        }
        return
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(NavyDeep)
            .padding(horizontal = 14.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(12.dp))

            // Header Banner
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = NavyDarkest),
                border = BorderStroke(1.dp, GoldBorder)
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
                            text = if (state.language == AppLanguage.URDU)
                                "ہول سیل کوٹیشن جنریٹر"
                            else
                                "WHOLESALE QUOTE GENERATOR",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = GoldLight,
                            letterSpacing = 1.sp
                        )
                        Text(
                            text = "${state.totalUnitsInCart} Screens Selected",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = PureWhite
                        )
                    }

                    OutlinedButton(
                        onClick = { viewModel.clearCart() },
                        border = BorderStroke(1.dp, NavyBorder),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Clear",
                            tint = SlateLight,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Clear", color = SlateLight, fontSize = 12.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "ITEMIZED WHOLESALE BREAKDOWN",
                style = MaterialTheme.typography.labelSmall,
                color = GoldLight,
                letterSpacing = 1.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        // Cart items
        items(state.cartItems) { cartItem ->
            CartItemRow(
                item = cartItem,
                state = state,
                onQtyChange = { newQty -> viewModel.updateCartQuantity(cartItem.product.id, newQty) },
                onRemove = { viewModel.updateCartQuantity(cartItem.product.id, 0) }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        // Shopkeeper Destination & Logistics form
        item {
            Spacer(modifier = Modifier.height(12.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = NavySurface),
                border = BorderStroke(1.dp, NavyBorder)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "SHOP & DISPATCH DETAILS",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = GoldAccent,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = state.customerName,
                        onValueChange = { viewModel.updateCustomerDetails(it, state.shopName, state.customerCity, state.customerPhone, state.freightCarrier) },
                        label = { Text("Buyer / Contact Person", color = SlateLight) },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = GoldPrimary,
                            unfocusedBorderColor = NavyBorder,
                            focusedTextColor = PureWhite,
                            unfocusedTextColor = PureWhite
                        ),
                        shape = RoundedCornerShape(8.dp)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = state.shopName,
                            onValueChange = { viewModel.updateCustomerDetails(state.customerName, it, state.customerCity, state.customerPhone, state.freightCarrier) },
                            label = { Text("Shop Name", color = SlateLight) },
                            modifier = Modifier.weight(1f),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = GoldPrimary,
                                unfocusedBorderColor = NavyBorder,
                                focusedTextColor = PureWhite,
                                unfocusedTextColor = PureWhite
                            ),
                            shape = RoundedCornerShape(8.dp)
                        )

                        OutlinedTextField(
                            value = state.customerCity,
                            onValueChange = { viewModel.updateCustomerDetails(state.customerName, state.shopName, it, state.customerPhone, state.freightCarrier) },
                            label = { Text("City (e.g. Lahore)", color = SlateLight) },
                            modifier = Modifier.weight(1f),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = GoldPrimary,
                                unfocusedBorderColor = NavyBorder,
                                focusedTextColor = PureWhite,
                                unfocusedTextColor = PureWhite
                            ),
                            shape = RoundedCornerShape(8.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = state.customerPhone,
                        onValueChange = { viewModel.updateCustomerDetails(state.customerName, state.shopName, state.customerCity, it, state.freightCarrier) },
                        label = { Text("Contact Phone / WhatsApp", color = SlateLight) },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = GoldPrimary,
                            unfocusedBorderColor = NavyBorder,
                            focusedTextColor = PureWhite,
                            unfocusedTextColor = PureWhite
                        ),
                        shape = RoundedCornerShape(8.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Select Cargo Dispatch Service:",
                        style = MaterialTheme.typography.labelSmall,
                        color = SlateLight
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        listOf(
                            "Daewoo Cargo",
                            "TCS Air Express",
                            "Hall Road Pickup"
                        ).forEach { carrier ->
                            val isSelected = state.freightCarrier == carrier
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(if (isSelected) GoldPrimary else NavyElevated)
                                    .border(1.dp, if (isSelected) GoldLight else NavyBorder, RoundedCornerShape(6.dp))
                                    .clickable {
                                        viewModel.updateCustomerDetails(
                                            state.customerName,
                                            state.shopName,
                                            state.customerCity,
                                            state.customerPhone,
                                            carrier
                                        )
                                    }
                                    .padding(horizontal = 8.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = carrier,
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isSelected) NavyDarkest else PureWhite
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Pricing Summary Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = NavySurface),
                border = BorderStroke(1.2.dp, GoldBorder)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "FORMAL WHOLESALE ESTIMATE",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = GoldLight,
                        letterSpacing = 1.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    SummaryRow("Total Display Units", "${state.totalUnitsInCart} pcs")
                    SummaryRow("Gross Catalog Subtotal", state.formatPrice(state.rawSubtotalPkr))

                    if (state.wholesaleTier.discountPercent > 0) {
                        SummaryRow(
                            "${state.wholesaleTier.tierName} (${state.wholesaleTier.discountPercent}%)",
                            "-${state.formatPrice(state.tierDiscountAmountPkr)}",
                            isAccent = true
                        )
                    }

                    SummaryRow("Packaging & Testing", "FREE (0% Dead Pixel QC)")
                    SummaryRow("Cargo Booking", "To Pay at ${state.customerCity} Terminal")

                    Spacer(modifier = Modifier.height(8.dp))
                    Divider(color = GoldBorder, thickness = 1.dp)
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "NET ESTIMATED TOTAL:",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = PureWhite
                        )
                        Text(
                            text = state.formatPrice(state.finalTotalPkr),
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.ExtraBold,
                            color = GoldAccent
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Primary CTAs: WhatsApp & Direct Call
            Button(
                onClick = {
                    val orderMsg = viewModel.generateWhatsAppOrderText()
                    openWhatsApp(context, "+923700357848", orderMsg)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("submit_whatsapp_quote_btn"),
                colors = ButtonDefaults.buttonColors(containerColor = WhatsAppGreen),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Submit Wholesale Order via WhatsApp",
                    color = PureWhite,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = { openDialer(context, "03700357848") },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(1.dp, GoldPrimary)
                ) {
                    Icon(
                        imageVector = Icons.Default.Call,
                        contentDescription = null,
                        tint = GoldPrimary,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Call Sales", color = GoldAccent, fontWeight = FontWeight.Bold)
                }

                OutlinedButton(
                    onClick = {
                        val shareIntent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(Intent.EXTRA_SUBJECT, "JPT Wholesale Quote - ${state.shopName}")
                            putExtra(Intent.EXTRA_TEXT, viewModel.generateWhatsAppOrderText())
                        }
                        context.startActivity(Intent.createChooser(shareIntent, "Share JPT Quote"))
                    },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(1.dp, NavyBorder)
                ) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = null,
                        tint = PureWhite,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Share Quote", color = PureWhite)
                }
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Composable
private fun CartItemRow(
    item: CartItem,
    state: JptUiState,
    onQtyChange: (Int) -> Unit,
    onRemove: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = NavySurface),
        border = BorderStroke(1.dp, NavyBorder)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = item.product.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = PureWhite
                    )
                    Text(
                        text = "${item.product.partCode} • ${item.product.grade.gradeName}",
                        style = MaterialTheme.typography.labelSmall,
                        color = SlateMuted
                    )
                }

                IconButton(
                    onClick = onRemove,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Remove item",
                        tint = SlateLight
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = { onQtyChange(maxOf(0, item.quantity - 5)) },
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(NavyElevated)
                    ) {
                        Icon(Icons.Default.Remove, contentDescription = "Decrease", tint = PureWhite)
                    }

                    Text(
                        text = "${item.quantity} pcs",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = GoldLight,
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )

                    IconButton(
                        onClick = { onQtyChange(item.quantity + 5) },
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(NavyElevated)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Increase", tint = PureWhite)
                    }
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = state.formatPrice(item.totalPrice),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.ExtraBold,
                        color = GoldAccent
                    )
                    Text(
                        text = "@ ${state.formatPrice(item.unitPrice)}/pc",
                        style = MaterialTheme.typography.labelSmall,
                        color = SlateMuted
                    )
                }
            }
        }
    }
}

@Composable
private fun SummaryRow(label: String, value: String, isAccent: Boolean = false) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = if (isAccent) GoldLight else SlateLight
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = if (isAccent) GoldAccent else PureWhite
        )
    }
}
