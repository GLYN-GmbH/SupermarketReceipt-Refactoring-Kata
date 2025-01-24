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

    fun addItemQuantity(product: Product, quantity: Double) {
        items.add(ProductQuantity(product, quantity))
        if (productQuantities.containsKey(product)) {
            productQuantities[product] = productQuantities[product]!! + quantity
        } else {
            productQuantities[product] = quantity
        }
    }

    internal fun handleOffers(receipt: Receipt, offers: Map<Product, Offer>, catalog: SupermarketCatalog) {
        for (product in productQuantities.keys) {
            val quantity: Double = productQuantities[product] ?: 0.0
            if (!offers.containsKey(product)) continue
            val offer = offers[product]!!
            val unitPrice = catalog.getUnitPrice(product)
            val quantityAsInt = quantity.toInt()
            val numberOfXs = quantityAsInt / offer.offerType.faktor
            val discount = when {
                offer.offerType === SpecialOfferType.TwoForAmount && quantityAsInt >= 2 -> {
                    val total =
                        offer.unitPrice * (quantityAsInt / offer.offerType.faktor) + quantityAsInt % offer.offerType.faktor * unitPrice
                    val discountN = unitPrice * quantity - total
                    Discount(product, "2 for " + offer.unitPrice, discountN)
                }

                offer.offerType === SpecialOfferType.ThreeForTwo && quantityAsInt > 2 -> {
                    val discountAmount =
                        quantity * unitPrice - (numberOfXs.toDouble() * 2.0 * unitPrice + quantityAsInt % 3 * unitPrice)
                    Discount(product, "3 for 2", discountAmount)
                }

                offer.offerType === SpecialOfferType.TenPercentDiscount -> {
                    Discount(
                        product,
                        offer.percent.toString() + "% off",
                        quantity * unitPrice * offer.percent / 100.0
                    )
                }

                offer.offerType === SpecialOfferType.FiveForAmount && quantityAsInt >= 5 -> {
                    val discountTotal =
                        unitPrice * quantity - (offer.unitPrice * numberOfXs + quantityAsInt % 5 * unitPrice)
                    Discount(product, offer.offerType.faktor.toString() + " for " + offer.unitPrice, discountTotal)
                }

                else -> {
                    null
                }
            }

            if (discount != null)
                receipt.addDiscount(discount)
        }
    }


}
