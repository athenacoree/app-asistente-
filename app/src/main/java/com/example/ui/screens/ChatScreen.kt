package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.ChatMessageEntity
import com.example.ui.theme.*
import com.example.ui.viewmodel.AssistantViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(viewModel: AssistantViewModel) {
    val messages by viewModel.messages.collectAsState()
    var inputText by remember { mutableStateOf("") }
    val listState = rememberLazyListState()
    val context = LocalContext.current

    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(iOSWhiteBackground)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Header iOS Top Bar
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = iOSCardSurface,
                shadowElevation = 2.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Avatar Icon
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.linearGradient(
                                    listOf(iOSPurplePrimary, iOSPurpleSecondary)
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = "Aura Avatar",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = viewModel.assistantName,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextDark
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(iOSPurpleLight)
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = "iOS Style",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = iOSPurpleDark
                                )
                            }
                        }
                        Text(
                            text = if (viewModel.isLoadingResponse) "Escribiendo respuesta..." else "En línea • ${viewModel.selectedModel.name}",
                            fontSize = 12.sp,
                            color = if (viewModel.isLoadingResponse) iOSPurplePrimary else TextMuted
                        )
                    }

                    // Live Canvas / Artifact Mode Toggle
                    IconButton(onClick = { viewModel.liveCanvasVisible = !viewModel.liveCanvasVisible }) {
                        Icon(
                            imageVector = Icons.Default.IntegrationInstructions,
                            contentDescription = "Live Canvas / Artifact",
                            tint = if (viewModel.liveCanvasVisible) iOSPurplePrimary else TextMuted
                        )
                    }

                    // Floating Screen Overlay Bubble Button
                    IconButton(
                        onClick = {
                            viewModel.triggerOverlayQuestion("¿Deseas que lea tu pantalla o te ayude con lo que estás viendo?")
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.FilterNone,
                            contentDescription = "Burbuja Pantalla",
                            tint = iOSPurplePrimary
                        )
                    }

                    // Settings Icon (Navigates directly to settings tab 3)
                    IconButton(onClick = { viewModel.selectedTab = 3 }) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Ajustes",
                            tint = iOSPurplePrimary
                        )
                    }

                    // Clear Chat
                    IconButton(onClick = { viewModel.clearHistory() }) {
                        Icon(
                            imageVector = Icons.Default.DeleteOutline,
                            contentDescription = "Limpiar Chat",
                            tint = TextMuted
                        )
                    }
                }
            }

            // Message List (WhatsApp iOS Style)
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                contentPadding = PaddingValues(top = 12.dp, bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                if (messages.isEmpty()) {
                    item {
                        EmptyChatGreeting(viewModel)
                    }
                }

                items(messages, key = { it.id }) { msg ->
                    WhatsAppChatBubble(
                        message = msg,
                        onSpeak = { viewModel.speakText(msg.content) },
                        onCopy = {
                            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                            val clip = ClipData.newPlainText("Aura AI Text", msg.content)
                            clipboard.setPrimaryClip(clip)
                            Toast.makeText(context, "Texto copiado al portapapeles", Toast.LENGTH_SHORT).show()
                        }
                    )
                }

                if (viewModel.isLoadingResponse) {
                    item {
                        AiTypingIndicator()
                    }
                }
            }

            // Input Bar iOS Glass Style
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = GlassSurface,
                shadowElevation = 8.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding()
                        .padding(horizontal = 12.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Voice Mic Button
                    IconButton(
                        onClick = {
                            viewModel.sendMessage("Hola Aura, háblame sobre mis proyectos")
                        },
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                            .background(iOSPurpleLight)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Mic,
                            contentDescription = "Voz",
                            tint = iOSPurplePrimary
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    // Text Field
                    OutlinedTextField(
                        value = inputText,
                        onValueChange = { inputText = it },
                        placeholder = { Text("Escribe o pide algo...", fontSize = 14.sp, color = TextMuted) },
                        modifier = Modifier
                            .weight(1f)
                            .heightIn(min = 44.dp, max = 120.dp),
                        shape = RoundedCornerShape(22.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedBorderColor = iOSPurplePrimary,
                            unfocusedBorderColor = GlassBorder
                        ),
                        maxLines = 4
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    // Send Button
                    FloatingActionButton(
                        onClick = {
                            if (inputText.isNotBlank()) {
                                val text = inputText
                                inputText = ""
                                viewModel.sendMessage(text)
                            }
                        },
                        modifier = Modifier.size(44.dp),
                        containerColor = iOSPurplePrimary,
                        contentColor = Color.White,
                        shape = CircleShape
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Send,
                            contentDescription = "Enviar",
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }

        // Screen Overlay Bubble Modal ("Burbuja hermosa en pantalla")
        if (viewModel.showFloatingOverlay) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.4f))
                    .clickable { viewModel.showFloatingOverlay = false },
                contentAlignment = Alignment.Center
            ) {
                Surface(
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth(0.9f)
                        .shadow(12.dp, RoundedCornerShape(24.dp))
                        .border(1.5.dp, iOSPurpleSecondary, RoundedCornerShape(24.dp)),
                    shape = RoundedCornerShape(24.dp),
                    color = Color.White
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape)
                                .background(iOSPurpleLight),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.ChatBubble,
                                contentDescription = "Burbuja",
                                tint = iOSPurplePrimary,
                                modifier = Modifier.size(28.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "Aura AI Pregunta:",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = iOSPurpleDark
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = viewModel.overlayQuestion,
                            fontSize = 15.sp,
                            textAlign = TextAlign.Center,
                            color = TextDark
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            OutlinedButton(
                                onClick = { viewModel.showFloatingOverlay = false },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text("Cancelar", color = TextMuted)
                            }

                            Button(
                                onClick = { viewModel.respondToOverlayPrompt() },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = iOSPurplePrimary)
                            ) {
                                Text("Sí, dime", color = Color.White, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }

        // Live Canvas / Artifact Mode Dialog Modal
        if (viewModel.liveCanvasVisible) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f))
                    .clickable { viewModel.liveCanvasVisible = false },
                contentAlignment = Alignment.Center
            ) {
                Surface(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(0.95f)
                        .fillMaxHeight(0.85f)
                        .clickable(enabled = false) {},
                    shape = RoundedCornerShape(24.dp),
                    color = Color.White,
                    shadowElevation = 16.dp
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(imageVector = Icons.Default.IntegrationInstructions, contentDescription = null, tint = iOSPurplePrimary)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(text = viewModel.activeArtifactTitle, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = iOSPurpleDark)
                            }
                            IconButton(onClick = { viewModel.liveCanvasVisible = false }) {
                                Icon(imageVector = Icons.Default.Close, contentDescription = "Cerrar", tint = TextMuted)
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Modo Artefacto Interactivo (Live Canvas) • Renderizado en tiempo real",
                            fontSize = 11.sp,
                            color = TextMuted
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Surface(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            color = Color(0xFF1E1E2E)
                        ) {
                            LazyColumn(modifier = Modifier.padding(14.dp)) {
                                item {
                                    Text(
                                        text = viewModel.activeArtifactCode,
                                        fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                                        fontSize = 12.sp,
                                        color = Color(0xFFA6ADC8),
                                        lineHeight = 18.sp
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Button(
                                onClick = {
                                    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                    val clip = ClipData.newPlainText("Canvas Code", viewModel.activeArtifactCode)
                                    clipboard.setPrimaryClip(clip)
                                    Toast.makeText(context, "Código copiado", Toast.LENGTH_SHORT).show()
                                },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = iOSPurplePrimary)
                            ) {
                                Icon(imageVector = Icons.Default.ContentCopy, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Copiar Código")
                            }

                            OutlinedButton(
                                onClick = {
                                    Toast.makeText(context, "Ejecutando simulación de vista previa...", Toast.LENGTH_SHORT).show()
                                },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Icon(imageVector = Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Simular UI")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun WhatsAppChatBubble(
    message: ChatMessageEntity,
    onSpeak: () -> Unit,
    onCopy: () -> Unit
) {
    val isUser = message.sender == "user"
    val formattedTime = remember(message.timestamp) {
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        sdf.format(Date(message.timestamp))
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start
    ) {
        if (!isUser) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(iOSPurpleLight),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.SmartToy,
                    contentDescription = "AI",
                    tint = iOSPurplePrimary,
                    modifier = Modifier.size(18.dp)
                )
            }
            Spacer(modifier = Modifier.width(6.dp))
        }

        Surface(
            modifier = Modifier
                .widthIn(max = 290.dp)
                .shadow(if (isUser) 2.dp else 1.dp, RoundedCornerShape(18.dp))
                .border(
                    width = if (isUser) 0.dp else 1.dp,
                    color = if (isUser) Color.Transparent else GlassBorder,
                    shape = RoundedCornerShape(18.dp)
                ),
            shape = RoundedCornerShape(
                topStart = 18.dp,
                topEnd = 18.dp,
                bottomStart = if (isUser) 18.dp else 4.dp,
                bottomEnd = if (isUser) 4.dp else 18.dp
            ),
            color = if (isUser) WhatsAppUserBubble else WhatsAppAIBubble
        ) {
            Column(modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp)) {
                if (!isUser) {
                    Text(
                        text = message.modelUsed,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = iOSPurplePrimary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }

                Text(
                    text = message.content,
                    fontSize = 15.sp,
                    color = if (isUser) WhatsAppUserText else WhatsAppAIText,
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (!isUser) {
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Icon(
                                imageVector = Icons.Default.VolumeUp,
                                contentDescription = "Escuchar",
                                tint = iOSPurplePrimary,
                                modifier = Modifier
                                    .size(16.dp)
                                    .clickable { onSpeak() }
                            )
                            Icon(
                                imageVector = Icons.Default.ContentCopy,
                                contentDescription = "Copiar",
                                tint = TextMuted,
                                modifier = Modifier
                                    .size(16.dp)
                                    .clickable { onCopy() }
                            )
                        }
                    } else {
                        Spacer(modifier = Modifier.width(1.dp))
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = formattedTime,
                            fontSize = 10.sp,
                            color = if (isUser) Color.White.copy(alpha = 0.8f) else TextMuted
                        )
                        if (isUser) {
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "✓✓",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AiTypingIndicator() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(start = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(iOSPurpleLight),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.AutoAwesome,
                contentDescription = "Typing",
                tint = iOSPurplePrimary,
                modifier = Modifier.size(18.dp)
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
            shadowElevation = 1.dp
        ) {
            Text(
                text = "Aura AI está pensando...",
                fontSize = 13.sp,
                color = iOSPurplePrimary,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
            )
        }
    }
}

@Composable
fun EmptyChatGreeting(viewModel: AssistantViewModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Box(
            modifier = Modifier
                .size(72.dp)
                .clip(CircleShape)
                .background(iOSPurpleLight),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.AutoAwesome,
                contentDescription = "Greeting",
                tint = iOSPurplePrimary,
                modifier = Modifier.size(36.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "¡Hola, ${viewModel.userName}!",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = TextDark
        )
        Text(
            text = "Soy tu asistente personal ${viewModel.assistantName}. Estoy configurada con estilo iOS, cerebro local y modelos LLM sin filtros.",
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            color = TextMuted,
            modifier = Modifier.padding(top = 6.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Quick Suggestions Cards
        val suggestions = listOf(
            "🧠 ¿Cuál es el estado de mi cerebro local en la SD?",
            "💻 Abre mi proyecto NotebookLM y analiza el código",
            "💬 Háblame como mi compañera sin restricciones",
            "⚡ Activa el modo linterna y revisa mi batería"
        )

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            suggestions.forEach { prompt ->
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { viewModel.sendMessage(prompt) },
                    shape = RoundedCornerShape(14.dp),
                    color = Color.White,
                    shadowElevation = 1.dp,
                    border = BorderStroke(1.dp, GlassBorder)
                ) {
                    Text(
                        text = prompt,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = iOSPurpleDark,
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp)
                    )
                }
            }
        }
    }
}
