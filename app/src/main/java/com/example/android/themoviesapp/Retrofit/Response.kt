package com.example.android.themoviesapp.Retrofit
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


data class ResponseMessageDetails (
    var message:String = "",
    var statusCode:String = "",
    var isSuccessFul:Boolean = false
        )


//Get Upcoming movies list
data class GetUpComingMoviesResponse(
    @SerializedName("dates")var date:Dates,
    @SerializedName("page") var page:Int=0,
    @SerializedName("results") var moviesList:ArrayList<UpComingMovies> = arrayListOf(),
    @SerializedName("total_pages") var totalPages:Int=0,
    @SerializedName("total_results") var totalResults:Int=0,
)

data class Dates (
    @SerializedName("maximum") var maximum: String? = null,
    @SerializedName("minimum") var minimum: String? = null
)

data class UpComingMovies(
    @SerializedName("adult") var adult:Boolean = false,
    @SerializedName("backdrop_path") var backdropPath: String? = null,
    @SerializedName("genre_ids") var genreIds: ArrayList<Long> = arrayListOf(),
    @SerializedName("id") var movieId: Long = 0,
    @SerializedName("original_language") var originalLanguage: String? = null,
    @SerializedName("original_title") var originalTitle: String? = null,
    @SerializedName("overview") var overview: String? = null,
    @SerializedName("popularity") var popularity:Double = 0.0,
    @SerializedName("poster_path") var posterPath: String? = null,
    @SerializedName("release_date") var releaseDate: String? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("video") var video:Boolean = false,
    @SerializedName("vote_average") var voteAverage:Double = 0.0,
    @SerializedName("vote_count") var voteCount: Long = 0
)

//Get Movie Details
@Parcelize
data class GetMovieDetailsResponse (
    @SerializedName("adult") var adult:Boolean = false,
    @SerializedName("backdrop_path") var backdropPath: String? = null,
    //@SerializedName("belongs_to_collection") var belongsToCollection: BelongsToCollection? = null,
    @SerializedName("budget") var budget: Long = 0,
    @SerializedName("genres") var genres: ArrayList<Genre> = arrayListOf(),
    @SerializedName("homepage") var homepage: String? = null,
    @SerializedName("id") var id: Long = 0,
    @SerializedName("imdb_id") var imdbId: String? = null,
    @SerializedName("original_language") var originalLanguage: String? = null,
    @SerializedName("original_title") var originalTitle: String? = null,
    @SerializedName("overview") var overview: String? = null,
    @SerializedName("popularity") var popularity:Double = 0.0,
    @SerializedName("poster_path") var posterPath: String? = null,
    //@SerializedName("production_companies") var productionCompanies: ArrayList<ProductionCompany>? = arrayListOf(),
   // @SerializedName("production_countries") var productionCountries: ArrayList<ProductionCountry>? = arrayListOf(),
    @SerializedName("release_date") var releaseDate: String? = null,
    @SerializedName("revenue") var revenue: Long = 0,
    @SerializedName("runtime") var runtime: Long = 0,
    //@SerializedName("spoken_languages") var spokenLanguages: List<SpokenLanguage>? = null,
    @SerializedName("status") var status: String? = null,
    @SerializedName("tagline") var tagline: String? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("video") var video:Boolean = false,
    @SerializedName("vote_average") var voteAverage:Double = 0.0,
    @SerializedName("vote_count") var voteCount: Long = 0
):Parcelable

data class BelongsToCollection (
    @SerializedName("id") var id: Long = 0,
    @SerializedName("name") var name: String? = null,
    @SerializedName("poster_path") var posterPath: String? = null,
    @SerializedName("backdrop_path") var backdropPath: String? = null

)
@Parcelize
data class Genre (
    @SerializedName("id") var id: Long = 0,
    @SerializedName("name") var name: String? = null

    ):Parcelable

data class ProductionCompany (
    @SerializedName("id") var id: Long = 0,
    @SerializedName("logo_path") var logoPath: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("origin_country") var originCountry: String? = null,

)

data class ProductionCountry (
    @SerializedName("iso_3166_1") var iso31661: String? = null,
    @SerializedName("name") var name: String? = null

)

data class SpokenLanguage (
    @SerializedName("english_name") var englishName: String? = null,
    @SerializedName("iso_639_1") var iso6391: String? = null,
    @SerializedName("name") var name: String? = null
)

//Get movie posters
data class GetMoviePostersResponse (
   // @SerializedName("backdrops") var backdrops: ArrayList<Backdrop>? = null,

    @SerializedName("id") var movieId: Long = 0,
    //@SerializedName("logos") var logos: List<Any>? = null,
    @SerializedName("posters") var posters: ArrayList<Poster> = arrayListOf(),
)

data class Poster (
    //@SerializedName("aspect_ratio") var aspectRatio = 0.0
   // @SerializedName("height") var height: Long = 0
    //@SerializedName("iso_639_1") var iso6391: String? = null
    @SerializedName("file_path") var filePath: String? = ""
    //@SerializedName("vote_average") var voteAverage = 0.0
   // @SerializedName("vote_count") var voteCount: Long = 0
    //@SerializedName("width") var width: Long = 0
    )

//get movie url
data class GetMovieTrailerUrlsResponse (
    @SerializedName("id") var id: Long = 0,
    @SerializedName("results") var urlResults: ArrayList<UrlResult> = arrayListOf()
)

data class UrlResult (
    @SerializedName("iso_639_1") var iso6391: String? = null,
    @SerializedName("iso_3166_1") var iso31661: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("key") var key: String? = null,
    @SerializedName("site") var site: String? = null,
    @SerializedName("size") var size: Long = 0,
    @SerializedName("type") var type: String? = null,
    @SerializedName("official") var official:Boolean = false,
    @SerializedName("published_at") var publishedAt: String? = null,
    @SerializedName("id") var id: String? = null,
)


