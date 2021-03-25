package dev.simecek.routines.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.MaterialTimePicker.INPUT_MODE_KEYBOARD
import com.google.android.material.timepicker.TimeFormat
import dagger.hilt.android.AndroidEntryPoint
import dev.simecek.routines.R
import dev.simecek.routines.databinding.FragmentCreateRoutineBinding
import dev.simecek.routines.viewModel.CreateRoutineViewModel
import java.time.LocalTime

@AndroidEntryPoint
class CreateRoutineFragment : Fragment() {

    private val viewModel: CreateRoutineViewModel by viewModels()
    private lateinit var binding: FragmentCreateRoutineBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateRoutineBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.timePickerInput.setOnClickListener {
            showTimePicker()
        }
        binding.createButton.setOnClickListener {
            createRoutineAndRedirect()
        }
        return binding.root
    }
    private fun showTimePicker() {
        binding.timePicker.requestFocus()
        val reminder = binding.viewModel?.reminder?.value?: LocalTime.now()
        val timePicker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setTitleText(R.string.reminder)
                .setHour(reminder.hour)
                .setMinute(reminder.minute)
                .setInputMode(INPUT_MODE_KEYBOARD)
                .build()
        timePicker.addOnPositiveButtonClickListener {
            viewModel.reminder.value = LocalTime.of(timePicker.hour, timePicker.minute)
        }
        timePicker.show(parentFragmentManager, "TIME_PICKER")
    }

    private fun createRoutineAndRedirect() {
        viewModel.createNewRoutine()
        val returnBackToList = CreateRoutineFragmentDirections.returnToList()
        findNavController().navigate(returnBackToList)
    }

}