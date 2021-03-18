package com.example.demo.view

import com.example.demo.app.Styles
import com.example.demo.controller.MainController
import javafx.beans.binding.Bindings
import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.geometry.Orientation
import javafx.geometry.Pos
import javafx.scene.control.ButtonType
import javafx.scene.control.ComboBox
import javafx.scene.control.Slider
import javafx.scene.control.TextField
import javafx.scene.input.KeyCode
import tornadofx.*


class MainView : View("Hello TornadoFX") {
    private val mainController: MainController by inject()

    var splitCobmo: ComboBox<Int> by singleAssign()
    var percentSlider: Slider by singleAssign()
    var billAmountField: TextField by singleAssign()

    override val root = vbox {

        alignment = Pos.TOP_CENTER
        label {
            text = "Total amount per person"
            addClass(Styles.heading)
        }
        label() {
            addClass(Styles.heading)
            this.textProperty().bind(Bindings.concat("$", Bindings.format("%.2f", mainController.totalAmountPerPerson)))
        }

        form {
            fieldset {
                labelPosition = Orientation.HORIZONTAL
                field {
                    maxWidth = 200.0
                    text = "Bill amount"
                    billAmountField = textfield("0") {
                    }
                    billAmountField.filterInput {
                        it.controlNewText.isDouble() || it.controlNewText.isInt()
                    }
                    billAmountField.setOnKeyPressed {
                        if (it.code == KeyCode.ENTER) {
                            validateField()
                        }
                    }
                }
                field {
                    label {
                        text = "Split By:"
                    }
                    splitCobmo = combobox(values = (1..10).toList()) {
                        prefWidth = 135.0
                        value = 1
                    }
                    splitCobmo.valueProperty().onChange {
                        validateField()
                    }
                }
                field {
                    hbox {
                        spacing = 13.0
                        label {
                            text = "Total tip:"
                        }
                        label().textProperty().bind(Bindings.concat("$", Bindings.format("%.2f", mainController.tipPercentageAmount)))
                    }
                }
                field {
                    hbox {
                        spacing = 5.0
                        label {
                            text = "Tip % "
                        }
                        percentSlider = slider(min = 0, max = 100, orientation = Orientation.HORIZONTAL)
                        percentSlider.valueProperty().onChange {
                            validateField()
                        }

                        label().textProperty().bind(Bindings.concat(mainController
                                .sliderPercentageAmount, "%"))
                    }
                }
            }
        }
    }

    private fun validateField() {
        if (!billAmountField.text.toString().isEmpty()) {
            mainController.calculate(SimpleDoubleProperty(billAmountField.text.toDouble()), SimpleIntegerProperty
            (splitCobmo.value), SimpleIntegerProperty(percentSlider.value.toInt()))
        } else {
            error("Error", "EmptyField", buttons = *arrayOf(ButtonType.OK))
        }
    }
}

