package supermarket.model

import supermarket.model.SpecialOfferType.*
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

            val (discountDescription, discountTotal) = calculateDiscount(
                offer,
                productQuantity,
                unitPrice
            )

            discountTotal?.let { receipt.addDiscount(Discount(productName, discountDescription, it)) }
        }
    }

    private fun calculateDiscount(
        offer: Offer,
        productQuantity: Double,
        unitPrice: Double
    ): Pair<String, Double?> {
        return when (offer.offerType) {
            ThreeForTwo        -> {
                if (productQuantity < 3) return Pair("", null)
                Pair("3 for 2", (productQuantity.toInt() / 3) * unitPrice)
            }

            FiveForAmount      -> quantityForPrice(
                5,
                productQuantity,
                offer,
                unitPrice
            )

            TwoForAmount       -> quantityForPrice(
                2,
                productQuantity,
                offer,
                unitPrice
            )

            TenPercentDiscount -> Pair("10.0% off", productQuantity * unitPrice * 0.1)
        }
    }

    private fun quantityForPrice(
        quantity: Int,
        productQuantity: Double,
        offer: Offer,
        unitPrice: Double
    ): Pair<String, Double?> {
        return if (productQuantity < quantity) Pair("", null)
        else {
            val restQuantity = productQuantity % quantity
            val discountQuantity = productQuantity.toInt() / quantity
            val totalCatalogPrice = productQuantity * unitPrice
            val normalPriceRemaining = restQuantity * unitPrice
            val dicountPrice = offer.unitPrice * discountQuantity
            val totalOfferPrice = dicountPrice + normalPriceRemaining
            val discountTotal = totalCatalogPrice - totalOfferPrice

            Pair("$quantity for ${offer.unitPrice}", discountTotal)
        }
    }

}
