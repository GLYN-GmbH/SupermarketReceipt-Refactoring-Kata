package supermarket.model

import supermarket.toUKFormat

data class Discount(val product: Product, val description: String, val discountAmount: Double) {
    fun getLineOutput(columnWidth: Int): String {
        val productPresentation = product.name
        val pricePresentation = discountAmount.toUKFormat()

        val columnLengthList = listOf(3, productPresentation.length, description.length, pricePresentation.length)
        val whitespaceSize = columnWidth - columnLengthList.sum()
        return "$description($productPresentation)${" ".repeat(whitespaceSize)}-$pricePresentation"
    }


}
