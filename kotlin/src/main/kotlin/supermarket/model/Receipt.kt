package supermarket.model

import supermarket.toUKFormat
import java.util.*

class Receipt {
    private val items = ArrayList<ReceiptItem>()
    private val discounts = ArrayList<Discount>()

    val totalPrice: Double
        get() = items.sumByDouble { it.totalPrice } - discounts.sumByDouble { it.discountAmount }

    fun addProduct(product: Product, quantity: Double, price: Double, totalPrice: Double) {
        this.items.add(ReceiptItem(product, quantity, price, totalPrice))
    }

    fun getItems(): List<ReceiptItem> {
        return ArrayList(this.items)
    }

    fun addDiscount(discount: Discount) {
        this.discounts.add(discount)
    }

    fun getDiscounts(): List<Discount> {
        return discounts
    }

    fun getTotalLineOutput(columnWidth: Int) : String {
        val pricePresentation = totalPrice.toUKFormat()
        val total = "Total: "
        val whitespace = " ".repeat(columnWidth - total.length - pricePresentation.length -2)
        return "$total $whitespace $pricePresentation"
    }
}
