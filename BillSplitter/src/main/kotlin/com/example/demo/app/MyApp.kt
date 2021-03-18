package com.example.demo.app

import com.example.demo.view.MainView
import javafx.stage.Stage
import tornadofx.App

class MyApp: App(MainView::class, Styles::class)
{
    override fun start(stage: Stage) {
        with(stage){
            height = 300.0
            width = 300.0
        }
        super.start(stage)
    }
}