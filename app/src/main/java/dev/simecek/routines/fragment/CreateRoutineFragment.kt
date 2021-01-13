package dev.simecek.routines.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import dev.simecek.routines.R
import dev.simecek.routines.databinding.FragmentCreateRoutineBinding
import dev.simecek.routines.viewModel.CreateRoutineViewModel

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
        val timePicker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .setTitleText(R.string.set_reminder)
                .setHour(12)
                .setMinute(0)
                .build()
        timePicker.addOnPositiveButtonClickListener {
            val minutes: String = if(timePicker.minute < 10) "0${timePicker.minute}" else timePicker.minute.toString()
            val hours: String = timePicker.hour.toString()
            viewModel.time.value = "$hours:$minutes"
        }
        timePicker.show(parentFragmentManager, "TIME_PICKER")
    }

    private fun createRoutine() {
        Snackbar.make(binding.timePickerInput, R.string.feature_not_supported, Snackbar.LENGTH_SHORT).show()
    }

}