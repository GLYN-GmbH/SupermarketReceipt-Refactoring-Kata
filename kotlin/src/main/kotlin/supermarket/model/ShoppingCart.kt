package supermarket.model

import java.util.ArrayList
import java.util.HashMap

class ShoppingCart {

    private val items = ArrayList<ProductQuantity>()
    internal var productQuantities: MutableMap<Product, Double> = HashMap()


    internal fun getItems(): List<ProductQuantity> {
        return ArrayList(items)
    }

    internal fun addItem(product: Product) {
        this.addItemQuantity(product, 1.0)
    }

    internal fun productQuantities(): Map<Product, Double> {
        return productQuantities
    }


    fun addItemQuantity(product: Product, quantity: Double) {
        items.add(ProductQuantity(product, quantity))
        if (productQuantities.containsKey(product)) {
            productQuantities[product] = productQuantities.getValue(product) + quantity
        } else {
            productQuantities[product] = quantity
        }
    }

    internal fun handleOffers(receipt: Receipt, offers: Map<Product, Offer>, catalog: SupermarketCatalog) {
        for (productName in productQuantities().keys) {
            val productQuantity = productQuantities.getValue(productName)

            if (!offers.containsKey(productName)) return

            val offer = offers.getValue(productName)
            val unitPrice = catalog.getUnitPrice(productName)
            val quantityAsInt = productQuantity.toInt()
            val productTotalPrice = productQuantity * unitPrice

            val (discountDescription, discountTotal) = calculateDiscount(offer, quantityAsInt, unitPrice, productTotalPrice)

            discountTotal?.let { receipt.addDiscount(Discount(productName, discountDescription, discountTotal)) }
        }
    }

    private fun calculateDiscount(offer: Offer, quantityAsInt: Int, unitPrice: Double, productTotalPrice: Double): Pair<String, Double?> {
        return when (offer.offerType) {
            SpecialOfferType.ThreeForTwo -> {
                if (quantityAsInt < 3) return Pair("", null)
                val discountTotal = (quantityAsInt / 3) * unitPrice
                Pair("3 for 2", discountTotal)
            }
            SpecialOfferType.FiveForAmount -> {
                if (quantityAsInt < 5) return Pair("", null)
                val discountTotal = productTotalPrice - (offer.argument * (quantityAsInt / 5) + quantityAsInt % 5 * unitPrice)
                Pair("5 for ${offer.argument}", discountTotal)
            }
            SpecialOfferType.TwoForAmount -> {
                if (quantityAsInt < 2) return Pair("", null)
                val discountTotal = productTotalPrice - (offer.argument * (quantityAsInt / 2) + quantityAsInt % 2 * unitPrice)
                Pair("2 for ${offer.argument}", discountTotal)
            }
            SpecialOfferType.TenPercentDiscount -> {
                val discountTotal = productTotalPrice * 0.1
                Pair("10.0% off", discountTotal)
            }
        }
    }
}
