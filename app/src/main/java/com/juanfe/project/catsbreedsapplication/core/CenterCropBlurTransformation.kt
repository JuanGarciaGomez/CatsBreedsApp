package com.juanfe.project.catsbreedsapplication.core

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.load.resource.bitmap.TransformationUtils
import java.security.MessageDigest

class CenterCropBlurTransformation(private val context: Context, private val blurRadius: Int) : BitmapTransformation() {

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update("CenterCropBlurTransformation".toByteArray(Charsets.UTF_8))
    }

    override fun transform(
        pool: BitmapPool,
        toTransform: Bitmap,
        outWidth: Int,
        outHeight: Int
    ): Bitmap {
        // Aplicar un center crop para el fondo desenfocado
        val centerCroppedBitmap = TransformationUtils.centerCrop(pool, toTransform, outWidth, outHeight)

        // Aplicar desenfoque al fondo
        val blurredBitmap = blurBitmap(centerCroppedBitmap)

        // Aplicar fitCenter para la imagen frontal
        val fitCenterBitmap = TransformationUtils.fitCenter(pool, toTransform, outWidth, outHeight)

        // Crear el lienzo para combinar ambas imágenes
        val resultBitmap = Bitmap.createBitmap(outWidth, outHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(resultBitmap)
        val paint = Paint()

        // Dibujar la imagen desenfocada con center crop como fondo
        canvas.drawBitmap(blurredBitmap, 0f, 0f, paint)

        // Calcular las coordenadas para centrar la imagen de encima con fitCenter
        val offsetX = (outWidth - fitCenterBitmap.width) / 2f
        val offsetY = (outHeight - fitCenterBitmap.height) / 2f

        // Dibujar la imagen normal con fit center encima, centrada
        canvas.drawBitmap(fitCenterBitmap, offsetX, offsetY, paint)

        return resultBitmap
    }

    private fun blurBitmap(bitmap: Bitmap): Bitmap {
        // Utilizamos RenderScript para aplicar un desenfoque Gaussian
        val renderScript = RenderScript.create(context)

        val input = Allocation.createFromBitmap(renderScript, bitmap)
        val output = Allocation.createTyped(renderScript, input.type)

        val blurScript = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript))
        blurScript.setRadius(blurRadius.toFloat()) // Ajusta el nivel de desenfoque
        blurScript.setInput(input)
        blurScript.forEach(output)

        output.copyTo(bitmap)

        // Liberar los recursos de RenderScript
        renderScript.destroy()
        return bitmap
    }
}


