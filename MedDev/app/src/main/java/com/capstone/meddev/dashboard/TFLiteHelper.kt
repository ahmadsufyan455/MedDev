package com.capstone.meddev.dashboard

import android.app.Activity
import android.graphics.Bitmap
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.common.FileUtil
import org.tensorflow.lite.support.common.TensorOperator
import org.tensorflow.lite.support.common.TensorProcessor
import org.tensorflow.lite.support.common.ops.NormalizeOp
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.image.ops.ResizeWithCropOrPadOp
import org.tensorflow.lite.support.label.TensorLabel
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.FileInputStream
import java.io.IOException
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel
import java.util.*

class TFLiteHelper(private val context: Activity) {

    private var imageSizeX = 0
    private var imageSizeY = 0

    private var labels: List<String>? = null
    private var tflite: Interpreter? = null

    private val tfliteModel: MappedByteBuffer? = null
    private var inputImageBuffer: TensorImage? = null
    private var outputProbabilityBuffer: TensorBuffer? = null
    private var probabilityProcessor: TensorProcessor? = null

    private val IMAGE_MEAN = 0.0f
    private val IMAGE_STD = 1.0f

    private val PROBABILITY_MEAN = 0.0f
    private val PROBABILITY_STD = 255.0f

    fun init() {
        try {
            val opt = Interpreter.Options()
            tflite = Interpreter(loadmodelfile(context)!!, opt)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun loadImage(bitmap: Bitmap): TensorImage {
        // Loads bitmap into a TensorImage.
        inputImageBuffer!!.load(bitmap)

        // Creates processor for the TensorImage.
        val cropSize = Math.min(bitmap.width, bitmap.height)
        // TODO(b/143564309): Fuse ops inside ImageProcessor.
        val imageProcessor = ImageProcessor.Builder()
            .add(ResizeWithCropOrPadOp(cropSize, cropSize))
            .add(ResizeOp(imageSizeX, imageSizeY, ResizeOp.ResizeMethod.NEAREST_NEIGHBOR))
            .add(getPreprocessNormalizeOp())
            .build()
        return imageProcessor.process(inputImageBuffer)
    }

    @Throws(IOException::class)
    private fun loadmodelfile(activity: Activity?): MappedByteBuffer? {
        val MODEL_NAME = "model.tflite"
        val fileDescriptor = activity!!.assets.openFd(MODEL_NAME)
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val startoffset = fileDescriptor.startOffset
        val declaredLength = fileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startoffset, declaredLength)
    }

    fun classifyImage(bitmap: Bitmap) {
        val imageTensorIndex = 0
        val imageShape = tflite!!.getInputTensor(imageTensorIndex).shape() // {1, height, width, 3}
        imageSizeY = imageShape[1]
        imageSizeX = imageShape[2]
        val imageDataType = tflite!!.getInputTensor(imageTensorIndex).dataType()
        val probabilityTensorIndex = 0
        val probabilityShape =
            tflite!!.getOutputTensor(probabilityTensorIndex).shape() // {1, NUM_CLASSES}
        val probabilityDataType = tflite!!.getOutputTensor(probabilityTensorIndex).dataType()
        inputImageBuffer = TensorImage(imageDataType)
        outputProbabilityBuffer =
            TensorBuffer.createFixedSize(probabilityShape, probabilityDataType)
        probabilityProcessor = TensorProcessor.Builder().add(getPostprocessNormalizeOp()).build()
        inputImageBuffer = loadImage(bitmap)
        tflite!!.run(inputImageBuffer!!.buffer, outputProbabilityBuffer!!.buffer.rewind())
    }

    fun showresult(): List<String>? {
        labels = try {
            FileUtil.loadLabels(context!!, "model.txt")
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
        val labeledProbability = TensorLabel(
            labels!!, probabilityProcessor!!.process(outputProbabilityBuffer)
        )
            .mapWithFloatValue
        val maxValueInMap = Collections.max(labeledProbability.values)
        val result: MutableList<String> = ArrayList()
        for ((key, value) in labeledProbability) {
            if (value == maxValueInMap) {
                result.add(key)
            }
        }
        return result
    }

    private fun getPreprocessNormalizeOp(): TensorOperator? {
        return NormalizeOp(IMAGE_MEAN, IMAGE_STD)
    }

    private fun getPostprocessNormalizeOp(): TensorOperator? {
        return NormalizeOp(PROBABILITY_MEAN, PROBABILITY_STD)
    }
}