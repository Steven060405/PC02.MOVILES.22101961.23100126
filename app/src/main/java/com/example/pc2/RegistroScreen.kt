
package com.example.pc2

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.firestore.FirebaseFirestore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroScreen(onGuardarClicked: () -> Unit) {
    var nombre by remember { mutableStateOf("") }
    var anio by remember { mutableStateOf("") }
    var titulos by remember { mutableStateOf("") }
    var url by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Registro Liga 1")
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre del equipo") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = anio,
            onValueChange = { anio = it },
            label = { Text("Año de fundacion") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = titulos,
            onValueChange = { titulos = it },
            label = { Text("Número de títulos ganados") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = url,
            onValueChange = { url = it },
            label = { Text("URL de la imagen del equipo") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            val equipo = hashMapOf(
                "nombre" to nombre,
                "anio" to anio,
                "titulos" to titulos,
                "url" to url
            )

            FirebaseFirestore.getInstance().collection("equipos")
                .add(equipo)
                .addOnSuccessListener {
                    onGuardarClicked()
                }
                .addOnFailureListener { e ->
                    Log.w("RegistroScreen", "Error al guardar", e)
                }
        }) {
            Text("Guardar")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegistroScreenPreview() {
    RegistroScreen(onGuardarClicked = {})
}
