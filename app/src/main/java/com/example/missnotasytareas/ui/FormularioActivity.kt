package com.example.missnotasytareas.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.missnotasytareas.Dialog.borrarDialogo
import com.example.missnotasytareas.MainActivity
import com.example.missnotasytareas.R
import com.example.missnotasytareas.controlador.constantes
import com.example.missnotasytareas.databinding.ActivityFormularioBinding
import com.example.missnotasytareas.databinding.ActivityMainBinding
import com.example.missnotasytareas.viewModel.FormularioViewModel

class FormularioActivity : AppCompatActivity(),borrarDialogo.BorrarListener{
    lateinit var binding: ActivityFormularioBinding
    lateinit var viewModel: FormularioViewModel
    lateinit var dialogo: borrarDialogo
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormularioBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dialogo = borrarDialogo(this, )
        viewModel = ViewModelProvider(this).get()
        viewModel.operacion = intent.getStringExtra(constantes.OPERACION_KEY)!!
        binding.modelo = viewModel
        binding.lifecycleOwner = this

        viewModel.operacionExitosa.observe(this, Observer {
            if(it){
                mostrarMensaje("Operacion exitosa")
                irAlInicio()
            }else{
                mostrarMensaje("Ocurrio un error")
            }
        })

        if (viewModel.operacion.equals(constantes.OPERACION_EDITAR)){
            viewModel.id.value = intent.getLongExtra(constantes.ID_NOTAS_KEY,0)
            viewModel.cargarDatos()
            binding.linearEditar.visibility= View.VISIBLE
            binding.btnGuardar.visibility = View.GONE
        }else{
            binding.linearEditar.visibility = View.GONE
            binding.btnGuardar.visibility = View.VISIBLE
        }
        binding.btnBorrar.setOnClickListener{
            mostrarDialogo()
        }
    }

    private fun mostrarDialogo() {
        dialogo.show(supportFragmentManager,"Dialogo Borrar")
    }


    private fun mostrarMensaje(s: String){
        Toast.makeText(applicationContext, s, Toast.LENGTH_SHORT).show()
    }

    private fun irAlInicio(){
        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    override fun borrarNota() {
        viewModel.eliminarNota()
    }
}