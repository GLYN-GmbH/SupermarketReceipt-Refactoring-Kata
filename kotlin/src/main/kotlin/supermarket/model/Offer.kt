package supermarket.model

data class Offer(
     val offerType: SpecialOfferType,
     val product: Product,
     val unitPrice: Double = 0.0,
     val percent: Double = 0.0
)
