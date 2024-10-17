data class Product(
    var name: String = "",
    var price: Double = 0.0,
    var description: String = "",
    var category: String = "",
    var company: String = "",
    var image: String = ""
) {
    // Firebase requires an empty constructor
    constructor() : this("", 0.0, "", "", "", "")
}
