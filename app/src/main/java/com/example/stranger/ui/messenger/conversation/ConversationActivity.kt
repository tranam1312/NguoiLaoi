package com.example.stranger.ui.messenger.conversation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.stranger.R
import com.example.stranger.ui.messenger.conversation.fragment.ConversationFragment

class ConversationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conversation)
        supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in,R.anim.fade_out,R.anim.fade_in,R.anim.silde_out).replace(android.R.id.content,ConversationFragment.newInstance())
    }
}