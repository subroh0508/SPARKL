package net.subroh0508.otonashi.vocabularies.imasparql
import net.subroh0508.otonashi.triples.TripleFacade
import net.subroh0508.otonashi.triples.extensions.get
import net.subroh0508.otonashi.triples.vocabulary.IriVocabulary

/*
 * This file was auto generated by otonashi-vocabularies-generator plugin
 *
 */

val Set<IriVocabulary>.imasC get() = get(ImasparqlClass::class)
val TripleFacade.imasC get() = iri.imasC

val Set<IriVocabulary>.imasP get() = get(ImasparqlProperty::class)
val TripleFacade.imasP get() = iri.imasP