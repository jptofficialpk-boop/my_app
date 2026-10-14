package com.example.ui.screens

import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material.icons.filled.Print
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Verified
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.AppScreen
import com.example.ui.JptUiState
import com.example.ui.JptViewModel
import com.example.ui.components.CircuitBackground
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
fun InvoiceViewScreen(
    state: JptUiState,
    viewModel: JptViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val invoice = state.currentInvoiceData

    if (invoice == null) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(NavyDeep)
                .padding(20.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "No Invoice Generated Yet",
                    style = MaterialTheme.typography.titleMedium,
                    color = PureWhite
                )
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick = { viewModel.setScreen(AppScreen.CART_QUOTE) },
                    colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary)
                ) {
                    Text("Go to Wholesale Quote", color = NavyDarkest, fontWeight = FontWeight.Bold)
                }
            }
        }
        return
    }

    val sdf = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
    val formattedDate = sdf.format(Date(invoice.timestamp))

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(NavyDeep)
    ) {
        // TOP APP BAR WITH ACTIONS
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = NavyDarkest,
            shadowElevation = 6.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = { viewModel.setScreen(AppScreen.CART_QUOTE) },
                        modifier = Modifier.testTag("back_from_invoice_btn")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = GoldLight
                        )
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Column {
                        Text(
                            text = "DIGITAL WHOLESALE INVOICE",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = PureWhite
                        )
                        Text(
                            text = invoice.invoiceNumber,
                            style = MaterialTheme.typography.labelSmall,
                            color = GoldAccent
                        )
                    }
                }

                Row {
                    IconButton(
                        onClick = { viewModel.openCurrentInvoicePdf(context) },
                        modifier = Modifier.testTag("download_pdf_top_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Default.PictureAsPdf,
                            contentDescription = "Download PDF",
                            tint = GoldLight
                        )
                    }
                }
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 14.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(12.dp))

                // Action Bar: Download PDF & WhatsApp Share
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = { viewModel.openCurrentInvoicePdf(context) },
                        modifier = Modifier
                            .weight(1f)
                            .height(44.dp)
                            .testTag("download_pdf_btn"),
                        colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Download,
                            contentDescription = null,
                            tint = NavyDarkest,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Download PDF", color = NavyDarkest, fontWeight = FontWeight.Bold)
                    }

                    Button(
                        onClick = { viewModel.shareCurrentInvoiceWhatsApp(context) },
                        modifier = Modifier
                            .weight(1.2f)
                            .height(44.dp)
                            .testTag("share_invoice_whatsapp_btn"),
                        colors = ButtonDefaults.buttonColors(containerColor = WhatsAppGreen),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("Share WhatsApp", color = PureWhite, fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // DIGITAL BILL CARD (JPT LUXURY BRANDING)
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = NavyDarkest),
                    border = BorderStroke(1.5.dp, GoldPrimary)
                ) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        CircuitBackground(circuitAlpha = 0.35f)

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(18.dp)
                        ) {
                            // Header with JPT Emblem
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.Top
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "JAMAL PRECISION TECH",
                                        style = MaterialTheme.typography.titleLarge,
                                        fontWeight = FontWeight.ExtraBold,
                                        letterSpacing = 1.sp,
                                        color = GoldLight
                                    )
                                    Text(
                                        text = "MOBILE DISPLAY WHOLESALE",
                                        style = MaterialTheme.typography.labelMedium,
                                        fontWeight = FontWeight.SemiBold,
                                        color = PureWhite
                                    )
                                    Text(
                                        text = "Hall Road, Lahore, Pakistan",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = SlateLight
                                    )
                                    Text(
                                        text = "Proprietor: Muhammad Jamal  |  WhatsApp: 0370-0357848",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = GoldAccent
                                    )
                                }

                                Image(
                                    painter = painterResource(id = R.drawable.img_jpt_logo),
                                    contentDescription = "JPT Emblem",
                                    modifier = Modifier
                                        .size(52.dp)
                                        .clip(CircleShape)
                                        .border(1.5.dp, GoldPrimary, CircleShape)
                                )
                            }

                            Spacer(modifier = Modifier.height(12.dp))
                            Divider(color = GoldBorder, thickness = 1.dp)
                            Spacer(modifier = Modifier.height(12.dp))

                            // Invoice metadata row
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column {
                                    Text("INVOICE NO:", style = MaterialTheme.typography.labelSmall, color = SlateMuted)
                                    Text(invoice.invoiceNumber, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = PureWhite)
                                }
                                Column(horizontalAlignment = Alignment.End) {
                                    Text("DATE & TIME:", style = MaterialTheme.typography.labelSmall, color = SlateMuted)
                                    Text(formattedDate, style = MaterialTheme.typography.bodySmall, color = PureWhite)
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            // Customer & Cargo Dispatch Details Card
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(10.dp),
                                colors = CardDefaults.cardColors(containerColor = NavyElevated),
                                border = BorderStroke(1.dp, NavyBorder)
                            ) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Text(
                                        text = "BILLED TO / RETAIL SHOP:",
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = GoldAccent
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text("Shop Name: ${invoice.shopName}", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, color = PureWhite)
                                    Text("Customer Name: ${invoice.customerName}", style = MaterialTheme.typography.bodySmall, color = SlateLight)
                                    Text("Contact Phone: ${invoice.customerPhone}", style = MaterialTheme.typography.bodySmall, color = SlateLight)
                                    Text("Destination City: ${invoice.customerCity}", style = MaterialTheme.typography.bodySmall, color = SlateLight)
                                    Text("Cargo Carrier: ${invoice.freightCarrier}", style = MaterialTheme.typography.bodySmall, color = GoldLight)
                                }
                            }

                            Spacer(modifier = Modifier.height(14.dp))

                            // Itemized Table Header
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(NavySurface)
                                    .border(1.dp, GoldBorder.copy(alpha = 0.3f), RoundedCornerShape(6.dp))
                                    .padding(8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("# Item & Model", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = GoldLight, modifier = Modifier.weight(2f))
                                Text("Qty", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = GoldLight, modifier = Modifier.weight(0.7f), textAlign = TextAlign.Center)
                                Text("Rate", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = GoldLight, modifier = Modifier.weight(0.9f), textAlign = TextAlign.End)
                                Text("Amount", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = GoldLight, modifier = Modifier.weight(1.1f), textAlign = TextAlign.End)
                            }

                            Spacer(modifier = Modifier.height(6.dp))

                            // Itemized Rows
                            invoice.items.forEachIndexed { idx, item ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp, horizontal = 4.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(modifier = Modifier.weight(2f)) {
                                        Text(
                                            text = "${idx + 1}. ${item.product.title}",
                                            style = MaterialTheme.typography.bodySmall,
                                            fontWeight = FontWeight.SemiBold,
                                            color = PureWhite
                                        )
                                        Text(
                                            text = "${item.product.partCode} • ${item.product.grade.gradeName}",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = SlateMuted
                                        )
                                    }

                                    Text(
                                        text = "${item.quantity} pcs",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = PureWhite,
                                        modifier = Modifier.weight(0.7f),
                                        textAlign = TextAlign.Center
                                    )

                                    Text(
                                        text = "Rs. %,d".format(item.unitPrice),
                                        style = MaterialTheme.typography.bodySmall,
                                        color = SlateLight,
                                        modifier = Modifier.weight(0.9f),
                                        textAlign = TextAlign.End
                                    )

                                    Text(
                                        text = "Rs. %,d".format(item.totalPrice),
                                        style = MaterialTheme.typography.bodySmall,
                                        fontWeight = FontWeight.Bold,
                                        color = GoldLight,
                                        modifier = Modifier.weight(1.1f),
                                        textAlign = TextAlign.End
                                    )
                                }
                                Divider(color = NavyBorder.copy(alpha = 0.4f), thickness = 0.5.dp)
                            }

                            Spacer(modifier = Modifier.height(14.dp))

                            // Totals breakdown
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("Gross Subtotal:", style = MaterialTheme.typography.bodyMedium, color = SlateLight)
                                Text("Rs. %,d".format(invoice.grossSubtotal), style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, color = PureWhite)
                            }

                            if (invoice.tierDiscount > 0) {
                                Spacer(modifier = Modifier.height(4.dp))
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text("Wholesale Tier Discount:", style = MaterialTheme.typography.bodyMedium, color = StatusInStock)
                                    Text("-Rs. %,d".format(invoice.tierDiscount), style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, color = StatusInStock)
                                }
                            }

                            Spacer(modifier = Modifier.height(4.dp))
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("QC Pre-Testing & Wooden Packaging:", style = MaterialTheme.typography.bodyMedium, color = SlateLight)
                                Text("FREE (Included)", style = MaterialTheme.typography.bodyMedium, color = GoldLight)
                            }

                            Spacer(modifier = Modifier.height(8.dp))
                            Divider(color = GoldBorder, thickness = 1.dp)
                            Spacer(modifier = Modifier.height(8.dp))

                            // Final Net Total Highlight Box
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(
                                        Brush.horizontalGradient(
                                            listOf(GoldDark, GoldPrimary, GoldAccent, GoldPrimary)
                                        )
                                    )
                                    .padding(vertical = 10.dp, horizontal = 12.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "NET TOTAL PAYABLE:",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = NavyDarkest
                                    )
                                    Text(
                                        text = "Rs. %,d".format(invoice.netTotal),
                                        style = MaterialTheme.typography.titleLarge,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = NavyDarkest
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(14.dp))

                            // Bank Transfer details
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(8.dp),
                                colors = CardDefaults.cardColors(containerColor = NavyElevated),
                                border = BorderStroke(1.dp, NavyBorder)
                            ) {
                                Column(modifier = Modifier.padding(10.dp)) {
                                    Text("BANK PAYMENT DETAILS:", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = GoldAccent)
                                    Text("Bank: Meezan Bank Ltd (Hall Road Lahore Branch)", style = MaterialTheme.typography.bodySmall, color = PureWhite)
                                    Text("Account Title: JAMAL PRECISION TECH", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold, color = PureWhite)
                                    Text("Account No: 0214-0105874219", style = MaterialTheme.typography.bodySmall, color = SlateLight)
                                    Text("IBAN: PK52MEZN0002140105874219", style = MaterialTheme.typography.bodySmall, color = GoldLight)
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            // Guarantee terms
                            Row(verticalAlignment = Alignment.Top) {
                                Icon(Icons.Default.Verified, contentDescription = null, tint = GoldPrimary, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "7-Day Checking Warranty (No Fitting Guarantee): Please check all displays prior to installing on phone chassis. Warranty void after fitting, gluing, or seal removal.",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = SlateMuted,
                                    fontSize = 11.sp
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(30.dp))
            }
        }
    }
}
