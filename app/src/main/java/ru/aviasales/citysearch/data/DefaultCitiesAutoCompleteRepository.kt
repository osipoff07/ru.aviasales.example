package ru.aviasales.citysearch.data

import ru.aviasales.citysearch.domain.CitiesAutoCompleteRepository
import ru.aviasales.common.Response
import ru.aviasales.citysearch.data.models.CityAutoCompleteModel
import ru.aviasales.citysearch.data.models.CityDataModel
import ru.aviasales.common.domain.ConnectionChecker
import ru.aviasales.common.domain.Mapper
import ru.aviasales.common.domain.mapList
import ru.aviasales.common.domain.model.City
import ru.aviasales.common.domain.NoConnectionException
import ru.aviasales.common.domain.NotFoundException
import ru.aviasales.common.domain.ServerResponseException
import java.lang.Exception

private const val NOT_FOUND_CODE: Int = 404

typealias RetrofitResponse<T> = retrofit2.Response<T>

class DefaultCitiesAutoCompleteRepository(
    private val dataSource: CitiesAutoCompleteRemoteDataSource,
    private val connectionChecker: ConnectionChecker,
    private val dataMapper: Mapper<CityDataModel, City>
): CitiesAutoCompleteRepository {

    //Todo Task.No needs to add cache
    override fun getCities(
        term: String,
        lang: String
    ): Response<List<City>> = try {
        val retrofitResponse: RetrofitResponse<CityAutoCompleteModel> = dataSource.getCities(term, lang).execute()
        val data: CityAutoCompleteModel = getBody(retrofitResponse)
        Response.Success(dataMapper.mapList(data.cities))
    } catch (exception: Exception) {

        Response.Failed(exception = exception)
    }

    @Throws(NotFoundException::class)
    private fun getBody(
       response: RetrofitResponse<CityAutoCompleteModel>
    ): CityAutoCompleteModel {
        val httpCode: Int = response.code()
        val responseMessage: String = response.message()
        if (response.isSuccessful) {

            return response.body() ?: throw ServerResponseException(
                httpCode,
                responseMessage
            )
        }
        if (!connectionChecker.isConnected()) {

            throw NoConnectionException()
        }
        if (httpCode == NOT_FOUND_CODE) {

            throw NotFoundException()
        }
        throw ServerResponseException(httpCode, responseMessage)
    }

}