package com.github.rogeryk.charity_android.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import com.github.rogeryk.charity_android.R
import kotlinx.android.synthetic.main.comment_dialog_view.*


class CommentDialog(context: Context): Dialog(context, R.style.comment_dialog_theme) {

    var confirmListener: ((String) -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.comment_dialog_view)

        confirm_btn.setOnClickListener {
            val content = comment_edit.text.toString()
            confirmListener?.invoke(content)
        }

    }

    override fun show() {
        super.show()

        val layoutParams = window.getAttributes();
        layoutParams.gravity = Gravity.BOTTOM;
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;

        window.decorView.setPadding(0, 0, 0, 0);

        window.attributes = layoutParams;
    }

}