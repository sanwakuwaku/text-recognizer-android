package com.sanwaku2.textrecognizer.ui.ocrrecords

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sanwaku2.textrecognizer.R
import com.sanwaku2.textrecognizer.databinding.OcrRecordsFragmentBinding
import com.sanwaku2.textrecognizer.ServiceLocator

/**
 * 保存したOCRデータ一覧表示画面
 */
class OcrRecordsFragment : Fragment() {
    private val viewModel: OcrRecordsViewModel by viewModels {
        ServiceLocator.provideOcrRecordsViewModelFactory(requireContext())
    }
    private lateinit var ocrRecordsView: RecyclerView
    private lateinit var binding: OcrRecordsFragmentBinding

    companion object {
        fun newInstance() = OcrRecordsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.ocr_records_fragment, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ocrRecordsView = view.findViewById(R.id.rv_records)
        val adapter = OcrRecordsViewAdapter(viewModel, viewLifecycleOwner)
        val layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
        ocrRecordsView.layoutManager = layoutManager
        ocrRecordsView.setHasFixedSize(true)
        ocrRecordsView.adapter = adapter

        viewModel.ocrRecords.observe(viewLifecycleOwner) {
            adapter.notifyDataSetChanged()
        }

        viewModel.fetchOcrRecords()
    }
}