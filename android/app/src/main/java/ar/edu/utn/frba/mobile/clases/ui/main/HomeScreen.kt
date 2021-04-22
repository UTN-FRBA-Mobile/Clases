package ar.edu.utn.frba.mobile.clases.ui.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
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
import ar.edu.utn.frba.mobile.clases.ui.model.Tweet
import coil.compose.AsyncImage
import kotlinx.coroutines.flow.distinctUntilChanged
import java.util.Date

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(viewModel: TweetsViewModel = viewModel(), navController: NavController? = null) {
    val tweets = viewModel.tweetsLiveData.observeAsState(emptyList()).value
    val isLoading = viewModel.isLoadingLiveData.observeAsState(false).value
    val pullRefreshState = rememberPullRefreshState(isLoading, { viewModel.loadNewTweets() })
    val listState = rememberLazyListState()
    LaunchedEffect(true) {
        viewModel.loadNewTweets()
    }
    AppScaffold(navController = navController) {
        Box(
            Modifier
                .pullRefresh(pullRefreshState)) {
            LazyColumn(state = listState) {
                items(items = tweets, key = { it.ts }) {
                    TweetView(it)
                }
            }
            PullRefreshIndicator(
                refreshing = isLoading,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter))
        }
    }
    InfiniteListHandler(listState = listState) {
        viewModel.loadOlderTweets()
    }
}

@Composable
fun TweetView(tweet: Tweet) {
    Row(
        modifier = Modifier.padding(8.dp)) {
        AsyncImage(
            model = tweet.profilePic,
            contentDescription = tweet.username,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(end = 8.dp)
                .size(48.dp)
                .clip(CircleShape)
                .background(Color.Gray))
        Column {
            Row() {
                Text(
                    text = tweet.name,
                    style = TextStyle(
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold),
                    modifier = Modifier.alignByBaseline())
                if (tweet.certified) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_check_circle_black_24dp),
                        contentDescription = "Certified",
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .alignBy { it.measuredHeight * 3 / 4 })
                }
                Text(
                    text = tweet.username,
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .alignByBaseline())
            }
            Text(
                text = tweet.content,
                modifier = Modifier.padding(bottom = 8.dp))
            if (tweet.image != null) {
                AsyncImage(
                    model = tweet.image,
                    contentDescription = tweet.content,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .aspectRatio(1.75F)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.Gray))
            }
            Row() {
                Counter(
                    icon = painterResource(id = R.drawable.ic_chat_bubble_outline_black_24dp),
                    iconDescription = stringResource(id = R.string.commentCount),
                    text = "${tweet.commentCount}",
                    modifier = Modifier.weight(1F))
                Counter(
                    icon = painterResource(id = R.drawable.ic_repeat_black_24dp),
                    iconDescription = stringResource(id = R.string.retweetCount),
                    text = "${tweet.retweetCount}",
                    modifier = Modifier.weight(1F))
                Counter(
                    icon = painterResource(id = R.drawable.ic_star_border_black_24dp),
                    iconDescription = stringResource(id = R.string.likeCount),
                    text = "${tweet.likeCount}",
                    modifier = Modifier.weight(1F))
                Counter(
                    icon = painterResource(id = R.drawable.ic_share_black_24dp),
                    iconDescription = stringResource(id = R.string.share),
                    text = "",
                    modifier = Modifier.weight(1F))
            }
        }
    }
}

@Composable
fun Counter(
    icon: Painter,
    iconDescription: String,
    text: String,
    modifier: Modifier) {
    Row(modifier = modifier) {
        Image(
            painter = icon,
            contentDescription = iconDescription,
            modifier = Modifier.align(Alignment.CenterVertically))
        Text(
            text = text,
            modifier = Modifier
                .padding(start = 4.dp)
                .align(Alignment.CenterVertically))
    }
}

/**
 * Handler to make any lazy column (or lazy row) infinite. Will notify the [onLoadMore]
 * callback once needed
 * @see <a href="https://dev.to/luismierez/infinite-lazycolumn-in-jetpack-compose-44a4">Source post</a>
 * @param listState state of the list that needs to also be passed to the LazyColumn composable.
 * Get it by calling rememberLazyListState()
 * @param buffer the number of items before the end of the list to call the onLoadMore callback
 * @param onLoadMore will notify when we need to load more
 */
@Composable
fun InfiniteListHandler(
    listState: LazyListState,
    buffer: Int = 2,
    onLoadMore: () -> Unit
) {
    val loadMore = remember {
        derivedStateOf {
            val layoutInfo = listState.layoutInfo
            val totalItemsNumber = layoutInfo.totalItemsCount
            val lastVisibleItemIndex = (layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0) + 1

            lastVisibleItemIndex > (totalItemsNumber - buffer)
        }
    }

    LaunchedEffect(loadMore) {
        snapshotFlow { loadMore.value }
            .distinctUntilChanged()
            .collect {
                onLoadMore()
            }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TweetView(Tweet(
        ts = Date().time,
        name = "Name",
        certified = true,
        commentCount = 123,
        content = "Content",
        image = "",
        likeCount = 234,
        retweetCount = 345,
        profilePic = "",
        username = "@username"))
}