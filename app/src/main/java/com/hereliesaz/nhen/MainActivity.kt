package com.hereliesaz.nhen

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class MainActivity : AppCompatActivity() {

    private lateinit var businessAdapter: BusinessAdapter
    private val allBusinesses = BusinessData.allBusinesses

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupRecyclerView()
        setupFilters()
        setupSearch()
        setupRandomizer()
    }

    private fun setupRecyclerView() {
        val recyclerView: RecyclerView = findViewById(R.id.business_recycler_view)
        businessAdapter = BusinessAdapter(allBusinesses)
        recyclerView.adapter = businessAdapter
    }

    private fun setupFilters() {
        val chipGroup: ChipGroup = findViewById(R.id.filter_chip_group)
        val categories = listOf("All") + allBusinesses.map { it.category }.distinct()
        val auras = allBusinesses.flatMap { it.auras }.distinct()
        val filters = categories + auras

        filters.forEach { filterName ->
            val chip = Chip(this).apply {
                text = filterName
                isCheckable = true
                isCheckedIconVisible = false
                setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        filterList(filterName)
                    }
                }
            }
            chipGroup.addView(chip)
        }
        // Set "All" as the default selection
        (chipGroup.getChildAt(0) as Chip).isChecked = true
    }

    private fun setupSearch() {
        val searchInput: EditText = findViewById(R.id.search_input)
        searchInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val query = s.toString().toLowerCase().trim()
                val filteredList = allBusinesses.filter {
                    it.name.toLowerCase().contains(query) || it.address.toLowerCase().contains(query)
                }
                businessAdapter.updateList(filteredList)
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun filterList(filter: String) {
        val filtered = when (filter) {
            "All" -> allBusinesses
            in allBusinesses.map { it.category }.distinct() -> {
                allBusinesses.filter { it.category == filter }
            }
            else -> { // Assumes it's an aura filter
                allBusinesses.filter { it.auras.contains(filter) }
            }
        }
        businessAdapter.updateList(filtered)
    }

    private fun setupRandomizer() {
        val randomizerButton: Button = findViewById(R.id.randomizer_button)
        randomizerButton.setOnClickListener {
            val randomBusiness = allBusinesses.random()
            AlertDialog.Builder(this)
                .setTitle(randomBusiness.name)
                .setMessage(randomBusiness.address)
                .setPositiveButton("OK", null)
                .show()
        }
    }
}