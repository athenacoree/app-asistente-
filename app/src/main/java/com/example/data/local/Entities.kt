package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chat_messages")
data class ChatMessageEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val content: String,
    val sender: String, // "user" or "ai"
    val timestamp: Long = System.currentTimeMillis(),
    val modelUsed: String = "OpenRouter Llama-3.3 70B",
    val isAction: Boolean = false,
    val actionType: String? = null
)

@Entity(tableName = "projects")
data class ProjectEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val description: String,
    val category: String = "General",
    val codeSnippet: String = "",
    val summaryNote: String = "",
    val updatedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "system_actions")
data class SystemActionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val category: String, // "Ajustes", "Redes Sociales", "Multimedias", "Proyectos", "Compañía"
    val title: String,
    val description: String,
    val commandKey: String,
    val isEnabled: Boolean = true
)
