package org.first.mnkotlin.foodFragemnts

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import org.first.mnkotlin.R

class Food3Fragment : Fragment() {

    companion object {
        fun newInstance() = Food3Fragment()
    }

    private lateinit var viewModel: Food3ViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_food3, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(Food3ViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
