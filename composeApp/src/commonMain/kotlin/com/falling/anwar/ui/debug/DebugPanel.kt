package com.falling.anwar.ui.debug

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.falling.anwar.domain.FallEvent
import com.falling.anwar.domain.FallStore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DebugPanel(
    fallStore: FallStore,
    onDismiss: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .navigationBarsPadding(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Debug Controls",
                style = MaterialTheme.typography.headlineSmall
            )
            
            Button(
                onClick = { 
                    fallStore.onEvent(FallEvent.FALL)
                    onDismiss()
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Text("Trigger FALL")
            }

            Button(
                onClick = { 
                    // NO_FALL doesn't clear ALERT as per rules, 
                    // but we call it here to simulate the event source.
                    // FallStore.onEvent(NO_FALL) is a no-op currently in logic but let's keep it.
                    fallStore.onEvent(FallEvent.NO_FALL)
                    onDismiss()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Send NO_FALL")
            }

            OutlinedButton(
                onClick = { 
                    fallStore.acknowledgeOk()
                    onDismiss()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Acknowledge OK (Stop Alert)")
            }

            TextButton(
                onClick = onDismiss,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Close")
            }
            
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
