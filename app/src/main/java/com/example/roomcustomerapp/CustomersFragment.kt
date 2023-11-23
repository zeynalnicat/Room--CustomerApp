import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomcustomerapp.CustomerAdapter
import com.example.roomcustomerapp.R
import com.example.roomcustomerapp.databinding.FragmentCustomerBinding
import com.example.roomcustomerapp.db.RoomDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class CustomersFragment : Fragment() {
    private lateinit var binding: FragmentCustomerBinding
    private lateinit var adapter: CustomerAdapter
    private var db: RoomDB? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCustomerBinding.inflate(inflater)

        db = RoomDB.accessDB(requireContext())
        setAdapter()

        binding.btnAdd.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_customersFragment_to_addCustomerFragment)
        }

        return binding.root
    }


    private fun setAdapter() {
        CoroutineScope(Dispatchers.Main).launch {
            val customerList = db!!.let {
                it.customerDao().selectAll()
            }
            adapter = CustomerAdapter(customerList, db!!) { bundle ->
                Navigation.findNavController(requireView())
                    .navigate(R.id.action_customersFragment_to_addCustomerFragment, bundle)
            }
            binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
            binding.recyclerView.adapter = adapter
        }
    }


}