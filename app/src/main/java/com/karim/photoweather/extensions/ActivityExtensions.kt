package com.karim.photoweather.extensions

import androidx.activity.ComponentActivity
import com.karim.photoweather.utils.TRANSFORMTION_PARAMS
import com.skydoves.transformationlayout.onTransformationEndContainer

fun ComponentActivity.onTransformationEndContainerApplyParams() {
    onTransformationEndContainer(intent.getParcelableExtra(TRANSFORMTION_PARAMS))
}