package com.example.ui.components

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Share
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
import androidx.compose.ui.window.Dialog
import com.example.R
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.GoldBorder
import com.example.ui.theme.GoldDark
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.NavyDarkest
import com.example.ui.theme.NavyDeep
import com.example.ui.theme.NavyElevated
import com.example.ui.theme.NavySurface
import com.example.ui.theme.PureWhite
import com.example.ui.theme.SlateLight
import com.example.ui.theme.SlateMuted
import com.example.ui.theme.WhatsAppGreen

@Composable
fun BusinessCardModal(
    onDismiss: () -> Unit
) {
    val context = LocalContext.current

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            shape = RoundedCornerShape(20.dp),
            color = NavyDarkest,
            border = BorderStroke(1.5.dp, GoldPrimary)
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                CircuitBackground(circuitAlpha = 0.4f)

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Header close button & title
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "OFFICIAL WHOLESALE CREDENTIAL",
                            style = MaterialTheme.typography.labelSmall,
                            color = GoldAccent,
                            letterSpacing = 1.5.sp
                        )
                        IconButton(
                            onClick = onDismiss,
                            modifier = Modifier
                                .size(32.dp)
                                .testTag("close_business_card_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close",
                                tint = SlateLight
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Digital Replica of User's Physical Business Card
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = NavyDeep),
                        border = BorderStroke(1.dp, GoldBorder)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            // Top row: Name & JPT Emblem
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.Top
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
                                    contentDescription = "JPT Emblem",
                                    modifier = Modifier
                                        .size(48.dp)
                                        .clip(CircleShape)
                                        .border(1.dp, GoldPrimary, CircleShape)
                                )
                            }

                            Spacer(modifier = Modifier.height(12.dp))
                            Divider(color = GoldBorder, thickness = 0.8.dp)
                            Spacer(modifier = Modifier.height(12.dp))

                            // Contact info rows
                            ContactDetailRow(
                                icon = Icons.Default.Call,
                                text = "0370-0357848",
                                onClick = { openDialer(context, "03700357848") }
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            ContactDetailRow(
                                icon = Icons.Default.Email,
                                text = "jpt.official@gmail.com",
                                onClick = { openEmail(context, "jpt.official@gmail.com") }
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            ContactDetailRow(
                                icon = Icons.Default.Share,
                                text = "@JPT_OFFICIAL_",
                                onClick = { openInstagram(context, "JPT_OFFICIAL_") }
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            ContactDetailRow(
                                icon = Icons.Default.LocationOn,
                                text = "Hall Road, Lahore, Pakistan",
                                onClick = { openMaps(context, "Hall Road, Lahore") }
                            )

                            Spacer(modifier = Modifier.height(14.dp))

                            // Dual QR Code Box
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                QrCodeTile(
                                    title = "WHATSAPP",
                                    drawableRes = R.drawable.qr_whatsapp,
                                    onClick = { openWhatsApp(context, "+923700357848", "Hello Muhammad Jamal, I am inquiring about JPT wholesale mobile screens.") }
                                )
                                QrCodeTile(
                                    title = "INSTAGRAM",
                                    drawableRes = R.drawable.qr_instagram,
                                    onClick = { openInstagram(context, "JPT_OFFICIAL_") }
                                )
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            // Brand Footer & Gold Badge
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

                    Spacer(modifier = Modifier.height(14.dp))

                    // Direct Action Buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = {
                                openWhatsApp(context, "+923700357848", "Assalam o Alaikum Jamal Bhai, I need wholesale screen rates.")
                            },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("modal_whatsapp_chat_btn"),
                            colors = ButtonDefaults.buttonColors(containerColor = WhatsAppGreen),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("Chat WhatsApp", color = PureWhite, fontWeight = FontWeight.Bold)
                        }

                        OutlinedButton(
                            onClick = { openDialer(context, "03700357848") },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("modal_call_btn"),
                            border = BorderStroke(1.dp, GoldPrimary),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("Direct Call", color = GoldAccent, fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ContactDetailRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    text: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(6.dp))
            .clickable { onClick() }
            .padding(vertical = 4.dp, horizontal = 4.dp),
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
            color = PureWhite
        )
    }
}

@Composable
private fun QrCodeTile(
    title: String,
    drawableRes: Int,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClick() }
            .padding(4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(90.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White)
                .padding(4.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = drawableRes),
                contentDescription = "$title QR Code",
                modifier = Modifier.fillMaxWidth()
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            color = GoldLight,
            letterSpacing = 1.sp
        )
    }
}

fun openWhatsApp(context: Context, phoneInternational: String, message: String) {
    try {
        val cleanNumber = phoneInternational.replace("+", "").replace(" ", "").replace("-", "")
        val uri = Uri.parse("https://api.whatsapp.com/send?phone=$cleanNumber&text=${Uri.encode(message)}")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        context.startActivity(intent)
    } catch (e: Exception) {
        Toast.makeText(context, "WhatsApp not available", Toast.LENGTH_SHORT).show()
    }
}

fun openDialer(context: Context, phoneNumber: String) {
    try {
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
        context.startActivity(intent)
    } catch (e: Exception) {
        Toast.makeText(context, "Unable to dial $phoneNumber", Toast.LENGTH_SHORT).show()
    }
}

fun openEmail(context: Context, email: String) {
    try {
        val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:$email"))
        intent.putExtra(Intent.EXTRA_SUBJECT, "B2B Wholesale Inquiry - Jamal Precision Tech")
        context.startActivity(intent)
    } catch (e: Exception) {
        Toast.makeText(context, "Email: $email", Toast.LENGTH_SHORT).show()
    }
}

fun openInstagram(context: Context, handle: String) {
    try {
        val cleanHandle = handle.replace("@", "")
        val uri = Uri.parse("https://instagram.com/$cleanHandle")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        context.startActivity(intent)
    } catch (e: Exception) {
        Toast.makeText(context, "Instagram: @$handle", Toast.LENGTH_SHORT).show()
    }
}

fun openMaps(context: Context, address: String) {
    try {
        val uri = Uri.parse("geo:0,0?q=${Uri.encode(address)}")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        context.startActivity(intent)
    } catch (e: Exception) {
        Toast.makeText(context, address, Toast.LENGTH_SHORT).show()
    }
}
