package ar.edu.utn.frba.mobile.clases.ui.status_update

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ar.edu.utn.frba.mobile.clases.R
import ar.edu.utn.frba.mobile.clases.ui.main.AppScaffold

@Composable
fun StatusUpdateScreen(navController: NavController? = null) {
    val (text, setText) = rememberSaveable { mutableStateOf("") }
    val (themeIndex, setThemeIndex) = rememberSaveable { mutableStateOf(0) }
    val themes = remember { PostTheme.createThemes() }
    val selectedTheme = themes[themeIndex]
    AppScaffold(
        title = stringResource(R.string.whatAreYouThinking),
        navController = navController) {
        Column {
            ProfileHeader(
                image = painterResource(R.drawable.mirtha),
                name = "Mirtha Legrand")
            BasicTextField(
                value = text,
                onValueChange = setText,
                textStyle = TextStyle(
                    color = selectedTheme.textColor),
                keyboardOptions = KeyboardOptions(
                    autoCorrect = true),
                modifier = Modifier.background(brush = Brush.linearGradient(
                    colors = selectedTheme.backgroundColors,
                    start = Offset(0.0f, 0.0f),
                    end = Offset(100.0f, 100.0f))))
            ColorPicker(themes, themeIndex, setThemeIndex)
            AttachmentToolbar()
        }
    }
}

@Composable
fun ProfileHeader(image: Painter, name: String) {
    Row {
        Image(painter = image, contentDescription = "")
        Column {
            Text(text = name)
            Row {
                Pill(
                    text = stringResource(R.string.friends),
                    icon = painterResource(R.drawable.ic_group_black_18dp))
                Pill(
                    text = stringResource(R.string.friends),
                    icon = painterResource(R.drawable.ic_add_black_18dp))
            }
        }
    }
}

@Composable
fun Pill(text: String, icon: Painter) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(4.dp)
            .border(width = 1.dp, color = Color(0x808080FF), shape = RoundedCornerShape(8.dp))
            .padding(4.dp)) {
        Image(painter = icon, contentDescription = "")
        Text(
            text = text)
        Image(painter = painterResource(R.drawable.ic_arrow_drop_down_black_18dp), contentDescription = "")
    }
}

@Composable
fun ColorPicker(themes: List<PostTheme>, selectedIndex: Int, onSelect: (Int) -> Unit) {
    val shape = RoundedCornerShape(8.dp)
    val modifier = Modifier
        .padding(5.dp)
        .size(dimensionResource(id = R.dimen.color_size))
        .border(width = Dp.Hairline, color = Color.Gray, shape = shape)
        .clip(shape)
    Box {
        LazyRow(
            contentPadding = PaddingValues(horizontal = 42.dp)) {
            itemsIndexed(items = themes) { index, theme ->
                Box(modifier = modifier
                    .background(
                        brush = Brush.linearGradient(
                            colors = theme.backgroundColors,
                            start = Offset(0.0f, 0.0f),
                            end = Offset(100.0f, 100.0f)
                        )
                    )
                    .clickable { onSelect(index) })
            }
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .background(color = Color.White)) {
            Image(
                painter = painterResource(R.drawable.ic_chevron_left_black_24dp),
                contentDescription = "")
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .background(color = Color.White)) {
            Image(
                painter = painterResource(R.drawable.ic_grid_on_black_24dp),
                contentDescription = "")
        }
    }
}

@Composable
fun AttachmentToolbar() {
    Row {
        Text(stringResource(R.string.addToYourPost))
        Image(
            painter = painterResource(R.drawable.ic_photo_library_black_24dp),
            contentDescription = "",
            colorFilter = ColorFilter.tint(Color(0xff40c040)))
        Image(
            painter = painterResource(R.drawable.ic_person_add_black_24dp),
            contentDescription = "",
            colorFilter = ColorFilter.tint(Color(0xff4080ff)))
        Image(
            painter = painterResource(R.drawable.ic_sentiment_very_satisfied_black_24dp),
            contentDescription = "",
            colorFilter = ColorFilter.tint(Color(0xffffc000)))
        Image(
            painter = painterResource(R.drawable.ic_location_on_black_24dp),
            contentDescription = "",
            colorFilter = ColorFilter.tint(Color(0xffff2020)))
    }
}

@Preview
@Composable
fun StatusUpdatePreview() {
    StatusUpdateScreen()
}
