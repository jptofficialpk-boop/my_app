package com.example.util

import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.widget.Toast
import androidx.core.content.FileProvider
import com.example.data.models.CartItem
import com.example.data.models.PaymentStatus
import com.example.data.models.ProductItem
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class InvoiceData(
    val invoiceNumber: String,
    val customerName: String,
    val shopName: String,
    val customerPhone: String,
    val customerCity: String,
    val freightCarrier: String,
    val cargoFee: Long = 0L,
    val items: List<CartItem>,
    val grossSubtotal: Long,
    val tierDiscount: Long,
    val netTotal: Long,
    val paymentStatus: PaymentStatus = PaymentStatus.UNPAID,
    val timestamp: Long = System.currentTimeMillis()
)

object PdfInvoiceGenerator {

    fun generateInvoicePdf(context: Context, data: InvoiceData): File? {
        val document = PdfDocument()

        // Standard A4 dimensions in PostScript points: 595 x 842
        val pageWidth = 595
        val pageHeight = 842
        val pageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create()
        val page = document.startPage(pageInfo)
        val canvas = page.canvas

        // Palette
        val navyColor = Color.rgb(10, 17, 40)
        val goldColor = Color.rgb(212, 175, 55)
        val darkSlateColor = Color.rgb(26, 38, 57)
        val lightGrayColor = Color.rgb(245, 247, 250)
        val textDarkColor = Color.rgb(30, 41, 59)
        val textMutedColor = Color.rgb(100, 116, 139)
        val greenColor = Color.rgb(16, 185, 129)

        val paint = Paint().apply { isAntiAlias = true }

        // 1. TOP HEADER BANNER
        paint.color = navyColor
        canvas.drawRect(0f, 0f, pageWidth.toFloat(), 110f, paint)

        // Gold decorative accent line
        paint.color = goldColor
        paint.strokeWidth = 3f
        canvas.drawLine(0f, 110f, pageWidth.toFloat(), 110f, paint)

        // Brand Title
        paint.color = goldColor
        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        paint.textSize = 20f
        canvas.drawText("JAMAL PRECISION TECH (JPT)", 30f, 40f, paint)

        // Subtitle
        paint.color = Color.WHITE
        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
        paint.textSize = 10f
        canvas.drawText("MOBILE DISPLAY WHOLESALE • HALL ROAD, LAHORE, PAKISTAN", 30f, 58f, paint)
        canvas.drawText("Proprietor: Muhammad Jamal  |  WhatsApp: 0370-0357848  |  Email: jpt.official@gmail.com", 30f, 72f, paint)
        canvas.drawText("B2B Wholesale Tax Invoice & Dispatch Order Note", 30f, 86f, paint)

        // Top Right Invoice Badge
        paint.color = goldColor
        paint.textSize = 14f
        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        val invText = "INVOICE: ${data.invoiceNumber}"
        val invTextWidth = paint.measureText(invText)
        canvas.drawText(invText, pageWidth - 30f - invTextWidth, 45f, paint)

        val sdf = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
        paint.color = Color.rgb(203, 213, 225)
        paint.textSize = 9f
        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
        val dateText = "Date: ${sdf.format(Date(data.timestamp))}"
        val dateWidth = paint.measureText(dateText)
        canvas.drawText(dateText, pageWidth - 30f - dateWidth, 62f, paint)

        // 2. BILLED TO & DISPATCH INFO BOX
        var curY = 125f
        paint.color = lightGrayColor
        val infoRect = RectF(30f, curY, pageWidth - 30f, curY + 75f)
        canvas.drawRoundRect(infoRect, 6f, 6f, paint)

        paint.color = Color.rgb(226, 232, 240)
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 1f
        canvas.drawRoundRect(infoRect, 6f, 6f, paint)
        paint.style = Paint.Style.FILL

        // Left Column: Customer details
        paint.color = navyColor
        paint.textSize = 10f
        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        canvas.drawText("BILLED TO / RETAIL SHOP:", 45f, curY + 20f, paint)

        paint.color = textDarkColor
        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
        paint.textSize = 10f
        canvas.drawText("Shop Name: ${data.shopName.ifBlank { "Direct Retail Shopkeeper" }}", 45f, curY + 36f, paint)
        canvas.drawText("Contact Name: ${data.customerName.ifBlank { "Muhammad Jamal Client" }}", 45f, curY + 50f, paint)
        canvas.drawText("Phone: ${data.customerPhone.ifBlank { "0370-0357848" }}", 45f, curY + 64f, paint)

        // Right Column: Dispatch details
        val rightColX = 330f
        paint.color = navyColor
        paint.textSize = 10f
        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        canvas.drawText("DISPATCH & LOGISTICS:", rightColX, curY + 20f, paint)

        paint.color = textDarkColor
        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
        paint.textSize = 10f
        canvas.drawText("Destination City: ${data.customerCity.ifBlank { "Lahore" }}", rightColX, curY + 36f, paint)
        canvas.drawText("Cargo Carrier: ${data.freightCarrier}", rightColX, curY + 50f, paint)

        paint.color = when (data.paymentStatus) {
            PaymentStatus.PAID -> greenColor
            PaymentStatus.PARTIAL -> Color.rgb(245, 158, 11)
            PaymentStatus.UNPAID -> Color.rgb(239, 68, 68)
        }
        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        canvas.drawText("Status: ${data.paymentStatus.displayName.uppercase()}", rightColX, curY + 64f, paint)

        // 3. TABLE HEADER
        curY += 90f
        paint.color = navyColor
        val tableHeadRect = RectF(30f, curY, pageWidth - 30f, curY + 24f)
        canvas.drawRoundRect(tableHeadRect, 4f, 4f, paint)

        paint.color = Color.WHITE
        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        paint.textSize = 9f
        canvas.drawText("#", 38f, curY + 16f, paint)
        canvas.drawText("DESCRIPTION / PART CODE", 60f, curY + 16f, paint)
        canvas.drawText("GRADE", 305f, curY + 16f, paint)
        canvas.drawText("QTY", 400f, curY + 16f, paint)
        canvas.drawText("RATE (PKR)", 445f, curY + 16f, paint)
        canvas.drawText("AMOUNT (PKR)", 510f, curY + 16f, paint)

        // 4. TABLE ROWS
        curY += 24f
        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
        paint.textSize = 8.5f

        data.items.forEachIndexed { idx, item ->
            if (idx % 2 == 1) {
                paint.color = Color.rgb(248, 250, 252)
                canvas.drawRect(30f, curY, pageWidth - 30f, curY + 22f, paint)
            }

            paint.color = textDarkColor
            canvas.drawText("${idx + 1}", 38f, curY + 15f, paint)

            val rawTitle = "${item.product.title} (${item.product.partCode})"
            val displayTitle = if (rawTitle.length > 44) rawTitle.substring(0, 41) + "..." else rawTitle
            canvas.drawText(displayTitle, 60f, curY + 15f, paint)

            val gradeText = if (item.product.grade.gradeName.length > 16)
                item.product.grade.gradeName.substring(0, 14) + ".."
            else
                item.product.grade.gradeName
            canvas.drawText(gradeText, 305f, curY + 15f, paint)

            canvas.drawText("${item.quantity} pcs", 400f, curY + 15f, paint)
            canvas.drawText("Rs. %,d".format(item.unitPrice), 445f, curY + 15f, paint)
            canvas.drawText("Rs. %,d".format(item.totalPrice), 510f, curY + 15f, paint)

            paint.color = Color.rgb(241, 245, 249)
            paint.strokeWidth = 0.5f
            canvas.drawLine(30f, curY + 22f, pageWidth - 30f, curY + 22f, paint)

            curY += 22f
        }

        // 5. SUMMARY & TOTALS BOX
        curY += 15f
        val summaryWidth = 240f
        val summaryX = pageWidth - 30f - summaryWidth

        paint.color = lightGrayColor
        val summaryRect = RectF(summaryX, curY, pageWidth - 30f, curY + 105f)
        canvas.drawRoundRect(summaryRect, 6f, 6f, paint)

        paint.color = Color.rgb(226, 232, 240)
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 1f
        canvas.drawRoundRect(summaryRect, 6f, 6f, paint)
        paint.style = Paint.Style.FILL

        var sumY = curY + 18f
        paint.color = textDarkColor
        paint.textSize = 9.5f
        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
        canvas.drawText("Gross Subtotal:", summaryX + 12f, sumY, paint)
        canvas.drawText("Rs. %,d".format(data.grossSubtotal), summaryX + summaryWidth - 12f - paint.measureText("Rs. %,d".format(data.grossSubtotal)), sumY, paint)

        if (data.tierDiscount > 0) {
            sumY += 16f
            paint.color = greenColor
            canvas.drawText("Wholesale Tier Savings:", summaryX + 12f, sumY, paint)
            val discText = "-Rs. %,d".format(data.tierDiscount)
            canvas.drawText(discText, summaryX + summaryWidth - 12f - paint.measureText(discText), sumY, paint)
        }

        if (data.cargoFee > 0) {
            sumY += 16f
            paint.color = textDarkColor
            canvas.drawText("Cargo & Freight Booking:", summaryX + 12f, sumY, paint)
            val cargoText = "+Rs. %,d".format(data.cargoFee)
            canvas.drawText(cargoText, summaryX + summaryWidth - 12f - paint.measureText(cargoText), sumY, paint)
        }

        sumY += 16f
        paint.color = textDarkColor
        canvas.drawText("QC Testing & Wooden Packing:", summaryX + 12f, sumY, paint)
        val freeText = "FREE (Included)"
        canvas.drawText(freeText, summaryX + summaryWidth - 12f - paint.measureText(freeText), sumY, paint)

        // Net Payable bar
        sumY += 12f
        paint.color = navyColor
        canvas.drawRect(summaryX, sumY, pageWidth - 30f, curY + 105f, paint)

        paint.color = goldColor
        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        paint.textSize = 11f
        canvas.drawText("NET PAYABLE TOTAL:", summaryX + 12f, sumY + 18f, paint)
        val totalText = "Rs. %,d".format(data.netTotal)
        val totalWidth = paint.measureText(totalText)
        canvas.drawText(totalText, summaryX + summaryWidth - 12f - totalWidth, sumY + 18f, paint)

        // 6. BANKING & OFFICIAL DETAILS
        val bankBoxWidth = 270f
        paint.color = lightGrayColor
        val bankRect = RectF(30f, curY, 30f + bankBoxWidth, curY + 105f)
        canvas.drawRoundRect(bankRect, 6f, 6f, paint)

        paint.color = Color.rgb(226, 232, 240)
        paint.style = Paint.Style.STROKE
        canvas.drawRoundRect(bankRect, 6f, 6f, paint)
        paint.style = Paint.Style.FILL

        paint.color = navyColor
        paint.textSize = 9.5f
        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        canvas.drawText("BANK TRANSFER PAYMENT DETAILS:", 42f, curY + 18f, paint)

        paint.color = textDarkColor
        paint.textSize = 8.5f
        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
        canvas.drawText("Bank: Meezan Bank Ltd (Hall Road Branch)", 42f, curY + 34f, paint)
        canvas.drawText("Account Title: JAMAL PRECISION TECH", 42f, curY + 48f, paint)
        canvas.drawText("Account No: 0214-0105874219", 42f, curY + 62f, paint)
        canvas.drawText("IBAN: PK52MEZN0002140105874219", 42f, curY + 76f, paint)

        // 7. TERMS & AUTHORIZED STAMP
        val footerY = pageHeight - 90f
        paint.color = Color.rgb(226, 232, 240)
        paint.strokeWidth = 1f
        canvas.drawLine(30f, footerY, pageWidth - 30f, footerY, paint)

        paint.color = textMutedColor
        paint.textSize = 7.5f
        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
        canvas.drawText("TERMS & CONDITIONS:", 30f, footerY + 14f, paint)
        canvas.drawText("1. All screens are factory QC tested. Please check on motherboard prior to frame fitting/gluing.", 30f, footerY + 25f, paint)
        canvas.drawText("2. Checking warranty only (No fitting guarantee). Removal of JPT seal or flex bend crease invalidates claim.", 30f, footerY + 36f, paint)
        canvas.drawText("3. Goods once sold under wholesale terms cannot be refunded but exchanged as per testing policy.", 30f, footerY + 47f, paint)

        paint.color = navyColor
        paint.textSize = 9f
        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        val sigX = 420f
        canvas.drawText("MUHAMMAD JAMAL", sigX, footerY + 32f, paint)
        paint.color = goldColor
        canvas.drawText("Proprietor - Jamal Precision Tech", sigX, footerY + 44f, paint)

        document.finishPage(page)

        val invoiceDir = File(context.cacheDir, "invoices")
        if (!invoiceDir.exists()) invoiceDir.mkdirs()

        val pdfFile = File(invoiceDir, "${data.invoiceNumber}.pdf")
        try {
            val fos = FileOutputStream(pdfFile)
            document.writeTo(fos)
            fos.flush()
            fos.close()
            document.close()
            return pdfFile
        } catch (e: Exception) {
            e.printStackTrace()
            document.close()
            return null
        }
    }

    // -------------------------------------------------------------
    // DAILY RATE LIST BANNER / PDF GENERATOR (ADMIN)
    // -------------------------------------------------------------
    fun generateDailyRateListPdf(context: Context, products: List<ProductItem>): File? {
        val document = PdfDocument()
        val pageWidth = 595
        val pageHeight = 842
        val pageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create()
        val page = document.startPage(pageInfo)
        val canvas = page.canvas

        val navyColor = Color.rgb(10, 17, 40)
        val goldColor = Color.rgb(212, 175, 55)
        val lightGrayColor = Color.rgb(245, 247, 250)
        val textDarkColor = Color.rgb(30, 41, 59)
        val textMutedColor = Color.rgb(100, 116, 139)
        val greenColor = Color.rgb(16, 185, 129)

        val paint = Paint().apply { isAntiAlias = true }

        // Top Header
        paint.color = navyColor
        canvas.drawRect(0f, 0f, pageWidth.toFloat(), 105f, paint)

        paint.color = goldColor
        paint.strokeWidth = 3f
        canvas.drawLine(0f, 105f, pageWidth.toFloat(), 105f, paint)

        paint.color = goldColor
        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        paint.textSize = 20f
        canvas.drawText("JAMAL PRECISION TECH (JPT)", 30f, 38f, paint)

        paint.color = Color.WHITE
        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
        paint.textSize = 10f
        canvas.drawText("OFFICIAL DAILY WHOLESALE DISPLAY RATE LIST • HALL ROAD, LAHORE", 30f, 54f, paint)
        canvas.drawText("Direct WhatsApp: 0370-0357848  |  Proprietor: Muhammad Jamal", 30f, 68f, paint)

        val sdf = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.getDefault())
        paint.color = goldColor
        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        paint.textSize = 10f
        val dateBanner = "RATES ACTIVE FOR: ${sdf.format(Date()).uppercase()}"
        canvas.drawText(dateBanner, 30f, 86f, paint)

        // Notice Bar
        var curY = 118f
        paint.color = lightGrayColor
        val noteRect = RectF(30f, curY, pageWidth - 30f, curY + 28f)
        canvas.drawRoundRect(noteRect, 4f, 4f, paint)

        paint.color = textDarkColor
        paint.textSize = 8.5f
        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        canvas.drawText("WHOLESALE TERMS: Minimum Order 10 pcs. Pre-tested 0% dead pixels. Daily cargo booking across Pakistan.", 40f, curY + 18f, paint)

        curY += 38f

        // Table Header
        paint.color = navyColor
        val headRect = RectF(30f, curY, pageWidth - 30f, curY + 22f)
        canvas.drawRoundRect(headRect, 4f, 4f, paint)

        paint.color = Color.WHITE
        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        paint.textSize = 8.5f
        canvas.drawText("MODEL / PART NAME", 38f, curY + 15f, paint)
        canvas.drawText("BRAND", 260f, curY + 15f, paint)
        canvas.drawText("GRADE", 330f, curY + 15f, paint)
        canvas.drawText("10+ pcs", 420f, curY + 15f, paint)
        canvas.drawText("50+ pcs", 480f, curY + 15f, paint)
        canvas.drawText("STATUS", 535f, curY + 15f, paint)

        curY += 22f
        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
        paint.textSize = 8f

        products.take(24).forEachIndexed { idx, prod ->
            if (idx % 2 == 1) {
                paint.color = Color.rgb(248, 250, 252)
                canvas.drawRect(30f, curY, pageWidth - 30f, curY + 19f, paint)
            }

            paint.color = textDarkColor
            val shortTitle = if (prod.title.length > 36) prod.title.substring(0, 33) + "..." else prod.title
            canvas.drawText(shortTitle, 38f, curY + 13f, paint)

            canvas.drawText(prod.brand.displayName.take(8), 260f, curY + 13f, paint)

            val shortGrade = if (prod.grade.gradeName.length > 14) prod.grade.gradeName.substring(0, 12) + ".." else prod.grade.gradeName
            canvas.drawText(shortGrade, 330f, curY + 13f, paint)

            paint.color = navyColor
            paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            canvas.drawText("Rs. %,d".format(prod.pricing.tier1PricePkr), 420f, curY + 13f, paint)

            paint.color = goldColor
            canvas.drawText("Rs. %,d".format(prod.pricing.tier2PricePkr), 480f, curY + 13f, paint)

            paint.color = if (prod.isAvailable) greenColor else Color.rgb(239, 68, 68)
            paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
            canvas.drawText(if (prod.isAvailable) "IN STOCK" else "OUT STOCK", 535f, curY + 13f, paint)

            paint.color = Color.rgb(241, 245, 249)
            paint.strokeWidth = 0.5f
            canvas.drawLine(30f, curY + 19f, pageWidth - 30f, curY + 19f, paint)

            curY += 19f
        }

        // Footer Banner
        val footerY = pageHeight - 65f
        paint.color = navyColor
        canvas.drawRect(0f, footerY, pageWidth.toFloat(), pageHeight.toFloat(), paint)

        paint.color = goldColor
        paint.textSize = 10f
        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        canvas.drawText("TO ORDER: SEND SCREEN LIST ON WHATSAPP: 0370-0357848", 30f, footerY + 24f, paint)
        paint.color = Color.WHITE
        paint.textSize = 8.5f
        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
        canvas.drawText("Jamal Precision Tech • Hall Road, Lahore • Bank: Meezan Bank Ltd Account 0214-0105874219", 30f, footerY + 42f, paint)

        document.finishPage(page)

        val dir = File(context.cacheDir, "rate_lists")
        if (!dir.exists()) dir.mkdirs()

        val dateSlug = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(Date())
        val ratePdf = File(dir, "JPT_Wholesale_Rate_List_$dateSlug.pdf")
        try {
            val fos = FileOutputStream(ratePdf)
            document.writeTo(fos)
            fos.flush()
            fos.close()
            document.close()
            return ratePdf
        } catch (e: Exception) {
            e.printStackTrace()
            document.close()
            return null
        }
    }

    fun openPdf(context: Context, pdfFile: File) {
        try {
            val uri = FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", pdfFile)
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(uri, "application/pdf")
                flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(Intent.createChooser(intent, "Open Document"))
        } catch (e: Exception) {
            Toast.makeText(context, "Cannot open PDF: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    fun sharePdfOnWhatsApp(context: Context, pdfFile: File, phoneInternational: String, captionText: String) {
        try {
            val uri = FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", pdfFile)
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "application/pdf"
                putExtra(Intent.EXTRA_STREAM, uri)
                putExtra(Intent.EXTRA_TEXT, captionText)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                setPackage("com.whatsapp")
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            try {
                val uri = FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", pdfFile)
                val intent = Intent(Intent.ACTION_SEND).apply {
                    type = "application/pdf"
                    putExtra(Intent.EXTRA_STREAM, uri)
                    putExtra(Intent.EXTRA_TEXT, captionText)
                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                }
                context.startActivity(Intent.createChooser(intent, "Share JPT Document"))
            } catch (ex: Exception) {
                Toast.makeText(context, "Share failed: ${ex.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
