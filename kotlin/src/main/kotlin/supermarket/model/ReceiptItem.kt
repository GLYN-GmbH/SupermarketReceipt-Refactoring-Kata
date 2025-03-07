package supermarket.model

import supermarket.toUKFormat
import java.util.*

data class ReceiptItem(val product: Product, val quantity: Double, val price: Double, val totalPrice: Double) {
    fun getLineOutput(columnWidth: Int = 40): String {
        val name = product.name
        val whitespaceSize = columnWidth - name.length - totalPrice.toUKFormat().length
        var line = name + " ".repeat(whitespaceSize) + totalPrice.toUKFormat() + "\n"

        if (quantity != 1.0) {
            line += "  ${price.toUKFormat()} * ${presentQuantity()}\n"
        }
        return line
    }

    private fun presentQuantity(): String {
        return if (ProductUnit.Each.equals(product.unit))
            String.format("%x", quantity.toInt())
        else
            String.format(Locale.UK, "%.3f", quantity)
    }
}
