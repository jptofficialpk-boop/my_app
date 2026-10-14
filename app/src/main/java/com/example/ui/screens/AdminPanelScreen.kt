package com.example.ui.screens

import android.widget.Toast
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.ToggleOff
import androidx.compose.material.icons.filled.ToggleOn
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.AlertDialog
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
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.models.Brand
import com.example.data.models.DisplayCategory
import com.example.data.models.OrderStatus
import com.example.data.models.PaymentStatus
import com.example.data.models.PlacedOrder
import com.example.data.models.ProductItem
import com.example.data.models.QualityGrade
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
import com.example.util.PdfInvoiceGenerator
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun AdminPanelScreen(
    state: JptUiState,
    viewModel: JptViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var selectedAdminTab by remember { mutableIntStateOf(0) }
    var adminSearchQuery by remember { mutableStateOf("") }
    var selectedBrandFilter by remember { mutableStateOf<Brand?>(null) }
    var showAddNewDialog by remember { mutableStateOf(false) }
    var productToDelete by remember { mutableStateOf<ProductItem?>(null) }

    if (!state.isAdminLoggedIn) {
        // ADMIN LOGIN / AUTHENTICATION GATE
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(NavyDeep)
                .padding(20.dp),
            contentAlignment = Alignment.Center
        ) {
            CircuitBackground(circuitAlpha = 0.45f)

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("admin_login_card"),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = NavyDarkest),
                border = BorderStroke(1.5.dp, GoldPrimary)
            ) {
                Column(
                    modifier = Modifier.padding(22.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                            .background(NavyElevated)
                            .border(1.5.dp, GoldPrimary, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.AdminPanelSettings,
                            contentDescription = "Admin Security",
                            tint = GoldPrimary,
                            modifier = Modifier.size(36.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "JPT PROPRIETOR ADMIN PANEL",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.ExtraBold,
                        color = GoldLight,
                        letterSpacing = 1.sp
                    )

                    Text(
                        text = "Inventory Control, Rates & Khata Ledger",
                        style = MaterialTheme.typography.bodyMedium,
                        color = SlateMuted
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    OutlinedTextField(
                        value = state.adminPinInput,
                        onValueChange = { viewModel.setAdminPinInput(it) },
                        label = { Text("Enter Admin PIN / Passcode", color = SlateLight) },
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("admin_pin_input"),
                        leadingIcon = {
                            Icon(Icons.Default.Lock, contentDescription = null, tint = GoldAccent)
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = GoldPrimary,
                            unfocusedBorderColor = NavyBorder,
                            focusedTextColor = PureWhite,
                            unfocusedTextColor = PureWhite
                        ),
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp)
                    )

                    if (state.adminErrorMessage != null) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = state.adminErrorMessage,
                            color = Color(0xFFEF4444),
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { viewModel.loginAdmin(state.adminPinInput) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("admin_login_submit_btn"),
                        colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "Access Admin Dashboard",
                            color = NavyDarkest,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(NavyElevated)
                            .clickable { viewModel.loginAdmin("7848") }
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Security,
                            contentDescription = null,
                            tint = GoldLight,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Proprietor Fast Login (PIN: 7848 / 0370)",
                            style = MaterialTheme.typography.labelSmall,
                            color = GoldLight
                        )
                    }
                }
            }
        }
        return
    }

    // -----------------------------------------------------------------
    // LOGGED-IN ADMIN DASHBOARD
    // -----------------------------------------------------------------
    val tabs = listOf("Rates & Stock", "Orders & Bills", "Khata Ledger", "Alerts & Claims")

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(NavyDeep)
    ) {
        // Admin Top Bar
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = NavyDarkest,
            shadowElevation = 6.dp
        ) {
            Column(modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .clip(CircleShape)
                                .background(GoldDark),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Tune, contentDescription = null, tint = GoldLight, modifier = Modifier.size(18.dp))
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                text = "JPT PROPRIETOR DASHBOARD",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = PureWhite
                            )
                            Text(
                                text = "Muhammad Jamal • Hall Road",
                                style = MaterialTheme.typography.labelSmall,
                                color = GoldAccent
                            )
                        }
                    }

                    IconButton(
                        onClick = { viewModel.logoutAdmin() },
                        modifier = Modifier.size(34.dp)
                    ) {
                        Icon(Icons.Default.Logout, contentDescription = "Logout", tint = SlateLight)
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Scrollable Tabs
                ScrollableTabRow(
                    selectedTabIndex = selectedAdminTab,
                    containerColor = NavyDarkest,
                    contentColor = GoldPrimary,
                    edgePadding = 0.dp,
                    indicator = { tabPositions ->
                        TabRowDefaults.SecondaryIndicator(
                            modifier = Modifier.tabIndicatorOffset(tabPositions[selectedAdminTab]),
                            color = GoldPrimary,
                            height = 3.dp
                        )
                    }
                ) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedAdminTab == index,
                            onClick = { selectedAdminTab = index },
                            text = {
                                Text(
                                    text = title,
                                    fontWeight = if (selectedAdminTab == index) FontWeight.Bold else FontWeight.Normal,
                                    color = if (selectedAdminTab == index) GoldLight else SlateMuted,
                                    fontSize = 12.sp
                                )
                            }
                        )
                    }
                }
            }
        }

        // TAB CONTENT
        when (selectedAdminTab) {
            0 -> {
                // TAB 0: RATES, INVENTORY & STOCK MANAGER
                val filteredAdminProducts = state.products.filter { prod ->
                    val matchesSearch = adminSearchQuery.isBlank() ||
                            prod.title.contains(adminSearchQuery, ignoreCase = true) ||
                            prod.partCode.contains(adminSearchQuery, ignoreCase = true) ||
                            prod.series.contains(adminSearchQuery, ignoreCase = true)

                    val matchesBrand = selectedBrandFilter == null || prod.brand == selectedBrandFilter
                    matchesSearch && matchesBrand
                }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 14.dp)
                ) {
                    item {
                        Spacer(modifier = Modifier.height(10.dp))

                        // Daily Rate List Export Banner Button
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    val pdf = PdfInvoiceGenerator.generateDailyRateListPdf(context, state.products)
                                    if (pdf != null) {
                                        Toast.makeText(context, "Rate List PDF Generated! Sharing to WhatsApp...", Toast.LENGTH_SHORT).show()
                                        val caption = "📢 *JAMAL PRECISION TECH (JPT)*\nOfficial Daily Wholesale Display Rate List • Hall Road, Lahore.\nFor Orders WhatsApp: 0370-0357848"
                                        PdfInvoiceGenerator.sharePdfOnWhatsApp(context, pdf, "+923700357848", caption)
                                    }
                                }
                                .testTag("export_rate_list_btn"),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = NavySurface),
                            border = BorderStroke(1.2.dp, GoldPrimary)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.PictureAsPdf, contentDescription = null, tint = GoldPrimary, modifier = Modifier.size(24.dp))
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Column {
                                        Text("Export Today's Rate List PDF", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = PureWhite)
                                        Text("Direct share for WhatsApp Status & Shopkeeper Groups", style = MaterialTheme.typography.labelSmall, color = GoldLight)
                                    }
                                }
                                Icon(Icons.Default.CheckCircle, contentDescription = null, tint = StatusInStock, modifier = Modifier.size(18.dp))
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Controls: Add New Model & Reset
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = { showAddNewDialog = true },
                                modifier = Modifier
                                    .weight(1.3f)
                                    .height(40.dp)
                                    .testTag("admin_add_model_btn"),
                                colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Icon(Icons.Default.Add, contentDescription = null, tint = NavyDarkest, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("+ Add New Screen", color = NavyDarkest, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            }

                            OutlinedButton(
                                onClick = { viewModel.resetRatesToDefault() },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(40.dp),
                                border = BorderStroke(1.dp, NavyBorder),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Icon(Icons.Default.Refresh, contentDescription = null, tint = SlateLight, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Reset All", color = SlateLight, fontSize = 12.sp)
                            }
                        }

                        if (state.adminSuccessMessage != null) {
                            Spacer(modifier = Modifier.height(10.dp))
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(StatusInStock.copy(alpha = 0.15f))
                                    .border(1.dp, StatusInStock, RoundedCornerShape(8.dp))
                                    .padding(8.dp)
                            ) {
                                Text(state.adminSuccessMessage, color = StatusInStock, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Search Bar
                        OutlinedTextField(
                            value = adminSearchQuery,
                            onValueChange = { adminSearchQuery = it },
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = { Text("Filter model to adjust rates...", color = SlateMuted) },
                            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = GoldAccent) },
                            singleLine = true,
                            shape = RoundedCornerShape(10.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = GoldPrimary,
                                unfocusedBorderColor = NavyBorder,
                                focusedTextColor = PureWhite,
                                unfocusedTextColor = PureWhite,
                                focusedContainerColor = NavySurface,
                                unfocusedContainerColor = NavyDeep
                            )
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Brand Filters
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            item {
                                AdminFilterChip("All", selectedBrandFilter == null) { selectedBrandFilter = null }
                            }
                            items(Brand.values()) { brand ->
                                AdminFilterChip(brand.displayName, selectedBrandFilter == brand) {
                                    selectedBrandFilter = if (selectedBrandFilter == brand) null else brand
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))
                    }

                    items(filteredAdminProducts) { product ->
                        AdminProductItemCard(
                            product = product,
                            onEditRates = { viewModel.startEditingProduct(product) },
                            onToggleStock = { viewModel.toggleStockAvailability(product.id) },
                            onDelete = { productToDelete = product }
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                    }

                    item { Spacer(modifier = Modifier.height(30.dp)) }
                }
            }

            1 -> {
                // TAB 1: ORDER & BILL MANAGEMENT
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 14.dp)
                ) {
                    item {
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "RECEIVED WHOLESALE ORDERS (${state.placedOrders.size})",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold,
                            color = GoldLight,
                            letterSpacing = 1.sp
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                    }

                    if (state.placedOrders.isEmpty()) {
                        item {
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(containerColor = NavySurface)
                            ) {
                                Column(modifier = Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text("No orders placed yet", color = SlateLight)
                                }
                            }
                        }
                    } else {
                        items(state.placedOrders) { order ->
                            AdminOrderCard(
                                order = order,
                                onViewInvoice = { viewModel.viewOrderInvoice(context, order) },
                                onUpdateStatus = { newStatus, newPayment ->
                                    viewModel.updateOrderStatus(order.orderId, newStatus, newPayment)
                                }
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                        }
                    }

                    item { Spacer(modifier = Modifier.height(30.dp)) }
                }
            }

            2 -> {
                // TAB 2: CUSTOMER LEDGER / KHATA SUMMARY
                val totalPendingReceivable = state.khataAccounts.sumOf { it.pendingBalancePkr }
                val totalBilled = state.khataAccounts.sumOf { it.totalBilledPkr }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 14.dp)
                ) {
                    item {
                        Spacer(modifier = Modifier.height(12.dp))

                        // Khata KPI Card
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = NavyDarkest),
                            border = BorderStroke(1.5.dp, GoldPrimary)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text("RETAIL KHATA & RECEIVABLE SUMMARY", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, color = GoldLight)
                                Spacer(modifier = Modifier.height(10.dp))
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Column {
                                        Text("Total Outstanding Balance", style = MaterialTheme.typography.labelSmall, color = SlateMuted)
                                        Text("Rs. %,d".format(totalPendingReceivable), style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.ExtraBold, color = Color(0xFFEF4444))
                                    }
                                    Column(horizontalAlignment = Alignment.End) {
                                        Text("Total Ledger Billed", style = MaterialTheme.typography.labelSmall, color = SlateMuted)
                                        Text("Rs. %,d".format(totalBilled), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = GoldLight)
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))
                        Text("SHOPKEEPER BALANCES", style = MaterialTheme.typography.labelSmall, color = GoldLight, letterSpacing = 1.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    items(state.khataAccounts) { khata ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = NavySurface),
                            border = BorderStroke(1.dp, NavyBorder)
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Column {
                                        Text(khata.shopName, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = PureWhite)
                                        Text("${khata.customerName} • ${khata.customerPhone} • ${khata.city}", style = MaterialTheme.typography.bodySmall, color = SlateMuted)
                                    }
                                    Column(horizontalAlignment = Alignment.End) {
                                        Text("Balance Due", style = MaterialTheme.typography.labelSmall, color = SlateMuted)
                                        Text(
                                            text = if (khata.pendingBalancePkr > 0) "Rs. %,d".format(khata.pendingBalancePkr) else "CLEARED",
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.Bold,
                                            color = if (khata.pendingBalancePkr > 0) Color(0xFFEF4444) else StatusInStock
                                        )
                                    }
                                }

                                if (khata.pendingBalancePkr > 0) {
                                    Spacer(modifier = Modifier.height(10.dp))
                                    Button(
                                        onClick = {
                                            val reminder = "Assalam o Alaikum ${khata.customerName} Bhai, gentle reminder from Jamal Precision Tech (Hall Road) regarding pending wholesale balance of Rs. %,d for ${khata.shopName}. Please transfer to Meezan Bank account.".format(khata.pendingBalancePkr)
                                            openWhatsApp(context, khata.customerPhone, reminder)
                                        },
                                        modifier = Modifier.fillMaxWidth().height(36.dp),
                                        shape = RoundedCornerShape(8.dp),
                                        colors = ButtonDefaults.buttonColors(containerColor = WhatsAppGreen)
                                    ) {
                                        Text("Send WhatsApp Payment Reminder", color = PureWhite, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                    }
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    item { Spacer(modifier = Modifier.height(30.dp)) }
                }
            }

            3 -> {
                // TAB 3: STOCK ALERTS & WARRANTY CLAIMS
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 14.dp)
                ) {
                    item {
                        Spacer(modifier = Modifier.height(12.dp))
                        Text("CLIENT OUT-OF-STOCK REQUESTS (${state.stockAlertRequests.size})", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, color = GoldLight)
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    if (state.stockAlertRequests.isEmpty()) {
                        item {
                            Text("No stock alert requests pending.", color = SlateMuted, style = MaterialTheme.typography.bodySmall)
                            Spacer(modifier = Modifier.height(14.dp))
                        }
                    } else {
                        items(state.stockAlertRequests) { alert ->
                            Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = NavySurface)) {
                                Column(modifier = Modifier.padding(10.dp)) {
                                    Text("${alert.productTitle} (${alert.partCode})", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = PureWhite)
                                    Text("Requested by: ${alert.shopName} (${alert.customerPhone}) for ${alert.requestedQuantity} pcs", style = MaterialTheme.typography.bodySmall, color = GoldLight)
                                }
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                        }
                    }

                    item {
                        Spacer(modifier = Modifier.height(14.dp))
                        Text("WARRANTY REPLACEMENT CLAIMS (${state.warrantyClaims.size})", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, color = GoldLight)
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    if (state.warrantyClaims.isEmpty()) {
                        item {
                            Text("No warranty claims submitted.", color = SlateMuted, style = MaterialTheme.typography.bodySmall)
                        }
                    } else {
                        items(state.warrantyClaims) { claim ->
                            Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = NavySurface)) {
                                Column(modifier = Modifier.padding(10.dp)) {
                                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                        Text(claim.claimId, style = MaterialTheme.typography.labelSmall, color = GoldAccent, fontWeight = FontWeight.Bold)
                                        Text("${claim.quantityDefective} pcs defective", style = MaterialTheme.typography.labelSmall, color = Color(0xFFEF4444), fontWeight = FontWeight.Bold)
                                    }
                                    Text(claim.productTitle, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, color = PureWhite)
                                    Text("Shop: ${claim.shopName} • Issue: ${claim.faultType}", style = MaterialTheme.typography.bodySmall, color = SlateLight)
                                    Text("Details: ${claim.details}", style = MaterialTheme.typography.bodySmall, color = SlateMuted)
                                }
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                        }
                    }

                    item { Spacer(modifier = Modifier.height(30.dp)) }
                }
            }
        }
    }

    // Edit Rate Dialog
    state.adminEditingProduct?.let { product ->
        AdminEditRateModal(
            product = product,
            onDismiss = { viewModel.startEditingProduct(null) },
            onSave = { t1, t2, t3, stock, moq ->
                viewModel.saveProductRateChanges(product.id, t1, t2, t3, stock, moq)
            }
        )
    }

    // Add New Model Dialog
    if (showAddNewDialog) {
        AdminAddNewModelModal(
            onDismiss = { showAddNewDialog = false },
            onAdd = { brand, series, title, partCode, category, grade, stock, moq, t1, t2, t3 ->
                viewModel.addNewCustomProduct(brand, series, title, partCode, category, grade, stock, moq, t1, t2, t3)
                showAddNewDialog = false
            }
        )
    }

    // Delete Confirmation Dialog
    productToDelete?.let { prod ->
        AlertDialog(
            onDismissRequest = { productToDelete = null },
            title = { Text("Delete Model from Catalog?", color = PureWhite, fontWeight = FontWeight.Bold) },
            text = { Text("Are you sure you want to permanently remove ${prod.title} (${prod.partCode}) from the wholesale catalog?", color = SlateLight) },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.deleteProduct(prod.id)
                        productToDelete = null
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEF4444))
                ) {
                    Text("Delete", color = PureWhite, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                OutlinedButton(onClick = { productToDelete = null }) {
                    Text("Cancel", color = SlateLight)
                }
            },
            containerColor = NavyDarkest,
            shape = RoundedCornerShape(14.dp)
        )
    }
}

@Composable
private fun AdminFilterChip(label: String, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(if (isSelected) GoldPrimary else NavyElevated)
            .border(1.dp, if (isSelected) GoldLight else NavyBorder, RoundedCornerShape(6.dp))
            .clickable { onClick() }
            .padding(horizontal = 8.dp, vertical = 5.dp)
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
private fun AdminProductItemCard(
    product: ProductItem,
    onEditRates: () -> Unit,
    onToggleStock: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = NavySurface),
        border = BorderStroke(1.dp, GoldBorder.copy(alpha = 0.4f))
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(product.title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = PureWhite)
                    Text("${product.brand.displayName} • ${product.partCode} • ${product.grade.gradeName}", style = MaterialTheme.typography.labelSmall, color = SlateMuted)
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Stock Status 1-Tap Toggle
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(if (product.isAvailable) StatusInStock.copy(alpha = 0.2f) else Color(0xFFEF4444).copy(alpha = 0.2f))
                            .border(1.dp, if (product.isAvailable) StatusInStock else Color(0xFFEF4444), RoundedCornerShape(6.dp))
                            .clickable { onToggleStock() }
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = if (product.isAvailable) "IN STOCK" else "OUT STOCK",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = if (product.isAvailable) StatusInStock else Color(0xFFEF4444)
                        )
                    }

                    Spacer(modifier = Modifier.width(6.dp))

                    IconButton(onClick = onDelete, modifier = Modifier.size(30.dp)) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color(0xFFEF4444), modifier = Modifier.size(18.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Rates Grid
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(NavyElevated)
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("10+ pcs", style = MaterialTheme.typography.labelSmall, color = SlateMuted)
                    Text("Rs. %,d".format(product.pricing.tier1PricePkr), style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, color = PureWhite)
                }
                Column {
                    Text("50+ pcs", style = MaterialTheme.typography.labelSmall, color = SlateMuted)
                    Text("Rs. %,d".format(product.pricing.tier2PricePkr), style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, color = GoldLight)
                }
                Column {
                    Text("100+ pcs", style = MaterialTheme.typography.labelSmall, color = SlateMuted)
                    Text("Rs. %,d".format(product.pricing.tier3PricePkr), style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, color = GoldAccent)
                }

                Button(
                    onClick = onEditRates,
                    colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary),
                    shape = RoundedCornerShape(6.dp),
                    modifier = Modifier.height(32.dp)
                ) {
                    Text("Edit Rates", color = NavyDarkest, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                }
            }
        }
    }
}

@Composable
private fun AdminOrderCard(
    order: PlacedOrder,
    onViewInvoice: () -> Unit,
    onUpdateStatus: (OrderStatus, PaymentStatus) -> Unit
) {
    val sdf = SimpleDateFormat("dd MMM, hh:mm a", Locale.getDefault())

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = NavySurface),
        border = BorderStroke(1.dp, NavyBorder)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(order.orderId, style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, color = GoldAccent)
                    Text(order.shopName, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = PureWhite)
                    Text("${order.customerName} • ${order.customerPhone} • ${order.customerCity}", style = MaterialTheme.typography.bodySmall, color = SlateMuted)
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text("Rs. %,d".format(order.netTotal), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.ExtraBold, color = GoldLight)
                    Text(sdf.format(Date(order.timestamp)), style = MaterialTheme.typography.labelSmall, color = SlateMuted)
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Badges row: Order Status & Payment Status
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color(order.status.badgeColorHex).copy(alpha = 0.2f))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(order.status.displayName, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = Color(order.status.badgeColorHex))
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color(order.paymentStatus.badgeColorHex).copy(alpha = 0.2f))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(order.paymentStatus.displayName, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = Color(order.paymentStatus.badgeColorHex))
                    }
                }

                Button(
                    onClick = onViewInvoice,
                    colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.height(34.dp)
                ) {
                    Icon(Icons.Default.Receipt, contentDescription = null, tint = NavyDarkest, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Invoice", color = NavyDarkest, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Quick Status Toggles for Admin
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                OutlinedButton(
                    onClick = {
                        val nextStatus = if (order.status == OrderStatus.PENDING) OrderStatus.DISPATCHED else OrderStatus.DELIVERED
                        onUpdateStatus(nextStatus, order.paymentStatus)
                    },
                    modifier = Modifier.weight(1f).height(32.dp),
                    shape = RoundedCornerShape(6.dp),
                    border = BorderStroke(1.dp, NavyBorder)
                ) {
                    Text(
                        text = if (order.status == OrderStatus.PENDING) "Mark Dispatched" else "Mark Delivered",
                        fontSize = 11.sp,
                        color = SlateLight
                    )
                }

                OutlinedButton(
                    onClick = {
                        val nextPay = if (order.paymentStatus == PaymentStatus.UNPAID) PaymentStatus.PAID else PaymentStatus.UNPAID
                        onUpdateStatus(order.status, nextPay)
                    },
                    modifier = Modifier.weight(1f).height(32.dp),
                    shape = RoundedCornerShape(6.dp),
                    border = BorderStroke(1.dp, NavyBorder)
                ) {
                    Text(
                        text = if (order.paymentStatus == PaymentStatus.UNPAID) "Mark Paid" else "Mark Unpaid",
                        fontSize = 11.sp,
                        color = GoldLight
                    )
                }
            }
        }
    }
}

@Composable
private fun AdminEditRateModal(
    product: ProductItem,
    onDismiss: () -> Unit,
    onSave: (Int, Int, Int, Int, Int) -> Unit
) {
    var t1Text by remember { mutableStateOf(product.pricing.tier1PricePkr.toString()) }
    var t2Text by remember { mutableStateOf(product.pricing.tier2PricePkr.toString()) }
    var t3Text by remember { mutableStateOf(product.pricing.tier3PricePkr.toString()) }
    var stockText by remember { mutableStateOf(product.stockCount.toString()) }
    var moqText by remember { mutableStateOf(product.moq.toString()) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier.fillMaxWidth().padding(6.dp),
            shape = RoundedCornerShape(18.dp),
            color = NavyDarkest,
            border = BorderStroke(1.5.dp, GoldPrimary)
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Text("EDIT WHOLESALE RATES", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = GoldAccent)
                Text(product.title, style = MaterialTheme.typography.bodySmall, color = SlateLight)
                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = t1Text, onValueChange = { t1Text = it },
                    label = { Text("Tier 1 Rate (10–49 pcs) - PKR", color = SlateMuted) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = GoldPrimary, unfocusedBorderColor = NavyBorder, focusedTextColor = PureWhite, unfocusedTextColor = PureWhite)
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = t2Text, onValueChange = { t2Text = it },
                    label = { Text("Tier 2 Rate (50–99 pcs) - PKR", color = SlateMuted) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = GoldPrimary, unfocusedBorderColor = NavyBorder, focusedTextColor = PureWhite, unfocusedTextColor = PureWhite)
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = t3Text, onValueChange = { t3Text = it },
                    label = { Text("Tier 3 Bulk Rate (100+ pcs) - PKR", color = SlateMuted) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = GoldPrimary, unfocusedBorderColor = NavyBorder, focusedTextColor = PureWhite, unfocusedTextColor = PureWhite)
                )
                Spacer(modifier = Modifier.height(8.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = stockText, onValueChange = { stockText = it },
                        label = { Text("Stock (pcs)", color = SlateMuted) },
                        modifier = Modifier.weight(1f),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = GoldPrimary, unfocusedBorderColor = NavyBorder, focusedTextColor = PureWhite, unfocusedTextColor = PureWhite)
                    )
                    OutlinedTextField(
                        value = moqText, onValueChange = { moqText = it },
                        label = { Text("MOQ", color = SlateMuted) },
                        modifier = Modifier.weight(1f),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = GoldPrimary, unfocusedBorderColor = NavyBorder, focusedTextColor = PureWhite, unfocusedTextColor = PureWhite)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        val t1 = t1Text.toIntOrNull() ?: product.pricing.tier1PricePkr
                        val t2 = t2Text.toIntOrNull() ?: product.pricing.tier2PricePkr
                        val t3 = t3Text.toIntOrNull() ?: product.pricing.tier3PricePkr
                        val st = stockText.toIntOrNull() ?: product.stockCount
                        val mq = moqText.toIntOrNull() ?: product.moq
                        onSave(t1, t2, t3, st, mq)
                    },
                    modifier = Modifier.fillMaxWidth().height(46.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Save Rates", color = NavyDarkest, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
private fun AdminAddNewModelModal(
    onDismiss: () -> Unit,
    onAdd: (Brand, String, String, String, DisplayCategory, QualityGrade, Int, Int, Int, Int, Int) -> Unit
) {
    var selectedBrand by remember { mutableStateOf(Brand.SAMSUNG) }
    var seriesText by remember { mutableStateOf("Galaxy A Series") }
    var titleText by remember { mutableStateOf("") }
    var partCodeText by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf(DisplayCategory.LCD_PANEL) }
    var selectedGrade by remember { mutableStateOf(QualityGrade.CROWN_HIGH_COPY) }
    var stockText by remember { mutableStateOf("300") }
    var moqText by remember { mutableStateOf("10") }
    var t1Text by remember { mutableStateOf("2600") }
    var t2Text by remember { mutableStateOf("2450") }
    var t3Text by remember { mutableStateOf("2300") }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier.fillMaxWidth().padding(4.dp),
            shape = RoundedCornerShape(18.dp),
            color = NavyDarkest,
            border = BorderStroke(1.5.dp, GoldPrimary)
        ) {
            LazyColumn(modifier = Modifier.padding(16.dp)) {
                item {
                    Text("ADD NEW DISPLAY MODEL", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = GoldAccent)
                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = titleText, onValueChange = { titleText = it },
                        label = { Text("Model Title (e.g. Vivo Y100 Display)", color = SlateMuted) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = GoldPrimary, unfocusedBorderColor = NavyBorder, focusedTextColor = PureWhite, unfocusedTextColor = PureWhite)
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    OutlinedTextField(
                        value = partCodeText, onValueChange = { partCodeText = it },
                        label = { Text("Part Code (e.g. JPT-VIV-Y100)", color = SlateMuted) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = GoldPrimary, unfocusedBorderColor = NavyBorder, focusedTextColor = PureWhite, unfocusedTextColor = PureWhite)
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        OutlinedTextField(
                            value = t1Text, onValueChange = { t1Text = it },
                            label = { Text("10+ Rate", color = SlateMuted) },
                            modifier = Modifier.weight(1f),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = GoldPrimary, unfocusedBorderColor = NavyBorder, focusedTextColor = PureWhite, unfocusedTextColor = PureWhite)
                        )
                        OutlinedTextField(
                            value = t2Text, onValueChange = { t2Text = it },
                            label = { Text("50+ Rate", color = SlateMuted) },
                            modifier = Modifier.weight(1f),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = GoldPrimary, unfocusedBorderColor = NavyBorder, focusedTextColor = PureWhite, unfocusedTextColor = PureWhite)
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        OutlinedTextField(
                            value = stockText, onValueChange = { stockText = it },
                            label = { Text("Stock pcs", color = SlateMuted) },
                            modifier = Modifier.weight(1f),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = GoldPrimary, unfocusedBorderColor = NavyBorder, focusedTextColor = PureWhite, unfocusedTextColor = PureWhite)
                        )
                        OutlinedTextField(
                            value = moqText, onValueChange = { moqText = it },
                            label = { Text("MOQ", color = SlateMuted) },
                            modifier = Modifier.weight(1f),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = GoldPrimary, unfocusedBorderColor = NavyBorder, focusedTextColor = PureWhite, unfocusedTextColor = PureWhite)
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Button(
                        onClick = {
                            if (titleText.isNotBlank()) {
                                onAdd(
                                    selectedBrand, seriesText, titleText,
                                    partCodeText.ifBlank { "JPT-${System.currentTimeMillis() % 1000}" },
                                    selectedCategory, selectedGrade,
                                    stockText.toIntOrNull() ?: 200, moqText.toIntOrNull() ?: 10,
                                    t1Text.toIntOrNull() ?: 2000, t2Text.toIntOrNull() ?: 1900, t3Text.toIntOrNull() ?: 1800
                                )
                            }
                        },
                        modifier = Modifier.fillMaxWidth().height(46.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Add to Wholesale Inventory", color = NavyDarkest, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
