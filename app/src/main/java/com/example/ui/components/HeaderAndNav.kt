package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Store
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.models.AppCurrency
import com.example.data.models.AppLanguage
import com.example.data.models.Brand
import com.example.data.models.DisplayCategory
import com.example.data.models.UserRole
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

@Composable
fun JptHeader(
    state: JptUiState,
    viewModel: JptViewModel,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current
    var showRegistrationDialog by remember { mutableStateOf(false) }

    Surface(
        modifier = modifier.fillMaxWidth(),
        color = NavyDarkest,
        shadowElevation = 8.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 8.dp)
        ) {
            // Top branding row
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Left: Logo & Company Name
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clickable { viewModel.setScreen(AppScreen.HOME) }
                        .testTag("brand_home_button")
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_jpt_logo),
                        contentDescription = "JPT Logo",
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                            .border(1.5.dp, GoldPrimary, CircleShape)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = if (state.language == AppLanguage.URDU) "جمال پریسیژن ٹیک" else "JAMAL PRECISION TECH",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = PureWhite,
                            letterSpacing = 0.5.sp
                        )
                        Text(
                            text = if (state.language == AppLanguage.URDU) "موبائل ڈسپلے ہول سیل • ہال روڈ" else "Mobile Display Wholesale • Hall Road",
                            style = MaterialTheme.typography.labelSmall,
                            color = GoldAccent
                        )
                    }
                }

                // Right Utility actions
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Currency Switcher
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(NavyElevated)
                            .border(1.dp, GoldBorder, RoundedCornerShape(8.dp))
                            .clickable { viewModel.toggleCurrency() }
                            .padding(horizontal = 7.dp, vertical = 5.dp)
                            .testTag("currency_toggle_button")
                    ) {
                        Text(
                            text = if (state.currency == AppCurrency.PKR) "PKR" else "USD",
                            style = MaterialTheme.typography.labelSmall,
                            color = GoldLight,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.width(4.dp))

                    // Language Switcher (EN / اردو)
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(NavyElevated)
                            .border(1.dp, GoldBorder, RoundedCornerShape(8.dp))
                            .clickable { viewModel.toggleLanguage() }
                            .padding(horizontal = 7.dp, vertical = 5.dp)
                            .testTag("language_toggle_button")
                    ) {
                        Text(
                            text = if (state.language == AppLanguage.ENGLISH) "اردو" else "EN",
                            style = MaterialTheme.typography.labelSmall,
                            color = PureWhite,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.width(4.dp))

                    // Shop Profile Registration Dialog Trigger
                    IconButton(
                        onClick = { showRegistrationDialog = true },
                        modifier = Modifier
                            .size(34.dp)
                            .testTag("shop_profile_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Store,
                            contentDescription = "Shop Profile",
                            tint = GoldAccent
                        )
                    }

                    // Owner Admin Gate Icon
                    IconButton(
                        onClick = {
                            viewModel.setScreen(AppScreen.ADMIN_PANEL)
                        },
                        modifier = Modifier
                            .size(34.dp)
                            .testTag("owner_admin_gate_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Default.AdminPanelSettings,
                            contentDescription = "Admin Rate Manager",
                            tint = if (state.isAdminLoggedIn) GoldPrimary else SlateLight
                        )
                    }

                    // Cart / Inquiry Count
                    BadgedBox(
                        badge = {
                            if (state.totalUnitsInCart > 0) {
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
                        },
                        modifier = Modifier.clickable { viewModel.setScreen(AppScreen.CART_QUOTE) }
                    ) {
                        IconButton(
                            onClick = { viewModel.setScreen(AppScreen.CART_QUOTE) },
                            modifier = Modifier
                                .size(34.dp)
                                .testTag("header_cart_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.ShoppingCart,
                                contentDescription = "Bulk Cart",
                                tint = PureWhite
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // ULTRA-FAST MODEL SEARCH BAR
            OutlinedTextField(
                value = state.searchQuery,
                onValueChange = {
                    viewModel.setSearchQuery(it)
                    if (state.currentScreen != AppScreen.CATALOG) {
                        viewModel.setScreen(AppScreen.CATALOG)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("quick_search_input"),
                placeholder = {
                    Text(
                        text = if (state.language == AppLanguage.URDU)
                            "ماڈل یا پارٹ کوڈ تلاش کریں (مثلاً A12, iPhone 13, OLED...)"
                        else
                            "Search Model, Brand, or Tech (A12, iPhone 13, OLED, LCD...)",
                        style = MaterialTheme.typography.bodyMedium,
                        color = SlateMuted
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = GoldAccent
                    )
                },
                trailingIcon = {
                    if (state.searchQuery.isNotEmpty()) {
                        IconButton(onClick = { viewModel.setSearchQuery("") }) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Clear",
                                tint = SlateLight
                            )
                        }
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = NavySurface,
                    unfocusedContainerColor = NavyDeep,
                    focusedBorderColor = GoldPrimary,
                    unfocusedBorderColor = NavyBorder,
                    focusedTextColor = PureWhite,
                    unfocusedTextColor = PureWhite
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = { focusManager.clearFocus() })
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Quick Filter Chips below search bar
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                item {
                    HeaderQuickChip(
                        label = "All Screens",
                        isSelected = state.selectedCategory == DisplayCategory.ALL && state.selectedBrand == null,
                        onClick = {
                            viewModel.selectCategory(DisplayCategory.ALL)
                            viewModel.selectBrand(null)
                            viewModel.setScreen(AppScreen.CATALOG)
                        }
                    )
                }
                item {
                    HeaderQuickChip(
                        label = "Original OLED",
                        isSelected = state.selectedCategory == DisplayCategory.ORIGINAL_OLED,
                        onClick = {
                            viewModel.selectCategory(DisplayCategory.ORIGINAL_OLED)
                            viewModel.setScreen(AppScreen.CATALOG)
                        }
                    )
                }
                item {
                    HeaderQuickChip(
                        label = "LCD Panels",
                        isSelected = state.selectedCategory == DisplayCategory.LCD_PANEL,
                        onClick = {
                            viewModel.selectCategory(DisplayCategory.LCD_PANEL)
                            viewModel.setScreen(AppScreen.CATALOG)
                        }
                    )
                }
                item {
                    HeaderQuickChip(
                        label = "Claim Warranty",
                        isSelected = state.currentScreen == AppScreen.WARRANTY_CLAIM,
                        onClick = {
                            viewModel.setScreen(AppScreen.WARRANTY_CLAIM)
                        }
                    )
                }
            }
        }
    }

    if (showRegistrationDialog) {
        ShopRegistrationDialog(
            state = state,
            onDismiss = { showRegistrationDialog = false },
            onSaveProfile = { owner, shop, city, phone, carrier ->
                viewModel.updateCustomerDetails(owner, shop, city, phone, carrier)
            }
        )
    }
}

@Composable
private fun HeaderQuickChip(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(if (isSelected) GoldPrimary else NavyElevated)
            .border(0.8.dp, if (isSelected) GoldLight else NavyBorder, RoundedCornerShape(6.dp))
            .clickable { onClick() }
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            color = if (isSelected) NavyDarkest else SlateLight
        )
    }
}
