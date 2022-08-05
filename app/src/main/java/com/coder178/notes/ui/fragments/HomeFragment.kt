package com.coder178.notes.ui.fragments

import android.os.Bundle
import android.view.*
//import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.coder178.notes.R
import com.coder178.notes.ViewModel.NotesViewModel
import com.coder178.notes.databinding.FragmentHomeBinding
import com.coder178.notes.ui.adapter.NotesAdapter
import android.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager
import com.coder178.notes.Model.Notes


class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    val viewModel: NotesViewModel by viewModels()
    var oldNotesLists = arrayListOf<Notes>()
    lateinit var adapter: NotesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)

        val sv = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        binding.rcvAllNotes.layoutManager = sv

        viewModel.getNotes().observe(viewLifecycleOwner, { notesList ->
            oldNotesLists = notesList as ArrayList<Notes>
            binding.rcvAllNotes.layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = NotesAdapter(requireContext(), notesList)
            binding.rcvAllNotes.adapter = adapter
        })

        binding.filterHigh.setOnClickListener {
            viewModel.getHighNotes().observe(viewLifecycleOwner, { notesList ->
                oldNotesLists = notesList as ArrayList<Notes>
                binding.rcvAllNotes.layoutManager = GridLayoutManager(requireContext(), 2)
                adapter = NotesAdapter(requireContext(), notesList)
                binding.rcvAllNotes.adapter = adapter            })
//            binding.rcvAllNotes.adapter = NotesAdapter(requireContext(), notesList)})
        }

        binding.filterMedium.setOnClickListener {
            viewModel.getMediumNotes().observe(viewLifecycleOwner, { notesList ->
                oldNotesLists = notesList as ArrayList<Notes>
                binding.rcvAllNotes.layoutManager = GridLayoutManager(requireContext(), 2)
                adapter = NotesAdapter(requireContext(), notesList)
                binding.rcvAllNotes.adapter = adapter            })
        }

        binding.filterLow.setOnClickListener {
            viewModel.getLowNotes().observe(viewLifecycleOwner, { notesList ->
                oldNotesLists = notesList as ArrayList<Notes>
                binding.rcvAllNotes.layoutManager = GridLayoutManager(requireContext(), 2)
                adapter = NotesAdapter(requireContext(), notesList)
                binding.rcvAllNotes.adapter = adapter            })
        }

        binding.allNotes.setOnClickListener {

            viewModel.getNotes().observe(viewLifecycleOwner, { notesList ->
                oldNotesLists = notesList as ArrayList<Notes>
                binding.rcvAllNotes.layoutManager = GridLayoutManager(requireContext(), 2)
                adapter = NotesAdapter(requireContext(), notesList)
                binding.rcvAllNotes.adapter = adapter            })

        }

        binding.btnAdd.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_homeFragment2_to_createNotesFragment3)
        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)

        val item = menu.findItem(R.id.app_bar_search)
        val searchView = item.actionView as SearchView
        searchView.queryHint = "Enter Notes Here..."
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                NotesFiltering(newText)
                return true
            }
        })

        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun NotesFiltering(newText: String?) {

        val newFilterList = arrayListOf<Notes>()

        for(i in oldNotesLists){
            if(i.title.contains(newText!!) || i.subTitile.contains(newText!!)){
                newFilterList.add(i)
            }
        }
        adapter.filtering(newFilterList)
    }

}