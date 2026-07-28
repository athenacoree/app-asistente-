package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.screens.BrainStorageScreen
import com.example.ui.screens.ChatScreen
import com.example.ui.screens.ProjectsNotebookScreen
import com.example.ui.screens.SettingsProfileScreen
import com.example.ui.theme.*
import com.example.ui.viewmodel.AssistantViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AuraAITheme {
                val viewModel: AssistantViewModel = viewModel()
                AuraMainApp(viewModel)
            }
        }
    }
}

@Composable
fun AuraMainApp(viewModel: AssistantViewModel) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            IosBottomNavigationBar(
                selectedTab = viewModel.selectedTab,
                onTabSelected = { viewModel.selectedTab = it }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (viewModel.selectedTab) {
                0 -> ChatScreen(viewModel)
                1 -> BrainStorageScreen(viewModel)
                2 -> ProjectsNotebookScreen(viewModel)
                3 -> SettingsProfileScreen(viewModel)
            }

            // iOS Style Voice Control Overlay Badges
            if (viewModel.iosVoiceControlEnabled) {
                Surface(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(top = 40.dp, end = 12.dp),
                    shape = RoundedCornerShape(12.dp),
                    color = Color.Black.copy(alpha = 0.75f)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "🗣️ Control por Voz: Di \"1 Chat\" | \"2 Cerebro\" | \"3 Proyectos\"",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun IosBottomNavigationBar(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .windowInsetsPadding(WindowInsets.navigationBars),
        color = GlassSurface,
        shadowElevation = 10.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IosTabItem(
                icon = Icons.Default.ChatBubble,
                label = "Conversación",
                isSelected = selectedTab == 0,
                onClick = { onTabSelected(0) }
            )
            IosTabItem(
                icon = Icons.Default.Psychology,
                label = "Cerebro SD",
                isSelected = selectedTab == 1,
                onClick = { onTabSelected(1) }
            )
            IosTabItem(
                icon = Icons.Default.MenuBook,
                label = "Proyectos",
                isSelected = selectedTab == 2,
                onClick = { onTabSelected(2) }
            )
            IosTabItem(
                icon = Icons.Default.Settings,
                label = "Ajustes",
                isSelected = selectedTab == 3,
                onClick = { onTabSelected(3) }
            )
        }
    }
}

@Composable
fun IosTabItem(
    icon: ImageVector,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 6.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .background(if (isSelected) iOSPurpleContainer else Color.Transparent)
                .padding(horizontal = 14.dp, vertical = 4.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = if (isSelected) iOSPurplePrimary else TextMuted,
                modifier = Modifier.size(22.dp)
            )
        }
        Text(
            text = label,
            fontSize = 11.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            color = if (isSelected) iOSPurpleDark else TextMuted
        )
    }
}
