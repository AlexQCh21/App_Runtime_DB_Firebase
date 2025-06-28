package com.example.app_s10.views

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RatingBar
import androidx.fragment.app.DialogFragment
import com.example.app_s10.R
import com.example.app_s10.model.Game
import com.example.app_s10.utils.RealTimeManager
import com.google.android.material.textfield.TextInputEditText
import java.io.Serializable
import java.util.Calendar

class FormularioDialogFragment : DialogFragment() {

    lateinit var realTimeManager: RealTimeManager
    lateinit var etGameTitle: TextInputEditText
    lateinit var etGameGenre: TextInputEditText
    lateinit var auto_platform: AutoCompleteTextView
    lateinit var input_description: TextInputEditText
    lateinit var input_release_date: TextInputEditText
    lateinit var cbxCompleted: CheckBox
    lateinit var ratingBar: RatingBar

    lateinit var dateEditText: EditText

    private var gameToEdit: Game? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gameToEdit = arguments?.getParcelable("game")

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.formulario, null)

        realTimeManager = RealTimeManager()
        val btnGuardar = view.findViewById<Button>(R.id.btnSaveGame)
        initComponenets(view)

        // Set datos si estamos editando
        gameToEdit?.let { game ->
            etGameTitle.setText(game.title)
            etGameGenre.setText(game.genre)
            ratingBar.rating = game.rating
            auto_platform.setText(game.platform)
            input_description.setText(game.description)
            cbxCompleted.isChecked = game.completed
            input_release_date.setText("01/01/${game.releaseYear}") // Puedes formatear la fecha real si la tienes
        }

        btnGuardar.setOnClickListener {
            saveGame()
            dismiss()
        }

        val plataformas = resources.getStringArray(R.array.plataformas_array)
        val autoCompleteTextView = view.findViewById<AutoCompleteTextView>(R.id.auto_platform)
        val adapter = ArrayAdapter(view.context, android.R.layout.simple_dropdown_item_1line, plataformas)
        autoCompleteTextView.setAdapter(adapter)

        dateEditText = view.findViewById<EditText>(R.id.input_release_date)
        dateEditText.setOnClickListener {
            val calendar = Calendar.getInstance()
            val datePicker = DatePickerDialog(
                view.context,
                { _, year, month, day ->
                    val fechaFormateada = String.format("%02d/%02d/%04d", day, month + 1, year)
                    dateEditText.setText(fechaFormateada)
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePicker.show()
        }

        builder.setView(view)
        return builder.create()
    }

    fun initComponenets(view: View) {
        etGameTitle = view.findViewById(R.id.etGameTitle)
        etGameGenre = view.findViewById(R.id.etGameGenre)
        ratingBar = view.findViewById(R.id.ratingBar)
        auto_platform = view.findViewById(R.id.auto_platform)
        input_description = view.findViewById(R.id.input_description)
        input_release_date = view.findViewById(R.id.input_release_date)
        cbxCompleted = view.findViewById(R.id.cbxCompleted)
    }

    fun saveGame() {
        val title = etGameTitle.text.toString()
        val genre = etGameGenre.text.toString()
        val rating = ratingBar.rating
        val platform = auto_platform.text.toString()
        val description = input_description.text.toString()
        val completed = cbxCompleted.isChecked
        val dateText = dateEditText.text.toString()
        val parts = dateText.split("/")
        val releaseYear = if (parts.size == 3) parts[2].toInt() else 0

        val existingId = gameToEdit?.id
        val userId = gameToEdit?.userId

        val game = Game(
            id = existingId,
            title = title,
            genre = genre,
            platform = platform,
            rating = rating,
            description = description,
            releaseYear = releaseYear,
            completed = completed,
            userId = userId
        )

        if (existingId != null) {
            realTimeManager.updateGame(existingId, game)
        } else {
            realTimeManager.addGame(game)
        }

        AlertDialog.Builder(requireContext())
            .setTitle("Juego guardado")
            .setMessage(if (existingId != null) "El juego fue actualizado." else "El juego fue agregado exitosamente.")
            .setPositiveButton("Aceptar", null)
            .show()
    }

    companion object {
        fun newInstance(game: Game): FormularioDialogFragment {
            val fragment = FormularioDialogFragment()
            val args = Bundle()
            args.putParcelable("game", game)
            fragment.arguments = args
            return fragment
        }
    }

}
