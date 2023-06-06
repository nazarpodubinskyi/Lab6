package com.example.demo.data

import java.io.Serializable
import java.util.Locale.Category

class Product : Serializable {
    var id: Int
    val name: String
    val category: String
    val description: String
    var price: Int
    var quantity: Int
    var date: String
    var inStock: String

    constructor(id: Int, name: String, category: String, description: String, price: Int, quantity: Int, date: String, inStock: String) {
        this.id = id
        this.name = name
        this.category = category
        this.description = description
        this.price = price
        this.quantity = quantity
        this.date = date
        this.inStock = inStock

    }

    constructor(name: String, category: String, description: String, price: Int, quantity: Int, date: String, inStock: String) {
        id = 0
        this.name = name
        this.category = category
        this.description = description
        this.price = price
        this.quantity = quantity
        this.date = date
        this.inStock = inStock
    }

    override fun toString(): String {
        return "Назва='" + name + '\'' +
                ", Категорія='" + category + '\'' +
                ", Опис=" + description +
                ", Ціна='" + price + '\'' +
                ", Кількість=" + quantity +
                ", Дата+" + date +
                ", Наявність=" + inStock

    }
}
