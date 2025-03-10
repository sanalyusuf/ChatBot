package com.sanal.chatbot

import android.annotation.SuppressLint
import android.os.Build
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.launch

class ChatBotViewModel :ViewModel() {

    val messageList by lazy {
        mutableStateListOf<MessageModel>()

    }


    val generativeModel : GenerativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = Constants.apiKey
    )
    @SuppressLint("NewApi")
    fun sendMessage(question : String){
       viewModelScope.launch {
           val chat = generativeModel.startChat(
               history = messageList.map {
                   content(it.role){ text(it.message) }
               }.toList()
           )

           messageList.add(MessageModel(question, "user"))


           val response = chat.sendMessage(question)
           if (Build.VERSION.SDK_INT >= 35) {
               messageList.removeLast()
           }
           messageList.add(MessageModel(response.text.toString(), "model"))
          /* Log.i("Response from Gemini",response.text.toString()) */
       }

    }

}