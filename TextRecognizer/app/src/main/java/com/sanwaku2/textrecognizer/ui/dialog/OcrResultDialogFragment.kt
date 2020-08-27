package com.sanwaku2.textrecognizer.ui.dialog

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.sanwaku2.logger.Logger
import com.sanwaku2.textrecognizer.R

/**
 * OCR結果表示用ダイアログ<p>
 * テキストは選択可能
 */
class OcrResultDialogFragment : DialogFragment() {
    interface DialogCallback {
        fun onDialogButtonClicked(requestCode: Int, resultCode: Int)
        fun onDialogCancelled(requestCode: Int)
    }

    private var dialogCallback: DialogCallback? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        val fragment = targetFragment
        if (fragment !is DialogCallback) {
            throw IllegalStateException()
        }
        dialogCallback = fragment
    }

    override fun onDetach() {
        super.onDetach()
        dialogCallback = null
    }

    override fun onCancel(dialog: DialogInterface) {
        dialogCallback?.onDialogCancelled(targetRequestCode)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val requestCode = targetRequestCode
        val listener = DialogInterface.OnClickListener { _, i ->
            dismiss()
            dialogCallback?.onDialogButtonClicked(requestCode, i)
        }

        isCancelable = arguments?.getBoolean("cancelable", true) ?: true

        val builder = AlertDialog.Builder(requireContext())

        val textView = layoutInflater.inflate(R.layout.dialog_selectable_textview, null) as TextView
        textView.setTextIsSelectable(true)
        builder.setView(textView)

        arguments?.getString("title").let {
            builder.setTitle(it)
        }
        arguments?.getString("message").let {
            textView.text = it
        }
        arguments?.getString("positive_btn_label").let {
            builder.setPositiveButton(it, listener)
        }
        arguments?.getString("negative_btn_label").let {
            builder.setNegativeButton(it, listener)
        }

        return builder.create()
    }

    class Builder<T : Fragment>(
        private val target: T,
        private var tag: String = "OcrResultDialog",
        private var requestCode: Int = -1,
        private var cancelable: Boolean = true,
        private var title: String = "",
        private var message: String = "",
        private var positiveBtnLabel: String = "",
        private var negativeBtnLabel: String = ""
    ) {
        fun show() {
            Logger.d("Builder.show()")
            val bundle = Bundle()
            bundle.putBoolean("cancelable", cancelable)
            bundle.putString("title", title)
            bundle.putString("message", message)
            bundle.putString("positive_btn_label", positiveBtnLabel)
            bundle.putString("negative_btn_label", negativeBtnLabel)

            val fragment = OcrResultDialogFragment()
            fragment.setTargetFragment(target, requestCode)
            fragment.arguments = bundle
            fragment.show(target.parentFragmentManager, tag)
        }
    }
}