package ar.edu.utn.frba.mobile.clases.ui.status_update

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ar.edu.utn.frba.mobile.clases.R
import ar.edu.utn.frba.mobile.clases.databinding.MainFragmentBinding
import ar.edu.utn.frba.mobile.clases.databinding.StatusUpdateFragmentBinding

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [StatusUpdateFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [StatusUpdateFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class StatusUpdateFragment : Fragment(), ColorsAdapter.ItemClickListener {
    private var _binding: StatusUpdateFragmentBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var listener: OnFragmentInteractionListener? = null
    val colorsAdapter = ColorsAdapter(this)
    val backgroundDrawable = GradientDrawable().apply {
        orientation = GradientDrawable.Orientation.TL_BR
        setColor(Color.WHITE)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = StatusUpdateFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<RecyclerView>(R.id.colorsRecycler).apply {
            layoutManager = LinearLayoutManager(context).apply {
                orientation = LinearLayoutManager.HORIZONTAL
            }
            adapter = colorsAdapter
        }
        binding.textField.background = backgroundDrawable
        binding.textField.setTextColor(Color.WHITE)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onItemClick(position: Int) {
        val colors = PostTheme.getBackgroundColors(position)
        backgroundDrawable.colors = colors
        val backColor = colors.get(0)
        binding.textField.setTextColor(PostTheme.getTextColor(backColor))
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment StatusUpdate.
         */
        @JvmStatic
        fun newInstance() =
            StatusUpdateFragment()
    }
}