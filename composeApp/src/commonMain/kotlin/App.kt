import androidx.compose.animation.Crossfade
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ListItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import api.NewsApi
import api.toArticle
import coil3.compose.AsyncImage
import database.entity.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    val newsApi = remember { NewsApi.create() }
    val database = remember { Factory.instance.createAppDatabase() }
    val articleDao = remember { database.articleDao() }

    val articles by articleDao.observeAll().collectAsState(initial = emptyList())

    val coroutineScope = rememberCoroutineScope()

    MaterialTheme {
        Box {
            LazyColumn(Modifier.fillMaxSize()) {
                items(articles) { article ->
                    ArticleItem(article = article)
                }
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
            ) {
                FloatingActionButton(
                    onClick = {
                        coroutineScope.launch(Dispatchers.IO) {
                            articleDao.deleteAll()
                        }
                    },
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Delete,
                        contentDescription = null
                    )
                }
                FloatingActionButton(
                    onClick = {
                        coroutineScope.launch(Dispatchers.IO) {
                            val newArticles = newsApi
                                .fetch()
                                ?.articles
                                ?.map { it.toArticle() }
                                ?: emptyList()
                            articleDao.insertOrReplaceAll(*newArticles.toTypedArray())
                        }
                    },
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Refresh,
                        contentDescription = null
                    )
                }
            }
        }

    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ArticleItem(
    article: Article,
    modifier: Modifier = Modifier
) {
    val uriHandler = LocalUriHandler.current
    var expanded by remember { mutableStateOf(false) }
    Crossfade(
        targetState = expanded,
        modifier = modifier
    ) { currentExpended ->
        if (!currentExpended) {
            ListItem(
                text = {
                    Text(article.title)
                },
                secondaryText = {
                    Text(article.description, maxLines = 1)
                },
                icon = {
                    AsyncImage(
                        model = article.urlToImage,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(56.dp)
                            .clip(RoundedCornerShape(4.dp))
                    )
                },
                modifier = Modifier.clickable { expanded = !expanded }
            )
        } else {
            Card {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    AsyncImage(
                        model = article.urlToImage,
                        contentDescription = null,
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { expanded = !expanded }
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ) {
                        Text(
                            text = article.title,
                            style = MaterialTheme.typography.h4,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(
                            onClick = { uriHandler.openUri(article.url) }
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Info,
                                contentDescription = null
                            )
                        }
                    }
                    Text(
                        text = "${article.author}(Published At ${article.publishedAt})",
                        style = MaterialTheme.typography.overline,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    Text(
                        text = article.content,
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    Spacer(Modifier.height(16.dp))
                }
            }
        }
    }
}