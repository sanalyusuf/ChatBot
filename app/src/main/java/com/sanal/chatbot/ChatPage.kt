package com.sanal.chatbot

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sanal.chatbot.ui.theme.ColorUserMessage
import com.sanal.chatbot.ui.theme.ColorUserModel

@Composable
fun ChatPage(modifier: Modifier = Modifier, viewModel: ChatBotViewModel) {
    Text(text = "ChatPage")
    Column(
        modifier = modifier
    ) {
        AppHeader()
        MessageList(
            modifier = Modifier.weight(1f),
            messageList = viewModel.messageList
        )
        MessageInput(onMessageSend = {
           viewModel.sendMessage(it)
        })
    }
}

@Composable
fun MessageList(modifier: Modifier = Modifier,messageList : List<MessageModel>) {
    LazyColumn (
        modifier = modifier,
        reverseLayout = true
    )
    {
        items(messageList.reversed())
        {
            MessageRow(messageModel = it)
        }



    }

}

@Composable
fun MessageRow(messageModel: MessageModel) {
    val isModel = messageModel.role == "model"

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = if (isModel) Arrangement.Start else Arrangement.End
    ) {
        Box(
            modifier = Modifier
                .padding(
                    start = if (isModel) 8.dp else 70.dp,
                    end = if (isModel) 70.dp else 8.dp,
                    top = 8.dp,
                    bottom = 8.dp
                )
                .clip(RoundedCornerShape(48f))
                .background(if (isModel) ColorUserModel else ColorUserMessage)
                .padding(16.dp)
        ) {
            Text(
                text = messageModel.message,
                fontWeight = FontWeight.W500,
                color = Color.DarkGray
            )
        }
    }
}



@Composable
fun MessageInput(onMessageSend : (String)-> Unit) {

    var message by remember {
        mutableStateOf("")
    }

Row (
    modifier = Modifier.padding(8.dp),
    verticalAlignment = Alignment.CenterVertically

) {
  OutlinedTextField(
      modifier = Modifier.weight(1f),
      value = message,
      onValueChange = {
                message = it

      }
  )
    IconButton(onClick = {
        onMessageSend(message)
        message = ""
    }) {
        Icon(
            imageVector = Icons.Default.Send,
            contentDescription = "Send"
        )
        
    }
}
    
}

@Composable
fun AppHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = "Chat Bot",
            color = Color.White,
            fontSize = 22.sp
        )
    }
}