package roberto.cano.superheroapp.ui.view

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import roberto.cano.superheroapp.R
import roberto.cano.superheroapp.core.Constants
import roberto.cano.superheroapp.data.model.ExceptionData
import roberto.cano.superheroapp.data.model.response.HeroResultResponse
import roberto.cano.superheroapp.databinding.ActivityMainBinding
import roberto.cano.superheroapp.ui.adapter.HeroAdapter
import roberto.cano.superheroapp.ui.viewmodel.MainActivityViewModel
import java.lang.Exception
import java.net.UnknownHostException

class MainActivity : AppCompatActivity() {
    private lateinit var heroesObserver : Observer<ArrayList<HeroResultResponse>>
    private lateinit var loaderObserver : Observer<Boolean>
    private lateinit var onErrorObserver : Observer<String>
    private lateinit var onSuccessObserver : Observer<String>
    private lateinit var onExceptionObserver : Observer<ExceptionData>
    private val binding get() = _binding
    private lateinit var _binding : ActivityMainBinding
    private lateinit var adapter: HeroAdapter
    private val viewModel : MainActivityViewModel by viewModels()
    private val biographyDialog = BiographyDialog ()
    private val statDialog = StatsDialog ()
    private val jobDialog = JobDialog ()
    private val appearanceDialog = AppearanceDialog ()
    private val connectionsDialog = ConnectionsDialog ()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initComponents()
        initObservers()
        setObservers()
        setListeners()
        adapter.page = 0
        viewModel.loadHeroes( Constants.ALPHABET.first() )
    }

    private fun initObservers() {
        heroesObserver = Observer <ArrayList<HeroResultResponse>> {
            CoroutineScope(Dispatchers.Main).launch {
                val list = ArrayList<HeroResultResponse>()
                list.addAll(adapter.currentList)
                list.addAll(it)
                adapter.page = adapter.page +1
                adapter.submitList( list )
            }
        }

        loaderObserver = Observer <Boolean> {
            CoroutineScope(Dispatchers.Main).launch {
                if (it){
                    binding.progressBar.visibility = View.VISIBLE
                }else{
                    binding.progressBar.visibility = View.GONE
                }
            }
        }

        onErrorObserver = Observer <String> {
            CoroutineScope(Dispatchers.Main).launch {
                Snackbar.make(binding.root,"Ha sucedido un error", 2000).show()
            }
        }

        onSuccessObserver = Observer <String> {
            CoroutineScope(Dispatchers.Main).launch {
                binding.progressBar.visibility = View.GONE
            }
        }

        onExceptionObserver = Observer <ExceptionData> {
            CoroutineScope(Dispatchers.Main).launch {
                binding.progressBar.visibility = View.GONE
                when (it.exception){
                    is NullPointerException ->  {
                        Snackbar.make( binding.root,"La respuesta del servidor está vacia, Contacte a sistemas" , 2000).show()
                    }
                    is HttpException -> {
                        Snackbar.make( binding.root,"El servidor no devolvió una respuesta valida, Contacte a sistemas.", 2000).show()
                    }
                    is IndexOutOfBoundsException -> {
                        Snackbar.make( binding.root, "La aplicación ha intentado recorrer posiciones fuera de índice.", 2000).show()
                    }
                    is UnknownHostException -> {
                        println()
                        Snackbar.make( binding.root, "No tienes conexión al servidor, busca un área donde tengas red inalambrica.", 2000).show()
                    }

                    is NoSuchElementException -> {
                        Snackbar.make( binding.root, "No tienes conexión al servidor, busca un área donde tengas red.", 2000).show()
                    }
                    else -> {
                        Snackbar.make( binding.root,"Se presentó un error durante la conexión al servidor: ", 2000).show()
                    }
                }
            }
        }
    }

    private fun initComponents() {

        binding.recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        adapter = HeroAdapter( object : HeroAdapter.OnClickListener {
            override fun onClickListener(
                option: HeroAdapter.ButtonType,
                item: HeroResultResponse ) {

                val bundle = Bundle()
                bundle.putString("image", item.image?.imageUrl)

                when ( option){
                    HeroAdapter.ButtonType.BIOGRAPHY -> {
                        supportFragmentManager?.let {
                            val biography = item.biography
                            biography?.name = item.name
                            bundle.putString("data", Gson().toJson(biography))
                            biographyDialog.arguments = bundle
                            biographyDialog.setStyle( DialogFragment.STYLE_NORMAL, R.style.DialogFragmentTheme )
                            biographyDialog.show(it, "Biography")
                        }
                    }
                    HeroAdapter.ButtonType.STAT -> {
                        supportFragmentManager?.let {
                            val stats = item.powerStats
                            stats?.name = item.name
                            bundle.putString("data", Gson().toJson(stats))
                            statDialog.arguments = bundle
                            statDialog.setStyle( DialogFragment.STYLE_NORMAL, R.style.DialogFragmentTheme )
                            statDialog.show(it, "Stats")
                        }}
                    HeroAdapter.ButtonType.CONNECTION -> {
                        supportFragmentManager?.let {
                            val connection = item.connections
                            connection?.name = item.name
                            bundle.putString("data", Gson().toJson(connection))
                            connectionsDialog.arguments = bundle
                            connectionsDialog.setStyle( DialogFragment.STYLE_NORMAL, R.style.DialogFragmentTheme )
                            connectionsDialog.show(it, "Connections")
                        }}
                    HeroAdapter.ButtonType.APPEARANCE -> {
                        supportFragmentManager?.let {
                            val appereance = item.appereance
                            appereance?.name = item.name
                            bundle.putString("data", Gson().toJson(appereance))
                            appearanceDialog.arguments = bundle
                            appearanceDialog.setStyle( DialogFragment.STYLE_NORMAL, R.style.DialogFragmentTheme )
                            appearanceDialog.show(it, "Appearance")
                        }}
                    HeroAdapter.ButtonType.JOB -> {
                        supportFragmentManager?.let {
                            val job = item.work
                            job?.name = item.name
                            bundle.putString("data", Gson().toJson(job))
                            jobDialog.arguments = bundle
                            jobDialog.setStyle( DialogFragment.STYLE_NORMAL, R.style.DialogFragmentTheme )
                            jobDialog.show(it, "Job")
                        }}
                    else -> {}
                }
            }

            override fun loadNewItems(page: Int) {
                viewModel.loadHeroes( Constants.ALPHABET.get(page))
            }
        })
    }

    private fun setListeners() {
        binding.recyclerView.adapter = adapter
    }

    private fun setObservers() {
        viewModel.onHeroesList.observe(this, heroesObserver )
        viewModel.onError.observe(this, onErrorObserver )
        viewModel.onException .observe(this, onExceptionObserver )
        viewModel.onSuccess.observe(this, onSuccessObserver )
        viewModel.onLoading.observe(this, loaderObserver )
    }

    override fun onDestroy() {
        viewModel.onHeroesList.removeObserver(heroesObserver)
        viewModel.onError.removeObserver(onErrorObserver)
        viewModel.onException.removeObserver(onExceptionObserver)
        viewModel.onSuccess.removeObserver(onSuccessObserver)
        viewModel.onLoading.removeObserver(loaderObserver)
        super.onDestroy()
    }


}