package com.majestadev.data.repository.paging.characters

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.majestadev.data.cache.Cache
import com.majestadev.data.network.Network
import com.majestadev.domain.entity.db.CharacterEpisodeCrossRef
import com.majestadev.domain.entity.network.characters.CharacterDetails
import com.majestadev.domain.entity.network.characters.Place
import com.majestadev.domain.util.RegexUtil

class CharactersPagingSource(
    private val cache: Cache,
    private val network: Network
) : PagingSource<Int, CharacterDetails>() {

    override fun getRefreshKey(state: PagingState<Int, CharacterDetails>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterDetails> {
        var results: List<CharacterDetails>

        val page: Int = params.key ?: FIRST_PAGE_INDEX
        val pageSize = params.loadSize
        var nextKey: Int? = null

        try {
            network.getCharacters(page).apply {

                if (this.info.next != null) {
                    val uri = Uri.parse(this.info.next)
                    val nextPageQuery = uri.getQueryParameter("page")
                    nextKey = nextPageQuery?.toInt()
                }

                results = this.results

                results.forEach { characterDetails ->

                    cache.addCharacter(characterDetails.toCharacterEntity())

                    characterDetails.episode.forEach { episodeUrl ->

                        cache.addCharacterEpisodeCrossRef(
                            CharacterEpisodeCrossRef(
                                characterDetails.id,
                                RegexUtil.extractIdFromUrl(episodeUrl)
                            )
                        )
                    }
                }
            }
        } catch (e: Exception) {
            cache.getCharacters().apply {
                nextKey = if (size < pageSize) null else nextKey?.plus(1)
                results = this.map { characterEntity ->
                    CharacterDetails(
                        characterEntity.characterId,
                        characterEntity.name,
                        characterEntity.status,
                        characterEntity.species,
                        characterEntity.type,
                        characterEntity.gender,
                        Place("", ""),
                        Place("", ""),
                        characterEntity.image,
                        emptyList()
                    )
                }
            }
        }

        return LoadResult.Page(data = results, prevKey = null, nextKey = nextKey)
    }

    companion object {
        private const val FIRST_PAGE_INDEX = 1
    }
}