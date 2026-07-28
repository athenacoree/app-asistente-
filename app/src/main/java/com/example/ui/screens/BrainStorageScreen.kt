package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AiModelDefaults
import com.example.ui.theme.*
import com.example.ui.viewmodel.AssistantViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BrainStorageScreen(viewModel: AssistantViewModel) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(iOSWhiteBackground)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(top = 16.dp, bottom = 100.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header
            item {
                Text(
                    text = "🧠 Cerebro LLM & Base de Datos",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDark
                )
                Text(
                    text = "Configura la ubicación de almacenamiento del cerebro, subcarpetas locales y el modelo LLM gratuito.",
                    fontSize = 13.sp,
                    color = TextMuted,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            // Storage Location Selector (Almacenamiento vs Tarjeta SD)
            item {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    color = Color.White,
                    shadowElevation = 2.dp,
                    border = BorderStroke(1.dp, GlassBorder)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.SdStorage,
                                contentDescription = "Ubicación",
                                tint = iOSPurplePrimary
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "¿Dónde quieres que se ejecute el cerebro?",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = iOSPurpleDark
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            StorageOptionCard(
                                title = "Almacenamiento",
                                subtitle = "Interno del teléfono",
                                icon = Icons.Default.Smartphone,
                                isSelected = viewModel.storageLocation == "Almacenamiento Interno",
                                onClick = { viewModel.initializeBrainDir(isSdCard = false) },
                                modifier = Modifier.weight(1f)
                            )

                            StorageOptionCard(
                                title = "Tarjeta SD",
                                subtitle = "Memoria externa SD",
                                icon = Icons.Default.SdCard,
                                isSelected = viewModel.storageLocation == "Tarjeta SD",
                                onClick = { viewModel.initializeBrainDir(isSdCard = true) },
                                modifier = Modifier.weight(1f)
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "Ruta activa: ${viewModel.brainFolderPath}",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            color = TextMuted
                        )
                    }
                }
            }

            // Subfolders Structure Visualizer
            item {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    color = Color.White,
                    shadowElevation = 2.dp,
                    border = BorderStroke(1.dp, GlassBorder)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.FolderZip,
                                contentDescription = "Carpetas",
                                tint = iOSPurplePrimary
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Estructura de Carpetas Creadas",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = iOSPurpleDark
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        val subfolders = listOf(
                            "📁 /AuraAI_Brain/" to "Carpeta raíz del cerebro local",
                            "  ├── 📂 /models/" to "Cerebro 50MB local (local_cerebro_50mb.bin)",
                            "  ├── 📂 /database/" to "Base de datos Room SQLite privada",
                            "  ├── 📂 /voices/" to "Modelos de síntesis de voz descargados",
                            "  ├── 📂 /projects/" to "Archivos y código fuente de NotebookLM",
                            "  └── 📂 /logs/" to "Archivos de aprendizaje y preferencia de usuario"
                        )

                        subfolders.forEach { (folder, desc) ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = folder,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = iOSPurplePrimary
                                )
                                Text(
                                    text = desc,
                                    fontSize = 11.sp,
                                    color = TextMuted
                                )
                            }
                        }
                    }
                }
            }

            // LLM Model Selector
            item {
                Text(
                    text = "🤖 Modelos LLM & OpenRouter Gratuitos",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDark
                )
            }

            items(AiModelDefaults.FREE_OPENROUTER_MODELS) { model ->
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { viewModel.selectedModel = model },
                    shape = RoundedCornerShape(16.dp),
                    color = if (viewModel.selectedModel.id == model.id) iOSPurpleLight else Color.White,
                    shadowElevation = 1.dp,
                    border = BorderStroke(
                        width = if (viewModel.selectedModel.id == model.id) 2.dp else 1.dp,
                        color = if (viewModel.selectedModel.id == model.id) iOSPurplePrimary else GlassBorder
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = viewModel.selectedModel.id == model.id,
                            onClick = { viewModel.selectedModel = model },
                            colors = RadioButtonDefaults.colors(selectedColor = iOSPurplePrimary)
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = model.name,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = iOSPurpleDark
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Surface(
                                    shape = RoundedCornerShape(4.dp),
                                    color = iOSPurplePrimary.copy(alpha = 0.15f)
                                ) {
                                    Text(
                                        text = model.size,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = iOSPurplePrimary,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }
                            Text(
                                text = model.description,
                                fontSize = 12.sp,
                                color = TextMuted,
                                modifier = Modifier.padding(top = 2.dp)
                            )
                        }
                    }
                }
            }

            // Custom OpenRouter Key / Render Server Input
            item {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    color = Color.White,
                    shadowElevation = 2.dp,
                    border = BorderStroke(1.dp, GlassBorder)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "🌐 Conexión a Servidor o API Personal",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = iOSPurpleDark
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        OutlinedTextField(
                            value = viewModel.openRouterKey,
                            onValueChange = { viewModel.openRouterKey = it },
                            label = { Text("OpenRouter API Key (Opcional)") },
                            placeholder = { Text("sk-or-v1-...") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        OutlinedTextField(
                            value = viewModel.renderServerUrl,
                            onValueChange = { viewModel.renderServerUrl = it },
                            label = { Text("Servidor Render / Endpoint Privado") },
                            placeholder = { Text("https://mi-servidor-ai.onrender.com/api") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun StorageOptionCard(
    title: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.clickable { onClick() },
        shape = RoundedCornerShape(14.dp),
        color = if (isSelected) iOSPurpleLight else Color(0xFFF8F8FC),
        border = BorderStroke(
            width = if (isSelected) 2.dp else 1.dp,
            color = if (isSelected) iOSPurplePrimary else GlassBorder
        )
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = if (isSelected) iOSPurplePrimary else TextMuted,
                modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = title,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = if (isSelected) iOSPurpleDark else TextDark
            )
            Text(
                text = subtitle,
                fontSize = 10.sp,
                color = TextMuted
            )
        }
    }
}
