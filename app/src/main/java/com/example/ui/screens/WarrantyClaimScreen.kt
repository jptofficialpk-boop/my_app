package com.example.ui.screens

import android.widget.Toast
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.models.WarrantyClaim
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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun WarrantyClaimScreen(
    state: JptUiState,
    viewModel: JptViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var selectedModelTitle by remember { mutableStateOf(state.products.firstOrNull()?.title ?: "Samsung Galaxy A12 LCD") }
    var selectedPartCode by remember { mutableStateOf(state.products.firstOrNull()?.partCode ?: "JPT-SM-A12-INCELL") }
    var defectiveQty by remember { mutableIntStateOf(1) }
    var faultType by remember { mutableStateOf("Touch Digitizer Not Responding") }
    var detailsText by remember { mutableStateOf("") }
    var photoAttached by remember { mutableStateOf(true) }

    val faultOptions = listOf(
        "Touch Digitizer Not Responding",
        "Blank Screen / No Display",
        "Color Lines / Flickering",
        "Dead Pixels Detected",
        "Flex Connector Defect",
        "Glass Frame / Bezel Defect"
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(NavyDeep)
            .padding(horizontal = 14.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(10.dp))

            // TOP HEADER
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = NavyDarkest,
                shape = RoundedCornerShape(14.dp),
                border = BorderStroke(1.dp, GoldBorder)
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { viewModel.setScreen(AppScreen.HOME) },
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = GoldLight
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "WARRANTY & REPLACEMENT CLAIM",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = PureWhite
                        )
                        Text(
                            text = "7-Day Checking Warranty Replacement (No Fitting Guarantee)",
                            style = MaterialTheme.typography.labelSmall,
                            color = GoldAccent
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Policy Notice Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = NavySurface),
                border = BorderStroke(1.dp, GoldBorder.copy(alpha = 0.5f))
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Shield,
                            contentDescription = null,
                            tint = GoldPrimary,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "JPT WARRANTY VERIFICATION RULES",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = GoldLight,
                            letterSpacing = 1.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "• Screens must be tested via motherboard connection before installing.\n• Fitting ki koi warranty nahi hai (Strictly no warranty after fitting or gluing display).\n• Seal removal, flex tear, or IC crack voids warranty.\n• 100% immediate replacement or credit adjustment on defects found during checking.",
                        style = MaterialTheme.typography.bodySmall,
                        color = SlateLight,
                        lineHeight = 18.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // CLAIM FORM
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = NavyDarkest),
                border = BorderStroke(1.dp, NavyBorder)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "SUBMIT REPLACEMENT REQUEST",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = GoldAccent
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Model Picker Chips
                    Text("Select Screen Model:", style = MaterialTheme.typography.labelSmall, color = SlateLight)
                    Spacer(modifier = Modifier.height(6.dp))

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(state.products.take(8)) { prod ->
                            val isSelected = selectedPartCode == prod.partCode
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(if (isSelected) GoldPrimary else NavyElevated)
                                    .border(1.dp, if (isSelected) GoldLight else NavyBorder, RoundedCornerShape(6.dp))
                                    .clickable {
                                        selectedModelTitle = prod.title
                                        selectedPartCode = prod.partCode
                                    }
                                    .padding(horizontal = 8.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = prod.title.split("(").firstOrNull() ?: prod.title,
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isSelected) NavyDarkest else PureWhite
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Defective Quantity Stepper
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Number of Defective Pieces:", style = MaterialTheme.typography.bodyMedium, color = PureWhite)
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Button(
                                onClick = { if (defectiveQty > 1) defectiveQty-- },
                                colors = ButtonDefaults.buttonColors(containerColor = NavyElevated),
                                modifier = Modifier.size(36.dp),
                                shape = CircleShape,
                                contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp)
                            ) {
                                Text("-", color = PureWhite, fontWeight = FontWeight.Bold)
                            }

                            Text(
                                text = "$defectiveQty pc${if (defectiveQty > 1) "s" else ""}",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = GoldLight,
                                modifier = Modifier.padding(horizontal = 12.dp)
                            )

                            Button(
                                onClick = { defectiveQty++ },
                                colors = ButtonDefaults.buttonColors(containerColor = NavyElevated),
                                modifier = Modifier.size(36.dp),
                                shape = CircleShape,
                                contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp)
                            ) {
                                Text("+", color = PureWhite, fontWeight = FontWeight.Bold)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Fault Type Selector
                    Text("Defect Type / Issue Observed:", style = MaterialTheme.typography.labelSmall, color = SlateLight)
                    Spacer(modifier = Modifier.height(6.dp))

                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        faultOptions.forEach { option ->
                            val isSelected = faultType == option
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(if (isSelected) GoldDark.copy(alpha = 0.25f) else NavyElevated)
                                    .border(1.dp, if (isSelected) GoldPrimary else NavyBorder, RoundedCornerShape(6.dp))
                                    .clickable { faultType = option }
                                    .padding(horizontal = 10.dp, vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = if (isSelected) Icons.Default.CheckCircle else Icons.Default.Shield,
                                    contentDescription = null,
                                    tint = if (isSelected) GoldAccent else SlateMuted,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = option,
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    color = if (isSelected) GoldLight else PureWhite
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = detailsText,
                        onValueChange = { detailsText = it },
                        label = { Text("Describe technician test result & fault...", color = SlateMuted) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(90.dp)
                            .testTag("warranty_details_input"),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = GoldPrimary,
                            unfocusedBorderColor = NavyBorder,
                            focusedTextColor = PureWhite,
                            unfocusedTextColor = PureWhite,
                            focusedContainerColor = NavySurface,
                            unfocusedContainerColor = NavyElevated
                        ),
                        shape = RoundedCornerShape(8.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Photo Proof Box
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(NavyElevated)
                            .border(1.dp, if (photoAttached) GoldBorder else NavyBorder, RoundedCornerShape(8.dp))
                            .clickable {
                                photoAttached = !photoAttached
                                Toast.makeText(context, if (photoAttached) "Test video/photo proof attached" else "Photo proof cleared", Toast.LENGTH_SHORT).show()
                            }
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.PhotoCamera,
                            contentDescription = null,
                            tint = if (photoAttached) StatusInStock else GoldLight,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = if (photoAttached) "Defect Photo / Test Video: ATTACHED" else "Tap to attach display fault photo",
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.Bold,
                                color = if (photoAttached) StatusInStock else PureWhite
                            )
                            Text(
                                text = "Show intact JPT warranty stamp & testing phone chassis",
                                style = MaterialTheme.typography.labelSmall,
                                color = SlateMuted
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    Button(
                        onClick = {
                            val claimId = "JPT-CLM-${(1000..9999).random()}"
                            val newClaim = WarrantyClaim(
                                claimId = claimId,
                                customerName = state.customerName,
                                shopName = state.shopName,
                                phone = state.customerPhone,
                                productTitle = selectedModelTitle,
                                partCode = selectedPartCode,
                                quantityDefective = defectiveQty,
                                faultType = faultType,
                                details = detailsText.ifBlank { "Screen failed testing on arrival." }
                            )
                            viewModel.submitWarrantyClaim(newClaim)

                            // WhatsApp Claim Format
                            val msg = "🛡️ *JPT WARRANTY CLAIM #${claimId}*\n" +
                                    "🏬 *Shop:* ${state.shopName} (${state.customerCity})\n" +
                                    "📞 *Contact:* ${state.customerPhone}\n" +
                                    "📱 *Model:* $selectedModelTitle ($selectedPartCode)\n" +
                                    "🔢 *Defective Qty:* $defectiveQty pcs\n" +
                                    "⚠️ *Fault:* $faultType\n" +
                                    "📝 *Notes:* ${detailsText.ifBlank { "Tested before gluing, intact JPT stamp" }}\n\n" +
                                    "Please process replacement authorization."

                            openWhatsApp(context, "+923700357848", msg)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("submit_warranty_claim_btn"),
                        colors = ButtonDefaults.buttonColors(containerColor = WhatsAppGreen),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(
                            text = "Submit Replacement Request (WhatsApp)",
                            color = PureWhite,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
