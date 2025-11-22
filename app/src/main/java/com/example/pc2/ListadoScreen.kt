
package com.example.pc2

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObjects

data class Equipo(val nombre: String = "", val anio: String = "", val titulos: String = "", val url: String = "")

@Composable
fun ListadoScreen() {
    val equipos = remember { mutableStateOf<List<Equipo>>(emptyList()) }

    LaunchedEffect(Unit) {
        val db = FirebaseFirestore.getInstance()
        db.collection("equipos")
            .get()
            .addOnSuccessListener { result ->
                equipos.value = result.toObjects(Equipo::class.java)
            }
            .addOnFailureListener { exception ->
                Log.w("ListadoScreen", "Error getting documents.", exception)
            }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Equipos Registrados", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(equipos.value) { equipo ->
                EquipoCard(equipo = equipo)
            }
        }
    }
}

@Composable
fun EquipoCard(equipo: Equipo) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(equipo.url),
                contentDescription = "Escudo del equipo ${equipo.nombre}",
                modifier = Modifier.size(80.dp),
                contentScale = ContentScale.Fit
            )
            Column(modifier = Modifier.padding(start = 16.dp)) {
                Text(equipo.nombre, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                Text("Fundación: ${equipo.anio}", style = MaterialTheme.typography.bodyMedium)
                Text("Títulos: ${equipo.titulos}", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ListadoScreenPreview() {
    ListadoScreen()
}

@Preview(showBackground = true)
@Composable
fun EquipoCardPreview() {
    EquipoCard(Equipo("Universitario", "1924", "27", "https://upload.wikimedia.org/wikipedia/commons/thumb/5/52/Escudo_del_Club_Universitario_de_Deportes.svg/1200px-Escudo_del_Club_Universitario_de_Deportes.svg.png"))
}
