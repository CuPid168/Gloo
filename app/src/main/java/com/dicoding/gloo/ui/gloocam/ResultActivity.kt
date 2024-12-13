package com.dicoding.gloo.ui.gloocam

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.gloo.R
import com.dicoding.gloo.api.ProductsItem
import com.dicoding.gloo.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val product = intent.getParcelableExtra<ProductsItem>(EXTRA_PRODUCT)

        if (product != null) {
            bindProductData(product)
        } else {
            Log.e(TAG, "Produk tidak ditemukan saat mengirimkan intent.")
            Toast.makeText(this@ResultActivity, "Data produk tidak tersedia.", Toast.LENGTH_SHORT).show()
        }

        binding.backButton.setOnClickListener {
            finish()
        }
    }

    private fun bindProductData(product: ProductsItem) {
        binding.productName.text = product.namaProduk ?: "Nama produk tidak tersedia"

        binding.servingSize.text = product.ukuranPorsi ?: "Tidak tersedia"
        binding.fat.text = product.lemak ?: "Tidak tersedia"
        binding.cholesterol.text = product.kolestrol ?: "Tidak tersedia"
        binding.protein.text = product.protein ?: "Tidak tersedia"
        binding.carbs.text = product.karbohidrat ?: "Tidak tersedia"
        binding.fiber.text = product.serat ?: "Tidak tersedia"
        binding.glukosa.text = product.glukosa ?: "Tidak tersedia"
        binding.salt.text = product.garam ?: "Tidak tersedia"

        val servingSizeString = product.ukuranPorsi?.replace("[^\\d.]".toRegex(), "") ?: "100"
        val servingSize = servingSizeString.toFloatOrNull() ?: 100f

        val freeSugarString = product.glukosa?.replace("[^\\d.]".toRegex(), "") ?: "0"
        val freeSugar = freeSugarString.toFloatOrNull() ?: 0f

        val saturatedFatString = product.lemak?.replace("[^\\d.]".toRegex(), "") ?: "0"
        val saturatedFat = saturatedFatString.toFloatOrNull() ?: 0f

        // Hitung nilai per 100 gram
        val freeSugarPer100g = (freeSugar / servingSize) * 100
        val saturatedFatPer100g = (saturatedFat / servingSize) * 100

        // Hitung Nutri-Grade
        val nutriGrade = getNutriGrade(freeSugarPer100g, saturatedFatPer100g)
        binding.grade.text = nutriGrade
        binding.gradeDesc.text = getGradeDescription(nutriGrade)

        displayProductImage(product.namaProduk)
    }

    private fun getNutriGrade(freeSugarPer100g: Float, saturatedFatPer100g: Float): String {
        var grade = when {
            freeSugarPer100g <= 1 -> "A"
            freeSugarPer100g > 1 && freeSugarPer100g <= 5 -> "B"
            freeSugarPer100g > 5 && freeSugarPer100g <= 10 -> "C"
            else -> "D"
        }

        grade = when (grade) {
            "A" -> if (saturatedFatPer100g > 0.7) "B" else "A"
            "B" -> if (saturatedFatPer100g > 1.2) "C" else "B"
            "C" -> if (saturatedFatPer100g > 2.8) "D" else "C"
            else -> "D"
        }

        return grade
    }

    private fun getGradeDescription(grade: String): String {
        return when (grade) {
            "A" -> "0% Gula (Tanpa Pemanis), sangat sehat."
            "B" -> "4% Gula (Pemanis), aman dikonsumsi."
            "C" -> "8% Gula, sebaiknya dikonsumsi dengan hati-hati."
            "D" -> "12% Gula, hindari konsumsi berlebihan."
            else -> "Tidak tersedia deskripsi untuk grade ini."
        }
    }

    private fun displayProductImage(productName: String?) {
        val drawableRes = when (productName?.lowercase()) {
            "better" -> R.drawable.better
            "leminerale" -> R.drawable.leminerale
            "oreo" -> R.drawable.oreo
            "pocari" -> R.drawable.pocari
            "uc1000" -> R.drawable.youc1000
            else -> R.drawable.ic_placeholder
        }

        binding.productImage.setImageResource(drawableRes)
    }

    companion object {
        const val EXTRA_PRODUCT = "extra_product"
        private const val TAG = "ResultActivity"
    }
}