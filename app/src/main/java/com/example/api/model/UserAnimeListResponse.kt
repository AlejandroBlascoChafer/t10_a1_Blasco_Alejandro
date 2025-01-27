package com.example.api.model

data class GraphQLRequest(
    val query: String,
    val variables: Map<String, String>
)

data class UserAnimeListResponse(
    val data: MediaListCollectionData
)

data class MediaListCollectionData(
    val MediaListCollection: MediaListCollection
)

data class MediaListCollection(
    val lists: List<AnimeList>
)

data class AnimeList(
    val entries: List<AnimeListEntry>
)

data class AnimeListEntry(
    val score: Int,
    val media: Anime
)

data class Anime(
    val title: Title,
    val coverImage: CoverImage
)

data class Title(
    val english: String
)

data class CoverImage(
    val medium: String
)
