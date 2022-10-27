package com.sourav1.marvel.Util

import com.sourav1.marvel.Data.Database.Entities.CharacterResult
import com.sourav1.marvel.Data.Database.Entities.ComicsResult
import com.sourav1.marvel.Model.DataClasses.CharacterData.Characters
import com.sourav1.marvel.Model.DataClasses.ComicsData.Comics
import retrofit2.Response
import java.math.BigInteger
import java.security.MessageDigest

class Constants {
    companion object {
        const val BASE_URL = "https://gateway.marvel.com"
        const val API_KEY = "f461e195aea359574315b346d68d2d77"
        private const val PRIVATE_KEY = "3d35f3030a4a95513d5622a2b81fac5ec9a7c734"

        val timeStamp: String = System.currentTimeMillis().toString()
        private val input = "$timeStamp$PRIVATE_KEY$API_KEY"
        private val md = MessageDigest.getInstance("MD5")

        val HASH = BigInteger(
            1, md.digest(input.toByteArray())
        ).toString(16).padStart(32, '0')

        fun convertHttpToHttps(url: String): String {
            var temp: String = ""
            val size = url.length
            var idx = 0
            while (idx <= 3) {
                temp += url[idx]
                idx++
            }
            temp += 's'
            while (idx < size) {
                temp += url[idx]
                idx++
            }
            return temp
        }

        fun parseCharacterResponseToCharacterResult(response: Response<Characters>): List<CharacterResult> {
            val mResponse: MutableList<CharacterResult> = mutableListOf()

            response.body()!!.data.results.forEach {
                mResponse.add(
                    CharacterResult(
                        id = it.id,
                        name = it.name,
                        description = if (it.description == null) {
                            "Description not provided by author.."
                        } else {
                            it.description
                        },
                        thumbnailUrl = it.thumbnail.path,
                        thumbnailExtension = it.thumbnail.extension,
                        comicsListURI = it.comics.collectionURI
                    )
                )
            }

            return mResponse.toList()
        }

        fun parseComicResponseToComicsResult(response: Response<Comics>, parentId: Int): List<ComicsResult> {
            val mResponse: MutableList<ComicsResult> = mutableListOf()

            response.body()!!.data.results.forEach {
                mResponse.add(
                    ComicsResult(
                        id = it.id,
                        parentId = parentId,
                        title = it.title,
                        description = if (it.description == null) {
                            "Description not provided by author.."
                        } else {
                            it.description
                        },
                        thumbnailExtension = it.thumbnail.extension,
                        thumbnailUrl = it.thumbnail.path,
                        printPrice = if (it.prices.size >= 1) {
                            it.prices[0].price
                        } else {
                            0.00
                        },
                        digitalPurchasePrice = if (it.prices.size >= 2) {
                            it.prices[1].price
                        } else {
                            0.00
                        },
                        pageCount = it.pageCount,
                        detailsUrl = if (it.urls.size >= 1) {
                            it.urls[0].url
                        } else {
                            null
                        },
                        purchaseUrl = if (it.urls.size >= 2) {
                            it.urls[1].url
                        } else {
                            null
                        }
                    )
                )
            }

            return mResponse.toList()
        }
    }
}