package com.example.missnotasytareas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.missnotasytareas.adaptadores.NotasAdapter
import com.example.missnotasytareas.controlador.constantes
import com.example.missnotasytareas.databinding.ActivityMainBinding
import com.example.missnotasytareas.ui.FormularioActivity
import com.example.missnotasytareas.viewModel.MainViewModel

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get()
        binding.lifecycleOwner= this
        binding.modelo= viewModel

        viewModel.iniciar()

        binding.reciclador.apply {
            layoutManager = LinearLayoutManager(applicationContext)
        }
        viewModel.notasList.observe(this, Observer {
            binding.reciclador.adapter = NotasAdapter(it)
        })

        binding.btnNueva.setOnClickListener(){
            val intent = Intent(this, FormularioActivity::class.java )
            intent.putExtra(constantes.OPERACION_KEY, constantes.OPERACION_INSERTAR)
            startActivity(intent)
        }
        binding.txtBuscar.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                if(s.toString().isNotEmpty()){
                    viewModel.buscarNota()

                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
            override fun afterTextChanged(s: Editable?) {

            }
        })
    }
}
