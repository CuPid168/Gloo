package com.dicoding.gloo.ui.gloocam

import android.content.Context
import android.graphics.Bitmap
import com.dicoding.gloo.ml.Modelv4
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer
import java.nio.ByteOrder

class ImageClassifierHelper(
    val context: Context,
    val classifierListener: ClassifierListener?
) {
    fun classifyImageFromBitmap(bitmap: Bitmap) {
        try {
            val model = Modelv4.newInstance(context)
            val byteBuffer = convertBitmapToByteBuffer(bitmap)
            val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)
            inputFeature0.loadBuffer(byteBuffer)

            val outputs = model.process(inputFeature0)
            val outputFeature0 = outputs.outputFeature0AsTensorBuffer

            val results = outputFeature0.floatArray

            val maxIndex = results.indices.maxByOrNull { results[it] } ?: -1
            val maxConfidence = results[maxIndex]
            classifierListener?.onResults(maxIndex, maxConfidence)
            model.close()

        } catch (e: Exception) {
            classifierListener?.onError(e.message ?: "Error processing image")
        }
    }

    private fun convertBitmapToByteBuffer(bitmap: Bitmap): ByteBuffer {
        val byteBuffer = ByteBuffer.allocateDirect(4 * 224 * 224 * 3)
        byteBuffer.order(ByteOrder.nativeOrder())

        val resizedBitmap = Bitmap.createScaledBitmap(bitmap, 224, 224, true)
        val intValues = IntArray(224 * 224)
        resizedBitmap.getPixels(intValues, 0, resizedBitmap.width, 0, 0, resizedBitmap.width, resizedBitmap.height)

        for (pixelValue in intValues) {
            val r = ((pixelValue shr 16) and 0xFF) / 127.5f - 1.0f
            val g = ((pixelValue shr 8) and 0xFF) / 127.5f - 1.0f
            val b = (pixelValue and 0xFF) / 127.5f - 1.0f

            byteBuffer.putFloat(r)
            byteBuffer.putFloat(g)
            byteBuffer.putFloat(b)
        }

        return byteBuffer
    }

    interface ClassifierListener {
        fun onError(error: String)
        fun onResults(labelIndex: Int, confidence: Float)
    }
}