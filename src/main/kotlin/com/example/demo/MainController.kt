package com.example.demo

import com.example.demo.data.DataBaseConnector
import com.example.demo.data.DataBaseRepository
import com.example.demo.data.Product
import com.example.demo.data.Repository
import javafx.collections.FXCollections
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.ComboBox
import javafx.scene.control.ListView
import javafx.stage.Stage
import java.io.IOException
import java.net.URL
import java.util.*
import java.util.stream.Collectors

class MainController : Initializable {
    @FXML
    lateinit var listProducts: ListView<*>

    @FXML
    lateinit var categoriesCombo: ComboBox<String>

    @FXML
    lateinit var inStockCombo: ComboBox<String>
    private lateinit var repository: Repository
    override fun initialize(url: URL?, resourceBundle: ResourceBundle?) {
        repository = DataBaseRepository(
                DataBaseConnector("Products"))
        updateListView()
        categoriesCombo.onAction = EventHandler {
            val selectedCategory = categoriesCombo.selectionModel.selectedItem
            filterByCategory(selectedCategory)
        }
        inStockCombo.onAction = EventHandler {
            val selectedInStock = inStockCombo.selectionModel.selectedItem
            filterByInStock(selectedInStock)
        }
    }

    fun updateListView() {
        val products = repository.all
        val productsList = FXCollections.observableList(products)
        listProducts.items = productsList
        val categories = products
            .stream()
            .map<String> { product: Product -> product.category }
            .distinct()
            .collect(Collectors.toList<String>())
        categories.add("all")
        val actorsList = FXCollections.observableList(categories)
        categoriesCombo.items = actorsList
        categoriesCombo.selectionModel.select(categories.size - 1)
        val formats = products
                .stream()
                .map<String> { product: Product -> product.inStock }
                .distinct()
                .collect(Collectors.toList<String>())
        formats.add("all")
        val formatsList = FXCollections.observableList(formats)
        inStockCombo.items = formatsList
        inStockCombo.selectionModel.select(formats.size - 1)
    }


    @FXML
    fun deleteProduct(actionEvent: ActionEvent?) {
        val toDelete = listProducts.selectionModel.selectedItem as Product
        repository.deleteProduct(toDelete.id)
        updateListView()
    }


    @FXML
    fun addNewProduct(actionEvent: ActionEvent?) {
        val newWindow = Stage()
        val loader = FXMLLoader(Lab_6::class.java.getResource(
                "add-film-form.fxml"
        )
        )
        var root: Parent? = null
        root = try {
            loader.load()
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
        newWindow.title = "Додати товар"
        newWindow.scene = Scene(root, 450.0, 300.0)
        //newWindow.initModality(Modality.WINDOW_MODAL);
        val secondController = loader.getController<AddProductController>()
        secondController.set_repository(repository)
        secondController.set_mainController(this)
        newWindow.show()
    }

    fun filterByCategory(category: String) {
        val products = if (category == "all") {
            repository.all
        } else {
            repository.getAllByCategory(category)
        }
        val filmsList = FXCollections.observableList(products)
        listProducts.items = filmsList
        inStockCombo.selectionModel.select("all")
    }


    fun filterByInStock(inStock: String?) {
        val products = if (inStock == "all") {
            repository.all
        } else {
            repository.getAllByInStock(inStock)
        }
        val productsList = FXCollections.observableList(products)
        listProducts.items = productsList
        categoriesCombo.selectionModel.select("all")
    }

}
