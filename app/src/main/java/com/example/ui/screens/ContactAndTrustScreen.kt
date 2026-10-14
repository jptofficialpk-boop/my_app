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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.models.AppLanguage
import com.example.ui.JptUiState
import com.example.ui.JptViewModel
import com.example.ui.components.CircuitBackground
import com.example.ui.components.openDialer
import com.example.ui.components.openEmail
import com.example.ui.components.openInstagram
import com.example.ui.components.openMaps
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
fun ContactAndTrustScreen(
    state: JptUiState,
    viewModel: JptViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(NavyDeep)
            .padding(horizontal = 14.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(12.dp))

            // REPLICA OF PHYSICAL LUXURY BUSINESS CARD
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { viewModel.toggleBusinessCard(true) }
                    .testTag("interactive_business_card_replica"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = NavyDarkest),
                border = BorderStroke(1.5.dp, GoldPrimary)
            ) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    CircuitBackground(circuitAlpha = 0.55f)

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "MUHAMMAD JAMAL",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 1.2.sp,
                                    color = PureWhite
                                )
                                Text(
                                    text = "PROPRIETOR",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.SemiBold,
                                    letterSpacing = 2.sp,
                                    color = GoldAccent
                                )
                            }

                            Image(
                                painter = painterResource(id = R.drawable.img_jpt_logo),
                                contentDescription = "JPT Logo",
                                modifier = Modifier
                                    .size(54.dp)
                                    .clip(CircleShape)
                                    .border(1.5.dp, GoldPrimary, CircleShape)
                            )
                        }

                        Spacer(modifier = Modifier.height(14.dp))
                        Divider(color = GoldBorder, thickness = 0.8.dp)
                        Spacer(modifier = Modifier.height(12.dp))

                        ContactRow(Icons.Default.Call, "0370-0357848") {
                            openDialer(context, "03700357848")
                        }
                        ContactRow(Icons.Default.Email, "jpt.official@gmail.com") {
                            openEmail(context, "jpt.official@gmail.com")
                        }
                        ContactRow(Icons.Default.Share, "@JPT_OFFICIAL_") {
                            openInstagram(context, "JPT_OFFICIAL_")
                        }
                        ContactRow(Icons.Default.LocationOn, "Hall Road, Lahore, Pakistan") {
                            openMaps(context, "Hall Road, Lahore")
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Dual Scan-to-Chat QR Codes
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            InteractiveQrItem(
                                title = "WHATSAPP",
                                drawableRes = R.drawable.qr_whatsapp,
                                subtitle = "Scan to Chat",
                                onClick = {
                                    openWhatsApp(context, "+923700357848", "Hello Muhammad Jamal, connecting from JPT app.")
                                }
                            )

                            InteractiveQrItem(
                                title = "INSTAGRAM",
                                drawableRes = R.drawable.qr_instagram,
                                subtitle = "@JPT_OFFICIAL_",
                                onClick = {
                                    openInstagram(context, "JPT_OFFICIAL_")
                                }
                            )
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        Text(
                            text = "LCD • OLED • SAMSUNG • INFINIX • TECNO • VIVO • OPPO • REDMI",
                            style = MaterialTheme.typography.labelSmall,
                            color = SlateLight,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(
                                    Brush.horizontalGradient(
                                        listOf(GoldDark, GoldPrimary, GoldAccent, GoldPrimary)
                                    )
                                )
                                .padding(vertical = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "BULK ORDERS AVAILABLE",
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.ExtraBold,
                                letterSpacing = 1.5.sp,
                                color = NavyDarkest
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // ONE-TAP DIRECT CONNECTIVITY ACTIONS
            Text(
                text = "DIRECT STORE & SALES CONNECTIVITY",
                style = MaterialTheme.typography.labelLarge,
                color = GoldLight,
                letterSpacing = 1.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = {
                        openWhatsApp(context, "+923700357848", "Assalam o Alaikum Jamal Bhai, inquiring about wholesale screen supply.")
                    },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = WhatsAppGreen)
                ) {
                    Text("Chat WhatsApp", color = PureWhite, fontWeight = FontWeight.Bold)
                }

                Button(
                    onClick = { openDialer(context, "03700357848") },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary)
                ) {
                    Text("Call Sales", color = NavyDarkest, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = { openMaps(context, "Hall Road, Lahore, Pakistan") },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(1.dp, GoldBorder)
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = GoldLight,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Hall Road Map", color = PureWhite, fontSize = 12.sp)
                }

                OutlinedButton(
                    onClick = { openInstagram(context, "JPT_OFFICIAL_") },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(1.dp, GoldBorder)
                ) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = null,
                        tint = GoldLight,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Instagram", color = PureWhite, fontSize = 12.sp)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // TRUST & WAREHOUSE DISPATCH SECTION
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = NavySurface),
                border = BorderStroke(1.dp, NavyBorder)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "PAKISTAN B2B WHOLESALE ASSURANCE",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = GoldAccent,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    TrustItem(
                        title = "100% Pre-Tested Displays",
                        desc = "Zero dead pixels, accurate touch digitizer matrix, and original color calibration guaranteed."
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TrustItem(
                        title = "7-Day Checking Warranty",
                        desc = "Return or replace any defective panel tested before frame installation. No warranty after fitting/gluing or stamp removal."
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TrustItem(
                        title = "Nationwide Express Cargo",
                        desc = "Daily direct cargo dispatches to Karachi, Rawalpindi, Islamabad, Faisalabad, Multan, Peshawar, and Quetta."
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TrustItem(
                        title = "Anti-Static Wooden Foam Packaging",
                        desc = "Triple-layered protective foam and reinforced packaging preventing flex cable tears during transit."
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // WHOLESALE WAREHOUSE PHOTO BANNER
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .border(1.dp, GoldBorder, RoundedCornerShape(14.dp))
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_display_parts),
                    contentDescription = "Warehouse Parts",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                listOf(Color.Transparent, NavyDarkest.copy(alpha = 0.85f))
                            )
                        )
                        .padding(12.dp),
                    contentAlignment = Alignment.BottomStart
                ) {
                    Column {
                        Text(
                            text = "Jamal Precision Tech • Hall Road Central Hub",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = PureWhite
                        )
                        Text(
                            text = "Over 10,000+ Replacement Screens in Immediate Stock",
                            style = MaterialTheme.typography.bodySmall,
                            color = GoldLight
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Composable
private fun ContactRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    text: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(6.dp))
            .clickable { onClick() }
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = GoldAccent,
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = PureWhite,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun InteractiveQrItem(
    title: String,
    drawableRes: Int,
    subtitle: String,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .clickable { onClick() }
            .padding(4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(105.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color.White)
                .padding(6.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = drawableRes),
                contentDescription = "$title QR",
                modifier = Modifier.fillMaxSize()
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold,
            color = GoldLight,
            letterSpacing = 1.sp
        )
        Text(
            text = subtitle,
            style = MaterialTheme.typography.labelSmall,
            color = SlateMuted
        )
    }
}

@Composable
private fun TrustItem(title: String, desc: String) {
    Row(verticalAlignment = Alignment.Top) {
        Icon(
            imageVector = Icons.Default.Verified,
            contentDescription = null,
            tint = GoldPrimary,
            modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
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
