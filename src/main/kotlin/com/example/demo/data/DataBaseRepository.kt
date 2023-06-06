package com.example.demo.data

import java.sql.SQLException
import java.util.*

class DataBaseRepository(private val dataBaseConnector: DataBaseConnector) : Repository {
    init {
        try {
            dataBaseConnector.connection.use { conn ->
                val tableCreateStr = """CREATE TABLE IF NOT EXISTS Products (
    id INT NOT NULL AUTO_INCREMENT,
    Name VARCHAR(50),
    Category VARCHAR(50),
    Description VARCHAR(50),
    Price INT,
    Quantity INT,
    Date VARCHAR(50),
    InStock VARCHAR(50),
    PRIMARY KEY (id)
);"""
                val createTable = conn.createStatement()
                createTable.execute(tableCreateStr)
            }
        } catch (e: SQLException) {
            throw RuntimeException(e)
        }
    }

    override fun getAll(): List<Product> {
        val products: MutableList<Product> = ArrayList()
        try {
            dataBaseConnector.connection.use { connection ->
                val statement = connection.createStatement()
                val rs = statement.executeQuery("select * from Products")
                while (rs.next()) {


                    products.add(Product(rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getInt(5),
                            rs.getInt(6),
                            rs.getString(7),
                            rs.getString(8)))
                }
                rs.close()
            }
        } catch (exception: SQLException) {
            println("Не відбулося підключення до БД")
            exception.printStackTrace()
        }
        return products
    }

    override fun getById(id: Int): Product {
        var product: Product? = null
        try {
            dataBaseConnector.connection.use { connection ->
                val statement = connection.prepareStatement("select * from Products where id = ?")
                statement.setInt(1, id)
                val rs = statement.executeQuery()
                if (rs.next()) {

                    product = Product(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getInt(5),
                        rs.getInt(6),
                        rs.getString(7),
                        rs.getString(8),
                    )
                }
                rs.close()
            }
        } catch (exception: SQLException) {
            exception.printStackTrace()
        } finally {
            return product!!
        }
    }

    override fun addProduct(product: Product): Boolean {
        var updCount = 0

        try {
            dataBaseConnector.connection.use { conn ->
                val preparedStatement = conn.prepareStatement("INSERT INTO Products (Name, Category, Description, Price, Quantity, Date, InStock) VALUES (?,?,?,?,?,?,?)")
                preparedStatement.setString(1, product.name)
                preparedStatement.setString(2, product.category)
                preparedStatement.setString(3, product.description)
                preparedStatement.setInt(4, product.price)
                preparedStatement.setInt(5, product.quantity)
                preparedStatement.setString(6, product.date)
                preparedStatement.setString(7, product.inStock)
                updCount = preparedStatement.executeUpdate()
            }
        } catch (e: SQLException) {
            throw RuntimeException(e)
        }
        return updCount > 0
    }

    override fun deleteProduct(id: Int): Boolean {
        var updCount = 0
        try {
            dataBaseConnector.connection.use { conn ->
                val preparedStatement = conn.prepareStatement(
                        "DELETE FROM Products WHERE id = ?")
                preparedStatement.setInt(1, id)
                updCount = preparedStatement.executeUpdate()
            }
        } catch (e: SQLException) {
            throw RuntimeException(e)
        }
        return updCount > 0
    }

    override fun getAllByCategory(category: String): List<Product> {
        val products: MutableList<Product> = ArrayList()
        try {
            dataBaseConnector.connection.use { connection ->
                val statement = connection.prepareStatement(
                        "select * from Products where Category Like(?)"
                )
                statement.setString(1, "%$category%")
                val rs = statement.executeQuery()
                while (rs.next()) {
                    products.add(Product(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getInt(5),
                        rs.getInt(6),
                        rs.getString(7),
                        rs.getString(8),
                    ))
                }
                rs.close()
            }
        } catch (exception: SQLException) {
            println("Не відбулося підключення до БД")
            exception.printStackTrace()
        }
        return products
    }

    override fun getAllByInStock(inStock: String): List<Product> {
        val products: MutableList<Product> = ArrayList()
        try {
            dataBaseConnector.connection.use { connection ->
                val statement = connection.prepareStatement(
                        "select * from Products where InStock Like(?)"
                )
                statement.setString(1, "%$inStock%")
                val rs = statement.executeQuery()
                while (rs.next()) {
                    products.add(Product(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getInt(5),
                        rs.getInt(6),
                        rs.getString(7),
                        rs.getString(8),
                    ))
                }
                rs.close()
            }
        } catch (exception: SQLException) {
            println("Не відбулося підключення до БД")
            exception.printStackTrace()
        }
        return products
    }
}
