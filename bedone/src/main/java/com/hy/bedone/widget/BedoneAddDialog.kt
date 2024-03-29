package com.hy.bedone.widget

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.SystemClock
import android.view.Gravity
import android.view.ViewGroup
import android.view.Window
import android.widget.EditText
import com.hy.bedone.R
import com.hy.common.model.Bedone
import kotlinx.android.synthetic.main.bedone_add_dialog.*

/**
 * @auther:hanshengjian
 * @date:2022/2/25
 *
 */
class BedoneAddDialog(context: Context, val type: Int, val addMethod: (bedone: Bedone) -> Unit) :
    Dialog(
        context
    ) {

    var bedoneEditText: EditText? = null;

    init {
        setContentView(R.layout.bedone_add_dialog)

        val dialogWindow: Window = getWindow()
        dialogWindow.setBackgroundDrawable(
            ColorDrawable(
                context.getResources().getColor(R.color.transparent)
            )
        );
        dialogWindow.setGravity(Gravity.BOTTOM)
        val layoutParams = dialogWindow.attributes
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
        dialogWindow.attributes = layoutParams

        bedoneEditText = findViewById(R.id.bedone_edit)

        btn_save.setOnClickListener {
            dismiss()
            val bedone = Bedone()
            bedone.content = bedoneEditText?.text.toString()
            bedone.createTime = SystemClock.elapsedRealtime()
            bedone.type = type
            addMethod?.invoke(bedone)
        }
    }
}