package ar.edu.utn.frba.mobile.clases.ui.movies

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import ar.edu.utn.frba.mobile.clases.R
import ar.edu.utn.frba.mobile.clases.core.MockMovieApi
import ar.edu.utn.frba.mobile.clases.core.MovieCategory
import ar.edu.utn.frba.mobile.clases.ui.main.AppScaffold
import ar.edu.utn.frba.mobile.clases.ui.theme.ClasesTheme

@Composable
fun MoviesScreen(viewModel: MoviesViewModel = viewModel(), navController: NavController? = null) {
    val categoriesState = viewModel.categories.observeAsState()
    val categories = categoriesState.value
    AppScaffold(title = stringResource(id = R.string.movies), navController = navController) {
        if (categories == null) {
            Loading()
        } else {
            Movies(categories)
        }
    }
}

@Composable
fun Loading() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun Movies(categories: List<MovieCategory>) {
    LazyColumn {
        categories.forEach { category ->
            item {
                CategoryRow(category.name)
            }
            items(items = category.movies) {movie ->
                MovieRow(
                    name = movie.name,
                    summary = movie.summary,
                    poster = movie.moviePoster
                )
            }
        }
    }
}

@Composable
private fun MovieRow(name: String, summary: String, @DrawableRes poster: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(10.dp)
    ) {
        Image(
            painterResource(id = poster), contentDescription = "",
            Modifier.weight(1F)
        )
        Column(
            Modifier
                .weight(2F)
                .padding(start = 10.dp)
        ) {
            Text(
                text = name,
                style = TextStyle(
                    fontWeight = FontWeight.Bold
                )
            )
            Text(text = summary)
        }
    }
}

@Composable
private fun CategoryRow(name: String) {
    Text(
        text = name,
        style = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = colorResource(id = R.color.colorAccent)
        ),
        modifier = Modifier.padding(10.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun CategoryRowPreview() {
    ClasesTheme {
        CategoryRow("Category Name")
    }
}

@Preview(showBackground = true)
@Composable
fun MovieRowPreview() {
    ClasesTheme {
        MovieRow(
            name = "Volver al futuro",
            summary = "Marty McFly, un estudiante de secundaria de 17 años, es enviado accidentalmente treinta años al pasado en un DeLorean que viaja en el tiempo, inventado por su gran amigo, el excéntrico científico Doc Brown.",
            poster = R.drawable.movie_icon_1)
    }
}

@Preview(showBackground = true)
@Composable
fun MoviesPreview() {
    ClasesTheme {
        Movies(MockMovieApi.sampleMovies())
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingPreview() {
    Loading()
}
