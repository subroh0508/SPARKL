package net.subroh0508.otonashi.sample.ktorreact.repository

import net.subroh0508.otonashi.core.Kotori
import net.subroh0508.otonashi.core.Otonashi
import net.subroh0508.otonashi.core.operators.functions.contains
import net.subroh0508.otonashi.core.vocabulary.common.rdfP
import net.subroh0508.otonashi.core.vocabulary.common.rdfsP
import net.subroh0508.otonashi.sample.ktorreact.httpclient.KtorClient
import net.subroh0508.otonashi.sample.ktorreact.model.CityResult
import net.subroh0508.otonashi.triples.Prefix
import net.subroh0508.otonashi.vocabularies.schema.SchemaPrefix
import net.subroh0508.otonashi.vocabularies.schema.schemaC
import net.subroh0508.otonashi.vocabularies.schema.schemaVocabularies

object DBPediaRepository {
    suspend fun fetch(prefectureName: String, cityName: String): List<CityResult>
            = KtorClient.get(
                buildQuery(prefectureName, cityName).toString(),
                CityResult::class
            ).results()

    private fun buildQuery(prefectureName: String, cityName: String): Kotori = init().where {
        v("prefecture") be {
            rdfP.type to schemaC.administrativeArea and
                    iri("dbo:wikiPageWikiLink") to iri("dbc:日本の都道府県") and
                    rdfsP.label to v("prefecture_name")
        }

        v("city") be {
            rdfP.type to schemaC.city and
                    iri("dbo:location") to v("prefecture") and
                    iri("dbo:abstract") to v("abstract") and
                    rdfsP.label to v("city_name")
        }

        when {
            prefectureName.isNotBlank() && cityName.isNotBlank() -> filter {
                contains(v("prefecture_name"), prefectureName) and
                        contains(v("city_name"), cityName)
            }
            prefectureName.isNotBlank() -> filter {
                contains(v("prefecture_name"), prefectureName)
            }
            cityName.isNotBlank() -> filter {
                contains(v("city_name"), cityName)
            }
        }
    }.select { + v("prefecture_name") + v("city_name") + v("abstract") }

    private fun init() = Otonashi.Study {
        destination("http://ja.dbpedia.org/sparql")
        reminds(SchemaPrefix.SCHEMA, DBPediaPrefix, DBPediaCategoryPrefix)
        buildsUp(*schemaVocabularies)
    }

    object DBPediaPrefix : Prefix {
        override val iri = "<http://dbpedia.org/ontology/>"
        override val prefix = "dbo"

        override fun toString() = "PREFIX $prefix: $iri"
    }

    object DBPediaCategoryPrefix : Prefix {
        override val iri = "<http://ja.dbpedia.org/resource/Category:>"
        override val prefix = "dbc"

        override fun toString() = "PREFIX $prefix: $iri"
    }
}