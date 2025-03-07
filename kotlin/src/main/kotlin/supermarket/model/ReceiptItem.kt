package supermarket.model

import java.util.*

data class ReceiptItem(val product: Product, val quantity: Double, val price: Double, val totalPrice: Double) {
    fun getLineOutput(columnWidth: Int = 40) {
        val name = product.name
        val whitespaceSize = columnWidth - name.length - totalPrice.toUKFormat().length
        var line = name + getWhitespace(whitespaceSize) + totalPrice.toUKFormat() + "\n"

        if (quantity != 1.0) {
            line += "  ${price.toUKFormat()} * ${presentQuantity()}\n"
        }
    }

    private fun presentQuantity(): String {
        return if (ProductUnit.Each.equals(product.unit))
            String.format("%x", quantity.toInt())
        else
            String.format(Locale.UK, "%.3f", quantity)
    }
}
