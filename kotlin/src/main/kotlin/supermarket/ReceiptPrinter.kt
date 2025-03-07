package supermarket

import supermarket.model.Receipt
import java.util.*

class ReceiptPrinter @JvmOverloads constructor(private val columnWidth: Int = 40) {

    fun printReceipt(receipt: Receipt): String {
        val result = StringBuilder()
        receipt.getItems().forEach { item ->
            result.append(item.getLineOutput())
        }

        receipt.getDiscounts().forEach { discount ->
            result.append(discount.getLineOutput(columnWidth))
        }
        result.append("\n")

        result.append(receipt.getTotalLineOutput(columnWidth))
        return result.toString()
    }
}

fun Double.toUKFormat() = String.format(Locale.UK, "%.2f", this)