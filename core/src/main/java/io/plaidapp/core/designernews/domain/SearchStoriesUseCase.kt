/*
 * Copyright 2018 Google LLC.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.plaidapp.core.designernews.domain

import io.plaidapp.core.data.Result
import io.plaidapp.core.designernews.data.stories.StoriesRepository
import io.plaidapp.core.designernews.data.stories.model.Story
import io.plaidapp.core.designernews.data.stories.model.toStory
import io.plaidapp.core.util.exhaustive
import javax.inject.Inject

/**
 * Use case that searches for stories based on a query and a page in [StoriesRepository]
 */
class SearchStoriesUseCase @Inject constructor(private val storiesRepository: StoriesRepository) {

    suspend operator fun invoke(query: String, page: Int): Result<List<Story>> {
        val result = storiesRepository.search(query, page)
        when (result) {
            is Result.Success -> {
                val stories = result.data.map { it.toStory(page) }
                return Result.Success(stories)
            }
            is Result.Error -> return result
        }.exhaustive
    }
}
