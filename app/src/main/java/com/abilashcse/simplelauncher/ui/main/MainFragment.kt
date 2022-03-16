package com.abilashcse.simplelauncher.ui.main

import android.content.Context.BATTERY_SERVICE
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.BatteryManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.abilashcse.simplelauncher.R
import com.abilashcse.simplelauncher.databinding.MainActivityBinding
import com.abilashcse.simplelauncher.databinding.MainFragmentBinding
import com.abilashcse.simplelauncher.viewmodel.MainViewModel


class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: MainFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        val bm = requireContext().getSystemService(BATTERY_SERVICE) as BatteryManager
        val batteryLevel = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
        "$batteryLevel %".also { binding.batteryLevelPercentage.text = it }
        binding.batteryProgressBar.max = 100
        binding.batteryProgressBar.progressTintList = getBatteryWarningColour(batteryLevel)
        binding.batteryProgressBar.progress = batteryLevel
    }

    private fun getBatteryWarningColour(
        batteryLevel: Int
    ): ColorStateList {
        return when (batteryLevel) {
            in 11..30 -> ColorStateList.valueOf(Color.YELLOW)
            in 0..10 -> ColorStateList.valueOf(Color.RED)
            else -> {
                ColorStateList.valueOf(Color.GREEN)
            }
        }
    }

}
