package com.example.calatour

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.calatour.Model.OffersList
import com.example.calatour.adapters.ChatAdapter
import com.example.calatour.model.ChatMessage
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ChatActivity : AppCompatActivity(), View.OnClickListener {

    private val dataSource = ArrayList<ChatMessage>()
    private lateinit var chatAdapter: ChatAdapter

    private lateinit var authorTextView: TextView
    private lateinit var contentEditText: EditText
    private lateinit var sendButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        chatAdapter = ChatAdapter(this, dataSource)

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        val chatRecyclerView = findViewById<RecyclerView>(R.id.chatMessageRecyclerView)
        chatRecyclerView.layoutManager = layoutManager
        chatRecyclerView.adapter = chatAdapter

        authorTextView = findViewById(R.id.messageAuthorTextView)
        contentEditText = findViewById(R.id.addMessageContentEditText)
        sendButton = findViewById(R.id.messageSendButton)

        sendButton.setOnClickListener(this)

        authorTextView.text = "Admin"
    }

    override fun onClick(v: View?) {
        dataSource.add( 0,
            ChatMessage(
                authorTextView.text.toString(),
                contentEditText.text.toString(),
                DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(LocalDateTime.now())
            )
        )
        chatAdapter.notifyItemInserted(0)
        contentEditText.text.clear()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.chat_options_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.chatOptionsDesign1) {
            chatAdapter.setDesign(1)
        } else if(item.itemId == R.id.chatOptionsDesign2) {
            chatAdapter.setDesign(2)
        } else if(item.itemId == R.id.chatOptionsMenuBothDesigns) {
            chatAdapter.setDesign(0)
        }
        return super.onOptionsItemSelected(item)
    }
}