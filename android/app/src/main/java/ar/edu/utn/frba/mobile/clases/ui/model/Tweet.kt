package ar.edu.utn.frba.mobile.clases.ui.model

data class Tweet(
    /**
     * Id y timestamp del tweet
     */
    val ts: Long,
    val profilePic: String,
    val name: String,
    val certified: Boolean,
    val username: String,
    val content: String,
    val image: String?,
    val commentCount: Long,
    val retweetCount: Long,
    val likeCount: Long
)