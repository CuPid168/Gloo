package com.dicoding.gloo.ui.gloocam

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.OrientationEventListener
import android.view.Surface
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.dicoding.gloo.R
import com.dicoding.gloo.api.ApiConfig
import com.dicoding.gloo.api.ProductResponse
import com.dicoding.gloo.databinding.ActivityGloocamBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class GloocamActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGloocamBinding
    private var cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
    private var imageCapture: ImageCapture? = null

    val productIdMapping = mapOf(
        0 to "VfQqB003y4GN72nCuX15",  // "better"
        1 to "f9TtPHDap0grDC35i5t8",  // "LeMinerale"
        2 to "1pw4hhe2tHyOzr0c5F2u",  // "Oreo"
        3 to "9VouR6Y4jHvt2r1utLKX",  // "pocari.jpg"
        4 to "GZptmQk4ito3p6inF6UU"   // "UC1000"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGloocamBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkCameraPermission()
        binding.captureImage.setOnClickListener { takePhoto() }

        binding.backButton.setOnClickListener {
            finish()
        }

        // Load GIF for loading indicator
        Glide.with(this)
            .asGif()
            .load(R.drawable.loading_gif) // Replace with your GIF file in drawable folder
            .into(binding.loadingGif)
    }

    override fun onResume() {
        super.onResume()
        hideSystemUI()
        startCamera() // Pastikan kamera dimulai ulang saat aktivitas dilanjutkan
    }

    private fun checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
            != android.content.pm.PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.CAMERA),
                CAMERA_PERMISSION_REQUEST_CODE
            )
        } else {
            startCamera()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE &&
            grantResults.isNotEmpty() &&
            grantResults[0] == android.content.pm.PackageManager.PERMISSION_GRANTED
        ) {
            startCamera()
        } else {
            Toast.makeText(this, "Izin kamera diperlukan.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.viewFinder.surfaceProvider)
                }

            imageCapture = ImageCapture.Builder()
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                .build()

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this,
                    cameraSelector,
                    preview,
                    imageCapture
                )

            } catch (exc: Exception) {
                Toast.makeText(
                    this@GloocamActivity,
                    "Gagal memunculkan kamera.",
                    Toast.LENGTH_SHORT
                ).show()
                Log.e(TAG, "startCamera: ${exc.message}")
            }
        }, ContextCompat.getMainExecutor(this))
    }

    private fun takePhoto() {
        val imageCapture = imageCapture ?: return

        val photoFile = createCustomTempFile(application)

        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        // Tampilkan loading indicator
        binding.loadingGif.visibility = View.VISIBLE

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    classifyImage(photoFile)
                }

                override fun onError(exc: ImageCaptureException) {
                    binding.loadingGif.visibility = View.GONE // Sembunyikan jika gagal
                    Toast.makeText(
                        this@GloocamActivity,
                        "Gagal mengambil gambar.",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.e(TAG, "onError: ${exc.message}")
                }
            }
        )
    }


    private fun classifyImage(photoFile: File) {
        val bitmap = BitmapFactory.decodeFile(photoFile.path)
        val classifierHelper = ImageClassifierHelper(
            context = this,
            classifierListener = object : ImageClassifierHelper.ClassifierListener {
                override fun onError(error: String) {
                    binding.loadingGif.visibility = View.GONE
                    Toast.makeText(this@GloocamActivity, error, Toast.LENGTH_SHORT).show()
                }

                override fun onResults(labelIndex: Int, confidence: Float) {
                    binding.loadingGif.visibility = View.GONE
                    if (confidence > 0.1f) { // Threshold minimal
                        fetchProductDetails(labelIndex)
                    } else {
                        Toast.makeText(
                            this@GloocamActivity,
                            "Confidence terlalu rendah.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        )
        classifierHelper.classifyImageFromBitmap(bitmap)
    }

    private fun hideSystemUI() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private val orientationEventListener by lazy {
        object : OrientationEventListener(this) {
            override fun onOrientationChanged(orientation: Int) {
                if (orientation == ORIENTATION_UNKNOWN) {
                    return
                }

                val rotation = when (orientation) {
                    in 45 until 135 -> Surface.ROTATION_270
                    in 135 until 225 -> Surface.ROTATION_180
                    in 225 until 315 -> Surface.ROTATION_90
                    else -> Surface.ROTATION_0
                }

                imageCapture?.targetRotation = rotation
            }
        }
    }

    private fun fetchProductDetails(index: Int) {
        if (index < 0 || index >= productIdMapping.size) {
            binding.loadingGif.visibility = View.GONE // Sembunyikan loading jika gagal
            Toast.makeText(this@GloocamActivity, "ID produk tidak ditemukan.", Toast.LENGTH_SHORT).show()
            return
        }

        val productId = productIdMapping[index]
        if (productId.isNullOrEmpty()) {
            binding.loadingGif.visibility = View.GONE
            Toast.makeText(this@GloocamActivity, "ID produk tidak ditemukan.", Toast.LENGTH_SHORT).show()
            return
        }

        val client = ApiConfig.getApiService().getProductById(productId)
        client.enqueue(object : Callback<ProductResponse> {
            override fun onResponse(
                call: Call<ProductResponse>,
                response: Response<ProductResponse>
            ) {
                binding.loadingGif.visibility = View.GONE // Sembunyikan loading
                if (response.isSuccessful) {
                    val productResponse = response.body()
                    if (productResponse != null && productResponse.product != null) {
                        val product = productResponse.product
                        val intent = Intent(this@GloocamActivity, ResultActivity::class.java)
                        intent.putExtra(ResultActivity.EXTRA_PRODUCT, product)
                        startActivity(intent)
                    } else {
                        Toast.makeText(
                            this@GloocamActivity,
                            "Produk tidak tersedia.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        this@GloocamActivity,
                        "Gagal mendapatkan data produk.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                binding.loadingGif.visibility = View.GONE // Sembunyikan loading
                Toast.makeText(
                    this@GloocamActivity,
                    "Error: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }


    override fun onStart() {
        super.onStart()
        orientationEventListener.enable()
    }

    override fun onStop() {
        super.onStop()
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.get().unbindAll()
        orientationEventListener.disable()
    }

    companion object {
        private const val TAG = "GloocamActivity"
        const val EXTRA_CAMERAX_IMAGE = "CameraX Image"
        const val CAMERAX_RESULT = 200
        private const val CAMERA_PERMISSION_REQUEST_CODE = 100
    }
}
