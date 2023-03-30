package org.d3if3014.phsolve

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var basahInputLayout: TextInputLayout
    private lateinit var keringInputLayout: TextInputLayout
    private lateinit var jenisTanamanSpinner: Spinner
    private lateinit var hitungButton: MaterialButton
    private lateinit var hasilTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        basahInputLayout = findViewById(R.id.basah_input_layout)
        keringInputLayout = findViewById(R.id.kering_input_layout)
        jenisTanamanSpinner = findViewById(R.id.jenis_tanaman_spinner)
        hitungButton = findViewById(R.id.hitung_button)
        hasilTextView = findViewById(R.id.hasil_text_view)

        ArrayAdapter.createFromResource(
            this,
            R.array.jenis_tanaman,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            jenisTanamanSpinner.adapter = adapter
        }

        hitungButton.setOnClickListener {
            hitung()
        }
    }

    @SuppressLint("StringFormatMatches")
    private fun hitung() {
        val beratBasah = basahInputLayout.editText?.text.toString().toDoubleOrNull() ?: return
        val beratKering = keringInputLayout.editText?.text.toString().toDoubleOrNull() ?: return

        val jenisTanaman = jenisTanamanSpinner.selectedItem.toString()

        val hasil = (beratBasah - beratKering) / beratKering * 100
        hasilTextView!!.text = String.format("Hasil: %.2f %%", hasil)

        // Pengkondisian untuk presentase kelembapan tanah optimal
        var ideal: Pair<Double, Double>? = null
        when (jenisTanaman) {
            "padi" -> ideal = Pair(20.0, 30.0)
            "jagung" -> ideal = Pair(25.0, 30.0)
            "kedelai" -> ideal = Pair(20.0, 30.0)
            "kacang tanah" -> ideal = Pair(25.0, 30.0)
            "sayuran" -> ideal = Pair(40.0, 80.0)
            "buah-buahan" -> ideal = Pair(60.0, 80.0)
        }

        if (ideal != null && hasil in ideal.first..ideal.second) {
            hasilTextView.append("\nKelembaban tanah optimal untuk $jenisTanaman")
        } else {
            hasilTextView.append("\nKelembaban tanah kurang optimal untuk $jenisTanaman")
        }
    }
}