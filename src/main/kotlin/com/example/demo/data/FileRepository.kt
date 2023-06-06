package com.example.demo.data

import java.io.*
import java.util.*
import java.util.function.ToIntFunction

class FileRepository(private val fileName: String) : Repository {
    private var products: MutableList<Product>

    init {
        products = ArrayList<Product>()
    }

    val allProducts: List<Any>
        get() {
            reloadData()
            return products
        }


    private fun reloadData() {
        if (File(fileName).exists()) {
            try {
                FileInputStream(fileName).use { fileInputStream ->
                    try {
                        ObjectInputStream(fileInputStream).use { objectInputStream -> products = objectInputStream.readObject() as MutableList<Product> }
                    } catch (e: ClassNotFoundException) {
                        throw RuntimeException(e)
                    }
                }
            } catch (e: FileNotFoundException) {
                throw RuntimeException(e)
            } catch (e: IOException) {
                //throw new RuntimeException(e);
            }
        }
    }

    override fun getById(id: Int): Product? {
        return null
    }

    override fun addProduct(product: Product): Boolean {
        var id = 0
        if (products.size > 0) {
            val maxId = products.stream().mapToInt(ToIntFunction<Product> { c: Product -> c.id }).max()
            if (maxId != null) {
                id = maxId.asInt
            }
        }
        product.id = id + 1
        products.add(product)
        try {
            save()
        } catch (e: IOException) {
            return false
        }
        return true
    }


    override fun getAll(): List<Product?> {
        reloadData()
        return products
    }

    @Throws(IOException::class)
    private fun save() {
        FileOutputStream(fileName).use { fileOutputStream -> ObjectOutputStream(fileOutputStream).use { objectOutputStream -> objectOutputStream.writeObject(products) } }
    }

    override fun getAllByCategory(actor: String?): List<Product>? {
        return null
    }



    override fun deleteProduct(id: Int): Boolean {
        return false
    }

    override fun getAllByInStock(price: String?): List<Product>? {
        return null
    }
}
