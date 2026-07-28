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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AiModelDefaults
import com.example.ui.theme.*
import com.example.ui.viewmodel.AssistantViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsProfileScreen(viewModel: AssistantViewModel) {
    val context = LocalContext.current

    var showAddFamilyDialog by remember { mutableStateOf(false) }
    var newFamilyName by remember { mutableStateOf("") }
    var newFamilyRelation by remember { mutableStateOf("Mamá") }
    var newFamilyPhone by remember { mutableStateOf("") }
    var newFamilyPhotoDesc by remember { mutableStateOf("") }
    var newFamilyNotes by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(iOSWhiteBackground)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(top = 16.dp, bottom = 120.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Title Header
            item {
                Text(
                    text = "⚙️ Ajustes de 1 AI",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDark
                )
                Text(
                    text = "Panel de control integral: datos personales, núcleo familiar, reconocimiento de rostros, modelos, voz y control de hardware.",
                    fontSize = 13.sp,
                    color = TextMuted,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            // 1. 📱 PERFIL Y PERSONALIDAD
            item {
                SettingsSectionCard(title = "📱 PERFIL Y PERSONALIDAD") {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        OutlinedTextField(
                            value = viewModel.userName,
                            onValueChange = { viewModel.userName = it },
                            label = { Text("Nombre del usuario") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        )

                        OutlinedTextField(
                            value = viewModel.assistantName,
                            onValueChange = { viewModel.assistantName = it },
                            label = { Text("Nombre del asistente") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        )

                        OutlinedTextField(
                            value = viewModel.systemPrompt,
                            onValueChange = { viewModel.systemPrompt = it },
                            label = { Text("Prompt de personalidad") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            minLines = 2
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Modo Sin Filtros / Auténtico",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextDark
                                )
                                Text(
                                    text = "Respuestas libres y directas sin censura molesta.",
                                    fontSize = 11.sp,
                                    color = TextMuted
                                )
                            }
                            Switch(
                                checked = viewModel.uncensoredMode,
                                onCheckedChange = { viewModel.uncensoredMode = it },
                                colors = SwitchDefaults.colors(checkedThumbColor = iOSPurplePrimary)
                            )
                        }
                    }
                }
            }

            // 👨‍👩‍👧‍👦 DATOS PERSONALES Y NÚCLEO FAMILIAR (RECONOCIMIENTO FACIAL)
            item {
                SettingsSectionCard(title = "👨‍👩‍👧‍👦 DATOS PERSONALES Y NÚCLEO FAMILIAR (Visión & Rostros)") {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text(
                            text = "👤 Información Personal Vincular",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = iOSPurpleDark
                        )

                        OutlinedTextField(
                            value = viewModel.userPhone,
                            onValueChange = { viewModel.userPhone = it },
                            label = { Text("Número de Teléfono Personal") },
                            placeholder = { Text("+1 (809) 555-0199") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            leadingIcon = { Icon(imageVector = Icons.Default.Phone, contentDescription = null, tint = iOSPurplePrimary) }
                        )

                        OutlinedTextField(
                            value = viewModel.userEmail,
                            onValueChange = { viewModel.userEmail = it },
                            label = { Text("Correo Electrónico") },
                            placeholder = { Text("correo@ejemplo.com") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = null, tint = iOSPurplePrimary) }
                        )

                        OutlinedTextField(
                            value = viewModel.userBio,
                            onValueChange = { viewModel.userBio = it },
                            label = { Text("Biografía / Datos del Usuario") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        )

                        OutlinedTextField(
                            value = viewModel.userPhotoDescription,
                            onValueChange = { viewModel.userPhotoDescription = it },
                            label = { Text("Patrón / Foto de Perfil del Usuario") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            leadingIcon = { Icon(imageVector = Icons.Default.AccountCircle, contentDescription = null, tint = iOSPurplePrimary) }
                        )

                        Divider(modifier = Modifier.padding(vertical = 4.dp), color = GlassBorder)

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "👥 Catálogo Familiar & Reconocimiento Facial",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = iOSPurpleDark
                                )
                                Text(
                                    text = "Guarda fotos y datos de tu familia. Si preguntas '¿quién es la persona en la foto o video?', 1 AI la identificará.",
                                    fontSize = 11.sp,
                                    color = TextMuted
                                )
                            }
                            IconButton(onClick = { showAddFamilyDialog = true }) {
                                Icon(imageVector = Icons.Default.PersonAdd, contentDescription = "Añadir Familiar", tint = iOSPurplePrimary)
                            }
                        }

                        // Family Members List
                        viewModel.familyMembers.forEach { member ->
                            Surface(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(14.dp),
                                color = iOSPurpleLight,
                                border = BorderStroke(1.dp, GlassBorder)
                            ) {
                                Row(
                                    modifier = Modifier.padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Surface(
                                        shape = RoundedCornerShape(10.dp),
                                        color = iOSPurplePrimary,
                                        modifier = Modifier.size(40.dp)
                                    ) {
                                        Box(contentAlignment = Alignment.Center) {
                                            Icon(imageVector = Icons.Default.Face, contentDescription = null, tint = Color.White)
                                        }
                                    }

                                    Spacer(modifier = Modifier.width(10.dp))

                                    Column(modifier = Modifier.weight(1f)) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Text(
                                                text = member.name,
                                                fontSize = 13.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = TextDark
                                            )
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Surface(
                                                shape = RoundedCornerShape(6.dp),
                                                color = iOSPurpleDark
                                            ) {
                                                Text(
                                                    text = member.relationship,
                                                    fontSize = 9.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = Color.White,
                                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                                )
                                            }
                                        }

                                        if (member.phone.isNotBlank()) {
                                            Text(text = "📞 ${member.phone}", fontSize = 11.sp, color = TextMuted)
                                        }

                                        Text(text = "📸 Muestra: ${member.photoDescription}", fontSize = 10.sp, color = iOSPurpleDark)
                                        if (member.notes.isNotBlank()) {
                                            Text(text = "📝 ${member.notes}", fontSize = 10.sp, color = TextMuted)
                                        }
                                    }

                                    IconButton(onClick = { viewModel.deleteFamilyMember(member.id) }) {
                                        Icon(imageVector = Icons.Default.DeleteOutline, contentDescription = "Eliminar", tint = Color(0xFFE53935))
                                    }
                                }
                            }
                        }

                        Button(
                            onClick = {
                                viewModel.selectedTab = 0
                                viewModel.sendMessage("¿Quién es la persona en la foto de mi mamá?")
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = iOSPurplePrimary)
                        ) {
                            Icon(imageVector = Icons.Default.FaceRetouchingNatural, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("📸 Probar Reconocimiento de Rostro en Foto")
                        }
                    }
                }
            }

            // 📱 CONTROL REMOTO BLUETOOTH IPHONE XR (PANTALLA ROTA & TECLADO HID POR VOZ)
            item {
                SettingsSectionCard(title = "📱 CONTROL REMOTO BLUETOOTH IPHONE XR (PANTALLA ROTA)") {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Emulación Teclado/Mouse Bluetooth HID",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextDark
                                )
                                Text(
                                    text = "Controla tu iPhone XR con pantalla rota por comandos de voz sin tocar la pantalla.",
                                    fontSize = 11.sp,
                                    color = TextMuted
                                )
                            }
                            Switch(
                                checked = viewModel.bluetoothHidEnabled,
                                onCheckedChange = { viewModel.bluetoothHidEnabled = it },
                                colors = SwitchDefaults.colors(checkedThumbColor = iOSPurplePrimary)
                            )
                        }

                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            color = iOSPurpleLight,
                            border = BorderStroke(1.dp, GlassBorder)
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(imageVector = Icons.Default.BluetoothConnected, contentDescription = null, tint = iOSPurplePrimary)
                                Spacer(modifier = Modifier.width(10.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = viewModel.bluetoothIphoneName,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = iOSPurpleDark
                                    )
                                    Text(
                                        text = if (viewModel.bluetoothHidConnected) "✅ Conectado via Bluetooth HID Peripheral" else "❌ Dispositivo no detectado",
                                        fontSize = 11.sp,
                                        color = TextMuted
                                    )
                                }
                            }
                        }

                        Text(
                            text = "Acciones Rápidas por Voz para iPhone XR:",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextDark
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = {
                                    viewModel.executeIphoneHidCommand("Ir a Inicio (Home)", "GUI + H")
                                    Toast.makeText(context, "Cmd+H enviado al iPhone XR", Toast.LENGTH_SHORT).show()
                                },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = iOSPurplePrimary)
                            ) {
                                Text("🏠 Inicio", fontSize = 11.sp)
                            }

                            Button(
                                onClick = {
                                    viewModel.executeIphoneHidCommand("Subir Volumen", "MEDIA_VOL_UP")
                                    Toast.makeText(context, "Volumen + enviado", Toast.LENGTH_SHORT).show()
                                },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = iOSPurplePrimary)
                            ) {
                                Text("🔊 Vol +", fontSize = 11.sp)
                            }

                            Button(
                                onClick = {
                                    viewModel.executeIphoneHidCommand("Pausa / Play Multimedia", "MEDIA_PLAY_PAUSE")
                                    Toast.makeText(context, "Play/Pausa enviado", Toast.LENGTH_SHORT).show()
                                },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = iOSPurplePrimary)
                            ) {
                                Text("⏯️ Play/Pausa", fontSize = 11.sp)
                            }
                        }

                        Text(
                            text = "📡 Monitor de Salida Teclado HID Bluetooth:",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = iOSPurpleDark
                        )

                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(130.dp),
                            shape = RoundedCornerShape(12.dp),
                            color = Color(0xFF1E1E2E)
                        ) {
                            LazyColumn(modifier = Modifier.padding(10.dp)) {
                                items(viewModel.bluetoothHidLogs) { log ->
                                    Text(
                                        text = log,
                                        fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                                        fontSize = 10.sp,
                                        color = Color(0xFFA6ADC8)
                                    )
                                }
                            }
                        }

                        Button(
                            onClick = {
                                viewModel.selectedTab = 0
                                viewModel.sendMessage("En el iPhone XR ve a inicio")
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = iOSPurpleDark)
                        ) {
                            Icon(imageVector = Icons.Default.Mic, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("🗣️ Probar Comando de Voz \"En el iPhone ve a inicio\"")
                        }
                    }
                }
            }

            // 2. 🧠 CEREBRO Y MODELOS
            item {
                SettingsSectionCard(title = "🧠 CEREBRO Y MODELOS") {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text(
                            text = "Modelo actual: ${viewModel.selectedModel.name}",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = iOSPurpleDark
                        )

                        Text(
                            text = "Almacenamiento: ${viewModel.storageLocation} (${viewModel.brainFolderPath.take(35)}...)",
                            fontSize = 11.sp,
                            color = TextMuted
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = { viewModel.selectedTab = 1 },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = iOSPurplePrimary)
                            ) {
                                Text("Gestor Cerebro", fontSize = 12.sp)
                            }

                            OutlinedButton(
                                onClick = {
                                    Toast.makeText(context, "Modelos sin pago OpenRouter listos", Toast.LENGTH_SHORT).show()
                                },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Text("Modelos Gratis", fontSize = 12.sp)
                            }
                        }

                        OutlinedTextField(
                            value = viewModel.openRouterKey,
                            onValueChange = { viewModel.openRouterKey = it },
                            label = { Text("OpenRouter API Key [Opcional]") },
                            placeholder = { Text("sk-or-v1-...") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        )
                    }
                }
            }

            // 3. 🎤 VOZ Y COMANDOS & DESCARGA DE VOCES
            item {
                SettingsSectionCard(title = "🎤 VOZ Y COMANDOS & DESCARGA DE VOCES") {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Activar \"Oye Aura\" (Wake Word)",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextDark
                                )
                                Text(
                                    text = "Escucha continua en segundo plano sin pulsar botones.",
                                    fontSize = 11.sp,
                                    color = TextMuted
                                )
                            }
                            Switch(
                                checked = viewModel.hotwordEnabled,
                                onCheckedChange = { viewModel.hotwordEnabled = it },
                                colors = SwitchDefaults.colors(checkedThumbColor = iOSPurplePrimary)
                            )
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "🗣️ Modo iOS \"Control por Voz\"",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextDark
                                )
                                Text(
                                    text = "Navega y controla todo el teléfono diciendo el nombre o número de lo que ves.",
                                    fontSize = 11.sp,
                                    color = TextMuted
                                )
                            }
                            Switch(
                                checked = viewModel.iosVoiceControlEnabled,
                                onCheckedChange = { viewModel.iosVoiceControlEnabled = it },
                                colors = SwitchDefaults.colors(checkedThumbColor = iOSPurplePrimary)
                            )
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "Modelos de Voz Realista Disponibles:",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = iOSPurpleDark
                        )

                        AiModelDefaults.REALISTIC_VOICES.forEach { voice ->
                            val isDownloaded = viewModel.downloadedVoiceIds.contains(voice.id)
                            val isDownloading = viewModel.voiceDownloadingId == voice.id
                            val isSelected = viewModel.selectedVoice.id == voice.id

                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(12.dp))
                                    .clickable {
                                        if (isDownloaded) {
                                            viewModel.selectedVoice = voice
                                        }
                                    },
                                color = if (isSelected) iOSPurpleLight else Color(0xFFF8F9FA),
                                border = BorderStroke(1.dp, if (isSelected) iOSPurplePrimary else GlassBorder)
                            ) {
                                Row(
                                    modifier = Modifier.padding(10.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    RadioButton(
                                        selected = isSelected,
                                        onClick = {
                                            if (isDownloaded) viewModel.selectedVoice = voice
                                        },
                                        enabled = isDownloaded
                                    )

                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = "${voice.name} (${voice.accent})",
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = TextDark
                                        )
                                        Text(
                                            text = voice.description,
                                            fontSize = 10.sp,
                                            color = TextMuted
                                        )
                                    }

                                    if (isDownloading) {
                                        CircularProgressIndicator(
                                            modifier = Modifier.size(20.dp),
                                            strokeWidth = 2.dp,
                                            color = iOSPurplePrimary
                                        )
                                    } else if (!isDownloaded) {
                                        IconButton(onClick = {
                                            viewModel.downloadVoiceProfile(voice.id) {
                                                Toast.makeText(context, "Modelo de voz ${voice.name} descargado e instalado", Toast.LENGTH_SHORT).show()
                                            }
                                        }) {
                                            Icon(
                                                imageVector = Icons.Default.Download,
                                                contentDescription = "Descargar Voz",
                                                tint = iOSPurplePrimary
                                            )
                                        }
                                    } else {
                                        IconButton(onClick = {
                                            viewModel.speakText("Probando la voz de ${voice.name}.")
                                        }) {
                                            Icon(
                                                imageVector = Icons.Default.VolumeUp,
                                                contentDescription = "Probar Voz",
                                                tint = iOSPurplePrimary
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // 4. 🏠 CONTROL DEL SISTEMA
            item {
                SettingsSectionCard(title = "🏠 CONTROL DEL SISTEMA (Accessiblity & Service)") {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Servicio Foreground Permanente",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextDark
                                )
                                Text(
                                    text = "Notificación persistente: Aura AI está activa",
                                    fontSize = 11.sp,
                                    color = TextMuted
                                )
                            }
                            Switch(
                                checked = viewModel.foregroundServiceActive,
                                onCheckedChange = {
                                    viewModel.foregroundServiceActive = it
                                    Toast.makeText(
                                        context,
                                        if (it) "Servicio en segundo plano ACTIVADO" else "Servicio pausado",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            )
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Burbuja Flotante (Overlay Messenger)",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextDark
                                )
                                Text(
                                    text = "Acceso rápido en pantalla desde cualquier app",
                                    fontSize = 11.sp,
                                    color = TextMuted
                                )
                            }
                            Switch(
                                checked = viewModel.floatingBubbleEnabled,
                                onCheckedChange = { viewModel.floatingBubbleEnabled = it }
                            )
                        }

                        // System Toggles Grid
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            ControlChip(
                                label = "WiFi",
                                isON = viewModel.wifiEnabled,
                                icon = Icons.Default.Wifi,
                                onClick = { viewModel.wifiEnabled = !viewModel.wifiEnabled },
                                modifier = Modifier.weight(1f)
                            )
                            ControlChip(
                                label = "Datos",
                                isON = viewModel.mobileDataEnabled,
                                icon = Icons.Default.SignalCellular4Bar,
                                onClick = { viewModel.mobileDataEnabled = !viewModel.mobileDataEnabled },
                                modifier = Modifier.weight(1f)
                            )
                            ControlChip(
                                label = "Bluetooth",
                                isON = viewModel.bluetoothEnabled,
                                icon = Icons.Default.Bluetooth,
                                onClick = { viewModel.bluetoothEnabled = !viewModel.bluetoothEnabled },
                                modifier = Modifier.weight(1f)
                            )
                            ControlChip(
                                label = "Linterna",
                                isON = viewModel.flashlightEnabled,
                                icon = Icons.Default.FlashOn,
                                onClick = { viewModel.flashlightEnabled = !viewModel.flashlightEnabled },
                                modifier = Modifier.weight(1f)
                            )
                        }

                        // Sliders for Volume & Brightness
                        Column {
                            Text(text = "🔊 Volumen del Sistema: ${(viewModel.volumeLevel * 100).toInt()}%", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                            Slider(
                                value = viewModel.volumeLevel,
                                onValueChange = { viewModel.volumeLevel = it },
                                colors = SliderDefaults.colors(thumbColor = iOSPurplePrimary, activeTrackColor = iOSPurplePrimary)
                            )
                        }

                        Column {
                            Text(text = "☀️ Brillo de Pantalla: ${(viewModel.brightnessLevel * 100).toInt()}%", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                            Slider(
                                value = viewModel.brightnessLevel,
                                onValueChange = { viewModel.brightnessLevel = it },
                                colors = SliderDefaults.colors(thumbColor = iOSPurplePrimary, activeTrackColor = iOSPurplePrimary)
                            )
                        }
                    }
                }
            }

            // 5. 💬 NOTIFICACIONES Y APPS
            item {
                SettingsSectionCard(title = "💬 NOTIFICACIONES Y APPS") {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "Leer notificaciones entrantes", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                            Switch(
                                checked = viewModel.readNotificationsEnabled,
                                onCheckedChange = { viewModel.readNotificationsEnabled = it }
                            )
                        }

                        val apps = listOf(
                            "WhatsApp" to viewModel.whatsappConnected,
                            "Telegram" to viewModel.telegramConnected,
                            "SMS" to viewModel.smsConnected,
                            "Correo Electrónico" to true
                        )

                        apps.forEach { (appName, isConnected) ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = "Integración con $appName", fontSize = 12.sp)
                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = if (isConnected) iOSPurpleLight else Color(0xFFEEEEEE)
                                ) {
                                    Text(
                                        text = if (isConnected) "Conectado" else "Desconectado",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isConnected) iOSPurpleDark else TextMuted,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // 6. 📂 PROYECTOS Y ARCHIVOS
            item {
                SettingsSectionCard(title = "📂 PROYECTOS Y ARCHIVOS (GitHub & Reader)") {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "GitHub OAuth (@${viewModel.githubUsername})", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                            Surface(shape = RoundedCornerShape(6.dp), color = iOSPurpleLight) {
                                Text("Vinculado", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = iOSPurpleDark, modifier = Modifier.padding(6.dp))
                            }
                        }

                        Button(
                            onClick = { viewModel.selectedTab = 2 },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = iOSPurplePrimary)
                        ) {
                            Icon(imageVector = Icons.Default.Folder, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Abrir Editor de Código & Repositorios")
                        }
                    }
                }
            }

            // 7. 🎨 DISEÑO Y PERSONALIZACIÓN
            item {
                SettingsSectionCard(title = "🎨 DISEÑO Y PERSONALIZACIÓN") {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text(text = "Tema Visual: ${viewModel.selectedTheme}", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            val themes = listOf("Morado Glass", "Azul Neón", "Oscuro Cyber")
                            themes.forEach { t ->
                                OutlinedButton(
                                    onClick = { viewModel.selectedTheme = t },
                                    modifier = Modifier.weight(1f),
                                    shape = RoundedCornerShape(8.dp),
                                    border = BorderStroke(1.dp, if (viewModel.selectedTheme == t) iOSPurplePrimary else GlassBorder)
                                ) {
                                    Text(t.take(6), fontSize = 10.sp)
                                }
                            }
                        }
                    }
                }
            }

            // 8. 🔒 SEGURIDAD Y PRIVACIDAD
            item {
                SettingsSectionCard(title = "🔒 SEGURIDAD Y PRIVACIDAD") {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(text = "Cifrar Conversaciones (AES-256)", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                                Text(text = "Protección biométrica y base de datos cifrada.", fontSize = 11.sp, color = TextMuted)
                            }
                            Switch(
                                checked = viewModel.isEncrypted,
                                onCheckedChange = { viewModel.isEncrypted = it }
                            )
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            OutlinedButton(
                                onClick = {
                                    Toast.makeText(context, "Datos exportados a /AuraAI_Brain/database/backup.json", Toast.LENGTH_LONG).show()
                                },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text("Exportar JSON", fontSize = 11.sp)
                            }

                            Button(
                                onClick = {
                                    viewModel.clearHistory()
                                    Toast.makeText(context, "Conversaciones borradas de forma segura", Toast.LENGTH_SHORT).show()
                                },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE53935))
                            ) {
                                Text("Borrar Datos", fontSize = 11.sp, color = Color.White)
                            }
                        }
                    }
                }
            }

            // 9. Catalog of 50 Capabilities
            item {
                Text(
                    text = "🛠️ Catálogo de 50 Capacidades del Sistema",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDark
                )
            }

            items(AiModelDefaults.INITIAL_50_CAPABILITIES) { (categoryName, actionsList) ->
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    color = Color.White,
                    shadowElevation = 1.dp,
                    border = BorderStroke(1.dp, GlassBorder)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = categoryName,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = iOSPurplePrimary
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        actionsList.forEach { action ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = "Capability",
                                    tint = iOSPurpleSecondary,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = action,
                                    fontSize = 12.sp,
                                    color = TextDark
                                )
                            }
                        }
                    }
                }
            }
        }

        if (showAddFamilyDialog) {
            AlertDialog(
                onDismissRequest = { showAddFamilyDialog = false },
                title = { Text("➕ Añadir Familiar / Contacto", fontWeight = FontWeight.Bold) },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        OutlinedTextField(
                            value = newFamilyName,
                            onValueChange = { newFamilyName = it },
                            label = { Text("Nombre Completo") },
                            placeholder = { Text("Ej: María Báez") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp)
                        )

                        OutlinedTextField(
                            value = newFamilyRelation,
                            onValueChange = { newFamilyRelation = it },
                            label = { Text("Parentesco (Mamá, Hermano, Esposa, Hijo...)") },
                            placeholder = { Text("Ej: Mamá") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp)
                        )

                        OutlinedTextField(
                            value = newFamilyPhone,
                            onValueChange = { newFamilyPhone = it },
                            label = { Text("Teléfono de Contacto") },
                            placeholder = { Text("Ej: +1 809 555 1122") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp)
                        )

                        OutlinedTextField(
                            value = newFamilyPhotoDesc,
                            onValueChange = { newFamilyPhotoDesc = it },
                            label = { Text("Patrón de Foto / Descripción Facial") },
                            placeholder = { Text("Ej: Cabello castaño, ojos marrones, sonrisa") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp)
                        )

                        OutlinedTextField(
                            value = newFamilyNotes,
                            onValueChange = { newFamilyNotes = it },
                            label = { Text("Notas Personales") },
                            placeholder = { Text("Ej: Madre del usuario. Le gusta el café") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp)
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            if (newFamilyName.isNotBlank()) {
                                viewModel.addFamilyMember(
                                    com.example.data.model.FamilyMember(
                                        name = newFamilyName,
                                        relationship = newFamilyRelation.ifBlank { "Familiar" },
                                        phone = newFamilyPhone,
                                        photoDescription = newFamilyPhotoDesc.ifBlank { "Rostro registrado en catálogo" },
                                        notes = newFamilyNotes
                                    )
                                )
                                newFamilyName = ""
                                newFamilyRelation = "Mamá"
                                newFamilyPhone = ""
                                newFamilyPhotoDesc = ""
                                newFamilyNotes = ""
                                showAddFamilyDialog = false
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = iOSPurplePrimary)
                    ) {
                        Text("Guardar Familiar")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showAddFamilyDialog = false }) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}

@Composable
fun SettingsSectionCard(
    title: String,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        color = Color.White,
        shadowElevation = 2.dp,
        border = BorderStroke(1.dp, GlassBorder)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = title,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = iOSPurpleDark
            )
            Spacer(modifier = Modifier.height(12.dp))
            content()
        }
    }
}

@Composable
fun ControlChip(
    label: String,
    isON: Boolean,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.clickable { onClick() },
        shape = RoundedCornerShape(10.dp),
        color = if (isON) iOSPurpleLight else Color(0xFFF2F2F7),
        border = BorderStroke(1.dp, if (isON) iOSPurplePrimary else GlassBorder)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = if (isON) iOSPurplePrimary else TextMuted,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = label,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = if (isON) iOSPurpleDark else TextDark
            )
            Text(
                text = if (isON) "ON" else "OFF",
                fontSize = 9.sp,
                color = if (isON) iOSPurplePrimary else TextMuted
            )
        }
    }
}

