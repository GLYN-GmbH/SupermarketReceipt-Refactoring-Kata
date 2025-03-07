package supermarket

import supermarket.model.Receipt
import java.util.*

class ReceiptPrinter @JvmOverloads constructor(private val columnWidth: Int = 40) {

    fun printReceipt(receipt: Receipt): String = receipt.print(columnWidth)
}


fun Double.toUKFormat() = String.format(Locale.UK, "%.2f", this)