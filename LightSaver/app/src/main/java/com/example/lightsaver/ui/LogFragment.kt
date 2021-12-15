package com.example.lightsaver.ui


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lightsaver.BaseFragment
import com.example.lightsaver.R
import com.example.lightsaver.ui.adapters.MessageAdapter
import com.example.lightsaver.viewmodel.MessageViewModel
import com.example.lightsaver.vo.Message
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import com.example.lightsaver.databinding.FragmentLogsBinding
import timber.log.Timber
import javax.inject.Inject

class LogFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    public lateinit var viewModel: MessageViewModel
    private lateinit var binding: FragmentLogsBinding;

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentLogsBinding.inflate(layoutInflater)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MessageViewModel::class.java)
        observeViewModel(viewModel)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (view is RecyclerView) {
            val context = view.getContext()
            view.layoutManager = LinearLayoutManager(context)
            view.adapter =  MessageAdapter(ArrayList<Message>())
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentLogsBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onDetach() {
        super.onDetach()
        disposable.dispose()
    }

    private fun observeViewModel(viewModel: MessageViewModel) {
        disposable.add(viewModel.getMessages()

               .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe({messages ->
                   binding.list.adapter = MessageAdapter(messages)
                   binding.list.invalidate()
               }, { error -> Timber.e("Unable to get messages: " + error)}))
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         */
        fun newInstance(): LogFragment {
            return LogFragment()
        }
    }
}