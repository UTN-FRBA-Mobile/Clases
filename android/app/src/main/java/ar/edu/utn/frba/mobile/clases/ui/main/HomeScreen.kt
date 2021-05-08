package ar.edu.utn.frba.mobile.clases.ui.main

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Button
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import ar.edu.utn.frba.mobile.clases.MainViewActivity
import ar.edu.utn.frba.mobile.clases.R
import coil.compose.AsyncImage

@Composable
fun HomeScreen(
    viewModel: MainViewModel = viewModel(),
    navController: NavController? = null) {
    val isGridView = viewModel.showAsGrid.observeAsState(false).value
    val numberOfColumns = viewModel.numberOfColumns.observeAsState(1).value
    val images = viewModel.images.observeAsState(emptyList()).value
    val context = LocalContext.current
    val addImageLauncher = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) {
        if (it != null) {
            launchEditImageActivity(context, it)
        }
    }
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> { viewModel.reloadImages() }
                else -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
    HomeScreenView(
        navController = navController,
        isGridView = isGridView,
        numberOfColumns = numberOfColumns,
        images = images,
        onToggleShowAsGrid = { viewModel.toggleShowAsGrid() },
        onAddImage = { addImageLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) })
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
private fun HomeScreenView(
    navController: NavController?,
    isGridView: Boolean,
    numberOfColumns: Int,
    images: List<StoredImage>,
    onToggleShowAsGrid: () -> Unit,
    onAddImage: () -> Unit,
) {
    AppScaffold(
        navController = navController,
        actions = {
            Button(onClick = {
                onToggleShowAsGrid()
            }) {
                Text(text = stringResource(id = if (isGridView) R.string.toggle_list else R.string.toggle_grid))
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddImage) {
                Icon(
                    painter = painterResource(R.drawable.ic_add_black_24dp),
                    contentDescription = stringResource(R.string.add_image)
                )
            }
        }) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(numberOfColumns)
        ) {
            items(
                items = images,
                key = { it.uri }) {
                Picture(
                    image = it,
                    modifier = Modifier.animateItem()
                )
            }
        }
    }
}

@Composable
fun Picture(
    image: StoredImage,
    modifier: Modifier) {
    AsyncImage(
        model = image.uri,
        contentDescription = "",
        modifier = modifier
            .padding(2.dp)
            .aspectRatio(1F))
}

private fun launchEditImageActivity(context: Context, uri: Uri) {
    val intent = Intent(context, MainViewActivity::class.java)
    intent.putExtra(MainViewActivity.URI_EXTRA, uri.toString())
    context.startActivity(intent)
}

@Preview
@Composable
fun DefaultPreview() {
    HomeScreenView(
        navController = null,
        isGridView = true,
        numberOfColumns = 3,
        images = emptyList(),
        onToggleShowAsGrid = {},
        onAddImage = {})
}
