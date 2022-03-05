package com.technorapper.onboarding.utils

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.view.View
import com.technorapper.onboarding.R

class DrawCustomMarker(locationFetchFromMapActivity: Context?, var dimension: Float) :
    View(locationFetchFromMapActivity) {
    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        val paint = Paint()
        paint.color = this.resources.getColor(R.color.white)
        paint.strokeWidth = 1f
        paint.isAntiAlias = true
        val bitmap = BitmapFactory.decodeResource(resources, R.mipmap.marker_with_text)
        val width = ((canvas.width - bitmap.width) / 2).toFloat()
        val height = (canvas.height - bitmap.height) / 2 - dimension
        canvas.drawBitmap(bitmap, width, height, null)
        //canvas.drawText("Sampe",width/2,height/2,paint);
        //canvas.drawPicture();

        /*
         * canvas.drawc canvas.drawo
         */
    }
}