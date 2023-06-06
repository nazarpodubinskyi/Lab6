package com.example.demo

import com.example.demo.data.Product
import com.example.demo.data.Repository
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.Node
import javafx.scene.control.CheckBox
import javafx.scene.control.TextField
import javafx.stage.Stage
import java.util.*

class AddProductController {
    private var _repository: Repository? = null
    private var _mainController: MainController? = null
    fun set_mainController(_mainController: MainController?) {
        this._mainController = _mainController
    }

    fun set_repository(_repository: Repository?) {
        this._repository = _repository
    }

    @FXML
    var name: TextField? = null

    @FXML
    var category: TextField? = null

    @FXML
    var description: TextField? = null

    @FXML
    var price: TextField? = null

    @FXML
    var quantity: TextField? = null

    @FXML
    var date: TextField? = null

    @FXML
    var inStock: TextField? = null
    fun addFilmToFile(actionEvent: ActionEvent) {
        val name_ = name!!.text
        val category_ = category!!.text
        val description_ = description!!.text
        val price_ = price!!.text.toInt()
        val quantity_ = quantity!!.text.toInt()
        val date_ = date!!.text
        val inStock_ = inStock!!.text
        val newProduct = Product(name_, category_, description_, price_, quantity_, date_, inStock_)
        _repository!!.addProduct(newProduct)
        val source = actionEvent.source as Node
        val stage = source.scene.window as Stage
        _mainController!!.updateListView()
        stage.close()
    }
}
