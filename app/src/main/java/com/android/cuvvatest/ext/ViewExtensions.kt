package com.android.cuvvatest.ext

import android.view.View
import android.widget.ImageView
import com.android.cuvvatest.R

fun View.display(displayed: Boolean) {
    visibility = if (displayed) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

fun View.show(visible: Boolean) {
    visibility = if (visible) {
        View.VISIBLE
    } else {
        View.INVISIBLE
    }
}

fun ImageView.toCarLogo(make: String) {
    setImageResource(
        when(make) {
            "Volkswagen" -> R.drawable.carlogo_volkswagen
            "Mercedes-Benz" -> R.drawable.carlogo_mercedes
            "MINI" -> R.drawable.carlogo_mini
            else -> R.drawable.ic_car
        }
    )
}
