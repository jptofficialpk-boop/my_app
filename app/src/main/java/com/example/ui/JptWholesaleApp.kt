package com.example.ui

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.models.AppLanguage
import com.example.ui.components.BusinessCardModal
import com.example.ui.components.JptHeader
import com.example.ui.components.ProductDetailDialog
import com.example.ui.components.openWhatsApp
import com.example.ui.screens.AdminPanelScreen
import com.example.ui.screens.BulkOrderMatrixScreen
import com.example.ui.screens.CartAndQuoteScreen
import com.example.ui.screens.CatalogScreen
import com.example.ui.screens.ContactAndTrustScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.InvoiceViewScreen
import com.example.ui.screens.WarrantyClaimScreen
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.GoldBorder
import com.example.ui.theme.GoldDark
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.NavyBorder
import com.example.ui.theme.NavyDarkest
import com.example.ui.theme.NavyDeep
import com.example.ui.theme.NavyElevated
import com.example.ui.theme.PureWhite
import com.example.ui.theme.SlateLight
import com.example.ui.theme.SlateMuted
import com.example.ui.theme.WhatsAppGreen

sealed class NavDestination(
    val screen: AppScreen,
    val titleEn: String,
    val titleUr: String,
    val icon: ImageVector,
    val testTag: String
) {
    object Home : NavDestination(AppScreen.HOME, "Home", "ہوم", Icons.Default.Home, "nav_home")
    object Catalog : NavDestination(AppScreen.CATALOG, "Inventory", "اسٹاک", Icons.Default.Inventory2, "nav_catalog")
    object Matrix : NavDestination(AppScreen.BULK_MATRIX, "Bulk Matrix", "میٹرکس", Icons.Default.ListAlt, "nav_matrix")
    object Cart : NavDestination(AppScreen.CART_QUOTE, "Inquiry Quote", "کوٹیشن", Icons.Default.ShoppingCart, "nav_cart")
    object Contact : NavDestination(AppScreen.CONTACT_TRUST, "JPT HQ", "رابطہ", Icons.Default.Business, "nav_contact")
}

val navDestinations = listOf(
    NavDestination.Home,
    NavDestination.Catalog,
    NavDestination.Matrix,
    NavDestination.Cart,
    NavDestination.Contact
)

@Composable
fun JptWholesaleApp(
    viewModel: JptViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    // Handle back button
    BackHandler(enabled = state.currentScreen != AppScreen.HOME || state.isBusinessCardVisible || state.selectedProductDetail != null) {
        when {
            state.isBusinessCardVisible -> viewModel.toggleBusinessCard(false)
            state.selectedProductDetail != null -> viewModel.showProductDetail(null)
            else -> viewModel.setScreen(AppScreen.HOME)
        }
    }

    Scaffold(
        contentWindowInsets = WindowInsets.safeDrawing,
        topBar = {
            JptHeader(state = state, viewModel = viewModel)
        },
        bottomBar = {
            NavigationBar(
                containerColor = NavyDarkest,
                contentColor = PureWhite,
                tonalElevation = 8.dp,
                modifier = Modifier
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    .border(
                        BorderStroke(1.dp, GoldBorder.copy(alpha = 0.4f)),
                        RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                    )
            ) {
                navDestinations.forEach { dest ->
                    val isSelected = state.currentScreen == dest.screen
                    NavigationBarItem(
                        selected = isSelected,
                        onClick = { viewModel.setScreen(dest.screen) },
                        icon = {
                            if (dest == NavDestination.Cart && state.totalUnitsInCart > 0) {
                                BadgedBox(
                                    badge = {
                                        Badge(
                                            containerColor = GoldPrimary,
                                            contentColor = NavyDarkest
                                        ) {
                                            Text(
                                                text = "${state.totalUnitsInCart}",
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 10.sp
                                            )
                                        }
                                    }
                                ) {
                                    Icon(
                                        imageVector = dest.icon,
                                        contentDescription = dest.titleEn,
                                        modifier = Modifier.size(22.dp)
                                    )
                                }
                            } else {
                                Icon(
                                    imageVector = dest.icon,
                                    contentDescription = dest.titleEn,
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                        },
                        label = {
                            Text(
                                text = if (state.language == AppLanguage.URDU) dest.titleUr else dest.titleEn,
                                fontSize = 11.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = NavyDarkest,
                            selectedTextColor = GoldLight,
                            unselectedIconColor = SlateMuted,
                            unselectedTextColor = SlateMuted,
                            indicatorColor = GoldPrimary
                        ),
                        modifier = Modifier.testTag(dest.testTag)
                    )
                }
            }
        },
        floatingActionButton = {
            // Floating WhatsApp Quick Inquire FAB
            if (state.currentScreen != AppScreen.CART_QUOTE) {
                FloatingActionButton(
                    onClick = {
                        openWhatsApp(
                            context,
                            "+923700357848",
                            "Hello Muhammad Jamal (JPT Wholesale), I am inquiring from the app about mobile screen inventory."
                        )
                    },
                    containerColor = WhatsAppGreen,
                    contentColor = PureWhite,
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.testTag("floating_whatsapp_fab")
                ) {
                    Icon(
                        imageVector = Icons.Default.Badge,
                        contentDescription = "WhatsApp Sales",
                        tint = PureWhite,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            AnimatedContent(
                targetState = state.currentScreen,
                transitionSpec = { fadeIn() togetherWith fadeOut() },
                label = "ScreenTransition"
            ) { targetScreen ->
                when (targetScreen) {
                    AppScreen.HOME -> HomeScreen(state = state, viewModel = viewModel)
                    AppScreen.CATALOG -> CatalogScreen(state = state, viewModel = viewModel)
                    AppScreen.BULK_MATRIX -> BulkOrderMatrixScreen(state = state, viewModel = viewModel)
                    AppScreen.CART_QUOTE -> CartAndQuoteScreen(state = state, viewModel = viewModel)
                    AppScreen.CONTACT_TRUST -> ContactAndTrustScreen(state = state, viewModel = viewModel)
                    AppScreen.ADMIN_PANEL -> AdminPanelScreen(state = state, viewModel = viewModel)
                    AppScreen.INVOICE_VIEW -> InvoiceViewScreen(state = state, viewModel = viewModel)
                    AppScreen.WARRANTY_CLAIM -> WarrantyClaimScreen(state = state, viewModel = viewModel)
                }
            }
        }
    }

    // Product Detail Modal
    state.selectedProductDetail?.let { product ->
        ProductDetailDialog(
            product = product,
            state = state,
            onDismiss = { viewModel.showProductDetail(null) },
            onAddToCart = { prod, qty ->
                viewModel.addToCart(prod, qty)
            }
        )
    }

    // Business Card Modal
    if (state.isBusinessCardVisible) {
        BusinessCardModal(
            onDismiss = { viewModel.toggleBusinessCard(false) }
        )
    }
}
