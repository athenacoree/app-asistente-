package com.example.data.model

data class LlmModelOption(
    val id: String,
    val name: String,
    val provider: String,
    val isFree: Boolean = true,
    val description: String,
    val size: String = "Cloud / Free"
)

data class VoiceProfile(
    val id: String,
    val name: String,
    val accent: String,
    val pitch: Float,
    val speechRate: Float,
    val description: String
)

data class FamilyMember(
    val id: String = java.util.UUID.randomUUID().toString(),
    val name: String,
    val relationship: String, // e.g., "Mamá", "Papá", "Esposa", "Hermano", "Hijo", "Amigo"
    val phone: String = "",
    val photoDescription: String = "",
    val notes: String = ""
)

object AiModelDefaults {
    val FREE_OPENROUTER_MODELS = listOf(
        LlmModelOption(
            id = "meta-llama/llama-3.3-70b-instruct:free",
            name = "Llama 3.3 70B Free",
            provider = "OpenRouter",
            description = "Modelo ultra rápido y potente con razona miento libre sin restricciones."
        ),
        LlmModelOption(
            id = "google/gemini-2.0-flash-lite-preview-02-05:free",
            name = "Gemini 2.0 Flash Lite Free",
            provider = "OpenRouter / Google",
            description = "Súper liviano y de baja latencia para respuestas conversacionales inmediatas."
        ),
        LlmModelOption(
            id = "qwen/qwen-2.5-72b-instruct:free",
            name = "Qwen 2.5 72B Instruct Free",
            provider = "OpenRouter / Alibaba",
            description = "Excelente en programación, código y tareas complejas de proyectos."
        ),
        LlmModelOption(
            id = "deepseek/deepseek-r1:free",
            name = "DeepSeek R1 Reasoning Free",
            provider = "OpenRouter / DeepSeek",
            description = "Razonamiento lógico paso a paso tipo O1 / Claude para desarrollo."
        ),
        LlmModelOption(
            id = "gemini-3.5-flash",
            name = "Gemini 3.5 Flash Direct",
            provider = "Google AI Studio",
            description = "Llamada directa vía API Key personal con velocidad nativa."
        ),
        LlmModelOption(
            id = "local-embedded-50mb",
            name = "Cerebro Local Offline (~50MB)",
            provider = "Motor Local On-Device",
            isFree = true,
            size = "50MB Local",
            description = "Motor ligero ejecutado en almacenamiento/SD sin depender de internet."
        )
    )

    val REALISTIC_VOICES = listOf(
        VoiceProfile("v_violeta", "Violeta UltraReal", "Español (Latam)", 1.0f, 1.0f, "Voz cálida, natural y empática estilo asistente personal."),
        VoiceProfile("v_aura_ai", "Aura AI Pro", "Español (España)", 1.05f, 0.98f, "Voz elegante, suave y articulada tipo Claude."),
        VoiceProfile("v_carlos", "Carlos Studio", "Español (México)", 0.92f, 1.02f, "Voz masculina profesional para soporte técnico y proyectos."),
        VoiceProfile("v_sora", "Sora Whisper", "Español (Neutro)", 1.15f, 0.95f, "Voz dulce e íntima ideal para compañía y conversación.")
    )

    val INITIAL_50_CAPABILITIES = listOf(
        "Ajustes" to listOf(
            "Encender / Apagar Wi-Fi",
            "Activar / Desactivar Datos Móviles",
            "Ajustar perfil de sonido (Silencio, Vibración, Normal)",
            "Manipular volumen de bocinas y audífonos Bluetooth",
            "Encender Linterna / Flash de cámara",
            "Leer estado de la Batería y optimización de energía",
            "Cambiar brillo de pantalla automático",
            "Verificar espacio libre en Almacenamiento y SD",
            "Copiar texto automático al Portapapeles",
            "Pegar contenido extraído en notas o chat"
        ),
        "Multimedias & Control" to listOf(
            "Reproducir / Pausar Música",
            "Avanzar o retroceder canción",
            "Tomar foto rápida con la cámara",
            "Abrir aplicaciones del teléfono por voz",
            "Capturar pantalla / Diagnóstico visual",
            "Modo altavoz para llamadas",
            "Escanear archivos en almacenamiento local",
            "Lanzar ecualizador de audio",
            "Reconocer audio ambiental",
            "Ajustar tiempo de apagado de pantalla"
        ),
        "Comunicación & Notificaciones" to listOf(
            "Leer Notificaciones entrantes en voz alta",
            "Enviar mensaje por WhatsApp / Telegram (Simulación / Intent)",
            "Dictado de voz a texto inteligente",
            "Responder preguntas en pantalla emergente (Burbuja iOS)",
            "Alertas automáticas sin comando previo (Hablar sola)",
            "Modo escucha fuera de la aplicación (Background Listener)",
            "Recordatorios personalizados con alarma",
            "Resumen diario de notificaciones perdidas",
            "Simulación de llamadas de asistente",
            "Búsqueda rápida en contactos"
        ),
        "Proyectos & Código (NotebookLM / Claude)" to listOf(
            "Análisis de código fuente y proyectos",
            "Generar resúmenes estilo NotebookLM",
            "Editor interactivo de código con sintaxis",
            "Indexación de documentos en subcarpetas /AuraAI_Brain/",
            "Sugerencias de refactorización de código",
            "Detección de errores y depuración",
            "Exportar notas de proyecto en Markdown",
            "Cargar fragmentos de código desde almacenamiento",
            "Preguntas y respuestas contextuales sobre código",
            "Seguimiento de tareas y lista To-Do de proyectos"
        ),
        "Acompañamiento & Personalización" to listOf(
            "Modo Compañía contra la soledad (Empatía profunda)",
            "Prompt de personalidad 100% personalizable",
            "Ajuste de filtro de contenido (Sin restricciones)",
            "Nombre del asistente editable (Aura, Jarvis, etc.)",
            "Aprendizaje progresivo según estilo de uso",
            "Registro de estado de ánimo del usuario",
            "Generador de frases motivacionales",
            "Sincronización con servidor Render / OpenRouter",
            "Gestión del cerebro en Tarjeta SD o Interna",
            "Copia de seguridad local de la conversación"
        )
    )
}
