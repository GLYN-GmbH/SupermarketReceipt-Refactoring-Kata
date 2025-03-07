package supermarket.model

import supermarket.toUKFormat

data class Discount(val product: Product, val description: String, val discountAmount: Double) {
    fun getLineOutput(columnWidth: Int):String {
        val productPresentation = product.name
        val pricePresentation = discountAmount.toUKFormat()

        val columnLengthList = listOf(productPresentation.length, description.length, pricePresentation.length)
        return "$description($productPresentation)${" ".repeat(getWidthCalculation(columnWidth, columnLengthList))}-$pricePresentation\n"
    }


    private fun getWidthCalculation(
        columnWidth: Int,
        columnLengthList: List<Int>
    ) = columnWidth - 3 - columnLengthList.sum()
}
