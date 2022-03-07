package roberto.cano.superheroapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import roberto.cano.superheroapp.core.RetrofitHelper
import roberto.cano.superheroapp.data.model.ExceptionData
import roberto.cano.superheroapp.data.model.response.HeroResultResponse
import roberto.cano.superheroapp.data.network.SuperHeroService

class MainActivityViewModel : ViewModel() {

    private val service = RetrofitHelper.getAPIService().create(SuperHeroService::class.java)
    var onException = MutableLiveData<ExceptionData> ()
    var onError = MutableLiveData<String>()
    var onSuccess = MutableLiveData<String>()
    var onLoading = MutableLiveData<Boolean>()
    var onHeroesList = MutableLiveData<ArrayList<HeroResultResponse>>()

    private fun setError ( message : String) = CoroutineScope(Dispatchers.Main).launch{
        setOnLoading(false)
            onError.value = message
    }

    private fun setOnLoading ( status : Boolean) = CoroutineScope(Dispatchers.Main).launch{
            onLoading.value = status
    }

    private fun setOnSuccess ( message : String) = CoroutineScope(Dispatchers.Main).launch{
            onSuccess.value = message
    }

    private fun setException ( exception : ExceptionData) = CoroutineScope(Dispatchers.Main).launch{
        setOnLoading(false)
            onException.value = exception
    }

    fun loadHeroes ( request : Char) = CoroutineScope(Dispatchers.IO).launch{
        try {
            setOnLoading(true)
            service.searchHeroes(  request ).let {
                if (it.isSuccessful){
                    it.body()?.let {
                        setOnLoading(false)
                        CoroutineScope(Dispatchers.Main).launch {
                            onHeroesList.value = it.results ?: ArrayList()
                        }
                    }?: run {
                        throw NoSuchElementException()
                    }
                }else{
                    throw NoSuchElementException()
                }
            }
        } catch (e: Exception){
            println("exception saltó $e")
            setException( ExceptionData(e , "loadHeroes"))
        }
    }

}