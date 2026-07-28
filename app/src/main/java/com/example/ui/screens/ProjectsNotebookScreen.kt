package com.example.ui.screens

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.ProjectEntity
import com.example.ui.theme.*
import com.example.ui.viewmodel.AssistantViewModel

@Composable
fun ProjectsNotebookScreen(viewModel: AssistantViewModel) {
    val context = LocalContext.current
    val projects by viewModel.projects.collectAsState()
    var selectedSubTab by remember { mutableIntStateOf(0) } // 0: Projects, 1: GitHub, 2: File Reader
    var showAddDialog by remember { mutableStateOf(false) }
    var selectedProjectForView by remember { mutableStateOf<ProjectEntity?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(iOSWhiteBackground)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Title & Action Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "📚 Proyectos, GitHub & Lector",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextDark
                    )
                    Text(
                        text = "Gestión de código, repositorios GitHub y visor de archivos (PDF/OCR).",
                        fontSize = 12.sp,
                        color = TextMuted
                    )
                }

                if (selectedSubTab == 0) {
                    FloatingActionButton(
                        onClick = { showAddDialog = true },
                        containerColor = iOSPurplePrimary,
                        contentColor = Color.White,
                        modifier = Modifier.size(44.dp),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "Nuevo Proyecto")
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Sub-tabs Segmented Control
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                color = Color(0xFFEFEFF4)
            ) {
                Row(modifier = Modifier.padding(4.dp)) {
                    val tabs = listOf("📚 Notebooks", "🐙 GitHub", "📄 Archivos")
                    tabs.forEachIndexed { index, title ->
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(10.dp))
                                .background(if (selectedSubTab == index) Color.White else Color.Transparent)
                                .clickable { selectedSubTab = index }
                                .padding(vertical = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = title,
                                fontSize = 12.sp,
                                fontWeight = if (selectedSubTab == index) FontWeight.Bold else FontWeight.Medium,
                                color = if (selectedSubTab == index) iOSPurpleDark else TextMuted
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            when (selectedSubTab) {
                0 -> {
                    // Projects List
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(bottom = 100.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        if (projects.isEmpty()) {
                            item {
                                Surface(
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(20.dp),
                                    color = Color.White,
                                    shadowElevation = 1.dp
                                ) {
                                    Column(
                                        modifier = Modifier.padding(24.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.FolderOpen,
                                            contentDescription = "Sin Proyectos",
                                            tint = iOSPurplePrimary,
                                            modifier = Modifier.size(48.dp)
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Text(
                                            text = "No tienes proyectos aún",
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = TextDark
                                        )
                                        Text(
                                            text = "Toca el botón '+' para crear tu primer espacio de código NotebookLM.",
                                            fontSize = 12.sp,
                                            color = TextMuted
                                        )
                                    }
                                }
                            }
                        }

                        items(projects, key = { it.id }) { project ->
                            ProjectCardItem(
                                project = project,
                                onClick = { selectedProjectForView = project },
                                onDelete = { viewModel.deleteProject(project.id) }
                            )
                        }
                    }
                }

                1 -> {
                    // GitHub Integration View
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(bottom = 100.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
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
                                        Icon(imageVector = Icons.Default.Code, contentDescription = null, tint = iOSPurplePrimary)
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text("🐙 Conexión a GitHub", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = iOSPurpleDark)
                                    }
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = "Usuario conectado: @${viewModel.githubUsername}. Puedes ver repositorios, descargar ZIPs, leer código y crear commits.",
                                        fontSize = 12.sp,
                                        color = TextMuted
                                    )

                                    Spacer(modifier = Modifier.height(12.dp))

                                    val repos = listOf(
                                        "aura-ai-android-assistant" to "Asistente Personal con Glassmorphism para Android",
                                        "mobile-llm-gemma-integration" to "Módulo de inferencia para Gemma 2B en memoria interna",
                                        "vector-database-chromadb-lite" to "Base de datos vectorial local para memoria a largo plazo"
                                    )

                                    repos.forEach { (repoName, repoDesc) ->
                                        Surface(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(vertical = 4.dp),
                                            shape = RoundedCornerShape(12.dp),
                                            color = iOSPurpleLight
                                        ) {
                                            Row(
                                                modifier = Modifier.padding(12.dp),
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Column(modifier = Modifier.weight(1f)) {
                                                    Text(text = repoName, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = iOSPurpleDark)
                                                    Text(text = repoDesc, fontSize = 11.sp, color = TextMuted)
                                                }
                                                IconButton(onClick = {
                                                    Toast.makeText(context, "Descargando ZIP de $repoName...", Toast.LENGTH_SHORT).show()
                                                }) {
                                                    Icon(imageVector = Icons.Default.Download, contentDescription = "Descargar ZIP", tint = iOSPurplePrimary)
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                2 -> {
                    // File Reader View (PDF, DOCX, TXT, ZIP, OCR)
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(bottom = 100.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
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
                                        Icon(imageVector = Icons.Default.FindInPage, contentDescription = null, tint = iOSPurplePrimary)
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text("📄 Lector de Archivos & OCR", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = iOSPurpleDark)
                                    }
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = "Soporta lectura directa de PDF, DOCX, TXT, archivos ZIP y escaneo OCR de texto en imágenes.",
                                        fontSize = 12.sp,
                                        color = TextMuted
                                    )

                                    Spacer(modifier = Modifier.height(14.dp))

                                    Button(
                                        onClick = {
                                            Toast.makeText(context, "Selecciona un archivo PDF, DOCX, TXT o Imagen", Toast.LENGTH_SHORT).show()
                                        },
                                        modifier = Modifier.fillMaxWidth(),
                                        shape = RoundedCornerShape(12.dp),
                                        colors = ButtonDefaults.buttonColors(containerColor = iOSPurplePrimary)
                                    ) {
                                        Icon(imageVector = Icons.Default.UploadFile, contentDescription = null)
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text("Cargar Archivo / Imagen OCR")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Add Project Dialog
        if (showAddDialog) {
            AddProjectDialog(
                onDismiss = { showAddDialog = false },
                onAdd = { title, desc, cat, code ->
                    viewModel.addProject(title, desc, cat, code)
                    showAddDialog = false
                }
            )
        }

        // View & Edit Code Dialog
        if (selectedProjectForView != null) {
            ProjectViewDialog(
                project = selectedProjectForView!!,
                onDismiss = { selectedProjectForView = null },
                onAskAi = { prompt ->
                    viewModel.selectedTab = 0
                    viewModel.sendMessage("Sobre el proyecto '${selectedProjectForView?.title}': $prompt\nCódigo:\n${selectedProjectForView?.codeSnippet}")
                    selectedProjectForView = null
                }
            )
        }
    }
}

@Composable
fun ProjectCardItem(
    project: ProjectEntity,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(18.dp),
        color = Color.White,
        shadowElevation = 2.dp,
        border = BorderStroke(1.dp, GlassBorder)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = iOSPurpleLight
                ) {
                    Text(
                        text = project.category,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = iOSPurpleDark,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }

                IconButton(onClick = onDelete, modifier = Modifier.size(24.dp)) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Eliminar",
                        tint = TextMuted,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = project.title,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark
            )

            Text(
                text = project.description,
                fontSize = 13.sp,
                color = TextMuted,
                modifier = Modifier.padding(top = 2.dp)
            )

            if (project.codeSnippet.isNotBlank()) {
                Spacer(modifier = Modifier.height(10.dp))
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    color = Color(0xFF1E1E2E)
                ) {
                    Text(
                        text = project.codeSnippet.take(120) + "...",
                        fontSize = 11.sp,
                        fontFamily = FontFamily.Monospace,
                        color = Color(0xFFA6ADC8),
                        modifier = Modifier.padding(10.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Psychology,
                    contentDescription = "Resumen",
                    tint = iOSPurplePrimary,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = project.summaryNote,
                    fontSize = 11.sp,
                    color = iOSPurpleDark,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
fun AddProjectDialog(
    onDismiss: () -> Unit,
    onAdd: (String, String, String, String) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Android / Kotlin") }
    var code by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Nuevo Proyecto NotebookLM", fontWeight = FontWeight.Bold) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Título del Proyecto") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = desc,
                    onValueChange = { desc = it },
                    label = { Text("Descripción") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = category,
                    onValueChange = { category = it },
                    label = { Text("Categoría / Lenguaje") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = code,
                    onValueChange = { code = it },
                    label = { Text("Código Fuente o Texto Inicial") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (title.isNotBlank()) {
                        onAdd(title, desc, category, code)
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = iOSPurplePrimary)
            ) {
                Text("Guardar Proyecto")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}

@Composable
fun ProjectViewDialog(
    project: ProjectEntity,
    onDismiss: () -> Unit,
    onAskAi: (String) -> Unit
) {
    var askPrompt by remember { mutableStateOf("Explica la lógica principal de este código y busca mejoras") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(project.title, fontWeight = FontWeight.Bold, color = iOSPurpleDark) },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(text = project.description, fontSize = 13.sp, color = TextMuted)

                Spacer(modifier = Modifier.height(10.dp))

                Text(text = "Código Fuente:", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp),
                    shape = RoundedCornerShape(10.dp),
                    color = Color(0xFF1E1E2E)
                ) {
                    Text(
                        text = project.codeSnippet.ifBlank { "// Sin código cargado todavía" },
                        fontSize = 11.sp,
                        fontFamily = FontFamily.Monospace,
                        color = Color(0xFFCDD6F4),
                        modifier = Modifier.padding(10.dp)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = askPrompt,
                    onValueChange = { askPrompt = it },
                    label = { Text("Preguntar a Aura AI sobre este código") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { onAskAi(askPrompt) },
                colors = ButtonDefaults.buttonColors(containerColor = iOSPurplePrimary)
            ) {
                Text("Analizar con AI")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cerrar")
            }
        }
    )
}
