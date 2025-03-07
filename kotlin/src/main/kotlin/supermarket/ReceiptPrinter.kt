package supermarket

import supermarket.model.ProductUnit
import supermarket.model.Receipt
import supermarket.model.ReceiptItem
import java.util.*

class ReceiptPrinter @JvmOverloads constructor(private val columnWidth: Int = 40) {

    fun printReceipt(receipt: Receipt): String {
        val result = StringBuilder()
        for (item in receipt.getItems()) {
            result.append(item.getLineOutput())
        }
        for (discount in receipt.getDiscounts()) {
            val productPresentation = discount.product.name
            val pricePresentation = discount.discountAmount.toUKFormat()
            val description = discount.description

            val columnLengthList = listOf(productPresentation.length, description.length, pricePresentation.length)
            result.append("$description($productPresentation)${getWhitespace(getWidthCalculation(columnLengthList))}-$pricePresentation\n")
        }
        result.append("\n")
        val pricePresentation = receipt.totalPrice.toUKFormat()
        val total = "Total: "
        val whitespace = getWhitespace(this.columnWidth - total.length - pricePresentation.length)
        result.append(total).append(whitespace).append(pricePresentation)
        return result.toString()
    }

    private fun getWidthCalculation(
        columnLengthList: List<Int>
    ) = this.columnWidth - 3 - columnLengthList.sum()

    private fun presentQuantity(item: ReceiptItem): String {
        return if (ProductUnit.Each.equals(item.product.unit))
            String.format("%x", item.quantity.toInt())
        else
            String.format(Locale.UK, "%.3f", item.quantity)
    }
}

fun getWhitespace(whitespaceSize: Int): String {
    val whitespace = StringBuilder()
    for (i in 0 until whitespaceSize) {
        whitespace.append(" ")
    }
    return whitespace.toString()
}

fun Double.toUKFormat() = String.format(Locale.UK, "%.2f", this)