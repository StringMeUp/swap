package com.example.jakala_swapi.ui.screens.movies


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.jakala_swapi.R
import com.example.jakala_swapi.helper.UiState.defaultMoviesUiState
import com.example.jakala_swapi.ui.MovieUiState
import com.example.jakala_swapi.ui.UiState
import com.example.jakala_swapi.widgets.HeaderItem

@Composable
fun MoviesScreen(
    viewModel: MoviesViewModel = hiltViewModel(),
    navigateToDetail: (id:String) -> Unit = {},
    padding: PaddingValues
) {

    val moviesUiState by viewModel.movieUiState.collectAsStateWithLifecycle()
    Scaffold(Modifier.padding(padding)) {
        MoviesScreenContent(
            moviesUiState = moviesUiState,
            padding = it,
            findMovieId = { viewModel.setMovieId(it) },
            navigateToDetail = { navigateToDetail(it) },
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun MoviesScreenContent(
    moviesUiState: UiState = defaultMoviesUiState(),
    padding: PaddingValues = PaddingValues(),
    navigateToDetail: (id:String) -> Unit = {},
    findMovieId: (input: String) -> Unit = {}
) {

    when (val state = moviesUiState) {
        UiState.Error -> {
            Text(text = "Error")
        }

        UiState.Loading -> {
            Text(text = "Loading")
        }

        is MovieUiState.Success -> {
            LazyColumn {
                item {
                    HeaderItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp)
                    )
                }

                items(state.movies) {
                    ListItem(
                        modifier = Modifier
                            .padding(8.dp)
                            .shadow(2.dp, RoundedCornerShape(16.dp))
                            .clickable {
                                findMovieId(it.url ?: "")
                                navigateToDetail(it.url?.filter { it.isDigit() }.toString())
                            },
                        colors = ListItemDefaults.colors(containerColor = Color.LightGray),

                        leadingContent = {
                            Image(
                                modifier = Modifier.size(100.dp),
                                painter = painterResource(id = R.drawable.ic_movie_selected),
                                contentDescription = ""
                            )
                        },
                        headlineContent = {
                            Text(text = it.title)
                        },
                        supportingContent = {
                            Text(text = it.director)
                            Text(text = it.producer)
                        })
                }
            }
        }

        else -> {
            /** Ignore */
        }
    }
}