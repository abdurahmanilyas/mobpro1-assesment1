package org.d3if3014.phsolve

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import android.widget.TextView
import android.widget.Toast

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
        val beratBasah = basahInputLayout.editText?.text.toString().toDoubleOrNull()
        val beratKering = keringInputLayout.editText?.text.toString().toDoubleOrNull()

        if (beratBasah ==null || beratKering ==null) {
            Toast.makeText(this, "Kolom input tidak boleh kosong", Toast.LENGTH_SHORT).show()
            return
        }

        val jenisTanaman = jenisTanamanSpinner.selectedItem.toString()

        val hasil = (beratBasah - beratKering) / beratKering * 100
        hasilTextView.text = String.format("Hasil: %.2f %%", hasil)

        // Pengkondisian untuk presentase kelembapan tanah optimal
        var ideal: Pair<Double, Double>? = null
        when (jenisTanaman) {
            "Padi" -> ideal = Pair(70.0, 80.0)
            "Jagung" -> ideal = Pair(60.0, 70.0)
            "Tebu" -> ideal = Pair(60.0, 70.0)
            "Kentang" -> ideal = Pair(60.0, 70.0)
            "Jeruk" -> ideal = Pair(50.0, 70.0)
            "Apel" -> ideal = Pair(60.0, 70.0)
        }

        if (ideal != null && hasil in ideal.first..ideal.second) {
            hasilTextView.append("\nKelembaban tanah optimal (${ideal.first}% - ${ideal.second}%) untuk $jenisTanaman")
        } else if (ideal != null && hasil < ideal.first) {
            hasilTextView.append("\nKelembaban tanah terlalu rendah (${ideal.first}% - ${ideal.second}%) untuk $jenisTanaman")
        } else if (ideal != null && hasil > ideal.second) {
            hasilTextView.append("\nKelembaban tanah terlalu tinggi (${ideal.first}% - ${ideal.second}%) untuk $jenisTanaman")
        } else {
            hasilTextView.append("\nData kelembaban tanah tidak tersedia untuk $jenisTanaman")
        }
    }
}