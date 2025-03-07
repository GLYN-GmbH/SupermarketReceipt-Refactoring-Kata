package supermarket.model

import supermarket.toUKFormat
import java.util.*

data class ReceiptItem(val product: Product, val quantity: Double, val price: Double, val totalPrice: Double) {
    fun getLineOutput(columnWidth: Int = 40): String {
        val name = product.name
        val totalPriceUkFormat = totalPrice.toUKFormat()
        val whitespaceSize = columnWidth - name.length - totalPriceUkFormat.length
        val productNameLine = "$name${" ".repeat(whitespaceSize)}$totalPriceUkFormat"

        return if (quantity != 1.0)
            "$productNameLine\n  ${price.toUKFormat()} * ${presentQuantity()}"
        else
            productNameLine
    }

    private fun presentQuantity(): String =
        if (ProductUnit.Each == product.unit)
            String.format("%x", quantity.toInt())
        else
            String.format(Locale.UK, "%.3f", quantity)

}
