package com.example.demo.controller

import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleIntegerProperty
import tornadofx.*

class MainController: Controller() {
    var totalAmountPerPerson = SimpleDoubleProperty(0.0)
    var tipPercentageAmount = SimpleDoubleProperty(0.0)
    var sliderPercentageAmount = SimpleIntegerProperty(0)

    fun calculate(billAmountValue: SimpleDoubleProperty, splitByValue: SimpleIntegerProperty, tipPercentageValue:  SimpleIntegerProperty) {
        tipPercentageAmount.cleanBind(billAmountValue * tipPercentageValue / 100)
        totalAmountPerPerson.cleanBind((tipPercentageAmount.value.toProperty() + billAmountValue)/splitByValue)
        sliderPercentageAmount.cleanBind(tipPercentageValue)

    }
}