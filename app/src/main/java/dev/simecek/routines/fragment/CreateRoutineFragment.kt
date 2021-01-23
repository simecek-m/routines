package dev.simecek.routines.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import dagger.hilt.android.AndroidEntryPoint
import dev.simecek.routines.R
import dev.simecek.routines.database.type.Reminder
import dev.simecek.routines.databinding.FragmentCreateRoutineBinding
import dev.simecek.routines.viewModel.CreateRoutineViewModel
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CreateRoutineFragment : Fragment() {

    private val viewModel: CreateRoutineViewModel by viewModels()
    private lateinit var binding: FragmentCreateRoutineBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_create_routine, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.timePickerInput.setOnClickListener {
            showTimePicker()
        }
        binding.createButton.setOnClickListener {
            createRoutine()
        }
        return binding.root
    }

    private fun showTimePicker() {
        val reminder = binding.viewModel?.reminder?.value?: Reminder()
        val timePicker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .setTitleText(R.string.set_reminder)
                .setHour(reminder.hour)
                .setMinute(reminder.minute)
                .build()
        timePicker.addOnPositiveButtonClickListener {
            viewModel.reminder.value = Reminder(timePicker.hour, timePicker.minute)
        }
        timePicker.show(parentFragmentManager, "TIME_PICKER")
    }

    private fun createRoutine() {
        lifecycleScope.launch {
            try {
                viewModel.createNewRoutine()
                val returnBackToList = CreateRoutineFragmentDirections.returnToList()
                findNavController().navigate(returnBackToList)
            } catch (ex: Exception) {
                Snackbar.make(binding.layout, R.string.error_while_creating_routine, Snackbar.LENGTH_SHORT).show()
            }
        }
    }

}